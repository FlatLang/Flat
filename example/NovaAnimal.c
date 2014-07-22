#include <precompiled.h>
#include "NovaAnimal.h"


nova_VTable_Animal nova_VTable_Animal_val =
{
	nova_Animal_getNumLegs,
	nova_Animal_getNumEyes,
	nova_Animal_getDescription,
	nova_Object_toString,
	nova_Object_equals,
};

Animal* nova_Animal_Animal(ExceptionData* exceptionData)
{
	CCLASS_NEW(Animal, this,);
	
	this->vtable = &nova_VTable_Animal_val;
	{
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

int nova_Animal_getNumLegs(Animal* this, ExceptionData* exceptionData)
{
	return 0;
}

int nova_Animal_getNumEyes(Animal* this, ExceptionData* exceptionData)
{
	return 0;
}

String* nova_Animal_getDescription(Animal* this, ExceptionData* exceptionData)
{
	return nova_String_String(exceptionData, "Its just a stupid animal...");
}
