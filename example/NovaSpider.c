#include <precompiled.h>
#include "NovaSpider.h"


nova_VTable_Spider nova_VTable_Spider_val =
{
	nova_0_Spider_getNumLegs,
	nova_0_Spider_getNumEyes,
	nova_0_Spider_getDescription,
};

Spider* nova_Spider_construct(Spider* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Spider, this,);
	this->vtable = &nova_VTable_Spider_val;
	nova_Object_super((Object*)this, 0);
	nova_Animal_super((Animal*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Animal_this((Animal*)(this), exceptionData);
	nova_Spider_super(this, 0);
	
	{
		nova_Spider_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_Spider(Spider** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_0_Spider_getNumLegs(Spider* this, ExceptionData* exceptionData)
{
	return 8;
}

int nova_0_Spider_getNumEyes(Spider* this, ExceptionData* exceptionData)
{
	return 9000;
}

String* nova_0_Spider_getDescription(Spider* this, ExceptionData* exceptionData)
{
	return nova_String_construct(0, exceptionData, "A disgusting thing (Spider)");
}

void nova_Spider_this(Spider* this, ExceptionData* exceptionData)
{
}

void nova_Spider_super(Spider* this, ExceptionData* exceptionData)
{
}
