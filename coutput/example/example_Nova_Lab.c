#include <precompiled.h>
#include <example/example_Nova_Lab.h>

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
typedef struct
{
} Context6;
typedef struct
{
} Context7;
typedef struct
{
} Context8;


example_Extension_VTable_Lab example_Extension_VTable_Lab_val =
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



void example_Nova_Lab_Nova_takesString(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova_s);
void example_Nova_Lab_Nova_doSomething(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_List* example_Nova_Lab_Nova_list);
double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* example_Nova_Lab_Nova_p);
nova_datastruct_list_Nova_Array* generated11(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_list_Nova_Array* generated12(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_list_Nova_IntArray* generated13(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData);
void example_Nova_Lab_Nova_testLambda62(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, Context1* context);
nova_datastruct_list_Nova_Array* generated14(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_list_Nova_Array* generated15(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_Object* example_Nova_Lab_Nova_testLambda63(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova_x, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context2* context);
char example_Nova_Lab_Nova_testLambda64(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context3* context);
nova_Nova_Object* example_Nova_Lab_Nova_testLambda65(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context4* context);
char example_Nova_Lab_Nova_testLambda66(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context5* context);
nova_Nova_Object* example_Nova_Lab_Nova_testLambda67(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context6* context);
nova_Nova_Object* example_Nova_Lab_Nova_testLambda68(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, int example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_IntArray* example_Nova_Lab_Nova__3, Context7* context);
nova_datastruct_list_Nova_Array* generated16(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_Object* example_Nova_Lab_Nova_testLambda69(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_List* example_Nova_Lab_Nova__3, Context8* context);
void example_Nova_Lab_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Lab* example_Nova_Lab_Nova_construct(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_Lab, this,);
	this->vtable = &example_Extension_VTable_Lab_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_Lab_Nova_super(this, exceptionData);
	
	{
		example_Nova_Lab_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_Lab_Nova_destroy(example_Nova_Lab** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_Lab_Nova_main(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova_args)
{
	nova_datastruct_Nova_BinaryTree* l1_Nova_tree = (nova_datastruct_Nova_BinaryTree*)nova_null;
	int l1_Nova_num1 = 0;
	int l1_Nova_num2 = 0;
	nova_Nova_String* l1_Nova_str = (nova_Nova_String*)nova_null;
	nova_Nova_Object* l1_Nova_str2 = (nova_Nova_Object*)nova_null;
	nova_datastruct_list_Nova_Array* l1_Nova_list = (nova_datastruct_list_Nova_Array*)nova_null;
	nova_datastruct_list_Nova_IntArray* l1_Nova_list2 = (nova_datastruct_list_Nova_IntArray*)nova_null;
	Context1 contextArg65 = 
	{
	};
	nova_datastruct_list_Nova_Array* l1_Nova_multi = (nova_datastruct_list_Nova_Array*)nova_null;
	Context2 contextArg66 = 
	{
	};
	Context3 contextArg67 = 
	{
	};
	Context4 contextArg68 = 
	{
	};
	Context5 contextArg69 = 
	{
	};
	Context6 contextArg70 = 
	{
	};
	Context7 contextArg71 = 
	{
	};
	nova_datastruct_list_Nova_LinkedList* l1_Nova_linked = (nova_datastruct_list_Nova_LinkedList*)nova_null;
	nova_Nova_String* l1_Nova_x = (nova_Nova_String*)nova_null;
	example_Nova_Square* l1_Nova_sq = (example_Nova_Square*)nova_null;
	
	l1_Nova_tree = nova_datastruct_Nova_BinaryTree_Nova_construct(0, exceptionData, (nova_datastruct_list_Nova_Array*)(generated11(0, exceptionData)));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Preorder: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(nova_datastruct_Nova_Tree_Nova_preorder((nova_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", "))))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Inorder: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(nova_datastruct_Nova_Tree_Nova_inorder((nova_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", "))))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Postorder: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(nova_datastruct_Nova_Tree_Nova_postorder((nova_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", "))))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Levelorder: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(nova_datastruct_Nova_Tree_Nova_levelorder((nova_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", "))))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
	l1_Nova_num1 = (int)(4);
	l1_Nova_num2 = (int)(l1_Nova_num1 = (int)(7));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("My nums: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_num1))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_num2))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(" hey: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((nova_datastruct_Nova_Tree_Nova_inorder((nova_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData))), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("!")))))))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Result from ternary: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_num1))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(" ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((l1_Nova_num1 < 3 ? nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("<")) : nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(">=")))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(" 3")))))));
	l1_Nova_str = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("this isnt null"));
	l1_Nova_str2 = (nova_Nova_Object*)nova_null;
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Elvis not null: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((l1_Nova_str != (nova_Nova_String*)nova_null ? l1_Nova_str : nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("wtf")))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Elvis null: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((l1_Nova_str2 != (nova_Nova_Object*)nova_null ? l1_Nova_str2 : (nova_Nova_Object*)nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("this is null")))), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
	nova_Nova_String_1_Nova_substring(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test")), exceptionData, 0);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_datastruct_list_Nova_IntRange_0_Nova_toString((nova_datastruct_list_Nova_IntRange_1_Nova_construct(0, exceptionData, (int)2, (int)8)), exceptionData)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(": ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((nova_datastruct_list_Nova_IntRange_0_Nova_join((nova_datastruct_list_Nova_IntRange_1_Nova_construct(0, exceptionData, (int)2, (int)8)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", "))))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""))))));
	l1_Nova_list = generated12(0, exceptionData);
	l1_Nova_list2 = generated13(0, exceptionData);
	nova_thread_async_Nova_Async_Nova_execute(0, exceptionData, (nova_thread_async_Nova_Async_closure3_Nova_func)&example_Nova_Lab_Nova_testLambda62, nova_null, &contextArg65);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Started async execution")));
	l1_Nova_multi = nova_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
	nova_datastruct_list_Nova_Array_0_Nova_add((nova_datastruct_list_Nova_Array*)(l1_Nova_multi), exceptionData, (nova_Nova_Object*)(generated14(0, exceptionData)));
	nova_datastruct_list_Nova_Array_0_Nova_add((nova_datastruct_list_Nova_Array*)(l1_Nova_multi), exceptionData, (nova_Nova_Object*)(generated15(0, exceptionData)));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Multi dimensional array: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((l1_Nova_multi)), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_Nova_capitalize(nova_primitive_Nova_Bool_2_Nova_toString(0, exceptionData, nova_datastruct_list_Nova_List_virtual0_Nova_contains((nova_datastruct_list_Nova_List*)(((nova_Nova_String*)nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(l1_Nova_multi), exceptionData, 0)), exceptionData, 0))->nova_Nova_String_Nova_chars), exceptionData, (nova_Nova_Object*)('z'))), exceptionData));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda63, nova_null, &contextArg66)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", "))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Mapped: ")), exceptionData, nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_filter((nova_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_Lab_Nova_testLambda64, nova_null, &contextArg67)), exceptionData, (nova_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda65, nova_null, &contextArg68)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Mapped backwards: ")), exceptionData, nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_filter((nova_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_Lab_Nova_testLambda66, nova_null, &contextArg69)), exceptionData, (nova_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda67, nova_null, &contextArg70)), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Mapped2: ")), exceptionData, nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(l1_Nova_list2), exceptionData, (nova_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda68, nova_null, &contextArg71)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")))));
	l1_Nova_linked = nova_datastruct_list_Nova_LinkedList_Nova_construct(0, exceptionData);
	nova_datastruct_list_Nova_LinkedList_Nova_addAll((nova_datastruct_list_Nova_LinkedList*)(l1_Nova_linked), exceptionData, (nova_datastruct_list_Nova_Array*)(generated16(0, exceptionData)));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Linked list backwards: ")), exceptionData, nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_datastruct_list_Nova_List*)(l1_Nova_linked), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")))));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Linked list forwards: ")), exceptionData, nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(l1_Nova_linked), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")))));
	l1_Nova_x = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Hey its a string"));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, (nova_Nova_String*)(l1_Nova_x));
	example_Nova_Lab_Nova_takesString(0, exceptionData, (nova_Nova_String*)(l1_Nova_x));
	if (1)
	{
		l1_Nova_x = (nova_Nova_String*)(nova_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData));
		nova_datastruct_list_Nova_Array_0_Nova_add((nova_datastruct_list_Nova_Array*)(l1_Nova_x), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("hey"))));
		nova_datastruct_list_Nova_Array_0_Nova_add((nova_datastruct_list_Nova_Array*)(l1_Nova_x), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("its now an array"))));
	}
	nova_io_Nova_Console_2_Nova_writeLine(0, exceptionData, (nova_Nova_Object*)(l1_Nova_x));
	l1_Nova_sq = example_Nova_Square_Nova_construct(0, exceptionData, 4);
	nova_io_Nova_Console_2_Nova_writeLine(0, exceptionData, (nova_Nova_Object*)(nova_primitive_number_Nova_Double_Nova_construct(0, exceptionData, example_Nova_Lab_Nova_getArea(0, exceptionData, (example_Nova_Polygon*)(l1_Nova_sq)))));
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_Lab_Nova_takesString(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova_s)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Received ")), exceptionData, example_Nova_Lab_Nova_s));
}

