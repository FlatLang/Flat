#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_NovaNetworkOutputStream.h>


nova_VTable_nova_standard_network_NovaNetworkOutputStream nova_VTable_nova_standard_network_NovaNetworkOutputStream_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_network_NovaNetworkOutputStream_Novawrite,
	nova_standard_network_NovaNetworkOutputStream_Nova0_write,
};
CCLASS_PRIVATE
(
	nova_standard_io_NovaFile* nova_standard_io_NovaOutputStream_Novastream;
	
	nova_standard_network_NovaConnectionSocket* nova_standard_network_NovaNetworkOutputStream_Novasocket;
	
)
void nova_standard_network_NovaNetworkOutputStreamNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_NovaNetworkOutputStream* nova_standard_network_NovaNetworkOutputStream_Nova1_construct(nova_standard_network_NovaNetworkOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
	CCLASS_NEW(nova_standard_network_NovaNetworkOutputStream, this);
	this->vtable = &nova_VTable_nova_standard_network_NovaNetworkOutputStream_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_io_NovaOutputStream_Novasuper((nova_standard_io_NovaOutputStream*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_io_NovaOutputStream_Novathis((nova_standard_io_NovaOutputStream*)(this), exceptionData);
	nova_standard_network_NovaNetworkOutputStream_Novasuper(this, exceptionData);
	
	{
		nova_standard_network_NovaNetworkOutputStream_Novathis(this, exceptionData, l0_Novasocket);
	}
	
	return this;
}

void nova_del_NetworkOutputStream(nova_standard_network_NovaNetworkOutputStream** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ConnectionSocket(&(*this)->prv->nova_standard_network_NovaNetworkOutputStream_Novasocket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_network_NovaNetworkOutputStream_Novathis(nova_standard_network_NovaNetworkOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
	this->prv->nova_standard_network_NovaNetworkOutputStream_Novasocket = l0_Novasocket;
}

char nova_standard_network_NovaNetworkOutputStream_Novawrite(nova_standard_network_NovaNetworkOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novadata)
{
	return nova_standard_network_NovaConnectionSocket_Novawrite(this->prv->nova_standard_network_NovaNetworkOutputStream_Novasocket, exceptionData, l0_Novadata);
}

char nova_standard_network_NovaNetworkOutputStream_Nova0_write(nova_standard_network_NovaNetworkOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novadata)
{
	return 0;
}

void nova_standard_network_NovaNetworkOutputStream_Novasuper(nova_standard_network_NovaNetworkOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_network_NovaNetworkOutputStream_Novasocket = (nova_standard_network_NovaConnectionSocket*)nova_null;
}
