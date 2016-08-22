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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



void example_Nova_Lab_Nova_takesString(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_s);
void example_Nova_Lab_Nova_doSomething(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_List* example_Nova_Lab_Nova_list);
double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* example_Nova_Lab_Nova_p);
nova_standard_datastruct_list_Nova_Array* generated10(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_Array* generated11(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_IntArray* generated12(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_Nova_Lab_Nova_testLambda28(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, Context1* context);
nova_standard_datastruct_list_Nova_Array* generated13(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_Array* generated14(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda29(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context2* context);
char example_Nova_Lab_Nova_testLambda30(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context3* context);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda31(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context4* context);
char example_Nova_Lab_Nova_testLambda32(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context5* context);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda33(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context6* context);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda34(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_IntArray* example_Nova_Lab_Nova__3, Context7* context);
nova_standard_datastruct_list_Nova_Array* generated15(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda35(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_List* example_Nova_Lab_Nova__3, Context8* context);
void example_Nova_Lab_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Lab* example_Nova_Lab_Nova_Lab(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_Lab, this,);
	this->vtable = &example_Extension_VTable_Lab_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_Lab_Nova_super(this, exceptionData);
	
	{
		example_Nova_Lab_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_Lab_Nova_destroy(example_Nova_Lab** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_Lab_Nova_main(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova_args)
{
	nova_standard_datastruct_Nova_BinaryTree* l1_Nova_tree = (nova_standard_datastruct_Nova_BinaryTree*)nova_null;
	int l1_Nova_num1 = 0;
	int l1_Nova_num2 = 0;
	nova_standard_Nova_String* l1_Nova_str = (nova_standard_Nova_String*)nova_null;
	nova_standard_Nova_Object* l1_Nova_str2 = (nova_standard_Nova_Object*)nova_null;
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	nova_standard_datastruct_list_Nova_IntArray* l1_Nova_list2 = (nova_standard_datastruct_list_Nova_IntArray*)nova_null;
	Context1 contextArg31 = 
	{
	};
	nova_standard_datastruct_list_Nova_Array* l1_Nova_multi = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	Context2 contextArg32 = 
	{
	};
	Context3 contextArg33 = 
	{
	};
	Context4 contextArg34 = 
	{
	};
	Context5 contextArg35 = 
	{
	};
	Context6 contextArg36 = 
	{
	};
	Context7 contextArg37 = 
	{
	};
	nova_standard_datastruct_list_Nova_LinkedList* l1_Nova_linked = (nova_standard_datastruct_list_Nova_LinkedList*)nova_null;
	nova_standard_Nova_String* l1_Nova_x = (nova_standard_Nova_String*)nova_null;
	example_Nova_Square* l1_Nova_sq = (example_Nova_Square*)nova_null;
	
	l1_Nova_tree = nova_standard_datastruct_Nova_BinaryTree_Nova_BinaryTree(0, exceptionData, generated10(0, exceptionData));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Preorder: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_preorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", ")))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Inorder: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_inorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", ")))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Postorder: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_postorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", ")))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Levelorder: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_levelorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", ")))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""))));
	l1_Nova_num1 = (int)(4);
	l1_Nova_num2 = (int)(l1_Nova_num1 = (int)(7));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "My nums: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_num1))), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_num2))), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, " hey: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)((nova_standard_datastruct_Nova_Tree_Nova_inorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData))), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "!"))))))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Result from ternary: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_num1))), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, " "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((l1_Nova_num1 < 3 ? nova_standard_Nova_String_1_Nova_String(0, exceptionData, "<") : nova_standard_Nova_String_1_Nova_String(0, exceptionData, ">="))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, " 3"))))));
	l1_Nova_str = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "this isnt null");
	l1_Nova_str2 = (nova_standard_Nova_Object*)nova_null;
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Elvis not null: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((l1_Nova_str != (nova_standard_Nova_String*)nova_null ? l1_Nova_str : nova_standard_Nova_String_1_Nova_String(0, exceptionData, "wtf"))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Elvis null: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)((l1_Nova_str2 != (nova_standard_Nova_Object*)nova_null ? l1_Nova_str2 : (nova_standard_Nova_Object*)nova_standard_Nova_String_1_Nova_String(0, exceptionData, "this is null"))), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""))));
	nova_standard_Nova_String_1_Nova_substring(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test"), exceptionData, 0);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_datastruct_list_Nova_IntRange_0_Nova_toString((nova_standard_datastruct_list_Nova_IntRange_1_Nova_IntRange(0, exceptionData, (int)2, (int)8)), exceptionData)), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, ": "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((nova_standard_datastruct_list_Nova_IntRange_0_Nova_join((nova_standard_datastruct_list_Nova_IntRange_1_Nova_IntRange(0, exceptionData, (int)2, (int)8)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", ")))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "")))));
	l1_Nova_list = generated11(0, exceptionData);
	l1_Nova_list2 = generated12(0, exceptionData);
	nova_standard_thread_async_Nova_Async_Nova_execute(0, exceptionData, (nova_standard_thread_async_Nova_Async_closure3_Nova_func)&example_Nova_Lab_Nova_testLambda28, example_Nova_Lab_Nova_Lab, &contextArg31);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Started async execution"));
	l1_Nova_multi = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_multi, exceptionData, (nova_standard_Nova_Object*)(generated13(0, exceptionData)));
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_multi, exceptionData, (nova_standard_Nova_Object*)(generated14(0, exceptionData)));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Multi dimensional array: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)((l1_Nova_multi)), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_Nova_capitalize(nova_standard_primitive_Nova_Bool_2_Nova_toString(0, exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_contains((nova_standard_datastruct_list_Nova_List*)(((nova_standard_Nova_String*)nova_standard_datastruct_list_Nova_Array_virtual1_Nova_get((nova_standard_datastruct_list_Nova_Array*)(nova_standard_datastruct_list_Nova_Array_virtual1_Nova_get((nova_standard_datastruct_list_Nova_Array*)(l1_Nova_multi), exceptionData, 0)), exceptionData, 0))->nova_standard_Nova_String_Nova_chars), exceptionData, (nova_standard_Nova_Object*)('z'))), exceptionData));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda29, example_Nova_Lab_Nova_Lab, &contextArg32)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", ")));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Mapped: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_Lab_Nova_testLambda30, example_Nova_Lab_Nova_Lab, &contextArg33)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda31, example_Nova_Lab_Nova_Lab, &contextArg34)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Mapped backwards: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_Lab_Nova_testLambda32, example_Nova_Lab_Nova_Lab, &contextArg35)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda33, example_Nova_Lab_Nova_Lab, &contextArg36)), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Mapped2: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list2), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda34, example_Nova_Lab_Nova_Lab, &contextArg37)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	l1_Nova_linked = nova_standard_datastruct_list_Nova_LinkedList_Nova_LinkedList(0, exceptionData);
	nova_standard_datastruct_list_Nova_LinkedList_Nova_addAll(l1_Nova_linked, exceptionData, generated15(0, exceptionData));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Linked list backwards: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_standard_datastruct_list_Nova_List*)(l1_Nova_linked), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Linked list forwards: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(l1_Nova_linked), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	l1_Nova_x = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Hey its a string");
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, l1_Nova_x);
	example_Nova_Lab_Nova_takesString(0, exceptionData, l1_Nova_x);
	if (1)
	{
		l1_Nova_x = (nova_standard_Nova_String*)(nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add((nova_standard_datastruct_list_Nova_Array*)(l1_Nova_x), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "hey")));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add((nova_standard_datastruct_list_Nova_Array*)(l1_Nova_x), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "its now an array")));
	}
	nova_standard_io_Nova_Console_2_Nova_writeLine(0, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_x));
	l1_Nova_sq = example_Nova_Square_Nova_Square(0, exceptionData, 4);
	nova_standard_io_Nova_Console_2_Nova_writeLine(0, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Double_Nova_Double(0, exceptionData, example_Nova_Lab_Nova_getArea(0, exceptionData, (example_Nova_Polygon*)(l1_Nova_sq)))));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_Lab_Nova_takesString(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_s)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Received "), exceptionData, example_Nova_Lab_Nova_s));
}

