#include <precompiled.h>

#include "NovaChar.h"

Char* nova_Char_Char(ExceptionData* exceptionData, char nova_0_value)
{
	CCLASS_NEW(Char, this,);
	
	this->nova_Char_value = 0;
	{
		this->nova_Char_value = nova_0_value;
	}
	
	return this;
}

void nova_del_Char(Char** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

String* nova_Char_toString(Char* this, ExceptionData* exceptionData)
{
	char* nova_1_chars;
	
	nova_1_chars = (char*)NOVA_MALLOC(sizeof(char) * (1));
	nova_1_chars[0] = this->nova_Char_value;
	return nova_String_String(exceptionData, nova_1_chars);
}
