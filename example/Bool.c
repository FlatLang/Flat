#include "Bool.h"
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

Bool* nova_Bool_Bool(ExceptionData* exceptionData)
{
	CCLASS_NEW(Bool, this,);
	
	{
	}
	
	return this;
}

void nova_del_Bool(Bool** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}
