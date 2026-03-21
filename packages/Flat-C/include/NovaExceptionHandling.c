#include "NovaExceptionHandling.h"

#ifdef _WIN32

static char const* NOVA_PROGRAM_NAME;

void setProgramName(char* name) {
  NOVA_PROGRAM_NAME = name;
}

// void printStack()
// {
//  /*       void *stack[48];
//          USHORT count = CaptureStackBackTrace(0, 48, stack, NULL);
//          for(USHORT c = 0; c < count; c++)
//            printf("addr %02d: %p\n", c, stack[c]);*/
    
//  typedef USHORT (WINAPI *CaptureStackBackTraceType)(__in ULONG, __in ULONG, __out PVOID*, __out_opt PULONG);
//    CaptureStackBackTraceType func = (CaptureStackBackTraceType)(GetProcAddress(LoadLibrary("kernel32.dll"), "RtlCaptureStackBackTrace"));

//    if(func == NULL)
//        return; // WOE 29.SEP.2010

//    // Quote from Microsoft Documentation:
//    // ## Windows Server 2003 and Windows XP:  
//    // ## The sum of the FramesToSkip and FramesToCapture parameters must be less than 63.
//    const int kMaxCallers = 62; 

//    void* callers[kMaxCallers];
//    int count = (func)(0, kMaxCallers, callers, NULL);
//    for(i = 0; i < count; i++)
//       printf(TraceFile, "*** %d called from %016I64LX\n", i, callers[i]);
// }

/* Resolve symbol name and source location given the path to the executable 
   and an address */
char* addr2line(char const * const program_name, void const * const addr)
{
  char addr2line_cmd[512] = {0};
  char* outbuf = NOVA_MALLOC(sizeof(char) * 0);
 
  /* have addr2line map the address to the relent line in the code */
  #ifdef __APPLE__
    /* apple does things differently... */
    sprintf(addr2line_cmd,"atos -o %.256s %p", program_name, addr); 
  #else
    sprintf(addr2line_cmd,"addr2line -f -p -e %.256s %p", program_name, addr); 
    
    FILE *fp = popen(addr2line_cmd, "r");
    char buf[1024];
    int pos = 0;
    
    while (fgets(buf, 1024, fp)) {
        int len = strlen(buf);
        
        outbuf = NOVA_REALLOC(outbuf, sizeof(char) * (pos + len + 1));
        arrayCopy(outbuf, pos, buf, 0, len, len);
        pos += len;
    }
    
    outbuf[pos] = '\0';

    fclose(fp);
  #endif
 
  /* This will print a nicely formatted string specifying the
     function and source line of the address */
  return outbuf;
}

char upstack(CONTEXT* context, STACKFRAME* frame)
{
  return StackWalk(IMAGE_FILE_MACHINE_I386,
                   GetCurrentProcess(),
                   GetCurrentThread(),
                   frame,
                   context,
                   0,
                   SymFunctionTableAccess,
                   SymGetModuleBase,
                   0);
}

#ifdef __GNUC__

void* getEIP()
{
    return __builtin_return_address(0);
}

NOVA_CODE_CONTEXT* nova_get_context() {
    #ifdef _WIN32
        const uintptr_t register esp asm("esp");
        const uintptr_t register ebp asm("ebp");

        CONTEXT* context = (CONTEXT*)NOVA_MALLOC(sizeof(CONTEXT));
        context->Eip = (const uintptr_t)getEIP();
        context->Esp = esp;
        context->Ebp = ebp;
        
        return context;
    #endif
}

