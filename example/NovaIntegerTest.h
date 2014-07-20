#pragma once
#ifndef FILE_IntegerTest_NOVA
#define FILE_IntegerTest_NOVA

typedef struct IntegerTest IntegerTest;

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

IntegerTest* nova_IntegerTest_IntegerTest(ExceptionData* exceptionData);
void nova_del_IntegerTest(IntegerTest** this, ExceptionData* exceptionData);
void nova_static_IntegerTest_main(IntegerTest* this, ExceptionData* exceptionData, String** nova_0_args);

#endif