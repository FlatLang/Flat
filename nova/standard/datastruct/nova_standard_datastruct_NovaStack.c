#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaStack.h>


nova_VTable_nova_standard_datastruct_NovaStack nova_VTable_nova_standard_datastruct_NovaStack_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_datastruct_NovaListNode* nova_standard_datastruct_NovaStack_Novatop;
	
)
void nova_standard_datastruct_NovaStackNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_NovaStack* nova_standard_datastruct_NovaStack_Nova0_construct(nova_standard_datastruct_NovaStack* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_NovaStack, this);
	this->vtable = &nova_VTable_nova_standard_datastruct_NovaStack_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_datastruct_NovaStack_Novasuper(this, 0);
	
	{
		nova_standard_datastruct_NovaStack_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Stack(nova_standard_datastruct_NovaStack** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ListNode(&(*this)->prv->nova_standard_datastruct_NovaStack_Novatop, exceptionData);
	NOVA_FREE((*this)->prv);
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_datastruct_NovaStack_Novapush(nova_standard_datastruct_NovaStack* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novadata)
{
	nova_standard_datastruct_NovaListNode* l1_Novanode;
	
	l1_Novanode = nova_standard_datastruct_NovaListNode_Novaconstruct(0, exceptionData, l0_Novadata);
	l1_Novanode->nova_standard_datastruct_NovaListNode_Novanext = this->prv->nova_standard_datastruct_NovaStack_Novatop;
	this->prv->nova_standard_datastruct_NovaStack_Novatop = l1_Novanode;
	this->nova_standard_datastruct_NovaStack_Novasize++;
}

nova_standard_NovaObject* nova_standard_datastruct_NovaStack_Novapop(nova_standard_datastruct_NovaStack* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaObject* l1_Novadata;
	
	if (nova_standard_datastruct_NovaStack_NovaisEmpty(this, exceptionData))
	{
		THROW(3, nova_standard_datastruct_NovaEmptyStackException_Nova0_construct(0, exceptionData));
	}
	l1_Novadata = this->prv->nova_standard_datastruct_NovaStack_Novatop->nova_standard_datastruct_NovaListNode_Novadata;
	this->prv->nova_standard_datastruct_NovaStack_Novatop = this->prv->nova_standard_datastruct_NovaStack_Novatop->nova_standard_datastruct_NovaListNode_Novanext;
	this->nova_standard_datastruct_NovaStack_Novasize--;
	return l1_Novadata;
}

char nova_standard_datastruct_NovaStack_NovaisEmpty(nova_standard_datastruct_NovaStack* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_NovaStack_Novasize <= 0;
}

void nova_standard_datastruct_NovaStack_Novathis(nova_standard_datastruct_NovaStack* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_datastruct_NovaStack_Novasuper(nova_standard_datastruct_NovaStack* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_datastruct_NovaStack_Novasize = 0;
	this->prv->nova_standard_datastruct_NovaStack_Novatop = (nova_standard_datastruct_NovaListNode*)nova_null;
}
