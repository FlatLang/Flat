#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaArrayList.h>


nova_VTable_nova_standard_datastruct_NovaArrayList nova_VTable_nova_standard_datastruct_NovaArrayList_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	int nova_standard_datastruct_NovaArrayList_NovabufferSize;
	nova_standard_NovaObject** nova_standard_datastruct_NovaArrayList_Novadata;
	
)

void nova_standard_datastruct_NovaArrayList_NovashiftRight(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaleft, int l0_Novaright);
void nova_standard_datastruct_NovaArrayList_NovashiftLeft(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaleft, int l0_Novaright);
void nova_standard_datastruct_NovaArrayListNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_NovaArrayList* nova_standard_datastruct_NovaArrayList_Nova0_construct(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_NovaArrayList, this);
	this->vtable = &nova_VTable_nova_standard_datastruct_NovaArrayList_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_datastruct_NovaArrayList_Novasuper(this, exceptionData);
	
	{
		nova_standard_datastruct_NovaArrayList_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_ArrayList(nova_standard_datastruct_NovaArrayList** this, nova_standard_exception_NovaExceptionData* exceptionData)
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

void nova_standard_datastruct_NovaArrayList_Novathis(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_datastruct_NovaArrayList_Novasize = 0;
	this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize = 0;
	nova_standard_datastruct_NovaArrayList_NovaincreaseSize(this, exceptionData);
}

void nova_standard_datastruct_NovaArrayList_Nova0_add(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaelement)
{
	if (this->nova_standard_datastruct_NovaArrayList_Novasize >= this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize)
	{
		nova_standard_datastruct_NovaArrayList_NovaincreaseSize(this, exceptionData);
	}
	this->prv->nova_standard_datastruct_NovaArrayList_Novadata[this->nova_standard_datastruct_NovaArrayList_Novasize++] = l0_Novaelement;
}

void nova_standard_datastruct_NovaArrayList_Nova1_add(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex, nova_standard_NovaObject* l0_Novaelement)
{
	nova_standard_datastruct_NovaArrayList_Nova0_add(this, exceptionData, l0_Novaelement);
	nova_standard_datastruct_NovaArrayList_NovashiftRight(this, exceptionData, l0_Novaindex, this->nova_standard_datastruct_NovaArrayList_Novasize);
	this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l0_Novaindex] = l0_Novaelement;
}

nova_standard_NovaObject* nova_standard_datastruct_NovaArrayList_Novaremove(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex)
{
	nova_standard_NovaObject* l1_Novaelement;
	
	l1_Novaelement = this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l0_Novaindex];
	nova_standard_datastruct_NovaArrayList_NovashiftLeft(this, exceptionData, l0_Novaindex + 1, this->nova_standard_datastruct_NovaArrayList_Novasize--);
	return l1_Novaelement;
}

void nova_standard_datastruct_NovaArrayList_NovashiftRight(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaleft, int l0_Novaright)
{
	int l2_Novai;
	
	l2_Novai = l0_Novaright - 1;
	for (; l2_Novai > l0_Novaleft; l2_Novai--)
	{
		this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l2_Novai] = this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l2_Novai - 1];
	}
	this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l0_Novaleft] = (nova_standard_NovaObject*)((nova_standard_NovaObject*)nova_null);
}

void nova_standard_datastruct_NovaArrayList_NovashiftLeft(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaleft, int l0_Novaright)
{
	int l2_Novai;
	
	l2_Novai = l0_Novaleft - 1;
	for (; l2_Novai < l0_Novaright - 1; l2_Novai++)
	{
		this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l2_Novai] = this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l2_Novai + 1];
	}
	this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l0_Novaright - 1] = (nova_standard_NovaObject*)((nova_standard_NovaObject*)nova_null);
}

void nova_standard_datastruct_NovaArrayList_Novaswap(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex1, int l0_Novaindex2)
{
	nova_standard_NovaObject* l1_Novatemp;
	
	l1_Novatemp = this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l0_Novaindex1];
	this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l0_Novaindex1] = this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l0_Novaindex2];
	this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l0_Novaindex2] = l1_Novatemp;
}

void nova_standard_datastruct_NovaArrayList_NovaincreaseSize(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaObject** l1_Novatmp;
	
	this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize = this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize + 3;
	l1_Novatmp = (nova_standard_NovaObject**)((nova_standard_NovaObject**)NOVA_MALLOC(sizeof(nova_standard_NovaObject*[this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize])));
	arrayCopy(l1_Novatmp, (int)(0), this->prv->nova_standard_datastruct_NovaArrayList_Novadata, (int)(0), (int)(this->nova_standard_datastruct_NovaArrayList_Novasize), (int)(this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize), (int)(4));
	this->prv->nova_standard_datastruct_NovaArrayList_Novadata = l1_Novatmp;
}

nova_standard_NovaObject* nova_standard_datastruct_NovaArrayList_Novaget(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex)
{
	return this->prv->nova_standard_datastruct_NovaArrayList_Novadata[l0_Novaindex];
}

void nova_standard_datastruct_NovaArrayList_Novasuper(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_datastruct_NovaArrayList_Novasize = 0;
	this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize = 0;
	this->prv->nova_standard_datastruct_NovaArrayList_Novadata = (nova_standard_NovaObject**)nova_null;
}
