#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_Nova_SVG.h>

nova_standard_svg_Extension_VTable_SVG nova_standard_svg_Extension_VTable_SVG_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_svg_Nova_SVGNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_svg_Nova_SVG* nova_standard_svg_Nova_SVG_2_Nova_construct(nova_standard_svg_Nova_SVG* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_svg_Nova_SVG, this,);
	this->vtable = &nova_standard_svg_Extension_VTable_SVG_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_svg_Nova_SVG_Nova_super(this, exceptionData);
	
	{
		nova_standard_svg_Nova_SVG_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_svg_Nova_SVG_Nova_destroy(nova_standard_svg_Nova_SVG** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_svg_Nova_SVGMainComponent_Nova_destroy(&(*this)->nova_standard_svg_Nova_SVG_Nova_root, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_svg_Nova_SVG_2_Nova_this(nova_standard_svg_Nova_SVG* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_svg_Nova_SVG_Nova_root = nova_standard_svg_Nova_SVGMainComponent_2_Nova_construct(0, exceptionData);
}

void nova_standard_svg_Nova_SVG_Nova_generateOutput(nova_standard_svg_Nova_SVG* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* nova_standard_svg_Nova_SVG_Nova_file)
{
	this->nova_standard_svg_Nova_SVG_Nova_root->vtable->nova_standard_svg_Nova_SVGMainComponent_virtual_Nova_generateOutput(this->nova_standard_svg_Nova_SVG_Nova_root, exceptionData, nova_standard_svg_Nova_SVG_Nova_file);
}

void nova_standard_svg_Nova_SVG_Nova_generateHTMLOutput(nova_standard_svg_Nova_SVG* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* nova_standard_svg_Nova_SVG_Nova_file)
{
	nova_standard_io_Nova_File_Nova_write(nova_standard_svg_Nova_SVG_Nova_file, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "<html>\n"));
	nova_standard_svg_Nova_SVG_Nova_generateOutput(this, exceptionData, nova_standard_svg_Nova_SVG_Nova_file);
	nova_standard_io_Nova_File_Nova_write(nova_standard_svg_Nova_SVG_Nova_file, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "</html>"));
}

void nova_standard_svg_Nova_SVG_Nova_super(nova_standard_svg_Nova_SVG* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_svg_Nova_SVG_Nova_root = (nova_standard_svg_Nova_SVGMainComponent*)nova_null;
}