void example_Nova_Lab_Nova_doSomething(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_List* example_Nova_Lab_Nova_list)
{
	Context8 contextArg72 = 
	{
	};
	
	nova_datastruct_list_Nova_List_virtual0_Nova_forEach((nova_datastruct_list_Nova_List*)(nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(example_Nova_Lab_Nova_list), exceptionData, (nova_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda69, nova_null, &contextArg72)), exceptionData, (nova_datastruct_list_Nova_List_closure3_Nova_func)&nova_io_Nova_Console_2_Nova_writeLine, 0, nova_null);
}

double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* example_Nova_Lab_Nova_p)
{
	return example_Nova_Polygon_virtual1_Nova_calculateArea((example_Nova_Polygon*)(example_Nova_Lab_Nova_p), exceptionData);
}

void example_Nova_Lab_0_Nova_this(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

nova_datastruct_list_Nova_Array* generated11(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_String** l1_Nova_temp = (nova_Nova_String**)nova_null;
	
	l1_Nova_temp = (nova_Nova_String**)NOVA_MALLOC(sizeof(nova_Nova_String) * 9);
	l1_Nova_temp[0] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("F"));
	l1_Nova_temp[1] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("B"));
	l1_Nova_temp[2] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("A"));
	l1_Nova_temp[3] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("D"));
	l1_Nova_temp[4] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("C"));
	l1_Nova_temp[5] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("E"));
	l1_Nova_temp[6] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("G"));
	l1_Nova_temp[7] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("I"));
	l1_Nova_temp[8] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("H"));
	return nova_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData, (nova_Nova_Object**)(l1_Nova_temp), 9);
}

