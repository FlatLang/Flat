#include "Array.h"
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


Array* nova_Array_Array(ExceptionData* exceptionData)
{
	CCLASS_NEW(Array, this,);
	
	this->nova_Array_length = 0;
	{
	}
	
	return this;
}

void nova_del_Array(Array** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}
