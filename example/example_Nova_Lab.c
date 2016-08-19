#include <precompiled.h>
#include <example/example_Nova_Lab.h>

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
nova_standard_datastruct_list_Nova_Array* generated9(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_Array* generated10(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_IntArray* generated11(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_Array* generated12(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_Array* generated13(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda11(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3);
char example_Nova_Lab_Nova_testLambda12(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda13(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3);
char example_Nova_Lab_Nova_testLambda14(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda15(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda16(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_IntArray* example_Nova_Lab_Nova__3);
nova_standard_datastruct_list_Nova_Array* generated14(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda17(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_List* example_Nova_Lab_Nova__3);
void example_Nova_LabNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
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
		nova_standard_datastruct_list_Nova_Array* l1_Nova_multi = (nova_standard_datastruct_list_Nova_Array*)nova_null;
		nova_standard_datastruct_list_Nova_LinkedList* l1_Nova_linked = (nova_standard_datastruct_list_Nova_LinkedList*)nova_null;
		nova_standard_Nova_String* l1_Nova_x = (nova_standard_Nova_String*)nova_null;
		example_Nova_Square* l1_Nova_sq = (example_Nova_Square*)nova_null;
		
		l1_Nova_tree = nova_standard_datastruct_Nova_BinaryTree_Nova_BinaryTree(0, exceptionData, generated9(0, exceptionData));
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
		l1_Nova_list = generated10(0, exceptionData);
		l1_Nova_list2 = generated11(0, exceptionData);
		l1_Nova_multi = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_multi, exceptionData, (nova_standard_Nova_Object*)(generated12(0, exceptionData)));
		nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_multi, exceptionData, (nova_standard_Nova_Object*)(generated13(0, exceptionData)));
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Multi dimensional array: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)((l1_Nova_multi)), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""))));
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda11, example_Nova_Lab_Nova_Lab)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", ")));
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Mapped: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_Lab_Nova_testLambda12, example_Nova_Lab_Nova_Lab)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda13, example_Nova_Lab_Nova_Lab)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Mapped backwards: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_Lab_Nova_testLambda14, example_Nova_Lab_Nova_Lab)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda15, example_Nova_Lab_Nova_Lab)), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Mapped2: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list2), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda16, example_Nova_Lab_Nova_Lab)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
		l1_Nova_linked = nova_standard_datastruct_list_Nova_LinkedList_Nova_LinkedList(0, exceptionData);
		nova_standard_datastruct_list_Nova_LinkedList_Nova_addAll(l1_Nova_linked, exceptionData, generated14(0, exceptionData));
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
		nova_standard_datastruct_list_Nova_List_virtual0_Nova_forEach((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(example_Nova_Lab_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda17, example_Nova_Lab_Nova_Lab)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure3_Nova_func)&nova_standard_io_Nova_Console_2_Nova_writeLine, 0);
}

double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* example_Nova_Lab_Nova_p)
{
		return example_Nova_Polygon_virtual1_Nova_calculateArea((example_Nova_Polygon*)(example_Nova_Lab_Nova_p), exceptionData);
}

void example_Nova_Lab_0_Nova_this(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

nova_standard_datastruct_list_Nova_Array* generated9(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
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

nova_standard_datastruct_list_Nova_Array* generated10(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
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

nova_standard_datastruct_list_Nova_IntArray* generated11(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
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

nova_standard_datastruct_list_Nova_Array* generated12(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		nova_standard_Nova_String** l1_Nova_temp = (nova_standard_Nova_String**)nova_null;
		
		l1_Nova_temp = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 2);
		l1_Nova_temp[0] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "This is a test");
		l1_Nova_temp[1] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "index 2");
		return nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_temp), 2);
}

nova_standard_datastruct_list_Nova_Array* generated13(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		nova_standard_Nova_String** l1_Nova_temp = (nova_standard_Nova_String**)nova_null;
		
		l1_Nova_temp = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 2);
		l1_Nova_temp[0] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Second dimension yo");
		l1_Nova_temp[1] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "waddup");
		return nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_temp), 2);
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda11(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3)
{
		char l2_Nova_something = 0;
		
		l2_Nova_something = 5 * example_Nova_Lab_Nova_x->nova_standard_Nova_String_Nova_count;
		return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, (l2_Nova_something))), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, " "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((example_Nova_Lab_Nova_x)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""))));
}

char example_Nova_Lab_Nova_testLambda12(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3)
{
		return (char)example_Nova_Lab_Nova__1->nova_standard_Nova_String_Nova_count >= 4;
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda13(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3)
{
		return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (example_Nova_Lab_Nova__2))), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, ": "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((example_Nova_Lab_Nova__1)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "?"))));
}

char example_Nova_Lab_Nova_testLambda14(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3)
{
		return (char)example_Nova_Lab_Nova__1->nova_standard_Nova_String_Nova_count >= 4;
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda15(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3)
{
		return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)((example_Nova_Lab_Nova__1)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "?"));
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda16(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_IntArray* example_Nova_Lab_Nova__3)
{
		return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (example_Nova_Lab_Nova__1))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "!"));
}

