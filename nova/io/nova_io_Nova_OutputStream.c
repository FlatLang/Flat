#include <precompiled.h>
#include <nova/io/nova_io_Nova_OutputStream.h>



nova_io_Extension_VTable_OutputStream nova_io_Extension_VTable_OutputStream_val =
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
	nova_io_Nova_OutputStream_virtual0_Nova_write,
	nova_io_Nova_OutputStream_virtual1_Nova_write,
};


CCLASS_PRIVATE
(
	nova_io_Nova_File* nova_io_Nova_OutputStream_Nova_stream;
	
)
void nova_io_Nova_OutputStream_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_io_Nova_OutputStream* nova_io_Nova_OutputStream_Nova_construct(nova_io_Nova_OutputStream* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_io_Nova_OutputStream, this);
	this->vtable = &nova_io_Extension_VTable_OutputStream_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_io_Nova_OutputStream_Nova_super(this, exceptionData);
	
	{
		nova_io_Nova_OutputStream_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_io_Nova_OutputStream_Nova_destroy(nova_io_Nova_OutputStream** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_io_Nova_File_Nova_destroy(&(*this)->prv->nova_io_Nova_OutputStream_Nova_stream, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}



void nova_io_Nova_OutputStream_0_Nova_this(nova_io_Nova_OutputStream* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_io_Nova_OutputStream_Nova_super(nova_io_Nova_OutputStream* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_io_Nova_OutputStream_Nova_stream = (nova_io_Nova_File*)nova_null;
}

char nova_io_Nova_OutputStream_virtual0_Nova_write(nova_io_Nova_OutputStream* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_io_Nova_OutputStream_Nova_data)
{
	return this->vtable->nova_io_Nova_OutputStream_virtual0_Nova_write((nova_io_Nova_OutputStream*)(this), exceptionData, nova_io_Nova_OutputStream_Nova_data);
}

char nova_io_Nova_OutputStream_virtual1_Nova_write(nova_io_Nova_OutputStream* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_io_Nova_OutputStream_Nova_data)
{
	return this->vtable->nova_io_Nova_OutputStream_virtual1_Nova_write((nova_io_Nova_OutputStream*)(this), exceptionData, nova_io_Nova_OutputStream_Nova_data);
}

