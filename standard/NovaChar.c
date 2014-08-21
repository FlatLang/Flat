#include <precompiled.h>
#include "NovaChar.h"


nova_VTable_Char nova_VTable_Char_val =
{
	nova_1_Char_toString,
	nova_0_Char_toString,
	nova_static_0_Number_numDigits,
};

Char* nova_Char_construct(Char* this, ExceptionData* exceptionData, char nova_0_value)
{
	CCLASS_NEW(Char, this,);
	this->vtable = &nova_VTable_Char_val;
	nova_Object_super((Object*)this, 0);
	nova_Number_super((Number*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Number_this((Number*)(this), exceptionData);
	nova_Char_super(this, 0);
	
	{
		nova_Char_this(this, exceptionData, nova_0_value);
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

void nova_Char_this(Char* this, ExceptionData* exceptionData, char nova_0_value)
{
	this->nova_Char_value = nova_0_value;
}

String* nova_1_Char_toString(Char* this, ExceptionData* exceptionData, char nova_0_c)
{
	char* nova_1_chars;
	
	nova_1_chars = (char*)NOVA_MALLOC(sizeof(char) * (1));
	nova_1_chars[0] = nova_0_c;
	return nova_String_construct(0, exceptionData, nova_1_chars);
}

String* nova_0_Char_toString(Char* this, ExceptionData* exceptionData)
{
	return this->vtable->nova_virtual_1_toString(this, exceptionData, this->nova_Char_value);
}

void nova_Char_super(Char* this, ExceptionData* exceptionData)
{
	this->nova_Char_value = 0;
}
