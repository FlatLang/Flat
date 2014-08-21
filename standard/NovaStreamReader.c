#include <precompiled.h>
#include "NovaStreamReader.h"



CCLASS_PRIVATE
(
	File* nova_InputStream_stream;
	
	File* nova_StreamReader_file;
	
)

StreamReader* nova_1_StreamReader_construct(StreamReader* this, ExceptionData* exceptionData, File* nova_0_file)
{
	CCLASS_NEW(StreamReader, this);
	nova_Object_super((Object*)this, 0);
	nova_InputStream_super((InputStream*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_InputStream_this((InputStream*)(this), exceptionData);
	nova_StreamReader_super(this, 0);
	
	{
		nova_StreamReader_this(this, exceptionData, nova_0_file);
	}
	
	return this;
}

void nova_del_StreamReader(StreamReader** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_File(&(*this)->prv->nova_StreamReader_file, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_StreamReader_this(StreamReader* this, ExceptionData* exceptionData, File* nova_0_file)
{
	this->prv->nova_StreamReader_file = nova_0_file;
}

String* nova_StreamReader_read(StreamReader* this, ExceptionData* exceptionData)
{
	String* nova_1_output;
	
	nova_1_output = nova_File_readAllContents(this->prv->nova_StreamReader_file, exceptionData);
	return nova_1_output;
}

void nova_StreamReader_super(StreamReader* this, ExceptionData* exceptionData)
{
	this->prv->nova_StreamReader_file = (File*)0;
}
