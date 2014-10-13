#include <precompiled.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaFloat.h>


nova_VTable_nova_standard_primitive_number_NovaFloat nova_VTable_nova_standard_primitive_number_NovaFloat_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_primitive_number_NovaFloat_Nova2_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_primitive_number_NovaFloat_static_Nova0_numDigits,
};
void nova_standard_primitive_number_NovaFloatNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_number_NovaFloat* nova_standard_primitive_number_NovaFloat_Novaconstruct(nova_standard_primitive_number_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novavalue)
{
	CCLASS_NEW(nova_standard_primitive_number_NovaFloat, this,);
	this->vtable = &nova_VTable_nova_standard_primitive_number_NovaFloat_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_primitive_NovaPrimitive_Novasuper((nova_standard_primitive_NovaPrimitive*)this, 0);
	nova_standard_primitive_number_NovaNumber_Novasuper((nova_standard_primitive_number_NovaNumber*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_primitive_NovaPrimitive_Novathis((nova_standard_primitive_NovaPrimitive*)(this), exceptionData);
	nova_standard_primitive_number_NovaNumber_Novathis((nova_standard_primitive_number_NovaNumber*)(this), exceptionData);
	nova_standard_primitive_number_NovaFloat_Novasuper(this, 0);
	
	{
		nova_standard_primitive_number_NovaFloat_Novathis(this, exceptionData, l0_Novavalue);
	}
	
	return this;
}

void nova_del_Float(nova_standard_primitive_number_NovaFloat** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_primitive_number_NovaFloat_Novathis(nova_standard_primitive_number_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novavalue)
{
	this->nova_standard_primitive_number_NovaFloat_Novavalue = (float)(l0_Novavalue);
}

int nova_standard_primitive_number_NovaFloat_static_Nova0_numDigits(nova_standard_primitive_number_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData, float l0_Novanumber)
{
	return nova_standard_primitive_number_NovaDouble_static_Nova0_numDigits(0, exceptionData, (double)(l0_Novanumber));
}

nova_standard_NovaString* nova_standard_primitive_number_NovaFloat_static_Nova1_toString(nova_standard_primitive_number_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData, float l0_Novavalue)
{
	return nova_standard_primitive_number_NovaDouble_static_Nova1_toString(0, exceptionData, (double)(l0_Novavalue));
}

nova_standard_NovaString* nova_standard_primitive_number_NovaFloat_Nova2_toString(nova_standard_primitive_number_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_primitive_number_NovaFloat_static_Nova1_toString(this, exceptionData, this->nova_standard_primitive_number_NovaFloat_Novavalue);
}

void nova_standard_primitive_number_NovaFloat_Novasuper(nova_standard_primitive_number_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_primitive_number_NovaFloat_Novavalue = 0;
}
