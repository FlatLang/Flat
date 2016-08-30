#pragma once
#ifndef FILE_compiler_tree_node_Nova_Scope_NOVA
#define FILE_compiler_tree_node_Nova_Scope_NOVA

typedef struct compiler_tree_node_Nova_Scope compiler_tree_node_Nova_Scope;


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
#include <compiler/util/compiler_util_Nova_Location.h>
#include <compiler/tree/node/compiler_tree_node_Nova_Listener.h>
#include <compiler/tree/node/compiler_tree_node_Nova_Node.h>


typedef struct compiler_tree_node_Extension_VTable_Scope compiler_tree_node_Extension_VTable_Scope;
struct compiler_tree_node_Extension_VTable_Scope
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	void (*compiler_tree_node_annotation_Nova_Annotatable_virtual0_Nova_addAnnotation)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_annotation_Nova_Annotation*);
	void (*compiler_tree_node_Nova_Listenable_virtual1_Nova_onAdded)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*);
	compiler_tree_node_Nova_Scope* (*compiler_tree_node_Nova_Node_virtual1_Nova_clone)(compiler_tree_node_Nova_Scope*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*, char);
};

extern compiler_tree_node_Extension_VTable_Scope compiler_tree_node_Extension_VTable_Scope_val;


CCLASS_CLASS
(
	compiler_tree_node_Nova_Scope, 
	
	compiler_tree_node_Extension_VTable_Scope* vtable;
	struct Private* prv;
)

void compiler_tree_node_Nova_Scope_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
compiler_tree_node_Nova_Scope* compiler_tree_node_Nova_Scope_Nova_construct(compiler_tree_node_Nova_Scope* this, nova_exception_Nova_ExceptionData* exceptionData);
void compiler_tree_node_Nova_Scope_Nova_destroy(compiler_tree_node_Nova_Scope** this, nova_exception_Nova_ExceptionData* exceptionData);
compiler_tree_node_Nova_Scope* compiler_tree_node_Nova_Scope_1_Nova_clone(compiler_tree_node_Nova_Scope* this, nova_exception_Nova_ExceptionData* exceptionData, compiler_tree_node_Nova_Node* compiler_tree_node_Nova_Scope_Nova_temporaryParent, compiler_util_Nova_Location* compiler_tree_node_Nova_Scope_Nova_locationIn, char compiler_tree_node_Nova_Scope_Nova_cloneChildren);
void compiler_tree_node_Nova_Scope_0_Nova_this(compiler_tree_node_Nova_Scope* this, nova_exception_Nova_ExceptionData* exceptionData);
void compiler_tree_node_Nova_Scope_0_Nova_super(compiler_tree_node_Nova_Scope* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
