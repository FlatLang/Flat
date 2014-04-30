#include "Person.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"

CCLASS_PRIVATE
(
	int fathom_age;
	String* fathom_name;
)

Person* fathom_Person_Person(ExceptionData* exceptionData, String* fathom_name_134, int fathom_age_134)
{
	CCLASS_NEW(Person, this);
	
	this->prv->fathom_age = 0;
	this->prv->fathom_name = 0;
	{
		this->prv->fathom_name = fathom_name_134;
		this->prv->fathom_age = fathom_age_134;
	}
	
	return this;
}

void fathom_del_Person(Person** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	fathom_del_String(&(*this)->prv->fathom_name, exceptionData);
	free((*this)->prv);
	
	{
	}
	free(*this);
}

int fathom_Person_getAge(Person* this, ExceptionData* exceptionData)
{
	return this->prv->fathom_age;
}

String* fathom_Person_getName(Person* this, ExceptionData* exceptionData)
{
	return this->prv->fathom_name;
}
