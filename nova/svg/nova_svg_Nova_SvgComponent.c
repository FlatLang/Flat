#include <precompiled.h>
#include <nova/svg/nova_svg_Nova_SvgComponent.h>



nova_svg_Extension_VTable_SvgComponent nova_svg_Extension_VTable_SvgComponent_val =
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
	nova_svg_Nova_SvgComponent_0_Nova_generateOutput,
};


void nova_svg_Nova_SvgComponent_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_svg_Nova_SvgComponent* nova_svg_Nova_SvgComponent_Nova_construct(nova_svg_Nova_SvgComponent* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_svg_Nova_SvgComponent, this,);
	this->vtable = &nova_svg_Extension_VTable_SvgComponent_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_svg_Nova_SvgComponent_Nova_super(this, exceptionData);
	
	{
		nova_svg_Nova_SvgComponent_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_svg_Nova_SvgComponent_Nova_destroy(nova_svg_Nova_SvgComponent** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_svg_Nova_SvgComponentList_Nova_destroy(&(*this)->nova_svg_Nova_SvgComponent_Nova_children, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_svg_Nova_SvgComponent_2_Nova_this(nova_svg_Nova_SvgComponent* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_svg_Nova_SvgComponent_Nova_children = nova_svg_Nova_SvgComponentList_Nova_construct(0, exceptionData);
}

void nova_svg_Nova_SvgComponent_0_Nova_generateOutput(nova_svg_Nova_SvgComponent* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_svg_Nova_SvgComponent_Nova_file)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("This is wrong")));
}

void nova_svg_Nova_SvgComponent_Nova_super(nova_svg_Nova_SvgComponent* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_svg_Nova_SvgComponent_Nova_children = (nova_svg_Nova_SvgComponentList*)nova_null;
}

void nova_svg_Nova_SvgComponent_virtual0_Nova_generateOutput(nova_svg_Nova_SvgComponent* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_svg_Nova_SvgComponent_Nova_file)
{
	this->vtable->nova_svg_Nova_SvgComponent_virtual0_Nova_generateOutput((nova_svg_Nova_SvgComponent*)(this), exceptionData, nova_svg_Nova_SvgComponent_Nova_file);
}

