#include <precompiled.h>
#include <example/network/example_network_Nova_ConnectionThread.h>

example_network_Extension_VTable_ConnectionThread example_network_Extension_VTable_ConnectionThread_val =
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
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	example_network_Nova_ConnectionThread_0_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_Nova_Thread_Nova_handle;
	
	nova_standard_network_Nova_ConnectionSocket* example_network_Nova_ConnectionThread_Nova_socket;
	
)
void example_network_Nova_ConnectionThreadNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_network_Nova_ConnectionThread* example_network_Nova_ConnectionThread_Nova_ConnectionThread(example_network_Nova_ConnectionThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_network_Nova_ConnectionSocket* example_network_Nova_ConnectionThread_Nova_socket)
{
	CCLASS_NEW(example_network_Nova_ConnectionThread, this);
	this->vtable = &example_network_Extension_VTable_ConnectionThread_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_thread_Nova_Thread_Nova_super((nova_standard_thread_Nova_Thread*)this, exceptionData);
	example_network_Nova_ConnectionThread_0_Nova_super(this, exceptionData);
	
	{
		example_network_Nova_ConnectionThread_Nova_this(this, exceptionData, example_network_Nova_ConnectionThread_Nova_socket);
	}
	
	return this;
}

void example_network_Nova_ConnectionThread_Nova_destroy(example_network_Nova_ConnectionThread** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_network_Nova_ConnectionSocket_Nova_destroy(&(*this)->prv->example_network_Nova_ConnectionThread_Nova_socket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void example_network_Nova_ConnectionThread_Nova_this(example_network_Nova_ConnectionThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_network_Nova_ConnectionSocket* example_network_Nova_ConnectionThread_Nova_socket)
{
	this->prv->example_network_Nova_ConnectionThread_Nova_socket = example_network_Nova_ConnectionThread_Nova_socket;
}

void example_network_Nova_ConnectionThread_0_Nova_run(example_network_Nova_ConnectionThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	while (1)
	{
		nova_standard_Nova_String* l1_Nova_message;
		
		if (!nova_standard_network_Nova_ConnectionSocket_Nova_validateConnection(this->prv->example_network_Nova_ConnectionThread_Nova_socket, exceptionData))
		{
			nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Disconnected."));
			break;
		}
		l1_Nova_message = (nova_standard_Nova_String*)(nova_standard_io_Nova_InputStream_virtual1_Nova_readString((nova_standard_io_Nova_InputStream*)(this->prv->example_network_Nova_ConnectionThread_Nova_socket->nova_standard_network_Nova_ConnectionSocket_Nova_in), exceptionData));
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "HE SAYS: "), exceptionData, l1_Nova_message));
	}
}

void example_network_Nova_ConnectionThread_0_Nova_super(example_network_Nova_ConnectionThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->example_network_Nova_ConnectionThread_Nova_socket = (nova_standard_network_Nova_ConnectionSocket*)nova_null;
}

