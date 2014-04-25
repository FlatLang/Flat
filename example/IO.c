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

void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_93)
{
fathom_IO_print(exceptionData, fathom_text_93);
fathom_IO_print(exceptionData, fathom_String_String(exceptionData, "\n"));
}

void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_120)
{
const char* fathom_cText_120;

fathom_cText_120 = fathom_String_toCharArray(fathom_text_120, exceptionData);
printf(fathom_cText_120);
}

void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_127)
{
printf("%d", fathom_j_127);
}

void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_132)
{
printf("%llu", fathom_j_132);
}

int fathom_IO_getInt(ExceptionData* exceptionData)
{
String* fathom_s_138;
char* fathom_data_138;
int fathom_num_138;

fathom_s_138 = fathom_IO_getLine(exceptionData);
fathom_data_138 = fathom_String_toCharArray(fathom_s_138, exceptionData);
fathom_num_138 = atoi(fathom_data_138);
return fathom_num_138;
}

String* fathom_IO_getLine(ExceptionData* exceptionData)
{
char* fathom_line_144;
String* fathom_s_144;

fathom_line_144 = ufgets(stdin);
fathom_s_144 = fathom_String_String(exceptionData, fathom_line_144);
return fathom_s_144;
}

void fathom_IO_waitForEnter(ExceptionData* exceptionData)
{
char* fathom_c_150;

fathom_c_150 = (char*)malloc(sizeof(char) * 2);
fgets(fathom_c_150, 2, stdin);
}
