#include <precompiled.h>
#include "NovaDog.h"


nova_VTable_Dog nova_VTable_Dog_val =
{
	nova_1_Dog_getNumLegs,
	nova_1_Dog_getNumEyes,
	nova_1_Dog_getDescription,
};

Dog* nova_Dog_Dog(Dog* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Dog, this,);
	
	this->vtable = &nova_VTable_Dog_val;
	{
	}
	
	return this;
}

void nova_del_Dog(Dog** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_1_Dog_getNumLegs(Dog* this, ExceptionData* exceptionData)
{
	return 4;
}

int nova_1_Dog_getNumEyes(Dog* this, ExceptionData* exceptionData)
{
	return 2;
}

String* nova_1_Dog_getDescription(Dog* this, ExceptionData* exceptionData)
{
	return nova_String_String(0, exceptionData, (char*)("A fuzzy dog"));
}
