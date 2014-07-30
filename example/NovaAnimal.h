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
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Animal
{
	int (*nova_virtual_2_getNumLegs)(Animal*, ExceptionData*);
	int (*nova_virtual_2_getNumEyes)(Animal*, ExceptionData*);
	String* (*nova_virtual_2_getDescription)(Animal*, ExceptionData*);
	String* (*nova_virtual_4_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Animal;

CCLASS_CLASS
(
	Animal, 
	
	nova_VTable_Animal* vtable;
)

Animal* nova_Animal_Animal(Animal* this, ExceptionData* exceptionData);
void nova_del_Animal(Animal** this, ExceptionData* exceptionData);
int nova_2_Animal_getNumLegs(Animal* this, ExceptionData* exceptionData);
int nova_2_Animal_getNumEyes(Animal* this, ExceptionData* exceptionData);
String* nova_2_Animal_getDescription(Animal* this, ExceptionData* exceptionData);

#endif