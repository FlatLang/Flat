#include <precompiled.h>
#include <nova/standard/nova_standard_NovaShort.h>


nova_VTable_nova_standard_NovaShort nova_VTable_nova_standard_NovaShort_val =
{
	nova_standard_NovaShort_Novanull2_toString,
	nova_standard_NovaObject_Novanull0_equals,
	nova_standard_NovaShort_static_Novanull0_numDigits,
	nova_standard_NovaShort_static_Novanull1_toString,
};

nova_standard_NovaShort* nova_standard_NovaShort_Novaconstruct(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novavalue)
{
	CCLASS_NEW(nova_standard_NovaShort, this,);
	this->vtable = &nova_VTable_nova_standard_NovaShort_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaNumber_Novasuper((nova_standard_NovaNumber*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaNumber_Novathis((nova_standard_NovaNumber*)(this), exceptionData);
	nova_standard_NovaShort_Novasuper(this, 0);
	
	{
		nova_standard_NovaShort_Novathis(this, exceptionData, l0_Novavalue);
	}
	
	return this;
}

void nova_del_Short(nova_standard_NovaShort** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaShort_Novathis(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novavalue)
{
	this->nova_standard_NovaShort_Novavalue = l0_Novavalue;
}

int nova_standard_NovaShort_static_Novanull0_numDigits(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novanumber)
{
	return nova_standard_NovaLong_static_NovanumDigits(0, exceptionData, (long_long)(l0_Novanumber));
}

nova_standard_NovaString* nova_standard_NovaShort_static_Novanull1_toString(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novavalue)
{
	return nova_standard_NovaLong_static_Novanull2_toString(0, exceptionData, (long_long)(l0_Novavalue));
}

nova_standard_NovaString* nova_standard_NovaShort_Novanull2_toString(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->vtable->nova_standard_NovaShort_static_Novavirtual1_toString(this, exceptionData, this->nova_standard_NovaShort_Novavalue);
}

void nova_standard_NovaShort_Novasuper(nova_standard_NovaShort* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_NovaShort_Novavalue = 0;
}
