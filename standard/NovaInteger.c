#include <precompiled.h>
#include "NovaInteger.h"


nova_VTable_Integer nova_VTable_Integer_val =
{
	nova_static_1_Integer_numDigits,
	nova_static_1_Integer_toString,
	nova_2_Integer_toString,
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

int nova_static_1_Integer_numDigits(Integer* this, ExceptionData* exceptionData, int nova_0_number)
{
	return nova_static_1_Long_numDigits(0, exceptionData, (long_long)(nova_0_number));
}

String* nova_static_1_Integer_toString(Integer* this, ExceptionData* exceptionData, int nova_0_value)
{
	return nova_static_1_Long_toString(0, exceptionData, (long_long)(nova_0_value));
}

String* nova_2_Integer_toString(Integer* this, ExceptionData* exceptionData)
{
	return this->vtable->nova_virtual_1_toString(this, exceptionData, this->nova_Integer_value);
}
