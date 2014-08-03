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
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
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

typedef struct nova_VTable_StabilityTest
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_StabilityTest;

CCLASS_CLASS
(
	StabilityTest, 
	
	nova_VTable_StabilityTest* vtable;
)

StabilityTest* nova_StabilityTest_construct(StabilityTest* this, ExceptionData* exceptionData);
void nova_del_StabilityTest(StabilityTest** this, ExceptionData* exceptionData);
void nova_static_StabilityTest_main(StabilityTest* this, ExceptionData* exceptionData, String** nova_0_args);
int nova_StabilityTest_runTests(StabilityTest* this, ExceptionData* exceptionData);
void nova_1_StabilityTest_fail(StabilityTest* this, ExceptionData* exceptionData);
void nova_2_StabilityTest_fail(StabilityTest* this, ExceptionData* exceptionData, String* nova_0_message);

#endif