#include <precompiled.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaChar.h>


nova_VTable_nova_standard_primitive_NovaChar nova_VTable_nova_standard_primitive_NovaChar_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_primitive_number_NovaNumber_static_Nova0_numDigits,
	nova_standard_primitive_NovaChar_static_NovatoString,
};
void nova_standard_primitive_NovaCharNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_NovaChar* nova_standard_primitive_NovaChar_Nova1_construct(nova_standard_primitive_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
{
	CCLASS_NEW(nova_standard_primitive_NovaChar, this,);
	this->vtable = &nova_VTable_nova_standard_primitive_NovaChar_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_primitive_number_NovaNumber_Novasuper((nova_standard_primitive_number_NovaNumber*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_primitive_number_NovaNumber_Novathis((nova_standard_primitive_number_NovaNumber*)(this), exceptionData);
	nova_standard_primitive_NovaChar_Novasuper(this, 0);
	
	{
		nova_standard_primitive_NovaChar_Novathis(this, exceptionData, l0_Novavalue);
	}
	
	return this;
}

void nova_del_Char(nova_standard_primitive_NovaChar** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_primitive_NovaChar_Novathis(nova_standard_primitive_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
{
	this->nova_standard_primitive_NovaChar_Novavalue = l0_Novavalue;
}

nova_standard_NovaString* nova_standard_primitive_NovaChar_static_NovatoString(nova_standard_primitive_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac)
{
	return nova_standard_NovaString_Nova0_construct(0, exceptionData, l0_Novac);
}

void nova_standard_primitive_NovaChar_Novasuper(nova_standard_primitive_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_primitive_NovaChar_Novavalue = 0;
}
