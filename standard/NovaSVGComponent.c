#include <precompiled.h>
#include "NovaSVGComponent.h"


nova_VTable_SVGComponent nova_VTable_SVGComponent_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
	nova_0_SVGComponent_generateOutput,
};

SVGComponent* nova_0_SVGComponent_construct(SVGComponent* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(SVGComponent, this,);
	this->vtable = &nova_VTable_SVGComponent_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_SVGComponent_super(this, 0);
	
	{
		nova_SVGComponent_this(this, exceptionData);
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

void nova_SVGComponent_this(SVGComponent* this, ExceptionData* exceptionData)
{
	this->nova_SVGComponent_children = nova_0_SVGComponentList_construct(0, exceptionData);
}

void nova_0_SVGComponent_generateOutput(SVGComponent* this, ExceptionData* exceptionData, File* nova_0_file)
{
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "This is wrong"));
}

void nova_SVGComponent_super(SVGComponent* this, ExceptionData* exceptionData)
{
	this->nova_SVGComponent_children = (SVGComponentList*)nova_null;
}
