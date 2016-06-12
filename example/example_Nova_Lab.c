#include <precompiled.h>
#include <example/example_Nova_Lab.h>

example_Extension_VTable_Lab example_Extension_VTable_Lab_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



char example_Nova_Lab_Nova_myFilterFunc(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_i);
void example_Nova_Lab_Nova_myforeach(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_i, int example_Nova_Lab_Nova_index, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova_list);
double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* example_Nova_Lab_Nova_p);
void example_Nova_LabNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Lab* example_Nova_Lab_2_Nova_construct(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_Lab, this,);
	this->vtable = &example_Extension_VTable_Lab_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_Lab_Nova_super(this, exceptionData);
	
	{
		example_Nova_Lab_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_Lab_Nova_destroy(example_Nova_Lab** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

char example_Nova_Lab_Nova_myFilterFunc(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_i)
{
	return (char)example_Nova_Lab_Nova_i->nova_standard_Nova_String_Nova_size >= 4;
}

void example_Nova_Lab_Nova_myforeach(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* example_Nova_Lab_Nova_i, int example_Nova_Lab_Nova_index, nova_standard_datastruct_list_Nova_Array* example_Nova_Lab_Nova_list)
{
	if (example_Nova_Lab_Nova_index > 0)
	{
		nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "));
	}
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, example_Nova_Lab_Nova_i);
}

void example_Nova_Lab_Nova_main(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_Lab_Nova_args)
{
	nova_standard_datastruct_Nova_BinaryTree* l1_Nova_tree;
	nova_standard_Nova_String** l1_Nova_a;
	int* l1_Nova_b;
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list2;
	nova_standard_datastruct_list_Nova_Array* l1_Nova_list3;
	nova_standard_datastruct_list_Nova_Iterator* l1_Nova_iter;
	nova_standard_datastruct_list_Nova_ArrayIterator* nova_local_0;
	nova_standard_Nova_String* l5_Nova_string;
	int l2_Nova_n;
	
	l1_Nova_tree = nova_standard_datastruct_Nova_BinaryTree_2_Nova_construct(0, exceptionData);
	nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(l1_Nova_tree, exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "F"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "B"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "A"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "D"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "C"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "E"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "G"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "I"))), exceptionData, (nova_standard_Nova_Object*)(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "H")));
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Preorder: "), exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_join(nova_standard_datastruct_Nova_Tree_Nova_preorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData), exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Inorder: "), exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_join(nova_standard_datastruct_Nova_Tree_Nova_inorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData), exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Postorder: "), exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_join(nova_standard_datastruct_Nova_Tree_Nova_postorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData), exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "))));
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Levelorder: "), exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_join(nova_standard_datastruct_Nova_Tree_Nova_levelorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData), exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "))));
	l1_Nova_a = (nova_standard_Nova_String**)NOVA_MALLOC(sizeof(nova_standard_Nova_String) * 7);
	l1_Nova_a[0] = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "this");
	l1_Nova_a[1] = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "is");
	l1_Nova_a[2] = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "a");
	l1_Nova_a[3] = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "test");
	l1_Nova_a[4] = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "to");
	l1_Nova_a[5] = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "see");
	l1_Nova_a[6] = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "what shows up");
	l1_Nova_b = (int*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Int) * 7);
	l1_Nova_b[0] = 1;
	l1_Nova_b[1] = 2;
	l1_Nova_b[2] = 3;
	l1_Nova_b[3] = 4;
	l1_Nova_b[4] = 5;
	l1_Nova_b[5] = 6;
	l1_Nova_b[6] = 7;
	l1_Nova_list = nova_standard_datastruct_list_Nova_Array_4_Nova_construct(0, exceptionData, (nova_standard_Nova_Object**)(l1_Nova_a), 7);
	l1_Nova_list2 = l1_Nova_list->vtable->nova_standard_datastruct_list_Nova_Array_virtual_Nova_filter(l1_Nova_list, exceptionData, (nova_standard_datastruct_list_Nova_Array_closure12_Nova_filterFunc)&example_Nova_Lab_Nova_myFilterFunc, (example_Nova_Lab*)nova_null);
	l1_Nova_list3 = nova_standard_datastruct_list_Nova_Array_4_Nova_construct(0, exceptionData, (nova_standard_Nova_Object**)&(l1_Nova_b), 7);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Long_Nova_construct(0, exceptionData, nova_standard_datastruct_list_Nova_Array_Nova_sumSize(l1_Nova_list2, exceptionData))));
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, l1_Nova_list2->nova_standard_datastruct_list_Nova_Array_Nova_stringsOnly);
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "With foreach:"));
	l1_Nova_list2->vtable->nova_standard_datastruct_list_Nova_Array_virtual_Nova_forEach(l1_Nova_list2, exceptionData, (nova_standard_datastruct_list_Nova_Array_closure3_Nova_func)&example_Nova_Lab_Nova_myforeach, (example_Nova_Lab*)nova_null);
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ""));
	l2_Nova_n = 0;
	for (; l2_Nova_n < l1_Nova_list2->nova_standard_datastruct_list_Nova_Array_Nova_size; l2_Nova_n++)
	{
		nova_standard_Nova_String* nova_local_1;
		nova_standard_Nova_String* nova_local_2;
		
		nova_local_1 = (nova_standard_Nova_String*)(nova_standard_datastruct_list_Nova_Array_Nova_get(l1_Nova_list2, exceptionData, l2_Nova_n));
		nova_local_2 = nova_local_1->vtable->nova_standard_Nova_String_virtual0_Nova_toString(nova_local_1, exceptionData);
		nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_local_2->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_2, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", ")));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ""));
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Using iterator:"));
	l1_Nova_iter = (nova_standard_datastruct_list_Nova_Iterator*)(nova_standard_datastruct_list_Nova_Array_Accessor_Nova_iterator(l1_Nova_list, exceptionData));
	while (l1_Nova_iter->vtable->itable.nova_standard_datastruct_list_Nova_Iterator_Accessor_Nova_hasNext(l1_Nova_iter, exceptionData))
	{
		if (l1_Nova_iter->nova_standard_datastruct_list_Nova_Iterator_Nova_position > 0)
		{
			nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "));
		}
		nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, (nova_standard_Nova_String*)(l1_Nova_iter->vtable->itable.nova_standard_datastruct_list_Nova_Iterator_Accessor_Nova_next(l1_Nova_iter, exceptionData)));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ""));
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "With foreach loop"));
	nova_local_0 = nova_standard_datastruct_list_Nova_Array_Accessor_Nova_iterator(l1_Nova_list, exceptionData);
	while (nova_local_0->vtable->nova_standard_datastruct_list_Nova_ArrayIterator_Accessor_Nova_hasNext(nova_local_0, exceptionData))
	{
		l5_Nova_string = (nova_standard_Nova_String*)(nova_local_0->vtable->nova_standard_datastruct_list_Nova_ArrayIterator_Accessor_Nova_next(nova_local_0, exceptionData));
		nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, l5_Nova_string->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l5_Nova_string, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", ")));
	}
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* example_Nova_Lab_Nova_p)
{
	return example_Nova_Lab_Nova_p->vtable->itable.example_Nova_Polygon_virtual0_Nova_calculateArea(example_Nova_Lab_Nova_p, exceptionData);
}

void example_Nova_Lab_2_Nova_this(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_Lab_Nova_super(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

