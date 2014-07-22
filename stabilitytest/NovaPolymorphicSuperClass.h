#pragma once
#ifndef FILE_PolymorphicSuperClass_NOVA
#define FILE_PolymorphicSuperClass_NOVA

typedef struct PolymorphicSuperClass PolymorphicSuperClass;

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
#include <NovaPolymorphicSubClass.h>

typedef struct nova_VTable_PolymorphicSuperClass
{
	String* (*nova_virtual_toString)(PolymorphicSuperClass*, ExceptionData*);
} nova_VTable_PolymorphicSuperClass;

CCLASS_CLASS
(
	PolymorphicSuperClass, 
	
	PolymorphicSubClass* nova_PolymorphicSuperClass_child;
	nova_VTable_PolymorphicSuperClass* vtable;
)

PolymorphicSuperClass* nova_PolymorphicSuperClass_PolymorphicSuperClass(ExceptionData* exceptionData);
void nova_del_PolymorphicSuperClass(PolymorphicSuperClass** this, ExceptionData* exceptionData);
void nova_PolymorphicSuperClass_giveBirth(PolymorphicSuperClass* this, ExceptionData* exceptionData);
String* nova_PolymorphicSuperClass_toString(PolymorphicSuperClass* this, ExceptionData* exceptionData);

#endif