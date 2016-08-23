#include <precompiled.h>
#include <bank/bank_NovaConnectionThread.h>


nova_VTable_bank_NovaConnectionThread nova_VTable_bank_NovaConnectionThread_val =
{
	nova_NovaObject_Nova0_getHashCodeLong,
	nova_NovaObject_Nova0_toString,
	nova_NovaObject_Nova0_equals,
	bank_NovaConnectionThread_Novarun,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_thread_NovaThread_Novahandle;
	
	nova_network_NovaConnectionSocket* bank_NovaConnectionThread_Novasocket;
	bank_NovaBank* bank_NovaConnectionThread_Novabank;
	
)
void bank_NovaConnectionThreadNova_init_static(nova_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

bank_NovaConnectionThread* bank_NovaConnectionThread_Novaconstruct(bank_NovaConnectionThread* this, nova_exception_NovaExceptionData* exceptionData, bank_NovaBank* l0_Novabank, nova_network_NovaConnectionSocket* l0_Novasocket)
{
	CCLASS_NEW(bank_NovaConnectionThread, this);
	this->vtable = &nova_VTable_bank_NovaConnectionThread_val;
	nova_NovaObject_Novasuper((nova_NovaObject*)this, exceptionData);
	nova_thread_NovaThread_Novasuper((nova_thread_NovaThread*)this, exceptionData);
	nova_NovaObject_Novathis((nova_NovaObject*)(this), exceptionData);
	nova_thread_NovaThread_Novathis((nova_thread_NovaThread*)(this), exceptionData);
	bank_NovaConnectionThread_Novasuper(this, exceptionData);
	
	{
		bank_NovaConnectionThread_Novathis(this, exceptionData, l0_Novabank, l0_Novasocket);
	}
	
	return this;
}

void nova_del_ConnectionThread(bank_NovaConnectionThread** this, nova_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ConnectionSocket(&(*this)->prv->bank_NovaConnectionThread_Novasocket, exceptionData);
	nova_del_Bank(&(*this)->prv->bank_NovaConnectionThread_Novabank, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void bank_NovaConnectionThread_Novathis(bank_NovaConnectionThread* this, nova_exception_NovaExceptionData* exceptionData, bank_NovaBank* l0_Novabank, nova_network_NovaConnectionSocket* l0_Novasocket)
{
	this->prv->bank_NovaConnectionThread_Novasocket = l0_Novasocket;
	this->prv->bank_NovaConnectionThread_Novabank = l0_Novabank;
}

void bank_NovaConnectionThread_Novarun(bank_NovaConnectionThread* this, nova_exception_NovaExceptionData* exceptionData)
{
	bank_NovaBank_NovarunRemote(this->prv->bank_NovaConnectionThread_Novabank, exceptionData, this->prv->bank_NovaConnectionThread_Novasocket);
	nova_network_NovaConnectionSocket_Novaclose(this->prv->bank_NovaConnectionThread_Novasocket, exceptionData);
}

void bank_NovaConnectionThread_Novasuper(bank_NovaConnectionThread* this, nova_exception_NovaExceptionData* exceptionData)
{
	this->prv->bank_NovaConnectionThread_Novasocket = (nova_network_NovaConnectionSocket*)nova_null;
	this->prv->bank_NovaConnectionThread_Novabank = (bank_NovaBank*)nova_null;
}
