#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaClientThread.h>


nova_VTable_stabilitytest_NovaClientThread nova_VTable_stabilitytest_NovaClientThread_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	stabilitytest_NovaClientThread_Nova0_run,
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
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_thread_NovaThread_Novasuper((nova_standard_thread_NovaThread*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_thread_NovaThread_Novathis((nova_standard_thread_NovaThread*)(this), exceptionData);
	stabilitytest_NovaClientThread_Novasuper(this, 0);
	
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

void stabilitytest_NovaClientThread_Nova0_run(stabilitytest_NovaClientThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_network_NovaClientSocket* l1_Novaclient;
	nova_standard_NovaString* l1_Novas;
	
	l1_Novaclient = nova_standard_network_NovaClientSocket_Nova0_construct(0, exceptionData);
	if (!nova_standard_network_NovaClientSocket_Novaconnect(l1_Novaclient, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "127.0.0.1"), this->prv->stabilitytest_NovaClientThread_Novaport))
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(this->prv->stabilitytest_NovaClientThread_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to connect to localhost server"));
	}
	l1_Novas = l1_Novaclient->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_Novavirtual0_readString(l1_Novaclient->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain, exceptionData);
	if (l1_Novas->nova_standard_NovaString_Novalength != stabilitytest_NovaNetworkStability_static_Novareceived->nova_standard_NovaString_Novalength || !l1_Novas->vtable->nova_standard_NovaString_Novavirtual_equals(l1_Novas, exceptionData, stabilitytest_NovaNetworkStability_static_Novareceived))
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(this->prv->stabilitytest_NovaClientThread_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Client unable to receive the correct message from server"));
	}
	l1_Novaclient->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout->vtable->nova_standard_io_NovaOutputStream_Novavirtual1_write(l1_Novaclient->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout, exceptionData, stabilitytest_NovaNetworkStability_static_Novareceived);
	if (!nova_standard_network_NovaClientSocket_Novaclose(l1_Novaclient, exceptionData))
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(this->prv->stabilitytest_NovaClientThread_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Unable to close Client connection"));
	}
}

void stabilitytest_NovaClientThread_Novasuper(stabilitytest_NovaClientThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->stabilitytest_NovaClientThread_Novaport = 0;
	this->prv->stabilitytest_NovaClientThread_Novaprogram = (stabilitytest_NovaStabilityTest*)nova_null;
}
