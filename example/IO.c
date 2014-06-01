#include "IO.h"
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
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

void nova_IO_println(ExceptionData* exceptionData, String* nova_0_text)
{
	nova_IO_print(exceptionData, nova_String_concat(nova_0_text, exceptionData, nova_String_String(exceptionData, "\n")));
}

void nova_IO_print(ExceptionData* exceptionData, String* nova_0_text)
{
	const char* nova_21_cText;
	
	nova_21_cText = nova_String_toCharArray(nova_0_text, exceptionData);
	fputs(nova_21_cText, stdout);
}

void nova_IO_printi(ExceptionData* exceptionData, int nova_0_j)
{
	printf("%d", nova_0_j);
}

void nova_IO_printl(ExceptionData* exceptionData, long_long nova_0_j)
{
	printf("%llu", nova_0_j);
}

int nova_IO_getInt(ExceptionData* exceptionData)
{
	String* nova_32_s;
	int nova_32_num;
	
	nova_32_s = nova_IO_getLine(exceptionData);
	nova_32_num = atoi(nova_String_toCharArray(nova_32_s, exceptionData));
	return nova_32_num;
}

char nova_IO_getChar(ExceptionData* exceptionData)
{
	char nova_38_c;
	
	nova_38_c = getchar();
	nova_IO_flush(exceptionData);
	return nova_38_c;
}

void nova_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* nova_IO_getLine(ExceptionData* exceptionData)
{
	char* nova_54_line;
	String* nova_54_s;
	
	nova_54_line = ufgets(stdin);
	nova_54_s = nova_String_String(exceptionData, nova_54_line);
	return nova_54_s;
}

void nova_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* nova_62_c;
	
	nova_IO_flush(exceptionData);
	nova_62_c = (char*)GC_MALLOC(sizeof(char) * (2));
	fgets(nova_62_c, 2, stdin);
}
