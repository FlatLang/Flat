#pragma once
#ifndef FILE_compiler_tree_node_annotation_Nova_Annotatable_NOVA
#define FILE_compiler_tree_node_annotation_Nova_Annotatable_NOVA

typedef struct compiler_tree_node_annotation_Nova_Annotatable compiler_tree_node_annotation_Nova_Annotatable;


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
#include <compiler/tree/node/annotation/compiler_tree_node_annotation_Nova_Annotation.h>


typedef struct compiler_tree_node_annotation_Extension_VTable_Annotatable compiler_tree_node_annotation_Extension_VTable_Annotatable;
struct compiler_tree_node_annotation_Extension_VTable_Annotatable
{
	nova_Interface_VTable itable;
	void (*compiler_tree_node_annotation_Nova_Annotatable_virtual0_Nova_addAnnotation)(compiler_tree_node_annotation_Nova_Annotatable*, nova_exception_Nova_ExceptionData*, compiler_tree_node_annotation_Nova_Annotation*);
};

extern compiler_tree_node_annotation_Extension_VTable_Annotatable compiler_tree_node_annotation_Extension_VTable_Annotatable_val;


CCLASS_CLASS
(
	compiler_tree_node_annotation_Nova_Annotatable, 
	
	compiler_tree_node_annotation_Extension_VTable_Annotatable* vtable;
	nova_datastruct_list_Nova_Array* compiler_tree_node_annotation_Nova_Annotatable_Nova_annotations;
)

void compiler_tree_node_annotation_Nova_Annotatable_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
void compiler_tree_node_annotation_Nova_Annotatable_0_Nova_addAnnotation(compiler_tree_node_annotation_Nova_Annotatable* this, nova_exception_Nova_ExceptionData* exceptionData, compiler_tree_node_annotation_Nova_Annotation* compiler_tree_node_annotation_Nova_Annotatable_Nova_annotation);void compiler_tree_node_annotation_Nova_Annotatable_virtual0_Nova_addAnnotation(compiler_tree_node_annotation_Nova_Annotatable* this, nova_exception_Nova_ExceptionData* exceptionData, compiler_tree_node_annotation_Nova_Annotation* compiler_tree_node_annotation_Nova_Annotatable_Nova_annotation);

#endif
