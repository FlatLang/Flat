#pragma once
#ifndef FILE_Time_NOVA
#define FILE_Time_NOVA

typedef struct Time Time;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Time
{
	String* (*nova_virtual_4_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Time;

CCLASS_CLASS
(
	Time, 
	
	nova_VTable_Time* vtable;
)

Time* nova_Time_construct(Time* this, ExceptionData* exceptionData);
void nova_del_Time(Time** this, ExceptionData* exceptionData);
long_long nova_static_Time_currentTimeMillis(Time* this, ExceptionData* exceptionData);

#endif