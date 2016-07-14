#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_LambdaStability.h>

stabilitytest_Extension_VTable_LambdaStability stabilitytest_Extension_VTable_LambdaStability_val =
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
	stabilitytest_Nova_LambdaStability_0_Nova_test,
};



char stabilitytest_Nova_LambdaStability_Nova_testLambda16(stabilitytest_Nova_LambdaStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* stabilitytest_Nova_LambdaStability_Nova_x);
nova_standard_Nova_Object* stabilitytest_Nova_LambdaStability_Nova_testLambda17(stabilitytest_Nova_LambdaStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* stabilitytest_Nova_LambdaStability_Nova_x, int stabilitytest_Nova_LambdaStability_Nova_i);
nova_standard_Nova_Object* stabilitytest_Nova_LambdaStability_Nova_testLambda18(stabilitytest_Nova_LambdaStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_LambdaStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_LambdaStability* stabilitytest_Nova_LambdaStability_Nova_LambdaStability(stabilitytest_Nova_LambdaStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_LambdaStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_LambdaStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_LambdaStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_LambdaStability_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_LambdaStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_LambdaStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_LambdaStability_Nova_destroy(stabilitytest_Nova_LambdaStability** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_LambdaStability_0_Nova_this(stabilitytest_Nova_LambdaStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_LambdaStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_LambdaStability_Nova_program);
}

void stabilitytest_Nova_LambdaStability_0_Nova_test(stabilitytest_Nova_LambdaStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String** l1_Nova_a;
	int* l1_Nova_b;
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list2;
	nova_standard_Nova_String* l1_Nova_mappedOutput;
	nova_standard_Nova_String* l1_Nova_mappedExpected;
	nova_standard_datastruct_list_Nova_LinkedList* l1_Nova_linked;
	nova_standard_datastruct_list_Nova_Array* l1_Nova_repeated;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0;
	nova_standard_Nova_String* l4_Nova_item;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Checking lambdas... "));
	l1_Nova_a = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 7);
	l1_Nova_a[0] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "this");
	l1_Nova_a[1] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "is");
	l1_Nova_a[2] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "a");
	l1_Nova_a[3] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test");
	l1_Nova_a[4] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "to");
	l1_Nova_a[5] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "see");
	l1_Nova_a[6] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "what shows up");
	l1_Nova_b = (int*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Int) * 7);
	l1_Nova_b[0] = (int)(1);
	l1_Nova_b[1] = (int)(2);
	l1_Nova_b[2] = (int)(3);
	l1_Nova_b[3] = (int)(4);
	l1_Nova_b[4] = (int)(5);
	l1_Nova_b[5] = (int)(6);
	l1_Nova_b[6] = (int)(7);
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_a), 7);
	l1_Nova_list2 = nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)&(l1_Nova_b), 7);
	l1_Nova_mappedOutput = (nova_standard_Nova_String*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&stabilitytest_Nova_LambdaStability_Nova_testLambda16, this)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&stabilitytest_Nova_LambdaStability_Nova_testLambda17, this)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", ")));
	l1_Nova_mappedExpected = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "0: this?, 1: test?, 2: what shows up?");
	if (!nova_standard_operators_Nova_Equals_virtual0_Nova_equals((nova_standard_operators_Nova_Equals*)(l1_Nova_mappedOutput), exceptionData, (nova_standard_Nova_Object*)(l1_Nova_mappedExpected)))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Failed first list map with 2 args. Expected '"), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(l1_Nova_mappedExpected), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "' but received '"), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(l1_Nova_mappedOutput), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "'"))))));
	}
	l1_Nova_linked = nova_standard_datastruct_list_Nova_LinkedList_Nova_LinkedList(0, exceptionData);
	nova_standard_datastruct_list_Nova_LinkedList_Nova_add(nova_standard_datastruct_list_Nova_LinkedList_Nova_add(nova_standard_datastruct_list_Nova_LinkedList_Nova_add(l1_Nova_linked, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test2"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test3")));
	if (!nova_standard_operators_Nova_Equals_virtual0_Nova_equals((nova_standard_operators_Nova_Equals*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_standard_datastruct_list_Nova_List*)(l1_Nova_linked), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test3, test2, test"))))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Failed reverse linked list"));
	}
	if (!nova_standard_operators_Nova_Equals_virtual0_Nova_equals((nova_standard_operators_Nova_Equals*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(l1_Nova_linked), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test, test2, test3"))))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Failed forward linked list"));
	}
	l1_Nova_repeated = (nova_standard_datastruct_list_Nova_Array*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&stabilitytest_Nova_LambdaStability_Nova_testLambda18, this));
	nova_local_0 = (nova_standard_datastruct_list_Nova_ArrayIterator*)(nova_standard_datastruct_list_Nova_Iterable_virtual_Accessor1_Nova_iterator((nova_standard_datastruct_list_Nova_Iterable*)(l1_Nova_repeated), exceptionData));
	while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
	{
		l4_Nova_item = (nova_standard_Nova_String*)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
		if (!nova_standard_operators_Nova_Equals_virtual0_Nova_equals((nova_standard_operators_Nova_Equals*)(l4_Nova_item), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test"))))
		{
			stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Map with no arguments failed"));
		}
	}
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "OK"));
}

char stabilitytest_Nova_LambdaStability_Nova_testLambda16(stabilitytest_Nova_LambdaStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* stabilitytest_Nova_LambdaStability_Nova_x)
{
	return (char)stabilitytest_Nova_LambdaStability_Nova_x->nova_standard_Nova_String_Nova_size >= 4;
}

nova_standard_Nova_Object* stabilitytest_Nova_LambdaStability_Nova_testLambda17(stabilitytest_Nova_LambdaStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* stabilitytest_Nova_LambdaStability_Nova_x, int stabilitytest_Nova_LambdaStability_Nova_i)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, stabilitytest_Nova_LambdaStability_Nova_i)), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, ": "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(stabilitytest_Nova_LambdaStability_Nova_x), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "?"))));
}

nova_standard_Nova_Object* stabilitytest_Nova_LambdaStability_Nova_testLambda18(stabilitytest_Nova_LambdaStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test");
}

void stabilitytest_Nova_LambdaStability_0_Nova_super(stabilitytest_Nova_LambdaStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

