#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_NovaSVGMainComponent.h>


nova_VTable_nova_standard_svg_NovaSVGMainComponent nova_VTable_nova_standard_svg_NovaSVGMainComponent_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_svg_NovaSVGMainComponent_Nova0_generateOutput,
};

nova_standard_svg_NovaSVGMainComponent* nova_standard_svg_NovaSVGMainComponent_Nova0_construct(nova_standard_svg_NovaSVGMainComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_svg_NovaSVGMainComponent, this,);
	this->vtable = &nova_VTable_nova_standard_svg_NovaSVGMainComponent_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_svg_NovaSVGComponent_Novasuper((nova_standard_svg_NovaSVGComponent*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_svg_NovaSVGComponent_Novathis((nova_standard_svg_NovaSVGComponent*)(this), exceptionData);
	nova_standard_svg_NovaSVGMainComponent_Novasuper(this, 0);
	
	{
		nova_standard_svg_NovaSVGMainComponent_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_SVGMainComponent(nova_standard_svg_NovaSVGMainComponent** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_svg_NovaSVGMainComponent_Novathis(nova_standard_svg_NovaSVGMainComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_svg_NovaSVGComponent_Novachildren = nova_standard_svg_NovaSVGComponentList_Nova0_construct(0, exceptionData);
}

void nova_standard_svg_NovaSVGMainComponent_Nova0_generateOutput(nova_standard_svg_NovaSVGMainComponent* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaFile* l0_Novafile)
{
	nova_standard_io_NovaFile_Novawrite(l0_Novafile, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "<?xml version = '1.0' standalone = 'no'?>\n"), exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "<!DOCTYPE svg PUBLIC\"-//W3C//DTD SVG1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">"), exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "<svg width=\"1400\"px height=\"950\"px version=\"1.1\" preserveAspectRatio=\"none\">\n"))));
	nova_standard_svg_NovaSVGComponentList_NovagenerateOutput(this->nova_standard_svg_NovaSVGComponent_Novachildren, exceptionData, l0_Novafile);
	nova_standard_io_NovaFile_Novawrite(l0_Novafile, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "</svg>\n"));
}

void nova_standard_svg_NovaSVGMainComponent_Novasuper(nova_standard_svg_NovaSVGMainComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
