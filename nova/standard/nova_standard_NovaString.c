#include <precompiled.h>
#include <nova/standard/nova_standard_NovaString.h>


nova_VTable_nova_standard_NovaString nova_VTable_nova_standard_NovaString_val =
{
	nova_standard_NovaString_Novanull0_toString,
	nova_standard_NovaString_Novanull0_equals,
	nova_standard_NovaString_Novanull0_concat,
};
CCLASS_PRIVATE
(
	char* nova_standard_NovaString_Novadata;
	
)

int nova_standard_NovaString_NovacalculateLength(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData);

nova_standard_NovaString* nova_standard_NovaString_Novaconstruct(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novadata)
{
	CCLASS_NEW(nova_standard_NovaString, this);
	this->vtable = &nova_VTable_nova_standard_NovaString_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaString_Novasuper(this, 0);
	
	{
		nova_standard_NovaString_Novathis(this, exceptionData, l0_Novadata);
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

void nova_standard_NovaString_Novathis(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novadata)
{
	this->prv->nova_standard_NovaString_Novadata = l0_Novadata;
	this->nova_standard_NovaString_Novalength = nova_standard_NovaString_NovacalculateLength(this, exceptionData);
}

int nova_standard_NovaString_NovacalculateLength(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return strlen(this->prv->nova_standard_NovaString_Novadata);
}

char* nova_standard_NovaString_NovatoCharArray(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->prv->nova_standard_NovaString_Novadata;
}

nova_standard_NovaString* nova_standard_NovaString_Novanull0_concat(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastr)
{
	int l1_Novasize;
	char* l1_NovanewData;
	nova_standard_NovaString* l1_NovanewStr;
	
	l0_Novastr = l0_Novastr->vtable->nova_standard_NovaString_Novavirtual0_toString(l0_Novastr, exceptionData);
	l1_Novasize = (int)(l0_Novastr->nova_standard_NovaString_Novalength + this->nova_standard_NovaString_Novalength + 1);
	l1_NovanewData = (char*)NOVA_MALLOC(sizeof(char) * (l1_Novasize));
	strcpy(l1_NovanewData, this->prv->nova_standard_NovaString_Novadata);
	strcat(l1_NovanewData, nova_standard_NovaString_NovatoCharArray(l0_Novastr, exceptionData));
	l1_NovanewData[l1_Novasize - 1] = '\0';
	l1_NovanewStr = nova_standard_NovaString_Novaconstruct(0, exceptionData, l1_NovanewData);
	return l1_NovanewStr;
}

char nova_standard_NovaString_Novanull0_equals(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaother)
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

nova_standard_NovaString* nova_standard_NovaString_Novanull0_toString(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this;
}

void nova_standard_NovaString_Novasuper(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_NovaString_Novalength = 0;
	this->prv->nova_standard_NovaString_Novadata = 0;
}
