#include <precompiled.h>
#include <nova/standard/nova_standard_NovaStreamReader.h>


nova_VTable_nova_standard_NovaStreamReader nova_VTable_nova_standard_NovaStreamReader_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_NovaFile* nova_standard_NovaInputStream_Novastream;
	
	nova_standard_NovaFile* nova_standard_NovaStreamReader_Novafile;
	
)

nova_standard_NovaStreamReader* nova_standard_NovaStreamReader_Novanull1_construct(nova_standard_NovaStreamReader* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaFile* l0_Novafile)
{
	CCLASS_NEW(nova_standard_NovaStreamReader, this);
	this->vtable = &nova_VTable_nova_standard_NovaStreamReader_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaInputStream_Novasuper((nova_standard_NovaInputStream*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaInputStream_Novathis((nova_standard_NovaInputStream*)(this), exceptionData);
	nova_standard_NovaStreamReader_Novasuper(this, 0);
	
	{
		nova_standard_NovaStreamReader_Novathis(this, exceptionData, l0_Novafile);
	}
	
	return this;
}

void nova_del_StreamReader(nova_standard_NovaStreamReader** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_File(&(*this)->prv->nova_standard_NovaStreamReader_Novafile, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaStreamReader_Novathis(nova_standard_NovaStreamReader* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaFile* l0_Novafile)
{
	this->prv->nova_standard_NovaStreamReader_Novafile = l0_Novafile;
}

nova_standard_NovaString* nova_standard_NovaStreamReader_Novaread(nova_standard_NovaStreamReader* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaString* l1_Novaoutput;
	
	l1_Novaoutput = nova_standard_NovaFile_NovareadAllContents(this->prv->nova_standard_NovaStreamReader_Novafile, exceptionData);
	return l1_Novaoutput;
}

void nova_standard_NovaStreamReader_Novasuper(nova_standard_NovaStreamReader* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_NovaStreamReader_Novafile = (nova_standard_NovaFile*)nova_null;
}
