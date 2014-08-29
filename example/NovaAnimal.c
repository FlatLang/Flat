#include <precompiled.h>
#include "NovaAnimal.h"


nova_VTable_Animal nova_VTable_Animal_val =
{
	nova_0_Animal_getNumLegs,
	nova_0_Animal_getNumEyes,
	nova_0_Animal_getDescription,
	nova_0_Object_toString,
	nova_0_Object_equals,
};

Animal* nova_0_Animal_construct(Animal* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Animal, this,);
	this->vtable = &nova_VTable_Animal_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Animal_super(this, 0);
	
	{
		nova_Animal_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_Animal(Animal** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_0_Animal_getNumLegs(Animal* this, ExceptionData* exceptionData)
{
	return 0;
}

int nova_0_Animal_getNumEyes(Animal* this, ExceptionData* exceptionData)
{
	return 0;
}

String* nova_0_Animal_getDescription(Animal* this, ExceptionData* exceptionData)
{
	return nova_String_construct(0, exceptionData, "Its just a stupid animal...");
}

void nova_Animal_this(Animal* this, ExceptionData* exceptionData)
{
}

void nova_Animal_super(Animal* this, ExceptionData* exceptionData)
{
}
