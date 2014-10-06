#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaHashMap.h>


nova_VTable_nova_standard_datastruct_NovaHashMap nova_VTable_nova_standard_datastruct_NovaHashMap_val =
{
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
	hashmapInsert(this->prv->nova_standard_datastruct_NovaHashMap_Novamap, l0_Novavalue, (long_long)(nova_standard_NovaObject_NovagetHashCodeLong((nova_standard_NovaObject*)(l0_Novakey), exceptionData)));
}

nova_standard_NovaObject* nova_standard_datastruct_NovaHashMap_Novaget(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novakey)
{
	return ((nova_standard_NovaObject*)hashmapGet(this->prv->nova_standard_datastruct_NovaHashMap_Novamap, (long_long)(nova_standard_NovaObject_NovagetHashCodeLong((nova_standard_NovaObject*)(l0_Novakey), exceptionData))));
}

void nova_standard_datastruct_NovaHashMap_Novasuper(nova_standard_datastruct_NovaHashMap* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_datastruct_NovaHashMap_Novamap = (hashmap*)nova_null;
}