nova_datastruct_list_Nova_Array* generated12(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_String** l1_Nova_temp = (nova_Nova_String**)nova_null;
	
	l1_Nova_temp = (nova_Nova_String**)NOVA_MALLOC(sizeof(nova_Nova_String) * 7);
	l1_Nova_temp[0] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("this"));
	l1_Nova_temp[1] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("is"));
	l1_Nova_temp[2] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("a"));
	l1_Nova_temp[3] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test"));
	l1_Nova_temp[4] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("to"));
	l1_Nova_temp[5] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("see"));
	l1_Nova_temp[6] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("what shows up"));
	return nova_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData, (nova_Nova_Object**)(l1_Nova_temp), 7);
}

nova_datastruct_list_Nova_IntArray* generated13(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	int* l1_Nova_temp = (int*)nova_null;
	
	l1_Nova_temp = (int*)NOVA_MALLOC(sizeof(nova_primitive_number_Nova_Int) * 7);
	l1_Nova_temp[0] = (int)(1);
	l1_Nova_temp[1] = (int)(2);
	l1_Nova_temp[2] = (int)(3);
	l1_Nova_temp[3] = (int)(4);
	l1_Nova_temp[4] = (int)(5);
	l1_Nova_temp[5] = (int)(6);
	l1_Nova_temp[6] = (int)(7);
	return nova_datastruct_list_Nova_IntArray_2_Nova_construct(0, exceptionData, l1_Nova_temp, 7);
}

