#ifndef FILE_Person_FATHOM
#define FILE_Person_FATHOM

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


Person* fathom_Person_Person(ExceptionData* exceptionData, String* fathom_name_121, int fathom_age_121);
void fathom_del_Person(Person** this, ExceptionData* exceptionData);
int fathom_Person_getAge(Person* this, ExceptionData* exceptionData);
String* fathom_Person_getName(Person* this, ExceptionData* exceptionData);
#endif