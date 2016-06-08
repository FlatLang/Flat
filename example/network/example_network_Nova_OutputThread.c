#include <precompiled.h>
#include <example/network/example_network_Nova_OutputThread.h>

example_network_Extension_VTable_OutputThread example_network_Extension_VTable_OutputThread_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	example_network_Nova_OutputThread_0_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_Nova_Thread_Nova_handle;
	
	nova_standard_network_Nova_ServerSocket* example_network_Nova_OutputThread_Nova_serverSocket;
	nova_standard_network_Nova_ConnectionSocket* example_network_Nova_OutputThread_Nova_socket;
	
)
void example_network_Nova_OutputThreadNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_network_Nova_OutputThread* example_network_Nova_OutputThread_Nova_construct(example_network_Nova_OutputThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_network_Nova_ServerSocket* example_network_Nova_OutputThread_Nova_serverSocket, nova_standard_network_Nova_ConnectionSocket* example_network_Nova_OutputThread_Nova_socket)
{
	CCLASS_NEW(example_network_Nova_OutputThread, this);
	this->vtable = &example_network_Extension_VTable_OutputThread_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_thread_Nova_Thread_Nova_super((nova_standard_thread_Nova_Thread*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_thread_Nova_Thread_2_Nova_this((nova_standard_thread_Nova_Thread*)(this), exceptionData);
	example_network_Nova_OutputThread_2_Nova_super(this, exceptionData);
	
	{
		example_network_Nova_OutputThread_Nova_this(this, exceptionData, example_network_Nova_OutputThread_Nova_serverSocket, example_network_Nova_OutputThread_Nova_socket);
	}
	
	return this;
}

void example_network_Nova_OutputThread_Nova_destroy(example_network_Nova_OutputThread** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_network_Nova_ServerSocket_Nova_destroy(&(*this)->prv->example_network_Nova_OutputThread_Nova_serverSocket, exceptionData);
	nova_standard_network_Nova_ConnectionSocket_Nova_destroy(&(*this)->prv->example_network_Nova_OutputThread_Nova_socket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void example_network_Nova_OutputThread_Nova_this(example_network_Nova_OutputThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_network_Nova_ServerSocket* example_network_Nova_OutputThread_Nova_serverSocket, nova_standard_network_Nova_ConnectionSocket* example_network_Nova_OutputThread_Nova_socket)
{
	this->prv->example_network_Nova_OutputThread_Nova_serverSocket = example_network_Nova_OutputThread_Nova_serverSocket;
	this->prv->example_network_Nova_OutputThread_Nova_socket = example_network_Nova_OutputThread_Nova_socket;
}

void example_network_Nova_OutputThread_0_Nova_run(example_network_Nova_OutputThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	while (this->prv->example_network_Nova_OutputThread_Nova_socket->nova_standard_network_Nova_ConnectionSocket_Nova_connected)
	{
		nova_standard_Nova_String* l1_Nova_message;
		
		l1_Nova_message = nova_standard_io_Nova_Console_Nova_readLine(0, exceptionData);
		if (l1_Nova_message->vtable->nova_standard_Nova_String_virtual_Nova_equals(l1_Nova_message, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "q")))
		{
			nova_standard_network_Nova_ServerSocket_Nova_close(this->prv->example_network_Nova_OutputThread_Nova_serverSocket, exceptionData);
			break;
		}
		if (!this->prv->example_network_Nova_OutputThread_Nova_socket->nova_standard_network_Nova_ConnectionSocket_Nova_connected)
		{
			break;
		}
		this->prv->example_network_Nova_OutputThread_Nova_socket->nova_standard_network_Nova_ConnectionSocket_Nova_out->vtable->nova_standard_io_Nova_OutputStream_virtual1_Nova_write(this->prv->example_network_Nova_OutputThread_Nova_socket->nova_standard_network_Nova_ConnectionSocket_Nova_out, exceptionData, l1_Nova_message);
	}
}

void example_network_Nova_OutputThread_2_Nova_super(example_network_Nova_OutputThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->example_network_Nova_OutputThread_Nova_serverSocket = (nova_standard_network_Nova_ServerSocket*)nova_null;
	this->prv->example_network_Nova_OutputThread_Nova_socket = (nova_standard_network_Nova_ConnectionSocket*)nova_null;
}

