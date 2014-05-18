#include "IO.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include <stdio.h>
#include <stdlib.h>
#include "String.h"
void fathom_IO_flush(ExceptionData* exceptionData);

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

void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_6)
{
	fathom_IO_print(exceptionData, fathom_String_concat(fathom_text_6, exceptionData, fathom_String_String(exceptionData, "\n")));
}

void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_11)
{
	const char* fathom_cText_11;
	
	fathom_cText_11 = fathom_String_toCharArray(fathom_text_11, exceptionData);
	fputs(fathom_cText_11, stdout);
}

void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_14)
{
	printf("%d", fathom_j_14);
}

void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_17)
{
	printf("%llu", fathom_j_17);
}

int fathom_IO_getInt(ExceptionData* exceptionData)
{
	String* fathom_s_20;
	char* fathom_data_20;
	int fathom_num_20;
	
	fathom_s_20 = fathom_IO_getLine(exceptionData);
	fathom_data_20 = fathom_String_toCharArray(fathom_s_20, exceptionData);
	fathom_num_20 = atoi(fathom_data_20);
	return fathom_num_20;
}

char fathom_IO_getChar(ExceptionData* exceptionData)
{
	char fathom_c_23;
	
	fathom_c_23 = getchar();
	fathom_IO_flush(exceptionData);
	return fathom_c_23;
}

void fathom_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* fathom_IO_getLine(ExceptionData* exceptionData)
{
	char* fathom_line_29;
	String* fathom_s_29;
	
	fathom_line_29 = ufgets(stdin);
	fathom_s_29 = fathom_String_String(exceptionData, fathom_line_29);
	return fathom_s_29;
}

void fathom_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* fathom_c_32;
	
	fathom_IO_flush(exceptionData);
	fathom_c_32 = (char*)malloc(sizeof(char) * (2));
	fgets(fathom_c_32, 2, stdin);
}
