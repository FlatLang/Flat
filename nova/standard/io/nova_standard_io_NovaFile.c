#include <precompiled.h>
#include <nova/standard/io/nova_standard_io_NovaFile.h>


nova_VTable_nova_standard_io_NovaFile nova_VTable_nova_standard_io_NovaFile_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	FILE* nova_standard_io_NovaFile_Novafp;
	nova_standard_NovaString* nova_standard_io_NovaFile_Novalocation;
	
)
void nova_standard_io_NovaFileNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_io_NovaFile* nova_standard_io_NovaFile_Nova1_construct(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novalocation)
{
	CCLASS_NEW(nova_standard_io_NovaFile, this);
	this->vtable = &nova_VTable_nova_standard_io_NovaFile_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_io_NovaFile_Novasuper(this, 0);
	
	{
		nova_standard_io_NovaFile_Nova0_this(this, exceptionData, l0_Novalocation);
	}
	
	return this;
}

nova_standard_io_NovaFile* nova_standard_io_NovaFile_Nova2_construct(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, FILE* l0_Novafp)
{
	CCLASS_NEW(nova_standard_io_NovaFile, this);
	this->vtable = &nova_VTable_nova_standard_io_NovaFile_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_io_NovaFile_Novasuper(this, 0);
	
	{
		nova_standard_io_NovaFile_Nova1_this(this, exceptionData, l0_Novafp);
	}
	
	return this;
}

void nova_del_File(nova_standard_io_NovaFile** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_String(&(*this)->prv->nova_standard_io_NovaFile_Novalocation, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_io_NovaFile_Nova0_this(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novalocation)
{
	this->prv->nova_standard_io_NovaFile_Novalocation = l0_Novalocation;
	this->prv->nova_standard_io_NovaFile_Novafp = fopen(nova_standard_NovaString_NovatoCharArray(l0_Novalocation, exceptionData), "r+");
}

void nova_standard_io_NovaFile_Nova1_this(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, FILE* l0_Novafp)
{
	this->prv->nova_standard_io_NovaFile_Novafp = l0_Novafp;
}

char nova_standard_io_NovaFile_Novadelete(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_io_NovaFile_Novaclose(this, exceptionData);
	return remove(nova_standard_NovaString_NovatoCharArray(this->prv->nova_standard_io_NovaFile_Novalocation, exceptionData)) == 0;
}

void nova_standard_io_NovaFile_Novareopen(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_io_NovaFile_Novaclose(this, exceptionData);
	this->prv->nova_standard_io_NovaFile_Novafp = fopen(nova_standard_NovaString_NovatoCharArray(this->prv->nova_standard_io_NovaFile_Novalocation, exceptionData), "r+");
}

void nova_standard_io_NovaFile_Novarewind(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	rewind(this->prv->nova_standard_io_NovaFile_Novafp);
}

char nova_standard_io_NovaFile_Novaexists(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->prv->nova_standard_io_NovaFile_Novafp != 0;
}

void nova_standard_io_NovaFile_NovaclearContents(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (nova_standard_io_NovaFile_Novaexists(this, exceptionData))
	{
		this->prv->nova_standard_io_NovaFile_Novafp = fopen(nova_standard_NovaString_NovatoCharArray(this->prv->nova_standard_io_NovaFile_Novalocation, exceptionData), "w");
	}
}

char nova_standard_io_NovaFile_Novacreate(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!nova_standard_io_NovaFile_Novaexists(this, exceptionData))
	{
		this->prv->nova_standard_io_NovaFile_Novafp = fopen(nova_standard_NovaString_NovatoCharArray(this->prv->nova_standard_io_NovaFile_Novalocation, exceptionData), "w");
		if (!nova_standard_io_NovaFile_Novaexists(this, exceptionData))
		{
			perror("fopen");
			return 0;
		}
		nova_standard_io_NovaFile_Novareopen(this, exceptionData);
		if (!nova_standard_io_NovaFile_Novaexists(this, exceptionData))
		{
			perror("fopen");
			return 0;
		}
		return 1;
	}
	return 0;
}

nova_standard_NovaString* nova_standard_io_NovaFile_NovareadAllContents(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaString* l1_Novadata;
	nova_standard_NovaString* l1_Novaline;
	
	l1_Novadata = nova_standard_NovaString_Novaconstruct(0, exceptionData, "");
	l1_Novaline = nova_standard_io_NovaFile_NovareadLine(this, exceptionData);
	while (l1_Novaline != (nova_standard_NovaString*)nova_null)
	{
		if (l1_Novadata->nova_standard_NovaString_Novalength > 0)
		{
			l1_Novadata = l1_Novadata->vtable->nova_standard_NovaString_Novavirtual0_concat(l1_Novadata, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "\n"));
		}
		l1_Novadata = l1_Novadata->vtable->nova_standard_NovaString_Novavirtual0_concat(l1_Novadata, exceptionData, l1_Novaline);
		l1_Novaline = nova_standard_io_NovaFile_NovareadLine(this, exceptionData);
	}
	return l1_Novadata;
}

