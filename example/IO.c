#include "IO.h"
#include <stdlib.h>
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

void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_29)
{
	fathom_IO_print(exceptionData, fathom_text_29);
	fathom_IO_print(exceptionData, fathom_String_String(exceptionData, "\n"));
}

void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_32)
{
	const char* fathom_cText_32;
	
	fathom_cText_32 = fathom_String_toCharArray(fathom_text_32, exceptionData);
	printf(fathom_cText_32);
}

void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_35)
{
	printf("%d", fathom_j_35);
}

void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_38)
{
	printf("%llu", fathom_j_38);
}

int fathom_IO_getInt(ExceptionData* exceptionData)
{
	String* fathom_s_41;
	char* fathom_data_41;
	int fathom_num_41;
	
	fathom_s_41 = fathom_IO_getLine(exceptionData);
	fathom_data_41 = fathom_String_toCharArray(fathom_s_41, exceptionData);
	fathom_num_41 = atoi(fathom_data_41);
	return fathom_num_41;
}

String* fathom_IO_getLine(ExceptionData* exceptionData)
{
	char* fathom_line_44;
	String* fathom_s_44;
	
	fathom_line_44 = ufgets(stdin);
	fathom_s_44 = fathom_String_String(exceptionData, fathom_line_44);
	return fathom_s_44;
}

void fathom_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* fathom_c_47;
	
	fathom_c_47 = (char*)malloc(sizeof(char) * 2);
	fgets(fathom_c_47, 2, stdin);
}
