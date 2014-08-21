#include <precompiled.h>
#include "NovaByte.h"


nova_VTable_Byte nova_VTable_Byte_val =
{
	nova_static_0_Byte_numDigits,
	nova_static_1_Byte_toString,
	nova_0_Byte_toString,
};

Byte* nova_Byte_construct(Byte* this, ExceptionData* exceptionData, char nova_0_value)
{
	CCLASS_NEW(Byte, this,);
	this->vtable = &nova_VTable_Byte_val;
	nova_Object_super((Object*)this, 0);
	nova_Number_super((Number*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Number_this((Number*)(this), exceptionData);
	nova_Byte_super(this, 0);
	
	{
		nova_Byte_this(this, exceptionData, nova_0_value);
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

void nova_Byte_this(Byte* this, ExceptionData* exceptionData, char nova_0_value)
{
	this->nova_Byte_value = nova_0_value;
}

int nova_static_0_Byte_numDigits(Byte* this, ExceptionData* exceptionData, char nova_0_number)
{
	return nova_static_Long_numDigits(0, exceptionData, (long_long)(nova_0_number));
}

String* nova_static_1_Byte_toString(Byte* this, ExceptionData* exceptionData, char nova_0_value)
{
	return nova_static_2_Long_toString(0, exceptionData, (long_long)(nova_0_value));
}

String* nova_0_Byte_toString(Byte* this, ExceptionData* exceptionData)
{
	return this->vtable->nova_virtual_1_toString(this, exceptionData, this->nova_Byte_value);
}

void nova_Byte_super(Byte* this, ExceptionData* exceptionData)
{
	this->nova_Byte_value = 0;
}
