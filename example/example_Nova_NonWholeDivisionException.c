#include <precompiled.h>
#include <example/example_Nova_NonWholeDivisionException.h>



example_Extension_VTable_NonWholeDivisionException example_Extension_VTable_NonWholeDivisionException_val =
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
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void example_Nova_NonWholeDivisionException_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_NonWholeDivisionException* example_Nova_NonWholeDivisionException_Nova_construct(example_Nova_NonWholeDivisionException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_NonWholeDivisionException, this,);
	this->vtable = &example_Extension_VTable_NonWholeDivisionException_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_Exception_Nova_super((nova_exception_Nova_Exception*)this, exceptionData);
	example_Nova_NonWholeDivisionException_0_Nova_super(this, exceptionData);
	
	{
		example_Nova_NonWholeDivisionException_3_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_NonWholeDivisionException_Nova_destroy(example_Nova_NonWholeDivisionException** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_NonWholeDivisionException_3_Nova_this(example_Nova_NonWholeDivisionException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_NonWholeDivisionException_0_Nova_super(example_Nova_NonWholeDivisionException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

