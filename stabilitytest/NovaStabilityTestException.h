#pragma once
#ifndef FILE_StabilityTestException_NOVA
#define FILE_StabilityTestException_NOVA

typedef struct StabilityTestException StabilityTestException;

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



StabilityTestException* nova_StabilityTestException_StabilityTestException(ExceptionData* exceptionData);
void nova_del_StabilityTestException(StabilityTestException** this, ExceptionData* exceptionData);

#endif