#include <precompiled.h>
#include "NovaTime.h"


nova_VTable_Time nova_VTable_Time_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

Time* nova_Time_construct(Time* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Time, this,);
	this->vtable = &nova_VTable_Time_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Time_super(this, 0);
	
	{
		nova_Time_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_Time(Time** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

long_long nova_static_Time_currentTimeMillis(Time* this, ExceptionData* exceptionData)
{
	return currentTimeMillis();
}

void nova_Time_this(Time* this, ExceptionData* exceptionData)
{
}

void nova_Time_super(Time* this, ExceptionData* exceptionData)
{
}
