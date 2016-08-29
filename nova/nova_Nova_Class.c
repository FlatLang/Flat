#include <precompiled.h>
#include <nova/nova_Nova_Class.h>



nova_Extension_VTable_Class nova_Extension_VTable_Class_val =
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
};


CCLASS_PRIVATE
(
	int nova_Nova_Class_Nova_id;
	
)
void nova_Nova_Class_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_Nova_Class* nova_Nova_Class_Nova_construct(nova_Nova_Class* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_Nova_Class, this);
	this->vtable = &nova_Extension_VTable_Class_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_Nova_Class_Nova_super(this, exceptionData);
	
	{
		nova_Nova_Class_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_Nova_Class_Nova_destroy(nova_Nova_Class** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_Nova_Class_Nova_this(nova_Nova_Class* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_Nova_Class_Nova_super(nova_Nova_Class* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_Nova_Class_Nova_id = 0;
}

