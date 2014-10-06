#include <precompiled.h>
#include <nova/standard/nova_standard_NovaString.h>


nova_VTable_nova_standard_NovaString nova_VTable_nova_standard_NovaString_val =
{
	nova_standard_NovaString_Nova0_toString,
	nova_standard_NovaString_Novaequals,
	nova_standard_NovaString_Nova0_concat,
};
CCLASS_PRIVATE
(
	char* nova_standard_NovaString_Novadata;
	
)

int nova_standard_NovaString_NovacalculateLength(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData);
int nova_standard_NovaString_Nova0_indexOf(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasearch, int l0_Novastart, int l0_Novadirection);
char nova_standard_NovaString_static_NovacontainsChar(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novaneedle, char* l0_NovaChars, int l0_Novalength);
int nova_standard_NovaString_static_NovawhitespaceLength;
char* nova_standard_NovaString_static_Novawhitespace;
void nova_standard_NovaStringNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
		nova_standard_NovaString_static_NovawhitespaceLength = 4;
		nova_standard_NovaString_static_Novawhitespace = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_NovaChar[4]));
		nova_standard_NovaString_static_Novawhitespace[0] = ' ';
		nova_standard_NovaString_static_Novawhitespace[1] = '\t';
		nova_standard_NovaString_static_Novawhitespace[2] = '\n';
		nova_standard_NovaString_static_Novawhitespace[3] = '\r';
	}
}

nova_standard_NovaString* nova_standard_NovaString_Nova0_construct(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac)
{
	CCLASS_NEW(nova_standard_NovaString, this);
	this->vtable = &nova_VTable_nova_standard_NovaString_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaString_Novasuper(this, 0);
	
	{
		nova_standard_NovaString_Nova0_this(this, exceptionData, l0_Novac);
	}
	
	return this;
}

nova_standard_NovaString* nova_standard_NovaString_Nova1_construct(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novadata)
{
	CCLASS_NEW(nova_standard_NovaString, this);
	this->vtable = &nova_VTable_nova_standard_NovaString_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaString_Novasuper(this, 0);
	
	{
		nova_standard_NovaString_Nova1_this(this, exceptionData, l0_Novadata);
	}
	
	return this;
}

void nova_del_String(nova_standard_NovaString** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaString_Nova0_this(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac)
{
	char* l2_Novadata;
	
	l2_Novadata = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_NovaChar[2]));
	l2_Novadata[0] = l0_Novac;
	l2_Novadata[1] = '\0';
	nova_standard_NovaString_Nova1_this(this, exceptionData, l2_Novadata);
}

void nova_standard_NovaString_Nova1_this(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novadata)
{
	this->prv->nova_standard_NovaString_Novadata = l0_Novadata;
	this->nova_standard_NovaString_Novalength = nova_standard_NovaString_NovacalculateLength(this, exceptionData);
}

int nova_standard_NovaString_NovacalculateLength(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return strlen((char*)(this->prv->nova_standard_NovaString_Novadata));
}

char* nova_standard_NovaString_NovatoCharArray(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->prv->nova_standard_NovaString_Novadata;
}

nova_standard_NovaString* nova_standard_NovaString_Nova0_concat(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastr)
{
	int l1_Novasize;
	char* l1_NovanewData;
	nova_standard_NovaString* l1_NovanewStr;
	
	l0_Novastr = l0_Novastr->vtable->nova_standard_NovaString_Novavirtual0_toString(l0_Novastr, exceptionData);
	l1_Novasize = l0_Novastr->nova_standard_NovaString_Novalength + this->nova_standard_NovaString_Novalength + 1;
	l1_NovanewData = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_NovaChar[l1_Novasize]));
	strcpy((char*)(l1_NovanewData), (char*)(this->prv->nova_standard_NovaString_Novadata));
	strcat((char*)(l1_NovanewData), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novastr, exceptionData)));
	l1_NovanewData[l1_Novasize - 1] = '\0';
	l1_NovanewStr = nova_standard_NovaString_Nova1_construct(0, exceptionData, l1_NovanewData);
	return l1_NovanewStr;
}

char nova_standard_NovaString_Novaequals(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaother)
{
	int l3_Novai;
	
	if (this->nova_standard_NovaString_Novalength != l0_Novaother->nova_standard_NovaString_Novalength)
	{
		return 0;
	}
	l3_Novai = 0;
	for (; l3_Novai < this->nova_standard_NovaString_Novalength; l3_Novai++)
	{
		if (this->prv->nova_standard_NovaString_Novadata[l3_Novai] != l0_Novaother->prv->nova_standard_NovaString_Novadata[l3_Novai])
		{
			return 0;
		}
	}
	return 1;
}

