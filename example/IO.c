#include "IO.h"
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

void nova_IO_println(ExceptionData* exceptionData, String* nova_IO_text_134)
{
	nova_IO_print(exceptionData, nova_String_concat(nova_IO_text_134, exceptionData, nova_String_String(exceptionData, "\n")));
}

void nova_IO_print(ExceptionData* exceptionData, String* nova_IO_text_140)
{
	const char* nova_IO_cText_140;
	
	nova_IO_cText_140 = nova_String_toCharArray(nova_IO_text_140, exceptionData);
	fputs(nova_IO_cText_140, stdout);
}

void nova_IO_printi(ExceptionData* exceptionData, int nova_IO_j_148)
{
	printf("%d", nova_IO_j_148);
}

void nova_IO_printl(ExceptionData* exceptionData, long_long nova_IO_j_152)
{
	printf("%llu", nova_IO_j_152);
}

int nova_IO_getInt(ExceptionData* exceptionData)
{
	String* nova_IO_s_157;
	int nova_IO_num_157;
	
	nova_IO_s_157 = nova_IO_getLine(exceptionData);
	nova_IO_num_157 = atoi(nova_String_toCharArray(nova_IO_s_157, exceptionData));
	return nova_IO_num_157;
}

char nova_IO_getChar(ExceptionData* exceptionData)
{
	char nova_IO_c_162;
	
	nova_IO_c_162 = getchar();
	nova_IO_flush(exceptionData);
	return nova_IO_c_162;
}

void nova_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* nova_IO_getLine(ExceptionData* exceptionData)
{
	char* nova_IO_line_177;
	String* nova_IO_s_177;
	
	nova_IO_line_177 = ufgets(stdin);
	nova_IO_s_177 = nova_String_String(exceptionData, nova_IO_line_177);
	return nova_IO_s_177;
}

void nova_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* nova_IO_c_182;
	
	nova_IO_flush(exceptionData);
	nova_IO_c_182 = (char*)malloc(sizeof(char) * (2));
	fgets(nova_IO_c_182, 2, stdin);
}
