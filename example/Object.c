#include "Object.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

Object* new_Object(ExceptionData* __FATHOM__exception_data);
void del_Object(Object** __o__, ExceptionData* __FATHOM__exception_data);
static String* __FATHOM__toString(Object* __o__, ExceptionData* __FATHOM__exception_data);

NO_PRIVATE

Object* new_Object(ExceptionData* __FATHOM__exception_data)
{
	NEW(Object, __o__);
	
	__o__->toString = __FATHOM__toString;
	
	{
	}
	
	return __o__;
}

void del_Object(Object** __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!*__o__)
	{
		return;
	}
	
	
	{
	}
	free(*__o__);
}

static String* __FATHOM__toString(Object* __o__, ExceptionData* __FATHOM__exception_data)
{
	return new_String(__FATHOM__exception_data, "Nothing");
}
