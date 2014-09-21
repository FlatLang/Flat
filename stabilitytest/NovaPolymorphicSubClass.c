#include <precompiled.h>
#include "NovaPolymorphicSubClass.h"


nova_VTable_PolymorphicSubClass nova_VTable_PolymorphicSubClass_val =
{
	nova_0_PolymorphicSubClass_toString,
	nova_0_Object_equals,
};

PolymorphicSubClass* nova_0_PolymorphicSubClass_construct(PolymorphicSubClass* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(PolymorphicSubClass, this,);
	this->vtable = &nova_VTable_PolymorphicSubClass_val;
	nova_Object_super((Object*)this, 0);
	nova_PolymorphicSuperClass_super((PolymorphicSuperClass*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_PolymorphicSuperClass_this((PolymorphicSuperClass*)(this), exceptionData);
	nova_PolymorphicSubClass_super(this, 0);
	
	{
		nova_PolymorphicSubClass_this(this, exceptionData);
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

String* nova_0_PolymorphicSubClass_toString(PolymorphicSubClass* this, ExceptionData* exceptionData)
{
	return nova_String_construct(0, exceptionData, "sub class");
}

void nova_PolymorphicSubClass_this(PolymorphicSubClass* this, ExceptionData* exceptionData)
{
}

void nova_PolymorphicSubClass_super(PolymorphicSubClass* this, ExceptionData* exceptionData)
{
}
