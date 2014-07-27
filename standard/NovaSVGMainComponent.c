#include <precompiled.h>
#include "NovaSVGMainComponent.h"


nova_VTable_SVGMainComponent nova_VTable_SVGMainComponent_val =
{
	nova_1_SVGMainComponent_generateOutput,
};

SVGMainComponent* nova_SVGMainComponent_SVGMainComponent(ExceptionData* exceptionData)
{
	CCLASS_NEW(SVGMainComponent, this,);
	
	this->vtable = &nova_VTable_SVGMainComponent_val;
	{
		this->nova_SVGComponent_children = nova_SVGComponentList_SVGComponentList(exceptionData);
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

void nova_1_SVGMainComponent_generateOutput(SVGMainComponent* this, ExceptionData* exceptionData, File* nova_0_file)
{
	nova_File_write(nova_0_file, exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)("<?xml version = '1.0' standalone = 'no'?>\n")), exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)("<!DOCTYPE svg PUBLIC\"-//W3C//DTD SVG1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">")), exceptionData, nova_String_String(exceptionData, (char*)("<svg width=\"1400\"px height=\"950\"px version=\"1.1\" preserveAspectRatio=\"none\">\n")))));
	nova_SVGComponentList_generateOutput(this->nova_SVGComponent_children, exceptionData, nova_0_file);
	nova_File_write(nova_0_file, exceptionData, nova_String_String(exceptionData, "</svg>\n"));
}