char** nova_process_calling_stacktrace(int count, NOVA_CODE_CONTEXT* context, void (*processFunc)(char*, void*), void* ptr) {
  SymInitialize(GetCurrentProcess(), 0, 1);
 
  STACKFRAME frame = { 0 };
 
  /* setup initial stack frame */
  frame.AddrPC.Offset         = context->Eip;
  frame.AddrPC.Mode           = AddrModeFlat;
  frame.AddrStack.Offset      = context->Esp;
  frame.AddrStack.Mode        = AddrModeFlat;
  frame.AddrFrame.Offset      = context->Ebp;
  frame.AddrFrame.Mode        = AddrModeFlat;
 
  for (; count > 0; count--) {
    upstack(context, &frame);
  }
  
  while (upstack(context, &frame)) {
    processFunc(addr2line(NOVA_PROGRAM_NAME, (void*)frame.AddrPC.Offset), ptr);
  }
  
  SymCleanup(GetCurrentProcess());
}

void printTraceElement(char* trace) {
    fputs(trace, stderr);
}

void nova_print_calling_stacktrace(int count, NOVA_CODE_CONTEXT* context) {
    nova_process_calling_stacktrace(count, context, (void (*)(char*, void*))&printTraceElement, 0);
}

void addTraceElement(char* trace, void* ptr) {
    void** ptrs = (void**)ptr;
    char** data = ptrs[0];
    int* size = ptrs[1];
    
    data = NOVA_REALLOC(data, sizeof(char*) * (*size + 1));
    data[*size] = trace;
    
    (*size)++;
}

char** nova_get_calling_stacktrace(int count, NOVA_CODE_CONTEXT* context, int* lines) {
  char** outbuf = NOVA_MALLOC(sizeof(char*) * 0);
  
  void* vs[2] = {outbuf, lines};
  
  nova_process_calling_stacktrace(count, context, &addTraceElement, vs);
  
  return outbuf;
}

#else

void nova_print_calling_stacktrace(int count)
{
  printf("print calling stacktrace unsupported on current OS\n");
}

#endif

void nova_print_stacktrace(CONTEXT* context)
{
  SymInitialize(GetCurrentProcess(), 0, 1);
 
  STACKFRAME frame = { 0 };
 
  /* setup initial stack frame */
  frame.AddrPC.Offset         = context->Eip;
  frame.AddrPC.Mode           = AddrModeFlat;
  frame.AddrStack.Offset      = context->Esp;
  frame.AddrStack.Mode        = AddrModeFlat;
  frame.AddrFrame.Offset      = context->Ebp;
  frame.AddrFrame.Mode        = AddrModeFlat;
 
  while (StackWalk(IMAGE_FILE_MACHINE_I386 ,
                   GetCurrentProcess(),
                   GetCurrentThread(),
                   &frame,
                   context,
                   0,
                   SymFunctionTableAccess,
                   SymGetModuleBase,
                   0 ) )
  {
    fputs(addr2line(NOVA_PROGRAM_NAME, (void*)frame.AddrPC.Offset), stderr);
    fputs("\n", stderr);
  }
 
  SymCleanup(GetCurrentProcess());
}

