#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaClientThread.h>


nova_VTable_stabilitytest_NovaClientThread nova_VTable_stabilitytest_NovaClientThread_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
	stabilitytest_NovaClientThread_0_Novarun,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_NovaThread_Novahandle;
	
	int stabilitytest_NovaClientThread_Novaport;
	stabilitytest_NovaStabilityTest* stabilitytest_NovaClientThread_Novaprogram;
	
)
void stabilitytest_NovaClientThreadNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaClientThread* stabilitytest_NovaClientThread_Novaconstruct(stabilitytest_NovaClientThread* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram, int l0_Novaport)
{
	CCLASS_NEW(stabilitytest_NovaClientThread, this);
	this->vtable = &nova_VTable_stabilitytest_NovaClientThread_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_thread_NovaThread_Novasuper((nova_standard_thread_NovaThread*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_thread_NovaThread_Novathis((nova_standard_thread_NovaThread*)(this), exceptionData);
	stabilitytest_NovaClientThread_Novasuper(this, exceptionData);
	
	{
		stabilitytest_NovaClientThread_Novathis(this, exceptionData, l0_Novaprogram, l0_Novaport);
	}
	
	return this;
}

void nova_del_ClientThread(stabilitytest_NovaClientThread** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_StabilityTest(&(*this)->prv->stabilitytest_NovaClientThread_Novaprogram, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaClientThread_Novathis(stabilitytest_NovaClientThread* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram, int l0_Novaport)
{
	this->prv->stabilitytest_NovaClientThread_Novaprogram = l0_Novaprogram;
	this->prv->stabilitytest_NovaClientThread_Novaport = l0_Novaport;
}

void stabilitytest_NovaClientThread_0_Novarun(stabilitytest_NovaClientThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_network_NovaClientSocket* l1_Novaclient;
	nova_standard_NovaString* l1_Novaip;
	nova_standard_NovaString* l1_Novas;
	nova_standard_NovaString* nova_local_0;
	
	l1_Novaclient = nova_standard_network_NovaClientSocket_0_Novaconstruct(0, exceptionData);
	l1_Novaip = nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "127.0.0.1");
	nova_local_0 = nova_standard_primitive_number_NovaInt_static_1_NovatoString(0, exceptionData, this->prv->stabilitytest_NovaClientThread_Novaport);
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_0_Novaconcat(nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "ClientSocket attempting to connect to "), exceptionData, l1_Novaip->vtable->nova_standard_NovaString_virtual0_Novaconcat(l1_Novaip, exceptionData, nova_standard_NovaString_0_Novaconcat(nova_standard_NovaString_1_Novaconstruct(0, exceptionData, ":"), exceptionData, nova_local_0->vtable->nova_standard_NovaString_virtual0_Novaconcat(nova_local_0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "... "))))));
	if (!nova_standard_network_NovaClientSocket_Novaconnect(l1_Novaclient, exceptionData, l1_Novaip, this->prv->stabilitytest_NovaClientThread_Novaport))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(this->prv->stabilitytest_NovaClientThread_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Failed to connect to localhost server"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Waiting for String from ServerSocket... "));
	l1_Novas = l1_Novaclient->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_virtual0_NovareadString(l1_Novaclient->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain, exceptionData);
	if (l1_Novas->nova_standard_NovaString_Novalength != stabilitytest_NovaNetworkStability_static_Novareceived->nova_standard_NovaString_Novalength || !l1_Novas->vtable->nova_standard_NovaString_virtual_Novaequals(l1_Novas, exceptionData, stabilitytest_NovaNetworkStability_static_Novareceived))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(this->prv->stabilitytest_NovaClientThread_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Client unable to receive the correct message from server"));
	}
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Attempting to send String to ServerSocket... "));
	l1_Novaclient->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout->vtable->nova_standard_io_NovaOutputStream_virtual1_Novawrite(l1_Novaclient->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout, exceptionData, stabilitytest_NovaNetworkStability_static_Novareceived);
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Attempting to close ClientSocket... "));
	if (!nova_standard_network_NovaClientSocket_Novaclose(l1_Novaclient, exceptionData))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(this->prv->stabilitytest_NovaClientThread_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Unable to close Client connection"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
}

void stabilitytest_NovaClientThread_Novasuper(stabilitytest_NovaClientThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->stabilitytest_NovaClientThread_Novaport = 0;
	this->prv->stabilitytest_NovaClientThread_Novaprogram = (stabilitytest_NovaStabilityTest*)nova_null;
}
