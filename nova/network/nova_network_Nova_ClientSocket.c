#include <precompiled.h>
#include <nova/network/nova_network_Nova_ClientSocket.h>



nova_network_Extension_VTable_ClientSocket nova_network_Extension_VTable_ClientSocket_val =
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
	SOCKET_ID_TYPE nova_network_Nova_ClientSocket_Nova_socket;
	
)
void nova_network_Nova_ClientSocket_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_network_Nova_ClientSocket* nova_network_Nova_ClientSocket_Nova_construct(nova_network_Nova_ClientSocket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_network_Nova_ClientSocket, this);
	this->vtable = &nova_network_Extension_VTable_ClientSocket_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_network_Nova_Socket_Nova_super((nova_network_Nova_Socket*)this, exceptionData);
	nova_network_Nova_ClientSocket_0_Nova_super(this, exceptionData);
	
	{
		nova_network_Nova_ClientSocket_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_network_Nova_ClientSocket_Nova_destroy(nova_network_Nova_ClientSocket** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	nova_network_Nova_ConnectionSocket_Nova_destroy(&(*this)->nova_network_Nova_ClientSocket_Nova_connection, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_network_Nova_ClientSocket_Nova_this(nova_network_Nova_ClientSocket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

char nova_network_Nova_ClientSocket_Nova_connect(nova_network_Nova_ClientSocket* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_network_Nova_ClientSocket_Nova_ipAddress, int nova_network_Nova_ClientSocket_Nova_port)
{
	this->prv->nova_network_Nova_ClientSocket_Nova_socket = nova_clientsocket_connect((char*)(nova_network_Nova_ClientSocket_Nova_ipAddress->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), nova_network_Nova_ClientSocket_Nova_port);
	if (this->prv->nova_network_Nova_ClientSocket_Nova_socket == 0)
	{
		return 0;
	}
	this->nova_network_Nova_ClientSocket_Nova_connection = nova_network_Nova_ConnectionSocket_Nova_construct(0, exceptionData, this->prv->nova_network_Nova_ClientSocket_Nova_socket);
	return 1;
}

char nova_network_Nova_ClientSocket_Nova_close(nova_network_Nova_ClientSocket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	int l1_Nova_result = 0;
	
	l1_Nova_result = (int)(nova_socket_close(this->prv->nova_network_Nova_ClientSocket_Nova_socket));
	if (l1_Nova_result == 0)
	{
		return 0;
	}
	return 1;
}

void nova_network_Nova_ClientSocket_0_Nova_super(nova_network_Nova_ClientSocket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_network_Nova_ClientSocket_Nova_connection = (nova_network_Nova_ConnectionSocket*)nova_null;
	this->prv->nova_network_Nova_ClientSocket_Nova_socket = 0;
}

