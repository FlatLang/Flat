#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_NovaSVG.h>


nova_VTable_nova_standard_svg_NovaSVG nova_VTable_nova_standard_svg_NovaSVG_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};

nova_standard_svg_NovaSVG* nova_standard_svg_NovaSVG_Nova0_construct(nova_standard_svg_NovaSVG* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_svg_NovaSVG, this,);
	this->vtable = &nova_VTable_nova_standard_svg_NovaSVG_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_svg_NovaSVG_Novasuper(this, 0);
	
	{
		nova_standard_svg_NovaSVG_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_SVG(nova_standard_svg_NovaSVG** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_SVGMainComponent(&(*this)->nova_standard_svg_NovaSVG_Novaroot, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_svg_NovaSVG_Novathis(nova_standard_svg_NovaSVG* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_svg_NovaSVG_Novaroot = nova_standard_svg_NovaSVGMainComponent_Nova0_construct(0, exceptionData);
}

void nova_standard_svg_NovaSVG_NovagenerateOutput(nova_standard_svg_NovaSVG* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaFile* l0_Novafile)
{
	this->nova_standard_svg_NovaSVG_Novaroot->vtable->nova_standard_svg_NovaSVGMainComponent_Novavirtual0_generateOutput(this->nova_standard_svg_NovaSVG_Novaroot, exceptionData, l0_Novafile);
}

void nova_standard_svg_NovaSVG_NovagenerateHTMLOutput(nova_standard_svg_NovaSVG* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaFile* l0_Novafile)
{
	nova_standard_io_NovaFile_Novawrite(l0_Novafile, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "<html>\n"));
	nova_standard_svg_NovaSVG_NovagenerateOutput(this, exceptionData, l0_Novafile);
	nova_standard_io_NovaFile_Novawrite(l0_Novafile, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "</html>"));
}

void nova_standard_svg_NovaSVG_Novasuper(nova_standard_svg_NovaSVG* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_svg_NovaSVG_Novaroot = (nova_standard_svg_NovaSVGMainComponent*)nova_null;
}
