#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_ClientThread.h>

stabilitytest_Extension_VTable_ClientThread stabilitytest_Extension_VTable_ClientThread_val =
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
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_ClientThread_0_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_Nova_Thread_Nova_handle;
	
	int stabilitytest_Nova_ClientThread_Nova_port;
	stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ClientThread_Nova_program;
	
)
void stabilitytest_Nova_ClientThreadNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ClientThread* stabilitytest_Nova_ClientThread_Nova_construct(stabilitytest_Nova_ClientThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ClientThread_Nova_program, int stabilitytest_Nova_ClientThread_Nova_port)
{
	CCLASS_NEW(stabilitytest_Nova_ClientThread, this);
	this->vtable = &stabilitytest_Extension_VTable_ClientThread_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_thread_Nova_Thread_Nova_super((nova_standard_thread_Nova_Thread*)this, exceptionData);
	stabilitytest_Nova_ClientThread_2_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ClientThread_Nova_this(this, exceptionData, stabilitytest_Nova_ClientThread_Nova_program, stabilitytest_Nova_ClientThread_Nova_port);
	}
	
	return this;
}

void stabilitytest_Nova_ClientThread_Nova_destroy(stabilitytest_Nova_ClientThread** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	stabilitytest_Nova_StabilityTest_Nova_destroy(&(*this)->prv->stabilitytest_Nova_ClientThread_Nova_program, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_ClientThread_Nova_this(stabilitytest_Nova_ClientThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ClientThread_Nova_program, int stabilitytest_Nova_ClientThread_Nova_port)
{
	this->prv->stabilitytest_Nova_ClientThread_Nova_program = stabilitytest_Nova_ClientThread_Nova_program;
	this->prv->stabilitytest_Nova_ClientThread_Nova_port = stabilitytest_Nova_ClientThread_Nova_port;
}

void stabilitytest_Nova_ClientThread_0_Nova_run(stabilitytest_Nova_ClientThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_network_Nova_ClientSocket* l1_Nova_client;
	nova_standard_Nova_String* l1_Nova_ip;
	nova_standard_Nova_String* l1_Nova_s;
	nova_standard_Nova_String* nova_local_0;
	
	l1_Nova_client = nova_standard_network_Nova_ClientSocket_2_Nova_construct(0, exceptionData);
	l1_Nova_ip = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "127.0.0.1");
	nova_local_0 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, this->prv->stabilitytest_Nova_ClientThread_Nova_port);
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "ClientSocket attempting to connect to "), exceptionData, l1_Nova_ip->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l1_Nova_ip, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ":"), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "... "))))));
	if (!nova_standard_network_Nova_ClientSocket_Nova_connect(l1_Nova_client, exceptionData, l1_Nova_ip, this->prv->stabilitytest_Nova_ClientThread_Nova_port))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->prv->stabilitytest_Nova_ClientThread_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to connect to localhost server"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Waiting for String from ServerSocket... "));
	l1_Nova_s = l1_Nova_client->nova_standard_network_Nova_ClientSocket_Nova_connection->nova_standard_network_Nova_ConnectionSocket_Nova_in->vtable->itable.nova_standard_io_Nova_InputStream_virtual0_Nova_readString(l1_Nova_client->nova_standard_network_Nova_ClientSocket_Nova_connection->nova_standard_network_Nova_ConnectionSocket_Nova_in, exceptionData);
	if (l1_Nova_s->nova_standard_Nova_String_Nova_size != stabilitytest_Nova_NetworkStability_Nova_received->nova_standard_Nova_String_Nova_size || !l1_Nova_s->vtable->nova_standard_Nova_String_virtual_Nova_equals(l1_Nova_s, exceptionData, stabilitytest_Nova_NetworkStability_Nova_received))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->prv->stabilitytest_Nova_ClientThread_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Client unable to receive the correct message from server"));
	}
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Attempting to send String to ServerSocket... "));
	l1_Nova_client->nova_standard_network_Nova_ClientSocket_Nova_connection->nova_standard_network_Nova_ConnectionSocket_Nova_out->vtable->nova_standard_io_Nova_OutputStream_virtual1_Nova_write(l1_Nova_client->nova_standard_network_Nova_ClientSocket_Nova_connection->nova_standard_network_Nova_ConnectionSocket_Nova_out, exceptionData, stabilitytest_Nova_NetworkStability_Nova_received);
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Attempting to close ClientSocket... "));
	if (!nova_standard_network_Nova_ClientSocket_Nova_close(l1_Nova_client, exceptionData))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->prv->stabilitytest_Nova_ClientThread_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Unable to close Client connection"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

void stabilitytest_Nova_ClientThread_2_Nova_super(stabilitytest_Nova_ClientThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->stabilitytest_Nova_ClientThread_Nova_port = 0;
	this->prv->stabilitytest_Nova_ClientThread_Nova_program = (stabilitytest_Nova_StabilityTest*)nova_null;
}

