#include <precompiled.h>
#include <nova/standard/nova_standard_NovaInputStream.h>


nova_VTable_nova_standard_NovaInputStream nova_VTable_nova_standard_NovaInputStream_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_NovaFile* nova_standard_NovaInputStream_Novastream;
	
)

nova_standard_NovaInputStream* nova_standard_NovaInputStream_Novanull2_construct(nova_standard_NovaInputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaInputStream, this);
	this->vtable = &nova_VTable_nova_standard_NovaInputStream_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaInputStream_Novasuper(this, 0);
	
	{
		nova_standard_NovaInputStream_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_InputStream(nova_standard_NovaInputStream** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_File(&(*this)->prv->nova_standard_NovaInputStream_Novastream, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}



void nova_standard_NovaInputStream_Novathis(nova_standard_NovaInputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaInputStream_Novasuper(nova_standard_NovaInputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_NovaInputStream_Novastream = (nova_standard_NovaFile*)nova_null;
}
