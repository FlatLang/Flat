#include <precompiled.h>
#include <example/network/example_network_Nova_ClientDemo.h>

example_network_Extension_VTable_ClientDemo example_network_Extension_VTable_ClientDemo_val =
{
	{
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
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_2_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void example_network_Nova_ClientDemoNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_network_Nova_ClientDemo* example_network_Nova_ClientDemo_Nova_ClientDemo(example_network_Nova_ClientDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_network_Nova_ClientDemo, this,);
	this->vtable = &example_network_Extension_VTable_ClientDemo_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_network_Nova_ClientDemo_Nova_super(this, exceptionData);
	
	{
		example_network_Nova_ClientDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_network_Nova_ClientDemo_Nova_destroy(example_network_Nova_ClientDemo** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_network_Nova_ClientDemo_Nova_main(example_network_Nova_ClientDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_network_Nova_ClientDemo_Nova_args)
{
	nova_standard_network_Nova_ClientSocket* l1_Nova_socket;
	nova_standard_Nova_String* l1_Nova_ip;
	int l1_Nova_port;
	char l1_Nova_connected;
	
	l1_Nova_socket = nova_standard_network_Nova_ClientSocket_Nova_ClientSocket(0, exceptionData);
	l1_Nova_ip = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "127.0.0.1");
	l1_Nova_port = (int)(5675);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Attempting to connect to "), exceptionData, nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(l1_Nova_ip), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, ":"), exceptionData, nova_standard_primitive_number_Nova_Int_3_Nova_toString(0, exceptionData, l1_Nova_port)))));
	l1_Nova_connected = nova_standard_network_Nova_ClientSocket_Nova_connect(l1_Nova_socket, exceptionData, l1_Nova_ip, l1_Nova_port);
	if (l1_Nova_connected)
	{
		example_network_Nova_ConnectionThread* l1_Nova_thread;
		
		l1_Nova_thread = example_network_Nova_ConnectionThread_Nova_ConnectionThread(0, exceptionData, l1_Nova_socket->nova_standard_network_Nova_ClientSocket_Nova_connection);
		nova_standard_thread_Nova_Thread_Nova_start((nova_standard_thread_Nova_Thread*)(l1_Nova_thread), exceptionData);
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Connected!"));
		while (l1_Nova_socket->nova_standard_network_Nova_ClientSocket_Nova_connection->nova_standard_network_Nova_ConnectionSocket_Nova_connected)
		{
			nova_standard_Nova_String* l2_Nova_message;
			
			l2_Nova_message = nova_standard_io_Nova_Console_Nova_readLine(0, exceptionData);
			if (nova_standard_operators_Nova_Equals_virtual2_Nova_equals((nova_standard_operators_Nova_Equals*)(l2_Nova_message), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "q"))))
			{
				nova_standard_network_Nova_ClientSocket_Nova_close(l1_Nova_socket, exceptionData);
				break;
			}
			nova_standard_io_Nova_OutputStream_virtual1_Nova_write((nova_standard_io_Nova_OutputStream*)(l1_Nova_socket->nova_standard_network_Nova_ClientSocket_Nova_connection->nova_standard_network_Nova_ConnectionSocket_Nova_out), exceptionData, l2_Nova_message);
		}
		nova_standard_network_Nova_ClientSocket_Nova_close(l1_Nova_socket, exceptionData);
	}
	else
	{
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Failed to connect"));
		nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
	}
}

void example_network_Nova_ClientDemo_0_Nova_this(example_network_Nova_ClientDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_network_Nova_ClientDemo_Nova_super(example_network_Nova_ClientDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

