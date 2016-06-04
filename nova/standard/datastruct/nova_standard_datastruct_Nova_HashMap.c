#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_HashMap.h>

nova_standard_datastruct_Extension_VTable_HashMap nova_standard_datastruct_Extension_VTable_HashMap_val =
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	hashmap* nova_standard_datastruct_Nova_HashMap_Nova_map;
	
)


void nova_standard_datastruct_Nova_HashMapNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_HashMap* nova_standard_datastruct_Nova_HashMap_2_Nova_construct(nova_standard_datastruct_Nova_HashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_HashMap, this);
	this->vtable = &nova_standard_datastruct_Extension_VTable_HashMap_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_HashMap_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_HashMap_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_HashMap_Nova_destroy(nova_standard_datastruct_Nova_HashMap** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_HashMap_2_Nova_this(nova_standard_datastruct_Nova_HashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_Nova_HashMap_Nova_map = hashmapCreate((int)(10));
}

void nova_standard_datastruct_Nova_HashMap_Nova_put(nova_standard_datastruct_Nova_HashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_HashMap_Nova_key, nova_standard_Nova_Object* nova_standard_datastruct_Nova_HashMap_Nova_value)
{
	if (nova_standard_datastruct_Nova_HashMap_Nova_containsKey(this, exceptionData, nova_standard_datastruct_Nova_HashMap_Nova_key))
	{
		return;
	}
	hashmapInsert(this->prv->nova_standard_datastruct_Nova_HashMap_Nova_map, nova_standard_datastruct_Nova_HashMap_Nova_value, (long_long)(((nova_standard_Nova_Object*)nova_standard_datastruct_Nova_HashMap_Nova_key)->vtable->nova_standard_Nova_Object_virtual0_Nova_getHashCodeLong(nova_standard_datastruct_Nova_HashMap_Nova_key, exceptionData)));
}

nova_standard_Nova_Object* nova_standard_datastruct_Nova_HashMap_Nova_get(nova_standard_datastruct_Nova_HashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_HashMap_Nova_key)
{
	return hashmapGet(this->prv->nova_standard_datastruct_Nova_HashMap_Nova_map, (long_long)(((nova_standard_Nova_Object*)nova_standard_datastruct_Nova_HashMap_Nova_key)->vtable->nova_standard_Nova_Object_virtual0_Nova_getHashCodeLong(nova_standard_datastruct_Nova_HashMap_Nova_key, exceptionData)));
}

nova_standard_Nova_Object* nova_standard_datastruct_Nova_HashMap_Nova_remove(nova_standard_datastruct_Nova_HashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_HashMap_Nova_key)
{
	nova_standard_Nova_Object* l1_Nova_val;
	
	if (!nova_standard_datastruct_Nova_HashMap_Nova_containsKey(this, exceptionData, nova_standard_datastruct_Nova_HashMap_Nova_key))
	{
		return (nova_standard_Nova_Object*)nova_null;
	}
	l1_Nova_val = nova_standard_datastruct_Nova_HashMap_Nova_get(this, exceptionData, nova_standard_datastruct_Nova_HashMap_Nova_key);
	hashmapRemove(this->prv->nova_standard_datastruct_Nova_HashMap_Nova_map, (long_long)(((nova_standard_Nova_Object*)nova_standard_datastruct_Nova_HashMap_Nova_key)->vtable->nova_standard_Nova_Object_virtual0_Nova_getHashCodeLong(nova_standard_datastruct_Nova_HashMap_Nova_key, exceptionData)));
	return l1_Nova_val;
}

char nova_standard_datastruct_Nova_HashMap_Nova_containsKey(nova_standard_datastruct_Nova_HashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_datastruct_Nova_HashMap_Nova_key)
{
	return hashmapContains(this->prv->nova_standard_datastruct_Nova_HashMap_Nova_map, (long_long)(((nova_standard_Nova_Object*)nova_standard_datastruct_Nova_HashMap_Nova_key)->vtable->nova_standard_Nova_Object_virtual0_Nova_getHashCodeLong(nova_standard_datastruct_Nova_HashMap_Nova_key, exceptionData)));
}

int nova_standard_datastruct_Nova_HashMap_Accessor_Nova_size(nova_standard_datastruct_Nova_HashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (int)hashmapCount(this->prv->nova_standard_datastruct_Nova_HashMap_Nova_map);
}


void nova_standard_datastruct_Nova_HashMap_Nova_super(nova_standard_datastruct_Nova_HashMap* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_Nova_HashMap_Nova_map = 0;
}

