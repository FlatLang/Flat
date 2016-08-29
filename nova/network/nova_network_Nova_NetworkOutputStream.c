#include <precompiled.h>
#include <nova/network/nova_network_Nova_NetworkOutputStream.h>



nova_network_Extension_VTable_NetworkOutputStream nova_network_Extension_VTable_NetworkOutputStream_val =
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
	nova_network_Nova_NetworkOutputStream_0_Nova_write,
	nova_network_Nova_NetworkOutputStream_1_Nova_write,
};


CCLASS_PRIVATE
(
	nova_io_Nova_File* nova_io_Nova_OutputStream_Nova_stream;
	
	nova_network_Nova_ConnectionSocket* nova_network_Nova_NetworkOutputStream_Nova_socket;
	
)
void nova_network_Nova_NetworkOutputStream_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_network_Nova_NetworkOutputStream* nova_network_Nova_NetworkOutputStream_Nova_construct(nova_network_Nova_NetworkOutputStream* this, nova_exception_Nova_ExceptionData* exceptionData, nova_network_Nova_ConnectionSocket* nova_network_Nova_NetworkOutputStream_Nova_socket)
{
	CCLASS_NEW(nova_network_Nova_NetworkOutputStream, this);
	this->vtable = &nova_network_Extension_VTable_NetworkOutputStream_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_io_Nova_OutputStream_Nova_super((nova_io_Nova_OutputStream*)this, exceptionData);
	nova_network_Nova_NetworkOutputStream_0_Nova_super(this, exceptionData);
	
	{
		nova_network_Nova_NetworkOutputStream_Nova_this(this, exceptionData, nova_network_Nova_NetworkOutputStream_Nova_socket);
	}
	
	return this;
}

void nova_network_Nova_NetworkOutputStream_Nova_destroy(nova_network_Nova_NetworkOutputStream** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_network_Nova_ConnectionSocket_Nova_destroy(&(*this)->prv->nova_network_Nova_NetworkOutputStream_Nova_socket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_network_Nova_NetworkOutputStream_Nova_this(nova_network_Nova_NetworkOutputStream* this, nova_exception_Nova_ExceptionData* exceptionData, nova_network_Nova_ConnectionSocket* nova_network_Nova_NetworkOutputStream_Nova_socket)
{
	this->prv->nova_network_Nova_NetworkOutputStream_Nova_socket = nova_network_Nova_NetworkOutputStream_Nova_socket;
}

char nova_network_Nova_NetworkOutputStream_0_Nova_write(nova_network_Nova_NetworkOutputStream* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_network_Nova_NetworkOutputStream_Nova_data)
{
	return nova_network_Nova_ConnectionSocket_Nova_write(this->prv->nova_network_Nova_NetworkOutputStream_Nova_socket, exceptionData, nova_network_Nova_NetworkOutputStream_Nova_data);
}

char nova_network_Nova_NetworkOutputStream_1_Nova_write(nova_network_Nova_NetworkOutputStream* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_network_Nova_NetworkOutputStream_Nova_data)
{
	return 0;
}

void nova_network_Nova_NetworkOutputStream_0_Nova_super(nova_network_Nova_NetworkOutputStream* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_network_Nova_NetworkOutputStream_Nova_socket = (nova_network_Nova_ConnectionSocket*)nova_null;
}

