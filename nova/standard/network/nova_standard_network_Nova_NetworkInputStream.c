#include <precompiled.h>
#include <nova/standard/network/nova_standard_network_Nova_NetworkInputStream.h>

nova_standard_network_Extension_VTable_NetworkInputStream nova_standard_network_Extension_VTable_NetworkInputStream_val =
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
		(nova_standard_Nova_String*(*)(nova_standard_io_Nova_InputStream*, nova_standard_exception_Nova_ExceptionData*))nova_standard_network_Nova_NetworkInputStream_0_Nova_readString,
		(char*(*)(nova_standard_io_Nova_InputStream*, nova_standard_exception_Nova_ExceptionData*))nova_standard_network_Nova_NetworkInputStream_0_Nova_readBytes,
		0,
		0,
		0,
		0,
	},
	nova_standard_network_Nova_NetworkInputStream_0_Nova_readString,
	nova_standard_network_Nova_NetworkInputStream_0_Nova_readBytes,
};


CCLASS_PRIVATE
(
	nova_standard_network_Nova_ConnectionSocket* nova_standard_network_Nova_NetworkInputStream_Nova_socket;
	
)
void nova_standard_network_Nova_NetworkInputStreamNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_network_Nova_NetworkInputStream* nova_standard_network_Nova_NetworkInputStream_Nova_NetworkInputStream(nova_standard_network_Nova_NetworkInputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_network_Nova_ConnectionSocket* nova_standard_network_Nova_NetworkInputStream_Nova_socket)
{
	CCLASS_NEW(nova_standard_network_Nova_NetworkInputStream, this);
	this->vtable = &nova_standard_network_Extension_VTable_NetworkInputStream_val;
	nova_standard_network_Nova_NetworkInputStream_Nova_super(this, exceptionData);
	
	{
		nova_standard_network_Nova_NetworkInputStream_Nova_this(this, exceptionData, nova_standard_network_Nova_NetworkInputStream_Nova_socket);
	}
	
	return this;
}

void nova_standard_network_Nova_NetworkInputStream_Nova_destroy(nova_standard_network_Nova_NetworkInputStream** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_network_Nova_ConnectionSocket_Nova_destroy(&(*this)->prv->nova_standard_network_Nova_NetworkInputStream_Nova_socket, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_network_Nova_NetworkInputStream_Nova_this(nova_standard_network_Nova_NetworkInputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_network_Nova_ConnectionSocket* nova_standard_network_Nova_NetworkInputStream_Nova_socket)
{
	this->prv->nova_standard_network_Nova_NetworkInputStream_Nova_socket = nova_standard_network_Nova_NetworkInputStream_Nova_socket;
}

nova_standard_Nova_String* nova_standard_network_Nova_NetworkInputStream_0_Nova_readString(nova_standard_network_Nova_NetworkInputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_network_Nova_ConnectionSocket_0_Nova_readString(this->prv->nova_standard_network_Nova_NetworkInputStream_Nova_socket, exceptionData);
}

char* nova_standard_network_Nova_NetworkInputStream_0_Nova_readBytes(nova_standard_network_Nova_NetworkInputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char*)nova_null;
}

void nova_standard_network_Nova_NetworkInputStream_Nova_super(nova_standard_network_Nova_NetworkInputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_network_Nova_NetworkInputStream_Nova_socket = (nova_standard_network_Nova_ConnectionSocket*)nova_null;
}

