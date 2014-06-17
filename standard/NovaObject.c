#include <precompiled.h>

#include "NovaObject.h"

Object* nova_Object_Object(ExceptionData* exceptionData)
{
	Object* this = NULL;
	
	{
	}
	
	return this;
}

void nova_del_Object(Object** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

String* nova_Object_toString(Object* this, ExceptionData* exceptionData)
{
	return nova_String_String(exceptionData, "[Object text here]");
}
