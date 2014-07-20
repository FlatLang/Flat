#pragma once
#ifndef FILE_TimeStability_NOVA
#define FILE_TimeStability_NOVA

typedef struct TimeStability TimeStability;

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
#include <NovaTime.h>
#include <NovaThread.h>
#include <NovaStabilityTest.h>



TimeStability* nova_TimeStability_TimeStability(ExceptionData* exceptionData);
void nova_del_TimeStability(TimeStability** this, ExceptionData* exceptionData);
void nova_static_TimeStability_test(TimeStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif