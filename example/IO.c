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
void nova_IO_flush(ExceptionData* exceptionData);

IO* nova_IO_IO(ExceptionData* exceptionData)
{
	CCLASS_NEW(IO, this,);
	
	{
	}
	
	return this;
}

void nova_del_IO(IO** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}

void nova_IO_println(ExceptionData* exceptionData, String* nova_text_26)
{
	nova_IO_print(exceptionData, nova_String_concat(nova_text_26, exceptionData, nova_String_String(exceptionData, "\n")));
}

void nova_IO_print(ExceptionData* exceptionData, String* nova_text_29)
{
	const char* nova_cText_29;
	
	nova_cText_29 = nova_String_toCharArray(nova_text_29, exceptionData);
	fputs(nova_cText_29, stdout);
}

void nova_IO_printi(ExceptionData* exceptionData, int nova_j_32)
{
	printf("%d", nova_j_32);
}

void nova_IO_printl(ExceptionData* exceptionData, long_long nova_j_35)
{
	printf("%llu", nova_j_35);
}

int nova_IO_getInt(ExceptionData* exceptionData)
{
	String* nova_s_38;
	char* nova_data_38;
	int nova_num_38;
	
	nova_s_38 = nova_IO_getLine(exceptionData);
	nova_data_38 = nova_String_toCharArray(nova_s_38, exceptionData);
	nova_num_38 = atoi(nova_data_38);
	return nova_num_38;
}

char nova_IO_getChar(ExceptionData* exceptionData)
{
	char nova_c_41;
	
	nova_c_41 = getchar();
	nova_IO_flush(exceptionData);
	return nova_c_41;
}

void nova_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* nova_IO_getLine(ExceptionData* exceptionData)
{
	char* nova_line_47;
	String* nova_s_47;
	
	nova_line_47 = ufgets(stdin);
	nova_s_47 = nova_String_String(exceptionData, nova_line_47);
	return nova_s_47;
}

void nova_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* nova_c_50;
	
	nova_IO_flush(exceptionData);
	nova_c_50 = (char*)malloc(sizeof(char) * (2));
	fgets(nova_c_50, 2, stdin);
}
