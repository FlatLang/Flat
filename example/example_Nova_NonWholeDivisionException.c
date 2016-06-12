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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void example_Nova_NonWholeDivisionExceptionNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_NonWholeDivisionException* example_Nova_NonWholeDivisionException_4_Nova_construct(example_Nova_NonWholeDivisionException* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_NonWholeDivisionException_Nova_message)
{
	CCLASS_NEW(example_Nova_NonWholeDivisionException, this,);
	this->vtable = &example_Extension_VTable_NonWholeDivisionException_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_exception_Nova_Exception_Nova_super((nova_standard_exception_Nova_Exception*)this, exceptionData);
	example_Nova_NonWholeDivisionException_0_Nova_super(this, exceptionData);
	
	{
		example_Nova_NonWholeDivisionException_4_Nova_this(this, exceptionData, example_Nova_NonWholeDivisionException_Nova_message);
	}
	
	return this;
}

void example_Nova_NonWholeDivisionException_Nova_destroy(example_Nova_NonWholeDivisionException** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void example_Nova_NonWholeDivisionException_4_Nova_this(example_Nova_NonWholeDivisionException* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_NonWholeDivisionException_Nova_message)
{
}

void example_Nova_NonWholeDivisionException_0_Nova_super(example_Nova_NonWholeDivisionException* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

