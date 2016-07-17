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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_ToStringStability_0_Nova_test,
};



void stabilitytest_Nova_ToStringStability_Nova_checkToString(stabilitytest_Nova_ToStringStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* stabilitytest_Nova_ToStringStability_Nova_type, nova_standard_primitive_number_Nova_Number* stabilitytest_Nova_ToStringStability_Nova_number, nova_standard_Nova_String* stabilitytest_Nova_ToStringStability_Nova_expected);
void stabilitytest_Nova_ToStringStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ToStringStability* stabilitytest_Nova_ToStringStability_Nova_ToStringStability(stabilitytest_Nova_ToStringStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ToStringStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_ToStringStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_ToStringStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_ToStringStability_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ToStringStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_ToStringStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_ToStringStability_Nova_destroy(stabilitytest_Nova_ToStringStability** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_ToStringStability_0_Nova_this(stabilitytest_Nova_ToStringStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ToStringStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_ToStringStability_Nova_program);
}

void stabilitytest_Nova_ToStringStability_0_Nova_test(stabilitytest_Nova_ToStringStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	double l1_Nova_d = 0;
	char l1_Nova_b = 0;
	int l1_Nova_i = 0;
	long_long l1_Nova_l = 0;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Checking toString implementations... "));
	l1_Nova_d = (double)(5.232);
	stabilitytest_Nova_ToStringStability_Nova_checkToString(this, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Double"), (nova_standard_primitive_number_Nova_Number*)(nova_standard_primitive_number_Nova_Double_Nova_Double(0, exceptionData, l1_Nova_d)), nova_standard_Nova_String_1_Nova_String(0, exceptionData, "5.232"));
	l1_Nova_b = 127;
	stabilitytest_Nova_ToStringStability_Nova_checkToString(this, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Byte"), (nova_standard_primitive_number_Nova_Number*)(nova_standard_primitive_number_Nova_Byte_Nova_Byte(0, exceptionData, l1_Nova_b)), nova_standard_Nova_String_1_Nova_String(0, exceptionData, "127"));
	l1_Nova_i = 2147483647;
	stabilitytest_Nova_ToStringStability_Nova_checkToString(this, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Int"), (nova_standard_primitive_number_Nova_Number*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_i)), nova_standard_Nova_String_1_Nova_String(0, exceptionData, "2147483647"));
	l1_Nova_l = (-9223372036854775807LL - 1);
	stabilitytest_Nova_ToStringStability_Nova_checkToString(this, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Long"), (nova_standard_primitive_number_Nova_Number*)(nova_standard_primitive_number_Nova_Long_Nova_Long(0, exceptionData, l1_Nova_l)), nova_standard_Nova_String_1_Nova_String(0, exceptionData, "-9223372036854775808"));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "OK"));
}

void stabilitytest_Nova_ToStringStability_Nova_checkToString(stabilitytest_Nova_ToStringStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* stabilitytest_Nova_ToStringStability_Nova_type, nova_standard_primitive_number_Nova_Number* stabilitytest_Nova_ToStringStability_Nova_number, nova_standard_Nova_String* stabilitytest_Nova_ToStringStability_Nova_expected)
{
	if (!nova_standard_operators_Nova_Equals_virtual0_Nova_equals((nova_standard_operators_Nova_Equals*)(nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)(stabilitytest_Nova_ToStringStability_Nova_number), exceptionData)), exceptionData, (nova_standard_Nova_Object*)(stabilitytest_Nova_ToStringStability_Nova_expected)))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(stabilitytest_Nova_ToStringStability_Nova_type), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, ".toString failed. expected "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(stabilitytest_Nova_ToStringStability_Nova_expected), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, " but received "), exceptionData, nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)(stabilitytest_Nova_ToStringStability_Nova_number), exceptionData))))));
	}
}

void stabilitytest_Nova_ToStringStability_0_Nova_super(stabilitytest_Nova_ToStringStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

