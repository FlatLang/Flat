#include <precompiled.h>
#include <example/example_Nova_Person.h>



example_Extension_VTable_Person example_Extension_VTable_Person_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	example_Nova_Person_0_Nova_sayHello,
};


void example_Nova_Person_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Person* example_Nova_Person_Nova_Person(example_Nova_Person* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Person_Nova_name, int example_Nova_Person_Nova_age)
{
	CCLASS_NEW(example_Nova_Person, this,);
	this->vtable = &example_Extension_VTable_Person_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_Person_Nova_super(this, exceptionData);
	
	{
		example_Nova_Person_2_Nova_this(this, exceptionData, example_Nova_Person_Nova_name, example_Nova_Person_Nova_age);
	}
	
	return this;
}

void example_Nova_Person_Nova_destroy(example_Nova_Person** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_Nova_String_Nova_destroy(&(*this)->example_Nova_Person_Nova_name, exceptionData);
	
	NOVA_FREE(*this);
}

void example_Nova_Person_2_Nova_this(example_Nova_Person* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Person_Nova_name, int example_Nova_Person_Nova_age)
{
	this->example_Nova_Person_Nova_name = example_Nova_Person_Nova_name;
	this->example_Nova_Person_Nova_age = example_Nova_Person_Nova_age;
}

void example_Nova_Person_0_Nova_sayHello(example_Nova_Person* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, "Hello from "), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(this->example_Nova_Person_Nova_name), exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, " the Person"))));
}

void example_Nova_Person_Nova_super(example_Nova_Person* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->example_Nova_Person_Nova_age = 0;
	this->example_Nova_Person_Nova_name = (nova_Nova_String*)nova_null;
}

void example_Nova_Person_virtual0_Nova_sayHello(example_Nova_Person* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->vtable->example_Nova_Person_virtual0_Nova_sayHello((example_Nova_Person*)(this), exceptionData);
}

