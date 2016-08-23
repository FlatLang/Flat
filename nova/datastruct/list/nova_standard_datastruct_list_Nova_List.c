#include <precompiled.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_List.h>



nova_datastruct_list_Extension_VTable_List nova_datastruct_list_Extension_VTable_List_val =
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
		0,
		0,
		0,
		0,
	},
	nova_datastruct_list_Nova_List_virtual0_Nova_toArray,
	nova_datastruct_list_Nova_List_virtual0_Nova_contains,
	nova_datastruct_list_Nova_List_virtual0_Nova_forEach,
	nova_datastruct_list_Nova_List_virtual0_Nova_map,
	nova_datastruct_list_Nova_List_virtual0_Nova_any,
	nova_datastruct_list_Nova_List_virtual0_Nova_all,
	nova_datastruct_list_Nova_List_virtual0_Nova_filter,
	nova_datastruct_list_Nova_List_virtual0_Nova_take,
	nova_datastruct_list_Nova_List_virtual0_Nova_skip,
	nova_datastruct_list_Nova_List_virtual0_Nova_firstWhere,
	nova_datastruct_list_Nova_List_virtual0_Nova_reverse,
	nova_datastruct_list_Nova_List_virtual0_Nova_join,
};


CCLASS_PRIVATE
(
	nova_Nova_Object* nova_datastruct_list_Nova_List_Nova_first;
	nova_Nova_Object* nova_datastruct_list_Nova_List_Nova_last;
	
)
void nova_datastruct_list_Nova_List_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}













nova_datastruct_list_Nova_Array* nova_datastruct_list_Nova_List_virtual0_Nova_toArray(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_toArray((nova_datastruct_list_Nova_List*)(this), exceptionData);
}

char nova_datastruct_list_Nova_List_virtual0_Nova_contains(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_list_Nova_List_Nova_t)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_contains((nova_datastruct_list_Nova_List*)(this), exceptionData, nova_datastruct_list_Nova_List_Nova_t);
}

void nova_datastruct_list_Nova_List_virtual0_Nova_forEach(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_List_closure3_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context)
{
	this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_forEach((nova_datastruct_list_Nova_List*)(this), exceptionData, nova_datastruct_list_Nova_List_Nova_func, nova_datastruct_list_Nova_List_ref_Nova_func, func_context);
}

nova_datastruct_list_Nova_List* nova_datastruct_list_Nova_List_virtual0_Nova_map(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_List_closure6_Nova_mapFunc nova_datastruct_list_Nova_List_Nova_mapFunc, void* nova_datastruct_list_Nova_List_ref_Nova_mapFunc, void* mapFunc_context)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_map((nova_datastruct_list_Nova_List*)(this), exceptionData, nova_datastruct_list_Nova_List_Nova_mapFunc, nova_datastruct_list_Nova_List_ref_Nova_mapFunc, mapFunc_context);
}

char nova_datastruct_list_Nova_List_virtual0_Nova_any(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_List_closure9_Nova_anyFunc nova_datastruct_list_Nova_List_Nova_anyFunc, void* nova_datastruct_list_Nova_List_ref_Nova_anyFunc, void* anyFunc_context)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_any((nova_datastruct_list_Nova_List*)(this), exceptionData, nova_datastruct_list_Nova_List_Nova_anyFunc, nova_datastruct_list_Nova_List_ref_Nova_anyFunc, anyFunc_context);
}

char nova_datastruct_list_Nova_List_virtual0_Nova_all(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_List_closure12_Nova_allFunc nova_datastruct_list_Nova_List_Nova_allFunc, void* nova_datastruct_list_Nova_List_ref_Nova_allFunc, void* allFunc_context)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_all((nova_datastruct_list_Nova_List*)(this), exceptionData, nova_datastruct_list_Nova_List_Nova_allFunc, nova_datastruct_list_Nova_List_ref_Nova_allFunc, allFunc_context);
}

nova_datastruct_list_Nova_List* nova_datastruct_list_Nova_List_virtual0_Nova_filter(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_List_closure15_Nova_filterFunc nova_datastruct_list_Nova_List_Nova_filterFunc, void* nova_datastruct_list_Nova_List_ref_Nova_filterFunc, void* filterFunc_context)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_filter((nova_datastruct_list_Nova_List*)(this), exceptionData, nova_datastruct_list_Nova_List_Nova_filterFunc, nova_datastruct_list_Nova_List_ref_Nova_filterFunc, filterFunc_context);
}

nova_datastruct_list_Nova_List* nova_datastruct_list_Nova_List_virtual0_Nova_take(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_list_Nova_List_Nova_howMany)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_take((nova_datastruct_list_Nova_List*)(this), exceptionData, nova_datastruct_list_Nova_List_Nova_howMany);
}

nova_datastruct_list_Nova_List* nova_datastruct_list_Nova_List_virtual0_Nova_skip(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_list_Nova_List_Nova_howMany)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_skip((nova_datastruct_list_Nova_List*)(this), exceptionData, nova_datastruct_list_Nova_List_Nova_howMany);
}

nova_Nova_Object* nova_datastruct_list_Nova_List_virtual0_Nova_firstWhere(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_List_closure18_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_firstWhere((nova_datastruct_list_Nova_List*)(this), exceptionData, nova_datastruct_list_Nova_List_Nova_func, nova_datastruct_list_Nova_List_ref_Nova_func, func_context);
}

nova_datastruct_list_Nova_List* nova_datastruct_list_Nova_List_virtual0_Nova_reverse(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_reverse((nova_datastruct_list_Nova_List*)(this), exceptionData);
}

nova_Nova_String* nova_datastruct_list_Nova_List_virtual0_Nova_join(nova_datastruct_list_Nova_List* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_datastruct_list_Nova_List_Nova_delimiter)
{
	return this->vtable->itable.nova_datastruct_list_Nova_List_virtual0_Nova_join((nova_datastruct_list_Nova_List*)(this), exceptionData, nova_datastruct_list_Nova_List_Nova_delimiter);
}

