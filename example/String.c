#include "String.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

String* new_String(ExceptionData* __FATHOM__exception_data, char* d);
void del_String(String** __o__, ExceptionData* __FATHOM__exception_data);
static char* __FATHOM__toCharArray(String* __o__, ExceptionData* __FATHOM__exception_data);

PRIVATE
(
	char* data;
)

String* new_String(ExceptionData* __FATHOM__exception_data, char* d)
{
	NEW(String, __o__);
	
	__o__->toCharArray = __FATHOM__toCharArray;
	
	__o__->prv->data = 0;
	{
		__o__->prv->data = d;
	}
	
	return __o__;
}

void del_String(String** __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!*__o__)
	{
		return;
	}
	
	
	free((*__o__)->prv);
	
	{
	}
	free(*__o__);
}

static char* __FATHOM__toCharArray(String* __o__, ExceptionData* __FATHOM__exception_data)
{
	return __o__->prv->data;
}
