#include <precompiled.h>
#include <nova/web/js/json/nova_web_js_json_Nova_Json.h>



nova_web_js_json_Extension_VTable_Json nova_web_js_json_Extension_VTable_Json_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		(nova_datastruct_list_Nova_Array*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*))nova_datastruct_Nova_HashMap_Nova_toArray,
		(char(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_datastruct_Nova_HashMap_Nova_contains,
		(void(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure3_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context))nova_datastruct_Nova_HashMap_Nova_forEach,
		(nova_datastruct_list_Nova_List*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure6_Nova_mapFunc nova_datastruct_list_Nova_List_Nova_mapFunc, void* nova_datastruct_list_Nova_List_ref_Nova_mapFunc, void* mapFunc_context))nova_datastruct_Nova_HashMap_Nova_map,
		(char(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure9_Nova_anyFunc nova_datastruct_list_Nova_List_Nova_anyFunc, void* nova_datastruct_list_Nova_List_ref_Nova_anyFunc, void* anyFunc_context))nova_datastruct_Nova_HashMap_Nova_any,
		(char(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure12_Nova_allFunc nova_datastruct_list_Nova_List_Nova_allFunc, void* nova_datastruct_list_Nova_List_ref_Nova_allFunc, void* allFunc_context))nova_datastruct_Nova_HashMap_Nova_all,
		(nova_datastruct_list_Nova_List*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure15_Nova_filterFunc nova_datastruct_list_Nova_List_Nova_filterFunc, void* nova_datastruct_list_Nova_List_ref_Nova_filterFunc, void* filterFunc_context))nova_datastruct_Nova_HashMap_Nova_filter,
		(nova_datastruct_list_Nova_List*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, int))nova_datastruct_Nova_HashMap_Nova_take,
		(nova_datastruct_list_Nova_List*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, int))nova_datastruct_Nova_HashMap_Nova_skip,
		(nova_Nova_Object*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure18_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context))nova_datastruct_Nova_HashMap_Nova_firstWhere,
		(nova_datastruct_list_Nova_List*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*))nova_datastruct_Nova_HashMap_Nova_reverse,
		(nova_Nova_String*(*)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_Nova_String*))nova_datastruct_Nova_HashMap_Nova_join,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_web_js_json_Nova_Json_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	nova_datastruct_Nova_HashMap_Nova_toArray,
	nova_datastruct_Nova_HashMap_Nova_contains,
	nova_datastruct_Nova_HashMap_Nova_any,
	nova_datastruct_Nova_HashMap_Nova_all,
	nova_datastruct_Nova_HashMap_Nova_map,
	nova_datastruct_Nova_HashMap_Nova_filter,
	nova_datastruct_Nova_HashMap_Nova_join,
	nova_datastruct_Nova_HashMap_Nova_skip,
	nova_datastruct_Nova_HashMap_Nova_take,
	nova_datastruct_Nova_HashMap_Nova_reverse,
	nova_datastruct_Nova_HashMap_Nova_firstWhere,
	nova_datastruct_Nova_HashMap_Nova_forEach,
	nova_datastruct_Nova_HashMap_0_Nova_put,
};


CCLASS_PRIVATE
(
	nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_buckets;
	int nova_datastruct_Nova_HashMap_Nova_bucketSize;
	
)
void nova_web_js_json_Nova_Json_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_web_js_json_Nova_Json* nova_web_js_json_Nova_Json_Nova_construct(nova_web_js_json_Nova_Json* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_web_js_json_Nova_Json, this);
	this->vtable = &nova_web_js_json_Extension_VTable_Json_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_HashMap_Nova_super((nova_datastruct_Nova_HashMap*)this, exceptionData);
	nova_web_js_json_Nova_Json_0_Nova_super(this, exceptionData);
	
	{
		nova_web_js_json_Nova_Json_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_web_js_json_Nova_Json_Nova_destroy(nova_web_js_json_Nova_Json** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

nova_Nova_String* nova_web_js_json_Nova_Json_0_Nova_toString(nova_web_js_json_Nova_Json* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("{}"));
}

void nova_web_js_json_Nova_Json_0_Nova_this(nova_web_js_json_Nova_Json* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_web_js_json_Nova_Json_0_Nova_super(nova_web_js_json_Nova_Json* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

