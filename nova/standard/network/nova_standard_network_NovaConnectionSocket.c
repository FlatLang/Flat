#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_NovaConnectionSocket.h>


nova_VTable_nova_standard_network_NovaConnectionSocket nova_VTable_nova_standard_network_NovaConnectionSocket_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_datastruct_NovaQueue* nova_standard_network_NovaConnectionSocket_NovainputBuffer;
	SOCKET_ID_TYPE nova_standard_network_NovaConnectionSocket_Novasocket;
	
)

nova_standard_NovaString* nova_standard_network_NovaConnectionSocket_Nova1_readString(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_NovacheckBuffer);
void nova_standard_network_NovaConnectionSocketNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_NovaConnectionSocket* nova_standard_network_NovaConnectionSocket_Novaconstruct(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, SOCKET_ID_TYPE l0_Novasocket)
{
	CCLASS_NEW(nova_standard_network_NovaConnectionSocket, this);
	this->vtable = &nova_VTable_nova_standard_network_NovaConnectionSocket_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_network_NovaSocket_Novasuper((nova_standard_network_NovaSocket*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_network_NovaSocket_Novathis((nova_standard_network_NovaSocket*)(this), exceptionData);
	nova_standard_network_NovaConnectionSocket_Novasuper(this, exceptionData);
	
	{
		nova_standard_network_NovaConnectionSocket_Novathis(this, exceptionData, l0_Novasocket);
	}
	
	return this;
}

void nova_del_ConnectionSocket(nova_standard_network_NovaConnectionSocket** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_Queue(&(*this)->prv->nova_standard_network_NovaConnectionSocket_NovainputBuffer, exceptionData);
	
	NOVA_FREE((*this)->prv);
	
	nova_del_InputStream(&(*this)->nova_standard_network_NovaConnectionSocket_Novain, exceptionData);
	nova_del_OutputStream(&(*this)->nova_standard_network_NovaConnectionSocket_Novaout, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_network_NovaConnectionSocket_Novathis(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, SOCKET_ID_TYPE l0_Novasocket)
{
	this->prv->nova_standard_network_NovaConnectionSocket_Novasocket = l0_Novasocket;
	this->nova_standard_network_NovaConnectionSocket_Novain = (nova_standard_io_NovaInputStream*)(nova_standard_network_NovaNetworkInputStream_Nova1_construct(0, exceptionData, this));
	this->nova_standard_network_NovaConnectionSocket_Novaout = (nova_standard_io_NovaOutputStream*)(nova_standard_network_NovaNetworkOutputStream_Nova1_construct(0, exceptionData, this));
	this->nova_standard_network_NovaConnectionSocket_Novaconnected = 1;
}

void nova_standard_network_NovaConnectionSocket_Novaclose(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_network_NovaConnectionSocket_Novaconnected = 0;
	nova_socket_close(this->prv->nova_standard_network_NovaConnectionSocket_Novasocket);
}

char nova_standard_network_NovaConnectionSocket_NovavalidateConnection(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaString* l1_Novamessage;
	
	if (!this->nova_standard_network_NovaConnectionSocket_Novaconnected)
	{
		return this->nova_standard_network_NovaConnectionSocket_Novaconnected;
	}
	l1_Novamessage = nova_standard_network_NovaConnectionSocket_Nova1_readString(this, exceptionData, 0);
	if (this->nova_standard_network_NovaConnectionSocket_Novaconnected)
	{
		nova_standard_datastruct_NovaQueue_Novaenqueue(this->prv->nova_standard_network_NovaConnectionSocket_NovainputBuffer, exceptionData, (nova_standard_NovaObject*)(l1_Novamessage));
	}
	return this->nova_standard_network_NovaConnectionSocket_Novaconnected;
}

nova_standard_NovaString* nova_standard_network_NovaConnectionSocket_Nova0_readString(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_network_NovaConnectionSocket_Nova1_readString(this, exceptionData, 1);
}

nova_standard_NovaString* nova_standard_network_NovaConnectionSocket_Nova1_readString(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_NovacheckBuffer)
{
	if (!l0_NovacheckBuffer || nova_standard_datastruct_NovaQueue_NovaisEmpty(this->prv->nova_standard_network_NovaConnectionSocket_NovainputBuffer, exceptionData))
	{
		char* l2_Novadata;
		
		l2_Novadata = (char*)(nova_socket_receive(this->prv->nova_standard_network_NovaConnectionSocket_Novasocket));
		if (l2_Novadata == 0)
		{
			this->nova_standard_network_NovaConnectionSocket_Novaconnected = 0;
			return (nova_standard_NovaString*)nova_null;
		}
		return nova_standard_NovaString_Nova1_construct(0, exceptionData, l2_Novadata);
	}
	return ((nova_standard_NovaString*)nova_standard_datastruct_NovaQueue_Novadequeue(this->prv->nova_standard_network_NovaConnectionSocket_NovainputBuffer, exceptionData));
}

char nova_standard_network_NovaConnectionSocket_Novawrite(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novadata)
{
	char l1_Novasuccess;
	
	l1_Novasuccess = nova_socket_send(this->prv->nova_standard_network_NovaConnectionSocket_Novasocket, (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novadata, exceptionData))) == 1;
	return l1_Novasuccess;
}

void nova_standard_network_NovaConnectionSocket_Novasuper(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_network_NovaConnectionSocket_Novaconnected = 0;
	this->nova_standard_network_NovaConnectionSocket_Novain = (nova_standard_io_NovaInputStream*)nova_null;
	this->nova_standard_network_NovaConnectionSocket_Novaout = (nova_standard_io_NovaOutputStream*)nova_null;
	this->prv->nova_standard_network_NovaConnectionSocket_NovainputBuffer = (nova_standard_datastruct_NovaQueue*)nova_null;
	this->prv->nova_standard_network_NovaConnectionSocket_Novasocket = 0;
	this->prv->nova_standard_network_NovaConnectionSocket_NovainputBuffer = nova_standard_datastruct_NovaQueue_Nova0_construct(0, exceptionData);
}
