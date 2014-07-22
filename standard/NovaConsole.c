#include <precompiled.h>
#include "NovaConsole.h"




void nova_static_Console_flush(Console* this, ExceptionData* exceptionData);

Console* nova_Console_Console(ExceptionData* exceptionData)
{
	Console* this = (Console*)1;
	
	{
	}
	
	return this;
}

void nova_del_Console(Console** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_Console_writeLine(Console* this, ExceptionData* exceptionData, String* nova_0_text)
{
	nova_static_Console_write((Console*)0, exceptionData, nova_String_concat(nova_0_text, exceptionData, nova_String_String(exceptionData, (char*)("\n"))));
}

void nova_static_Console_write(Console* this, ExceptionData* exceptionData, String* nova_0_text)
{
	char* nova_1_cText;
	
	nova_1_cText = nova_String_toCharArray((String*)(nova_0_text), exceptionData);
	fputs(nova_1_cText, stdout);
}

void nova_static_Console_writei(Console* this, ExceptionData* exceptionData, int nova_0_j)
{
	printf((char*)("%d"), nova_0_j);
}

void nova_static_Console_writed(Console* this, ExceptionData* exceptionData, double nova_0_j)
{
	printf((char*)("%f"), (int)(nova_0_j));
}

void nova_static_Console_writel(Console* this, ExceptionData* exceptionData, long_long nova_0_j)
{
	printf((char*)("%lld"), (int)(nova_0_j));
}

int nova_static_Console_readInt(Console* this, ExceptionData* exceptionData)
{
	String* nova_1_s;
	
	nova_1_s = nova_static_Console_readLine((Console*)0, exceptionData);
	return atoi(nova_String_toCharArray((String*)(nova_1_s), exceptionData));
}

double nova_static_Console_readDouble(Console* this, ExceptionData* exceptionData)
{
	double nova_1_num;
	
	scanf((char*)("%lf"), &nova_1_num);
	nova_static_Console_flush((Console*)0, exceptionData);
	return nova_1_num;
}

char nova_static_Console_readChar(Console* this, ExceptionData* exceptionData)
{
	char nova_1_c;
	
	nova_1_c = getchar();
	nova_static_Console_flush((Console*)0, exceptionData);
	return nova_1_c;
}

void nova_static_Console_flush(Console* this, ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

String* nova_static_Console_readLine(Console* this, ExceptionData* exceptionData)
{
	char* nova_1_line;
	String* nova_1_s;
	
	nova_1_line = ufgets(stdin);
	nova_1_s = nova_String_String(exceptionData, nova_1_line);
	return nova_1_s;
}

void nova_static_Console_waitForEnter(Console* this, ExceptionData* exceptionData)
{
	char* nova_1_c;
	
	nova_static_Console_flush((Console*)0, exceptionData);
	nova_1_c = (char*)NOVA_MALLOC(sizeof(char) * (2));
	fgets(nova_1_c, 2, stdin);
}
