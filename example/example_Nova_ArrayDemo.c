#include <precompiled.h>
#include <example/example_Nova_ArrayDemo.h>

example_Extension_VTable_ArrayDemo example_Extension_VTable_ArrayDemo_val =
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
};


void example_Nova_ArrayDemoNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_ArrayDemo* example_Nova_ArrayDemo_0_Nova_construct(example_Nova_ArrayDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_ArrayDemo, this,);
	this->vtable = &example_Extension_VTable_ArrayDemo_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_ArrayDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_ArrayDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_ArrayDemo_Nova_destroy(example_Nova_ArrayDemo** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_ArrayDemo_Nova_main(example_Nova_ArrayDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_ArrayDemo_Nova_args)
{
	char l1_Nova_c;
	
	l1_Nova_c = 'y';
	while (l1_Nova_c == 'y' || l1_Nova_c == 'Y')
	{
		nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
		example_Nova_Animal* l1_Nova_animal;
		int l3_Nova_i;
		int l5_Nova_i;
		int l7_Nova_i;
		
		l1_Nova_list = nova_standard_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Dog_0_Nova_construct(0, exceptionData)));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Dog_0_Nova_construct(0, exceptionData)));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Spider_0_Nova_construct(0, exceptionData)));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Dog_0_Nova_construct(0, exceptionData)));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Animal_0_Nova_construct(0, exceptionData)));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Spider_0_Nova_construct(0, exceptionData)));
		l3_Nova_i = 0;
		for (; l3_Nova_i < l1_Nova_list->nova_standard_datastruct_list_Nova_Array_Nova_size; l3_Nova_i++)
		{
			nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Contains: "), exceptionData, example_Nova_Animal_1_Nova_toString((example_Nova_Animal*)(nova_standard_datastruct_list_Nova_Array_Nova_get(l1_Nova_list, exceptionData, l3_Nova_i)), exceptionData)));
		}
		l1_Nova_animal = (example_Nova_Animal*)(nova_standard_datastruct_list_Nova_Array_Nova_remove(l1_Nova_list, exceptionData, 2));
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "--------- Removed: "), exceptionData, nova_standard_Nova_String_0_Nova_concat(l1_Nova_animal->example_Nova_Animal_1_Nova_toString(l1_Nova_animal, exceptionData), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, " ----------"))));
		l5_Nova_i = 0;
		for (; l5_Nova_i < l1_Nova_list->nova_standard_datastruct_list_Nova_Array_Nova_size; l5_Nova_i++)
		{
			nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Contains: "), exceptionData, example_Nova_Animal_1_Nova_toString((example_Nova_Animal*)(nova_standard_datastruct_list_Nova_Array_Nova_get(l1_Nova_list, exceptionData, l5_Nova_i)), exceptionData)));
		}
		nova_standard_datastruct_list_Nova_Array_1_Nova_add(l1_Nova_list, exceptionData, 1, (nova_standard_Nova_Object*)(example_Nova_Spider_0_Nova_construct(0, exceptionData)));
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "--------- Added a new spider at index 1 ----------"));
		l7_Nova_i = 0;
		for (; l7_Nova_i < l1_Nova_list->nova_standard_datastruct_list_Nova_Array_Nova_size; l7_Nova_i++)
		{
			nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Contains: "), exceptionData, example_Nova_Animal_1_Nova_toString((example_Nova_Animal*)(nova_standard_datastruct_list_Nova_Array_Nova_get(l1_Nova_list, exceptionData, l7_Nova_i)), exceptionData)));
		}
		nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Run again? (Y/N)"));
		l1_Nova_c = nova_standard_io_Nova_Console_Nova_readChar(0, exceptionData);
	}
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "\nFinished"));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_ArrayDemo_0_Nova_this(example_Nova_ArrayDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_ArrayDemo_Nova_super(example_Nova_ArrayDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

