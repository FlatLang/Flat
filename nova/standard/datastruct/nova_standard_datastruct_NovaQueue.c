#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaQueue.h>


nova_VTable_nova_standard_datastruct_NovaQueue nova_VTable_nova_standard_datastruct_NovaQueue_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_datastruct_NovaQueue_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_datastruct_NovaArrayList* nova_standard_datastruct_NovaQueue_Novadata;
	
)
void nova_standard_datastruct_NovaQueueNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_NovaQueue* nova_standard_datastruct_NovaQueue_Nova0_construct(nova_standard_datastruct_NovaQueue* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_NovaQueue, this);
	this->vtable = &nova_VTable_nova_standard_datastruct_NovaQueue_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_datastruct_NovaQueue_Novasuper(this, 0);
	
	{
		nova_standard_datastruct_NovaQueue_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Queue(nova_standard_datastruct_NovaQueue** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ArrayList(&(*this)->prv->nova_standard_datastruct_NovaQueue_Novadata, exceptionData);
	NOVA_FREE((*this)->prv);
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_datastruct_NovaQueue_Novathis(nova_standard_datastruct_NovaQueue* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_NovaQueue_Novadata = nova_standard_datastruct_NovaArrayList_Nova0_construct(0, exceptionData);
}

nova_standard_NovaObject* nova_standard_datastruct_NovaQueue_Novadequeue(nova_standard_datastruct_NovaQueue* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_datastruct_NovaQueue_Novasize--;
	return ((nova_standard_NovaObject*)nova_standard_datastruct_NovaArrayList_Novaremove(this->prv->nova_standard_datastruct_NovaQueue_Novadata, exceptionData, 0));
}

void nova_standard_datastruct_NovaQueue_Novaenqueue(nova_standard_datastruct_NovaQueue* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaelement)
{
	nova_standard_datastruct_NovaArrayList_Nova0_add(this->prv->nova_standard_datastruct_NovaQueue_Novadata, exceptionData, l0_Novaelement);
	this->nova_standard_datastruct_NovaQueue_Novasize++;
}

char nova_standard_datastruct_NovaQueue_NovaisEmpty(nova_standard_datastruct_NovaQueue* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_NovaQueue_Novasize <= 0;
}

nova_standard_NovaString* nova_standard_datastruct_NovaQueue_Nova0_toString(nova_standard_datastruct_NovaQueue* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaString* l1_Novas;
	int l2_Novai;
	
	l1_Novas = nova_standard_NovaString_Nova1_construct(0, exceptionData, "");
	l2_Novai = this->nova_standard_datastruct_NovaQueue_Novasize - 1;
	for (; l2_Novai >= 0; l2_Novai--)
	{
		if (l2_Novai < this->nova_standard_datastruct_NovaQueue_Novasize - 1)
		{
			l1_Novas = l1_Novas->vtable->nova_standard_NovaString_Novavirtual0_concat(l1_Novas, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, ", "));
		}
		l1_Novas = l1_Novas->vtable->nova_standard_NovaString_Novavirtual0_concat(l1_Novas, exceptionData, ((nova_standard_NovaObject*)nova_standard_datastruct_NovaArrayList_Novaget(this->prv->nova_standard_datastruct_NovaQueue_Novadata, exceptionData, l2_Novai))->vtable->nova_standard_NovaObject_Novavirtual0_toString((nova_standard_NovaObject*)(((nova_standard_NovaObject*)nova_standard_datastruct_NovaArrayList_Novaget(this->prv->nova_standard_datastruct_NovaQueue_Novadata, exceptionData, l2_Novai))), exceptionData));
	}
	return l1_Novas;
}

void nova_standard_datastruct_NovaQueue_Novasuper(nova_standard_datastruct_NovaQueue* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_datastruct_NovaQueue_Novasize = 0;
	this->prv->nova_standard_datastruct_NovaQueue_Novadata = (nova_standard_datastruct_NovaArrayList*)nova_null;
}
