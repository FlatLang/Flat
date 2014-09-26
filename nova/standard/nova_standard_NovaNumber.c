#include <precompiled.h>
#include <nova/standard/nova_standard_NovaNumber.h>


nova_VTable_nova_standard_NovaNumber nova_VTable_nova_standard_NovaNumber_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
	nova_standard_NovaNumber_static_Novanull0_numDigits,
	nova_standard_NovaNumber_static_Novanull1_toString,
};

nova_standard_NovaNumber* nova_standard_NovaNumber_Novanull0_construct(nova_standard_NovaNumber* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaNumber, this,);
	this->vtable = &nova_VTable_nova_standard_NovaNumber_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaNumber_Novasuper(this, 0);
	
	{
		nova_standard_NovaNumber_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Number(nova_standard_NovaNumber** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_standard_NovaNumber_static_Novanull0_numDigits(nova_standard_NovaNumber* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novanumber)
{
	return -1;
}

nova_standard_NovaString* nova_standard_NovaNumber_static_Novanull1_toString(nova_standard_NovaNumber* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novavalue)
{
	return nova_standard_NovaString_Novaconstruct(0, exceptionData, "[Unimplemented]");
}

void nova_standard_NovaNumber_Novathis(nova_standard_NovaNumber* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaNumber_Novasuper(nova_standard_NovaNumber* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
