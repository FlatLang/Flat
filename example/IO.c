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

void nova_IO_println(ExceptionData* exceptionData, String* nova_IO_text_12)
{
	nova_IO_print(exceptionData, nova_String_concat(nova_IO_text_12, exceptionData, nova_String_String(exceptionData, "\n")));
}

void nova_IO_print(ExceptionData* exceptionData, String* nova_IO_text_51)
{
	const char* nova_IO_cText_51;
	
	nova_IO_cText_51 = nova_String_toCharArray(nova_IO_text_51, exceptionData);
	fputs(nova_IO_cText_51, stdout);
}

void nova_IO_printi(ExceptionData* exceptionData, int nova_IO_j_57)
{
	printf("%d", nova_IO_j_57);
}

void nova_IO_printl(ExceptionData* exceptionData, long_long nova_IO_j_62)
{
	printf("%llu", nova_IO_j_62);
}

int nova_IO_getInt(ExceptionData* exceptionData)
{
	String* nova_IO_s_68;
	char* nova_IO_data_68;
	int nova_IO_num_68;
	
	nova_IO_s_68 = nova_IO_getLine(exceptionData);
	nova_IO_data_68 = nova_String_toCharArray(nova_IO_s_68, exceptionData);
	nova_IO_num_68 = atoi(nova_IO_data_68);
	return nova_IO_num_68;
}

char nova_IO_getChar(ExceptionData* exceptionData)
{
	char nova_IO_c_75;
	
	nova_IO_c_75 = getchar();
	nova_IO_flush(exceptionData);
	return nova_IO_c_75;
}

void nova_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* nova_IO_getLine(ExceptionData* exceptionData)
{
	char* nova_IO_line_86;
	String* nova_IO_s_86;
	
	nova_IO_line_86 = ufgets(stdin);
	nova_IO_s_86 = nova_String_String(exceptionData, nova_IO_line_86);
	return nova_IO_s_86;
}

void nova_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* nova_IO_c_91;
	
	nova_IO_flush(exceptionData);
	nova_IO_c_91 = (char*)malloc(sizeof(char) * (2));
	fgets(nova_IO_c_91, 2, stdin);
}
