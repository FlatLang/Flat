#include "String.h"
#include <gc.h>
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

String* nova_String_String(ExceptionData* exceptionData, char* nova_0_data)
{
	CCLASS_NEW(String, this);
	
	this->nova_String_length = 0;
	this->prv->nova_String_data = 0;
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

String* nova_String_concat(String* this, ExceptionData* exceptionData, String* nova_0_str)
{
	char* nova_93_newData;
	String* nova_93_newStr;
	
	nova_93_newData = (char*)GC_MALLOC(sizeof(char) * (nova_0_str->nova_String_length + this->nova_String_length + 1));
	strcpy(nova_93_newData, this->prv->nova_String_data);
	strcat(nova_93_newData, nova_String_toCharArray(nova_0_str, exceptionData));
	nova_93_newStr = nova_String_String(exceptionData, nova_93_newData);
	return nova_93_newStr;
}
