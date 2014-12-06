#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaNetworkStability.h>


nova_VTable_stabilitytest_NovaNetworkStability nova_VTable_stabilitytest_NovaNetworkStability_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
};

nova_standard_network_NovaServerSocket* stabilitytest_NovaNetworkStability_static_NovacreateServer(stabilitytest_NovaNetworkStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram, int l0_Novaport);
nova_standard_NovaString* stabilitytest_NovaNetworkStability_static_Novareceived;
void stabilitytest_NovaNetworkStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
		stabilitytest_NovaNetworkStability_static_Novareceived = nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "THIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVEDTHIS IS THE STRING THAT SHOULD BE RECEIVED");
	}
}

stabilitytest_NovaNetworkStability* stabilitytest_NovaNetworkStability_0_Novaconstruct(stabilitytest_NovaNetworkStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaNetworkStability, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaNetworkStability_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaNetworkStability_Novasuper(this, exceptionData);
	
	{
		stabilitytest_NovaNetworkStability_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_NetworkStability(stabilitytest_NovaNetworkStability** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaNetworkStability_static_Novatest(stabilitytest_NovaNetworkStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	int l1_Novaport;
	nova_standard_network_NovaServerSocket* l1_Novaserver;
	stabilitytest_NovaClientThread* l1_Novathread;
	nova_standard_network_NovaConnectionSocket* l1_Novaconnection;
	nova_standard_NovaString* l1_Novas;
	
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking network stability..."));
	l1_Novaport = 24243;
	l1_Novaserver = stabilitytest_NovaNetworkStability_static_NovacreateServer((stabilitytest_NovaNetworkStability*)nova_null, exceptionData, l0_Novaprogram, l1_Novaport);
	l1_Novathread = stabilitytest_NovaClientThread_Novaconstruct(0, exceptionData, l0_Novaprogram, l1_Novaport);
	nova_standard_thread_NovaThread_Novastart((nova_standard_thread_NovaThread*)(l1_Novathread), exceptionData);
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Accepting ClientSocket connection... "));
	l1_Novaconnection = nova_standard_network_NovaServerSocket_NovaacceptClient(l1_Novaserver, exceptionData);
	if (l1_Novaconnection == (nova_standard_network_NovaConnectionSocket*)nova_null)
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Failed to accept the client"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Sending String to ClientSocket... "));
	if (!l1_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout->vtable->nova_standard_io_NovaOutputStream_virtual1_Novawrite(l1_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout, exceptionData, stabilitytest_NovaNetworkStability_static_Novareceived))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Server unable to send data to client"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Waiting for String from ClientSocket... "));
	l1_Novas = l1_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_virtual0_NovareadString(l1_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain, exceptionData);
	if (l1_Novas->nova_standard_NovaString_Novalength != stabilitytest_NovaNetworkStability_static_Novareceived->nova_standard_NovaString_Novalength || !l1_Novas->vtable->nova_standard_NovaString_virtual_Novaequals(l1_Novas, exceptionData, stabilitytest_NovaNetworkStability_static_Novareceived))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Server unable to receive correct message from client"));
	}
	nova_standard_thread_NovaThread_Novajoin((nova_standard_thread_NovaThread*)(l1_Novathread), exceptionData);
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Attempting to close ServerSocket connection... "));
	if (!nova_standard_network_NovaServerSocket_Novaclose(l1_Novaserver, exceptionData))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Unable to close server"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
}

nova_standard_network_NovaServerSocket* stabilitytest_NovaNetworkStability_static_NovacreateServer(stabilitytest_NovaNetworkStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram, int l0_Novaport)
{
	nova_standard_network_NovaServerSocket* l1_Novaserver;
	nova_standard_NovaString* nova_local_0;
	
	l1_Novaserver = nova_standard_network_NovaServerSocket_0_Novaconstruct(0, exceptionData);
	nova_local_0 = nova_standard_primitive_number_NovaInt_static_1_NovatoString(0, exceptionData, l0_Novaport);
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_0_Novaconcat(nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Attempting to start ServerSocket on port "), exceptionData, nova_local_0->vtable->nova_standard_NovaString_virtual0_Novaconcat(nova_local_0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "... "))));
	if (!nova_standard_network_NovaServerSocket_Novastart(l1_Novaserver, exceptionData, l0_Novaport))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_0_Novaconcat(nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Unable to start server on port "), exceptionData, nova_standard_primitive_number_NovaInt_static_1_NovatoString(0, exceptionData, l0_Novaport)));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
	return l1_Novaserver;
}

void stabilitytest_NovaNetworkStability_Novathis(stabilitytest_NovaNetworkStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaNetworkStability_Novasuper(stabilitytest_NovaNetworkStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
