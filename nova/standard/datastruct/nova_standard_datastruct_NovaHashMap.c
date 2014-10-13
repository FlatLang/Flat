#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaHashMap.h>


nova_VTable_nova_standard_datastruct_NovaHashMap nova_VTable_nova_standard_datastruct_NovaHashMap_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	hashmap* nova_standard_datastruct_NovaHashMap_Novamap;
	
)
void nova_standard_datastruct_NovaHashMapNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_NovaHashMap* nova_standard_datastruct_NovaHashMap_Nova0_construct(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_NovaHashMap, this);
	this->vtable = &nova_VTable_nova_standard_datastruct_NovaHashMap_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_datastruct_NovaHashMap_Novasuper(this, 0);
	
	{
		nova_standard_datastruct_NovaHashMap_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_HashMap(nova_standard_datastruct_NovaHashMap** this, nova_standard_exception_NovaExceptionData* exceptionData)
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

void nova_standard_datastruct_NovaHashMap_Novathis(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_NovaHashMap_Novamap = hashmapCreate((int)(10));
}

void nova_standard_datastruct_NovaHashMap_Novaput(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novakey, nova_standard_NovaObject* l0_Novavalue)
{
	nova_standard_NovaString* nova_local_0;
	
	nova_local_0 = l0_Novakey->vtable->nova_standard_NovaObject_Novavirtual0_toString((nova_standard_NovaObject*)(l0_Novakey), exceptionData);
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "HashCode for("), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "): "), exceptionData, nova_standard_primitive_number_NovaLong_static_Nova1_toString(0, exceptionData, l0_Novakey->vtable->nova_standard_NovaObject_Novavirtual0_getHashCodeLong((nova_standard_NovaObject*)(l0_Novakey), exceptionData))))));
	hashmapInsert(this->prv->nova_standard_datastruct_NovaHashMap_Novamap, l0_Novavalue, (long_long)(l0_Novakey->vtable->nova_standard_NovaObject_Novavirtual0_getHashCodeLong((nova_standard_NovaObject*)(l0_Novakey), exceptionData)));
}

nova_standard_NovaObject* nova_standard_datastruct_NovaHashMap_Novaget(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novakey)
{
	nova_standard_NovaString* nova_local_0;
	
	nova_local_0 = l0_Novakey->vtable->nova_standard_NovaObject_Novavirtual0_toString((nova_standard_NovaObject*)(l0_Novakey), exceptionData);
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "HashCode gfor("), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "): "), exceptionData, nova_standard_primitive_number_NovaLong_static_Nova1_toString(0, exceptionData, l0_Novakey->vtable->nova_standard_NovaObject_Novavirtual0_getHashCodeLong((nova_standard_NovaObject*)(l0_Novakey), exceptionData))))));
	return ((nova_standard_NovaObject*)hashmapGet(this->prv->nova_standard_datastruct_NovaHashMap_Novamap, (long_long)(l0_Novakey->vtable->nova_standard_NovaObject_Novavirtual0_getHashCodeLong((nova_standard_NovaObject*)(l0_Novakey), exceptionData))));
}

void nova_standard_datastruct_NovaHashMap_Novasuper(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_NovaHashMap_Novamap = (hashmap*)nova_null;
}
