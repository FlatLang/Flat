#include <precompiled.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaChar.h>


nova_VTable_nova_standard_primitive_number_NovaChar nova_VTable_nova_standard_primitive_number_NovaChar_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_primitive_number_NovaChar_Nova2_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_primitive_number_NovaNumber_static_Nova0_numDigits,
};
void nova_standard_primitive_number_NovaCharNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_number_NovaChar* nova_standard_primitive_number_NovaChar_Nova3_construct(nova_standard_primitive_number_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
{
	CCLASS_NEW(nova_standard_primitive_number_NovaChar, this,);
	this->vtable = &nova_VTable_nova_standard_primitive_number_NovaChar_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_primitive_NovaPrimitive_Novasuper((nova_standard_primitive_NovaPrimitive*)this, exceptionData);
	nova_standard_primitive_number_NovaNumber_Novasuper((nova_standard_primitive_number_NovaNumber*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_primitive_NovaPrimitive_Novathis((nova_standard_primitive_NovaPrimitive*)(this), exceptionData);
	nova_standard_primitive_number_NovaNumber_Novathis((nova_standard_primitive_number_NovaNumber*)(this), exceptionData);
	nova_standard_primitive_number_NovaChar_Novasuper(this, exceptionData);
	
	{
		nova_standard_primitive_number_NovaChar_Novathis(this, exceptionData, l0_Novavalue);
	}
	
	return this;
}

void nova_del_Char(nova_standard_primitive_number_NovaChar** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_primitive_number_NovaChar_Novathis(nova_standard_primitive_number_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novavalue)
{
	this->nova_standard_primitive_number_NovaChar_Novavalue = l0_Novavalue;
}

nova_standard_NovaString* nova_standard_primitive_number_NovaChar_static_Nova1_toString(nova_standard_primitive_number_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac)
{
	return nova_standard_NovaString_Nova0_construct(0, exceptionData, l0_Novac);
}

nova_standard_NovaString* nova_standard_primitive_number_NovaChar_Nova2_toString(nova_standard_primitive_number_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_primitive_number_NovaChar_static_Nova1_toString(this, exceptionData, this->nova_standard_primitive_number_NovaChar_Novavalue);
}

char nova_standard_primitive_number_NovaChar_Nova0_toLowerCase(nova_standard_primitive_number_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 0;
}

char nova_standard_primitive_number_NovaChar_static_Nova1_toLowerCase(nova_standard_primitive_number_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac)
{
	int l1_Novaid;
	
	l1_Novaid = (int)l0_Novac;
	if (l1_Novaid >= 65 && l1_Novaid <= 90)
	{
		return (char)(l1_Novaid + 32);
	}
	return l0_Novac;
}

char nova_standard_primitive_number_NovaChar_static_NovatoUpperCase(nova_standard_primitive_number_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac)
{
	int l1_Novaid;
	
	l1_Novaid = (int)l0_Novac;
	if (l1_Novaid >= 97 && l1_Novaid <= 122)
	{
		return (char)(l1_Novaid - 32);
	}
	return l0_Novac;
}

void nova_standard_primitive_number_NovaChar_Novasuper(nova_standard_primitive_number_NovaChar* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_primitive_number_NovaChar_Novavalue = 0;
}