int nova_standard_NovaString_Nova0_indexOf(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasearch, int l0_Novastart, int l0_Novadirection)
{
	int l2_Novai;
	
	l2_Novai = l0_Novastart;
	for (; l2_Novai < this->nova_standard_NovaString_Novalength && l2_Novai >= 0; l2_Novai = l2_Novai + l0_Novadirection)
	{
		char l2_Novafound;
		int l3_Novaj;
		
		l2_Novafound = 1;
		l3_Novaj = 0;
		for (; l3_Novaj < l0_Novasearch->nova_standard_NovaString_Novalength && l2_Novai + l3_Novaj < this->nova_standard_NovaString_Novalength && l2_Novafound; l3_Novaj++)
		{
			if (l0_Novasearch->prv->nova_standard_NovaString_Novadata[l3_Novaj] != this->prv->nova_standard_NovaString_Novadata[l2_Novai + l3_Novaj])
			{
				l2_Novafound = 0;
			}
		}
		if (l2_Novafound)
		{
			return l2_Novai;
		}
	}
	return -1;
}

int nova_standard_NovaString_Nova1_indexOf(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasearch)
{
	return nova_standard_NovaString_Nova0_indexOf(this, exceptionData, l0_Novasearch, 0, 1);
}

int nova_standard_NovaString_Nova2_indexOf(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasearch, int l0_Novastart)
{
	return nova_standard_NovaString_Nova0_indexOf(this, exceptionData, l0_Novasearch, l0_Novastart, 1);
}

int nova_standard_NovaString_NovalastIndexOf(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasearch)
{
	return nova_standard_NovaString_Nova0_indexOf(this, exceptionData, l0_Novasearch, this->nova_standard_NovaString_Novalength - 1, -1);
}

nova_standard_NovaString* nova_standard_NovaString_Nova0_substring(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novastart, int l0_Novaend)
{
	char* l1_Novabuf;
	
	if (l0_Novaend - l0_Novastart == 0)
	{
		return nova_standard_NovaString_Nova1_construct(0, exceptionData, "");
	}
	else if (l0_Novaend - l0_Novastart < 0)
	{
		THROW(1, nova_standard_exception_NovaException_Nova0_construct(0, exceptionData));
	}
	l1_Novabuf = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_NovaChar[l0_Novaend - l0_Novastart + 1]));
	memcpy((char*)(l1_Novabuf), (char**)&(this->prv->nova_standard_NovaString_Novadata[l0_Novastart]), (int)(l0_Novaend - l0_Novastart));
	l1_Novabuf[l0_Novaend - l0_Novastart] = '\0';
	return nova_standard_NovaString_Nova1_construct(0, exceptionData, l1_Novabuf);
}

nova_standard_NovaString* nova_standard_NovaString_Nova1_substring(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novastart)
{
	return nova_standard_NovaString_Nova0_substring(this, exceptionData, l0_Novastart, this->nova_standard_NovaString_Novalength);
}

char nova_standard_NovaString_NovalastChar(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_NovaCharAt(this, exceptionData, this->nova_standard_NovaString_Novalength - 1);
}

char nova_standard_NovaString_NovaCharAt(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex)
{
	return this->prv->nova_standard_NovaString_Novadata[l0_Novaindex];
}

nova_standard_NovaString* nova_standard_NovaString_Novatrim(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	int l1_Novastart;
	int l1_Novaend;
	
	l1_Novastart = 0;
	l1_Novaend = this->nova_standard_NovaString_Novalength - 1;
	while (l1_Novastart < this->nova_standard_NovaString_Novalength && nova_standard_NovaString_static_NovacontainsChar(this, exceptionData, this->prv->nova_standard_NovaString_Novadata[l1_Novastart], nova_standard_NovaString_static_Novawhitespace, nova_standard_NovaString_static_NovawhitespaceLength))
	{
		l1_Novastart++;
	}
	while (l1_Novaend >= 0 && nova_standard_NovaString_static_NovacontainsChar(this, exceptionData, this->prv->nova_standard_NovaString_Novadata[l1_Novaend], nova_standard_NovaString_static_Novawhitespace, nova_standard_NovaString_static_NovawhitespaceLength))
	{
		l1_Novaend--;
	}
	if (l1_Novaend == 0)
	{
		return nova_standard_NovaString_Nova1_construct(0, exceptionData, "");
	}
	if (l1_Novastart == 0 && l1_Novaend == this->nova_standard_NovaString_Novalength - 1)
	{
		return this;
	}
	return nova_standard_NovaString_Nova0_substring(this, exceptionData, l1_Novastart, l1_Novaend + 1);
}

char nova_standard_NovaString_static_NovacontainsChar(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novaneedle, char* l0_NovaChars, int l0_Novalength)
{
	int l2_Novai;
	
	l2_Novai = 0;
	for (; l2_Novai < l0_Novalength; l2_Novai++)
	{
		if (l0_Novaneedle == l0_NovaChars[l2_Novai])
		{
			return 1;
		}
	}
	return 0;
}

nova_standard_NovaString* nova_standard_NovaString_Nova0_toString(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this;
}

void nova_standard_NovaString_Novasuper(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_NovaString_Novalength = 0;
	this->prv->nova_standard_NovaString_Novadata = 0;
}
