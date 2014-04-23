#include "Time.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include <Fathom.h>

Time* __static__Time;



Time* fathom_Time_Time(ExceptionData* exceptionData)
{
	NEW(Time, reference,);
	
	{
	}
	
	return reference;
}

void fathom_del_Time(Time** reference, ExceptionData* exceptionData)
{
	if (!*reference)
	{
		return;
	}
	
	
	{
	}
	free(*reference);
}

long_long fathom_Time_currentTimeMillis(Time* reference, ExceptionData* exceptionData)
{
	return currentTimeMillis();
}
