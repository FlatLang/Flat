#include <precompiled.h>
#include <nova/standard/nova_standard_NovaFloat.h>


nova_VTable_nova_standard_NovaFloat nova_VTable_nova_standard_NovaFloat_val =
{
	nova_standard_NovaFloat_Novanull3_toString,
	nova_standard_NovaObject_Novanull0_equals,
	nova_standard_NovaNumber_static_Novanull0_numDigits,
	nova_standard_NovaNumber_static_Novanull1_toString,
};

nova_standard_NovaFloat* nova_standard_NovaFloat_Novaconstruct(nova_standard_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novavalue)
{
	CCLASS_NEW(nova_standard_NovaFloat, this,);
	this->vtable = &nova_VTable_nova_standard_NovaFloat_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaNumber_Novasuper((nova_standard_NovaNumber*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaNumber_Novathis((nova_standard_NovaNumber*)(this), exceptionData);
	nova_standard_NovaFloat_Novasuper(this, 0);
	
	{
		nova_standard_NovaFloat_Novathis(this, exceptionData, l0_Novavalue);
	}
	
	return this;
}

void nova_del_Float(nova_standard_NovaFloat** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaFloat_Novathis(nova_standard_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novavalue)
{
	this->nova_standard_NovaFloat_Novavalue = (float)(l0_Novavalue);
}

int nova_standard_NovaFloat_static_NovanumDigits(nova_standard_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData, float l0_Novanumber)
{
	return nova_standard_NovaDouble_static_NovanumDigits(0, exceptionData, (double)(l0_Novanumber));
}

nova_standard_NovaString* nova_standard_NovaFloat_static_Novanull2_toString(nova_standard_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData, float l0_Novavalue)
{
	return nova_standard_NovaDouble_static_Novanull2_toString(0, exceptionData, (double)(l0_Novavalue));
}

nova_standard_NovaString* nova_standard_NovaFloat_Novanull3_toString(nova_standard_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaFloat_static_Novanull2_toString(this, exceptionData, this->nova_standard_NovaFloat_Novavalue);
}

void nova_standard_NovaFloat_Novasuper(nova_standard_NovaFloat* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_NovaFloat_Novavalue = 0;
}
