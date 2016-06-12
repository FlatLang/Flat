#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_Nova_ConnectionSocket.h>

nova_standard_network_Extension_VTable_ConnectionSocket nova_standard_network_Extension_VTable_ConnectionSocket_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	nova_standard_datastruct_list_Nova_Queue* nova_standard_network_Nova_ConnectionSocket_Nova_inputBuffer;
	SOCKET_ID_TYPE nova_standard_network_Nova_ConnectionSocket_Nova_socket;
	
)

nova_standard_Nova_String* nova_standard_network_Nova_ConnectionSocket_1_Nova_readString(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_network_Nova_ConnectionSocket_Nova_checkBuffer);
void nova_standard_network_Nova_ConnectionSocketNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_Nova_ConnectionSocket* nova_standard_network_Nova_ConnectionSocket_Nova_construct(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData, SOCKET_ID_TYPE nova_standard_network_Nova_ConnectionSocket_Nova_socket)
{
	CCLASS_NEW(nova_standard_network_Nova_ConnectionSocket, this);
	this->vtable = &nova_standard_network_Extension_VTable_ConnectionSocket_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_network_Nova_Socket_Nova_super((nova_standard_network_Nova_Socket*)this, exceptionData);
	nova_standard_network_Nova_ConnectionSocket_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_network_Nova_ConnectionSocket_Nova_this(this, exceptionData, nova_standard_network_Nova_ConnectionSocket_Nova_socket);
	}
	
	return this;
}

void nova_standard_network_Nova_ConnectionSocket_Nova_destroy(nova_standard_network_Nova_ConnectionSocket** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_list_Nova_Queue_Nova_destroy(&(*this)->prv->nova_standard_network_Nova_ConnectionSocket_Nova_inputBuffer, exceptionData);
	
	NOVA_FREE((*this)->prv);
	
	
	nova_standard_io_Nova_OutputStream_Nova_destroy(&(*this)->nova_standard_network_Nova_ConnectionSocket_Nova_out, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_network_Nova_ConnectionSocket_Nova_this(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData, SOCKET_ID_TYPE nova_standard_network_Nova_ConnectionSocket_Nova_socket)
{
	this->prv->nova_standard_network_Nova_ConnectionSocket_Nova_socket = nova_standard_network_Nova_ConnectionSocket_Nova_socket;
	this->nova_standard_network_Nova_ConnectionSocket_Nova_in = (nova_standard_io_Nova_InputStream*)(nova_standard_network_Nova_NetworkInputStream_Nova_construct(0, exceptionData, this));
	this->nova_standard_network_Nova_ConnectionSocket_Nova_out = (nova_standard_io_Nova_OutputStream*)(nova_standard_network_Nova_NetworkOutputStream_Nova_construct(0, exceptionData, this));
	this->nova_standard_network_Nova_ConnectionSocket_Nova_connected = 1;
}

void nova_standard_network_Nova_ConnectionSocket_Nova_close(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_network_Nova_ConnectionSocket_Nova_connected = 0;
	nova_socket_close(this->prv->nova_standard_network_Nova_ConnectionSocket_Nova_socket);
}

char nova_standard_network_Nova_ConnectionSocket_Nova_validateConnection(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_message;
	
	if (!this->nova_standard_network_Nova_ConnectionSocket_Nova_connected)
	{
		return this->nova_standard_network_Nova_ConnectionSocket_Nova_connected;
	}
	l1_Nova_message = nova_standard_network_Nova_ConnectionSocket_1_Nova_readString(this, exceptionData, 0);
	if (this->nova_standard_network_Nova_ConnectionSocket_Nova_connected)
	{
		nova_standard_datastruct_list_Nova_Queue_Nova_enqueue(this->prv->nova_standard_network_Nova_ConnectionSocket_Nova_inputBuffer, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_message));
	}
	return this->nova_standard_network_Nova_ConnectionSocket_Nova_connected;
}

nova_standard_Nova_String* nova_standard_network_Nova_ConnectionSocket_0_Nova_readString(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_network_Nova_ConnectionSocket_1_Nova_readString(this, exceptionData, 1);
}

nova_standard_Nova_String* nova_standard_network_Nova_ConnectionSocket_1_Nova_readString(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_network_Nova_ConnectionSocket_Nova_checkBuffer)
{
	if (!nova_standard_network_Nova_ConnectionSocket_Nova_checkBuffer || nova_standard_datastruct_list_Nova_Queue_Accessor_Nova_empty(this->prv->nova_standard_network_Nova_ConnectionSocket_Nova_inputBuffer, exceptionData))
	{
		char* l1_Nova_data;
		
		l1_Nova_data = (char*)(nova_socket_receive(this->prv->nova_standard_network_Nova_ConnectionSocket_Nova_socket));
		if (l1_Nova_data == 0)
		{
			this->nova_standard_network_Nova_ConnectionSocket_Nova_connected = 0;
			return (nova_standard_Nova_String*)nova_null;
		}
		return nova_standard_Nova_String_2_Nova_construct(0, exceptionData, l1_Nova_data);
	}
	return (nova_standard_Nova_String*)nova_standard_datastruct_list_Nova_Queue_Nova_dequeue(this->prv->nova_standard_network_Nova_ConnectionSocket_Nova_inputBuffer, exceptionData);
}

char nova_standard_network_Nova_ConnectionSocket_Nova_write(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_network_Nova_ConnectionSocket_Nova_data)
{
	char l1_Nova_success;
	
	l1_Nova_success = nova_socket_send(this->prv->nova_standard_network_Nova_ConnectionSocket_Nova_socket, (char*)(nova_standard_network_Nova_ConnectionSocket_Nova_data->nova_standard_Nova_String_Nova_chars)) == 1;
	return l1_Nova_success;
}

void nova_standard_network_Nova_ConnectionSocket_0_Nova_super(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_network_Nova_ConnectionSocket_Nova_connected = 0;
	this->nova_standard_network_Nova_ConnectionSocket_Nova_in = (nova_standard_io_Nova_InputStream*)nova_null;
	this->nova_standard_network_Nova_ConnectionSocket_Nova_out = (nova_standard_io_Nova_OutputStream*)nova_null;
	this->prv->nova_standard_network_Nova_ConnectionSocket_Nova_inputBuffer = (nova_standard_datastruct_list_Nova_Queue*)nova_null;
	this->prv->nova_standard_network_Nova_ConnectionSocket_Nova_socket = 0;
	this->prv->nova_standard_network_Nova_ConnectionSocket_Nova_inputBuffer = nova_standard_datastruct_list_Nova_Queue_0_Nova_construct(0, exceptionData);
}

