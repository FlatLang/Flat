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
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>



Time* nova_Time_Time(ExceptionData* exceptionData);
void nova_del_Time(Time** this, ExceptionData* exceptionData);
long_long nova_static_Time_currentTimeMillis(Time* this, ExceptionData* exceptionData);

#endif