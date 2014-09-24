#include <precompiled.h>
#include "NovaHashMap.h"


nova_VTable_HashMap nova_VTable_HashMap_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};
CCLASS_PRIVATE
(
	hashmap* nova_HashMap_map;
	
)

HashMap* nova_0_HashMap_construct(HashMap* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(HashMap, this);
	this->vtable = &nova_VTable_HashMap_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_HashMap_super(this, 0);
	
	{
		nova_HashMap_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_HashMap(HashMap** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_HashMap_this(HashMap* this, ExceptionData* exceptionData)
{
	this->prv->nova_HashMap_map = hashmapCreate(10);
}

void nova_HashMap_put(HashMap* this, ExceptionData* exceptionData, Object* nova_0_key, Object* nova_0_value)
{
	String* nova_local_0;
	
	nova_local_0 = nova_0_value->vtable->nova_virtual_0_toString(nova_0_value, exceptionData);
	nova_static_0_Console_writeLine(0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, "Adding "), exceptionData, nova_local_0->vtable->nova_virtual_0_concat(nova_local_0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, " at "), exceptionData, nova_3_Long_toString(nova_Long_construct(0, exceptionData, nova_Object_getHashCodeLong(nova_0_key, exceptionData)), exceptionData)))));
	hashmapInsert(this->prv->nova_HashMap_map, nova_0_value, nova_Object_getHashCodeLong(nova_0_key, exceptionData));
}

Object* nova_HashMap_get(HashMap* this, ExceptionData* exceptionData, Object* nova_0_key)
{
	return (Object*)hashmapGet(this->prv->nova_HashMap_map, nova_Object_getHashCodeLong(nova_0_key, exceptionData));
}

void nova_HashMap_super(HashMap* this, ExceptionData* exceptionData)
{
	this->prv->nova_HashMap_map = (hashmap*)nova_null;
}
