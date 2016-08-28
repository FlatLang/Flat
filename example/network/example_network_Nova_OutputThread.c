#include <precompiled.h>
#include <example/network/example_network_Nova_OutputThread.h>



example_network_Extension_VTable_OutputThread example_network_Extension_VTable_OutputThread_val =
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
	example_network_Nova_OutputThread_0_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_thread_Nova_Thread_Nova_handle;
	
	nova_network_Nova_ServerSocket* example_network_Nova_OutputThread_Nova_serverSocket;
	nova_network_Nova_ConnectionSocket* example_network_Nova_OutputThread_Nova_socket;
	
)
void example_network_Nova_OutputThread_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_network_Nova_OutputThread* example_network_Nova_OutputThread_Nova_OutputThread(example_network_Nova_OutputThread* this, nova_exception_Nova_ExceptionData* exceptionData, nova_network_Nova_ServerSocket* example_network_Nova_OutputThread_Nova_serverSocket, nova_network_Nova_ConnectionSocket* example_network_Nova_OutputThread_Nova_socket)
{
	CCLASS_NEW(example_network_Nova_OutputThread, this);
	this->vtable = &example_network_Extension_VTable_OutputThread_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_thread_Nova_Thread_Nova_super((nova_thread_Nova_Thread*)this, exceptionData);
	example_network_Nova_OutputThread_0_Nova_super(this, exceptionData);
	
	{
		example_network_Nova_OutputThread_Nova_this(this, exceptionData, example_network_Nova_OutputThread_Nova_serverSocket, example_network_Nova_OutputThread_Nova_socket);
	}
	
	return this;
}

void example_network_Nova_OutputThread_Nova_destroy(example_network_Nova_OutputThread** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_network_Nova_ServerSocket_Nova_destroy(&(*this)->prv->example_network_Nova_OutputThread_Nova_serverSocket, exceptionData);
	nova_network_Nova_ConnectionSocket_Nova_destroy(&(*this)->prv->example_network_Nova_OutputThread_Nova_socket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void example_network_Nova_OutputThread_Nova_this(example_network_Nova_OutputThread* this, nova_exception_Nova_ExceptionData* exceptionData, nova_network_Nova_ServerSocket* example_network_Nova_OutputThread_Nova_serverSocket, nova_network_Nova_ConnectionSocket* example_network_Nova_OutputThread_Nova_socket)
{
	this->prv->example_network_Nova_OutputThread_Nova_serverSocket = example_network_Nova_OutputThread_Nova_serverSocket;
	this->prv->example_network_Nova_OutputThread_Nova_socket = example_network_Nova_OutputThread_Nova_socket;
}

void example_network_Nova_OutputThread_0_Nova_run(example_network_Nova_OutputThread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	while (this->prv->example_network_Nova_OutputThread_Nova_socket->nova_network_Nova_ConnectionSocket_Nova_connected)
	{
		nova_Nova_String* l1_Nova_message = (nova_Nova_String*)nova_null;
		
		l1_Nova_message = nova_io_Nova_Console_Nova_readLine(0, exceptionData);
		if (nova_operators_Nova_Equals_virtual0_Nova_equals((nova_operators_Nova_Equals*)(l1_Nova_message), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("q")))))
		{
			nova_network_Nova_ServerSocket_Nova_close(this->prv->example_network_Nova_OutputThread_Nova_serverSocket, exceptionData);
			break;
		}
		if (!this->prv->example_network_Nova_OutputThread_Nova_socket->nova_network_Nova_ConnectionSocket_Nova_connected)
		{
			break;
		}
		nova_io_Nova_OutputStream_virtual0_Nova_write((nova_io_Nova_OutputStream*)(this->prv->example_network_Nova_OutputThread_Nova_socket->nova_network_Nova_ConnectionSocket_Nova_out), exceptionData, l1_Nova_message);
	}
}

void example_network_Nova_OutputThread_0_Nova_super(example_network_Nova_OutputThread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->example_network_Nova_OutputThread_Nova_serverSocket = (nova_network_Nova_ServerSocket*)nova_null;
	this->prv->example_network_Nova_OutputThread_Nova_socket = (nova_network_Nova_ConnectionSocket*)nova_null;
}

