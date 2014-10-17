#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_NovaNetworkInputStream.h>


nova_VTable_nova_standard_network_NovaNetworkInputStream nova_VTable_nova_standard_network_NovaNetworkInputStream_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_network_NovaNetworkInputStream_NovareadString,
	nova_standard_network_NovaNetworkInputStream_NovareadBytes,
};
CCLASS_PRIVATE
(
	nova_standard_io_NovaFile* nova_standard_io_NovaInputStream_Novastream;
	
	nova_standard_network_NovaConnectionSocket* nova_standard_network_NovaNetworkInputStream_Novasocket;
	
)
void nova_standard_network_NovaNetworkInputStreamNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_NovaNetworkInputStream* nova_standard_network_NovaNetworkInputStream_Nova1_construct(nova_standard_network_NovaNetworkInputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
	CCLASS_NEW(nova_standard_network_NovaNetworkInputStream, this);
	this->vtable = &nova_VTable_nova_standard_network_NovaNetworkInputStream_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_io_NovaInputStream_Novasuper((nova_standard_io_NovaInputStream*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_io_NovaInputStream_Novathis((nova_standard_io_NovaInputStream*)(this), exceptionData);
	nova_standard_network_NovaNetworkInputStream_Novasuper(this, exceptionData);
	
	{
		nova_standard_network_NovaNetworkInputStream_Novathis(this, exceptionData, l0_Novasocket);
	}
	
	return this;
}

void nova_del_NetworkInputStream(nova_standard_network_NovaNetworkInputStream** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ConnectionSocket(&(*this)->prv->nova_standard_network_NovaNetworkInputStream_Novasocket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_network_NovaNetworkInputStream_Novathis(nova_standard_network_NovaNetworkInputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
	this->prv->nova_standard_network_NovaNetworkInputStream_Novasocket = l0_Novasocket;
}

nova_standard_NovaString* nova_standard_network_NovaNetworkInputStream_NovareadString(nova_standard_network_NovaNetworkInputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_network_NovaConnectionSocket_Nova0_readString(this->prv->nova_standard_network_NovaNetworkInputStream_Novasocket, exceptionData);
}

char* nova_standard_network_NovaNetworkInputStream_NovareadBytes(nova_standard_network_NovaNetworkInputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return (char*)nova_null;
}

void nova_standard_network_NovaNetworkInputStream_Novasuper(nova_standard_network_NovaNetworkInputStream* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_network_NovaNetworkInputStream_Novasocket = (nova_standard_network_NovaConnectionSocket*)nova_null;
}
