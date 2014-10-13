#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaEmptyStackException.h>


nova_VTable_nova_standard_datastruct_NovaEmptyStackException nova_VTable_nova_standard_datastruct_NovaEmptyStackException_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_datastruct_NovaEmptyStackExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_NovaEmptyStackException* nova_standard_datastruct_NovaEmptyStackException_Nova0_construct(nova_standard_datastruct_NovaEmptyStackException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_NovaEmptyStackException, this,);
	this->vtable = &nova_VTable_nova_standard_datastruct_NovaEmptyStackException_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_exception_NovaException_Novasuper((nova_standard_exception_NovaException*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaException_Novathis((nova_standard_exception_NovaException*)(this), exceptionData);
	nova_standard_datastruct_NovaEmptyStackException_Novasuper(this, 0);
	
	{
		nova_standard_datastruct_NovaEmptyStackException_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_EmptyStackException(nova_standard_datastruct_NovaEmptyStackException** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_datastruct_NovaEmptyStackException_Novathis(nova_standard_datastruct_NovaEmptyStackException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_datastruct_NovaEmptyStackException_Novasuper(nova_standard_datastruct_NovaEmptyStackException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
