#include <precompiled.h>
#include <bank/bank_NovaClientInputThread.h>


nova_VTable_bank_NovaClientInputThread nova_VTable_bank_NovaClientInputThread_val =
{
	nova_NovaObject_Nova0_getHashCodeLong,
	nova_NovaObject_Nova0_toString,
	nova_NovaObject_Nova0_equals,
	bank_NovaClientInputThread_Nova0_run,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_thread_NovaThread_Novahandle;
	
	nova_network_NovaConnectionSocket* bank_NovaClientInputThread_Novasocket;
	
)
void bank_NovaClientInputThreadNova_init_static(nova_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

bank_NovaClientInputThread* bank_NovaClientInputThread_Novaconstruct(bank_NovaClientInputThread* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaConnectionSocket* l0_Novasocket)
{
	CCLASS_NEW(bank_NovaClientInputThread, this);
	this->vtable = &nova_VTable_bank_NovaClientInputThread_val;
	nova_NovaObject_Novasuper((nova_NovaObject*)this, exceptionData);
	nova_thread_NovaThread_Novasuper((nova_thread_NovaThread*)this, exceptionData);
	nova_NovaObject_Novathis((nova_NovaObject*)(this), exceptionData);
	nova_thread_NovaThread_Novathis((nova_thread_NovaThread*)(this), exceptionData);
	bank_NovaClientInputThread_Novasuper(this, exceptionData);
	
	{
		bank_NovaClientInputThread_Novathis(this, exceptionData, l0_Novasocket);
	}
	
	return this;
}

void nova_del_ClientInputThread(bank_NovaClientInputThread** this, nova_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ConnectionSocket(&(*this)->prv->bank_NovaClientInputThread_Novasocket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void bank_NovaClientInputThread_Novathis(bank_NovaClientInputThread* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaConnectionSocket* l0_Novasocket)
{
	this->prv->bank_NovaClientInputThread_Novasocket = l0_Novasocket;
}

void bank_NovaClientInputThread_Nova0_run(bank_NovaClientInputThread* this, nova_exception_NovaExceptionData* exceptionData)
{
	nova_NovaString* l1_Novausername;
	nova_NovaString* l1_Novapassword;
	
	l1_Novausername = nova_io_NovaConsole_static_NovareadLine(0, exceptionData);
	this->prv->bank_NovaClientInputThread_Novasocket->nova_network_NovaConnectionSocket_Novaout->vtable->nova_io_NovaOutputStream_Novavirtual1_write(this->prv->bank_NovaClientInputThread_Novasocket->nova_network_NovaConnectionSocket_Novaout, exceptionData, l1_Novausername);
	l1_Novapassword = nova_security_NovaMD5_static_Novaencrypt(0, exceptionData, nova_io_NovaConsole_static_NovareadPassword(0, exceptionData));
	this->prv->bank_NovaClientInputThread_Novasocket->nova_network_NovaConnectionSocket_Novaout->vtable->nova_io_NovaOutputStream_Novavirtual1_write(this->prv->bank_NovaClientInputThread_Novasocket->nova_network_NovaConnectionSocket_Novaout, exceptionData, l1_Novapassword);
	while (this->prv->bank_NovaClientInputThread_Novasocket->nova_network_NovaConnectionSocket_Novaconnected)
	{
		nova_NovaString* l2_Novainput;
		
		l2_Novainput = nova_io_NovaConsole_static_NovareadLine(0, exceptionData);
		this->prv->bank_NovaClientInputThread_Novasocket->nova_network_NovaConnectionSocket_Novaout->vtable->nova_io_NovaOutputStream_Novavirtual1_write(this->prv->bank_NovaClientInputThread_Novasocket->nova_network_NovaConnectionSocket_Novaout, exceptionData, l2_Novainput);
	}
}

void bank_NovaClientInputThread_Novasuper(bank_NovaClientInputThread* this, nova_exception_NovaExceptionData* exceptionData)
{
	this->prv->bank_NovaClientInputThread_Novasocket = (nova_network_NovaConnectionSocket*)nova_null;
}
