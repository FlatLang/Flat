#include <precompiled.h>
#include "NovaSpider.h"


nova_VTable_Spider nova_VTable_Spider_val =
{
	nova_Spider_getNumLegs,
	nova_Spider_getNumEyes,
	nova_Spider_getDescription,
};

Spider* nova_Spider_construct(Spider* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Spider, this,);
	
	this->vtable = &nova_VTable_Spider_val;
	{
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

void nova_Spider_getNumLegs(Spider* this, ExceptionData* exceptionData)
{
	return 8;
}

void nova_Spider_getNumEyes(Spider* this, ExceptionData* exceptionData)
{
	return 9000;
}

String* nova_Spider_getDescription(Spider* this, ExceptionData* exceptionData)
{
	return nova_String_construct(0, exceptionData, "A disgusting thing (Spider)");
}
