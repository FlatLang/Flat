#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaArray.h>


nova_VTable_nova_standard_datastruct_NovaArray nova_VTable_nova_standard_datastruct_NovaArray_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};

nova_standard_datastruct_NovaArray* nova_standard_datastruct_NovaArray_Novanull0_construct(nova_standard_datastruct_NovaArray* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_NovaArray, this,);
	this->vtable = &nova_VTable_nova_standard_datastruct_NovaArray_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_datastruct_NovaArray_Novasuper(this, 0);
	
	{
		nova_standard_datastruct_NovaArray_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Array(nova_standard_datastruct_NovaArray** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_datastruct_NovaArray_Novathis(nova_standard_datastruct_NovaArray* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_datastruct_NovaArray_Novasuper(nova_standard_datastruct_NovaArray* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_datastruct_NovaArray_Novalength = 0;
}
