#pragma once
#ifndef FILE_PolymorphismStability_NOVA
#define FILE_PolymorphismStability_NOVA

typedef struct PolymorphismStability PolymorphismStability;

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
#include <NovaStabilityTest.h>
#include <NovaPolymorphicSuperClass.h>
#include <NovaPolymorphicSubClass.h>

typedef struct nova_VTable_PolymorphismStability
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_PolymorphismStability;

CCLASS_CLASS
(
	PolymorphismStability, 
	
	nova_VTable_PolymorphismStability* vtable;
)

PolymorphismStability* nova_0_PolymorphismStability_construct(PolymorphismStability* this, ExceptionData* exceptionData);
void nova_del_PolymorphismStability(PolymorphismStability** this, ExceptionData* exceptionData);
void nova_static_PolymorphismStability_test(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_PolymorphismStability_this(PolymorphismStability* this, ExceptionData* exceptionData);
void nova_PolymorphismStability_super(PolymorphismStability* this, ExceptionData* exceptionData);

#endif