#include <precompiled.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_CompiledList.h>

nova_standard_datastruct_list_Extension_VTable_CompiledList nova_standard_datastruct_list_Extension_VTable_CompiledList_val =
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
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_datastruct_list_Nova_CompiledListNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_list_Nova_CompiledList* nova_standard_datastruct_list_Nova_CompiledList_Nova_CompiledList(nova_standard_datastruct_list_Nova_CompiledList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_list_Nova_CompiledList, this,);
	this->vtable = &nova_standard_datastruct_list_Extension_VTable_CompiledList_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_list_Nova_CompiledList_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_list_Nova_CompiledList_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_list_Nova_CompiledList_Nova_destroy(nova_standard_datastruct_list_Nova_CompiledList** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_list_Nova_CompiledList_0_Nova_this(nova_standard_datastruct_list_Nova_CompiledList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_datastruct_list_Nova_CompiledList_Nova_super(nova_standard_datastruct_list_Nova_CompiledList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

