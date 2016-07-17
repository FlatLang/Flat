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
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda10(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3);
char example_Nova_Lab_Nova_testLambda11(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda12(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3);
char example_Nova_Lab_Nova_testLambda13(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda14(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda15(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_List* example_Nova_Lab_Nova__3);
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

void example_Nova_Lab_Nova_main(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_Lab_Nova_args)
{
	nova_standard_datastruct_Nova_BinaryTree* l1_Nova_tree = (nova_standard_datastruct_Nova_BinaryTree*)nova_null;
	int l1_Nova_num1 = 0;
	int l1_Nova_num2 = 0;
	nova_standard_Nova_String* l1_Nova_thing = (nova_standard_Nova_String*)nova_null;
	nova_standard_Nova_String* l1_Nova_str = (nova_standard_Nova_String*)nova_null;
	nova_standard_Nova_Object* l1_Nova_str2 = (nova_standard_Nova_Object*)nova_null;
	nova_standard_Nova_String** l1_Nova_a = (nova_standard_Nova_String**)nova_null;
	int* l1_Nova_b = (int*)nova_null;
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	nova_standard_datastruct_list_Nova_IntArray* l1_Nova_list2 = (nova_standard_datastruct_list_Nova_IntArray*)nova_null;
	nova_standard_datastruct_list_Nova_LinkedList* l1_Nova_linked = (nova_standard_datastruct_list_Nova_LinkedList*)nova_null;
	nova_standard_Nova_String* l1_Nova_x = (nova_standard_Nova_String*)nova_null;
	example_Nova_Square* l1_Nova_sq = (example_Nova_Square*)nova_null;
	
	l1_Nova_tree = nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_BinaryTree(0, exceptionData), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "F"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "B"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "A"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "D"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "C"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "E"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "G"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "I"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "H")));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Preorder: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_preorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Inorder: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_inorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Postorder: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_postorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Levelorder: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_levelorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	l1_Nova_num1 = (int)(4);
	l1_Nova_num2 = (int)(l1_Nova_num1 = (int)(7));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "My nums: "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, l1_Nova_num1)), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "), exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, l1_Nova_num2)))));
	l1_Nova_thing = l1_Nova_num1 < 2 ? nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, l1_Nova_num1)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, " < 2")) : nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, l1_Nova_num1)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, " >= 2"));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Result from ternary: "), exceptionData, l1_Nova_thing));
	l1_Nova_str = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "this isnt null");
	l1_Nova_str2 = (nova_standard_Nova_Object*)nova_null;
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Elvis not null: "), exceptionData, (l1_Nova_str != (nova_standard_Nova_String*)nova_null ? l1_Nova_str : nova_standard_Nova_String_1_Nova_String(0, exceptionData, "wtf"))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Elvis null: "), exceptionData, nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)((l1_Nova_str2 != (nova_standard_Nova_Object*)nova_null ? l1_Nova_str2 : (nova_standard_Nova_Object*)nova_standard_Nova_String_1_Nova_String(0, exceptionData, "this is null"))), exceptionData)));
	nova_standard_Nova_String_1_Nova_substring(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test"), exceptionData, 0);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)((nova_standard_datastruct_list_Nova_IntRange_1_Nova_IntRange(0, exceptionData, 2, 8))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", ")));
	l1_Nova_a = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 7);
	l1_Nova_a[0] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "this");
	l1_Nova_a[1] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "is");
	l1_Nova_a[2] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "a");
	l1_Nova_a[3] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test");
	l1_Nova_a[4] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "to");
	l1_Nova_a[5] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "see");
	l1_Nova_a[6] = nova_standard_Nova_String_1_Nova_String(0, exceptionData, "what shows up");
	l1_Nova_b = (int*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Int) * 7);
	l1_Nova_b[0] = (int)(1);
	l1_Nova_b[1] = (int)(2);
	l1_Nova_b[2] = (int)(3);
	l1_Nova_b[3] = (int)(4);
	l1_Nova_b[4] = (int)(5);
	l1_Nova_b[5] = (int)(6);
	l1_Nova_b[6] = (int)(7);
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_2_Nova_Array(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_a), 7);
	l1_Nova_list2 = nova_standard_datastruct_list_Nova_IntArray_2_Nova_IntArray(0, exceptionData, l1_Nova_b, 7);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda10, example_Nova_Lab_Nova_Lab)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", ")));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Mapped: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_Lab_Nova_testLambda11, example_Nova_Lab_Nova_Lab)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda12, example_Nova_Lab_Nova_Lab)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Mapped backwards: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_Lab_Nova_testLambda13, example_Nova_Lab_Nova_Lab)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda14, example_Nova_Lab_Nova_Lab)), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "))));
	l1_Nova_linked = nova_standard_datastruct_list_Nova_LinkedList_Nova_LinkedList(0, exceptionData);
	nova_standard_datastruct_list_Nova_LinkedList_Nova_add(nova_standard_datastruct_list_Nova_LinkedList_Nova_add(nova_standard_datastruct_list_Nova_LinkedList_Nova_add(l1_Nova_linked, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test2"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test3")));
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
	nova_standard_datastruct_list_Nova_List_virtual0_Nova_forEach((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(example_Nova_Lab_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda15, example_Nova_Lab_Nova_Lab)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure3_Nova_func)&nova_standard_io_Nova_Console_2_Nova_writeLine, 0);
}

double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* example_Nova_Lab_Nova_p)
{
	return example_Nova_Polygon_virtual1_Nova_calculateArea((example_Nova_Polygon*)(example_Nova_Lab_Nova_p), exceptionData);
}

void example_Nova_Lab_0_Nova_this(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda10(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3)
{
	char l2_Nova_something = 0;
	
	l2_Nova_something = 5 * example_Nova_Lab_Nova_x->nova_standard_Nova_String_Nova_count;
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, l2_Nova_something)), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, " "), exceptionData, example_Nova_Lab_Nova_x));
}

char example_Nova_Lab_Nova_testLambda11(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3)
{
	return (char)example_Nova_Lab_Nova__1->nova_standard_Nova_String_Nova_count >= 4;
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda12(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, example_Nova_Lab_Nova__2)), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, ": "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(example_Nova_Lab_Nova__1), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "?"))));
}

char example_Nova_Lab_Nova_testLambda13(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3)
{
	return (char)example_Nova_Lab_Nova__1->nova_standard_Nova_String_Nova_count >= 4;
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda14(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova__3)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(example_Nova_Lab_Nova__1), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "?"));
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda15(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* example_Nova_Lab_Nova__1, int example_Nova_Lab_Nova__2, nova_standard_datastruct_list_Nova_List* example_Nova_Lab_Nova__3)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)(example_Nova_Lab_Nova__1), exceptionData)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "!!!"));
}

void example_Nova_Lab_Nova_super(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

