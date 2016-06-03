#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Enumerable.h>


nova_standard_datastruct_Extension_VTable_Enumerable nova_standard_datastruct_Extension_VTable_Enumerable_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		(nova_standard_datastruct_Nova_Enumerable*(*)(nova_standard_datastruct_Nova_Enumerable*, nova_standard_exception_Nova_ExceptionData*, l0_3_Nova_filterFunc l0_Nova_filterFunc, void* l0_ref_Nova_filterFunc))nova_standard_datastruct_Nova_Enumerable_0_Nova_filter,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_datastruct_Nova_Enumerable_0_Nova_filter,
};


void nova_standard_datastruct_Nova_EnumerableNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Enumerable* nova_standard_datastruct_Nova_Enumerable_4_Nova_construct(nova_standard_datastruct_Nova_Enumerable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Enumerable, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Enumerable_val;
	nova_standard_datastruct_Nova_Enumerable_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Enumerable_4_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_Enumerable_Nova_destroy(nova_standard_datastruct_Nova_Enumerable** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_standard_datastruct_Nova_Enumerable* nova_standard_datastruct_Nova_Enumerable_0_Nova_filter(nova_standard_datastruct_Nova_Enumerable* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_3_Nova_filterFunc l0_Nova_filterFunc, void* l0_ref_Nova_filterFunc){}
void nova_standard_datastruct_Nova_Enumerable_4_Nova_this(nova_standard_datastruct_Nova_Enumerable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_datastruct_Nova_Enumerable_Nova_super(nova_standard_datastruct_Nova_Enumerable* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

