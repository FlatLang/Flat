#include <precompiled.h>
#include "NovaShort.h"


nova_VTable_Short nova_VTable_Short_val =
{
	nova_static_Short_numDigits,
	nova_static_0_Short_toString,
	nova_1_Short_toString,
	nova_static_Number_numDigits,
	nova_static_Number_toString,
};

Short* nova_Short_construct(Short* this, ExceptionData* exceptionData, short nova_0_value)
{
	CCLASS_NEW(Short, this,);
	
	this->nova_Short_value = 0;
	this->vtable = &nova_VTable_Short_val;
	{
		this->nova_Short_value = nova_0_value;
	}
	
	return this;
}

void nova_del_Short(Short** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_static_Short_numDigits(Short* this, ExceptionData* exceptionData, short nova_0_number)
{
	return nova_static_Long_numDigits(0, exceptionData, (long_long)(nova_0_number));
}

String* nova_static_0_Short_toString(Short* this, ExceptionData* exceptionData, short nova_0_value)
{
	return nova_static_0_Long_toString(0, exceptionData, (long_long)(nova_0_value));
}

String* nova_1_Short_toString(Short* this, ExceptionData* exceptionData)
{
	return this->vtable->nova_virtual_0_toString(this, exceptionData, this->nova_Short_value);
}
