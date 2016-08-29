#include <precompiled.h>
#include <nova/io/nova_io_Nova_StreamReader.h>



nova_io_Extension_VTable_StreamReader nova_io_Extension_VTable_StreamReader_val =
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
		(nova_Nova_String*(*)(nova_io_Nova_InputStream*, nova_exception_Nova_ExceptionData*))nova_io_Nova_StreamReader_Nova_readString,
		(nova_datastruct_list_Nova_Array*(*)(nova_io_Nova_InputStream*, nova_exception_Nova_ExceptionData*))nova_io_Nova_StreamReader_Nova_readBytes,
		0,
		0,
		0,
		0,
	},
	nova_io_Nova_StreamReader_Nova_readString,
	nova_io_Nova_StreamReader_Nova_readBytes,
};


CCLASS_PRIVATE
(
	nova_io_Nova_File* nova_io_Nova_StreamReader_Nova_file;
	
)
void nova_io_Nova_StreamReader_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_io_Nova_StreamReader* nova_io_Nova_StreamReader_Nova_construct(nova_io_Nova_StreamReader* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_io_Nova_StreamReader_Nova_file)
{
	CCLASS_NEW(nova_io_Nova_StreamReader, this);
	this->vtable = &nova_io_Extension_VTable_StreamReader_val;
	nova_io_Nova_StreamReader_Nova_super(this, exceptionData);
	
	{
		nova_io_Nova_StreamReader_Nova_this(this, exceptionData, nova_io_Nova_StreamReader_Nova_file);
	}
	
	return this;
}

void nova_io_Nova_StreamReader_Nova_destroy(nova_io_Nova_StreamReader** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_io_Nova_File_Nova_destroy(&(*this)->prv->nova_io_Nova_StreamReader_Nova_file, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_io_Nova_StreamReader_Nova_this(nova_io_Nova_StreamReader* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_io_Nova_StreamReader_Nova_file)
{
	this->prv->nova_io_Nova_StreamReader_Nova_file = nova_io_Nova_StreamReader_Nova_file;
}

nova_datastruct_list_Nova_Array* nova_io_Nova_StreamReader_Nova_readBytes(nova_io_Nova_StreamReader* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Array*)nova_null;
}

nova_Nova_String* nova_io_Nova_StreamReader_Nova_readString(nova_io_Nova_StreamReader* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_String* l1_Nova_output = (nova_Nova_String*)nova_null;
	
	l1_Nova_output = nova_io_Nova_File_Nova_readAllContents(this->prv->nova_io_Nova_StreamReader_Nova_file, exceptionData);
	return l1_Nova_output;
}

void nova_io_Nova_StreamReader_Nova_super(nova_io_Nova_StreamReader* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_io_Nova_StreamReader_Nova_file = (nova_io_Nova_File*)nova_null;
}

