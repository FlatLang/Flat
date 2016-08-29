#include <precompiled.h>
#include <nova/network/nova_network_Nova_NetworkInputStream.h>



nova_network_Extension_VTable_NetworkInputStream nova_network_Extension_VTable_NetworkInputStream_val =
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
		(nova_Nova_String*(*)(nova_io_Nova_InputStream*, nova_exception_Nova_ExceptionData*))nova_network_Nova_NetworkInputStream_0_Nova_readString,
		(nova_datastruct_list_Nova_Array*(*)(nova_io_Nova_InputStream*, nova_exception_Nova_ExceptionData*))nova_network_Nova_NetworkInputStream_0_Nova_readBytes,
		0,
		0,
		0,
		0,
	},
	nova_network_Nova_NetworkInputStream_0_Nova_readString,
	nova_network_Nova_NetworkInputStream_0_Nova_readBytes,
};


CCLASS_PRIVATE
(
	nova_network_Nova_ConnectionSocket* nova_network_Nova_NetworkInputStream_Nova_socket;
	
)
void nova_network_Nova_NetworkInputStream_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_network_Nova_NetworkInputStream* nova_network_Nova_NetworkInputStream_Nova_construct(nova_network_Nova_NetworkInputStream* this, nova_exception_Nova_ExceptionData* exceptionData, nova_network_Nova_ConnectionSocket* nova_network_Nova_NetworkInputStream_Nova_socket)
{
	CCLASS_NEW(nova_network_Nova_NetworkInputStream, this);
	this->vtable = &nova_network_Extension_VTable_NetworkInputStream_val;
	nova_network_Nova_NetworkInputStream_Nova_super(this, exceptionData);
	
	{
		nova_network_Nova_NetworkInputStream_Nova_this(this, exceptionData, nova_network_Nova_NetworkInputStream_Nova_socket);
	}
	
	return this;
}

void nova_network_Nova_NetworkInputStream_Nova_destroy(nova_network_Nova_NetworkInputStream** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_network_Nova_ConnectionSocket_Nova_destroy(&(*this)->prv->nova_network_Nova_NetworkInputStream_Nova_socket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_network_Nova_NetworkInputStream_Nova_this(nova_network_Nova_NetworkInputStream* this, nova_exception_Nova_ExceptionData* exceptionData, nova_network_Nova_ConnectionSocket* nova_network_Nova_NetworkInputStream_Nova_socket)
{
	this->prv->nova_network_Nova_NetworkInputStream_Nova_socket = nova_network_Nova_NetworkInputStream_Nova_socket;
}

nova_Nova_String* nova_network_Nova_NetworkInputStream_0_Nova_readString(nova_network_Nova_NetworkInputStream* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_network_Nova_ConnectionSocket_0_Nova_readString(this->prv->nova_network_Nova_NetworkInputStream_Nova_socket, exceptionData);
}

nova_datastruct_list_Nova_Array* nova_network_Nova_NetworkInputStream_0_Nova_readBytes(nova_network_Nova_NetworkInputStream* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Array*)nova_null;
}

void nova_network_Nova_NetworkInputStream_Nova_super(nova_network_Nova_NetworkInputStream* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_network_Nova_NetworkInputStream_Nova_socket = (nova_network_Nova_ConnectionSocket*)nova_null;
}

