#pragma once
#ifndef FILE_nova_web_js_json_Nova_Json_NOVA
#define FILE_nova_web_js_json_Nova_Json_NOVA

typedef struct nova_web_js_json_Nova_Json nova_web_js_json_Nova_Json;


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
#include <nova/datastruct/nova_datastruct_Nova_HashMap.h>


typedef struct nova_web_js_json_Extension_VTable_Json nova_web_js_json_Extension_VTable_Json;
struct nova_web_js_json_Extension_VTable_Json
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_web_js_json_Nova_Json*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_virtual0_Nova_toArray)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*);
	char (*nova_datastruct_list_Nova_List_virtual0_Nova_contains)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*);
	char (*nova_datastruct_list_Nova_List_virtual0_Nova_any)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure3_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	char (*nova_datastruct_list_Nova_List_virtual0_Nova_all)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure6_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_virtual0_Nova_map)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure9_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_virtual0_Nova_filter)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure12_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	nova_Nova_String* (*nova_datastruct_list_Nova_List_virtual0_Nova_join)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
	nova_datastruct_Nova_Pair* (*nova_datastruct_list_Nova_List_virtual0_Nova_skip)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, int);
	nova_datastruct_Nova_Pair* (*nova_datastruct_list_Nova_List_virtual0_Nova_take)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, int);
	nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_virtual0_Nova_reverse)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*);
	nova_datastruct_Nova_Pair* (*nova_datastruct_list_Nova_List_virtual0_Nova_firstWhere)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure15_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	void (*nova_datastruct_list_Nova_List_virtual0_Nova_forEach)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure18_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	nova_datastruct_Nova_HashMap* (*nova_datastruct_Nova_HashMap_virtual1_Nova_put)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, nova_Nova_Object*);
};

extern nova_web_js_json_Extension_VTable_Json nova_web_js_json_Extension_VTable_Json_val;


CCLASS_CLASS
(
	nova_web_js_json_Nova_Json, 
	
	nova_web_js_json_Extension_VTable_Json* vtable;
	int nova_datastruct_Nova_HashMap_Nova_size;
	struct Private* prv;
)

void nova_web_js_json_Nova_Json_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_web_js_json_Nova_Json* nova_web_js_json_Nova_Json_Nova_construct(nova_web_js_json_Nova_Json* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_web_js_json_Nova_Json_Nova_destroy(nova_web_js_json_Nova_Json** this, nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_String* nova_web_js_json_Nova_Json_0_Nova_toString(nova_web_js_json_Nova_Json* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_web_js_json_Nova_Json_0_Nova_this(nova_web_js_json_Nova_Json* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_web_js_json_Nova_Json_0_Nova_super(nova_web_js_json_Nova_Json* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
