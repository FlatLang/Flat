#include <precompiled.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Float.h>


nova_standard_primitive_number_Extension_VTable_Float nova_standard_primitive_number_Extension_VTable_Float_val =
{
	{
		(nova_standard_Nova_Object*(*)(nova_standard_operators_Nova_Multipliable*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_primitive_number_Nova_Float_1_Nova_multiply,
		0,
		0,
		0,
		0,
		0,
		0,
		(int(*)(nova_standard_datastruct_Nova_Comparable*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_primitive_number_Nova_Float_0_Nova_compareTo,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_primitive_number_Nova_Float_2_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_primitive_number_Nova_Float_0_Nova_numDigits,
	nova_standard_primitive_number_Nova_Float_0_Nova_compareTo,
	nova_standard_primitive_number_Nova_Float_1_Nova_multiply,
};


void nova_standard_primitive_number_Nova_FloatNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_number_Nova_Float* nova_standard_primitive_number_Nova_Float_Nova_construct(nova_standard_primitive_number_Nova_Float* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_value)
{
	CCLASS_NEW(nova_standard_primitive_number_Nova_Float, this,);
	this->vtable = &nova_standard_primitive_number_Extension_VTable_Float_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_primitive_Nova_Primitive_Nova_super((nova_standard_primitive_Nova_Primitive*)this, exceptionData);
	nova_standard_primitive_number_Nova_Number_2_Nova_super((nova_standard_primitive_number_Nova_Number*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_primitive_Nova_Primitive_4_Nova_this((nova_standard_primitive_Nova_Primitive*)(this), exceptionData);
	nova_standard_primitive_number_Nova_Number_7_Nova_this((nova_standard_primitive_number_Nova_Number*)(this), exceptionData);
	nova_standard_primitive_number_Nova_Float_4_Nova_super(this, exceptionData);
	
	{
		nova_standard_primitive_number_Nova_Float_Nova_this(this, exceptionData, l0_Nova_value);
	}
	
	return this;
}

void nova_standard_primitive_number_Nova_Float_Nova_destroy(nova_standard_primitive_number_Nova_Float** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_primitive_number_Nova_Float_Nova_this(nova_standard_primitive_number_Nova_Float* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_value)
{
	this->nova_standard_primitive_number_Nova_Float_Nova_value = (float)(l0_Nova_value);
}

int nova_standard_primitive_number_Nova_Float_0_Nova_numDigits(nova_standard_primitive_number_Nova_Float* this, nova_standard_exception_Nova_ExceptionData* exceptionData, float l0_Nova_number)
{
	return nova_standard_primitive_number_Nova_Double_0_Nova_numDigits(0, exceptionData, (double)(l0_Nova_number));
}

nova_standard_Nova_String* nova_standard_primitive_number_Nova_Float_1_Nova_toString(nova_standard_primitive_number_Nova_Float* this, nova_standard_exception_Nova_ExceptionData* exceptionData, float l0_Nova_value)
{
	return nova_standard_primitive_number_Nova_Double_1_Nova_toString(0, exceptionData, (double)(l0_Nova_value));
}

nova_standard_Nova_String* nova_standard_primitive_number_Nova_Float_2_Nova_toString(nova_standard_primitive_number_Nova_Float* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_primitive_number_Nova_Float_1_Nova_toString(this, exceptionData, this->nova_standard_primitive_number_Nova_Float_Nova_value);
}

float nova_standard_primitive_number_Nova_Float_0_Nova_compareTo(nova_standard_primitive_number_Nova_Float* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Float* l0_Nova_prim)
{
	float l1_Nova_other;
	
	l1_Nova_other = l0_Nova_prim->nova_standard_primitive_number_Nova_Float_Nova_value;
	return this->nova_standard_primitive_number_Nova_Float_Nova_value - l1_Nova_other;
}

float nova_standard_primitive_number_Nova_Float_1_Nova_multiply(nova_standard_primitive_number_Nova_Float* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Float* l0_Nova_prim)
{
	float l1_Nova_value;
	
	l1_Nova_value = l0_Nova_prim->nova_standard_primitive_number_Nova_Float_Nova_value;
	return this->nova_standard_primitive_number_Nova_Float_Nova_value * l1_Nova_value;
}

void nova_standard_primitive_number_Nova_Float_4_Nova_super(nova_standard_primitive_number_Nova_Float* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_primitive_number_Nova_Float_Nova_value = 0;
}