void example_Nova_Lab_Nova_testLambda62(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, Context1* context)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Wait a second!")));
	nova_thread_Nova_Thread_Nova_sleep(0, exceptionData, 1000);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("ok, now what")));
}

nova_datastruct_list_Nova_Array* generated14(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_String** l1_Nova_temp = (nova_Nova_String**)nova_null;
	
	l1_Nova_temp = (nova_Nova_String**)NOVA_MALLOC(sizeof(nova_Nova_String) * 2);
	l1_Nova_temp[0] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("This is a test"));
	l1_Nova_temp[1] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("index 2"));
	return nova_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData, (nova_Nova_Object**)(l1_Nova_temp), 2);
}

nova_datastruct_list_Nova_Array* generated15(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_String** l1_Nova_temp = (nova_Nova_String**)nova_null;
	
	l1_Nova_temp = (nova_Nova_String**)NOVA_MALLOC(sizeof(nova_Nova_String) * 2);
	l1_Nova_temp[0] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Second dimension yo"));
	l1_Nova_temp[1] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("waddup"));
	return nova_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData, (nova_Nova_Object**)(l1_Nova_temp), 2);
}

nova_Nova_Object* example_Nova_Lab_Nova_testLambda63(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova_x, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context2* context)
{
	int l2_Nova_something = 0;
	
	l2_Nova_something = 5 * example_Nova_Lab_Nova_x->nova_Nova_String_Nova_count;
	return (nova_Nova_Object*)nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l2_Nova_something))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(" ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((example_Nova_Lab_Nova_x)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))));
}

char example_Nova_Lab_Nova_testLambda64(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context3* context)
{
	return (char)example_Nova_Lab_Nova__1->nova_Nova_String_Nova_count >= 4;
}

nova_Nova_Object* example_Nova_Lab_Nova_testLambda65(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context4* context)
{
	return (nova_Nova_Object*)nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (example_Nova_Lab_Nova__2))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(": ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((example_Nova_Lab_Nova__1)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("?")))));
}

char example_Nova_Lab_Nova_testLambda66(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context5* context)
{
	return (char)example_Nova_Lab_Nova__1->nova_Nova_String_Nova_count >= 4;
}

nova_Nova_Object* example_Nova_Lab_Nova_testLambda67(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context6* context)
{
	return (nova_Nova_Object*)nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((example_Nova_Lab_Nova__1)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("?")));
}

nova_Nova_Object* example_Nova_Lab_Nova_testLambda68(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, int example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_IntArray* example_Nova_Lab_Nova__3, Context7* context)
{
	return (nova_Nova_Object*)nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (example_Nova_Lab_Nova__1))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("!")));
}

nova_datastruct_list_Nova_Array* generated16(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_String** l1_Nova_temp = (nova_Nova_String**)nova_null;
	
	l1_Nova_temp = (nova_Nova_String**)NOVA_MALLOC(sizeof(nova_Nova_String) * 3);
	l1_Nova_temp[0] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test"));
	l1_Nova_temp[1] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test2"));
	l1_Nova_temp[2] = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test3"));
	return nova_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData, (nova_Nova_Object**)(l1_Nova_temp), 3);
}

nova_Nova_Object* example_Nova_Lab_Nova_testLambda69(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_datastruct_list_Nova_List* example_Nova_Lab_Nova__3, Context8* context)
{
	return (nova_Nova_Object*)nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((example_Nova_Lab_Nova__1)), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("!!!")));
}

void example_Nova_Lab_Nova_super(example_Nova_Lab* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

