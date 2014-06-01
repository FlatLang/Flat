#include "DivideByZeroException.h"
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

DivideByZeroException* nova_DivideByZeroException_DivideByZeroException(ExceptionData* exceptionData)
{
	CCLASS_NEW(DivideByZeroException, this,);
	
	{
	}
	
	return this;
}

void nova_del_DivideByZeroException(DivideByZeroException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}
