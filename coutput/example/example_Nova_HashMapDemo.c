#include <precompiled.h>
#include <example/example_Nova_HashMapDemo.h>

typedef struct
{
} Context1;
typedef struct
{
} Context2;
typedef struct
{
} Context3;
typedef struct
{
} Context4;
typedef struct
{
} Context5;


example_Extension_VTable_HashMapDemo example_Extension_VTable_HashMapDemo_val =
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
};



void example_Nova_HashMapDemo_Nova_testLambda57(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* example_Nova_HashMapDemo_Nova__1, int example_Nova_HashMapDemo_Nova__2, nova_datastruct_Nova_HashMap* example_Nova_HashMapDemo_Nova__3, Context1* context);
void example_Nova_HashMapDemo_Nova_testLambda58(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* example_Nova_HashMapDemo_Nova__1, int example_Nova_HashMapDemo_Nova__2, nova_datastruct_Nova_HashMap* example_Nova_HashMapDemo_Nova__3, Context2* context);
char example_Nova_HashMapDemo_Nova_testLambda59(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* example_Nova_HashMapDemo_Nova__1, int example_Nova_HashMapDemo_Nova__2, nova_datastruct_Nova_HashMap* example_Nova_HashMapDemo_Nova__3, Context3* context);
nova_Nova_Object* example_Nova_HashMapDemo_Nova_testLambda60(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* example_Nova_HashMapDemo_Nova__1, int example_Nova_HashMapDemo_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_HashMapDemo_Nova__3, Context4* context);
void example_Nova_HashMapDemo_Nova_testLambda61(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* example_Nova_HashMapDemo_Nova__1, int example_Nova_HashMapDemo_Nova__2, nova_datastruct_Nova_HashMap* example_Nova_HashMapDemo_Nova__3, Context5* context);
void example_Nova_HashMapDemo_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_HashMapDemo* example_Nova_HashMapDemo_Nova_construct(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_HashMapDemo, this,);
	this->vtable = &example_Extension_VTable_HashMapDemo_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_HashMapDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_HashMapDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_HashMapDemo_Nova_destroy(example_Nova_HashMapDemo** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_HashMapDemo_Nova_main(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_HashMapDemo_Nova_args)
{
	nova_datastruct_Nova_HashMap* l1_Nova_words = (nova_datastruct_Nova_HashMap*)nova_null;
	Context1 contextArg60 = 
	{
	};
	Context2 contextArg61 = 
	{
	};
	Context3 contextArg62 = 
	{
	};
	Context4 contextArg63 = 
	{
	};
	Context5 contextArg64 = 
	{
	};
	
	l1_Nova_words = nova_datastruct_Nova_HashMap_0_Nova_construct(0, exceptionData);
	nova_datastruct_Nova_HashMap_virtual1_Nova_put((nova_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test"))), (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("is test"))));
	nova_datastruct_Nova_HashMap_virtual1_Nova_put((nova_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test2"))), (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("is test2"))));
	nova_datastruct_Nova_HashMap_virtual1_Nova_put((nova_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test3hey"))), (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("is test3hey"))));
	nova_datastruct_Nova_HashMap_virtual1_Nova_put((nova_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test4hey"))), (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("is test4hey"))));
	nova_datastruct_Nova_HashMap_virtual1_Nova_put((nova_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test5"))), (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("is test5"))));
	nova_datastruct_list_Nova_List_virtual0_Nova_forEach((nova_datastruct_list_Nova_List*)(l1_Nova_words), exceptionData, (nova_datastruct_list_Nova_List_closure3_Nova_func)&example_Nova_HashMapDemo_Nova_testLambda57, nova_null, &contextArg60);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\nRemoving test2: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((nova_datastruct_Nova_HashMap_Nova_remove((nova_datastruct_Nova_HashMap*)(l1_Nova_words), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test2")))))), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\n")))));
	nova_datastruct_list_Nova_List_virtual0_Nova_forEach((nova_datastruct_list_Nova_List*)(l1_Nova_words), exceptionData, (nova_datastruct_list_Nova_List_closure3_Nova_func)&example_Nova_HashMapDemo_Nova_testLambda58, nova_null, &contextArg61);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\n")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_filter((nova_datastruct_list_Nova_List*)(l1_Nova_words), exceptionData, (nova_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_HashMapDemo_Nova_testLambda59, nova_null, &contextArg62)), exceptionData, (nova_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_HashMapDemo_Nova_testLambda60, nova_null, &contextArg63)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\n")))));
	nova_datastruct_list_Nova_List_virtual0_Nova_forEach((nova_datastruct_list_Nova_List*)(l1_Nova_words), exceptionData, (nova_datastruct_list_Nova_List_closure3_Nova_func)&example_Nova_HashMapDemo_Nova_testLambda61, nova_null, &contextArg64);
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_HashMapDemo_0_Nova_this(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_HashMapDemo_Nova_testLambda57(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* example_Nova_HashMapDemo_Nova__1, int example_Nova_HashMapDemo_Nova__2, nova_datastruct_Nova_HashMap* example_Nova_HashMapDemo_Nova__3, Context1* context)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Contains: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((example_Nova_HashMapDemo_Nova__1)), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
}

void example_Nova_HashMapDemo_Nova_testLambda58(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* example_Nova_HashMapDemo_Nova__1, int example_Nova_HashMapDemo_Nova__2, nova_datastruct_Nova_HashMap* example_Nova_HashMapDemo_Nova__3, Context2* context)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Contains: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((example_Nova_HashMapDemo_Nova__1)), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
}

char example_Nova_HashMapDemo_Nova_testLambda59(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* example_Nova_HashMapDemo_Nova__1, int example_Nova_HashMapDemo_Nova__2, nova_datastruct_Nova_HashMap* example_Nova_HashMapDemo_Nova__3, Context3* context)
{
	return nova_Nova_String_Nova_contains((nova_Nova_String*)(((nova_Nova_String*)example_Nova_HashMapDemo_Nova__1->nova_datastruct_Nova_Pair_Nova_key)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("hey")));
}

nova_Nova_Object* example_Nova_HashMapDemo_Nova_testLambda60(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* example_Nova_HashMapDemo_Nova__1, int example_Nova_HashMapDemo_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_HashMapDemo_Nova__3, Context4* context)
{
	return (nova_Nova_Object*)example_Nova_HashMapDemo_Nova__1->nova_datastruct_Nova_Pair_Nova_value;
}

void example_Nova_HashMapDemo_Nova_testLambda61(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* example_Nova_HashMapDemo_Nova__1, int example_Nova_HashMapDemo_Nova__2, nova_datastruct_Nova_HashMap* example_Nova_HashMapDemo_Nova__3, Context5* context)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Contains: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((example_Nova_HashMapDemo_Nova__1)), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
}

void example_Nova_HashMapDemo_Nova_super(example_Nova_HashMapDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

