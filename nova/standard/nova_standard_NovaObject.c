#include <precompiled.h>
#include <nova/standard/nova_standard_NovaObject.h>


nova_VTable_nova_standard_NovaObject nova_VTable_nova_standard_NovaObject_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_NovaObjectNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_NovaObject* nova_standard_NovaObject_Nova0_construct(nova_standard_NovaObject* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaObject, this,);
	this->vtable = &nova_VTable_nova_standard_NovaObject_val;
	nova_standard_NovaObject_Novasuper(this, 0);
	
	{
		nova_standard_NovaObject_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Object(nova_standard_NovaObject** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

nova_standard_NovaString* nova_standard_NovaObject_NovagetHashCode(nova_standard_NovaObject* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_Nova1_construct(0, exceptionData, (char*)(hashCode(this)));
}

long nova_standard_NovaObject_NovagetHashCodeLong(nova_standard_NovaObject* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return strtol(hashCode(this), (int)(0), (int)(16));
}

nova_standard_NovaString* nova_standard_NovaObject_Nova0_toString(nova_standard_NovaObject* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "[Object @"), exceptionData, nova_standard_NovaObject_NovagetHashCode(this, exceptionData)->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_standard_NovaObject_NovagetHashCode(this, exceptionData), exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "]")));
}

char nova_standard_NovaObject_Nova0_equals(nova_standard_NovaObject* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaanother)
{
	return this == l0_Novaanother;
}

void nova_standard_NovaObject_Novathis(nova_standard_NovaObject* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaObject_Novasuper(nova_standard_NovaObject* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
