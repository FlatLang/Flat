#include <precompiled.h>
#include <nova/io/nova_io_Nova_InputStream.h>



nova_io_Extension_VTable_InputStream nova_io_Extension_VTable_InputStream_val =
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
		0,
		0,
		0,
		0,
	},
	nova_io_Nova_InputStream_virtual1_Nova_readString,
	nova_io_Nova_InputStream_virtual1_Nova_readBytes,
};


void nova_io_Nova_InputStream_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}



nova_Nova_String* nova_io_Nova_InputStream_virtual1_Nova_readString(nova_io_Nova_InputStream* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_io_Nova_InputStream_virtual1_Nova_readString((nova_io_Nova_InputStream*)(this), exceptionData);
}

nova_datastruct_list_Nova_Array* nova_io_Nova_InputStream_virtual1_Nova_readBytes(nova_io_Nova_InputStream* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_io_Nova_InputStream_virtual1_Nova_readBytes((nova_io_Nova_InputStream*)(this), exceptionData);
}

