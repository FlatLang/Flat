#include "IO.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <stdio.h>
#include <stdlib.h>
#include "String.h"
#include <Fathom.h>

IO* __static__IO;

IO* fathom_IO_IO(ExceptionData* exceptionData)
{
NEW(IO, this,);

{
}

return this;
}

void fathom_del_IO(IO** this, ExceptionData* exceptionData)
{
if (!*this)
{
return;
}


{
}
free(*this);
}

void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_47)
{
fathom_IO_print(exceptionData, fathom_text_47);
fathom_IO_print(exceptionData, fathom_String_String(exceptionData, "\n"));
}

void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_50)
{
const char* fathom_cText_50;

fathom_cText_50 = fathom_String_toCharArray(fathom_text_50, exceptionData);
printf(fathom_cText_50);
}

void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_53)
{
printf("%d", fathom_j_53);
}

void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_56)
{
printf("%llu", fathom_j_56);
}

int fathom_IO_getInt(ExceptionData* exceptionData)
{
String* fathom_s_59;
char* fathom_data_59;
int fathom_num_59;

fathom_s_59 = fathom_IO_getLine(exceptionData);
fathom_data_59 = fathom_String_toCharArray(fathom_s_59, exceptionData);
fathom_num_59 = atoi(fathom_data_59);
return fathom_num_59;
}

String* fathom_IO_getLine(ExceptionData* exceptionData)
{
char* fathom_line_62;
String* fathom_s_62;

fathom_line_62 = ufgets(stdin);
fathom_s_62 = fathom_String_String(exceptionData, fathom_line_62);
return fathom_s_62;
}

void fathom_IO_waitForEnter(ExceptionData* exceptionData)
{
char* fathom_c_65;

fathom_c_65 = (char*)malloc(sizeof(char) * 2);
fgets(fathom_c_65, 2, stdin);
}
