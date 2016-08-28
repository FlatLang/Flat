#include <precompiled.h>
#include <bank/bank_NovaServerOutputThread.h>


nova_VTable_bank_NovaServerOutputThread nova_VTable_bank_NovaServerOutputThread_val =
{
	nova_NovaObject_Nova0_getHashCodeLong,
	nova_NovaObject_Nova0_toString,
	nova_NovaObject_Nova0_equals,
	bank_NovaServerOutputThread_Nova0_run,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_thread_NovaThread_Novahandle;
	
	nova_network_NovaServerSocket* bank_NovaServerOutputThread_Novasocket;
	
)
void bank_NovaServerOutputThreadNova_init_static(nova_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

bank_NovaServerOutputThread* bank_NovaServerOutputThread_Novaconstruct(bank_NovaServerOutputThread* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaServerSocket* l0_Novasocket)
{
	CCLASS_NEW(bank_NovaServerOutputThread, this);
	this->vtable = &nova_VTable_bank_NovaServerOutputThread_val;
	nova_NovaObject_Novasuper((nova_NovaObject*)this, exceptionData);
	nova_thread_NovaThread_Novasuper((nova_thread_NovaThread*)this, exceptionData);
	nova_NovaObject_Novathis((nova_NovaObject*)(this), exceptionData);
	nova_thread_NovaThread_Novathis((nova_thread_NovaThread*)(this), exceptionData);
	bank_NovaServerOutputThread_Novasuper(this, exceptionData);
	
	{
		bank_NovaServerOutputThread_Novathis(this, exceptionData, l0_Novasocket);
	}
	
	return this;
}

void nova_del_ServerOutputThread(bank_NovaServerOutputThread** this, nova_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ServerSocket(&(*this)->prv->bank_NovaServerOutputThread_Novasocket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void bank_NovaServerOutputThread_Novathis(bank_NovaServerOutputThread* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaServerSocket* l0_Novasocket)
{
	this->prv->bank_NovaServerOutputThread_Novasocket = l0_Novasocket;
}

void bank_NovaServerOutputThread_Nova0_run(bank_NovaServerOutputThread* this, nova_exception_NovaExceptionData* exceptionData)
{
	nova_network_NovaConnectionSocket* l1_Novarequest;
	
	l1_Novarequest = nova_network_NovaServerSocket_NovaacceptClient(this->prv->bank_NovaServerOutputThread_Novasocket, exceptionData);
	if (l1_Novarequest == (nova_network_NovaConnectionSocket*)nova_null)
	{
		nova_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_NovaString_Nova1_construct(0, exceptionData, "Failed to accept client"));
	}
	while (l1_Novarequest != (nova_network_NovaConnectionSocket*)nova_null && this->prv->bank_NovaServerOutputThread_Novasocket->nova_network_NovaServerSocket_Novaopen)
	{
		bank_NovaBank* l3_Novabank;
		bank_NovaConnectionThread* l3_Novathread;
		
		nova_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_NovaString_Nova1_construct(0, exceptionData, "Accepted new client"));
		l3_Novabank = bank_NovaBank_Nova1_construct(0, exceptionData, l1_Novarequest);
		l3_Novathread = bank_NovaConnectionThread_Novaconstruct(0, exceptionData, l3_Novabank, l1_Novarequest);
		nova_thread_NovaThread_Novastart((nova_thread_NovaThread*)(l3_Novathread), exceptionData);
		l1_Novarequest = nova_network_NovaServerSocket_NovaacceptClient(this->prv->bank_NovaServerOutputThread_Novasocket, exceptionData);
	}
	nova_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_NovaString_Nova1_construct(0, exceptionData, "Done"));
}

void bank_NovaServerOutputThread_Novasuper(bank_NovaServerOutputThread* this, nova_exception_NovaExceptionData* exceptionData)
{
	this->prv->bank_NovaServerOutputThread_Novasocket = (nova_network_NovaServerSocket*)nova_null;
}
