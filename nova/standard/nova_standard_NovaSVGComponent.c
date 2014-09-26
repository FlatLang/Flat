#include <precompiled.h>
#include <nova/standard/nova_standard_NovaSVGComponent.h>


nova_VTable_nova_standard_NovaSVGComponent nova_VTable_nova_standard_NovaSVGComponent_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
	nova_standard_NovaSVGComponent_Novanull0_generateOutput,
};

nova_standard_NovaSVGComponent* nova_standard_NovaSVGComponent_Novanull0_construct(nova_standard_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaSVGComponent, this,);
	this->vtable = &nova_VTable_nova_standard_NovaSVGComponent_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaSVGComponent_Novasuper(this, 0);
	
	{
		nova_standard_NovaSVGComponent_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_SVGComponent(nova_standard_NovaSVGComponent** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_SVGComponentList(&(*this)->nova_standard_NovaSVGComponent_Novachildren, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaSVGComponent_Novathis(nova_standard_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_NovaSVGComponent_Novachildren = nova_standard_NovaSVGComponentList_Novanull0_construct(0, exceptionData);
}

void nova_standard_NovaSVGComponent_Novanull0_generateOutput(nova_standard_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaFile* l0_Novafile)
{
	nova_standard_NovaConsole_static_Novanull0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "This is wrong"));
}

void nova_standard_NovaSVGComponent_Novasuper(nova_standard_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_NovaSVGComponent_Novachildren = (nova_standard_NovaSVGComponentList*)nova_null;
}
