#include <precompiled.h>
#include "NovaInt.h"


nova_VTable_Int nova_VTable_Int_val =
{
	nova_static_Int_numDigits,
	nova_static_0_Int_toString,
	nova_1_Int_toString,
};

Int* nova_Int_construct(Int* this, ExceptionData* exceptionData, int nova_0_value)
{
	CCLASS_NEW(Int, this,);
	
	this->nova_Int_value = 0;
	this->vtable = &nova_VTable_Int_val;
	{
		this->nova_Int_value = nova_0_value;
	}
	
	return this;
}

void nova_del_Int(Int** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_static_Int_numDigits(Int* this, ExceptionData* exceptionData, int nova_0_number)
{
	return nova_static_Long_numDigits(0, exceptionData, (long_long)(nova_0_number));
}

String* nova_static_0_Int_toString(Int* this, ExceptionData* exceptionData, int nova_0_value)
{
	return nova_static_0_Long_toString(0, exceptionData, (long_long)(nova_0_value));
}

String* nova_1_Int_toString(Int* this, ExceptionData* exceptionData)
{
	return this->vtable->nova_virtual_0_toString(this, exceptionData, this->nova_Int_value);
}
