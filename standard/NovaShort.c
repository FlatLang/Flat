#include <precompiled.h>
#include "NovaShort.h"


nova_VTable_Short nova_VTable_Short_val =
{
	nova_static_0_Short_numDigits,
	nova_static_1_Short_toString,
	nova_2_Short_toString,
};

Short* nova_Short_construct(Short* this, ExceptionData* exceptionData, short nova_0_value)
{
	CCLASS_NEW(Short, this,);
	this->vtable = &nova_VTable_Short_val;
	nova_Object_super((Object*)this, 0);
	nova_Number_super((Number*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Number_this((Number*)(this), exceptionData);
	nova_Short_super(this, 0);
	
	{
		nova_Short_this(this, exceptionData, nova_0_value);
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

void nova_Short_this(Short* this, ExceptionData* exceptionData, short nova_0_value)
{
	this->nova_Short_value = nova_0_value;
}

int nova_static_0_Short_numDigits(Short* this, ExceptionData* exceptionData, short nova_0_number)
{
	return nova_static_Long_numDigits(0, exceptionData, (long_long)(nova_0_number));
}

String* nova_static_1_Short_toString(Short* this, ExceptionData* exceptionData, short nova_0_value)
{
	return nova_static_2_Long_toString(0, exceptionData, (long_long)(nova_0_value));
}

String* nova_2_Short_toString(Short* this, ExceptionData* exceptionData)
{
	return this->vtable->nova_virtual_1_toString(this, exceptionData, this->nova_Short_value);
}

void nova_Short_super(Short* this, ExceptionData* exceptionData)
{
	this->nova_Short_value = 0;
}
