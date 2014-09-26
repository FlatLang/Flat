#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaList.h>


nova_VTable_nova_standard_datastruct_NovaList nova_VTable_nova_standard_datastruct_NovaList_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_datastruct_NovaListNode* nova_standard_datastruct_NovaList_Novastart;
	nova_standard_datastruct_NovaListNode* nova_standard_datastruct_NovaList_Novacurrent;
	
)

nova_standard_datastruct_NovaList* nova_standard_datastruct_NovaList_Novanull0_construct(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_NovaList, this);
	this->vtable = &nova_VTable_nova_standard_datastruct_NovaList_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_datastruct_NovaList_Novasuper(this, 0);
	
	{
		nova_standard_datastruct_NovaList_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_List(nova_standard_datastruct_NovaList** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_ListNode(&(*this)->prv->nova_standard_datastruct_NovaList_Novastart, exceptionData);
	nova_del_ListNode(&(*this)->prv->nova_standard_datastruct_NovaList_Novacurrent, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

nova_standard_datastruct_NovaListNode* nova_standard_datastruct_NovaList_NovaE(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->prv->nova_standard_datastruct_NovaList_Novastart;
}

void nova_standard_datastruct_NovaList_Novaadd(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novadata)
{
	nova_standard_datastruct_NovaListNode* l1_Novanode;
	
	l1_Novanode = nova_standard_datastruct_NovaListNode_Novaconstruct(0, exceptionData, l0_Novadata);
	if (this->prv->nova_standard_datastruct_NovaList_Novastart == (nova_standard_datastruct_NovaListNode*)nova_null)
	{
		this->prv->nova_standard_datastruct_NovaList_Novastart = l1_Novanode;
		this->prv->nova_standard_datastruct_NovaList_Novacurrent = l1_Novanode;
	}
	else
	{
		this->prv->nova_standard_datastruct_NovaList_Novacurrent->nova_standard_datastruct_NovaListNode_Novanext = l1_Novanode;
	}
	this->prv->nova_standard_datastruct_NovaList_Novacurrent = l1_Novanode;
}

void nova_standard_datastruct_NovaList_Novaremove(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novadata)
{
	nova_standard_datastruct_NovaListNode* l1_Novaprev;
	nova_standard_datastruct_NovaListNode* l1_Novacur;
	
	if (this->prv->nova_standard_datastruct_NovaList_Novastart->nova_standard_datastruct_NovaListNode_Novadata == l0_Novadata)
	{
		this->prv->nova_standard_datastruct_NovaList_Novastart = this->prv->nova_standard_datastruct_NovaList_Novastart->nova_standard_datastruct_NovaListNode_Novanext;
	}
	l1_Novaprev = this->prv->nova_standard_datastruct_NovaList_Novastart;
	l1_Novacur = this->prv->nova_standard_datastruct_NovaList_Novastart->nova_standard_datastruct_NovaListNode_Novanext;
	while (l1_Novacur != (nova_standard_datastruct_NovaListNode*)nova_null)
	{
		nova_standard_NovaObject* l3_Novad;
		
		l3_Novad = l1_Novacur->nova_standard_datastruct_NovaListNode_Novadata;
		if (l3_Novad == l0_Novadata)
		{
			l1_Novaprev->nova_standard_datastruct_NovaListNode_Novanext = l1_Novacur->nova_standard_datastruct_NovaListNode_Novanext;
		}
		l1_Novacur = l1_Novacur->nova_standard_datastruct_NovaListNode_Novanext;
	}
}

void nova_standard_datastruct_NovaList_Novathis(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_datastruct_NovaList_Novasuper(nova_standard_datastruct_NovaList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_NovaList_Novastart = (nova_standard_datastruct_NovaListNode*)nova_null;
	this->prv->nova_standard_datastruct_NovaList_Novacurrent = (nova_standard_datastruct_NovaListNode*)nova_null;
}
