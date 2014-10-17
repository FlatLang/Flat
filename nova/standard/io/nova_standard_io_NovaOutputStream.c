#include <precompiled.h>
#include <nova/standard/io/nova_standard_io_NovaOutputStream.h>


nova_VTable_nova_standard_io_NovaOutputStream nova_VTable_nova_standard_io_NovaOutputStream_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_io_NovaOutputStream_Nova1_write,
	nova_standard_io_NovaOutputStream_Novawrite,
};
CCLASS_PRIVATE
(
	nova_standard_io_NovaFile* nova_standard_io_NovaOutputStream_Novastream;
	
)
void nova_standard_io_NovaOutputStreamNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_io_NovaOutputStream* nova_standard_io_NovaOutputStream_Nova2_construct(nova_standard_io_NovaOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_io_NovaOutputStream, this);
	this->vtable = &nova_VTable_nova_standard_io_NovaOutputStream_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_io_NovaOutputStream_Novasuper(this, exceptionData);
	
	{
		nova_standard_io_NovaOutputStream_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_OutputStream(nova_standard_io_NovaOutputStream** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_File(&(*this)->prv->nova_standard_io_NovaOutputStream_Novastream, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

char nova_standard_io_NovaOutputStream_Nova1_write(nova_standard_io_NovaOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novadata){}
char nova_standard_io_NovaOutputStream_Novawrite(nova_standard_io_NovaOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novadata){}
void nova_standard_io_NovaOutputStream_Novathis(nova_standard_io_NovaOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_io_NovaOutputStream_Novasuper(nova_standard_io_NovaOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_io_NovaOutputStream_Novastream = (nova_standard_io_NovaFile*)nova_null;
}
