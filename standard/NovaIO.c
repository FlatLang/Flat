#include <precompiled.h>

#include "NovaIO.h"

void nova_IO_flush(ExceptionData* exceptionData);

IO* nova_IO_IO(ExceptionData* exceptionData)
{
	IO* this = NULL;
	
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
	NOVA_FREE(*this);
}

void nova_IO_println(ExceptionData* exceptionData, String* nova_0_text)
{
	nova_IO_print(exceptionData, nova_String_concat(nova_0_text, exceptionData, nova_String_String(exceptionData, "\n")));
}

void nova_IO_print(ExceptionData* exceptionData, String* nova_0_text)
{
	const char* nova_1_cText;
	
	nova_1_cText = nova_String_toCharArray(nova_0_text, exceptionData);
	fputs(nova_1_cText, stdout);
}

void nova_IO_printi(ExceptionData* exceptionData, int nova_0_j)
{
	printf("%d", nova_0_j);
}

void nova_IO_printd(ExceptionData* exceptionData, double nova_0_j)
{
	printf("%f", nova_0_j);
}

void nova_IO_printl(ExceptionData* exceptionData, long_long nova_0_j)
{
	printf("%lld", nova_0_j);
}

int nova_IO_getInt(ExceptionData* exceptionData)
{
	String* nova_1_s;
	int nova_1_num;
	
	nova_1_s = nova_IO_getLine(exceptionData);
	nova_1_num = atoi(nova_String_toCharArray(nova_1_s, exceptionData));
	return nova_1_num;
}

double nova_IO_getDouble(ExceptionData* exceptionData)
{
	double nova_1_num;
	
	scanf("%lf", &nova_1_num);
	nova_IO_flush(exceptionData);
	return nova_1_num;
}

char nova_IO_getChar(ExceptionData* exceptionData)
{
	char nova_1_c;
	
	nova_1_c = getchar();
	nova_IO_flush(exceptionData);
	return nova_1_c;
}

void nova_IO_flush(ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* nova_IO_getLine(ExceptionData* exceptionData)
{
	char* nova_1_line;
	String* nova_1_s;
	
	nova_1_line = ufgets(stdin);
	nova_1_s = nova_String_String(exceptionData, nova_1_line);
	return nova_1_s;
}

void nova_IO_waitForEnter(ExceptionData* exceptionData)
{
	char* nova_1_c;
	
	nova_IO_flush(exceptionData);
	nova_1_c = (char*)NOVA_MALLOC(sizeof(char) * (2));
	fgets(nova_1_c, 2, stdin);
}
