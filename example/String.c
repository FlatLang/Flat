#include "String.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

PRIVATE
(
	char* fathom_data;
)

String* fathom_String_String(ExceptionData* exceptionData, char* fathom_data_38)
{
	NEW(String, reference);
	
	reference->prv->fathom_data = 0;
	{
		reference->prv->fathom_data = fathom_data_38;
	}
	
	return reference;
}

void fathom_del_String(String** reference, ExceptionData* exceptionData)
{
	if (!*reference)
	{
		return;
	}
	
	
	free((*reference)->prv);
	
	{
	}
	free(*reference);
}

char* fathom_String_toCharArray(String* reference, ExceptionData* exceptionData)
{
	return reference->prv->fathom_data;
}

void fathom_String_do1(String* reference, ExceptionData* exceptionData)
{
}

void fathom_String_do2(String* reference, ExceptionData* exceptionData)
{
}

void fathom_String_do3(String* reference, ExceptionData* exceptionData)
{
}

void fathom_String_do4(String* reference, ExceptionData* exceptionData)
{
}

void fathom_String_do5(String* reference, ExceptionData* exceptionData)
{
}

void fathom_String_do6(String* reference, ExceptionData* exceptionData)
{
}
