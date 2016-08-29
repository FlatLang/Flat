#include <precompiled.h>
#include <nova/svg/nova_svg_Nova_SvgCircle.h>



nova_svg_Extension_VTable_SvgCircle nova_svg_Extension_VTable_SvgCircle_val =
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
	nova_svg_Nova_SvgCircle_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	nova_svg_Nova_SvgCircle_Nova_generateOutput,
};


void nova_svg_Nova_SvgCircle_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_svg_Nova_SvgCircle* nova_svg_Nova_SvgCircle_Nova_construct(nova_svg_Nova_SvgCircle* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_svg_Nova_SvgCircle_Nova_x, double nova_svg_Nova_SvgCircle_Nova_y, int nova_svg_Nova_SvgCircle_Nova_r)
{
	CCLASS_NEW(nova_svg_Nova_SvgCircle, this,);
	this->vtable = &nova_svg_Extension_VTable_SvgCircle_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_svg_Nova_SvgComponent_Nova_super((nova_svg_Nova_SvgComponent*)this, exceptionData);
	nova_svg_Nova_SvgCircle_0_Nova_super(this, exceptionData);
	
	{
		nova_svg_Nova_SvgCircle_1_Nova_this(this, exceptionData, nova_svg_Nova_SvgCircle_Nova_x, nova_svg_Nova_SvgCircle_Nova_y, nova_svg_Nova_SvgCircle_Nova_r);
	}
	
	return this;
}

void nova_svg_Nova_SvgCircle_Nova_destroy(nova_svg_Nova_SvgCircle** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	
	NOVA_FREE(*this);
}

void nova_svg_Nova_SvgCircle_1_Nova_this(nova_svg_Nova_SvgCircle* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_svg_Nova_SvgCircle_Nova_x, double nova_svg_Nova_SvgCircle_Nova_y, int nova_svg_Nova_SvgCircle_Nova_r)
{
	this->nova_svg_Nova_SvgCircle_Nova_x = nova_svg_Nova_SvgCircle_Nova_x;
	this->nova_svg_Nova_SvgCircle_Nova_y = nova_svg_Nova_SvgCircle_Nova_y;
	this->nova_svg_Nova_SvgCircle_Nova_r = nova_svg_Nova_SvgCircle_Nova_r;
}

void nova_svg_Nova_SvgCircle_Nova_generateOutput(nova_svg_Nova_SvgCircle* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_svg_Nova_SvgCircle_Nova_file)
{
	nova_io_Nova_File_Nova_write(nova_svg_Nova_SvgCircle_Nova_file, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("<circle cx=\"")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, this->nova_svg_Nova_SvgCircle_Nova_x)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\" cy=\"")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, this->nova_svg_Nova_SvgCircle_Nova_y)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\" r=\"")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, this->nova_svg_Nova_SvgCircle_Nova_r)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\" stroke=\"false\" fill=\"black\"/>\n")))))))));
}

nova_Nova_String* nova_svg_Nova_SvgCircle_0_Nova_toString(nova_svg_Nova_SvgCircle* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("[Circle at (")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, this->nova_svg_Nova_SvgCircle_Nova_x)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, this->nova_svg_Nova_SvgCircle_Nova_y)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(") with a radius of ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, this->nova_svg_Nova_SvgCircle_Nova_r)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("]"))))))));
}

void nova_svg_Nova_SvgCircle_0_Nova_super(nova_svg_Nova_SvgCircle* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_svg_Nova_SvgCircle_Nova_x = 0;
	this->nova_svg_Nova_SvgCircle_Nova_y = 0;
	this->nova_svg_Nova_SvgCircle_Nova_r = 0;
}

