#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaArrayList.h>


nova_VTable_nova_standard_datastruct_NovaArrayList nova_VTable_nova_standard_datastruct_NovaArrayList_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};
CCLASS_PRIVATE
(
	int nova_standard_datastruct_NovaArrayList_NovabufferSize;
	nova_standard_NovaObject** nova_standard_datastruct_NovaArrayList_Novadata;
	
)

nova_standard_datastruct_NovaArrayList* nova_standard_datastruct_NovaArrayList_Novaconstruct(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_NovaArrayList, this);
	this->vtable = &nova_VTable_nova_standard_datastruct_NovaArrayList_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_datastruct_NovaArrayList_Novasuper(this, 0);
	
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

void nova_standard_datastruct_NovaArrayList_Novaadd(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaelement)
{
	if (this->nova_standard_datastruct_NovaArrayList_Novasize >= this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize)
	{
		nova_standard_datastruct_NovaArrayList_NovaincreaseSize(this, exceptionData);
	}
	this->prv->nova_standard_datastruct_NovaArrayList_Novadata[this->nova_standard_datastruct_NovaArrayList_Novasize++] = l0_Novaelement;
}

void nova_standard_datastruct_NovaArrayList_NovaincreaseSize(nova_standard_datastruct_NovaArrayList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaObject** l1_Novatmp;
	
	this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize = this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize + 3;
	l1_Novatmp = (nova_standard_NovaObject**)((nova_standard_NovaObject**)NOVA_MALLOC(sizeof(nova_standard_NovaObject) * (this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize)));
	arrayCopy(l1_Novatmp, 0, this->prv->nova_standard_datastruct_NovaArrayList_Novadata, 0, this->nova_standard_datastruct_NovaArrayList_Novasize, this->prv->nova_standard_datastruct_NovaArrayList_NovabufferSize, 4);
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
