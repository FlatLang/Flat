#include <precompiled.h>
#include <example/network/example_network_NovaOutputThread.h>


nova_VTable_example_network_NovaOutputThread nova_VTable_example_network_NovaOutputThread_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	example_network_NovaOutputThread_Novarun,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_NovaThread_Novahandle;
	
	nova_standard_network_NovaServerSocket* example_network_NovaOutputThread_NovaserverSocket;
	nova_standard_network_NovaConnectionSocket* example_network_NovaOutputThread_Novasocket;
	
)
void example_network_NovaOutputThreadNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

example_network_NovaOutputThread* example_network_NovaOutputThread_Novaconstruct(example_network_NovaOutputThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaServerSocket* l0_NovaserverSocket, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
	CCLASS_NEW(example_network_NovaOutputThread, this);
	this->vtable = &nova_VTable_example_network_NovaOutputThread_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_thread_NovaThread_Novasuper((nova_standard_thread_NovaThread*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_thread_NovaThread_Novathis((nova_standard_thread_NovaThread*)(this), exceptionData);
	example_network_NovaOutputThread_Novasuper(this, 0);
	
	{
		example_network_NovaOutputThread_Novathis(this, exceptionData, l0_NovaserverSocket, l0_Novasocket);
	}
	
	return this;
}

void nova_del_OutputThread(example_network_NovaOutputThread** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ServerSocket(&(*this)->prv->example_network_NovaOutputThread_NovaserverSocket, exceptionData);
	nova_del_ConnectionSocket(&(*this)->prv->example_network_NovaOutputThread_Novasocket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void example_network_NovaOutputThread_Novathis(example_network_NovaOutputThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaServerSocket* l0_NovaserverSocket, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
	this->prv->example_network_NovaOutputThread_NovaserverSocket = l0_NovaserverSocket;
	this->prv->example_network_NovaOutputThread_Novasocket = l0_Novasocket;
}

void example_network_NovaOutputThread_Novarun(example_network_NovaOutputThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	while (this->prv->example_network_NovaOutputThread_Novasocket->nova_standard_network_NovaConnectionSocket_Novaconnected)
	{
		nova_standard_NovaString* l2_Novamessage;
		
		l2_Novamessage = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
		if (l2_Novamessage->vtable->nova_standard_NovaString_Novavirtual_equals(l2_Novamessage, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "q")))
		{
			nova_standard_network_NovaServerSocket_Novaclose(this->prv->example_network_NovaOutputThread_NovaserverSocket, exceptionData);
			break;
		}
		if (!this->prv->example_network_NovaOutputThread_Novasocket->nova_standard_network_NovaConnectionSocket_Novaconnected)
		{
			break;
		}
		this->prv->example_network_NovaOutputThread_Novasocket->nova_standard_network_NovaConnectionSocket_Novaout->vtable->nova_standard_io_NovaOutputStream_Novavirtual1_write(this->prv->example_network_NovaOutputThread_Novasocket->nova_standard_network_NovaConnectionSocket_Novaout, exceptionData, l2_Novamessage);
	}
}

void example_network_NovaOutputThread_Novasuper(example_network_NovaOutputThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->example_network_NovaOutputThread_NovaserverSocket = (nova_standard_network_NovaServerSocket*)nova_null;
	this->prv->example_network_NovaOutputThread_Novasocket = (nova_standard_network_NovaConnectionSocket*)nova_null;
}
