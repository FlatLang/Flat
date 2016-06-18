#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_StabilityTestCase.h>

stabilitytest_Extension_VTable_StabilityTestCase stabilitytest_Extension_VTable_StabilityTestCase_val =
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
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_StabilityTestCase_0_Nova_test,
};


void stabilitytest_Nova_StabilityTestCaseNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_StabilityTestCase* stabilitytest_Nova_StabilityTestCase_0_Nova_construct(stabilitytest_Nova_StabilityTestCase* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_StabilityTestCase_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_StabilityTestCase, this,);
	this->vtable = &stabilitytest_Extension_VTable_StabilityTestCase_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_StabilityTestCase_0_Nova_this(this, exceptionData, stabilitytest_Nova_StabilityTestCase_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_StabilityTestCase_Nova_destroy(stabilitytest_Nova_StabilityTestCase** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	stabilitytest_Nova_StabilityTest_Nova_destroy(&(*this)->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData);
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_StabilityTestCase_0_Nova_this(stabilitytest_Nova_StabilityTestCase* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_StabilityTestCase_Nova_program)
{
	this->stabilitytest_Nova_StabilityTestCase_Nova_program = stabilitytest_Nova_StabilityTestCase_Nova_program;
}

void stabilitytest_Nova_StabilityTestCase_0_Nova_test(stabilitytest_Nova_StabilityTestCase* this, nova_standard_exception_Nova_ExceptionData* exceptionData){}
void stabilitytest_Nova_StabilityTestCase_Nova_super(stabilitytest_Nova_StabilityTestCase* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->stabilitytest_Nova_StabilityTestCase_Nova_program = (stabilitytest_Nova_StabilityTest*)nova_null;
}

