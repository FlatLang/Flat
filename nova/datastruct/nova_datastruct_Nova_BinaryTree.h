#pragma once
#ifndef FILE_nova_datastruct_Nova_BinaryTree_NOVA
#define FILE_nova_datastruct_Nova_BinaryTree_NOVA

typedef struct nova_datastruct_Nova_BinaryTree nova_datastruct_Nova_BinaryTree;


#include <Nova.h>
#include <ExceptionHandler.h>
#include <InterfaceVTable.h>
#include <nova/exception/nova_exception_Nova_ExceptionData.h>
#include <nova/exception/nova_exception_Nova_Exception.h>
#include <nova/exception/nova_exception_Nova_DivideByZeroException.h>
#include <nova/io/nova_io_Nova_Console.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Number.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Byte.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Short.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Int.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Long.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Float.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Double.h>
#include <nova/primitive/nova_primitive_Nova_Null.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Char.h>
#include <nova/primitive/nova_primitive_Nova_Bool.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Array.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_CharArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_DoubleArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntRange.h>
#include <nova/thread/nova_thread_Nova_Thread.h>
#include <nova/thread/async/nova_thread_async_Nova_Async.h>
#include <nova/gc/nova_gc_Nova_GC.h>
#include <nova/math/nova_math_Nova_Math.h>
#include <nova/nova_Nova_Object.h>
#include <nova/nova_Nova_String.h>
#include <nova/nova_Nova_System.h>
#include <nova/nova_Nova_Class.h>
#include <nova/datastruct/nova_datastruct_Nova_BinaryNode.h>
#include <nova/datastruct/nova_datastruct_Nova_Comparable.h>
#include <nova/datastruct/nova_datastruct_Nova_Tree.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_ArrayIterator.h>


typedef struct nova_datastruct_Extension_VTable_BinaryTree nova_datastruct_Extension_VTable_BinaryTree;
struct nova_datastruct_Extension_VTable_BinaryTree
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
};

extern nova_datastruct_Extension_VTable_BinaryTree nova_datastruct_Extension_VTable_BinaryTree_val;


CCLASS_CLASS
(
	nova_datastruct_Nova_BinaryTree, 
	
	nova_datastruct_Extension_VTable_BinaryTree* vtable;
	nova_datastruct_Nova_Node* nova_datastruct_Nova_Tree_Nova_root;
)

void nova_datastruct_Nova_BinaryTree_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_Nova_BinaryTree* nova_datastruct_Nova_BinaryTree_Nova_construct(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_BinaryTree_Nova_data);
void nova_datastruct_Nova_BinaryTree_Nova_destroy(nova_datastruct_Nova_BinaryTree** this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_datastruct_Nova_BinaryTree_1_Nova_this(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_BinaryTree_Nova_data);
nova_datastruct_Nova_BinaryTree* nova_datastruct_Nova_BinaryTree_Nova_addNode(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Comparable* nova_datastruct_Nova_BinaryTree_Nova_data);
nova_datastruct_Nova_BinaryTree* nova_datastruct_Nova_BinaryTree_Nova_addNodes(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_datastruct_Nova_BinaryTree_Nova_data);
nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryTree_Accessor_Nova_root(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryTree_Mutator_Nova_root(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_BinaryNode* nova_datastruct_Nova_BinaryTree_Nova_node);
void nova_datastruct_Nova_BinaryTree_0_Nova_super(nova_datastruct_Nova_BinaryTree* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
