#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_NovaServerSocket.h>


nova_VTable_nova_standard_network_NovaServerSocket nova_VTable_nova_standard_network_NovaServerSocket_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	SOCKET_ID_TYPE nova_standard_network_NovaServerSocket_NovaserverSocket;
	
)
void nova_standard_network_NovaServerSocketNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_NovaServerSocket* nova_standard_network_NovaServerSocket_Nova0_construct(nova_standard_network_NovaServerSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_network_NovaServerSocket, this);
	this->vtable = &nova_VTable_nova_standard_network_NovaServerSocket_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_network_NovaSocket_Novasuper((nova_standard_network_NovaSocket*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_network_NovaSocket_Novathis((nova_standard_network_NovaSocket*)(this), exceptionData);
	nova_standard_network_NovaServerSocket_Novasuper(this, 0);
	
	{
		nova_standard_network_NovaServerSocket_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_ServerSocket(nova_standard_network_NovaServerSocket** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	nova_del_ArrayList(&(*this)->nova_standard_network_NovaServerSocket_Novarequests, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_network_NovaServerSocket_Novathis(nova_standard_network_NovaServerSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

char nova_standard_network_NovaServerSocket_Novastart(nova_standard_network_NovaServerSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaport)
{
	this->prv->nova_standard_network_NovaServerSocket_NovaserverSocket = nova_serversocket_start((int)(l0_Novaport));
	if (this->prv->nova_standard_network_NovaServerSocket_NovaserverSocket == 0)
	{
		return 0;
	}
	return 1;
}

char nova_standard_network_NovaServerSocket_Novaclose(nova_standard_network_NovaServerSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	int l1_Novaresult;
	
	l1_Novaresult = (int)(nova_socket_close(this->prv->nova_standard_network_NovaServerSocket_NovaserverSocket));
	if (l1_Novaresult == 0)
	{
		return 0;
	}
	return 1;
}

nova_standard_network_NovaConnectionSocket* nova_standard_network_NovaServerSocket_NovaacceptClient(nova_standard_network_NovaServerSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	SOCKET_ID_TYPE l1_NovaclientSocket;
	nova_standard_network_NovaConnectionSocket* l1_Novasocket;
	
	l1_NovaclientSocket = nova_serversocket_accept(this->prv->nova_standard_network_NovaServerSocket_NovaserverSocket);
	if (l1_NovaclientSocket == 0)
	{
		return (nova_standard_network_NovaConnectionSocket*)nova_null;
	}
	l1_Novasocket = nova_standard_network_NovaConnectionSocket_Novaconstruct(0, exceptionData, l1_NovaclientSocket);
	return l1_Novasocket;
}

void nova_standard_network_NovaServerSocket_Novasuper(nova_standard_network_NovaServerSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_network_NovaServerSocket_Novarequests = (nova_standard_datastruct_NovaArrayList*)nova_null;
	this->prv->nova_standard_network_NovaServerSocket_NovaserverSocket = (SOCKET_ID_TYPE)nova_null;
	this->nova_standard_network_NovaServerSocket_Novarequests = nova_standard_datastruct_NovaArrayList_Nova0_construct(0, exceptionData);
}
