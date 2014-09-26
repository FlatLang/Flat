#include <precompiled.h>
#include <nova/standard/nova_standard_NovaOutputStream.h>


nova_VTable_nova_standard_NovaOutputStream nova_VTable_nova_standard_NovaOutputStream_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_NovaFile* nova_standard_NovaOutputStream_Novastream;
	
)

nova_standard_NovaOutputStream* nova_standard_NovaOutputStream_Novanull0_construct(nova_standard_NovaOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaOutputStream, this);
	this->vtable = &nova_VTable_nova_standard_NovaOutputStream_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaOutputStream_Novasuper(this, 0);
	
	{
		nova_standard_NovaOutputStream_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_OutputStream(nova_standard_NovaOutputStream** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_File(&(*this)->prv->nova_standard_NovaOutputStream_Novastream, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}



void nova_standard_NovaOutputStream_Novathis(nova_standard_NovaOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaOutputStream_Novasuper(nova_standard_NovaOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_NovaOutputStream_Novastream = (nova_standard_NovaFile*)nova_null;
}
