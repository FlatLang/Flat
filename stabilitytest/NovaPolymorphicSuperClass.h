#pragma once
#ifndef FILE_PolymorphicSuperClass_NOVA
#define FILE_PolymorphicSuperClass_NOVA

typedef struct PolymorphicSuperClass PolymorphicSuperClass;

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
#include <NovaPolymorphicSubClass.h>

typedef struct nova_VTable_PolymorphicSuperClass
{
	String* (*nova_virtual_0_toString)(PolymorphicSuperClass*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_PolymorphicSuperClass;

CCLASS_CLASS
(
	PolymorphicSuperClass, 
	
	nova_VTable_PolymorphicSuperClass* vtable;
	PolymorphicSubClass* nova_PolymorphicSuperClass_child;
)

PolymorphicSuperClass* nova_0_PolymorphicSuperClass_construct(PolymorphicSuperClass* this, ExceptionData* exceptionData);
void nova_del_PolymorphicSuperClass(PolymorphicSuperClass** this, ExceptionData* exceptionData);
void nova_PolymorphicSuperClass_giveBirth(PolymorphicSuperClass* this, ExceptionData* exceptionData);
String* nova_0_PolymorphicSuperClass_toString(PolymorphicSuperClass* this, ExceptionData* exceptionData);
void nova_PolymorphicSuperClass_this(PolymorphicSuperClass* this, ExceptionData* exceptionData);
void nova_PolymorphicSuperClass_super(PolymorphicSuperClass* this, ExceptionData* exceptionData);

#endif