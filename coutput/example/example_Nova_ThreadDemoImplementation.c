#include <precompiled.h>
#include <example/example_Nova_ThreadDemoImplementation.h>



example_Extension_VTable_ThreadDemoImplementation example_Extension_VTable_ThreadDemoImplementation_val =
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
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	example_Nova_ThreadDemoImplementation_0_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_thread_Nova_Thread_Nova_handle;
	
	long_long example_Nova_ThreadDemoImplementation_Nova_millis;
	nova_Nova_String* example_Nova_ThreadDemoImplementation_Nova_word;
	
)
void example_Nova_ThreadDemoImplementation_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_ThreadDemoImplementation* example_Nova_ThreadDemoImplementation_Nova_construct(example_Nova_ThreadDemoImplementation* this, nova_exception_Nova_ExceptionData* exceptionData, long_long example_Nova_ThreadDemoImplementation_Nova_millis, nova_Nova_String* example_Nova_ThreadDemoImplementation_Nova_word)
{
	CCLASS_NEW(example_Nova_ThreadDemoImplementation, this);
	this->vtable = &example_Extension_VTable_ThreadDemoImplementation_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_thread_Nova_Thread_Nova_super((nova_thread_Nova_Thread*)this, exceptionData);
	example_Nova_ThreadDemoImplementation_0_Nova_super(this, exceptionData);
	
	{
		example_Nova_ThreadDemoImplementation_Nova_this(this, exceptionData, example_Nova_ThreadDemoImplementation_Nova_millis, example_Nova_ThreadDemoImplementation_Nova_word);
	}
	
	return this;
}

void example_Nova_ThreadDemoImplementation_Nova_destroy(example_Nova_ThreadDemoImplementation** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_Nova_String_Nova_destroy(&(*this)->prv->example_Nova_ThreadDemoImplementation_Nova_word, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void example_Nova_ThreadDemoImplementation_Nova_this(example_Nova_ThreadDemoImplementation* this, nova_exception_Nova_ExceptionData* exceptionData, long_long example_Nova_ThreadDemoImplementation_Nova_millis, nova_Nova_String* example_Nova_ThreadDemoImplementation_Nova_word)
{
	this->prv->example_Nova_ThreadDemoImplementation_Nova_millis = example_Nova_ThreadDemoImplementation_Nova_millis;
	this->prv->example_Nova_ThreadDemoImplementation_Nova_word = example_Nova_ThreadDemoImplementation_Nova_word;
}

void example_Nova_ThreadDemoImplementation_0_Nova_run(example_Nova_ThreadDemoImplementation* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	int l2_Nova_i = 0;
	
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)10; l2_Nova_i++)
	{
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, this->prv->example_Nova_ThreadDemoImplementation_Nova_word);
		nova_thread_Nova_Thread_Nova_sleep(0, exceptionData, this->prv->example_Nova_ThreadDemoImplementation_Nova_millis);
	}
}

void example_Nova_ThreadDemoImplementation_0_Nova_super(example_Nova_ThreadDemoImplementation* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->example_Nova_ThreadDemoImplementation_Nova_millis = 0;
	this->prv->example_Nova_ThreadDemoImplementation_Nova_word = (nova_Nova_String*)nova_null;
}

