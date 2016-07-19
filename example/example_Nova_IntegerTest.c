#include <precompiled.h>
#include <example/example_Nova_IntegerTest.h>

example_Extension_VTable_IntegerTest example_Extension_VTable_IntegerTest_val =
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


void example_Nova_IntegerTestNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_IntegerTest* example_Nova_IntegerTest_Nova_IntegerTest(example_Nova_IntegerTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_IntegerTest, this,);
	this->vtable = &example_Extension_VTable_IntegerTest_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_IntegerTest_Nova_super(this, exceptionData);
	
	{
		example_Nova_IntegerTest_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_IntegerTest_Nova_destroy(example_Nova_IntegerTest** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_IntegerTest_Nova_main(example_Nova_IntegerTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_IntegerTest_Nova_args)
{
	char l1_Nova_c = 0;
	
	l1_Nova_c = 'y';
	while (l1_Nova_c == 'y' || l1_Nova_c == 'Y')
	{
		nova_standard_time_Nova_Timer* l1_Nova_timer = (nova_standard_time_Nova_Timer*)nova_null;
		nova_standard_datastruct_list_Nova_IntRangeIterator* nova_local_0 = (nova_standard_datastruct_list_Nova_IntRangeIterator*)nova_null;
		int l2_Nova_i = 0;
		
		l1_Nova_timer = nova_standard_time_Nova_Timer_Nova_start(nova_standard_time_Nova_Timer_Nova_Timer(0, exceptionData), exceptionData);
		nova_local_0 = (nova_standard_datastruct_list_Nova_IntRangeIterator*)(nova_standard_datastruct_list_Nova_IntRange_Accessor_Nova_iterator(nova_standard_datastruct_list_Nova_IntRange_1_Nova_IntRange(0, exceptionData, (int)0, (int)999999), exceptionData));
		while (nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_hasNext((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData))
		{
			l2_Nova_i = (int)(nova_standard_datastruct_list_Nova_Iterator_virtual_Accessor_Nova_next((nova_standard_datastruct_list_Nova_Iterator*)(nova_local_0), exceptionData));
			nova_standard_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, l2_Nova_i);
		}
		nova_standard_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData);
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Time taken: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_standard_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "ms"))));
		nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Run again? (Y/N) "));
		l1_Nova_c = nova_standard_io_Nova_Console_Nova_readChar(0, exceptionData);
	}
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "\nFinished"));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_IntegerTest_0_Nova_this(example_Nova_IntegerTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_IntegerTest_Nova_super(example_Nova_IntegerTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

