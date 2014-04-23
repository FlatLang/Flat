#include "Object.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"



Object* fathom_Object_Object(ExceptionData* exceptionData)
{
	NEW(Object, reference,);
	
	{
	}
	
	return reference;
}

void fathom_del_Object(Object** reference, ExceptionData* exceptionData)
{
	if (!*reference)
	{
		return;
	}
	
	
	{
	}
	free(*reference);
}

String* fathom_Object_toString(Object* reference, ExceptionData* exceptionData)
{
	return fathom_String_String(exceptionData, "Nothing");
}
