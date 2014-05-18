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
	char* fathom_data;
)
void fathom_String_calculateLength(String* this, ExceptionData* exceptionData);

String* fathom_String_String(ExceptionData* exceptionData, char* fathom_data_79)
{
	CCLASS_NEW(String, this);
	
	this->fathom_length = 0;
	this->prv->fathom_data = 0;
	{
		this->prv->fathom_data = fathom_data_79;
		fathom_String_calculateLength(this, exceptionData);
	}
	
	return this;
}

void fathom_del_String(String** this, ExceptionData* exceptionData)
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

void fathom_String_calculateLength(String* this, ExceptionData* exceptionData)
{
	this->fathom_length = strlen(this->prv->fathom_data);
}

int fathom_String_getLength(String* this, ExceptionData* exceptionData)
{
	return this->fathom_length;
}

char* fathom_String_toCharArray(String* this, ExceptionData* exceptionData)
{
	return this->prv->fathom_data;
}

String* fathom_String_concat(String* this, ExceptionData* exceptionData, String* fathom_str_100)
{
	char* fathom_newData_100;
	char* fathom_chars_100;
	String* fathom_newStr_100;
	
	fathom_newData_100 = (char*)malloc(sizeof(char) * (fathom_String_getLength(fathom_str_100, exceptionData) + this->fathom_length + 1));
	strcpy(fathom_newData_100, this->prv->fathom_data);
	fathom_chars_100 = fathom_String_toCharArray(fathom_str_100, exceptionData);
	strcat(fathom_newData_100, fathom_chars_100);
	fathom_newStr_100 = fathom_String_String(exceptionData, fathom_newData_100);
	return fathom_newStr_100;
}
