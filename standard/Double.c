#include "Double.h"
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


Double* nova_Double_Double(ExceptionData* exceptionData)
{
	Double* this = NULL;
	
	{
	}
	
	return this;
}

void nova_del_Double(Double** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}
