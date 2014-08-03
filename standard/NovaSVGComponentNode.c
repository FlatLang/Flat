#include <precompiled.h>
#include "NovaSVGComponentNode.h"


nova_VTable_SVGComponentNode nova_VTable_SVGComponentNode_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};

SVGComponentNode* nova_SVGComponentNode_construct(SVGComponentNode* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(SVGComponentNode, this,);
	
	this->nova_SVGComponentNode_next = (SVGComponentNode*)0;
	this->nova_SVGComponentNode_component = (SVGComponent*)0;
	this->vtable = &nova_VTable_SVGComponentNode_val;
	{
	}
	
	return this;
}

void nova_del_SVGComponentNode(SVGComponentNode** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_SVGComponentNode(&(*this)->nova_SVGComponentNode_next, exceptionData);
	nova_del_SVGComponent(&(*this)->nova_SVGComponentNode_component, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}
