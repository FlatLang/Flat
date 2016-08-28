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
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void example_Nova_ArrayDemo_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_ArrayDemo* example_Nova_ArrayDemo_Nova_ArrayDemo(example_Nova_ArrayDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_ArrayDemo, this,);
	this->vtable = &example_Extension_VTable_ArrayDemo_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_ArrayDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_ArrayDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_ArrayDemo_Nova_destroy(example_Nova_ArrayDemo** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_ArrayDemo_Nova_main(example_Nova_ArrayDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_ArrayDemo_Nova_args)
{
	char l1_Nova_c = 0;
	
	l1_Nova_c = 'y';
	while (l1_Nova_c == 'y' || l1_Nova_c == 'Y')
	{
		nova_datastruct_list_Nova_Array* l1_Nova_list = (nova_datastruct_list_Nova_Array*)nova_null;
		example_Nova_Animal* l1_Nova_animal = (example_Nova_Animal*)nova_null;
		int l3_Nova_i = 0;
		int l5_Nova_i2 = 0;
		int l7_Nova_i3 = 0;
		
		l1_Nova_list = nova_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
		nova_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_Nova_Object*)(example_Nova_Dog_Nova_Dog(0, exceptionData)));
		nova_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_Nova_Object*)(example_Nova_Dog_Nova_Dog(0, exceptionData)));
		nova_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_Nova_Object*)(example_Nova_Spider_Nova_Spider(0, exceptionData)));
		nova_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_Nova_Object*)(example_Nova_Dog_Nova_Dog(0, exceptionData)));
		nova_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_Nova_Object*)(example_Nova_Animal_Nova_Animal(0, exceptionData)));
		nova_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_Nova_Object*)(example_Nova_Spider_Nova_Spider(0, exceptionData)));
		l3_Nova_i = (int)0;
		for (; l3_Nova_i < (int)l1_Nova_list->nova_datastruct_list_Nova_Array_Nova_count; l3_Nova_i++)
		{
			nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, "Contains: "), exceptionData, nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(l1_Nova_list), exceptionData, l3_Nova_i)), exceptionData)));
		}
		l1_Nova_animal = (example_Nova_Animal*)(nova_datastruct_list_Nova_Array_Nova_remove(l1_Nova_list, exceptionData, 2));
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, "--------- Removed: "), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(l1_Nova_animal), exceptionData)), exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, " ----------"))));
		l5_Nova_i2 = (int)0;
		for (; l5_Nova_i2 < (int)l1_Nova_list->nova_datastruct_list_Nova_Array_Nova_count; l5_Nova_i2++)
		{
			nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, "Contains: "), exceptionData, nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(l1_Nova_list), exceptionData, l5_Nova_i2)), exceptionData)));
		}
		nova_datastruct_list_Nova_Array_1_Nova_add(l1_Nova_list, exceptionData, 1, (nova_Nova_Object*)(example_Nova_Spider_Nova_Spider(0, exceptionData)));
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "--------- Added a new spider at index 1 ----------"));
		l7_Nova_i3 = (int)0;
		for (; l7_Nova_i3 < (int)l1_Nova_list->nova_datastruct_list_Nova_Array_Nova_count; l7_Nova_i3++)
		{
			nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, "Contains: "), exceptionData, nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(l1_Nova_list), exceptionData, l7_Nova_i3)), exceptionData)));
		}
		nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Run again? (Y/N)"));
		l1_Nova_c = nova_io_Nova_Console_Nova_readChar(0, exceptionData);
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "\nFinished"));
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_ArrayDemo_0_Nova_this(example_Nova_ArrayDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_ArrayDemo_Nova_super(example_Nova_ArrayDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

