#include "String.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"
#include <string.h>

CCLASS_PRIVATE
(
	char* nova_String_data;
	
)
int nova_String_calculateLength(String* this, ExceptionData* exceptionData);

String* nova_String_String(ExceptionData* exceptionData, char* nova_String_data_113)
{
	CCLASS_NEW(String, this);
	
	this->nova_String_length = 0;
	this->prv->nova_String_data = 0;
	{
		this->prv->nova_String_data = nova_String_data_113;
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
	
	
	free((*this)->prv);
	
	{
	}
	free(*this);
}

int nova_String_calculateLength(String* this, ExceptionData* exceptionData)
{
	strlen(this->prv->nova_String_data);
}

char* nova_String_toCharArray(String* this, ExceptionData* exceptionData)
{
	return this->prv->nova_String_data;
}

String* nova_String_concat(String* this, ExceptionData* exceptionData, String* nova_String_str_141)
{
	char* nova_String_newData_141;
	String* nova_String_newStr_141;
	
	nova_String_newData_141 = (char*)malloc(sizeof(char) * (nova_String_str_141->nova_String_length + this->nova_String_length + 1));
	strcpy(nova_String_newData_141, this->prv->nova_String_data);
	strcat(nova_String_newData_141, nova_String_toCharArray(nova_String_str_141, exceptionData));
	nova_String_newStr_141 = nova_String_String(exceptionData, nova_String_newData_141);
	return nova_String_newStr_141;
}
