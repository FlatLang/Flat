#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_Nova_SVGCircle.h>

nova_standard_svg_Extension_VTable_SVGCircle nova_standard_svg_Extension_VTable_SVGCircle_val =
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
	nova_standard_svg_Nova_SVGCircle_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_svg_Nova_SVGCircle_Nova_generateOutput,
};


void nova_standard_svg_Nova_SVGCircleNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_svg_Nova_SVGCircle* nova_standard_svg_Nova_SVGCircle_1_Nova_construct(nova_standard_svg_Nova_SVGCircle* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_svg_Nova_SVGCircle_Nova_x, double nova_standard_svg_Nova_SVGCircle_Nova_y, int nova_standard_svg_Nova_SVGCircle_Nova_r)
{
	CCLASS_NEW(nova_standard_svg_Nova_SVGCircle, this,);
	this->vtable = &nova_standard_svg_Extension_VTable_SVGCircle_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_svg_Nova_SVGComponent_Nova_super((nova_standard_svg_Nova_SVGComponent*)this, exceptionData);
	nova_standard_svg_Nova_SVGCircle_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_svg_Nova_SVGCircle_1_Nova_this(this, exceptionData, nova_standard_svg_Nova_SVGCircle_Nova_x, nova_standard_svg_Nova_SVGCircle_Nova_y, nova_standard_svg_Nova_SVGCircle_Nova_r);
	}
	
	return this;
}

void nova_standard_svg_Nova_SVGCircle_Nova_destroy(nova_standard_svg_Nova_SVGCircle** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_svg_Nova_SVGCircle_1_Nova_this(nova_standard_svg_Nova_SVGCircle* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_svg_Nova_SVGCircle_Nova_x, double nova_standard_svg_Nova_SVGCircle_Nova_y, int nova_standard_svg_Nova_SVGCircle_Nova_r)
{
	this->nova_standard_svg_Nova_SVGCircle_Nova_x = nova_standard_svg_Nova_SVGCircle_Nova_x;
	this->nova_standard_svg_Nova_SVGCircle_Nova_y = nova_standard_svg_Nova_SVGCircle_Nova_y;
	this->nova_standard_svg_Nova_SVGCircle_Nova_r = nova_standard_svg_Nova_SVGCircle_Nova_r;
}

void nova_standard_svg_Nova_SVGCircle_Nova_generateOutput(nova_standard_svg_Nova_SVGCircle* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* nova_standard_svg_Nova_SVGCircle_Nova_file)
{
	nova_standard_Nova_String* nova_local_0;
	nova_standard_Nova_String* nova_local_1;
	nova_standard_Nova_String* nova_local_2;
	
	nova_local_0 = nova_standard_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, this->nova_standard_svg_Nova_SVGCircle_Nova_x);
	nova_local_1 = nova_standard_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, this->nova_standard_svg_Nova_SVGCircle_Nova_y);
	nova_local_2 = nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, this->nova_standard_svg_Nova_SVGCircle_Nova_r);
	nova_standard_io_Nova_File_Nova_write(nova_standard_svg_Nova_SVGCircle_Nova_file, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "<circle cx=\""), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "\" cy=\""), exceptionData, nova_local_1->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_1, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "\" r=\""), exceptionData, nova_local_2->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_2, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "\" stroke=\"false\" fill=\"black\"/>\n"))))))));
}

nova_standard_Nova_String* nova_standard_svg_Nova_SVGCircle_1_Nova_toString(nova_standard_svg_Nova_SVGCircle* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* nova_local_0;
	nova_standard_Nova_String* nova_local_1;
	nova_standard_Nova_String* nova_local_2;
	
	nova_local_0 = nova_standard_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, this->nova_standard_svg_Nova_SVGCircle_Nova_x);
	nova_local_1 = nova_standard_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, this->nova_standard_svg_Nova_SVGCircle_Nova_y);
	nova_local_2 = nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, this->nova_standard_svg_Nova_SVGCircle_Nova_r);
	return nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "[Circle at ("), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "), exceptionData, nova_local_1->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_1, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ") with a radius of "), exceptionData, nova_local_2->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_2, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "]")))))));
}

void nova_standard_svg_Nova_SVGCircle_0_Nova_super(nova_standard_svg_Nova_SVGCircle* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_svg_Nova_SVGCircle_Nova_x = 0;
	this->nova_standard_svg_Nova_SVGCircle_Nova_y = 0;
	this->nova_standard_svg_Nova_SVGCircle_Nova_r = 0;
}

