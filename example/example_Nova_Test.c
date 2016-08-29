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
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void example_Nova_Test_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Test* example_Nova_Test_Nova_construct(example_Nova_Test* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_Test, this,);
	this->vtable = &example_Extension_VTable_Test_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_Test_Nova_super(this, exceptionData);
	
	{
		example_Nova_Test_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_Test_Nova_destroy(example_Nova_Test** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_Test_Nova_main(example_Nova_Test* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_Test_Nova_args)
{
	nova_datastruct_list_Nova_Stack* l1_Nova_s = (nova_datastruct_list_Nova_Stack*)nova_null;
	int l1_Nova_d = 0;
	
	l1_Nova_s = nova_datastruct_list_Nova_Stack_Nova_construct(0, exceptionData);
	nova_datastruct_list_Nova_Stack_Nova_push((nova_datastruct_list_Nova_Stack*)(l1_Nova_s), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("How are you?"))));
	nova_datastruct_list_Nova_Stack_Nova_push((nova_datastruct_list_Nova_Stack*)(l1_Nova_s), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Larry"))));
	nova_datastruct_list_Nova_Stack_Nova_push((nova_datastruct_list_Nova_Stack*)(l1_Nova_s), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Hello"))));
	while (!nova_datastruct_list_Nova_Stack_Accessor_Nova_empty((nova_datastruct_list_Nova_Stack*)(l1_Nova_s), exceptionData))
	{
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, (nova_Nova_String*)(nova_datastruct_list_Nova_Stack_Nova_pop((nova_datastruct_list_Nova_Stack*)(l1_Nova_s), exceptionData)));
	}
	l1_Nova_d = (int)(5);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Comparison: ")), exceptionData, nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_primitive_number_Nova_Int_0_Nova_compareTo(nova_primitive_number_Nova_Int_Nova_construct(0, exceptionData, l1_Nova_d), exceptionData, 3))));
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_Test_0_Nova_this(example_Nova_Test* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_Test_Nova_super(example_Nova_Test* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

