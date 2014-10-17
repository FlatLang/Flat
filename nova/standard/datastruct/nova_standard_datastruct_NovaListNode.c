#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaListNode.h>


nova_VTable_nova_standard_datastruct_NovaListNode nova_VTable_nova_standard_datastruct_NovaListNode_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_datastruct_NovaListNodeNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_NovaListNode* nova_standard_datastruct_NovaListNode_Novaconstruct(nova_standard_datastruct_NovaListNode* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novadata)
{
	CCLASS_NEW(nova_standard_datastruct_NovaListNode, this,);
	this->vtable = &nova_VTable_nova_standard_datastruct_NovaListNode_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_datastruct_NovaListNode_Novasuper(this, exceptionData);
	
	{
		nova_standard_datastruct_NovaListNode_Novathis(this, exceptionData, l0_Novadata);
	}
	
	return this;
}

void nova_del_ListNode(nova_standard_datastruct_NovaListNode** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_ListNode(&(*this)->nova_standard_datastruct_NovaListNode_Novanext, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_datastruct_NovaListNode_Novathis(nova_standard_datastruct_NovaListNode* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novadata)
{
	this->nova_standard_datastruct_NovaListNode_Novadata = l0_Novadata;
}

void nova_standard_datastruct_NovaListNode_Novasuper(nova_standard_datastruct_NovaListNode* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_datastruct_NovaListNode_Novadata = (nova_standard_NovaObject*)nova_null;
	this->nova_standard_datastruct_NovaListNode_Novanext = (nova_standard_datastruct_NovaListNode*)nova_null;
}
