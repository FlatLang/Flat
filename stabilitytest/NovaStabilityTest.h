#pragma once
#ifndef FILE_StabilityTest_NOVA
#define FILE_StabilityTest_NOVA

typedef struct StabilityTest StabilityTest;

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
#include <NovaTimeStability.h>
#include <NovaFileStability.h>
#include <NovaThreadStability.h>
#include <NovaExceptionStability.h>
#include <NovaSyntaxStability.h>
#include <NovaClosureStability.h>
#include <NovaPolymorphismStability.h>
#include <NovaUnstableException.h>



StabilityTest* nova_StabilityTest_StabilityTest(ExceptionData* exceptionData);
void nova_del_StabilityTest(StabilityTest** this, ExceptionData* exceptionData);
void nova_static_StabilityTest_main(StabilityTest* this, ExceptionData* exceptionData, String** nova_0_args);
int nova_StabilityTest_runTests(StabilityTest* this, ExceptionData* exceptionData);
void nova_StabilityTest_fail(StabilityTest* this, ExceptionData* exceptionData, String* nova_0_message);

#endif