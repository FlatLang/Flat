#include "String.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include <string.h>

CCLASS_PRIVATE
(
	char* nova_data;
)
void nova_String_calculateLength(String* this, ExceptionData* exceptionData);

String* nova_String_String(ExceptionData* exceptionData, char* nova_data_74)
{
	CCLASS_NEW(String, this);
	
	this->nova_length = 0;
	this->prv->nova_data = 0;
	{
		this->prv->nova_data = nova_data_74;
		nova_String_calculateLength(this, exceptionData);
	}
	
	return this;
}

void nova_del_String(String** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	free((*this)->prv);
	
	{
	}
	free(*this);
}

void nova_String_calculateLength(String* this, ExceptionData* exceptionData)
{
	this->nova_length = strlen(this->prv->nova_data);
}

int nova_String_getLength(String* this, ExceptionData* exceptionData)
{
	return this->nova_length;
}

char* nova_String_toCharArray(String* this, ExceptionData* exceptionData)
{
	return this->prv->nova_data;
}

String* nova_String_concat(String* this, ExceptionData* exceptionData, String* nova_str_103)
{
	char* nova_newData_103;
	char* nova_chars_103;
	String* nova_newStr_103;
	
	nova_newData_103 = (char*)malloc(sizeof(char) * (nova_str_103->nova_length + this->nova_length + 1));
	strcpy(nova_newData_103, this->prv->nova_data);
	nova_chars_103 = nova_String_toCharArray(nova_str_103, exceptionData);
	strcat(nova_newData_103, nova_chars_103);
	nova_newStr_103 = nova_String_String(exceptionData, nova_newData_103);
	return nova_newStr_103;
}
