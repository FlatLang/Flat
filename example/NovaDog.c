#include <precompiled.h>
#include "NovaDog.h"


nova_VTable_Dog nova_VTable_Dog_val =
{
	nova_0_Animal_toString,
	nova_0_Object_equals,
	nova_0_Dog_getNumLegs,
	nova_0_Dog_getNumEyes,
	nova_0_Dog_getDescription,
};

Dog* nova_0_Dog_construct(Dog* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Dog, this,);
	this->vtable = &nova_VTable_Dog_val;
	nova_Object_super((Object*)this, 0);
	nova_Animal_super((Animal*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Animal_this((Animal*)(this), exceptionData);
	nova_Dog_super(this, 0);
	
	{
		nova_Dog_this(this, exceptionData);
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

int nova_0_Dog_getNumLegs(Dog* this, ExceptionData* exceptionData)
{
	return 4;
}

int nova_0_Dog_getNumEyes(Dog* this, ExceptionData* exceptionData)
{
	return 2;
}

String* nova_0_Dog_getDescription(Dog* this, ExceptionData* exceptionData)
{
	return nova_String_construct(0, exceptionData, "A fuzzy dog");
}

void nova_Dog_this(Dog* this, ExceptionData* exceptionData)
{
}

void nova_Dog_super(Dog* this, ExceptionData* exceptionData)
{
}
