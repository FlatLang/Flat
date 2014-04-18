#include "Person.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "String.h"

Person* new_Person(ExceptionData* __FATHOM__exception_data, String* name, int age);
void del_Person(Person** __o__, ExceptionData* __FATHOM__exception_data);
static int __FATHOM__getAge(Person* __o__, ExceptionData* __FATHOM__exception_data);
static String* __FATHOM__getName(Person* __o__, ExceptionData* __FATHOM__exception_data);

PRIVATE
(
	int age;
	String* name;
)

Person* new_Person(ExceptionData* __FATHOM__exception_data, String* name, int age)
{
	NEW(Person, __o__);
	
	__o__->getAge = __FATHOM__getAge;
	__o__->getName = __FATHOM__getName;
	
	__o__->prv->age = 0;
	__o__->prv->name = 0;
	{
		__o__->prv->name = name;
		__o__->prv->age = age;
	}
	
	return __o__;
}

void del_Person(Person** __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!*__o__)
	{
		return;
	}
	
	del_String(&(*__o__)->prv->name, __FATHOM__exception_data);
	free((*__o__)->prv);
	
	{
	}
	free(*__o__);
}

static int __FATHOM__getAge(Person* __o__, ExceptionData* __FATHOM__exception_data)
{
	return __o__->prv->age;
}

static String* __FATHOM__getName(Person* __o__, ExceptionData* __FATHOM__exception_data)
{
	return __o__->prv->name;
}
