#include "IO.h"
#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
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
	const char* nova_20_cText;
	
	nova_20_cText = nova_String_toCharArray(nova_0_text, exceptionData);
	fputs(nova_20_cText, stdout);
}

void nova_IO_printi(ExceptionData* exceptionData, int nova_0_j)
{
	printf("%d", nova_0_j);
}

void nova_IO_printl(ExceptionData* exceptionData, long_long nova_0_j)
{
	printf("%lld", nova_0_j);
}

int nova_IO_getInt(ExceptionData* exceptionData)
{
	String* nova_29_s;
	int nova_29_num;
	
	nova_29_s = nova_IO_getLine(exceptionData);
	nova_29_num = atoi(nova_String_toCharArray(nova_29_s, exceptionData));
	return nova_29_num;
}

char nova_IO_getChar(ExceptionData* exceptionData)
{
	char nova_32_c;
	
	nova_32_c = getchar();
	nova_IO_flush(exceptionData);
	return nova_32_c;
}

void nova_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* nova_IO_getLine(ExceptionData* exceptionData)
{
	char* nova_38_line;
	String* nova_38_s;
	
	nova_38_line = ufgets(stdin);
	nova_38_s = nova_String_String(exceptionData, nova_38_line);
	return nova_38_s;
}

void nova_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* nova_41_c;
	
	nova_IO_flush(exceptionData);
	nova_41_c = (char*)malloc(sizeof(char) * (2));
	fgets(nova_41_c, 2, stdin);
}
