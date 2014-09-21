#pragma once
#ifndef FILE_StabilityTest_NOVA
#define FILE_StabilityTest_NOVA

typedef struct StabilityTest StabilityTest;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaNull.h>
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
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_StabilityTest;

CCLASS_CLASS
(
	StabilityTest, 
	
	nova_VTable_StabilityTest* vtable;
)

StabilityTest* nova_0_StabilityTest_construct(StabilityTest* this, ExceptionData* exceptionData);
void nova_del_StabilityTest(StabilityTest** this, ExceptionData* exceptionData);
void nova_static_StabilityTest_main(StabilityTest* this, ExceptionData* exceptionData, String** nova_0_args);
int nova_StabilityTest_runTests(StabilityTest* this, ExceptionData* exceptionData);
void nova_0_StabilityTest_fail(StabilityTest* this, ExceptionData* exceptionData);
void nova_1_StabilityTest_fail(StabilityTest* this, ExceptionData* exceptionData, String* nova_0_message);
void nova_StabilityTest_this(StabilityTest* this, ExceptionData* exceptionData);
void nova_StabilityTest_super(StabilityTest* this, ExceptionData* exceptionData);

#endif