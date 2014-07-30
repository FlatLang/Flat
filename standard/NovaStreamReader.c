#include <precompiled.h>
#include "NovaStreamReader.h"



CCLASS_PRIVATE
(
	File* nova_InputStream_stream;
	
	File* nova_StreamReader_file;
	
)

StreamReader* nova_StreamReader_StreamReader(StreamReader* this, ExceptionData* exceptionData, File* nova_0_file)
{
	CCLASS_NEW(StreamReader, this);
	
	this->prv->nova_StreamReader_file = (File*)0;
	{
		this->prv->nova_StreamReader_file = nova_0_file;
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

String* nova_StreamReader_read(StreamReader* this, ExceptionData* exceptionData)
{
	String* nova_1_output;
	
	nova_1_output = nova_File_readAllContents(this->prv->nova_StreamReader_file, exceptionData);
	return nova_1_output;
}
