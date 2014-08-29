#pragma once
#ifndef FILE_Person_NOVA
#define FILE_Person_NOVA

typedef struct Person Person;

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

typedef struct nova_VTable_Person
{
	void (*nova_virtual_0_sayHello)(Person*, ExceptionData*);
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Person;

CCLASS_CLASS
(
	Person, 
	
	nova_VTable_Person* vtable;
	int nova_Person_age;
	String* nova_Person_name;
)

Person* nova_Person_construct(Person* this, ExceptionData* exceptionData, String* nova_0_name, int nova_0_age);
void nova_del_Person(Person** this, ExceptionData* exceptionData);
void nova_Person_this(Person* this, ExceptionData* exceptionData, String* nova_0_name, int nova_0_age);
void nova_0_Person_sayHello(Person* this, ExceptionData* exceptionData);
void nova_Person_super(Person* this, ExceptionData* exceptionData);

#endif