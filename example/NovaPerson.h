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
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

CCLASS_CLASS
(
	Person, 
	
	int nova_Person_age;
	String* nova_Person_name;
)

Person* nova_Person_Person(ExceptionData* exceptionData, String* nova_0_name, int nova_0_age);
void nova_del_Person(Person** this, ExceptionData* exceptionData);
void nova_Person_sayHello(Person* this, ExceptionData* exceptionData);

#endif