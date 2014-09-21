#pragma once
#ifndef FILE_PolymorphicSubClass_NOVA
#define FILE_PolymorphicSubClass_NOVA

typedef struct PolymorphicSubClass PolymorphicSubClass;

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
#include <NovaPolymorphicSuperClass.h>

typedef struct nova_VTable_PolymorphicSubClass
{
	String* (*nova_virtual_0_toString)(PolymorphicSubClass*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_PolymorphicSubClass;

CCLASS_CLASS
(
	PolymorphicSubClass, 
	
	nova_VTable_PolymorphicSubClass* vtable;
	PolymorphicSubClass* nova_PolymorphicSuperClass_child;
)

PolymorphicSubClass* nova_0_PolymorphicSubClass_construct(PolymorphicSubClass* this, ExceptionData* exceptionData);
void nova_del_PolymorphicSubClass(PolymorphicSubClass** this, ExceptionData* exceptionData);
String* nova_0_PolymorphicSubClass_toString(PolymorphicSubClass* this, ExceptionData* exceptionData);
void nova_PolymorphicSubClass_this(PolymorphicSubClass* this, ExceptionData* exceptionData);
void nova_PolymorphicSubClass_super(PolymorphicSubClass* this, ExceptionData* exceptionData);

#endif