#include <precompiled.h>
#include "NovaPolymorphicSuperClass.h"


nova_VTable_PolymorphicSuperClass nova_VTable_PolymorphicSuperClass_val =
{
	nova_PolymorphicSuperClass_toString,
	nova_Object_equals,
};

PolymorphicSuperClass* nova_PolymorphicSuperClass_construct(PolymorphicSuperClass* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(PolymorphicSuperClass, this,);
	
	this->nova_PolymorphicSuperClass_child = (PolymorphicSubClass*)0;
	this->vtable = &nova_VTable_PolymorphicSuperClass_val;
	{
	}
	
	return this;
}

void nova_del_PolymorphicSuperClass(PolymorphicSuperClass** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_PolymorphicSubClass(&(*this)->nova_PolymorphicSuperClass_child, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_PolymorphicSuperClass_giveBirth(PolymorphicSuperClass* this, ExceptionData* exceptionData)
{
	this->nova_PolymorphicSuperClass_child = nova_PolymorphicSubClass_construct(0, exceptionData);
}

String* nova_PolymorphicSuperClass_toString(PolymorphicSuperClass* this, ExceptionData* exceptionData)
{
	return nova_String_construct(0, exceptionData, "super class");
}
