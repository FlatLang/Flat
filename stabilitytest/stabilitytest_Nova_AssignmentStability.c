#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_AssignmentStability.h>



stabilitytest_Extension_VTable_AssignmentStability stabilitytest_Extension_VTable_AssignmentStability_val =
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
	stabilitytest_Nova_AssignmentStability_Nova_test,
};


void stabilitytest_Nova_AssignmentStability_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_AssignmentStability* stabilitytest_Nova_AssignmentStability_Nova_construct(stabilitytest_Nova_AssignmentStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_AssignmentStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_AssignmentStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_AssignmentStability_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_AssignmentStability_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_AssignmentStability_Nova_this(this, exceptionData, stabilitytest_Nova_AssignmentStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_AssignmentStability_Nova_destroy(stabilitytest_Nova_AssignmentStability** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_AssignmentStability_Nova_this(stabilitytest_Nova_AssignmentStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_AssignmentStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_AssignmentStability_Nova_program);
}

void stabilitytest_Nova_AssignmentStability_Nova_test(stabilitytest_Nova_AssignmentStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	stabilitytest_Nova_ClassWithProperties* l1_Nova_props = (stabilitytest_Nova_ClassWithProperties*)nova_null;
	char l1_Nova_num = 0;
	char l1_Nova_a = 0;
	char l1_Nova_b = 0;
	char l1_Nova_c = 0;
	
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Checking embedded property assignments... ")));
	l1_Nova_props = stabilitytest_Nova_ClassWithProperties_Nova_construct(0, exceptionData);
	l1_Nova_num = (char)(stabilitytest_Nova_ClassWithProperties_Mutator_Nova_prop1(l1_Nova_props, exceptionData, stabilitytest_Nova_ClassWithProperties_Mutator_Nova_prop2(l1_Nova_props, exceptionData, 1)));
	if (l1_Nova_num != 1 || stabilitytest_Nova_ClassWithProperties_Accessor_Nova_prop1(l1_Nova_props, exceptionData) != 1 || stabilitytest_Nova_ClassWithProperties_Accessor_Nova_prop2(l1_Nova_props, exceptionData) != 1)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Did not return mutated property value correctly. Expected 1, 1, 1 but received ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, l1_Nova_num)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, stabilitytest_Nova_ClassWithProperties_Accessor_Nova_prop1(l1_Nova_props, exceptionData))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")), exceptionData, nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, stabilitytest_Nova_ClassWithProperties_Accessor_Nova_prop2(l1_Nova_props, exceptionData))))))));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("OK")));
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Checking embedded primitive assignments... ")));
	l1_Nova_a = l1_Nova_b = l1_Nova_c = 1;
	if (l1_Nova_a != 1 || l1_Nova_b != 1 || l1_Nova_c != 1)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Did not set assigned primitive values correctly. Expected 1, 1, 1 but received ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, l1_Nova_a)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, l1_Nova_b)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")), exceptionData, nova_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, l1_Nova_c)))))));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("OK")));
}

void stabilitytest_Nova_AssignmentStability_0_Nova_super(stabilitytest_Nova_AssignmentStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

