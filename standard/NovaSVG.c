#include <precompiled.h>
#include "NovaSVG.h"




SVG* nova_SVG_SVG(ExceptionData* exceptionData)
{
	CCLASS_NEW(SVG, this,);
	
	this->nova_SVG_root = (Object*)0;
	{
		this->nova_SVG_root = nova_SVGMainComponent_SVGMainComponent(exceptionData);
	}
	
	return this;
}

void nova_del_SVG(SVG** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_SVGMainComponent(&(*this)->nova_SVG_root, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_SVG_generateOutput(SVG* this, ExceptionData* exceptionData, File* nova_0_file)
{
	this->nova_SVG_root->vtable->nova_virtual_generateOutput(this->nova_SVG_root, exceptionData, nova_0_file);
}

void nova_SVG_generateHTMLOutput(SVG* this, ExceptionData* exceptionData, File* nova_0_file)
{
	nova_File_write(nova_0_file, exceptionData, nova_String_String(exceptionData, "<html>\n"));
	nova_SVG_generateOutput(this, exceptionData, nova_0_file);
	nova_File_write(nova_0_file, exceptionData, nova_String_String(exceptionData, "</html>"));
}
