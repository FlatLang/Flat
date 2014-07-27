#include <precompiled.h>
#include "NovaNumber.h"


nova_VTable_Number nova_VTable_Number_val =
{
	nova_static_2_Number_numDigits,
	nova_static_3_Number_toString,
	nova_2_Object_toString,
	nova_2_Object_equals,
};

Number* nova_Number_Number(ExceptionData* exceptionData)
{
	CCLASS_NEW(Number, this,);
	
	this->vtable = &nova_VTable_Number_val;
	{
	}
	
	return this;
}

void nova_del_Number(Number** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_static_2_Number_numDigits(Number* this, ExceptionData* exceptionData, int nova_0_number)
{
}

String* nova_static_3_Number_toString(Number* this, ExceptionData* exceptionData, int nova_0_value)
{
}
