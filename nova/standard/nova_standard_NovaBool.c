#include <precompiled.h>
#include <nova/standard/nova_standard_NovaBool.h>


nova_VTable_nova_standard_NovaBool nova_VTable_nova_standard_NovaBool_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};

nova_standard_NovaBool* nova_standard_NovaBool_Novanull0_construct(nova_standard_NovaBool* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaBool, this,);
	this->vtable = &nova_VTable_nova_standard_NovaBool_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaBool_Novasuper(this, 0);
	
	{
		nova_standard_NovaBool_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Bool(nova_standard_NovaBool** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaBool_Novathis(nova_standard_NovaBool* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaBool_Novasuper(nova_standard_NovaBool* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
