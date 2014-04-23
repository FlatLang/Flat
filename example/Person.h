#ifndef FILE_Person_FATHOM
#define FILE_Person_FATHOM

typedef struct Person Person;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

CLASS
(
	Person, 
	
	, struct Private* prv;
)

Person* fathom_Person_Person(ExceptionData* exceptionData, String* fathom_name_134, int fathom_age_134);
void fathom_del_Person(Person** reference, ExceptionData* exceptionData);
int fathom_Person_getAge(Person* reference, ExceptionData* exceptionData);
String* fathom_Person_getName(Person* reference, ExceptionData* exceptionData);
#endif