#pragma once
#ifndef FILE_PolymorphicSubClass_NOVA
#define FILE_PolymorphicSubClass_NOVA

typedef struct PolymorphicSubClass PolymorphicSubClass;

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

typedef struct nova_VTable_PolymorphicSubClass
{
	String* (*nova_virtual_toString)(PolymorphicSubClass*, ExceptionData*);
} nova_VTable_PolymorphicSubClass;

CCLASS_CLASS
(
	PolymorphicSubClass, 
	
	nova_VTable_PolymorphicSubClass* vtable;
)

PolymorphicSubClass* nova_PolymorphicSubClass_PolymorphicSubClass(ExceptionData* exceptionData);
void nova_del_PolymorphicSubClass(PolymorphicSubClass** this, ExceptionData* exceptionData);
String* nova_PolymorphicSubClass_toString(PolymorphicSubClass* this, ExceptionData* exceptionData);

#endif