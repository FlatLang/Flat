#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_ToStringStability.h>



stabilitytest_Extension_VTable_ToStringStability stabilitytest_Extension_VTable_ToStringStability_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	stabilitytest_Nova_ToStringStability_0_Nova_test,
};



void stabilitytest_Nova_ToStringStability_Nova_checkToString(stabilitytest_Nova_ToStringStability* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* stabilitytest_Nova_ToStringStability_Nova_type, nova_primitive_number_Nova_Number* stabilitytest_Nova_ToStringStability_Nova_number, nova_Nova_String* stabilitytest_Nova_ToStringStability_Nova_expected);
void stabilitytest_Nova_ToStringStability_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ToStringStability* stabilitytest_Nova_ToStringStability_Nova_ToStringStability(stabilitytest_Nova_ToStringStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ToStringStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_ToStringStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_ToStringStability_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_ToStringStability_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ToStringStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_ToStringStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_ToStringStability_Nova_destroy(stabilitytest_Nova_ToStringStability** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_ToStringStability_0_Nova_this(stabilitytest_Nova_ToStringStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ToStringStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_ToStringStability_Nova_program);
}

void stabilitytest_Nova_ToStringStability_0_Nova_test(stabilitytest_Nova_ToStringStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	double l1_Nova_d = 0;
	char l1_Nova_b = 0;
	int l1_Nova_i = 0;
	long_long l1_Nova_l = 0;
	
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Checking toString implementations... ")));
	l1_Nova_d = (double)(5.232);
	stabilitytest_Nova_ToStringStability_Nova_checkToString(this, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Double")), (nova_primitive_number_Nova_Number*)(nova_primitive_number_Nova_Double_Nova_Double(0, exceptionData, l1_Nova_d)), nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("5.232")));
	l1_Nova_b = 127;
	stabilitytest_Nova_ToStringStability_Nova_checkToString(this, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Byte")), (nova_primitive_number_Nova_Number*)(nova_primitive_number_Nova_Byte_Nova_Byte(0, exceptionData, l1_Nova_b)), nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("127")));
	l1_Nova_i = 2147483647;
	stabilitytest_Nova_ToStringStability_Nova_checkToString(this, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Int")), (nova_primitive_number_Nova_Number*)(nova_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_i)), nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("2147483647")));
	l1_Nova_l = (-9223372036854775807LL - 1);
	stabilitytest_Nova_ToStringStability_Nova_checkToString(this, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Long")), (nova_primitive_number_Nova_Number*)(nova_primitive_number_Nova_Long_Nova_Long(0, exceptionData, l1_Nova_l)), nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("-9223372036854775808")));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("OK")));
}

void stabilitytest_Nova_ToStringStability_Nova_checkToString(stabilitytest_Nova_ToStringStability* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* stabilitytest_Nova_ToStringStability_Nova_type, nova_primitive_number_Nova_Number* stabilitytest_Nova_ToStringStability_Nova_number, nova_Nova_String* stabilitytest_Nova_ToStringStability_Nova_expected)
{
	if (!nova_operators_Nova_Equals_virtual0_Nova_equals((nova_operators_Nova_Equals*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(stabilitytest_Nova_ToStringStability_Nova_number), exceptionData)), exceptionData, (nova_Nova_Object*)(stabilitytest_Nova_ToStringStability_Nova_expected)))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(stabilitytest_Nova_ToStringStability_Nova_type), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, (char*)(".toString failed. expected ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(stabilitytest_Nova_ToStringStability_Nova_expected), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, (char*)(" but received ")), exceptionData, nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(stabilitytest_Nova_ToStringStability_Nova_number), exceptionData))))));
	}
}

void stabilitytest_Nova_ToStringStability_0_Nova_super(stabilitytest_Nova_ToStringStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