void example_Nova_Lab_Nova_doSomething(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_List* example_Nova_Lab_Nova_list)
{
	Context8 contextArg38 = 
	{
	};
	
	nova_standard_datastruct_list_Nova_List_virtual0_Nova_forEach((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(example_Nova_Lab_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda35, example_Nova_Lab_Nova_Lab, &contextArg38)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure3_Nova_func)&nova_standard_io_Nova_Console_2_Nova_writeLine, 0, nova_null);
}

double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* example_Nova_Lab_Nova_p)
{
	return example_Nova_Polygon_virtual1_Nova_calculateArea((example_Nova_Polygon*)(example_Nova_Lab_Nova_p), exceptionData);
}

void example_Nova_Lab_0_Nova_this(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

nova_standard_datastruct_list_Nova_Array* generated10(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String** l1_Nova_temp = (nova_standard_Nova_String**)nova_null;
	
	l1_Nova_temp = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 9);
	l1_Nova_temp[0] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "F");
	l1_Nova_temp[1] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "B");
	l1_Nova_temp[2] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "A");
	l1_Nova_temp[3] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "D");
	l1_Nova_temp[4] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "C");
	l1_Nova_temp[5] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "E");
	l1_Nova_temp[6] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "G");
	l1_Nova_temp[7] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "I");
	l1_Nova_temp[8] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "H");
	return nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_temp), 9);
}

