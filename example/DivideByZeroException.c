#include "DivideByZeroException.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"

DivideByZeroException* fathom_DivideByZeroException_DivideByZeroException(ExceptionData* exceptionData)
{
	CCLASS_NEW(DivideByZeroException, this,);
	
	{
	}
	
	return this;
}

void fathom_del_DivideByZeroException(DivideByZeroException** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}
