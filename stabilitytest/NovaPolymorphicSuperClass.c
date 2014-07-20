#include <precompiled.h>
#include "NovaPolymorphicSuperClass.h"


nova_VTable_PolymorphicSuperClass nova_VTable_PolymorphicSuperClass_val =
{
	nova_PolymorphicSuperClass_toString,
};

PolymorphicSuperClass* nova_PolymorphicSuperClass_PolymorphicSuperClass(ExceptionData* exceptionData)
{
	CCLASS_NEW(PolymorphicSuperClass, this,);
	
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
	
	
	{
	}
	NOVA_FREE(*this);
}

String* nova_PolymorphicSuperClass_toString(PolymorphicSuperClass* this, ExceptionData* exceptionData)
{
	return nova_String_String(exceptionData, "super class");
}
