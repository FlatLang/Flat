#include <precompiled.h>
#include "NovaString.h"


nova_VTable_String nova_VTable_String_val =
{
	nova_0_String_toString,
	nova_0_String_equals,
	nova_0_String_concat,
};
CCLASS_PRIVATE
(
	char* nova_String_data;
	
)

int nova_String_calculateLength(String* this, ExceptionData* exceptionData);

String* nova_String_construct(String* this, ExceptionData* exceptionData, char* nova_0_data)
{
	CCLASS_NEW(String, this);
	this->vtable = &nova_VTable_String_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_String_super(this, 0);
	
	{
		nova_String_this(this, exceptionData, nova_0_data);
	}
	
	return this;
}

void nova_del_String(String** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_String_this(String* this, ExceptionData* exceptionData, char* nova_0_data)
{
	this->prv->nova_String_data = nova_0_data;
	this->nova_String_length = nova_String_calculateLength(this, exceptionData);
}

int nova_String_calculateLength(String* this, ExceptionData* exceptionData)
{
	return strlen(this->prv->nova_String_data);
}

char* nova_String_toCharArray(String* this, ExceptionData* exceptionData)
{
	return this->prv->nova_String_data;
}

String* nova_0_String_concat(String* this, ExceptionData* exceptionData, String* nova_0_str)
{
	int nova_1_size;
	char* nova_1_newData;
	String* nova_1_newStr;
	
	nova_0_str = nova_0_str->vtable->nova_virtual_0_toString(nova_0_str, exceptionData);
	nova_1_size = (int)(nova_0_str->nova_String_length + this->nova_String_length + 1);
	nova_1_newData = (char*)NOVA_MALLOC(sizeof(char) * (nova_1_size));
	strcpy(nova_1_newData, this->prv->nova_String_data);
	strcat(nova_1_newData, nova_String_toCharArray(nova_0_str, exceptionData));
	nova_1_newData[nova_1_size - 1] = '\0';
	nova_1_newStr = nova_String_construct(0, exceptionData, nova_1_newData);
	return nova_1_newStr;
}

char nova_0_String_equals(String* this, ExceptionData* exceptionData, String* nova_0_other)
{
	int nova_3_i;
	
	if (this->nova_String_length != nova_0_other->nova_String_length)
	{
		return 0;
	}
	nova_3_i = 0;
	for (; nova_3_i < this->nova_String_length; nova_3_i++)
	{
		if (this->prv->nova_String_data[nova_3_i] != nova_0_other->prv->nova_String_data[nova_3_i])
		{
			return 0;
		}
	}
	return 1;
}

String* nova_0_String_toString(String* this, ExceptionData* exceptionData)
{
	return this;
}

void nova_String_super(String* this, ExceptionData* exceptionData)
{
	this->nova_String_length = 0;
	this->prv->nova_String_data = 0;
}
