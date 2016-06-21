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



double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* example_Nova_Lab_Nova_p);
char example_Nova_Lab_Nova_testLambda9(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda10(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x, int example_Nova_Lab_Nova_i);
char example_Nova_Lab_Nova_testLambda11(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda12(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x);
nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda13(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_Lab_Nova_x);
void example_Nova_LabNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Lab* example_Nova_Lab_0_Nova_construct(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
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
	nova_standard_datastruct_Nova_BinaryTree* l1_Nova_tree;
	nova_standard_Nova_String** l1_Nova_a;
	int* l1_Nova_b;
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list2;
	nova_standard_datastruct_list_Nova_LinkedList* l1_Nova_linked;
	
	l1_Nova_tree = nova_standard_datastruct_Nova_BinaryTree_Nova_construct(0, exceptionData);
	nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(l1_Nova_tree, exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "F"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "B"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "A"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "D"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "C"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "E"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "G"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "I"))), exceptionData, (nova_standard_datastruct_Nova_Comparable*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "H")));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Preorder: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_preorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Inorder: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_inorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Postorder: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_postorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Levelorder: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_Nova_Tree_Nova_levelorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "))));
	l1_Nova_a = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 7);
	l1_Nova_a[0] = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "this");
	l1_Nova_a[1] = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "is");
	l1_Nova_a[2] = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "a");
	l1_Nova_a[3] = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "test");
	l1_Nova_a[4] = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "to");
	l1_Nova_a[5] = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "see");
	l1_Nova_a[6] = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "what shows up");
	l1_Nova_b = (int*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Int) * 7);
	l1_Nova_b[0] = 1;
	l1_Nova_b[1] = 2;
	l1_Nova_b[2] = 3;
	l1_Nova_b[3] = 4;
	l1_Nova_b[4] = 5;
	l1_Nova_b[5] = 6;
	l1_Nova_b[6] = 7;
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_a), 7);
	l1_Nova_list2 = nova_standard_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData, (nova_standard_Nova_Object**)&(l1_Nova_b), 7);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Mapped: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_Lab_Nova_testLambda9, (example_Nova_Lab*)nova_null)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda10, (example_Nova_Lab*)nova_null)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Mapped backwards: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list), exceptionData, (nova_standard_datastruct_list_Nova_List_closure15_Nova_filterFunc)&example_Nova_Lab_Nova_testLambda11, (example_Nova_Lab*)nova_null)), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda12, (example_Nova_Lab*)nova_null)), exceptionData)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Mapped2: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_map((nova_standard_datastruct_list_Nova_List*)(l1_Nova_list2), exceptionData, (nova_standard_datastruct_list_Nova_List_closure6_Nova_mapFunc)&example_Nova_Lab_Nova_testLambda13, (example_Nova_Lab*)nova_null)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "))));
	l1_Nova_linked = nova_standard_datastruct_list_Nova_LinkedList_0_Nova_construct(0, exceptionData);
	nova_standard_datastruct_list_Nova_LinkedList_Nova_add(nova_standard_datastruct_list_Nova_LinkedList_Nova_add(nova_standard_datastruct_list_Nova_LinkedList_Nova_add(l1_Nova_linked, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "test"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "test2"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "test3")));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Linked list backwards: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(nova_standard_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_standard_datastruct_list_Nova_List*)(l1_Nova_linked), exceptionData)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Linked list forwards: "), exceptionData, nova_standard_datastruct_list_Nova_List_virtual0_Nova_join((nova_standard_datastruct_list_Nova_List*)(l1_Nova_linked), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* example_Nova_Lab_Nova_p)
{
	return example_Nova_Polygon_virtual1_Nova_calculateArea((example_Nova_Polygon*)(example_Nova_Lab_Nova_p), exceptionData);
}

void example_Nova_Lab_0_Nova_this(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

char example_Nova_Lab_Nova_testLambda9(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x)
{
	return (char)example_Nova_Lab_Nova_x->nova_standard_Nova_String_Nova_size >= 4;
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda10(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x, int example_Nova_Lab_Nova_i)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, example_Nova_Lab_Nova_i)), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ": "), exceptionData, nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(example_Nova_Lab_Nova_x), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "?"))));
}

char example_Nova_Lab_Nova_testLambda11(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x)
{
	return (char)example_Nova_Lab_Nova_x->nova_standard_Nova_String_Nova_size >= 4;
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda12(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_x)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(example_Nova_Lab_Nova_x), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "?"));
}

nova_standard_Nova_Object* example_Nova_Lab_Nova_testLambda13(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_Lab_Nova_x)
{
	return (nova_standard_Nova_Object*)nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, example_Nova_Lab_Nova_x)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "!"));
}

void example_Nova_Lab_Nova_super(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

