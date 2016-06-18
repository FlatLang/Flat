#include <precompiled.h>
#include <example/example_Nova_QueueDemo.h>

example_Extension_VTable_QueueDemo example_Extension_VTable_QueueDemo_val =
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


void example_Nova_QueueDemoNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_QueueDemo* example_Nova_QueueDemo_0_Nova_construct(example_Nova_QueueDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_QueueDemo, this,);
	this->vtable = &example_Extension_VTable_QueueDemo_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_QueueDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_QueueDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_QueueDemo_Nova_destroy(example_Nova_QueueDemo** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_QueueDemo_Nova_main(example_Nova_QueueDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_QueueDemo_Nova_args)
{
	nova_standard_datastruct_list_Nova_Queue* l1_Nova_q;
	int l1_Nova_num;
	
	l1_Nova_q = nova_standard_datastruct_list_Nova_Queue_0_Nova_construct(0, exceptionData);
	nova_standard_datastruct_list_Nova_Queue_Nova_enqueue(l1_Nova_q, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, 5)));
	nova_standard_datastruct_list_Nova_Queue_Nova_enqueue(l1_Nova_q, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, 7)));
	nova_standard_datastruct_list_Nova_Queue_Nova_enqueue(l1_Nova_q, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, 3)));
	nova_standard_datastruct_list_Nova_Queue_Nova_enqueue(l1_Nova_q, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, 10)));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Queue: "), exceptionData, l1_Nova_q->vtable->nova_standard_datastruct_list_Nova_Queue_virtual1_Nova_toString(l1_Nova_q, exceptionData)));
	l1_Nova_num = ((nova_standard_primitive_number_Nova_Int*)nova_standard_datastruct_list_Nova_Queue_Nova_dequeue(l1_Nova_q, exceptionData))->nova_standard_primitive_number_Nova_Int_Nova_value;
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Dequeued: "), exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, l1_Nova_num)));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Queue: "), exceptionData, l1_Nova_q->vtable->nova_standard_datastruct_list_Nova_Queue_virtual1_Nova_toString(l1_Nova_q, exceptionData)));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_QueueDemo_0_Nova_this(example_Nova_QueueDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_QueueDemo_Nova_super(example_Nova_QueueDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

