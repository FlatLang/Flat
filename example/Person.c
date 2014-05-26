#include "Person.h"
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

Person* nova_Person_Person(ExceptionData* exceptionData, String* nova_Person_name_147, int nova_Person_age_147)
{
	CCLASS_NEW(Person, this,);
	
	this->nova_Person_age = 0;
	this->nova_Person_name = 0;
	{
		this->nova_Person_name = nova_Person_name_147;
		this->nova_Person_age = nova_Person_age_147;
	}
	
	return this;
}

void nova_del_Person(Person** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_String(&(*this)->nova_Person_name, exceptionData);
	
	{
	}
	free(*this);
}

void nova_Person_sayHello(Person* this, ExceptionData* exceptionData)
{
	nova_IO_println(exceptionData, nova_String_concat(nova_String_String(exceptionData, "Hello from "), exceptionData, nova_String_concat(this->nova_Person_name, exceptionData, nova_String_String(exceptionData, " the Person"))));
}
