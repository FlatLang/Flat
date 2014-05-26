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

void nova_IO_println(ExceptionData* exceptionData, String* nova_IO_text_74)
{
	nova_IO_print(exceptionData, nova_String_concat(nova_IO_text_74, exceptionData, nova_String_String(exceptionData, "\n")));
}

void nova_IO_print(ExceptionData* exceptionData, String* nova_IO_text_85)
{
	const char* nova_IO_cText_85;
	
	nova_IO_cText_85 = nova_String_toCharArray(nova_IO_text_85, exceptionData);
	fputs(nova_IO_cText_85, stdout);
}

void nova_IO_printi(ExceptionData* exceptionData, int nova_IO_j_100)
{
	printf("%d", nova_IO_j_100);
}

void nova_IO_printl(ExceptionData* exceptionData, long_long nova_IO_j_106)
{
	printf("%llu", nova_IO_j_106);
}

int nova_IO_getInt(ExceptionData* exceptionData)
{
	String* nova_IO_s_111;
	int nova_IO_num_111;
	
	nova_IO_s_111 = nova_IO_getLine(exceptionData);
	nova_IO_num_111 = atoi(nova_String_toCharArray(nova_IO_s_111, exceptionData));
	return nova_IO_num_111;
}

char nova_IO_getChar(ExceptionData* exceptionData)
{
	char nova_IO_c_116;
	
	nova_IO_c_116 = getchar();
	nova_IO_flush(exceptionData);
	return nova_IO_c_116;
}

void nova_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* nova_IO_getLine(ExceptionData* exceptionData)
{
	char* nova_IO_line_125;
	String* nova_IO_s_125;
	
	nova_IO_line_125 = ufgets(stdin);
	nova_IO_s_125 = nova_String_String(exceptionData, nova_IO_line_125);
	return nova_IO_s_125;
}

void nova_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* nova_IO_c_130;
	
	nova_IO_flush(exceptionData);
	nova_IO_c_130 = (char*)malloc(sizeof(char) * (2));
	fgets(nova_IO_c_130, 2, stdin);
}
