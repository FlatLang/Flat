#include "Exception.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"

Exception* fathom_Exception_Exception(ExceptionData* exceptionData)
{
	CCLASS_NEW(Exception, this,);
	
	{
	}
	
	return this;
}

void fathom_del_Exception(Exception** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}
