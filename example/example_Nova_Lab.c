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



nova_standard_primitive_Nova_Null* nova_null;
void* nova_garbageData;

int main(int argc, char** argvs)
{
		nova_standard_Nova_String** args;
		int      i;
		
		nova_standard_exception_Nova_ExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_garbageData = malloc(sizeof(void*));
		nova_standard_gc_Nova_GC_Nova_init(0, exceptionData);
		
		nova_null = nova_standard_primitive_Nova_Null_Nova_Null(0, exceptionData);
		novaEnv.nova_standard_Object.getHashCodeLong = nova_standard_Extension_VTable_Object_val.nova_standard_Nova_Object_virtual1_Nova_getHashCodeLong;
		novaEnv.nova_standard_Object.toString = nova_standard_Extension_VTable_Object_val.nova_standard_Nova_Object_virtual1_Nova_toString;
		novaEnv.nova_standard_Object.equals__nova_standard_Object = nova_standard_Extension_VTable_Object_val.nova_standard_operators_Nova_Equals_virtual0_Nova_equals;
		novaEnv.nova_standard_String.concat__nova_standard_String = nova_standard_Extension_VTable_String_val.nova_standard_Nova_String_virtual1_Nova_concat;
		novaEnv.nova_standard_String.toString = nova_standard_Extension_VTable_String_val.nova_standard_Nova_Object_virtual1_Nova_toString;
		novaEnv.nova_standard_datastruct_Comparable.compareTo__nova_standard_Object = nova_standard_datastruct_Extension_VTable_Comparable_val.itable.nova_standard_datastruct_Nova_Comparable_virtual0_Nova_compareTo;
		novaEnv.nova_standard_datastruct_HashMap.put__nova_standard_Object__nova_standard_Object = nova_standard_datastruct_Extension_VTable_HashMap_val.nova_standard_datastruct_Nova_HashMap_virtual1_Nova_put;
		novaEnv.nova_standard_datastruct_list_Array.get__nova_standard_primitive_number_Int = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_Array_virtual1_Nova_get;
		novaEnv.nova_standard_datastruct_list_Array.map__nova_standard_Object = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_List_virtual0_Nova_map;
		novaEnv.nova_standard_datastruct_list_Array.forEach__void = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_List_virtual0_Nova_forEach;
		novaEnv.nova_standard_datastruct_list_Array.any__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_List_virtual0_Nova_any;
		novaEnv.nova_standard_datastruct_list_Array.all__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_List_virtual0_Nova_all;
		novaEnv.nova_standard_datastruct_list_Array.filter__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter;
		novaEnv.nova_standard_datastruct_list_Array.take__nova_standard_primitive_number_Int = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_List_virtual0_Nova_take;
		novaEnv.nova_standard_datastruct_list_Array.skip__nova_standard_primitive_number_Int = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_List_virtual0_Nova_skip;
		novaEnv.nova_standard_datastruct_list_Array.firstWhere__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_List_virtual0_Nova_firstWhere;
		novaEnv.nova_standard_datastruct_list_Array.reverse = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_List_virtual0_Nova_reverse;
		novaEnv.nova_standard_datastruct_list_Array.join__nova_standard_String = nova_standard_datastruct_list_Extension_VTable_Array_val.nova_standard_datastruct_list_Nova_List_virtual0_Nova_join;
		novaEnv.nova_standard_datastruct_list_Iterator.reset = nova_standard_datastruct_list_Extension_VTable_Iterator_val.itable.nova_standard_datastruct_list_Nova_Iterator_virtual0_Nova_reset;
		novaEnv.nova_standard_datastruct_list_List.toArray = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_toArray;
		novaEnv.nova_standard_datastruct_list_List.contains__nova_standard_Object = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_contains;
		novaEnv.nova_standard_datastruct_list_List.forEach__void = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_forEach;
		novaEnv.nova_standard_datastruct_list_List.map__nova_standard_Object = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_map;
		novaEnv.nova_standard_datastruct_list_List.any__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_any;
		novaEnv.nova_standard_datastruct_list_List.all__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_all;
		novaEnv.nova_standard_datastruct_list_List.filter__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter;
		novaEnv.nova_standard_datastruct_list_List.take__nova_standard_primitive_number_Int = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_take;
		novaEnv.nova_standard_datastruct_list_List.skip__nova_standard_primitive_number_Int = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_skip;
		novaEnv.nova_standard_datastruct_list_List.firstWhere__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_firstWhere;
		novaEnv.nova_standard_datastruct_list_List.reverse = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_reverse;
		novaEnv.nova_standard_datastruct_list_List.join__nova_standard_String = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_join;
		novaEnv.nova_standard_io_InputStream.readString = nova_standard_io_Extension_VTable_InputStream_val.itable.nova_standard_io_Nova_InputStream_virtual1_Nova_readString;
		novaEnv.nova_standard_io_InputStream.readBytes = nova_standard_io_Extension_VTable_InputStream_val.itable.nova_standard_io_Nova_InputStream_virtual1_Nova_readBytes;
		novaEnv.nova_standard_io_OutputStream.write__nova_standard_String = nova_standard_io_Extension_VTable_OutputStream_val.nova_standard_io_Nova_OutputStream_virtual0_Nova_write;
		novaEnv.nova_standard_io_OutputStream.write__nova_standard_Object = nova_standard_io_Extension_VTable_OutputStream_val.nova_standard_io_Nova_OutputStream_virtual1_Nova_write;
		novaEnv.nova_standard_math_NumericOperand.toString = nova_standard_math_Extension_VTable_NumericOperand_val.nova_standard_Nova_Object_virtual1_Nova_toString;
		novaEnv.nova_standard_operators_Equals.equals__nova_standard_Object = nova_standard_operators_Extension_VTable_Equals_val.itable.nova_standard_operators_Nova_Equals_virtual0_Nova_equals;
		novaEnv.nova_standard_operators_Multiply.multiply__nova_standard_Object = nova_standard_operators_Extension_VTable_Multiply_val.itable.nova_standard_operators_Nova_Multiply_virtual1_Nova_multiply;
		novaEnv.nova_standard_primitive_number_Number.numDigits__nova_standard_primitive_number_Number = nova_standard_primitive_number_Extension_VTable_Number_val.nova_standard_primitive_number_Nova_Number_virtual0_Nova_numDigits;
		novaEnv.nova_standard_svg_SVGComponent.generateOutput__nova_standard_io_File = nova_standard_svg_Extension_VTable_SVGComponent_val.nova_standard_svg_Nova_SVGComponent_virtual0_Nova_generateOutput;
		novaEnv.nova_standard_thread_Thread.run = nova_standard_thread_Extension_VTable_Thread_val.nova_standard_thread_Nova_Thread_virtual0_Nova_run;
		novaEnv.nova_standard_thread_UncaughtExceptionHandler.uncaughtException__nova_standard_thread_Thread__nova_standard_exception_Exception = nova_standard_thread_Extension_VTable_UncaughtExceptionHandler_val.nova_standard_thread_Nova_UncaughtExceptionHandler_virtual1_Nova_uncaughtException;
		novaEnv.example_Animal.getNumLegs = example_Extension_VTable_Animal_val.example_Nova_Animal_virtual1_Nova_getNumLegs;
		novaEnv.example_Animal.getNumEyes = example_Extension_VTable_Animal_val.example_Nova_Animal_virtual1_Nova_getNumEyes;
		novaEnv.example_Animal.getDescription = example_Extension_VTable_Animal_val.example_Nova_Animal_virtual1_Nova_getDescription;
		novaEnv.example_Person.sayHello = example_Extension_VTable_Person_val.example_Nova_Person_virtual0_Nova_sayHello;
		novaEnv.example_Polygon.numberSides = example_Extension_VTable_Polygon_val.itable.example_Nova_Polygon_virtual1_Nova_numberSides;
		novaEnv.example_Polygon.calculateArea = example_Extension_VTable_Polygon_val.itable.example_Nova_Polygon_virtual1_Nova_calculateArea;
		novaEnv.stabilitytest_PolymorphicSuperClass.toString = stabilitytest_Extension_VTable_PolymorphicSuperClass_val.nova_standard_Nova_Object_virtual1_Nova_toString;
		novaEnv.stabilitytest_StabilityTestCase.test = stabilitytest_Extension_VTable_StabilityTestCase_val.stabilitytest_Nova_StabilityTestCase_virtual0_Nova_test;
		
		nova_standard_Nova_Class_Nova_init_static(exceptionData);
		nova_standard_Nova_Object_Nova_init_static(exceptionData);
		nova_standard_Nova_String_Nova_init_static(exceptionData);
		nova_standard_Nova_System_Nova_init_static(exceptionData);
		nova_standard_database_Nova_DBConnector_Nova_init_static(exceptionData);
		nova_standard_database_Nova_ResultSet_Nova_init_static(exceptionData);
		nova_standard_datastruct_Nova_BinaryNode_Nova_init_static(exceptionData);
		nova_standard_datastruct_Nova_BinaryTree_Nova_init_static(exceptionData);
		nova_standard_datastruct_Nova_Bounds_Nova_init_static(exceptionData);
		nova_standard_datastruct_Nova_Comparable_Nova_init_static(exceptionData);
		nova_standard_datastruct_Nova_HashMap_Nova_init_static(exceptionData);
		nova_standard_datastruct_Nova_Node_Nova_init_static(exceptionData);
		nova_standard_datastruct_Nova_ReversibleHashMap_Nova_init_static(exceptionData);
		nova_standard_datastruct_Nova_Tree_Nova_init_static(exceptionData);
		nova_standard_datastruct_Nova_Vector_Nova_init_static(exceptionData);
		nova_standard_datastruct_Nova_Vector2D_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_Array_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_ArrayIterator_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_CharArray_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_CharArrayIterator_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_CompiledList_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_DoubleArray_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_DoubleArrayIterator_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_EmptyStackException_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IntArray_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IntArrayIterator_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IntRange_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IntRangeIterator_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_Iterable_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_Iterator_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_LinkedList_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_LinkedListIterator_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_List_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_ListNode_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_NoSuchElementException_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_Queue_Nova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_Stack_Nova_init_static(exceptionData);
		nova_standard_exception_Nova_DivideByZeroException_Nova_init_static(exceptionData);
		nova_standard_exception_Nova_Exception_Nova_init_static(exceptionData);
		nova_standard_exception_Nova_ExceptionData_Nova_init_static(exceptionData);
		nova_standard_exception_Nova_UnimplementedOperationException_Nova_init_static(exceptionData);
		nova_standard_gc_Nova_GC_Nova_init_static(exceptionData);
		nova_standard_io_Nova_Console_Nova_init_static(exceptionData);
		nova_standard_io_Nova_File_Nova_init_static(exceptionData);
		nova_standard_io_Nova_File_Nova_init_static(exceptionData);
		nova_standard_io_Nova_FileNotFoundException_Nova_init_static(exceptionData);
		nova_standard_io_Nova_InputStream_Nova_init_static(exceptionData);
		nova_standard_io_Nova_OutputStream_Nova_init_static(exceptionData);
		nova_standard_io_Nova_StreamReader_Nova_init_static(exceptionData);
		nova_standard_math_Nova_ArithmeticSequence_Nova_init_static(exceptionData);
		nova_standard_math_Nova_Diekstra_Nova_init_static(exceptionData);
		nova_standard_math_Nova_GeometricSequence_Nova_init_static(exceptionData);
		nova_standard_math_Nova_Graph_Nova_init_static(exceptionData);
		nova_standard_math_Nova_InvalidNumericStatementException_Nova_init_static(exceptionData);
		nova_standard_math_Nova_Math_Nova_init_static(exceptionData);
		nova_standard_math_Nova_Matrix_Nova_init_static(exceptionData);
		nova_standard_math_Nova_NumericOperand_Nova_init_static(exceptionData);
		nova_standard_math_Nova_NumericOperation_Nova_init_static(exceptionData);
		nova_standard_math_Nova_NumericStatement_Nova_init_static(exceptionData);
		nova_standard_math_Nova_NumericTree_Nova_init_static(exceptionData);
		nova_standard_math_Nova_Polynomial_Nova_init_static(exceptionData);
		nova_standard_math_Nova_Sequence_Nova_init_static(exceptionData);
		nova_standard_math_Nova_Statement_Nova_init_static(exceptionData);
		nova_standard_math_Nova_StatementComponent_Nova_init_static(exceptionData);
		nova_standard_math_Nova_VariableOperand_Nova_init_static(exceptionData);
		nova_standard_math_calculus_Nova_Calculus_Nova_init_static(exceptionData);
		nova_standard_math_huffman_Nova_HuffmanTree_Nova_init_static(exceptionData);
		nova_standard_math_logic_Nova_Conclusion_Nova_init_static(exceptionData);
		nova_standard_math_logic_Nova_Hypothesis_Nova_init_static(exceptionData);
		nova_standard_math_logic_Nova_InvalidFormulaException_Nova_init_static(exceptionData);
		nova_standard_math_logic_Nova_LogicalConnective_Nova_init_static(exceptionData);
		nova_standard_math_logic_Nova_LogicalStatement_Nova_init_static(exceptionData);
		nova_standard_math_logic_Nova_StatementComponent_Nova_init_static(exceptionData);
		nova_standard_math_logic_Nova_StatementGroup_Nova_init_static(exceptionData);
		nova_standard_math_logic_Nova_StatementLetter_Nova_init_static(exceptionData);
		nova_standard_math_logic_Nova_WFF_Nova_init_static(exceptionData);
		nova_standard_network_Nova_ClientSocket_Nova_init_static(exceptionData);
		nova_standard_network_Nova_ConnectionSocket_Nova_init_static(exceptionData);
		nova_standard_network_Nova_NetworkInputStream_Nova_init_static(exceptionData);
		nova_standard_network_Nova_NetworkOutputStream_Nova_init_static(exceptionData);
		nova_standard_network_Nova_ServerSocket_Nova_init_static(exceptionData);
		nova_standard_network_Nova_Socket_Nova_init_static(exceptionData);
		nova_standard_operators_Nova_Equals_Nova_init_static(exceptionData);
		nova_standard_operators_Nova_Multiply_Nova_init_static(exceptionData);
		nova_standard_primitive_Nova_Bool_Nova_init_static(exceptionData);
		nova_standard_primitive_Nova_Null_Nova_init_static(exceptionData);
		nova_standard_primitive_Nova_Primitive_Nova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_Byte_Nova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_Char_Nova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_Double_Nova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_Float_Nova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_Int_Nova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_Integer_Nova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_Long_Nova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_Number_Nova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_RealNumber_Nova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_Short_Nova_init_static(exceptionData);
		nova_standard_process_Nova_Process_Nova_init_static(exceptionData);
		nova_standard_security_Nova_MD5_Nova_init_static(exceptionData);
		nova_standard_star_Nova_Window_Nova_init_static(exceptionData);
		nova_standard_star_Nova_WindowThread_Nova_init_static(exceptionData);
		nova_standard_svg_Nova_SVG_Nova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGCircle_Nova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGComponent_Nova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGComponentList_Nova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGComponentNode_Nova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGMainComponent_Nova_init_static(exceptionData);
		nova_standard_thread_Nova_Thread_Nova_init_static(exceptionData);
		nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_init_static(exceptionData);
		nova_standard_thread_async_Nova_Async_Nova_init_static(exceptionData);
		nova_standard_thread_async_Nova_AsyncResult_Nova_init_static(exceptionData);
		nova_standard_time_Nova_Date_Nova_init_static(exceptionData);
		nova_standard_time_Nova_Time_Nova_init_static(exceptionData);
		nova_standard_time_Nova_Timer_Nova_init_static(exceptionData);
		example_Nova_Animal_Nova_init_static(exceptionData);
		example_Nova_ArrayDemo_Nova_init_static(exceptionData);
		example_Nova_BodyBuilder_Nova_init_static(exceptionData);
		example_Nova_ClosureDemo_Nova_init_static(exceptionData);
		example_Nova_Dog_Nova_init_static(exceptionData);
		example_Nova_ExceptionHandlingDemo_Nova_init_static(exceptionData);
		example_Nova_FileTest_Nova_init_static(exceptionData);
		example_Nova_GenericDemo_Nova_init_static(exceptionData);
		example_Nova_HashMapDemo_Nova_init_static(exceptionData);
		example_Nova_IntegerTest_Nova_init_static(exceptionData);
		example_Nova_Lab_Nova_init_static(exceptionData);
		example_Nova_MathDemo_Nova_init_static(exceptionData);
		example_Nova_NonWholeDivisionException_Nova_init_static(exceptionData);
		example_Nova_Person_Nova_init_static(exceptionData);
		example_Nova_Polygon_Nova_init_static(exceptionData);
		example_Nova_PolymorphismDemo_Nova_init_static(exceptionData);
		example_Nova_QueueDemo_Nova_init_static(exceptionData);
		example_Nova_Spider_Nova_init_static(exceptionData);
		example_Nova_Square_Nova_init_static(exceptionData);
		example_Nova_SVGTest_Nova_init_static(exceptionData);
		example_Nova_T1_Nova_init_static(exceptionData);
		example_Nova_T2_Nova_init_static(exceptionData);
		example_Nova_Test_Nova_init_static(exceptionData);
		example_Nova_ThreadDemo_Nova_init_static(exceptionData);
		example_Nova_ThreadDemoImplementation_Nova_init_static(exceptionData);
		example_ackermann_Nova_Ackermann_Nova_init_static(exceptionData);
		example_copy_Nova_Dog_Nova_init_static(exceptionData);
		example_database_Nova_DatabaseDemo_Nova_init_static(exceptionData);
		example_network_Nova_ClientDemo_Nova_init_static(exceptionData);
		example_network_Nova_ConnectionThread_Nova_init_static(exceptionData);
		example_network_Nova_OutputThread_Nova_init_static(exceptionData);
		example_network_Nova_ServerDemo_Nova_init_static(exceptionData);
		stabilitytest_Nova_AssignmentStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_ClassWithProperties_Nova_init_static(exceptionData);
		stabilitytest_Nova_ClientThread_Nova_init_static(exceptionData);
		stabilitytest_Nova_ClosureStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_ExceptionStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_FileStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_LambdaStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_NetworkStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_PolymorphicSubClass_Nova_init_static(exceptionData);
		stabilitytest_Nova_PolymorphicSuperClass_Nova_init_static(exceptionData);
		stabilitytest_Nova_PolymorphismStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_StabilityExceptionHandler_Nova_init_static(exceptionData);
		stabilitytest_Nova_StabilityTest_Nova_init_static(exceptionData);
		stabilitytest_Nova_StabilityTestCase_Nova_init_static(exceptionData);
		stabilitytest_Nova_StabilityTestException_Nova_init_static(exceptionData);
		stabilitytest_Nova_SyntaxStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_ThreadImplementation_Nova_init_static(exceptionData);
		stabilitytest_Nova_ThreadStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_TimeStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_ToStringStability_Nova_init_static(exceptionData);
		stabilitytest_Nova_UnstableException_Nova_init_static(exceptionData);
		
		args = (nova_standard_Nova_String**)NOVA_MALLOC(argc * sizeof(nova_standard_Nova_String));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_Nova_String_1_Nova_String(0, 0, str);
		}
		nova_standard_datastruct_list_Nova_Array* argsArray = nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)args, argc);
		TRY
		{
				example_Nova_Lab_Nova_main(0, exceptionData, argsArray);
		}
		CATCH (1)
		{
				nova_standard_exception_Nova_Exception* base = (nova_standard_exception_Nova_Exception*)exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException;
				printf("Exception in Thread 'main': %s", base->nova_standard_exception_Nova_Exception_Nova_message->nova_standard_Nova_String_Nova_chars);
				nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		FreeConsole();
		NOVA_FREE(args);
		nova_standard_gc_Nova_GC_Nova_collect(0, exceptionData);
		
		
		return 0;
}
