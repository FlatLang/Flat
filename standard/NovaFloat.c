#include <precompiled.h>
#include "NovaFloat.h"


nova_VTable_Float nova_VTable_Float_val =
{
	nova_3_Float_toString,
	nova_static_0_Number_numDigits,
	nova_static_1_Number_toString,
};

Float* nova_Float_construct(Float* this, ExceptionData* exceptionData, int nova_0_value)
{
	CCLASS_NEW(Float, this,);
	this->vtable = &nova_VTable_Float_val;
	nova_Object_super((Object*)this, 0);
	nova_Number_super((Number*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Number_this((Number*)(this), exceptionData);
	nova_Float_super(this, 0);
	
	{
		nova_Float_this(this, exceptionData, nova_0_value);
	}
	
	return this;
}

void nova_del_Float(Float** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_Float_this(Float* this, ExceptionData* exceptionData, int nova_0_value)
{
	this->nova_Float_value = (float)(nova_0_value);
}

int nova_static_Float_numDigits(Float* this, ExceptionData* exceptionData, float nova_0_number)
{
	return nova_static_Double_numDigits(0, exceptionData, (double)(nova_0_number));
}

String* nova_static_2_Float_toString(Float* this, ExceptionData* exceptionData, float nova_0_value)
{
	return nova_static_2_Double_toString(0, exceptionData, (double)(nova_0_value));
}

String* nova_3_Float_toString(Float* this, ExceptionData* exceptionData)
{
	return nova_static_2_Float_toString(this, exceptionData, this->nova_Float_value);
}

void nova_Float_super(Float* this, ExceptionData* exceptionData)
{
	this->nova_Float_value = 0;
}
