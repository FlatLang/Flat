#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_NovaRequestSocket.h>


nova_VTable_nova_standard_network_NovaRequestSocket nova_VTable_nova_standard_network_NovaRequestSocket_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_network_NovaRequestSocketNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_NovaRequestSocket* nova_standard_network_NovaRequestSocket_Novaconstruct(nova_standard_network_NovaRequestSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaInputStream* l0_Novain, nova_standard_io_NovaOutputStream* l0_Novaout)
{
	CCLASS_NEW(nova_standard_network_NovaRequestSocket, this,);
	this->vtable = &nova_VTable_nova_standard_network_NovaRequestSocket_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_network_NovaSocket_Novasuper((nova_standard_network_NovaSocket*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_network_NovaSocket_Novathis((nova_standard_network_NovaSocket*)(this), exceptionData);
	nova_standard_network_NovaRequestSocket_Novasuper(this, 0);
	
	{
		nova_standard_network_NovaRequestSocket_Novathis(this, exceptionData, l0_Novain, l0_Novaout);
	}
	
	return this;
}

void nova_del_RequestSocket(nova_standard_network_NovaRequestSocket** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_InputStream(&(*this)->nova_standard_network_NovaRequestSocket_Novain, exceptionData);
	nova_del_OutputStream(&(*this)->nova_standard_network_NovaRequestSocket_Novaout, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_network_NovaRequestSocket_Novathis(nova_standard_network_NovaRequestSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaInputStream* l0_Novain, nova_standard_io_NovaOutputStream* l0_Novaout)
{
	this->nova_standard_network_NovaRequestSocket_Novain = l0_Novain;
	this->nova_standard_network_NovaRequestSocket_Novaout = l0_Novaout;
}

void nova_standard_network_NovaRequestSocket_Novasuper(nova_standard_network_NovaRequestSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_network_NovaRequestSocket_Novain = (nova_standard_io_NovaInputStream*)nova_null;
	this->nova_standard_network_NovaRequestSocket_Novaout = (nova_standard_io_NovaOutputStream*)nova_null;
}
