#include <precompiled.h>
#include "NovaSpider.h"


nova_VTable_Spider nova_VTable_Spider_val =
{
	nova_Spider_getNumLegs,
	nova_Spider_getNumEyes,
	nova_Spider_getDescription,
};

Spider* nova_Spider_Spider(ExceptionData* exceptionData)
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

int nova_Spider_getNumLegs(Spider* this, ExceptionData* exceptionData)
{
	return 8;
}

int nova_Spider_getNumEyes(Spider* this, ExceptionData* exceptionData)
{
	return 9000;
}

String* nova_Spider_getDescription(Spider* this, ExceptionData* exceptionData)
{
	return nova_String_String(exceptionData, "It's a disgusting thing (Spider)");
}
