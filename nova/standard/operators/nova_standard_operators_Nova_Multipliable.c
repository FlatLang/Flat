#include <precompiled.h>
#include <nova/standard/operators/nova_standard_operators_Nova_Multipliable.h>


nova_standard_operators_Extension_VTable_Multipliable nova_standard_operators_Extension_VTable_Multipliable_val =
{
	{
		(nova_standard_Nova_Object*(*)(nova_standard_operators_Nova_Multipliable*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_operators_Nova_Multipliable_1_Nova_multiply,
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
	},
	nova_standard_Nova_Object_3_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_operators_Nova_MultipliableNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_operators_Nova_Multipliable* nova_standard_operators_Nova_Multipliable_8_Nova_construct(nova_standard_operators_Nova_Multipliable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_operators_Nova_Multipliable, this,);
	this->vtable = &nova_standard_operators_Extension_VTable_Multipliable_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_operators_Nova_Multipliable_Nova_super(this, exceptionData);
	
	{
		nova_standard_operators_Nova_Multipliable_8_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_operators_Nova_Multipliable_Nova_destroy(nova_standard_operators_Nova_Multipliable** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_standard_Nova_Object* nova_standard_operators_Nova_Multipliable_1_Nova_multiply(nova_standard_operators_Nova_Multipliable* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_value){}
void nova_standard_operators_Nova_Multipliable_8_Nova_this(nova_standard_operators_Nova_Multipliable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_operators_Nova_Multipliable_Nova_super(nova_standard_operators_Nova_Multipliable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

