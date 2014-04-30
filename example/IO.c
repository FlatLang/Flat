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

void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_28)
{
	fathom_IO_print(exceptionData, fathom_text_28);
	fathom_IO_print(exceptionData, fathom_String_String(exceptionData, "\n"));
}

void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_31)
{
	const char* fathom_cText_31;
	
	fathom_cText_31 = fathom_String_toCharArray(fathom_text_31, exceptionData);
	printf(fathom_cText_31);
}

void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_36)
{
	printf("%d", fathom_j_36);
}

void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_43)
{
	printf("%llu", fathom_j_43);
}

int fathom_IO_getInt(ExceptionData* exceptionData)
{
	String* fathom_s_86;
	char* fathom_data_86;
	int fathom_num_86;
	
	fathom_s_86 = fathom_IO_getLine(exceptionData);
	fathom_data_86 = fathom_String_toCharArray(fathom_s_86, exceptionData);
	fathom_num_86 = atoi(fathom_data_86);
	return fathom_num_86;
}

String* fathom_IO_getLine(ExceptionData* exceptionData)
{
	char* fathom_line_89;
	String* fathom_s_89;
	
	fathom_line_89 = ufgets(stdin);
	fathom_s_89 = fathom_String_String(exceptionData, fathom_line_89);
	return fathom_s_89;
}

void fathom_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* fathom_c_92;
	
	fathom_c_92 = (char*)malloc(sizeof(char) * 2);
	fgets(fathom_c_92, 2, stdin);
}
