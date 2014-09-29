#include <precompiled.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaChar.h>


nova_VTable_nova_standard_primitive_NovaChar nova_VTable_nova_standard_primitive_NovaChar_val =
{
	nova_standard_primitive_NovaChar_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_primitive_number_NovaNumber_static_Nova0_numDigits,
	nova_standard_primitive_NovaChar_Nova1_toString,
};

nova_standard_primitive_NovaChar* nova_standard_primitive_NovaChar_Novaconstruct(nova_standard_primitive_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
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

nova_standard_NovaString* nova_standard_primitive_NovaChar_Nova1_toString(nova_standard_primitive_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac)
{
	char* l1_Novachars;
	
	l1_Novachars = (char*)NOVA_MALLOC(sizeof(char[1]));
	l1_Novachars[0] = l0_Novac;
	return nova_standard_NovaString_Novaconstruct(0, exceptionData, l1_Novachars);
}

nova_standard_NovaString* nova_standard_primitive_NovaChar_Nova0_toString(nova_standard_primitive_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->vtable->nova_standard_primitive_NovaChar_Novavirtual1_toString(this, exceptionData, this->nova_standard_primitive_NovaChar_Novavalue);
}

void nova_standard_primitive_NovaChar_Novasuper(nova_standard_primitive_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_primitive_NovaChar_Novavalue = 0;
}
