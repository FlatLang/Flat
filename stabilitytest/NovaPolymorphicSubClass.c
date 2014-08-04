#include <precompiled.h>
#include "NovaPolymorphicSubClass.h"


nova_VTable_PolymorphicSubClass nova_VTable_PolymorphicSubClass_val =
{
	nova_1_PolymorphicSubClass_toString,
};

PolymorphicSubClass* nova_PolymorphicSubClass_construct(PolymorphicSubClass* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(PolymorphicSubClass, this,);
	
	this->vtable = &nova_VTable_PolymorphicSubClass_val;
	{
	}
	
	return this;
}

void nova_del_PolymorphicSubClass(PolymorphicSubClass** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

String* nova_1_PolymorphicSubClass_toString(PolymorphicSubClass* this, ExceptionData* exceptionData)
{
	return nova_String_construct(0, exceptionData, "sub class");
}
