#include <precompiled.h>
#include "NovaByte.h"


nova_VTable_Byte nova_VTable_Byte_val =
{
	nova_static_Byte_numDigits,
	nova_static_0_Byte_toString,
	nova_1_Byte_toString,
	nova_static_Number_numDigits,
	nova_static_Number_toString,
};

Byte* nova_Byte_construct(Byte* this, ExceptionData* exceptionData, char nova_0_value)
{
	CCLASS_NEW(Byte, this,);
	
	this->nova_Byte_value = 0;
	this->vtable = &nova_VTable_Byte_val;
	{
		this->nova_Byte_value = nova_0_value;
	}
	
	return this;
}

void nova_del_Byte(Byte** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_static_Byte_numDigits(Byte* this, ExceptionData* exceptionData, char nova_0_number)
{
	return nova_static_Long_numDigits(0, exceptionData, (long_long)(nova_0_number));
}

String* nova_static_0_Byte_toString(Byte* this, ExceptionData* exceptionData, char nova_0_value)
{
	return nova_static_0_Long_toString(0, exceptionData, (long_long)(nova_0_value));
}

String* nova_1_Byte_toString(Byte* this, ExceptionData* exceptionData)
{
	return this->vtable->nova_virtual_0_toString(this, exceptionData, this->nova_Byte_value);
}