LONG WINAPI nova_exception_handler(EXCEPTION_POINTERS * ExceptionInfo)
{
    nova_exception_Nova_Exception* exception;
    
  switch(ExceptionInfo->ExceptionRecord->ExceptionCode)
  {
    case EXCEPTION_ACCESS_VIOLATION:
      exception = (nova_exception_Nova_Exception*)nova_exception_Nova_NullAccessException_Nova_construct(0, 0);
      exception->nova_exception_Nova_Exception_Nova_stackTrace = nova_exception_Nova_StackTrace_0_Nova_construct(0, ExceptionInfo->ContextRecord);
      THROW(exception, 0);
      fputs("Error: EXCEPTION_ACCESS_VIOLATION\n", stderr);
      break;
    case EXCEPTION_ARRAY_BOUNDS_EXCEEDED:
      fputs("Error: EXCEPTION_ARRAY_BOUNDS_EXCEEDED\n", stderr);
      break;
    case EXCEPTION_BREAKPOINT:
      fputs("Error: EXCEPTION_BREAKPOINT\n", stderr);
      break;
    case EXCEPTION_DATATYPE_MISALIGNMENT:
      fputs("Error: EXCEPTION_DATATYPE_MISALIGNMENT\n", stderr);
      break;
    case EXCEPTION_FLT_DENORMAL_OPERAND:
      fputs("Error: EXCEPTION_FLT_DENORMAL_OPERAND\n", stderr);
      break;
    case EXCEPTION_FLT_DIVIDE_BY_ZERO:
      fputs("Error: EXCEPTION_FLT_DIVIDE_BY_ZERO\n", stderr);
      break;
    case EXCEPTION_FLT_INEXACT_RESULT:
      fputs("Error: EXCEPTION_FLT_INEXACT_RESULT\n", stderr);
      break;
    case EXCEPTION_FLT_INVALID_OPERATION:
      fputs("Error: EXCEPTION_FLT_INVALID_OPERATION\n", stderr);
      break;
    case EXCEPTION_FLT_OVERFLOW:
      fputs("Error: EXCEPTION_FLT_OVERFLOW\n", stderr);
      break;
    case EXCEPTION_FLT_STACK_CHECK:
      fputs("Error: EXCEPTION_FLT_STACK_CHECK\n", stderr);
      break;
    case EXCEPTION_FLT_UNDERFLOW:
      fputs("Error: EXCEPTION_FLT_UNDERFLOW\n", stderr);
      break;
    case EXCEPTION_ILLEGAL_INSTRUCTION:
      fputs("Error: EXCEPTION_ILLEGAL_INSTRUCTION\n", stderr);
      break;
    case EXCEPTION_IN_PAGE_ERROR:
      fputs("Error: EXCEPTION_IN_PAGE_ERROR\n", stderr);
      break;
    case EXCEPTION_INT_DIVIDE_BY_ZERO:
      fputs("Error: EXCEPTION_INT_DIVIDE_BY_ZERO\n", stderr);
      break;
    case EXCEPTION_INT_OVERFLOW:
      fputs("Error: EXCEPTION_INT_OVERFLOW\n", stderr);
      break;
    case EXCEPTION_INVALID_DISPOSITION:
      fputs("Error: EXCEPTION_INVALID_DISPOSITION\n", stderr);
      break;
    case EXCEPTION_NONCONTINUABLE_EXCEPTION:
      fputs("Error: EXCEPTION_NONCONTINUABLE_EXCEPTION\n", stderr);
      break;
    case EXCEPTION_PRIV_INSTRUCTION:
      fputs("Error: EXCEPTION_PRIV_INSTRUCTION\n", stderr);
      break;
    case EXCEPTION_SINGLE_STEP:
      fputs("Error: EXCEPTION_SINGLE_STEP\n", stderr);
      break;
    case EXCEPTION_STACK_OVERFLOW:
      fputs("Error: EXCEPTION_STACK_OVERFLOW\n", stderr);
      break;
    default:
      fputs("Error: Unrecognized Exception\n", stderr);
      break;
  }
  fflush(stderr);
  
  /* If this is a stack overflow then we can't walk the stack, so just show
    where the error happened */
  if (EXCEPTION_STACK_OVERFLOW != ExceptionInfo->ExceptionRecord->ExceptionCode)
  {
      nova_print_stacktrace(ExceptionInfo->ContextRecord);
  }
  else
  {
      fputs(addr2line(NOVA_PROGRAM_NAME, (void*)ExceptionInfo->ContextRecord->Eip), stderr);
  }
  
  char c[2];
  fgets(c, 2, stdin);
 
  return EXCEPTION_EXECUTE_HANDLER;
}

void nova_signal_handler(int code) {
        fprintf(stdout, "Segmentation fault %d\n", code);
        fflush(stdout);
        //printStack();
        exit(1);
}

#else
void nova_print_calling_stacktrace(int count)
{
  printf("print calling stacktrace unsupported on current OS\n");
}
#endif