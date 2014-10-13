#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_NovaSocket.h>


nova_VTable_nova_standard_network_NovaSocket nova_VTable_nova_standard_network_NovaSocket_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_network_NovaSocketNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_NovaSocket* nova_standard_network_NovaSocket_Nova0_construct(nova_standard_network_NovaSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_network_NovaSocket, this,);
	this->vtable = &nova_VTable_nova_standard_network_NovaSocket_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_network_NovaSocket_Novasuper(this, 0);
	
	{
		nova_standard_network_NovaSocket_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Socket(nova_standard_network_NovaSocket** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_String(&(*this)->nova_standard_network_NovaSocket_Novaip, exceptionData);
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_network_NovaSocket_Novathis(nova_standard_network_NovaSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_network_NovaSocket_Novasuper(nova_standard_network_NovaSocket* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_network_NovaSocket_Novaip = (nova_standard_NovaString*)nova_null;
	this->nova_standard_network_NovaSocket_Novaport = 0;
}
