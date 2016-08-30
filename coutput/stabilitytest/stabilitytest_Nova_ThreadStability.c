#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_ThreadStability.h>



stabilitytest_Extension_VTable_ThreadStability stabilitytest_Extension_VTable_ThreadStability_val =
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
	stabilitytest_Nova_ThreadStability_0_Nova_test,
};



void stabilitytest_Nova_ThreadStability_Nova_createThreads(stabilitytest_Nova_ThreadStability* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* stabilitytest_Nova_ThreadStability_Nova_threads, int stabilitytest_Nova_ThreadStability_Nova_amount);
void stabilitytest_Nova_ThreadStability_Nova_checkMemoryAccess(stabilitytest_Nova_ThreadStability* this, nova_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ThreadStability_Nova_joinThreads(stabilitytest_Nova_ThreadStability* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* stabilitytest_Nova_ThreadStability_Nova_threads, int stabilitytest_Nova_ThreadStability_Nova_amount);
void stabilitytest_Nova_ThreadStability_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ThreadStability* stabilitytest_Nova_ThreadStability_Nova_construct(stabilitytest_Nova_ThreadStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ThreadStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_ThreadStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_ThreadStability_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_ThreadStability_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ThreadStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_ThreadStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_ThreadStability_Nova_destroy(stabilitytest_Nova_ThreadStability** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_ThreadStability_0_Nova_this(stabilitytest_Nova_ThreadStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ThreadStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_ThreadStability_Nova_program);
}

void stabilitytest_Nova_ThreadStability_0_Nova_test(stabilitytest_Nova_ThreadStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_datastruct_list_Nova_Array* l1_Nova_threads = (nova_datastruct_list_Nova_Array*)nova_null;
	
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Checking Thread.nova with 20 Threads... ")));
	l1_Nova_threads = nova_datastruct_list_Nova_Array_1_Nova_construct(0, exceptionData, 20);
	stabilitytest_Nova_ThreadStability_Nova_createThreads(this, exceptionData, l1_Nova_threads, 20);
	stabilitytest_Nova_ThreadStability_Nova_checkMemoryAccess(0, exceptionData);
	stabilitytest_Nova_ThreadStability_Nova_joinThreads(0, exceptionData, l1_Nova_threads, 20);
}

void stabilitytest_Nova_ThreadStability_Nova_createThreads(stabilitytest_Nova_ThreadStability* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* stabilitytest_Nova_ThreadStability_Nova_threads, int stabilitytest_Nova_ThreadStability_Nova_amount)
{
	stabilitytest_Nova_StabilityExceptionHandler* l1_Nova_handler = (stabilitytest_Nova_StabilityExceptionHandler*)nova_null;
	int l2_Nova_i = 0;
	
	l1_Nova_handler = stabilitytest_Nova_StabilityExceptionHandler_Nova_construct(0, exceptionData, this->stabilitytest_Nova_StabilityTestCase_Nova_program);
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)stabilitytest_Nova_ThreadStability_Nova_amount; l2_Nova_i++)
	{
		nova_datastruct_list_Nova_Array_Nova_set((nova_datastruct_list_Nova_Array*)(stabilitytest_Nova_ThreadStability_Nova_threads), exceptionData, l2_Nova_i, (nova_Nova_Object*)(stabilitytest_Nova_ThreadImplementation_Nova_construct(0, exceptionData, 10, 10)));
		nova_thread_Nova_Thread_Nova_start((nova_thread_Nova_Thread*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(stabilitytest_Nova_ThreadStability_Nova_threads), exceptionData, l2_Nova_i)), exceptionData);
	}
}

void stabilitytest_Nova_ThreadStability_Nova_checkMemoryAccess(stabilitytest_Nova_ThreadStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	int l2_Nova_i = 0;
	
	nova_thread_Nova_Thread_Nova_sleep(0, exceptionData, 30);
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Checking memory access with multi-threading... ")));
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)1000; l2_Nova_i++)
	{
		nova_Nova_String* l2_Nova_s = (nova_Nova_String*)nova_null;
		
		l2_Nova_s = nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, l2_Nova_i);
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("OK")));
}

void stabilitytest_Nova_ThreadStability_Nova_joinThreads(stabilitytest_Nova_ThreadStability* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* stabilitytest_Nova_ThreadStability_Nova_threads, int stabilitytest_Nova_ThreadStability_Nova_amount)
{
	int l2_Nova_i = 0;
	
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)stabilitytest_Nova_ThreadStability_Nova_amount; l2_Nova_i++)
	{
		nova_thread_Nova_Thread_Nova_join((nova_thread_Nova_Thread*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(stabilitytest_Nova_ThreadStability_Nova_threads), exceptionData, l2_Nova_i)), exceptionData);
	}
}

void stabilitytest_Nova_ThreadStability_0_Nova_super(stabilitytest_Nova_ThreadStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

