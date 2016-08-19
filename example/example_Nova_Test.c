#include <precompiled.h>
#include <example/example_Nova_Test.h>

example_Extension_VTable_Test example_Extension_VTable_Test_val =
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
};


void example_Nova_TestNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Test* example_Nova_Test_Nova_Test(example_Nova_Test* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_Test, this,);
	this->vtable = &example_Extension_VTable_Test_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_Test_Nova_super(this, exceptionData);
	
	{
		example_Nova_Test_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_Test_Nova_destroy(example_Nova_Test** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_Test_Nova_main(example_Nova_Test* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* example_Nova_Test_Nova_args)
{
	nova_standard_datastruct_list_Nova_Stack* l1_Nova_s = (nova_standard_datastruct_list_Nova_Stack*)nova_null;
	int l1_Nova_d = 0;
	
	l1_Nova_s = nova_standard_datastruct_list_Nova_Stack_Nova_Stack(0, exceptionData);
	nova_standard_datastruct_list_Nova_Stack_Nova_push(l1_Nova_s, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "How are you?")));
	nova_standard_datastruct_list_Nova_Stack_Nova_push(l1_Nova_s, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Larry")));
	nova_standard_datastruct_list_Nova_Stack_Nova_push(l1_Nova_s, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Hello")));
	while (!nova_standard_datastruct_list_Nova_Stack_Accessor_Nova_empty(l1_Nova_s, exceptionData))
	{
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, (nova_standard_Nova_String*)(nova_standard_datastruct_list_Nova_Stack_Nova_pop(l1_Nova_s, exceptionData)));
	}
	l1_Nova_d = (int)(5);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Comparison: "), exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_standard_primitive_number_Nova_Int_0_Nova_compareTo(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_d), exceptionData, 3))));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_Test_0_Nova_this(example_Nova_Test* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_Test_Nova_super(example_Nova_Test* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

