#pragma once
#ifndef FILE_compiler_tree_node_exceptionhandling_Nova_Try_NOVA
#define FILE_compiler_tree_node_exceptionhandling_Nova_Try_NOVA

typedef struct compiler_tree_node_exceptionhandling_Nova_Try compiler_tree_node_exceptionhandling_Nova_Try;


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


typedef struct compiler_tree_node_exceptionhandling_Extension_VTable_Try compiler_tree_node_exceptionhandling_Extension_VTable_Try;
struct compiler_tree_node_exceptionhandling_Extension_VTable_Try
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
};

extern compiler_tree_node_exceptionhandling_Extension_VTable_Try compiler_tree_node_exceptionhandling_Extension_VTable_Try_val;


CCLASS_CLASS
(
	compiler_tree_node_exceptionhandling_Nova_Try, 
	
	compiler_tree_node_exceptionhandling_Extension_VTable_Try* vtable;
	struct Private* prv;
)
extern nova_Nova_String* compiler_tree_node_exceptionhandling_Nova_Try_Nova_IDENTIFIER;

void compiler_tree_node_exceptionhandling_Nova_Try_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
compiler_tree_node_exceptionhandling_Nova_Try* compiler_tree_node_exceptionhandling_Nova_Try_Nova_construct(compiler_tree_node_exceptionhandling_Nova_Try* this, nova_exception_Nova_ExceptionData* exceptionData);
void compiler_tree_node_exceptionhandling_Nova_Try_Nova_destroy(compiler_tree_node_exceptionhandling_Nova_Try** this, nova_exception_Nova_ExceptionData* exceptionData);
void compiler_tree_node_exceptionhandling_Nova_Try_0_Nova_this(compiler_tree_node_exceptionhandling_Nova_Try* this, nova_exception_Nova_ExceptionData* exceptionData);
void compiler_tree_node_exceptionhandling_Nova_Try_Nova_super(compiler_tree_node_exceptionhandling_Nova_Try* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
