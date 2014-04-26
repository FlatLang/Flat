#include "IO.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include <windows.h>
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
CCLASS_NEW(IO, this,);

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

void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_78)
{
fathom_IO_print(exceptionData, fathom_text_78);
fathom_IO_print(exceptionData, fathom_String_String(exceptionData, "\n"));
}

void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_81)
{
const char* fathom_cText_81;

fathom_cText_81 = fathom_String_toCharArray(fathom_text_81, exceptionData);
printf(fathom_cText_81);
}

void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_84)
{
printf("%d", fathom_j_84);
}

void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_87)
{
printf("%llu", fathom_j_87);
}

int fathom_IO_getInt(ExceptionData* exceptionData)
{
String* fathom_s_90;
char* fathom_data_90;
int fathom_num_90;

fathom_s_90 = fathom_IO_getLine(exceptionData);
fathom_data_90 = fathom_String_toCharArray(fathom_s_90, exceptionData);
fathom_num_90 = atoi(fathom_data_90);
return fathom_num_90;
}

String* fathom_IO_getLine(ExceptionData* exceptionData)
{
char* fathom_line_93;
String* fathom_s_93;

fathom_line_93 = ufgets(stdin);
fathom_s_93 = fathom_String_String(exceptionData, fathom_line_93);
return fathom_s_93;
}

void fathom_IO_waitForEnter(ExceptionData* exceptionData)
{
char* fathom_c_96;

fathom_c_96 = (char*)malloc(sizeof(char) * 2);
fgets(fathom_c_96, 2, stdin);
}
