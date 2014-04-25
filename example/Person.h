#ifndef FILE_Person_FATHOM
#define FILE_Person_FATHOM

typedef struct Person Person;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"

CLASS
(
Person, 

struct Private* prv;
)

Person* fathom_Person_Person(ExceptionData* exceptionData, String* fathom_name_18, int fathom_age_18);
void fathom_del_Person(Person** this, ExceptionData* exceptionData);
int fathom_Person_getAge(Person* this, ExceptionData* exceptionData);
String* fathom_Person_getName(Person* this, ExceptionData* exceptionData);
#endif