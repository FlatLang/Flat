#include <precompiled.h>
#include "NovaSVGComponent.h"


nova_VTable_SVGComponent nova_VTable_SVGComponent_val =
{
	nova_SVGComponent_generateOutput,
};

SVGComponent* nova_SVGComponent_SVGComponent(ExceptionData* exceptionData)
{
	CCLASS_NEW(SVGComponent, this,);
	
	this->nova_SVGComponent_children = (SVGComponentList*)0;
	this->vtable = &nova_VTable_SVGComponent_val;
	{
		this->nova_SVGComponent_children = nova_SVGComponentList_SVGComponentList(exceptionData);
	}
	
	return this;
}

void nova_del_SVGComponent(SVGComponent** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_SVGComponentList(&(*this)->nova_SVGComponent_children, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_SVGComponent_generateOutput(SVGComponent* this, ExceptionData* exceptionData, File* nova_0_file)
{
}
