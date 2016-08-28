#include <precompiled.h>
#include <example/example_Nova_T1.h>



example_Extension_VTable_T1 example_Extension_VTable_T1_val =
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
	example_Nova_T1_Accessor_Nova_ind,
};


void example_Nova_T1_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_T1* example_Nova_T1_Nova_T1(example_Nova_T1* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_T1, this,);
	this->vtable = &example_Extension_VTable_T1_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_T1_Nova_super(this, exceptionData);
	
	{
		example_Nova_T1_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_T1_Nova_destroy(example_Nova_T1** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_T1_0_Nova_this(example_Nova_T1* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

int example_Nova_T1_Accessor_Nova_ind(example_Nova_T1* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (int)1;
}

void example_Nova_T1_Nova_super(example_Nova_T1* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

int example_Nova_T1_virtual_Accessor_Nova_ind(example_Nova_T1* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->example_Nova_T1_virtual_Accessor_Nova_ind(0, exceptionData);
}

