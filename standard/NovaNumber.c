#include <precompiled.h>
#include "NovaNumber.h"


nova_VTable_Number nova_VTable_Number_val =
{
	nova_static_0_Number_numDigits,
	nova_static_1_Number_toString,
	nova_0_Object_toString,
	nova_0_Object_equals,
};

Number* nova_0_Number_construct(Number* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Number, this,);
	this->vtable = &nova_VTable_Number_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Number_super(this, 0);
	
	{
		nova_Number_this(this, exceptionData);
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

int nova_static_0_Number_numDigits(Number* this, ExceptionData* exceptionData, int nova_0_number)
{
	return -1;
}

String* nova_static_1_Number_toString(Number* this, ExceptionData* exceptionData, int nova_0_value)
{
	return nova_String_construct(0, exceptionData, "[Unimplemented]");
}

void nova_Number_this(Number* this, ExceptionData* exceptionData)
{
}

void nova_Number_super(Number* this, ExceptionData* exceptionData)
{
}
