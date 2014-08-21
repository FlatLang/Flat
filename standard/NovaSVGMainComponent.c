#include <precompiled.h>
#include "NovaSVGMainComponent.h"


nova_VTable_SVGMainComponent nova_VTable_SVGMainComponent_val =
{
	nova_0_SVGMainComponent_generateOutput,
};

SVGMainComponent* nova_0_SVGMainComponent_construct(SVGMainComponent* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(SVGMainComponent, this,);
	this->vtable = &nova_VTable_SVGMainComponent_val;
	nova_Object_super((Object*)this, 0);
	nova_SVGComponent_super((SVGComponent*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_SVGComponent_this((SVGComponent*)(this), exceptionData);
	nova_SVGMainComponent_super(this, 0);
	
	{
		nova_SVGMainComponent_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_SVGMainComponent(SVGMainComponent** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_SVGMainComponent_this(SVGMainComponent* this, ExceptionData* exceptionData)
{
	this->nova_SVGComponent_children = nova_0_SVGComponentList_construct(0, exceptionData);
}

void nova_0_SVGMainComponent_generateOutput(SVGMainComponent* this, ExceptionData* exceptionData, File* nova_0_file)
{
	nova_File_write(nova_0_file, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "<?xml version = '1.0' standalone = 'no'?>\n"), exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "<!DOCTYPE svg PUBLIC\"-//W3C//DTD SVG1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">"), exceptionData, nova_String_construct(0, exceptionData, "<svg width=\"1400\"px height=\"950\"px version=\"1.1\" preserveAspectRatio=\"none\">\n"))));
	nova_SVGComponentList_generateOutput(this->nova_SVGComponent_children, exceptionData, nova_0_file);
	nova_File_write(nova_0_file, exceptionData, nova_String_construct(0, exceptionData, "</svg>\n"));
}

void nova_SVGMainComponent_super(SVGMainComponent* this, ExceptionData* exceptionData)
{
}
