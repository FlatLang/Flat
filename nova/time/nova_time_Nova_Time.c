#include <precompiled.h>
#include <nova/time/nova_time_Nova_Time.h>



nova_time_Extension_VTable_Time nova_time_Extension_VTable_Time_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};




void nova_time_Nova_Time_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_time_Nova_Time* nova_time_Nova_Time_Nova_construct(nova_time_Nova_Time* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_time_Nova_Time, this,);
	this->vtable = &nova_time_Extension_VTable_Time_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_time_Nova_Time_Nova_super(this, exceptionData);
	
	{
		nova_time_Nova_Time_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_time_Nova_Time_Nova_destroy(nova_time_Nova_Time** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_time_Nova_Time_0_Nova_this(nova_time_Nova_Time* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

long_long nova_time_Nova_Time_Accessor_Nova_currentTimeMillis(nova_time_Nova_Time* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (long_long)currentTimeMillis();
}


void nova_time_Nova_Time_Nova_super(nova_time_Nova_Time* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