nova_standard_datastruct_list_Nova_Array* generated11(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String** l1_Nova_temp = (nova_standard_Nova_String**)nova_null;
	
	l1_Nova_temp = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 7);
	l1_Nova_temp[0] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "this");
	l1_Nova_temp[1] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "is");
	l1_Nova_temp[2] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "a");
	l1_Nova_temp[3] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test");
	l1_Nova_temp[4] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "to");
	l1_Nova_temp[5] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "see");
	l1_Nova_temp[6] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "what shows up");
	return nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_temp), 7);
}

nova_standard_datastruct_list_Nova_IntArray* generated12(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	int* l1_Nova_temp = (int*)nova_null;
	
	l1_Nova_temp = (int*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Int) * 7);
	l1_Nova_temp[0] = (int)(1);
	l1_Nova_temp[1] = (int)(2);
	l1_Nova_temp[2] = (int)(3);
	l1_Nova_temp[3] = (int)(4);
	l1_Nova_temp[4] = (int)(5);
	l1_Nova_temp[5] = (int)(6);
	l1_Nova_temp[6] = (int)(7);
	return nova_standard_datastruct_list_Nova_IntArray_2_Nova_IntArray(0, exceptionData, l1_Nova_temp, 7);
}

void example_Nova_Lab_Nova_testLambda28(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, Context1* context)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Wait a second!"));
	nova_standard_thread_Nova_Thread_Nova_sleep(0, exceptionData, 1000);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "ok, now what"));
}

nova_standard_datastruct_list_Nova_Array* generated13(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String** l1_Nova_temp = (nova_standard_Nova_String**)nova_null;
	
	l1_Nova_temp = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 2);
	l1_Nova_temp[0] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "This is a test");
	l1_Nova_temp[1] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "index 2");
	return nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_temp), 2);
}

nova_standard_datastruct_list_Nova_Array* generated14(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String** l1_Nova_temp = (nova_standard_Nova_String**)nova_null;
	
	l1_Nova_temp = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 2);
	l1_Nova_temp[0] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Second dimension yo");
	l1_Nova_temp[1] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "waddup");
	return nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_temp), 2);
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda29(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context2* context)
{
	char l2_Nova_something = 0;
	
	l2_Nova_something = 5 * example_Nova_Lab_Nova_x->nova_standard_Nova_String_Nova_count;
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, (l2_Nova_something))), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, " "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((example_Nova_Lab_Nova_x)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""))));
}

char example_Nova_Lab_Nova_testLambda30(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context3* context)
{
	return (char)example_Nova_Lab_Nova__1->nova_standard_Nova_String_Nova_count >= 4;
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda31(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context4* context)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (example_Nova_Lab_Nova__2))), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, ": "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((example_Nova_Lab_Nova__1)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "?"))));
}

char example_Nova_Lab_Nova_testLambda32(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context5* context)
{
	return (char)example_Nova_Lab_Nova__1->nova_standard_Nova_String_Nova_count >= 4;
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda33(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3, Context6* context)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((example_Nova_Lab_Nova__1)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "?"));
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda34(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_IntArray* example_Nova_Lab_Nova__3, Context7* context)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (example_Nova_Lab_Nova__1))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "!"));
}

nova_standard_datastruct_list_Nova_Array* generated15(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String** l1_Nova_temp = (nova_standard_Nova_String**)nova_null;
	
	l1_Nova_temp = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 3);
	l1_Nova_temp[0] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test");
	l1_Nova_temp[1] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test2");
	l1_Nova_temp[2] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test3");
	return nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_temp), 3);
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda35(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_List* example_Nova_Lab_Nova__3, Context8* context)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)((example_Nova_Lab_Nova__1)), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "!!!"));
}

void example_Nova_Lab_Nova_super(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

