#include <precompiled.h>
#include <example/example_Nova_ThreadDemo.h>



example_Extension_VTable_ThreadDemo example_Extension_VTable_ThreadDemo_val =
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


void example_Nova_ThreadDemo_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_ThreadDemo* example_Nova_ThreadDemo_Nova_construct(example_Nova_ThreadDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_ThreadDemo, this,);
	this->vtable = &example_Extension_VTable_ThreadDemo_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_ThreadDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_ThreadDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_ThreadDemo_Nova_destroy(example_Nova_ThreadDemo** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_ThreadDemo_Nova_main(example_Nova_ThreadDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_ThreadDemo_Nova_args)
{
	char l1_Nova_c = 0;
	
	l1_Nova_c = 'y';
	while (l1_Nova_c == 'y' || l1_Nova_c == 'Y')
	{
		nova_thread_Nova_Thread* l1_Nova_thread = (nova_thread_Nova_Thread*)nova_null;
		nova_thread_Nova_Thread* l1_Nova_thread2 = (nova_thread_Nova_Thread*)nova_null;
		nova_time_Nova_Timer* l1_Nova_timer = (nova_time_Nova_Timer*)nova_null;
		
		l1_Nova_thread = (nova_thread_Nova_Thread*)(example_Nova_ThreadDemoImplementation_Nova_construct(0, exceptionData, 100, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Thread1"))));
		l1_Nova_thread2 = (nova_thread_Nova_Thread*)(example_Nova_ThreadDemoImplementation_Nova_construct(0, exceptionData, 100, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Thread2"))));
		l1_Nova_timer = nova_time_Nova_Timer_Nova_start(nova_time_Nova_Timer_Nova_construct(0, exceptionData), exceptionData);
		nova_thread_Nova_Thread_Nova_start(l1_Nova_thread, exceptionData);
		nova_thread_Nova_Thread_Nova_start(l1_Nova_thread2, exceptionData);
		nova_thread_Nova_Thread_Nova_join(l1_Nova_thread, exceptionData);
		nova_thread_Nova_Thread_Nova_join(l1_Nova_thread2, exceptionData);
		nova_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData);
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Time taken: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("ms")))));
		nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Run again? (Y/N) ")));
		l1_Nova_c = nova_io_Nova_Console_Nova_readChar(0, exceptionData);
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\nFinished")));
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_ThreadDemo_0_Nova_this(example_Nova_ThreadDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_ThreadDemo_Nova_super(example_Nova_ThreadDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

