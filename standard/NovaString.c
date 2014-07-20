#include <precompiled.h>
#include "NovaString.h"


nova_VTable_String nova_VTable_String_val =
{
	nova_String_equals,
};
CCLASS_PRIVATE
(
	char* nova_String_data;
	
)

int nova_String_calculateLength(String* this, ExceptionData* exceptionData);

String* nova_String_String(ExceptionData* exceptionData, char* nova_0_data)
{
	CCLASS_NEW(String, this);
	
	this->nova_String_length = (Object*)0;
	this->prv->nova_String_data = (Object*)0;
	this->vtable = &nova_VTable_String_val;
	{
		this->prv->nova_String_data = nova_0_data;
		this->nova_String_length = nova_String_calculateLength(this, exceptionData);
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

int nova_String_calculateLength(String* this, ExceptionData* exceptionData)
{
	return strlen(this->prv->nova_String_data);
}

char* nova_String_toCharArray(String* this, ExceptionData* exceptionData)
{
	return this->prv->nova_String_data;
}

String* nova_String_concat(String* this, ExceptionData* exceptionData, String* nova_0_str)
{
	int nova_1_size;
	char* nova_1_newData;
	String* nova_1_newStr;
	
	nova_1_size = nova_0_str->nova_String_length + this->nova_String_length + 1;
	nova_1_newData = (char*)NOVA_MALLOC(sizeof(char) * (nova_1_size));
	strcpy(nova_1_newData, this->prv->nova_String_data);
	strcat(nova_1_newData, nova_String_toCharArray(nova_0_str, exceptionData));
	nova_1_newData[nova_1_size - 1] = '\0';
	nova_1_newStr = nova_String_String(exceptionData, nova_1_newData);
	return nova_1_newStr;
}

char nova_String_equals(String* this, ExceptionData* exceptionData, String* nova_0_other)
{
	int nova_1_i;
	
	if (this->nova_String_length != nova_0_other->nova_String_length)
	{
		return 0;
	}
	nova_1_i = 0;
	for (; nova_1_i < this->nova_String_length; nova_1_i++)
	{
		if (this->prv->nova_String_data[nova_1_i] != nova_0_other->prv->nova_String_data[nova_1_i])
		{
			return 0;
		}
	}
	return 1;
}
