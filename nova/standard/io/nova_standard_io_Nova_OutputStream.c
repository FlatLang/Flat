#include <precompiled.h>
#include <nova/standard/io/nova_standard_io_Nova_OutputStream.h>


nova_standard_io_Extension_VTable_OutputStream nova_standard_io_Extension_VTable_OutputStream_val =
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_io_Nova_OutputStream_1_Nova_write,
	nova_standard_io_Nova_OutputStream_2_Nova_write,
};


CCLASS_PRIVATE
(
	nova_standard_io_Nova_File* nova_standard_io_Nova_OutputStream_Nova_stream;
	
)
void nova_standard_io_Nova_OutputStreamNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_io_Nova_OutputStream* nova_standard_io_Nova_OutputStream_4_Nova_construct(nova_standard_io_Nova_OutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_io_Nova_OutputStream, this);
	this->vtable = &nova_standard_io_Extension_VTable_OutputStream_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_io_Nova_OutputStream_Nova_super(this, exceptionData);
	
	{
		nova_standard_io_Nova_OutputStream_4_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_io_Nova_OutputStream_Nova_destroy(nova_standard_io_Nova_OutputStream** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_io_Nova_File_Nova_destroy(&(*this)->prv->nova_standard_io_Nova_OutputStream_Nova_stream, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

char nova_standard_io_Nova_OutputStream_1_Nova_write(nova_standard_io_Nova_OutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_data){}
char nova_standard_io_Nova_OutputStream_2_Nova_write(nova_standard_io_Nova_OutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data){}
void nova_standard_io_Nova_OutputStream_4_Nova_this(nova_standard_io_Nova_OutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_io_Nova_OutputStream_Nova_super(nova_standard_io_Nova_OutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_io_Nova_OutputStream_Nova_stream = (nova_standard_io_Nova_File*)nova_null;
}

