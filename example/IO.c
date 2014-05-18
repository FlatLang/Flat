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

void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_23)
{
	fathom_IO_print(exceptionData, fathom_String_concat(fathom_text_23, exceptionData, fathom_String_String(exceptionData, "\n")));
}

void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_30)
{
	const char* fathom_cText_30;
	
	fathom_cText_30 = fathom_String_toCharArray(fathom_text_30, exceptionData);
	fputs(fathom_cText_30, stdout);
}

void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_35)
{
	printf("%d", fathom_j_35);
}

void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_42)
{
	printf("%llu", fathom_j_42);
}

int fathom_IO_getInt(ExceptionData* exceptionData)
{
	String* fathom_s_47;
	char* fathom_data_47;
	int fathom_num_47;
	
	fathom_s_47 = fathom_IO_getLine(exceptionData);
	fathom_data_47 = fathom_String_toCharArray(fathom_s_47, exceptionData);
	fathom_num_47 = atoi(fathom_data_47);
	return fathom_num_47;
}

char fathom_IO_getChar(ExceptionData* exceptionData)
{
	char fathom_c_50;
	
	fathom_c_50 = getchar();
	fathom_IO_flush(exceptionData);
	return fathom_c_50;
}

void fathom_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* fathom_IO_getLine(ExceptionData* exceptionData)
{
	char* fathom_line_57;
	String* fathom_s_57;
	
	fathom_line_57 = ufgets(stdin);
	fathom_s_57 = fathom_String_String(exceptionData, fathom_line_57);
	return fathom_s_57;
}

void fathom_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* fathom_c_64;
	
	fathom_IO_flush(exceptionData);
	fathom_c_64 = (char*)malloc(sizeof(char) * (2));
	fgets(fathom_c_64, 2, stdin);
}
