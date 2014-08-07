#pragma once
#ifndef FILE_PolymorphismDemo_NOVA
#define FILE_PolymorphismDemo_NOVA

typedef struct PolymorphismDemo PolymorphismDemo;

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
#include <NovaAnimal.h>
#include <NovaSpider.h>
#include <NovaDog.h>

typedef struct nova_VTable_PolymorphismDemo
{
	String* (*nova_virtual_toString)(Object*, ExceptionData*);
	char (*nova_virtual_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_PolymorphismDemo;

CCLASS_CLASS
(
	PolymorphismDemo, 
	
	nova_VTable_PolymorphismDemo* vtable;
)

PolymorphismDemo* nova_PolymorphismDemo_construct(PolymorphismDemo* this, ExceptionData* exceptionData);
void nova_del_PolymorphismDemo(PolymorphismDemo** this, ExceptionData* exceptionData);
void nova_static_PolymorphismDemo_main(PolymorphismDemo* this, ExceptionData* exceptionData, String** nova_0_args);

#endif