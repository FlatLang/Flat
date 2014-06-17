#include <precompiled.h>

#include "NovaSVGComponentNode.h"

SVGComponentNode* nova_SVGComponentNode_SVGComponentNode(ExceptionData* exceptionData)
{
	CCLASS_NEW(SVGComponentNode, this,);
	
	this->nova_SVGComponentNode_next = 0;
	this->nova_SVGComponentNode_component = 0;
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
	nova_del_SVGCircle(&(*this)->nova_SVGComponentNode_component, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}
