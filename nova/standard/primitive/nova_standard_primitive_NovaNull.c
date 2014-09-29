#include <precompiled.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaNull.h>


nova_VTable_nova_standard_primitive_NovaNull nova_VTable_nova_standard_primitive_NovaNull_val =
{
	nova_standard_primitive_NovaNull_Nova0_toString,
	nova_standard_NovaString_Nova0_equals,
	nova_standard_primitive_NovaNull_Nova0_concat,
};
CCLASS_PRIVATE
(
	char* nova_standard_NovaString_Novadata;
	
)

nova_standard_primitive_NovaNull* nova_standard_primitive_NovaNull_Nova0_construct(nova_standard_primitive_NovaNull* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_primitive_NovaNull, this);
	this->vtable = &nova_VTable_nova_standard_primitive_NovaNull_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaString_Novasuper((nova_standard_NovaString*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_primitive_NovaNull_Novasuper(this, 0);
	
	{
		nova_standard_primitive_NovaNull_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Null(nova_standard_primitive_NovaNull** this, nova_standard_exception_NovaExceptionData* exceptionData)
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

nova_standard_NovaString* nova_standard_primitive_NovaNull_Nova0_toString(nova_standard_primitive_NovaNull* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_Novaconstruct(0, exceptionData, "null");
}

nova_standard_NovaString* nova_standard_primitive_NovaNull_Nova0_concat(nova_standard_primitive_NovaNull* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaother)
{
	return nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "null"), exceptionData, l0_Novaother);
}

void nova_standard_primitive_NovaNull_Novathis(nova_standard_primitive_NovaNull* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_primitive_NovaNull_Novasuper(nova_standard_primitive_NovaNull* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
