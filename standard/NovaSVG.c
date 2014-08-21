#include <precompiled.h>
#include "NovaSVG.h"


nova_VTable_SVG nova_VTable_SVG_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

SVG* nova_0_SVG_construct(SVG* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(SVG, this,);
	this->vtable = &nova_VTable_SVG_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_SVG_super(this, 0);
	
	{
		nova_SVG_this(this, exceptionData);
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

void nova_SVG_this(SVG* this, ExceptionData* exceptionData)
{
	this->nova_SVG_root = nova_0_SVGMainComponent_construct(0, exceptionData);
}

void nova_SVG_generateOutput(SVG* this, ExceptionData* exceptionData, File* nova_0_file)
{
	this->nova_SVG_root->vtable->nova_virtual_0_generateOutput(this->nova_SVG_root, exceptionData, nova_0_file);
}

void nova_SVG_generateHTMLOutput(SVG* this, ExceptionData* exceptionData, File* nova_0_file)
{
	nova_File_write(nova_0_file, exceptionData, nova_String_construct(0, exceptionData, "<html>\n"));
	nova_SVG_generateOutput(this, exceptionData, nova_0_file);
	nova_File_write(nova_0_file, exceptionData, nova_String_construct(0, exceptionData, "</html>"));
}

void nova_SVG_super(SVG* this, ExceptionData* exceptionData)
{
	this->nova_SVG_root = (SVGMainComponent*)0;
}
