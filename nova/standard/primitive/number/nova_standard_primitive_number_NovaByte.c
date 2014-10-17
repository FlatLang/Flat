#include <precompiled.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaByte.h>


nova_VTable_nova_standard_primitive_number_NovaByte nova_VTable_nova_standard_primitive_number_NovaByte_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_primitive_number_NovaByte_Nova2_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_primitive_number_NovaByte_static_NovanumDigits,
};
void nova_standard_primitive_number_NovaByteNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_number_NovaByte* nova_standard_primitive_number_NovaByte_Novaconstruct(nova_standard_primitive_number_NovaByte* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
{
	CCLASS_NEW(nova_standard_primitive_number_NovaByte, this,);
	this->vtable = &nova_VTable_nova_standard_primitive_number_NovaByte_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_primitive_NovaPrimitive_Novasuper((nova_standard_primitive_NovaPrimitive*)this, exceptionData);
	nova_standard_primitive_number_NovaNumber_Novasuper((nova_standard_primitive_number_NovaNumber*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_primitive_NovaPrimitive_Novathis((nova_standard_primitive_NovaPrimitive*)(this), exceptionData);
	nova_standard_primitive_number_NovaNumber_Novathis((nova_standard_primitive_number_NovaNumber*)(this), exceptionData);
	nova_standard_primitive_number_NovaByte_Novasuper(this, exceptionData);
	
	{
		nova_standard_primitive_number_NovaByte_Novathis(this, exceptionData, l0_Novavalue);
	}
	
	return this;
}

void nova_del_Byte(nova_standard_primitive_number_NovaByte** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_primitive_number_NovaByte_Novathis(nova_standard_primitive_number_NovaByte* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
{
	this->nova_standard_primitive_number_NovaByte_Novavalue = l0_Novavalue;
}

int nova_standard_primitive_number_NovaByte_static_NovanumDigits(nova_standard_primitive_number_NovaByte* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novanumber)
{
	return nova_standard_primitive_number_NovaLong_static_Nova0_numDigits(0, exceptionData, (long)(l0_Novanumber));
}

nova_standard_NovaString* nova_standard_primitive_number_NovaByte_static_Nova1_toString(nova_standard_primitive_number_NovaByte* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
{
	return nova_standard_primitive_number_NovaLong_static_Nova1_toString(0, exceptionData, (long)(l0_Novavalue));
}

nova_standard_NovaString* nova_standard_primitive_number_NovaByte_Nova2_toString(nova_standard_primitive_number_NovaByte* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_primitive_number_NovaByte_static_Nova1_toString(this, exceptionData, this->nova_standard_primitive_number_NovaByte_Novavalue);
}

void nova_standard_primitive_number_NovaByte_Novasuper(nova_standard_primitive_number_NovaByte* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_primitive_number_NovaByte_Novavalue = 0;
}
