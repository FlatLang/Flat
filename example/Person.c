#include "Person.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"

CCLASS_PRIVATE
(
	int nova_age;
	String* nova_name;
)

Person* nova_Person_Person(ExceptionData* exceptionData, String* nova_name_73, int nova_age_73)
{
	CCLASS_NEW(Person, this);
	
	this->prv->nova_age = 0;
	this->prv->nova_name = 0;
	{
		this->prv->nova_name = nova_name_73;
		this->prv->nova_age = nova_age_73;
	}
	
	return this;
}

void nova_del_Person(Person** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_String(&(*this)->prv->nova_name, exceptionData);
	free((*this)->prv);
	
	{
	}
	free(*this);
}

int nova_Person_getAge(Person* this, ExceptionData* exceptionData)
{
	return this->prv->nova_age;
}

String* nova_Person_getName(Person* this, ExceptionData* exceptionData)
{
	return this->prv->nova_name;
}
