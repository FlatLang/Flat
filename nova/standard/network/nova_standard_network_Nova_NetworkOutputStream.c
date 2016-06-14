#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_Nova_NetworkOutputStream.h>

nova_standard_network_Extension_VTable_NetworkOutputStream nova_standard_network_Extension_VTable_NetworkOutputStream_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_network_Nova_NetworkOutputStream_0_Nova_write,
	nova_standard_network_Nova_NetworkOutputStream_1_Nova_write,
};


CCLASS_PRIVATE
(
	nova_standard_io_Nova_File* nova_standard_io_Nova_OutputStream_Nova_stream;
	
	nova_standard_network_Nova_ConnectionSocket* nova_standard_network_Nova_NetworkOutputStream_Nova_socket;
	
)
void nova_standard_network_Nova_NetworkOutputStreamNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_Nova_NetworkOutputStream* nova_standard_network_Nova_NetworkOutputStream_Nova_construct(nova_standard_network_Nova_NetworkOutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_network_Nova_ConnectionSocket* nova_standard_network_Nova_NetworkOutputStream_Nova_socket)
{
	CCLASS_NEW(nova_standard_network_Nova_NetworkOutputStream, this);
	this->vtable = &nova_standard_network_Extension_VTable_NetworkOutputStream_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_io_Nova_OutputStream_Nova_super((nova_standard_io_Nova_OutputStream*)this, exceptionData);
	nova_standard_network_Nova_NetworkOutputStream_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_network_Nova_NetworkOutputStream_Nova_this(this, exceptionData, nova_standard_network_Nova_NetworkOutputStream_Nova_socket);
	}
	
	return this;
}

void nova_standard_network_Nova_NetworkOutputStream_Nova_destroy(nova_standard_network_Nova_NetworkOutputStream** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_network_Nova_ConnectionSocket_Nova_destroy(&(*this)->prv->nova_standard_network_Nova_NetworkOutputStream_Nova_socket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_network_Nova_NetworkOutputStream_Nova_this(nova_standard_network_Nova_NetworkOutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_network_Nova_ConnectionSocket* nova_standard_network_Nova_NetworkOutputStream_Nova_socket)
{
	this->prv->nova_standard_network_Nova_NetworkOutputStream_Nova_socket = nova_standard_network_Nova_NetworkOutputStream_Nova_socket;
}

char nova_standard_network_Nova_NetworkOutputStream_0_Nova_write(nova_standard_network_Nova_NetworkOutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_network_Nova_NetworkOutputStream_Nova_data)
{
	return nova_standard_network_Nova_ConnectionSocket_Nova_write(this->prv->nova_standard_network_Nova_NetworkOutputStream_Nova_socket, exceptionData, nova_standard_network_Nova_NetworkOutputStream_Nova_data);
}

char nova_standard_network_Nova_NetworkOutputStream_1_Nova_write(nova_standard_network_Nova_NetworkOutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_network_Nova_NetworkOutputStream_Nova_data)
{
	return 0;
}

void nova_standard_network_Nova_NetworkOutputStream_0_Nova_super(nova_standard_network_Nova_NetworkOutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_network_Nova_NetworkOutputStream_Nova_socket = (nova_standard_network_Nova_ConnectionSocket*)nova_null;
}

