#include <precompiled.h>
#include <nova/network/nova_network_Nova_ServerSocket.h>



nova_network_Extension_VTable_ServerSocket nova_network_Extension_VTable_ServerSocket_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


CCLASS_PRIVATE
(
	SOCKET_ID_TYPE nova_network_Nova_ServerSocket_Nova_serverSocket;
	
)
void nova_network_Nova_ServerSocket_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_network_Nova_ServerSocket* nova_network_Nova_ServerSocket_Nova_construct(nova_network_Nova_ServerSocket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_network_Nova_ServerSocket, this);
	this->vtable = &nova_network_Extension_VTable_ServerSocket_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_network_Nova_Socket_Nova_super((nova_network_Nova_Socket*)this, exceptionData);
	nova_network_Nova_ServerSocket_0_Nova_super(this, exceptionData);
	
	{
		nova_network_Nova_ServerSocket_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_network_Nova_ServerSocket_Nova_destroy(nova_network_Nova_ServerSocket** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	nova_datastruct_list_Nova_Array_Nova_destroy(&(*this)->nova_network_Nova_ServerSocket_Nova_requests, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_network_Nova_ServerSocket_0_Nova_this(nova_network_Nova_ServerSocket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_network_Nova_ServerSocket_Nova_open = 0;
}

char nova_network_Nova_ServerSocket_Nova_start(nova_network_Nova_ServerSocket* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_network_Nova_ServerSocket_Nova_port)
{
	this->prv->nova_network_Nova_ServerSocket_Nova_serverSocket = nova_serversocket_start(nova_network_Nova_ServerSocket_Nova_port);
	if (this->prv->nova_network_Nova_ServerSocket_Nova_serverSocket == 0)
	{
		return 0;
	}
	this->nova_network_Nova_ServerSocket_Nova_open = 1;
	return 1;
}

char nova_network_Nova_ServerSocket_Nova_close(nova_network_Nova_ServerSocket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	int l1_Nova_result = 0;
	
	l1_Nova_result = (int)(nova_socket_close(this->prv->nova_network_Nova_ServerSocket_Nova_serverSocket));
	if (l1_Nova_result == 0)
	{
		return 0;
	}
	this->nova_network_Nova_ServerSocket_Nova_open = 0;
	return 1;
}

nova_network_Nova_ConnectionSocket* nova_network_Nova_ServerSocket_Nova_acceptClient(nova_network_Nova_ServerSocket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	SOCKET_ID_TYPE l1_Nova_clientSocket = (SOCKET_ID_TYPE)nova_null;
	nova_network_Nova_ConnectionSocket* l1_Nova_socket = (nova_network_Nova_ConnectionSocket*)nova_null;
	
	l1_Nova_clientSocket = nova_serversocket_accept(this->prv->nova_network_Nova_ServerSocket_Nova_serverSocket);
	if (l1_Nova_clientSocket == 0)
	{
		return (nova_network_Nova_ConnectionSocket*)nova_null;
	}
	l1_Nova_socket = nova_network_Nova_ConnectionSocket_Nova_construct(0, exceptionData, l1_Nova_clientSocket);
	return l1_Nova_socket;
}

void nova_network_Nova_ServerSocket_0_Nova_super(nova_network_Nova_ServerSocket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_network_Nova_ServerSocket_Nova_open = 0;
	this->nova_network_Nova_ServerSocket_Nova_requests = (nova_datastruct_list_Nova_Array*)nova_null;
	this->prv->nova_network_Nova_ServerSocket_Nova_serverSocket = 0;
	this->nova_network_Nova_ServerSocket_Nova_requests = nova_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
}

