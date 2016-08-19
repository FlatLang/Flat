#include <precompiled.h>
#include <example/example_Nova_GenericDemo.h>

example_Extension_VTable_GenericDemo example_Extension_VTable_GenericDemo_val =
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


void example_Nova_GenericDemoNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_GenericDemo* example_Nova_GenericDemo_Nova_GenericDemo(example_Nova_GenericDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_GenericDemo, this,);
	this->vtable = &example_Extension_VTable_GenericDemo_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_GenericDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_GenericDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_GenericDemo_Nova_destroy(example_Nova_GenericDemo** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_GenericDemo_Nova_main(example_Nova_GenericDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* example_Nova_GenericDemo_Nova_args)
{
	nova_standard_datastruct_list_Nova_Stack* l1_Nova_strs = (nova_standard_datastruct_list_Nova_Stack*)nova_null;
	
	l1_Nova_strs = nova_standard_datastruct_list_Nova_Stack_Nova_Stack(0, exceptionData);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Pushing \"test\""));
	nova_standard_datastruct_list_Nova_Stack_Nova_push(l1_Nova_strs, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test")));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Pushing \"ASDFASDF\""));
	nova_standard_datastruct_list_Nova_Stack_Nova_push(l1_Nova_strs, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "ASDFASDF")));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Pushing \"3!\""));
	nova_standard_datastruct_list_Nova_Stack_Nova_push(l1_Nova_strs, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "3!")));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Pushing \"34!!4334\""));
	nova_standard_datastruct_list_Nova_Stack_Nova_push(l1_Nova_strs, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "34!!4334")));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Pushing null"));
	nova_standard_datastruct_list_Nova_Stack_Nova_push(l1_Nova_strs, exceptionData, (nova_standard_Nova_Object*)((nova_standard_Nova_Object*)nova_null));
	while (!nova_standard_datastruct_list_Nova_Stack_Accessor_Nova_empty(l1_Nova_strs, exceptionData))
	{
		nova_standard_Nova_String* l1_Nova_popped = (nova_standard_Nova_String*)nova_null;
		
		l1_Nova_popped = (nova_standard_Nova_String*)(nova_standard_datastruct_list_Nova_Stack_Nova_pop(l1_Nova_strs, exceptionData));
		if (l1_Nova_popped != (nova_standard_Nova_String*)nova_null)
		{
			l1_Nova_popped = (nova_standard_Nova_String*)(nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "\""), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(l1_Nova_popped), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "\""))));
		}
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Popping: "), exceptionData, l1_Nova_popped));
	}
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_GenericDemo_0_Nova_this(example_Nova_GenericDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_GenericDemo_Nova_super(example_Nova_GenericDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

