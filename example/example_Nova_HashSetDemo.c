#include <precompiled.h>
#include <example/example_Nova_HashSetDemo.h>



example_Extension_VTable_HashSetDemo example_Extension_VTable_HashSetDemo_val =
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


void example_Nova_HashSetDemo_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_HashSetDemo* example_Nova_HashSetDemo_Nova_construct(example_Nova_HashSetDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_HashSetDemo, this,);
	this->vtable = &example_Extension_VTable_HashSetDemo_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_HashSetDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_HashSetDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_HashSetDemo_Nova_destroy(example_Nova_HashSetDemo** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_HashSetDemo_Nova_main(example_Nova_HashSetDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_HashSetDemo_Nova_args)
{
	nova_datastruct_Nova_HashSet* l1_Nova_set = (nova_datastruct_Nova_HashSet*)nova_null;
	int l1_Nova_count = 0;
	nova_time_Nova_Timer* l1_Nova_timer = (nova_time_Nova_Timer*)nova_null;
	long_long l1_Nova_stringTime = 0;
	long_long l1_Nova_addTime = 0;
	long_long nova_zero_check29 = 0;
	long_long nova_zero_check30 = 0;
	long_long nova_zero_check31 = 0;
	long_long nova_zero_check32 = 0;
	long_long nova_zero_check33 = 0;
	long_long nova_zero_check34 = 0;
	long_long nova_zero_check35 = 0;
	long_long nova_zero_check36 = 0;
	long_long l1_Nova_getTime = 0;
	long_long nova_zero_check37 = 0;
	long_long nova_zero_check38 = 0;
	long_long nova_zero_check39 = 0;
	long_long nova_zero_check40 = 0;
	long_long nova_zero_check41 = 0;
	long_long nova_zero_check42 = 0;
	long_long nova_zero_check43 = 0;
	long_long nova_zero_check44 = 0;
	long_long l1_Nova_newStringTime = 0;
	long_long nova_zero_check45 = 0;
	long_long nova_zero_check46 = 0;
	long_long nova_zero_check47 = 0;
	long_long nova_zero_check48 = 0;
	long_long nova_zero_check49 = 0;
	long_long nova_zero_check50 = 0;
	long_long nova_zero_check51 = 0;
	long_long nova_zero_check52 = 0;
	int l2_Nova_i = 0;
	int l4_Nova_i = 0;
	int l22_Nova_i = 0;
	int l40_Nova_i = 0;
	
	l1_Nova_set = nova_datastruct_Nova_HashSet_0_Nova_construct(0, exceptionData);
	nova_datastruct_Nova_HashSet_Nova_add((nova_datastruct_Nova_HashSet*)(l1_Nova_set), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("TEseting"))));
	nova_io_Nova_Console_2_Nova_writeLine(0, exceptionData, (nova_Nova_Object*)(l1_Nova_set));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Contains TEset? ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_Nova_Bool_2_Nova_toString(0, exceptionData, (nova_datastruct_list_Nova_List_virtual0_Nova_contains((nova_datastruct_list_Nova_List*)(l1_Nova_set), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("TEset"))))))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Contains TEseting? ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_Nova_Bool_2_Nova_toString(0, exceptionData, (nova_datastruct_list_Nova_List_virtual0_Nova_contains((nova_datastruct_list_Nova_List*)(l1_Nova_set), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("TEseting"))))))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
	nova_io_Nova_Console_2_Nova_writeLine(0, exceptionData, (nova_Nova_Object*)(l1_Nova_set));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Benchmarking")));
	l1_Nova_count = 50000;
	l1_Nova_timer = nova_time_Nova_Timer_Nova_start(nova_time_Nova_Timer_Nova_construct(0, exceptionData), exceptionData);
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)l1_Nova_count; l2_Nova_i++)
	{
		nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("my string")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l2_Nova_i))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""))));
	}
	l1_Nova_stringTime = nova_time_Nova_Timer_Accessor_Nova_duration(nova_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData), exceptionData);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Took ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, (l1_Nova_stringTime))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("ms to create ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_count))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(" strings")))))));
	l4_Nova_i = (int)0;
	for (; l4_Nova_i < (int)50000; l4_Nova_i++)
	{
		nova_datastruct_Nova_HashSet_Nova_add((nova_datastruct_Nova_HashSet*)(l1_Nova_set), exceptionData, (nova_Nova_Object*)(nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("my string")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l4_Nova_i))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""))))));
	}
	l1_Nova_addTime = nova_time_Nova_Timer_Accessor_Nova_duration(nova_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData), exceptionData);
	nova_zero_check29 = l1_Nova_stringTime;
	if (nova_zero_check29 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check30 = nova_zero_check29;
	if (nova_zero_check30 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check31 = nova_zero_check30;
	if (nova_zero_check31 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check32 = nova_zero_check31;
	if (nova_zero_check32 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check33 = nova_zero_check32;
	if (nova_zero_check33 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check34 = nova_zero_check33;
	if (nova_zero_check34 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check35 = nova_zero_check34;
	if (nova_zero_check35 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check36 = nova_zero_check35;
	if (nova_zero_check36 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Took ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, (l1_Nova_addTime))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("ms to call add ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_count))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(" times ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, ((l1_Nova_addTime - l1_Nova_stringTime) * 100 / nova_zero_check36))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("% overhead")))))))));
	nova_time_Nova_Timer_Nova_start(l1_Nova_timer, exceptionData);
	l22_Nova_i = (int)0;
	for (; l22_Nova_i < (int)l1_Nova_count; l22_Nova_i++)
	{
		nova_datastruct_Nova_HashSet_Nova_get((nova_datastruct_Nova_HashSet*)(l1_Nova_set), exceptionData, (nova_Nova_Object*)(nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("my string")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l22_Nova_i))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""))))));
	}
	l1_Nova_getTime = nova_time_Nova_Timer_Accessor_Nova_duration(nova_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData), exceptionData);
	nova_zero_check37 = l1_Nova_stringTime;
	if (nova_zero_check37 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check38 = nova_zero_check37;
	if (nova_zero_check38 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check39 = nova_zero_check38;
	if (nova_zero_check39 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check40 = nova_zero_check39;
	if (nova_zero_check40 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check41 = nova_zero_check40;
	if (nova_zero_check41 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check42 = nova_zero_check41;
	if (nova_zero_check42 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check43 = nova_zero_check42;
	if (nova_zero_check43 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check44 = nova_zero_check43;
	if (nova_zero_check44 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Took ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, (l1_Nova_getTime))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("ms to call get ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_count))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(" times ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, ((l1_Nova_getTime - l1_Nova_stringTime) * 100 / nova_zero_check44))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("% overhead")))))))));
	nova_time_Nova_Timer_Nova_start(l1_Nova_timer, exceptionData);
	l40_Nova_i = (int)0;
	for (; l40_Nova_i < (int)l1_Nova_count; l40_Nova_i++)
	{
		nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("my string")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l40_Nova_i))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""))));
	}
	l1_Nova_newStringTime = nova_time_Nova_Timer_Accessor_Nova_duration(nova_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData), exceptionData);
	nova_zero_check45 = l1_Nova_stringTime;
	if (nova_zero_check45 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check46 = nova_zero_check45;
	if (nova_zero_check46 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check47 = nova_zero_check46;
	if (nova_zero_check47 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check48 = nova_zero_check47;
	if (nova_zero_check48 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check49 = nova_zero_check48;
	if (nova_zero_check49 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check50 = nova_zero_check49;
	if (nova_zero_check50 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check51 = nova_zero_check50;
	if (nova_zero_check51 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_zero_check52 = nova_zero_check51;
	if (nova_zero_check52 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_construct(0, exceptionData));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Took ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, (l1_Nova_newStringTime))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("ms to create ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_count))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(" strings ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, ((l1_Nova_newStringTime - l1_Nova_stringTime) * 100 / nova_zero_check52))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("% overhead")))))))));
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_HashSetDemo_0_Nova_this(example_Nova_HashSetDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_HashSetDemo_Nova_super(example_Nova_HashSetDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

