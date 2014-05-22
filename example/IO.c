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

void nova_IO_println(ExceptionData* exceptionData, String* nova_IO_text_31)
{
	nova_IO_print(exceptionData, nova_String_concat(nova_IO_text_31, exceptionData, nova_String_String(exceptionData, "\n")));
}

void nova_IO_print(ExceptionData* exceptionData, String* nova_IO_text_37)
{
	const char* nova_IO_cText_37;
	
	nova_IO_cText_37 = nova_String_toCharArray(nova_IO_text_37, exceptionData);
	fputs(nova_IO_cText_37, stdout);
}

void nova_IO_printi(ExceptionData* exceptionData, int nova_IO_j_45)
{
	printf("%d", nova_IO_j_45);
}

void nova_IO_printl(ExceptionData* exceptionData, long_long nova_IO_j_49)
{
	printf("%llu", nova_IO_j_49);
}

int nova_IO_getInt(ExceptionData* exceptionData)
{
	String* nova_IO_s_54;
	char* nova_IO_data_54;
	int nova_IO_num_54;
	
	nova_IO_s_54 = nova_IO_getLine(exceptionData);
	nova_IO_data_54 = nova_String_toCharArray(nova_IO_s_54, exceptionData);
	nova_IO_num_54 = atoi(nova_IO_data_54);
	return nova_IO_num_54;
}

char nova_IO_getChar(ExceptionData* exceptionData)
{
	char nova_IO_c_60;
	
	nova_IO_c_60 = getchar();
	nova_IO_flush(exceptionData);
	return nova_IO_c_60;
}

void nova_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* nova_IO_getLine(ExceptionData* exceptionData)
{
	char* nova_IO_line_114;
	String* nova_IO_s_114;
	
	nova_IO_line_114 = ufgets(stdin);
	nova_IO_s_114 = nova_String_String(exceptionData, nova_IO_line_114);
	return nova_IO_s_114;
}

void nova_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* nova_IO_c_117;
	
	nova_IO_flush(exceptionData);
	nova_IO_c_117 = (char*)malloc(sizeof(char) * (2));
	fgets(nova_IO_c_117, 2, stdin);
}
