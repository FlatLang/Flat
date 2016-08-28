#include <precompiled.h>
#include <nova/datastruct/nova_datastruct_Nova_HashMap.h>



nova_datastruct_Extension_VTable_HashMap nova_datastruct_Extension_VTable_HashMap_val =
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
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_getHashCodeLong,
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_datastruct_Nova_HashMap_0_Nova_put,
};


CCLASS_PRIVATE
(
	hashmap* nova_datastruct_Nova_HashMap_Nova_map;
	
)


void nova_datastruct_Nova_HashMap_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_Nova_HashMap(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_datastruct_Nova_HashMap, this);
	this->vtable = &nova_datastruct_Extension_VTable_HashMap_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_HashMap_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_HashMap_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_datastruct_Nova_HashMap_Nova_destroy(nova_datastruct_Nova_HashMap** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_datastruct_Nova_HashMap_0_Nova_this(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_datastruct_Nova_HashMap_Nova_map = hashmapCreate(10);
}

void nova_datastruct_Nova_HashMap_0_Nova_put(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_value)
{
	if (nova_datastruct_Nova_HashMap_Nova_containsKey(this, exceptionData, nova_datastruct_Nova_HashMap_Nova_key))
	{
		return;
	}
	hashmapInsert(this->prv->nova_datastruct_Nova_HashMap_Nova_map, nova_datastruct_Nova_HashMap_Nova_value, (long_long)((long_long)(nova_Nova_Object_virtual1_Nova_getHashCodeLong((nova_Nova_Object*)(nova_datastruct_Nova_HashMap_Nova_key), exceptionData))));
}

nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_get(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key)
{
	return hashmapGet(this->prv->nova_datastruct_Nova_HashMap_Nova_map, (long_long)((long_long)(nova_Nova_Object_virtual1_Nova_getHashCodeLong((nova_Nova_Object*)(nova_datastruct_Nova_HashMap_Nova_key), exceptionData))));
}

nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_remove(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key)
{
	nova_Nova_Object* l1_Nova_val = (nova_Nova_Object*)nova_null;
	
	if (!nova_datastruct_Nova_HashMap_Nova_containsKey(this, exceptionData, nova_datastruct_Nova_HashMap_Nova_key))
	{
		return (nova_Nova_Object*)nova_null;
	}
	l1_Nova_val = nova_datastruct_Nova_HashMap_Nova_get(this, exceptionData, nova_datastruct_Nova_HashMap_Nova_key);
	hashmapRemove(this->prv->nova_datastruct_Nova_HashMap_Nova_map, (long_long)((long_long)(nova_Nova_Object_virtual1_Nova_getHashCodeLong((nova_Nova_Object*)(nova_datastruct_Nova_HashMap_Nova_key), exceptionData))));
	return l1_Nova_val;
}

char nova_datastruct_Nova_HashMap_Nova_containsKey(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key)
{
	return hashmapContains(this->prv->nova_datastruct_Nova_HashMap_Nova_map, (long_long)((long_long)(nova_Nova_Object_virtual1_Nova_getHashCodeLong((nova_Nova_Object*)(nova_datastruct_Nova_HashMap_Nova_key), exceptionData))));
}

int nova_datastruct_Nova_HashMap_Accessor_Nova_size(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (int)hashmapCount(this->prv->nova_datastruct_Nova_HashMap_Nova_map);
}


void nova_datastruct_Nova_HashMap_Nova_super(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_datastruct_Nova_HashMap_Nova_map = 0;
}

void nova_datastruct_Nova_HashMap_virtual1_Nova_put(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_value)
{
	this->vtable->nova_datastruct_Nova_HashMap_virtual1_Nova_put((nova_datastruct_Nova_HashMap*)(this), exceptionData, nova_datastruct_Nova_HashMap_Nova_key, nova_datastruct_Nova_HashMap_Nova_value);
}

