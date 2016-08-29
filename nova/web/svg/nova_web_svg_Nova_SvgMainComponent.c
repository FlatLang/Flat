#include <precompiled.h>
#include <nova/web/svg/nova_web_svg_Nova_SvgMainComponent.h>



nova_web_svg_Extension_VTable_SvgMainComponent nova_web_svg_Extension_VTable_SvgMainComponent_val =
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
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	nova_web_svg_Nova_SvgMainComponent_0_Nova_generateOutput,
};


void nova_web_svg_Nova_SvgMainComponent_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_web_svg_Nova_SvgMainComponent* nova_web_svg_Nova_SvgMainComponent_Nova_construct(nova_web_svg_Nova_SvgMainComponent* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_web_svg_Nova_SvgMainComponent, this,);
	this->vtable = &nova_web_svg_Extension_VTable_SvgMainComponent_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_web_svg_Nova_SvgComponent_Nova_super((nova_web_svg_Nova_SvgComponent*)this, exceptionData);
	nova_web_svg_Nova_SvgMainComponent_0_Nova_super(this, exceptionData);
	
	{
		nova_web_svg_Nova_SvgMainComponent_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_web_svg_Nova_SvgMainComponent_Nova_destroy(nova_web_svg_Nova_SvgMainComponent** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_web_svg_Nova_SvgMainComponent_2_Nova_this(nova_web_svg_Nova_SvgMainComponent* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_web_svg_Nova_SvgComponent_Nova_children = nova_web_svg_Nova_SvgComponentList_Nova_construct(0, exceptionData);
}

void nova_web_svg_Nova_SvgMainComponent_0_Nova_generateOutput(nova_web_svg_Nova_SvgMainComponent* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_web_svg_Nova_SvgMainComponent_Nova_file)
{
	nova_io_Nova_File_Nova_write(nova_web_svg_Nova_SvgMainComponent_Nova_file, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("<?xml version = '1.0' standalone = 'no'?>\n")), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("<!DOCTYPE svg PUBLIC\"-//W3C//DTD Svg1.1//EN\" \"http://www.w3.org/Graphics/Svg/1.1/DTD/svg11.dtd\">")), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("<svg width=\"1400px\" height=\"950px\" version=\"1.1\" preserveAspectRatio=\"none\">\n")))));
	nova_web_svg_Nova_SvgComponentList_Nova_generateOutput(this->nova_web_svg_Nova_SvgComponent_Nova_children, exceptionData, nova_web_svg_Nova_SvgMainComponent_Nova_file);
	nova_io_Nova_File_Nova_write(nova_web_svg_Nova_SvgMainComponent_Nova_file, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("</svg>\n")));
}

void nova_web_svg_Nova_SvgMainComponent_0_Nova_super(nova_web_svg_Nova_SvgMainComponent* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

