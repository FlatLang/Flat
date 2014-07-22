#pragma once
#ifndef FILE_ExceptionStability_NOVA
#define FILE_ExceptionStability_NOVA

typedef struct ExceptionStability ExceptionStability;

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
#include <NovaStabilityTest.h>
#include <NovaStabilityTestException.h>



ExceptionStability* nova_ExceptionStability_ExceptionStability(ExceptionData* exceptionData);
void nova_del_ExceptionStability(ExceptionStability** this, ExceptionData* exceptionData);
void nova_static_ExceptionStability_test(ExceptionStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif