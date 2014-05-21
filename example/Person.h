#ifndef FILE_Person_NOVA
#define FILE_Person_NOVA

typedef struct Person Person;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"

CCLASS_CLASS
(
	Person, 
	
	struct Private* prv;
)


Person* nova_Person_Person(ExceptionData* exceptionData, String* nova_name_73, int nova_age_73);
void nova_del_Person(Person** this, ExceptionData* exceptionData);
int nova_Person_getAge(Person* this, ExceptionData* exceptionData);
String* nova_Person_getName(Person* this, ExceptionData* exceptionData);
#endif