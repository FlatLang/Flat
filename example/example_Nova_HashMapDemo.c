#include <precompiled.h>
#include <example/example_Nova_HashMapDemo.h>

example_Extension_VTable_HashMapDemo example_Extension_VTable_HashMapDemo_val =
{
	{
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
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
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_2_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void example_Nova_HashMapDemoNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_HashMapDemo* example_Nova_HashMapDemo_Nova_HashMapDemo(example_Nova_HashMapDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_HashMapDemo, this,);
	this->vtable = &example_Extension_VTable_HashMapDemo_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_HashMapDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_HashMapDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_HashMapDemo_Nova_destroy(example_Nova_HashMapDemo** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_HashMapDemo_Nova_main(example_Nova_HashMapDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_HashMapDemo_Nova_args)
{
	nova_standard_datastruct_Nova_HashMap* l1_Nova_words;
	int* l1_Nova_nums;
	
	l1_Nova_words = nova_standard_datastruct_Nova_HashMap_Nova_HashMap(0, exceptionData);
	l1_Nova_nums = (int*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Int) * 6);
	l1_Nova_nums[0] = (int)(0);
	l1_Nova_nums[1] = (int)(1);
	l1_Nova_nums[2] = (int)(2);
	l1_Nova_nums[3] = (int)(3);
	l1_Nova_nums[4] = (int)(4);
	l1_Nova_nums[5] = (int)(5);
	nova_standard_datastruct_Nova_HashMap_virtual0_Nova_put((nova_standard_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_nums[0])), (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Zero")));
	nova_standard_datastruct_Nova_HashMap_virtual0_Nova_put((nova_standard_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_nums[1])), (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "One")));
	nova_standard_datastruct_Nova_HashMap_virtual0_Nova_put((nova_standard_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_nums[2])), (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Two")));
	nova_standard_datastruct_Nova_HashMap_virtual0_Nova_put((nova_standard_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_nums[3])), (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Three")));
	nova_standard_datastruct_Nova_HashMap_virtual0_Nova_put((nova_standard_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_nums[4])), (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Four")));
	nova_standard_datastruct_Nova_HashMap_virtual0_Nova_put((nova_standard_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_nums[5])), (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Five")));
	nova_standard_datastruct_Nova_HashMap_virtual0_Nova_put((nova_standard_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, l1_Nova_nums[5])), (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_HashMapDemo_0_Nova_this(example_Nova_HashMapDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_HashMapDemo_Nova_super(example_Nova_HashMapDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

