#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_NovaSVGCircle.h>


nova_VTable_nova_standard_svg_NovaSVGCircle nova_VTable_nova_standard_svg_NovaSVGCircle_val =
{
	nova_standard_svg_NovaSVGCircle_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_svg_NovaSVGCircle_Nova0_generateOutput,
};
void nova_standard_svg_NovaSVGCircleNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_svg_NovaSVGCircle* nova_standard_svg_NovaSVGCircle_Novaconstruct(nova_standard_svg_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novax, double l0_Novay, int l0_Novar)
{
	CCLASS_NEW(nova_standard_svg_NovaSVGCircle, this,);
	this->vtable = &nova_VTable_nova_standard_svg_NovaSVGCircle_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_svg_NovaSVGComponent_Novasuper((nova_standard_svg_NovaSVGComponent*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_svg_NovaSVGComponent_Novathis((nova_standard_svg_NovaSVGComponent*)(this), exceptionData);
	nova_standard_svg_NovaSVGCircle_Novasuper(this, 0);
	
	{
		nova_standard_svg_NovaSVGCircle_Novathis(this, exceptionData, l0_Novax, l0_Novay, l0_Novar);
	}
	
	return this;
}

void nova_del_SVGCircle(nova_standard_svg_NovaSVGCircle** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_svg_NovaSVGCircle_Novathis(nova_standard_svg_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novax, double l0_Novay, int l0_Novar)
{
	this->nova_standard_svg_NovaSVGCircle_Novax = l0_Novax;
	this->nova_standard_svg_NovaSVGCircle_Novay = l0_Novay;
	this->nova_standard_svg_NovaSVGCircle_Novar = l0_Novar;
}

void nova_standard_svg_NovaSVGCircle_Nova0_generateOutput(nova_standard_svg_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaFile* l0_Novafile)
{
	nova_standard_NovaString* nova_local_0;
	nova_standard_NovaString* nova_local_1;
	nova_standard_NovaString* nova_local_2;
	
	nova_local_0 = nova_standard_primitive_number_NovaDouble_static_Nova0_toString(0, exceptionData, this->nova_standard_svg_NovaSVGCircle_Novax);
	nova_local_1 = nova_standard_primitive_number_NovaDouble_static_Nova0_toString(0, exceptionData, this->nova_standard_svg_NovaSVGCircle_Novay);
	nova_local_2 = nova_standard_primitive_number_NovaInt_static_Nova0_toString(0, exceptionData, this->nova_standard_svg_NovaSVGCircle_Novar);
	nova_standard_io_NovaFile_Novawrite(l0_Novafile, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "<circle cx=\""), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "\" cy=\""), exceptionData, nova_local_1->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_1, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "\" r=\""), exceptionData, nova_local_2->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_2, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "\" stroke=\"false\" fill=\"black\"/>\n"))))))));
}

nova_standard_NovaString* nova_standard_svg_NovaSVGCircle_Nova0_toString(nova_standard_svg_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaString* nova_local_0;
	nova_standard_NovaString* nova_local_1;
	nova_standard_NovaString* nova_local_2;
	
	nova_local_0 = nova_standard_primitive_number_NovaDouble_static_Nova0_toString(0, exceptionData, this->nova_standard_svg_NovaSVGCircle_Novax);
	nova_local_1 = nova_standard_primitive_number_NovaDouble_static_Nova0_toString(0, exceptionData, this->nova_standard_svg_NovaSVGCircle_Novay);
	nova_local_2 = nova_standard_primitive_number_NovaInt_static_Nova0_toString(0, exceptionData, this->nova_standard_svg_NovaSVGCircle_Novar);
	return nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "[Circle at ("), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, ", "), exceptionData, nova_local_1->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_1, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, ") with a radius of "), exceptionData, nova_local_2->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_2, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "]")))))));
}

void nova_standard_svg_NovaSVGCircle_Novasuper(nova_standard_svg_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_svg_NovaSVGCircle_Novax = 0;
	this->nova_standard_svg_NovaSVGCircle_Novay = 0;
	this->nova_standard_svg_NovaSVGCircle_Novar = 0;
}
