#include <precompiled.h>
#include "NovaPerson.h"


nova_VTable_Person nova_VTable_Person_val =
{
	nova_Person_sayHello,
};

Person* nova_Person_Person(ExceptionData* exceptionData, String* nova_0_name, int nova_0_age)
{
	CCLASS_NEW(Person, this,);
	
	this->nova_Person_age = 0;
	this->nova_Person_name = (String*)0;
	this->vtable = &nova_VTable_Person_val;
	{
		this->nova_Person_name = nova_0_name;
		this->nova_Person_age = nova_0_age;
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
	NOVA_FREE(*this);
}

void nova_Person_sayHello(Person* this, ExceptionData* exceptionData)
{
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)("Hello from ")), exceptionData, nova_String_concat(this->nova_Person_name, exceptionData, nova_String_String(exceptionData, (char*)(" the Person")))));
}
