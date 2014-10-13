#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_NovaClientSocket.h>


nova_VTable_nova_standard_network_NovaClientSocket nova_VTable_nova_standard_network_NovaClientSocket_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	SOCKET_ID_TYPE nova_standard_network_NovaClientSocket_Novasocket;
	
)
void nova_standard_network_NovaClientSocketNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_NovaClientSocket* nova_standard_network_NovaClientSocket_Nova0_construct(nova_standard_network_NovaClientSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_network_NovaClientSocket, this);
	this->vtable = &nova_VTable_nova_standard_network_NovaClientSocket_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_network_NovaSocket_Novasuper((nova_standard_network_NovaSocket*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_network_NovaSocket_Novathis((nova_standard_network_NovaSocket*)(this), exceptionData);
	nova_standard_network_NovaClientSocket_Novasuper(this, 0);
	
	{
		nova_standard_network_NovaClientSocket_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_ClientSocket(nova_standard_network_NovaClientSocket** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	nova_del_ConnectionSocket(&(*this)->nova_standard_network_NovaClientSocket_Novaconnection, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_network_NovaClientSocket_Novathis(nova_standard_network_NovaClientSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

char nova_standard_network_NovaClientSocket_Novaconnect(nova_standard_network_NovaClientSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_NovaipAddress, int l0_Novaport)
{
	this->prv->nova_standard_network_NovaClientSocket_Novasocket = nova_clientsocket_connect((char*)(nova_standard_NovaString_NovatoCharArray(l0_NovaipAddress, exceptionData)), (int)(l0_Novaport));
	if (this->prv->nova_standard_network_NovaClientSocket_Novasocket == 0)
	{
		return 0;
	}
	this->nova_standard_network_NovaClientSocket_Novaconnection = nova_standard_network_NovaConnectionSocket_Novaconstruct(0, exceptionData, this->prv->nova_standard_network_NovaClientSocket_Novasocket);
	return 1;
}

char nova_standard_network_NovaClientSocket_Novaclose(nova_standard_network_NovaClientSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	int l1_Novaresult;
	
	l1_Novaresult = (int)(nova_socket_close(this->prv->nova_standard_network_NovaClientSocket_Novasocket));
	if (l1_Novaresult == 0)
	{
		return 0;
	}
	return 1;
}

void nova_standard_network_NovaClientSocket_Novasuper(nova_standard_network_NovaClientSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_network_NovaClientSocket_Novaconnection = (nova_standard_network_NovaConnectionSocket*)nova_null;
	this->prv->nova_standard_network_NovaClientSocket_Novasocket = (SOCKET_ID_TYPE)nova_null;
}
