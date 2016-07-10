#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_ReversibleHashMap.h>

nova_standard_datastruct_Extension_VTable_ReversibleHashMap nova_standard_datastruct_Extension_VTable_ReversibleHashMap_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_datastruct_Nova_ReversibleHashMap_Nova_put,
};


CCLASS_PRIVATE
(
	hashmap* nova_standard_datastruct_Nova_HashMap_Nova_map;
	
	nova_standard_datastruct_Nova_HashMap* nova_standard_datastruct_Nova_ReversibleHashMap_Nova_rev;
	
)
void nova_standard_datastruct_Nova_ReversibleHashMapNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_ReversibleHashMap* nova_standard_datastruct_Nova_ReversibleHashMap_Nova_ReversibleHashMap(nova_standard_datastruct_Nova_ReversibleHashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_ReversibleHashMap, this);
	this->vtable = &nova_standard_datastruct_Extension_VTable_ReversibleHashMap_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_HashMap_Nova_super((nova_standard_datastruct_Nova_HashMap*)this, exceptionData);
	nova_standard_datastruct_Nova_ReversibleHashMap_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_ReversibleHashMap_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_ReversibleHashMap_Nova_destroy(nova_standard_datastruct_Nova_ReversibleHashMap** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_Nova_HashMap_Nova_destroy(&(*this)->prv->nova_standard_datastruct_Nova_ReversibleHashMap_Nova_rev, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_ReversibleHashMap_0_Nova_this(nova_standard_datastruct_Nova_ReversibleHashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_Nova_ReversibleHashMap_Nova_rev = nova_standard_datastruct_Nova_HashMap_Nova_HashMap(0, exceptionData);
}

void nova_standard_datastruct_Nova_ReversibleHashMap_Nova_put(nova_standard_datastruct_Nova_ReversibleHashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_ReversibleHashMap_Nova_key, nova_standard_Nova_Object* nova_standard_datastruct_Nova_ReversibleHashMap_Nova_value)
{
}

nova_standard_Nova_Object* nova_standard_datastruct_Nova_ReversibleHashMap_Nova_getKey(nova_standard_datastruct_Nova_ReversibleHashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_ReversibleHashMap_Nova_value)
{
	return (nova_standard_Nova_Object*)nova_standard_datastruct_Nova_HashMap_Nova_get(this->prv->nova_standard_datastruct_Nova_ReversibleHashMap_Nova_rev, exceptionData, (nova_standard_Nova_Object*)(nova_standard_datastruct_Nova_ReversibleHashMap_Nova_value));
}

nova_standard_Nova_Object* nova_standard_datastruct_Nova_ReversibleHashMap_Nova_getValue(nova_standard_datastruct_Nova_ReversibleHashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_ReversibleHashMap_Nova_key)
{
	return nova_standard_datastruct_Nova_HashMap_Nova_get((nova_standard_datastruct_Nova_HashMap*)(this), exceptionData, nova_standard_datastruct_Nova_ReversibleHashMap_Nova_key);
}

void nova_standard_datastruct_Nova_ReversibleHashMap_0_Nova_super(nova_standard_datastruct_Nova_ReversibleHashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_Nova_ReversibleHashMap_Nova_rev = (nova_standard_datastruct_Nova_HashMap*)nova_null;
}

