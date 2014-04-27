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

void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_39)
{
fathom_IO_print(exceptionData, fathom_text_39);
fathom_IO_print(exceptionData, fathom_String_String(exceptionData, "\n"));
}

void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_71)
{
const char* fathom_cText_71;

fathom_cText_71 = fathom_String_toCharArray(fathom_text_71, exceptionData);
printf(fathom_cText_71);
}

void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_75)
{
printf("%d", fathom_j_75);
}

void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_78)
{
printf("%llu", fathom_j_78);
}

int fathom_IO_getInt(ExceptionData* exceptionData)
{
String* fathom_s_81;
char* fathom_data_81;
int fathom_num_81;

fathom_s_81 = fathom_IO_getLine(exceptionData);
fathom_data_81 = fathom_String_toCharArray(fathom_s_81, exceptionData);
fathom_num_81 = atoi(fathom_data_81);
return fathom_num_81;
}

String* fathom_IO_getLine(ExceptionData* exceptionData)
{
char* fathom_line_84;
String* fathom_s_84;

fathom_line_84 = ufgets(stdin);
fathom_s_84 = fathom_String_String(exceptionData, fathom_line_84);
return fathom_s_84;
}

void fathom_IO_waitForEnter(ExceptionData* exceptionData)
{
char* fathom_c_87;

fathom_c_87 = (char*)malloc(sizeof(char) * 2);
fgets(fathom_c_87, 2, stdin);
}