nova_standard_NovaString* nova_standard_io_NovaFile_NovareadLine(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	int l1_Novabuf;
	int l1_Novasize;
	char* l1_Novaline;
	char l1_Novac;
	int l1_Novaindex;
	
	l1_Novabuf = 5;
	l1_Novasize = l1_Novabuf;
	l1_Novaline = (char*)NOVA_MALLOC(sizeof(char[l1_Novasize]));
	l1_Novac = getc(this->prv->nova_standard_io_NovaFile_Novafp);
	if (l1_Novac == EOF)
	{
		return (nova_standard_NovaString*)nova_null;
	}
	l1_Novaindex = 0;
	while (l1_Novac != '\n' && l1_Novac != EOF)
	{
		if (l1_Novaindex >= l1_Novasize)
		{
			l1_Novasize = l1_Novasize + l1_Novabuf;
			l1_Novaline = realloc(l1_Novaline, l1_Novasize);
		}
		l1_Novaline[l1_Novaindex++] = l1_Novac;
		l1_Novac = getc(this->prv->nova_standard_io_NovaFile_Novafp);
	}
	if (l1_Novaindex >= l1_Novasize)
	{
		l1_Novasize++;
		l1_Novaline = realloc(l1_Novaline, l1_Novasize);
	}
	l1_Novaline[l1_Novaindex++] = '\0';
	l1_Novaline = realloc(l1_Novaline, l1_Novaindex);
	return nova_standard_NovaString_Novaconstruct(0, exceptionData, l1_Novaline);
}

void nova_standard_io_NovaFile_NovawriteLine(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaline)
{
	nova_standard_io_NovaFile_Novawrite(this, exceptionData, l0_Novaline->vtable->nova_standard_NovaString_Novavirtual0_concat(l0_Novaline, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "\n")));
}

void nova_standard_io_NovaFile_Novawrite(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novadata)
{
	fputs(nova_standard_NovaString_NovatoCharArray(l0_Novadata, exceptionData), this->prv->nova_standard_io_NovaFile_Novafp);
	nova_standard_io_NovaFile_Novaflush(this, exceptionData);
}

void nova_standard_io_NovaFile_Novaflush(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	fflush(this->prv->nova_standard_io_NovaFile_Novafp);
}

void nova_standard_io_NovaFile_Novaclose(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (nova_standard_io_NovaFile_Novaexists(this, exceptionData))
	{
		fclose(this->prv->nova_standard_io_NovaFile_Novafp);
	}
}

int nova_standard_io_NovaFile_static_NovagetMaxOpenFiles(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return getMaxOpenFiles();
}

void nova_standard_io_NovaFile_static_NovasetMaxOpenFiles(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novanum)
{
	short l1_Novamin;
	short l1_Novamax;
	
	l1_Novamin = (short)20;
	l1_Novamax = (short)2048;
	if (l0_Novanum > l1_Novamax || l0_Novanum < l1_Novamin)
	{
		nova_standard_NovaString* nova_local_0;
		nova_standard_NovaString* nova_local_1;
		
		nova_local_0 = nova_standard_primitive_number_NovaInt_Nova2_toString(nova_standard_primitive_number_NovaInt_Novaconstruct(0, exceptionData, l0_Novanum), exceptionData);
		nova_local_1 = nova_standard_primitive_number_NovaShort_Nova2_toString(nova_standard_primitive_number_NovaShort_Novaconstruct(0, exceptionData, l1_Novamin), exceptionData);
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "Invalid max number of open files: "), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "\nValid values include "), exceptionData, nova_local_1->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_1, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "-"), exceptionData, nova_standard_primitive_number_NovaShort_Nova2_toString(nova_standard_primitive_number_NovaShort_Novaconstruct(0, exceptionData, l1_Novamax), exceptionData)))))));
	}
	else
	{
		setMaxOpenFiles(l0_Novanum);
	}
}

void nova_standard_io_NovaFile_Novasuper(nova_standard_io_NovaFile* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_io_NovaFile_Novafp = (FILE*)nova_null;
	this->prv->nova_standard_io_NovaFile_Novalocation = (nova_standard_NovaString*)nova_null;
}
