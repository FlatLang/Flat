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
	
	FUNC(int, getAge, Person* __o__, ExceptionData* __FATHOM__exception_data);
	FUNC(String*, getName, Person* __o__, ExceptionData* __FATHOM__exception_data);
)

Person* new_Person(ExceptionData* __FATHOM__exception_data, String* name, int age);
void del_Person(Person** __o__, ExceptionData* __FATHOM__exception_data);
#endif