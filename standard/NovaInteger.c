#include <precompiled.h>

#include "NovaInteger.h"

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_0_value)
{
	CCLASS_NEW(Integer, this,);
	
	this->nova_Integer_value = 0;
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

int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_0_number)
{
	return nova_Long_numDigits(exceptionData, nova_0_number);
}

String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_0_value)
{
	return nova_Long_toAString(exceptionData, nova_0_value);
}

String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData)
{
	return nova_Integer_toAString(exceptionData, this->nova_Integer_value);
}
