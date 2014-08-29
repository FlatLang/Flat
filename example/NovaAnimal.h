#pragma once
#ifndef FILE_Animal_NOVA
#define FILE_Animal_NOVA

typedef struct Animal Animal;

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

typedef struct nova_VTable_Animal
{
	int (*nova_virtual_0_getNumLegs)(Animal*, ExceptionData*);
	int (*nova_virtual_0_getNumEyes)(Animal*, ExceptionData*);
	String* (*nova_virtual_0_getDescription)(Animal*, ExceptionData*);
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Animal;

CCLASS_CLASS
(
	Animal, 
	
	nova_VTable_Animal* vtable;
)

Animal* nova_0_Animal_construct(Animal* this, ExceptionData* exceptionData);
void nova_del_Animal(Animal** this, ExceptionData* exceptionData);
int nova_0_Animal_getNumLegs(Animal* this, ExceptionData* exceptionData);
int nova_0_Animal_getNumEyes(Animal* this, ExceptionData* exceptionData);
String* nova_0_Animal_getDescription(Animal* this, ExceptionData* exceptionData);
void nova_Animal_this(Animal* this, ExceptionData* exceptionData);
void nova_Animal_super(Animal* this, ExceptionData* exceptionData);

#endif