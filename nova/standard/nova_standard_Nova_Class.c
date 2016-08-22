#include <precompiled.h>
#include <nova/standard/nova_standard_Nova_Class.h>



nova_standard_Extension_VTable_Class nova_standard_Extension_VTable_Class_val =
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
};


CCLASS_PRIVATE
(
	int nova_standard_Nova_Class_Nova_id;
	
)
void nova_standard_Nova_Class_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_Nova_Class* nova_standard_Nova_Class_Nova_Class(nova_standard_Nova_Class* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_Nova_Class, this);
	this->vtable = &nova_standard_Extension_VTable_Class_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Class_Nova_super(this, exceptionData);
	
	{
		nova_standard_Nova_Class_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_Nova_Class_Nova_destroy(nova_standard_Nova_Class** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_Nova_Class_Nova_this(nova_standard_Nova_Class* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_Nova_Class_Nova_super(nova_standard_Nova_Class* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_Nova_Class_Nova_id = 0;
}

