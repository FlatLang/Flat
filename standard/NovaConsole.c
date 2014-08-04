#include <precompiled.h>
#include "NovaConsole.h"


nova_VTable_Console nova_VTable_Console_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};

void nova_static_Console_flush(Console* this, ExceptionData* exceptionData);

Console* nova_Console_construct(Console* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Console, this,);
	
	this->vtable = &nova_VTable_Console_val;
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

void nova_static_1_Console_writeLine(Console* this, ExceptionData* exceptionData, String* nova_0_text)
{
	nova_static_1_Console_write((Console*)0, exceptionData, nova_String_concat(nova_0_text, exceptionData, nova_String_construct(0, exceptionData, "\n")));
}

void nova_static_2_Console_writeLine(Console* this, ExceptionData* exceptionData, Object* nova_0_obj)
{
	nova_static_1_Console_writeLine((Console*)0, exceptionData, nova_0_obj->vtable->nova_virtual_2_toString(nova_0_obj, exceptionData));
}

void nova_static_3_Console_writeLine(Console* this, ExceptionData* exceptionData, double nova_0_num)
{
	nova_static_1_Console_writeLine((Console*)0, exceptionData, nova_static_1_Double_toString(0, exceptionData, nova_0_num));
}

void nova_static_4_Console_writeLine(Console* this, ExceptionData* exceptionData, float nova_0_num)
{
	nova_static_1_Console_writeLine((Console*)0, exceptionData, nova_static_1_Double_toString(0, exceptionData, (double)(nova_0_num)));
}

void nova_static_5_Console_writeLine(Console* this, ExceptionData* exceptionData, long_long nova_0_num)
{
	nova_static_1_Console_writeLine((Console*)0, exceptionData, nova_static_1_Long_toString(0, exceptionData, nova_0_num));
}

void nova_static_6_Console_writeLine(Console* this, ExceptionData* exceptionData, int nova_0_num)
{
	nova_static_1_Console_writeLine((Console*)0, exceptionData, nova_static_1_Int_toString(0, exceptionData, nova_0_num));
}

void nova_static_7_Console_writeLine(Console* this, ExceptionData* exceptionData, short nova_0_num)
{
	nova_static_1_Console_writeLine((Console*)0, exceptionData, nova_static_1_Int_toString(0, exceptionData, (int)(nova_0_num)));
}

void nova_static_8_Console_writeLine(Console* this, ExceptionData* exceptionData, char nova_0_num)
{
	nova_static_1_Console_writeLine((Console*)0, exceptionData, nova_static_1_Byte_toString(0, exceptionData, nova_0_num));
}

void nova_static_9_Console_writeLine(Console* this, ExceptionData* exceptionData, char nova_0_c)
{
	nova_static_1_Console_writeLine((Console*)0, exceptionData, nova_1_Char_toString(0, exceptionData, nova_0_c));
}

void nova_static_1_Console_write(Console* this, ExceptionData* exceptionData, String* nova_0_text)
{
	char* nova_1_cText;
	
	nova_1_cText = nova_String_toCharArray(nova_0_text, exceptionData);
	fputs(nova_1_cText, stdout);
}

void nova_static_2_Console_write(Console* this, ExceptionData* exceptionData, Object* nova_0_obj)
{
	nova_static_1_Console_write((Console*)0, exceptionData, nova_0_obj->vtable->nova_virtual_2_toString(nova_0_obj, exceptionData));
}

void nova_static_3_Console_write(Console* this, ExceptionData* exceptionData, double nova_0_num)
{
	nova_static_1_Console_write((Console*)0, exceptionData, nova_static_1_Double_toString(0, exceptionData, nova_0_num));
}

void nova_static_4_Console_write(Console* this, ExceptionData* exceptionData, float nova_0_num)
{
	nova_static_1_Console_write((Console*)0, exceptionData, nova_static_1_Double_toString(0, exceptionData, (double)(nova_0_num)));
}

void nova_static_5_Console_write(Console* this, ExceptionData* exceptionData, long_long nova_0_num)
{
	nova_static_1_Console_write((Console*)0, exceptionData, nova_static_1_Long_toString(0, exceptionData, nova_0_num));
}

void nova_static_6_Console_write(Console* this, ExceptionData* exceptionData, int nova_0_num)
{
	nova_static_1_Console_write((Console*)0, exceptionData, nova_static_1_Int_toString(0, exceptionData, nova_0_num));
}

void nova_static_7_Console_write(Console* this, ExceptionData* exceptionData, short nova_0_num)
{
	nova_static_1_Console_write((Console*)0, exceptionData, nova_static_1_Int_toString(0, exceptionData, (int)(nova_0_num)));
}

void nova_static_8_Console_write(Console* this, ExceptionData* exceptionData, char nova_0_num)
{
	nova_static_1_Console_write((Console*)0, exceptionData, nova_static_1_Byte_toString(0, exceptionData, nova_0_num));
}

void nova_static_9_Console_write(Console* this, ExceptionData* exceptionData, char nova_0_c)
{
	nova_static_1_Console_write((Console*)0, exceptionData, nova_1_Char_toString(0, exceptionData, nova_0_c));
}

int nova_static_Console_readInt(Console* this, ExceptionData* exceptionData)
{
	String* nova_1_s;
	
	nova_1_s = nova_static_Console_readLine((Console*)0, exceptionData);
	return atoi(nova_String_toCharArray(nova_1_s, exceptionData));
}

double nova_static_Console_readDouble(Console* this, ExceptionData* exceptionData)
{
	double nova_1_num;
	
	scanf("%lf", &nova_1_num);
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
	nova_1_s = nova_String_construct(0, exceptionData, nova_1_line);
	return nova_1_s;
}

void nova_static_Console_waitForEnter(Console* this, ExceptionData* exceptionData)
{
	char* nova_1_c;
	
	nova_static_Console_flush((Console*)0, exceptionData);
	nova_1_c = (char*)NOVA_MALLOC(sizeof(char) * (2));
	fgets(nova_1_c, 2, stdin);
}
