#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_Nova_SVGComponent.h>

nova_standard_svg_Extension_VTable_SVGComponent nova_standard_svg_Extension_VTable_SVGComponent_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_svg_Nova_SVGComponent_0_Nova_generateOutput,
};


void nova_standard_svg_Nova_SVGComponentNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_svg_Nova_SVGComponent* nova_standard_svg_Nova_SVGComponent_2_Nova_construct(nova_standard_svg_Nova_SVGComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_svg_Nova_SVGComponent, this,);
	this->vtable = &nova_standard_svg_Extension_VTable_SVGComponent_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_svg_Nova_SVGComponent_Nova_super(this, exceptionData);
	
	{
		nova_standard_svg_Nova_SVGComponent_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_svg_Nova_SVGComponent_Nova_destroy(nova_standard_svg_Nova_SVGComponent** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_svg_Nova_SVGComponentList_Nova_destroy(&(*this)->nova_standard_svg_Nova_SVGComponent_Nova_children, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_svg_Nova_SVGComponent_2_Nova_this(nova_standard_svg_Nova_SVGComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_svg_Nova_SVGComponent_Nova_children = nova_standard_svg_Nova_SVGComponentList_0_Nova_construct(0, exceptionData);
}

void nova_standard_svg_Nova_SVGComponent_0_Nova_generateOutput(nova_standard_svg_Nova_SVGComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* nova_standard_svg_Nova_SVGComponent_Nova_file)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "This is wrong"));
}

void nova_standard_svg_Nova_SVGComponent_Nova_super(nova_standard_svg_Nova_SVGComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_svg_Nova_SVGComponent_Nova_children = (nova_standard_svg_Nova_SVGComponentList*)nova_null;
}

