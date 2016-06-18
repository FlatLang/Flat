#include <precompiled.h>
#include <example/copy/example_copy_Nova_Dog.h>

example_copy_Extension_VTable_Dog example_copy_Extension_VTable_Dog_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void example_copy_Nova_DogNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_copy_Nova_Dog* example_copy_Nova_Dog_Nova_construct(example_copy_Nova_Dog* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_copy_Nova_Dog_Nova_a, int example_copy_Nova_Dog_Nova_b)
{
	CCLASS_NEW(example_copy_Nova_Dog, this,);
	this->vtable = &example_copy_Extension_VTable_Dog_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_copy_Nova_Dog_Nova_super(this, exceptionData);
	
	{
		example_copy_Nova_Dog_Nova_this(this, exceptionData, example_copy_Nova_Dog_Nova_a, example_copy_Nova_Dog_Nova_b);
	}
	
	return this;
}

void example_copy_Nova_Dog_Nova_destroy(example_copy_Nova_Dog** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_copy_Nova_Dog_Nova_this(example_copy_Nova_Dog* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_copy_Nova_Dog_Nova_a, int example_copy_Nova_Dog_Nova_b)
{
	nova_standard_Nova_String* nova_local_0;
	
	nova_local_0 = nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, example_copy_Nova_Dog_Nova_a);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "), exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, example_copy_Nova_Dog_Nova_b))));
}

void example_copy_Nova_Dog_Nova_super(example_copy_Nova_Dog* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

