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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	example_Nova_Person_0_Nova_sayHello,
};


void example_Nova_PersonNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Person* example_Nova_Person_3_Nova_construct(example_Nova_Person* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Person_Nova_name, int example_Nova_Person_Nova_age)
{
	CCLASS_NEW(example_Nova_Person, this,);
	this->vtable = &example_Extension_VTable_Person_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_Person_Nova_super(this, exceptionData);
	
	{
		example_Nova_Person_3_Nova_this(this, exceptionData, example_Nova_Person_Nova_name, example_Nova_Person_Nova_age);
	}
	
	return this;
}

void example_Nova_Person_Nova_destroy(example_Nova_Person** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_standard_Nova_String_Nova_destroy(&(*this)->example_Nova_Person_Nova_name, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void example_Nova_Person_3_Nova_this(example_Nova_Person* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Person_Nova_name, int example_Nova_Person_Nova_age)
{
	this->example_Nova_Person_Nova_name = example_Nova_Person_Nova_name;
	this->example_Nova_Person_Nova_age = example_Nova_Person_Nova_age;
}

void example_Nova_Person_0_Nova_sayHello(example_Nova_Person* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Hello from "), exceptionData, this->example_Nova_Person_Nova_name->vtable->nova_standard_Nova_String_virtual0_Nova_concat(this->example_Nova_Person_Nova_name, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, " the Person"))));
}

void example_Nova_Person_Nova_super(example_Nova_Person* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->example_Nova_Person_Nova_age = 0;
	this->example_Nova_Person_Nova_name = (nova_standard_Nova_String*)nova_null;
}

