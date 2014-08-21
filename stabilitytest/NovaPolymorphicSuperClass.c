#include <precompiled.h>
#include "NovaPolymorphicSuperClass.h"


nova_VTable_PolymorphicSuperClass nova_VTable_PolymorphicSuperClass_val =
{
	nova_0_PolymorphicSuperClass_toString,
	nova_0_Object_equals,
};

PolymorphicSuperClass* nova_0_PolymorphicSuperClass_construct(PolymorphicSuperClass* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(PolymorphicSuperClass, this,);
	this->vtable = &nova_VTable_PolymorphicSuperClass_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_PolymorphicSuperClass_super(this, 0);
	
	{
		nova_PolymorphicSuperClass_this(this, exceptionData);
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

String* nova_0_PolymorphicSuperClass_toString(PolymorphicSuperClass* this, ExceptionData* exceptionData)
{
	return nova_String_construct(0, exceptionData, "super class");
}

void nova_PolymorphicSuperClass_this(PolymorphicSuperClass* this, ExceptionData* exceptionData)
{
}

void nova_PolymorphicSuperClass_super(PolymorphicSuperClass* this, ExceptionData* exceptionData)
{
	this->nova_PolymorphicSuperClass_child = (PolymorphicSubClass*)0;
}
