#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_NetworkStability.h>



stabilitytest_Extension_VTable_NetworkStability stabilitytest_Extension_VTable_NetworkStability_val =
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
	stabilitytest_Nova_NetworkStability_0_Nova_test,
};



nova_network_Nova_ServerSocket* stabilitytest_Nova_NetworkStability_Nova_createServer(stabilitytest_Nova_NetworkStability* this, nova_exception_Nova_ExceptionData* exceptionData, int stabilitytest_Nova_NetworkStability_Nova_port);
nova_Nova_String* stabilitytest_Nova_NetworkStability_Nova_received;
void stabilitytest_Nova_NetworkStability_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
		stabilitytest_Nova_NetworkStability_Nova_received = nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("THIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVED"));
	}
}

stabilitytest_Nova_NetworkStability* stabilitytest_Nova_NetworkStability_Nova_NetworkStability(stabilitytest_Nova_NetworkStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_NetworkStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_NetworkStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_NetworkStability_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_NetworkStability_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_NetworkStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_NetworkStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_NetworkStability_Nova_destroy(stabilitytest_Nova_NetworkStability** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_NetworkStability_0_Nova_this(stabilitytest_Nova_NetworkStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_NetworkStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_NetworkStability_Nova_program);
}

void stabilitytest_Nova_NetworkStability_0_Nova_test(stabilitytest_Nova_NetworkStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	int l1_Nova_port = 0;
	nova_network_Nova_ServerSocket* l1_Nova_server = (nova_network_Nova_ServerSocket*)nova_null;
	stabilitytest_Nova_ClientThread* l1_Nova_thread = (stabilitytest_Nova_ClientThread*)nova_null;
	nova_network_Nova_ConnectionSocket* l1_Nova_connection = (nova_network_Nova_ConnectionSocket*)nova_null;
	nova_Nova_String* l1_Nova_s = (nova_Nova_String*)nova_null;
	
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Checking network stability...")));
	l1_Nova_port = (int)(24243);
	l1_Nova_server = stabilitytest_Nova_NetworkStability_Nova_createServer(this, exceptionData, l1_Nova_port);
	l1_Nova_thread = stabilitytest_Nova_ClientThread_Nova_ClientThread(0, exceptionData, this->stabilitytest_Nova_StabilityTestCase_Nova_program, l1_Nova_port);
	nova_thread_Nova_Thread_Nova_start((nova_thread_Nova_Thread*)(l1_Nova_thread), exceptionData);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Accepting ClientSocket connection... ")));
	l1_Nova_connection = nova_network_Nova_ServerSocket_Nova_acceptClient(l1_Nova_server, exceptionData);
	if (l1_Nova_connection == (nova_network_Nova_ConnectionSocket*)nova_null)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Failed to accept the client")));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Sending String to ClientSocket... ")));
	if (!nova_io_Nova_OutputStream_virtual0_Nova_write((nova_io_Nova_OutputStream*)(l1_Nova_connection->nova_network_Nova_ConnectionSocket_Nova_out), exceptionData, stabilitytest_Nova_NetworkStability_Nova_received))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Server unable to send data to client")));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Waiting for String from ClientSocket... ")));
	l1_Nova_s = (nova_Nova_String*)(nova_io_Nova_InputStream_virtual1_Nova_readString((nova_io_Nova_InputStream*)(l1_Nova_connection->nova_network_Nova_ConnectionSocket_Nova_in), exceptionData));
	if (l1_Nova_s->nova_Nova_String_Nova_count != stabilitytest_Nova_NetworkStability_Nova_received->nova_Nova_String_Nova_count || !nova_operators_Nova_Equals_virtual0_Nova_equals((nova_operators_Nova_Equals*)(l1_Nova_s), exceptionData, (nova_Nova_Object*)(stabilitytest_Nova_NetworkStability_Nova_received)))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Server unable to receive correct message from client")));
	}
	nova_thread_Nova_Thread_Nova_join((nova_thread_Nova_Thread*)(l1_Nova_thread), exceptionData);
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Attempting to close ServerSocket connection... ")));
	if (!nova_network_Nova_ServerSocket_Nova_close(l1_Nova_server, exceptionData))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Unable to close server")));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("OK")));
}

nova_network_Nova_ServerSocket* stabilitytest_Nova_NetworkStability_Nova_createServer(stabilitytest_Nova_NetworkStability* this, nova_exception_Nova_ExceptionData* exceptionData, int stabilitytest_Nova_NetworkStability_Nova_port)
{
	nova_network_Nova_ServerSocket* l1_Nova_server = (nova_network_Nova_ServerSocket*)nova_null;
	
	l1_Nova_server = nova_network_Nova_ServerSocket_Nova_ServerSocket(0, exceptionData);
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Attempting to start ServerSocket on port ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, stabilitytest_Nova_NetworkStability_Nova_port)), exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("... ")))));
	if (!nova_network_Nova_ServerSocket_Nova_start(l1_Nova_server, exceptionData, stabilitytest_Nova_NetworkStability_Nova_port))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Unable to start server on port ")), exceptionData, nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, stabilitytest_Nova_NetworkStability_Nova_port)));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("OK")));
	return l1_Nova_server;
}

void stabilitytest_Nova_NetworkStability_0_Nova_super(stabilitytest_Nova_NetworkStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

