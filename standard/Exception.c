#include "Exception.h"
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


Exception* nova_Exception_Exception(ExceptionData* exceptionData)
{
	Exception* this = NULL;
	
	{
	}
	
	return this;
}

void nova_del_Exception(Exception** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}
