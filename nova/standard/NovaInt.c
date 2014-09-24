#include <precompiled.h>
#include "NovaInt.h"


nova_VTable_Int nova_VTable_Int_val =
{
	nova_2_Int_toString,
	nova_0_Object_equals,
	nova_static_0_Int_numDigits,
	nova_static_1_Int_toString,
};

Int* nova_Int_construct(Int* this, ExceptionData* exceptionData, int nova_0_value)
{
	CCLASS_NEW(Int, this,);
	this->vtable = &nova_VTable_Int_val;
	nova_Object_super((Object*)this, 0);
	nova_Number_super((Number*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Number_this((Number*)(this), exceptionData);
	nova_Int_super(this, 0);
	
	{
		nova_Int_this(this, exceptionData, nova_0_value);
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

void nova_Int_this(Int* this, ExceptionData* exceptionData, int nova_0_value)
{
	this->nova_Int_value = nova_0_value;
}

int nova_static_0_Int_numDigits(Int* this, ExceptionData* exceptionData, int nova_0_number)
{
	return nova_static_Long_numDigits(0, exceptionData, (long_long)(nova_0_number));
}

String* nova_static_1_Int_toString(Int* this, ExceptionData* exceptionData, int nova_0_value)
{
	return nova_static_2_Long_toString(0, exceptionData, (long_long)(nova_0_value));
}

String* nova_2_Int_toString(Int* this, ExceptionData* exceptionData)
{
	return this->vtable->nova_virtual_1_toString(this, exceptionData, this->nova_Int_value);
}

void nova_Int_super(Int* this, ExceptionData* exceptionData)
{
	this->nova_Int_value = 0;
}
