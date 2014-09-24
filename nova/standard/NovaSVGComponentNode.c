#include <precompiled.h>
#include "NovaSVGComponentNode.h"


nova_VTable_SVGComponentNode nova_VTable_SVGComponentNode_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

SVGComponentNode* nova_0_SVGComponentNode_construct(SVGComponentNode* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(SVGComponentNode, this,);
	this->vtable = &nova_VTable_SVGComponentNode_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_SVGComponentNode_super(this, 0);
	
	{
		nova_SVGComponentNode_this(this, exceptionData);
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

void nova_SVGComponentNode_this(SVGComponentNode* this, ExceptionData* exceptionData)
{
}

void nova_SVGComponentNode_super(SVGComponentNode* this, ExceptionData* exceptionData)
{
	this->nova_SVGComponentNode_next = (SVGComponentNode*)nova_null;
	this->nova_SVGComponentNode_component = (SVGComponent*)nova_null;
}
