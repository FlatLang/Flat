#pragma once
#ifndef FILE_PolymorphismStability_NOVA
#define FILE_PolymorphismStability_NOVA

typedef struct PolymorphismStability PolymorphismStability;

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
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaStabilityTest.h>
#include <NovaPolymorphicSuperClass.h>
#include <NovaPolymorphicSubClass.h>

typedef struct nova_VTable_PolymorphismStability
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_PolymorphismStability;

CCLASS_CLASS
(
	PolymorphismStability, 
	
	nova_VTable_PolymorphismStability* vtable;
)

PolymorphismStability* nova_PolymorphismStability_PolymorphismStability(PolymorphismStability* this, ExceptionData* exceptionData);
void nova_del_PolymorphismStability(PolymorphismStability** this, ExceptionData* exceptionData);
void nova_static_PolymorphismStability_test(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif