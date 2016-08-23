#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_ClientThread.h>



stabilitytest_Extension_VTable_ClientThread stabilitytest_Extension_VTable_ClientThread_val =
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
	nova_Nova_Object_0_Nova_getHashCodeLong,
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_ClientThread_0_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_thread_Nova_Thread_Nova_handle;
	
	int stabilitytest_Nova_ClientThread_Nova_port;
	stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ClientThread_Nova_program;
	
)
void stabilitytest_Nova_ClientThread_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ClientThread* stabilitytest_Nova_ClientThread_Nova_ClientThread(stabilitytest_Nova_ClientThread* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ClientThread_Nova_program, int stabilitytest_Nova_ClientThread_Nova_port)
{
	CCLASS_NEW(stabilitytest_Nova_ClientThread, this);
	this->vtable = &stabilitytest_Extension_VTable_ClientThread_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_thread_Nova_Thread_Nova_super((nova_thread_Nova_Thread*)this, exceptionData);
	stabilitytest_Nova_ClientThread_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ClientThread_Nova_this(this, exceptionData, stabilitytest_Nova_ClientThread_Nova_program, stabilitytest_Nova_ClientThread_Nova_port);
	}
	
	return this;
}

void stabilitytest_Nova_ClientThread_Nova_destroy(stabilitytest_Nova_ClientThread** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	stabilitytest_Nova_StabilityTest_Nova_destroy(&(*this)->prv->stabilitytest_Nova_ClientThread_Nova_program, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_ClientThread_Nova_this(stabilitytest_Nova_ClientThread* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ClientThread_Nova_program, int stabilitytest_Nova_ClientThread_Nova_port)
{
	this->prv->stabilitytest_Nova_ClientThread_Nova_program = stabilitytest_Nova_ClientThread_Nova_program;
	this->prv->stabilitytest_Nova_ClientThread_Nova_port = stabilitytest_Nova_ClientThread_Nova_port;
}

void stabilitytest_Nova_ClientThread_0_Nova_run(stabilitytest_Nova_ClientThread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_network_Nova_ClientSocket* l1_Nova_client = (nova_network_Nova_ClientSocket*)nova_null;
	nova_Nova_String* l1_Nova_ip = (nova_Nova_String*)nova_null;
	nova_Nova_String* l1_Nova_s = (nova_Nova_String*)nova_null;
	
	l1_Nova_client = nova_network_Nova_ClientSocket_Nova_ClientSocket(0, exceptionData);
	l1_Nova_ip = nova_Nova_String_1_Nova_String(0, exceptionData, "127.0.0.1");
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, "ClientSocket attempting to connect to "), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(l1_Nova_ip), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, ":"), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, this->prv->stabilitytest_Nova_ClientThread_Nova_port)), exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "... "))))));
	if (!nova_network_Nova_ClientSocket_Nova_connect(l1_Nova_client, exceptionData, l1_Nova_ip, this->prv->stabilitytest_Nova_ClientThread_Nova_port))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->prv->stabilitytest_Nova_ClientThread_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Failed to connect to localhost server"));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "OK"));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Waiting for String from ServerSocket... "));
	l1_Nova_s = (nova_Nova_String*)(nova_io_Nova_InputStream_virtual1_Nova_readString((nova_io_Nova_InputStream*)(l1_Nova_client->nova_network_Nova_ClientSocket_Nova_connection->nova_network_Nova_ConnectionSocket_Nova_in), exceptionData));
	if (l1_Nova_s->nova_Nova_String_Nova_count != stabilitytest_Nova_NetworkStability_Nova_received->nova_Nova_String_Nova_count || !nova_operators_Nova_Equals_virtual0_Nova_equals((nova_operators_Nova_Equals*)(l1_Nova_s), exceptionData, (nova_Nova_Object*)(stabilitytest_Nova_NetworkStability_Nova_received)))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->prv->stabilitytest_Nova_ClientThread_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Client unable to receive the correct message from server"));
	}
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Attempting to send String to ServerSocket... "));
	nova_io_Nova_OutputStream_virtual0_Nova_write((nova_io_Nova_OutputStream*)(l1_Nova_client->nova_network_Nova_ClientSocket_Nova_connection->nova_network_Nova_ConnectionSocket_Nova_out), exceptionData, stabilitytest_Nova_NetworkStability_Nova_received);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "OK"));
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Attempting to close ClientSocket... "));
	if (!nova_network_Nova_ClientSocket_Nova_close(l1_Nova_client, exceptionData))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->prv->stabilitytest_Nova_ClientThread_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Unable to close Client connection"));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "OK"));
}

void stabilitytest_Nova_ClientThread_0_Nova_super(stabilitytest_Nova_ClientThread* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->stabilitytest_Nova_ClientThread_Nova_port = 0;
	this->prv->stabilitytest_Nova_ClientThread_Nova_program = (stabilitytest_Nova_StabilityTest*)nova_null;
}

