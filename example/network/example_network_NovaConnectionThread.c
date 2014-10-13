#include <precompiled.h>
#include <example/network/example_network_NovaConnectionThread.h>


nova_VTable_example_network_NovaConnectionThread nova_VTable_example_network_NovaConnectionThread_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	example_network_NovaConnectionThread_Novarun,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_NovaThread_Novahandle;
	
	nova_standard_network_NovaConnectionSocket* example_network_NovaConnectionThread_Novasocket;
	
)
void example_network_NovaConnectionThreadNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

example_network_NovaConnectionThread* example_network_NovaConnectionThread_Novaconstruct(example_network_NovaConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
	CCLASS_NEW(example_network_NovaConnectionThread, this);
	this->vtable = &nova_VTable_example_network_NovaConnectionThread_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_thread_NovaThread_Novasuper((nova_standard_thread_NovaThread*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_thread_NovaThread_Novathis((nova_standard_thread_NovaThread*)(this), exceptionData);
	example_network_NovaConnectionThread_Novasuper(this, 0);
	
	{
		example_network_NovaConnectionThread_Novathis(this, exceptionData, l0_Novasocket);
	}
	
	return this;
}

void nova_del_ConnectionThread(example_network_NovaConnectionThread** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ConnectionSocket(&(*this)->prv->example_network_NovaConnectionThread_Novasocket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void example_network_NovaConnectionThread_Novathis(example_network_NovaConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
	this->prv->example_network_NovaConnectionThread_Novasocket = l0_Novasocket;
}

void example_network_NovaConnectionThread_Novarun(example_network_NovaConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	while (1)
	{
		nova_standard_NovaString* l2_Novamessage;
		
		if (!nova_standard_network_NovaConnectionSocket_NovavalidateConnection(this->prv->example_network_NovaConnectionThread_Novasocket, exceptionData))
		{
			nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Disconnected."));
			break;
		}
		l2_Novamessage = this->prv->example_network_NovaConnectionThread_Novasocket->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_Novavirtual0_readString(this->prv->example_network_NovaConnectionThread_Novasocket->nova_standard_network_NovaConnectionSocket_Novain, exceptionData);
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "HE SAYS: "), exceptionData, l2_Novamessage));
	}
}

void example_network_NovaConnectionThread_Novasuper(example_network_NovaConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->example_network_NovaConnectionThread_Novasocket = (nova_standard_network_NovaConnectionSocket*)nova_null;
}
