#include <precompiled.h>
#include <bank/bank_NovaClientConnectionThread.h>


nova_VTable_bank_NovaClientConnectionThread nova_VTable_bank_NovaClientConnectionThread_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	bank_NovaClientConnectionThread_Novarun,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_NovaThread_Novahandle;
	
	nova_standard_network_NovaConnectionSocket* bank_NovaClientConnectionThread_Novasocket;
	
)
void bank_NovaClientConnectionThreadNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

bank_NovaClientConnectionThread* bank_NovaClientConnectionThread_Novaconstruct(bank_NovaClientConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
	CCLASS_NEW(bank_NovaClientConnectionThread, this);
	this->vtable = &nova_VTable_bank_NovaClientConnectionThread_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_thread_NovaThread_Novasuper((nova_standard_thread_NovaThread*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_thread_NovaThread_Novathis((nova_standard_thread_NovaThread*)(this), exceptionData);
	bank_NovaClientConnectionThread_Novasuper(this, exceptionData);
	
	{
		bank_NovaClientConnectionThread_Novathis(this, exceptionData, l0_Novasocket);
	}
	
	return this;
}

void nova_del_ClientConnectionThread(bank_NovaClientConnectionThread** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ConnectionSocket(&(*this)->prv->bank_NovaClientConnectionThread_Novasocket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void bank_NovaClientConnectionThread_Novathis(bank_NovaClientConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
	this->prv->bank_NovaClientConnectionThread_Novasocket = l0_Novasocket;
}

void bank_NovaClientConnectionThread_Novarun(bank_NovaClientConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	while (1)
	{
		nova_standard_NovaString* l2_Novamessage;
		
		if (!nova_standard_network_NovaConnectionSocket_NovavalidateConnection(this->prv->bank_NovaClientConnectionThread_Novasocket, exceptionData))
		{
			nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Disconnected."));
			break;
		}
		l2_Novamessage = this->prv->bank_NovaClientConnectionThread_Novasocket->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_Novavirtual0_readString(this->prv->bank_NovaClientConnectionThread_Novasocket->nova_standard_network_NovaConnectionSocket_Novain, exceptionData);
		nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, l2_Novamessage);
	}
}

void bank_NovaClientConnectionThread_Novasuper(bank_NovaClientConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->bank_NovaClientConnectionThread_Novasocket = (nova_standard_network_NovaConnectionSocket*)nova_null;
}
