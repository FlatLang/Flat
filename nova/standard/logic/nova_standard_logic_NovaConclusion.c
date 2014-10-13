#include <precompiled.h>
#include <nova/standard/logic/nova_standard_logic_NovaConclusion.h>


nova_VTable_nova_standard_logic_NovaConclusion nova_VTable_nova_standard_logic_NovaConclusion_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_logic_NovaStatement_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_NovaString* nova_standard_logic_NovaStatement_Novastatement;
	nova_standard_datastruct_NovaArrayList* nova_standard_logic_NovaStatement_Novacomponents;
	
)
void nova_standard_logic_NovaConclusionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_logic_NovaConclusion* nova_standard_logic_NovaConclusion_Novaconstruct(nova_standard_logic_NovaConclusion* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastatement)
{
	CCLASS_NEW(nova_standard_logic_NovaConclusion, this);
	this->vtable = &nova_VTable_nova_standard_logic_NovaConclusion_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_logic_NovaStatement_Novasuper((nova_standard_logic_NovaStatement*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_logic_NovaStatement_Novathis((nova_standard_logic_NovaStatement*)(this), exceptionData, l0_Novastatement);
	nova_standard_logic_NovaConclusion_Novasuper(this, 0);
	
	{
		nova_standard_logic_NovaConclusion_Novathis(this, exceptionData, l0_Novastatement);
	}
	
	return this;
}

void nova_del_Conclusion(nova_standard_logic_NovaConclusion** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_logic_NovaConclusion_Novathis(nova_standard_logic_NovaConclusion* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastatement)
{
	nova_standard_logic_NovaStatement_Novathis((nova_standard_logic_NovaStatement*)(this), exceptionData, l0_Novastatement);
}

void nova_standard_logic_NovaConclusion_Novasuper(nova_standard_logic_NovaConclusion* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
