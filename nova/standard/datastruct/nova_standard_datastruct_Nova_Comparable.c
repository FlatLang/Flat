#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Comparable.h>


nova_VTable_nova_standard_datastruct_Nova_Comparable nova_VTable_nova_standard_datastruct_Nova_Comparable_val =
{
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
void nova_standard_datastruct_Nova_ComparableNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Comparable* nova_standard_datastruct_Nova_Comparable_2_Nova_construct(nova_standard_datastruct_Nova_Comparable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Comparable, this,);
	this->vtable = &nova_VTable_nova_standard_datastruct_Nova_Comparable_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Comparable_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Comparable_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_Comparable_Nova_destroy(nova_standard_datastruct_Nova_Comparable** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_standard_datastruct_Nova_Comparable_Nova_compareTo(nova_standard_datastruct_Nova_Comparable* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_other){}
void nova_standard_datastruct_Nova_Comparable_Nova_this(nova_standard_datastruct_Nova_Comparable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_datastruct_Nova_Comparable_Nova_super(nova_standard_datastruct_Nova_Comparable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

