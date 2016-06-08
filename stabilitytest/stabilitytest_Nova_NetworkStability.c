#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_NetworkStability.h>

stabilitytest_Extension_VTable_NetworkStability stabilitytest_Extension_VTable_NetworkStability_val =
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
	stabilitytest_Nova_NetworkStability_0_Nova_test,
};



nova_standard_network_Nova_ServerSocket* stabilitytest_Nova_NetworkStability_Nova_createServer(stabilitytest_Nova_NetworkStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int stabilitytest_Nova_NetworkStability_Nova_port);
nova_standard_Nova_String* stabilitytest_Nova_NetworkStability_Nova_received;
void stabilitytest_Nova_NetworkStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
		stabilitytest_Nova_NetworkStability_Nova_received = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "THIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVED");
	}
}

stabilitytest_Nova_NetworkStability* stabilitytest_Nova_NetworkStability_0_Nova_construct(stabilitytest_Nova_NetworkStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_NetworkStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_NetworkStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_NetworkStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_NetworkStability_2_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_NetworkStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_NetworkStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_NetworkStability_Nova_destroy(stabilitytest_Nova_NetworkStability** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_Nova_NetworkStability_0_Nova_test(stabilitytest_Nova_NetworkStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	int l1_Nova_port;
	nova_standard_network_Nova_ServerSocket* l1_Nova_server;
	stabilitytest_Nova_ClientThread* l1_Nova_thread;
	nova_standard_network_Nova_ConnectionSocket* l1_Nova_connection;
	nova_standard_Nova_String* l1_Nova_s;
	
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking network stability..."));
	l1_Nova_port = 24243;
	l1_Nova_server = stabilitytest_Nova_NetworkStability_Nova_createServer(this, exceptionData, l1_Nova_port);
	l1_Nova_thread = stabilitytest_Nova_ClientThread_Nova_construct(0, exceptionData, this->stabilitytest_Nova_StabilityTestCase_Nova_program, l1_Nova_port);
	nova_standard_thread_Nova_Thread_Nova_start((nova_standard_thread_Nova_Thread*)(l1_Nova_thread), exceptionData);
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Accepting ClientSocket connection... "));
	l1_Nova_connection = nova_standard_network_Nova_ServerSocket_Nova_acceptClient(l1_Nova_server, exceptionData);
	if (l1_Nova_connection == (nova_standard_network_Nova_ConnectionSocket*)nova_null)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to accept the client"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Sending String to ClientSocket... "));
	if (!l1_Nova_connection->nova_standard_network_Nova_ConnectionSocket_Nova_out->vtable->nova_standard_io_Nova_OutputStream_virtual1_Nova_write(l1_Nova_connection->nova_standard_network_Nova_ConnectionSocket_Nova_out, exceptionData, stabilitytest_Nova_NetworkStability_Nova_received))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Server unable to send data to client"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Waiting for String from ClientSocket... "));
	l1_Nova_s = l1_Nova_connection->nova_standard_network_Nova_ConnectionSocket_Nova_in->vtable->itable.nova_standard_io_Nova_InputStream_virtual0_Nova_readString(l1_Nova_connection->nova_standard_network_Nova_ConnectionSocket_Nova_in, exceptionData);
	if (l1_Nova_s->nova_standard_Nova_String_Nova_size != stabilitytest_Nova_NetworkStability_Nova_received->nova_standard_Nova_String_Nova_size || !l1_Nova_s->vtable->nova_standard_Nova_String_virtual_Nova_equals(l1_Nova_s, exceptionData, stabilitytest_Nova_NetworkStability_Nova_received))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Server unable to receive correct message from client"));
	}
	nova_standard_thread_Nova_Thread_Nova_join((nova_standard_thread_Nova_Thread*)(l1_Nova_thread), exceptionData);
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Attempting to close ServerSocket connection... "));
	if (!nova_standard_network_Nova_ServerSocket_Nova_close(l1_Nova_server, exceptionData))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Unable to close server"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

nova_standard_network_Nova_ServerSocket* stabilitytest_Nova_NetworkStability_Nova_createServer(stabilitytest_Nova_NetworkStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int stabilitytest_Nova_NetworkStability_Nova_port)
{
	nova_standard_network_Nova_ServerSocket* l1_Nova_server;
	nova_standard_Nova_String* nova_local_0;
	
	l1_Nova_server = nova_standard_network_Nova_ServerSocket_2_Nova_construct(0, exceptionData);
	nova_local_0 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, stabilitytest_Nova_NetworkStability_Nova_port);
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Attempting to start ServerSocket on port "), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "... "))));
	if (!nova_standard_network_Nova_ServerSocket_Nova_start(l1_Nova_server, exceptionData, stabilitytest_Nova_NetworkStability_Nova_port))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Unable to start server on port "), exceptionData, nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, stabilitytest_Nova_NetworkStability_Nova_port)));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
	return l1_Nova_server;
}

void stabilitytest_Nova_NetworkStability_0_Nova_this(stabilitytest_Nova_NetworkStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_NetworkStability_Nova_program)
{
}

void stabilitytest_Nova_NetworkStability_2_Nova_super(stabilitytest_Nova_NetworkStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

