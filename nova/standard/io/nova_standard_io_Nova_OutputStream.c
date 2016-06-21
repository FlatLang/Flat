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
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_io_Nova_OutputStream_virtual0_Nova_write,
	nova_standard_io_Nova_OutputStream_virtual1_Nova_write,
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

nova_standard_io_Nova_OutputStream* nova_standard_io_Nova_OutputStream_0_Nova_construct(nova_standard_io_Nova_OutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_io_Nova_OutputStream, this);
	this->vtable = &nova_standard_io_Extension_VTable_OutputStream_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_io_Nova_OutputStream_Nova_super(this, exceptionData);
	
	{
		nova_standard_io_Nova_OutputStream_0_Nova_this(this, exceptionData);
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



void nova_standard_io_Nova_OutputStream_0_Nova_this(nova_standard_io_Nova_OutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_io_Nova_OutputStream_Nova_super(nova_standard_io_Nova_OutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_io_Nova_OutputStream_Nova_stream = (nova_standard_io_Nova_File*)nova_null;
}

char nova_standard_io_Nova_OutputStream_virtual0_Nova_write(nova_standard_io_Nova_OutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_io_Nova_OutputStream_Nova_data)
{
	return this->vtable->nova_standard_io_Nova_OutputStream_virtual0_Nova_write((nova_standard_io_Nova_OutputStream*)(this), exceptionData, nova_standard_io_Nova_OutputStream_Nova_data);
}

char nova_standard_io_Nova_OutputStream_virtual1_Nova_write(nova_standard_io_Nova_OutputStream* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_io_Nova_OutputStream_Nova_data)
{
	return this->vtable->nova_standard_io_Nova_OutputStream_virtual1_Nova_write((nova_standard_io_Nova_OutputStream*)(this), exceptionData, nova_standard_io_Nova_OutputStream_Nova_data);
}

