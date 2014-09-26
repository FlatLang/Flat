#include <precompiled.h>
#include <nova/standard/nova_standard_NovaSVGComponentNode.h>


nova_VTable_nova_standard_NovaSVGComponentNode nova_VTable_nova_standard_NovaSVGComponentNode_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};

nova_standard_NovaSVGComponentNode* nova_standard_NovaSVGComponentNode_Novanull0_construct(nova_standard_NovaSVGComponentNode* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaSVGComponentNode, this,);
	this->vtable = &nova_VTable_nova_standard_NovaSVGComponentNode_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaSVGComponentNode_Novasuper(this, 0);
	
	{
		nova_standard_NovaSVGComponentNode_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_SVGComponentNode(nova_standard_NovaSVGComponentNode** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_SVGComponentNode(&(*this)->nova_standard_NovaSVGComponentNode_Novanext, exceptionData);
	nova_del_SVGComponent(&(*this)->nova_standard_NovaSVGComponentNode_Novacomponent, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaSVGComponentNode_Novathis(nova_standard_NovaSVGComponentNode* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaSVGComponentNode_Novasuper(nova_standard_NovaSVGComponentNode* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_NovaSVGComponentNode_Novanext = (nova_standard_NovaSVGComponentNode*)nova_null;
	this->nova_standard_NovaSVGComponentNode_Novacomponent = (nova_standard_NovaSVGComponent*)nova_null;
}
