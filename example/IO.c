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

void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_13)
{
	fathom_IO_print(exceptionData, fathom_String_concat(fathom_text_13, exceptionData, fathom_String_String(exceptionData, "\n")));
}

void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_42)
{
	const char* fathom_cText_42;
	
	fathom_cText_42 = fathom_String_toCharArray(fathom_text_42, exceptionData);
	fputs(fathom_cText_42, stdout);
}

void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_48)
{
	printf("%d", fathom_j_48);
}

void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_52)
{
	printf("%llu", fathom_j_52);
}

int fathom_IO_getInt(ExceptionData* exceptionData)
{
	String* fathom_s_58;
	char* fathom_data_58;
	int fathom_num_58;
	
	fathom_s_58 = fathom_IO_getLine(exceptionData);
	fathom_data_58 = fathom_String_toCharArray(fathom_s_58, exceptionData);
	fathom_num_58 = atoi(fathom_data_58);
	return fathom_num_58;
}

char fathom_IO_getChar(ExceptionData* exceptionData)
{
	char fathom_c_64;
	
	fathom_c_64 = getchar();
	fathom_IO_flush(exceptionData);
	return fathom_c_64;
}

void fathom_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* fathom_IO_getLine(ExceptionData* exceptionData)
{
	char* fathom_line_73;
	String* fathom_s_73;
	
	fathom_line_73 = ufgets(stdin);
	fathom_s_73 = fathom_String_String(exceptionData, fathom_line_73);
	return fathom_s_73;
}

void fathom_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* fathom_c_78;
	
	fathom_IO_flush(exceptionData);
	fathom_c_78 = (char*)malloc(sizeof(char) * (2));
	fgets(fathom_c_78, 2, stdin);
}
