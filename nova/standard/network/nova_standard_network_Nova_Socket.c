#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_Nova_Socket.h>

nova_standard_network_Extension_VTable_Socket nova_standard_network_Extension_VTable_Socket_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_network_Nova_SocketNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_Nova_Socket* nova_standard_network_Nova_Socket_2_Nova_construct(nova_standard_network_Nova_Socket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_network_Nova_Socket, this,);
	this->vtable = &nova_standard_network_Extension_VTable_Socket_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_network_Nova_Socket_Nova_super(this, exceptionData);
	
	{
		nova_standard_network_Nova_Socket_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_network_Nova_Socket_Nova_destroy(nova_standard_network_Nova_Socket** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_Nova_String_Nova_destroy(&(*this)->nova_standard_network_Nova_Socket_Nova_ip, exceptionData);
	
	
	NOVA_FREE(*this);
}

void nova_standard_network_Nova_Socket_2_Nova_this(nova_standard_network_Nova_Socket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_network_Nova_Socket_Nova_super(nova_standard_network_Nova_Socket* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_network_Nova_Socket_Nova_ip = (nova_standard_Nova_String*)nova_null;
	this->nova_standard_network_Nova_Socket_Nova_port = 0;
}

