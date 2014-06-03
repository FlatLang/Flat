#include "Object.h"
#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"

Object* nova_Object_Object(ExceptionData* exceptionData)
{
	CCLASS_NEW(Object, this,);
	
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
	free(*this);
}

String* nova_Object_toString(Object* this, ExceptionData* exceptionData)
{
	return nova_String_String(exceptionData, "[Object text here]");
}
