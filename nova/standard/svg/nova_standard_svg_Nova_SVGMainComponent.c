#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_Nova_SVGMainComponent.h>



nova_standard_svg_Extension_VTable_SVGMainComponent nova_standard_svg_Extension_VTable_SVGMainComponent_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_svg_Nova_SVGMainComponent_0_Nova_generateOutput,
};


void nova_standard_svg_Nova_SVGMainComponent_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_svg_Nova_SVGMainComponent* nova_standard_svg_Nova_SVGMainComponent_Nova_SVGMainComponent(nova_standard_svg_Nova_SVGMainComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_svg_Nova_SVGMainComponent, this,);
	this->vtable = &nova_standard_svg_Extension_VTable_SVGMainComponent_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_svg_Nova_SVGComponent_Nova_super((nova_standard_svg_Nova_SVGComponent*)this, exceptionData);
	nova_standard_svg_Nova_SVGMainComponent_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_svg_Nova_SVGMainComponent_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_svg_Nova_SVGMainComponent_Nova_destroy(nova_standard_svg_Nova_SVGMainComponent** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_svg_Nova_SVGMainComponent_2_Nova_this(nova_standard_svg_Nova_SVGMainComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_svg_Nova_SVGComponent_Nova_children = nova_standard_svg_Nova_SVGComponentList_Nova_SVGComponentList(0, exceptionData);
}

void nova_standard_svg_Nova_SVGMainComponent_0_Nova_generateOutput(nova_standard_svg_Nova_SVGMainComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* nova_standard_svg_Nova_SVGMainComponent_Nova_file)
{
	nova_standard_io_Nova_File_Nova_write(nova_standard_svg_Nova_SVGMainComponent_Nova_file, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "<?xml version = '1.0' standalone = 'no'?>\n"), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "<!DOCTYPE svg PUBLIC\"-//W3C//DTD SVG1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">"), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "<svg width=\"1400px\" height=\"950px\" version=\"1.1\" preserveAspectRatio=\"none\">\n"))));
	nova_standard_svg_Nova_SVGComponentList_Nova_generateOutput(this->nova_standard_svg_Nova_SVGComponent_Nova_children, exceptionData, nova_standard_svg_Nova_SVGMainComponent_Nova_file);
	nova_standard_io_Nova_File_Nova_write(nova_standard_svg_Nova_SVGMainComponent_Nova_file, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "</svg>\n"));
}

void nova_standard_svg_Nova_SVGMainComponent_0_Nova_super(nova_standard_svg_Nova_SVGMainComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

