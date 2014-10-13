#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_NovaSVGComponent.h>


nova_VTable_nova_standard_svg_NovaSVGComponent nova_VTable_nova_standard_svg_NovaSVGComponent_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_svg_NovaSVGComponent_Nova0_generateOutput,
};
void nova_standard_svg_NovaSVGComponentNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_svg_NovaSVGComponent* nova_standard_svg_NovaSVGComponent_Nova0_construct(nova_standard_svg_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_svg_NovaSVGComponent, this,);
	this->vtable = &nova_VTable_nova_standard_svg_NovaSVGComponent_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_svg_NovaSVGComponent_Novasuper(this, 0);
	
	{
		nova_standard_svg_NovaSVGComponent_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_SVGComponent(nova_standard_svg_NovaSVGComponent** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_SVGComponentList(&(*this)->nova_standard_svg_NovaSVGComponent_Novachildren, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_svg_NovaSVGComponent_Novathis(nova_standard_svg_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_svg_NovaSVGComponent_Novachildren = nova_standard_svg_NovaSVGComponentList_Nova0_construct(0, exceptionData);
}

void nova_standard_svg_NovaSVGComponent_Nova0_generateOutput(nova_standard_svg_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaFile* l0_Novafile)
{
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "This is wrong"));
}

void nova_standard_svg_NovaSVGComponent_Novasuper(nova_standard_svg_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_svg_NovaSVGComponent_Novachildren = (nova_standard_svg_NovaSVGComponentList*)nova_null;
}
