#ifndef FILE_Person_NOVA
#define FILE_Person_NOVA

typedef struct Person Person;

#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"

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