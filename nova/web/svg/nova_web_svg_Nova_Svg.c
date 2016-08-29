#include <precompiled.h>
#include <nova/web/svg/nova_web_svg_Nova_Svg.h>



nova_web_svg_Extension_VTable_Svg nova_web_svg_Extension_VTable_Svg_val =
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
};


void nova_web_svg_Nova_Svg_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_web_svg_Nova_Svg* nova_web_svg_Nova_Svg_Nova_construct(nova_web_svg_Nova_Svg* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_web_svg_Nova_Svg, this,);
	this->vtable = &nova_web_svg_Extension_VTable_Svg_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_web_svg_Nova_Svg_Nova_super(this, exceptionData);
	
	{
		nova_web_svg_Nova_Svg_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_web_svg_Nova_Svg_Nova_destroy(nova_web_svg_Nova_Svg** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_web_svg_Nova_SvgMainComponent_Nova_destroy(&(*this)->nova_web_svg_Nova_Svg_Nova_root, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_web_svg_Nova_Svg_0_Nova_this(nova_web_svg_Nova_Svg* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_web_svg_Nova_Svg_Nova_root = nova_web_svg_Nova_SvgMainComponent_Nova_construct(0, exceptionData);
}

void nova_web_svg_Nova_Svg_Nova_generateOutput(nova_web_svg_Nova_Svg* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_web_svg_Nova_Svg_Nova_file)
{
	nova_web_svg_Nova_SvgComponent_virtual0_Nova_generateOutput((nova_web_svg_Nova_SvgComponent*)(this->nova_web_svg_Nova_Svg_Nova_root), exceptionData, nova_web_svg_Nova_Svg_Nova_file);
}

void nova_web_svg_Nova_Svg_Nova_generateHTMLOutput(nova_web_svg_Nova_Svg* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_web_svg_Nova_Svg_Nova_file)
{
	nova_io_Nova_File_Nova_write(nova_web_svg_Nova_Svg_Nova_file, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("<html>\n")));
	nova_web_svg_Nova_Svg_Nova_generateOutput(this, exceptionData, nova_web_svg_Nova_Svg_Nova_file);
	nova_io_Nova_File_Nova_write(nova_web_svg_Nova_Svg_Nova_file, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("</html>")));
}

void nova_web_svg_Nova_Svg_Nova_super(nova_web_svg_Nova_Svg* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_web_svg_Nova_Svg_Nova_root = (nova_web_svg_Nova_SvgMainComponent*)nova_null;
}