nova_standard_datastruct_list_Nova_Array* generated14(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		nova_standard_Nova_String** l1_Nova_temp = (nova_standard_Nova_String**)nova_null;
		
		l1_Nova_temp = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 3);
		l1_Nova_temp[0] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test");
		l1_Nova_temp[1] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test2");
		l1_Nova_temp[2] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test3");
		return nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_temp), 3);
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda17(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_List* example_Nova_Lab_Nova__3)
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
		
		nova_standard_Nova_ClassNova_init_static(exceptionData);
		nova_standard_Nova_ObjectNova_init_static(exceptionData);
		nova_standard_Nova_StringNova_init_static(exceptionData);
		nova_standard_Nova_SystemNova_init_static(exceptionData);
		nova_standard_database_Nova_DBConnectorNova_init_static(exceptionData);
		nova_standard_database_Nova_ResultSetNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_BinaryNodeNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_BinaryTreeNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_BoundsNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_ComparableNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_HashMapNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_NodeNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_ReversibleHashMapNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_TreeNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_VectorNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_Vector2DNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_ArrayNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_ArrayIteratorNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_CharArrayNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_CharArrayIteratorNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_CompiledListNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_DoubleArrayNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_DoubleArrayIteratorNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_EmptyStackExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IntArrayNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IntArrayIteratorNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IntRangeNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IntRangeIteratorNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IterableNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IteratorNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_LinkedListNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_LinkedListIteratorNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_ListNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_ListNodeNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_NoSuchElementExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_QueueNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_StackNova_init_static(exceptionData);
		nova_standard_exception_Nova_DivideByZeroExceptionNova_init_static(exceptionData);
		nova_standard_exception_Nova_ExceptionNova_init_static(exceptionData);
		nova_standard_exception_Nova_ExceptionDataNova_init_static(exceptionData);
		nova_standard_exception_Nova_UnimplementedOperationExceptionNova_init_static(exceptionData);
		nova_standard_gc_Nova_GCNova_init_static(exceptionData);
		nova_standard_io_Nova_ConsoleNova_init_static(exceptionData);
		nova_standard_io_Nova_FileNova_init_static(exceptionData);
		nova_standard_io_Nova_FileNova_init_static(exceptionData);
		nova_standard_io_Nova_FileNotFoundExceptionNova_init_static(exceptionData);
		nova_standard_io_Nova_InputStreamNova_init_static(exceptionData);
		nova_standard_io_Nova_OutputStreamNova_init_static(exceptionData);
		nova_standard_io_Nova_StreamReaderNova_init_static(exceptionData);
		nova_standard_math_Nova_ArithmeticSequenceNova_init_static(exceptionData);
		nova_standard_math_Nova_DiekstraNova_init_static(exceptionData);
		nova_standard_math_Nova_GeometricSequenceNova_init_static(exceptionData);
		nova_standard_math_Nova_GraphNova_init_static(exceptionData);
		nova_standard_math_Nova_InvalidNumericStatementExceptionNova_init_static(exceptionData);
		nova_standard_math_Nova_MathNova_init_static(exceptionData);
		nova_standard_math_Nova_MatrixNova_init_static(exceptionData);
		nova_standard_math_Nova_NumericOperandNova_init_static(exceptionData);
		nova_standard_math_Nova_NumericOperationNova_init_static(exceptionData);
		nova_standard_math_Nova_NumericStatementNova_init_static(exceptionData);
		nova_standard_math_Nova_NumericTreeNova_init_static(exceptionData);
		nova_standard_math_Nova_PolynomialNova_init_static(exceptionData);
		nova_standard_math_Nova_SequenceNova_init_static(exceptionData);
		nova_standard_math_Nova_StatementNova_init_static(exceptionData);
		nova_standard_math_Nova_StatementComponentNova_init_static(exceptionData);
		nova_standard_math_Nova_VariableOperandNova_init_static(exceptionData);
		nova_standard_math_calculus_Nova_CalculusNova_init_static(exceptionData);
		nova_standard_math_huffman_Nova_HuffmanTreeNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_ConclusionNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_HypothesisNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_InvalidFormulaExceptionNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_LogicalConnectiveNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_LogicalStatementNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_StatementComponentNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_StatementGroupNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_StatementLetterNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_WFFNova_init_static(exceptionData);
		nova_standard_network_Nova_ClientSocketNova_init_static(exceptionData);
		nova_standard_network_Nova_ConnectionSocketNova_init_static(exceptionData);
		nova_standard_network_Nova_NetworkInputStreamNova_init_static(exceptionData);
		nova_standard_network_Nova_NetworkOutputStreamNova_init_static(exceptionData);
		nova_standard_network_Nova_ServerSocketNova_init_static(exceptionData);
		nova_standard_network_Nova_SocketNova_init_static(exceptionData);
		nova_standard_operators_Nova_EqualsNova_init_static(exceptionData);
		nova_standard_operators_Nova_MultiplyNova_init_static(exceptionData);
		nova_standard_primitive_Nova_BoolNova_init_static(exceptionData);
		nova_standard_primitive_Nova_NullNova_init_static(exceptionData);
		nova_standard_primitive_Nova_PrimitiveNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_ByteNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_CharNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_DoubleNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_FloatNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_IntNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_IntegerNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_LongNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_NumberNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_RealNumberNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_ShortNova_init_static(exceptionData);
		nova_standard_process_Nova_ProcessNova_init_static(exceptionData);
		nova_standard_security_Nova_MD5Nova_init_static(exceptionData);
		nova_standard_star_Nova_WindowNova_init_static(exceptionData);
		nova_standard_star_Nova_WindowThreadNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGCircleNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGComponentNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGComponentListNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGComponentNodeNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGMainComponentNova_init_static(exceptionData);
		nova_standard_thread_Nova_ThreadNova_init_static(exceptionData);
		nova_standard_thread_Nova_UncaughtExceptionHandlerNova_init_static(exceptionData);
		nova_standard_thread_async_Nova_AsyncNova_init_static(exceptionData);
		nova_standard_thread_async_Nova_AsyncResultNova_init_static(exceptionData);
		nova_standard_time_Nova_DateNova_init_static(exceptionData);
		nova_standard_time_Nova_TimeNova_init_static(exceptionData);
		nova_standard_time_Nova_TimerNova_init_static(exceptionData);
		example_Nova_AnimalNova_init_static(exceptionData);
		example_Nova_ArrayDemoNova_init_static(exceptionData);
		example_Nova_BodyBuilderNova_init_static(exceptionData);
		example_Nova_ClosureDemoNova_init_static(exceptionData);
		example_Nova_DogNova_init_static(exceptionData);
		example_Nova_ExceptionHandlingDemoNova_init_static(exceptionData);
		example_Nova_FileTestNova_init_static(exceptionData);
		example_Nova_GenericDemoNova_init_static(exceptionData);
		example_Nova_HashMapDemoNova_init_static(exceptionData);
		example_Nova_IntegerTestNova_init_static(exceptionData);
		example_Nova_LabNova_init_static(exceptionData);
		example_Nova_MathDemoNova_init_static(exceptionData);
		example_Nova_NonWholeDivisionExceptionNova_init_static(exceptionData);
		example_Nova_PersonNova_init_static(exceptionData);
		example_Nova_PolygonNova_init_static(exceptionData);
		example_Nova_PolymorphismDemoNova_init_static(exceptionData);
		example_Nova_QueueDemoNova_init_static(exceptionData);
		example_Nova_SpiderNova_init_static(exceptionData);
		example_Nova_SquareNova_init_static(exceptionData);
		example_Nova_SVGTestNova_init_static(exceptionData);
		example_Nova_T1Nova_init_static(exceptionData);
		example_Nova_T2Nova_init_static(exceptionData);
		example_Nova_TestNova_init_static(exceptionData);
		example_Nova_ThreadDemoNova_init_static(exceptionData);
		example_Nova_ThreadDemoImplementationNova_init_static(exceptionData);
		example_ackermann_Nova_AckermannNova_init_static(exceptionData);
		example_copy_Nova_DogNova_init_static(exceptionData);
		example_database_Nova_DatabaseDemoNova_init_static(exceptionData);
		example_network_Nova_ClientDemoNova_init_static(exceptionData);
		example_network_Nova_ConnectionThreadNova_init_static(exceptionData);
		example_network_Nova_OutputThreadNova_init_static(exceptionData);
		example_network_Nova_ServerDemoNova_init_static(exceptionData);
		stabilitytest_Nova_AssignmentStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_ClassWithPropertiesNova_init_static(exceptionData);
		stabilitytest_Nova_ClientThreadNova_init_static(exceptionData);
		stabilitytest_Nova_ClosureStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_ExceptionStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_FileStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_LambdaStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_NetworkStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_PolymorphicSubClassNova_init_static(exceptionData);
		stabilitytest_Nova_PolymorphicSuperClassNova_init_static(exceptionData);
		stabilitytest_Nova_PolymorphismStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_StabilityExceptionHandlerNova_init_static(exceptionData);
		stabilitytest_Nova_StabilityTestNova_init_static(exceptionData);
		stabilitytest_Nova_StabilityTestCaseNova_init_static(exceptionData);
		stabilitytest_Nova_StabilityTestExceptionNova_init_static(exceptionData);
		stabilitytest_Nova_SyntaxStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_ThreadImplementationNova_init_static(exceptionData);
		stabilitytest_Nova_ThreadStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_TimeStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_ToStringStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_UnstableExceptionNova_init_static(exceptionData);
		
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
