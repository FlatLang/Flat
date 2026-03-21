#ifndef NOVA_EXCEPTION_HANDLING
#define NOVA_EXCEPTION_HANDLING

#include <stdio.h>
#include <Nova.h>

#ifdef _WIN32

#include <Windows.h>
#include <imagehlp.h>
#include <MacroLib.h>

void setProgramName(char* name);
char* addr2line(char const * const program_name, void const * const addr);
NOVA_CODE_CONTEXT* nova_get_context();
void nova_print_stacktrace(CONTEXT* context);
void nova_print_calling_stacktrace(int count, NOVA_CODE_CONTEXT* context);
char** nova_get_calling_stacktrace(int count, NOVA_CODE_CONTEXT* context, int* lines);
LONG WINAPI nova_exception_handler(EXCEPTION_POINTERS * ExceptionInfo);
void nova_signal_handler(int code);

#endif

#endif