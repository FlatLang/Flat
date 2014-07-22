#include <precompiled.h>
#include "NovaInteger.h"


nova_VTable_Integer nova_VTable_Integer_val =
{
	nova_static_Integer_numDigits,
	nova_static_Integer_toAString,
	nova_Integer_toString,
};

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_0_value)
{
	CCLASS_NEW(Integer, this,);
	
	this->nova_Integer_value = 0;
	this->vtable = &nova_VTable_Integer_val;
	{
		this->nova_Integer_value = nova_0_value;
	}
	
	return this;
}

void nova_del_Integer(Integer** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_static_Integer_numDigits(Integer* this, ExceptionData* exceptionData, int nova_0_number)
{
	return nova_static_Long_numDigits((Object*)0, exceptionData, nova_0_number);
}

String* nova_static_Integer_toAString(Integer* this, ExceptionData* exceptionData, int nova_0_value)
{
	return nova_static_Long_toAString((Object*)0, exceptionData, nova_0_value);
}

String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData)
{
	return this->vtable->nova_virtual_toAString(this, exceptionData, this->nova_Integer_value);
}
