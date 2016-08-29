#include <precompiled.h>
#include <nova/network/nova_network_Nova_Socket.h>



nova_network_Extension_VTable_Socket nova_network_Extension_VTable_Socket_val =
{
	{
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
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void nova_network_Nova_Socket_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_network_Nova_Socket* nova_network_Nova_Socket_Nova_construct(nova_network_Nova_Socket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_network_Nova_Socket, this,);
	this->vtable = &nova_network_Extension_VTable_Socket_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_network_Nova_Socket_Nova_super(this, exceptionData);
	
	{
		nova_network_Nova_Socket_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_network_Nova_Socket_Nova_destroy(nova_network_Nova_Socket** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_Nova_String_Nova_destroy(&(*this)->nova_network_Nova_Socket_Nova_ip, exceptionData);
	
	
	NOVA_FREE(*this);
}

void nova_network_Nova_Socket_0_Nova_this(nova_network_Nova_Socket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_network_Nova_Socket_Nova_super(nova_network_Nova_Socket* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_network_Nova_Socket_Nova_ip = (nova_Nova_String*)nova_null;
	this->nova_network_Nova_Socket_Nova_port = 0;
}

