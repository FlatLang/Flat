#pragma once
#ifndef FILE_ClosureStability_NOVA
#define FILE_ClosureStability_NOVA

typedef struct ClosureStability ClosureStability;

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
#include <NovaStabilityTest.h>

typedef struct nova_VTable_ClosureStability
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_ClosureStability;

CCLASS_CLASS
(
	ClosureStability, 
	
	nova_VTable_ClosureStability* vtable;
	struct Private* prv;
)

ClosureStability* nova_ClosureStability_construct(ClosureStability* this, ExceptionData* exceptionData);
void nova_del_ClosureStability(ClosureStability** this, ExceptionData* exceptionData);
void nova_static_ClosureStability_test(ClosureStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif