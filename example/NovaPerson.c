#include <precompiled.h>
#include "NovaPerson.h"


nova_VTable_Person nova_VTable_Person_val =
{
	nova_0_Person_sayHello,
	nova_0_Object_toString,
	nova_0_Object_equals,
};

Person* nova_Person_construct(Person* this, ExceptionData* exceptionData, String* nova_0_name, int nova_0_age)
{
	CCLASS_NEW(Person, this,);
	this->vtable = &nova_VTable_Person_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Person_super(this, 0);
	
	{
		nova_Person_this(this, exceptionData, nova_0_name, nova_0_age);
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

void nova_Person_this(Person* this, ExceptionData* exceptionData, String* nova_0_name, int nova_0_age)
{
	this->nova_Person_name = nova_0_name;
	this->nova_Person_age = nova_0_age;
}

void nova_0_Person_sayHello(Person* this, ExceptionData* exceptionData)
{
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "Hello from "), exceptionData, nova_String_concat(this->nova_Person_name, exceptionData, nova_String_construct(0, exceptionData, " the Person"))));
}

void nova_Person_super(Person* this, ExceptionData* exceptionData)
{
	this->nova_Person_age = 0;
	this->nova_Person_name = (String*)0;
}
