#include "Time.h"
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

Time* nova_Time_Time(ExceptionData* exceptionData)
{
	CCLASS_NEW(Time, this,);
	
	{
	}
	
	return this;
}

void nova_del_Time(Time** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}

long_long nova_Time_currentTimeMillis(ExceptionData* exceptionData)
{
	return currentTimeMillis();
}
