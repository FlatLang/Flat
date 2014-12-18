#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_Nova_ClientSocket.h>


nova_VTable_nova_standard_network_Nova_ClientSocket nova_VTable_nova_standard_network_Nova_ClientSocket_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
CCLASS_PRIVATE
(
	SOCKET_ID_TYPE nova_standard_network_Nova_ClientSocket_Nova_socket;
	
)
void nova_standard_network_Nova_ClientSocketNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_Nova_ClientSocket* nova_standard_network_Nova_ClientSocket_2_Nova_construct(nova_standard_network_Nova_ClientSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_network_Nova_ClientSocket, this);
	this->vtable = &nova_VTable_nova_standard_network_Nova_ClientSocket_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_network_Nova_Socket_Nova_super((nova_standard_network_Nova_Socket*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_network_Nova_Socket_2_Nova_this((nova_standard_network_Nova_Socket*)(this), exceptionData);
	nova_standard_network_Nova_ClientSocket_2_Nova_super(this, exceptionData);
	
	{
		nova_standard_network_Nova_ClientSocket_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_network_Nova_ClientSocket_Nova_destroy(nova_standard_network_Nova_ClientSocket** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	nova_standard_network_Nova_ConnectionSocket_Nova_destroy(&(*this)->nova_standard_network_Nova_ClientSocket_Nova_connection, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_network_Nova_ClientSocket_2_Nova_this(nova_standard_network_Nova_ClientSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

char nova_standard_network_Nova_ClientSocket_Nova_connect(nova_standard_network_Nova_ClientSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_ipAddress, int l0_Nova_port)
{
	this->prv->nova_standard_network_Nova_ClientSocket_Nova_socket = nova_clientsocket_connect((char*)(l0_Nova_ipAddress->nova_standard_Nova_String_Nova_chars), (int)(l0_Nova_port));
	if (this->prv->nova_standard_network_Nova_ClientSocket_Nova_socket == 0)
	{
		return 0;
	}
	this->nova_standard_network_Nova_ClientSocket_Nova_connection = nova_standard_network_Nova_ConnectionSocket_Nova_construct(0, exceptionData, this->prv->nova_standard_network_Nova_ClientSocket_Nova_socket);
	return 1;
}

char nova_standard_network_Nova_ClientSocket_Nova_close(nova_standard_network_Nova_ClientSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	int l1_Nova_result;
	
	l1_Nova_result = (int)(nova_socket_close(this->prv->nova_standard_network_Nova_ClientSocket_Nova_socket));
	if (l1_Nova_result == 0)
	{
		return 0;
	}
	return 1;
}

void nova_standard_network_Nova_ClientSocket_2_Nova_super(nova_standard_network_Nova_ClientSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_network_Nova_ClientSocket_Nova_connection = (nova_standard_network_Nova_ConnectionSocket*)nova_null;
	this->prv->nova_standard_network_Nova_ClientSocket_Nova_socket = 0;
}

