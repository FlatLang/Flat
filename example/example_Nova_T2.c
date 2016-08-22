#include <precompiled.h>
#include <example/example_Nova_T2.h>



example_Extension_VTable_T2 example_Extension_VTable_T2_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	example_Nova_T2_Accessor_Nova_ind,
};


void example_Nova_T2_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_T2* example_Nova_T2_Nova_T2(example_Nova_T2* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_T2, this,);
	this->vtable = &example_Extension_VTable_T2_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_T1_Nova_super((example_Nova_T1*)this, exceptionData);
	example_Nova_T2_0_Nova_super(this, exceptionData);
	
	{
		example_Nova_T2_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_T2_Nova_destroy(example_Nova_T2** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_T2_0_Nova_this(example_Nova_T2* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

int example_Nova_T2_Accessor_Nova_ind(example_Nova_T2* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (int)2;
}

void example_Nova_T2_0_Nova_super(example_Nova_T2* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

