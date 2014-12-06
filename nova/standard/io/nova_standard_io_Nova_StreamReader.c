#include <precompiled.h>
#include <nova/standard/io/nova_standard_io_Nova_StreamReader.h>


nova_VTable_nova_standard_io_Nova_StreamReader nova_VTable_nova_standard_io_Nova_StreamReader_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_io_Nova_StreamReader_0_Nova_readString,
	nova_standard_io_Nova_StreamReader_0_Nova_readBytes,
};
CCLASS_PRIVATE
(
	nova_standard_io_Nova_File* nova_standard_io_Nova_StreamReader_Nova_file;
	
)
void nova_standard_io_Nova_StreamReaderNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_io_Nova_StreamReader* nova_standard_io_Nova_StreamReader_Nova_construct(nova_standard_io_Nova_StreamReader* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_file)
{
	CCLASS_NEW(nova_standard_io_Nova_StreamReader, this);
	this->vtable = &nova_VTable_nova_standard_io_Nova_StreamReader_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_io_Nova_InputStream_Nova_super((nova_standard_io_Nova_InputStream*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_io_Nova_InputStream_Nova_this((nova_standard_io_Nova_InputStream*)(this), exceptionData);
	nova_standard_io_Nova_StreamReader_Nova_super(this, exceptionData);
	
	{
		nova_standard_io_Nova_StreamReader_Nova_this(this, exceptionData, l0_Nova_file);
	}
	
	return this;
}

void nova_standard_io_Nova_StreamReader_Nova_destroy(nova_standard_io_Nova_StreamReader** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_io_Nova_File_Nova_destroy(&(*this)->prv->nova_standard_io_Nova_StreamReader_Nova_file, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_io_Nova_StreamReader_Nova_this(nova_standard_io_Nova_StreamReader* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_file)
{
	this->prv->nova_standard_io_Nova_StreamReader_Nova_file = l0_Nova_file;
}

char* nova_standard_io_Nova_StreamReader_0_Nova_readBytes(nova_standard_io_Nova_StreamReader* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char*)nova_null;
}

nova_standard_Nova_String* nova_standard_io_Nova_StreamReader_0_Nova_readString(nova_standard_io_Nova_StreamReader* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_output;
	
	l1_Nova_output = nova_standard_io_Nova_File_Nova_readAllContents(this->prv->nova_standard_io_Nova_StreamReader_Nova_file, exceptionData);
	return l1_Nova_output;
}

void nova_standard_io_Nova_StreamReader_Nova_super(nova_standard_io_Nova_StreamReader* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_io_Nova_StreamReader_Nova_file = (nova_standard_io_Nova_File*)nova_null;
}

