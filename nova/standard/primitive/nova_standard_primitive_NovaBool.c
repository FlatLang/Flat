#include <precompiled.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaBool.h>


nova_VTable_nova_standard_primitive_NovaBool nova_VTable_nova_standard_primitive_NovaBool_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_primitive_NovaBool_Nova2_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_primitive_NovaBoolNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_NovaBool* nova_standard_primitive_NovaBool_Nova1_construct(nova_standard_primitive_NovaBool* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
{
	CCLASS_NEW(nova_standard_primitive_NovaBool, this,);
	this->vtable = &nova_VTable_nova_standard_primitive_NovaBool_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_primitive_NovaPrimitive_Novasuper((nova_standard_primitive_NovaPrimitive*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_primitive_NovaPrimitive_Novathis((nova_standard_primitive_NovaPrimitive*)(this), exceptionData);
	nova_standard_primitive_NovaBool_Novasuper(this, exceptionData);
	
	{
		nova_standard_primitive_NovaBool_Novathis(this, exceptionData, l0_Novavalue);
	}
	
	return this;
}

void nova_del_Bool(nova_standard_primitive_NovaBool** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_primitive_NovaBool_Novathis(nova_standard_primitive_NovaBool* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
{
	this->nova_standard_primitive_NovaBool_Novavalue = l0_Novavalue;
}

nova_standard_NovaString* nova_standard_primitive_NovaBool_static_Nova1_toString(nova_standard_primitive_NovaBool* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
{
	if (l0_Novavalue)
	{
		return nova_standard_NovaString_Nova1_construct(0, exceptionData, "true");
	}
	return nova_standard_NovaString_Nova1_construct(0, exceptionData, "false");
}

nova_standard_NovaString* nova_standard_primitive_NovaBool_Nova2_toString(nova_standard_primitive_NovaBool* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_primitive_NovaBool_static_Nova1_toString(this, exceptionData, this->nova_standard_primitive_NovaBool_Novavalue);
}

void nova_standard_primitive_NovaBool_Novasuper(nova_standard_primitive_NovaBool* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_primitive_NovaBool_Novavalue = 0;
}
