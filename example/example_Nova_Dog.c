#include <precompiled.h>
#include <example/example_Nova_Dog.h>



example_Extension_VTable_Dog example_Extension_VTable_Dog_val =
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
	example_Nova_Animal_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	example_Nova_Dog_Nova_getNumLegs,
	example_Nova_Dog_Nova_getNumEyes,
	example_Nova_Dog_Nova_getDescription,
};


void example_Nova_Dog_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Dog* example_Nova_Dog_Nova_Dog(example_Nova_Dog* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_Dog, this,);
	this->vtable = &example_Extension_VTable_Dog_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_Animal_Nova_super((example_Nova_Animal*)this, exceptionData);
	example_Nova_Dog_0_Nova_super(this, exceptionData);
	
	{
		example_Nova_Dog_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_Dog_Nova_destroy(example_Nova_Dog** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_Dog_0_Nova_this(example_Nova_Dog* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

int example_Nova_Dog_Nova_getNumLegs(example_Nova_Dog* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (int)4;
}

int example_Nova_Dog_Nova_getNumEyes(example_Nova_Dog* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (int)2;
}

nova_Nova_String* example_Nova_Dog_Nova_getDescription(example_Nova_Dog* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("A fuzzy dog"));
}

void example_Nova_Dog_0_Nova_super(example_Nova_Dog* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

