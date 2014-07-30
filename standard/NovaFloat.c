#include <precompiled.h>
#include "NovaFloat.h"


nova_VTable_Float nova_VTable_Float_val =
{
	nova_2_Float_toString,
	nova_static_2_Number_numDigits,
	nova_static_3_Number_toString,
};

Float* nova_Float_Float(Float* this, ExceptionData* exceptionData, int nova_0_value)
{
	CCLASS_NEW(Float, this,);
	
	this->nova_Float_value = 0;
	this->vtable = &nova_VTable_Float_val;
	{
		this->nova_Float_value = (float)(nova_0_value);
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

int nova_static_1_Float_numDigits(Float* this, ExceptionData* exceptionData, float nova_0_number)
{
	return nova_static_1_Double_numDigits(0, exceptionData, (double)(nova_0_number));
}

String* nova_static_1_Float_toString(Float* this, ExceptionData* exceptionData, float nova_0_value)
{
	return nova_static_1_Double_toString(0, exceptionData, (double)(nova_0_value));
}

String* nova_2_Float_toString(Float* this, ExceptionData* exceptionData)
{
	return nova_static_1_Float_toString(this, exceptionData, this->nova_Float_value);
}
