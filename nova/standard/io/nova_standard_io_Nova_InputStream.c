#include <precompiled.h>
#include <nova/standard/io/nova_standard_io_Nova_InputStream.h>


nova_standard_io_Extension_VTable_InputStream nova_standard_io_Extension_VTable_InputStream_val =
{
	{
		0,
		0,
		(nova_standard_Nova_String*(*)(nova_standard_io_Nova_InputStream*, nova_standard_exception_Nova_ExceptionData*))nova_standard_io_Nova_InputStream_0_Nova_readString,
		(char*(*)(nova_standard_io_Nova_InputStream*, nova_standard_exception_Nova_ExceptionData*))nova_standard_io_Nova_InputStream_0_Nova_readBytes,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_io_Nova_InputStream_0_Nova_readString,
	nova_standard_io_Nova_InputStream_0_Nova_readBytes,
};


void nova_standard_io_Nova_InputStreamNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_io_Nova_InputStream* nova_standard_io_Nova_InputStream_1_Nova_construct(nova_standard_io_Nova_InputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_io_Nova_InputStream, this,);
	this->vtable = &nova_standard_io_Extension_VTable_InputStream_val;
	nova_standard_io_Nova_InputStream_Nova_super(this, exceptionData);
	
	{
		nova_standard_io_Nova_InputStream_1_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_io_Nova_InputStream_Nova_destroy(nova_standard_io_Nova_InputStream** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_standard_Nova_String* nova_standard_io_Nova_InputStream_0_Nova_readString(nova_standard_io_Nova_InputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData){}
char* nova_standard_io_Nova_InputStream_0_Nova_readBytes(nova_standard_io_Nova_InputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData){}
void nova_standard_io_Nova_InputStream_1_Nova_this(nova_standard_io_Nova_InputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_io_Nova_InputStream_Nova_super(nova_standard_io_Nova_InputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

