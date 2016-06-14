#include <precompiled.h>
#include <nova/standard/time/nova_standard_time_Nova_Time.h>

nova_standard_time_Extension_VTable_Time nova_standard_time_Extension_VTable_Time_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};




void nova_standard_time_Nova_TimeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_time_Nova_Time* nova_standard_time_Nova_Time_0_Nova_construct(nova_standard_time_Nova_Time* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_time_Nova_Time, this,);
	this->vtable = &nova_standard_time_Extension_VTable_Time_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_time_Nova_Time_Nova_super(this, exceptionData);
	
	{
		nova_standard_time_Nova_Time_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_time_Nova_Time_Nova_destroy(nova_standard_time_Nova_Time** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_time_Nova_Time_0_Nova_this(nova_standard_time_Nova_Time* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

long nova_standard_time_Nova_Time_Accessor_Nova_currentTimeMillis(nova_standard_time_Nova_Time* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (long)currentTimeMillis();
}


void nova_standard_time_Nova_Time_Nova_super(nova_standard_time_Nova_Time* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

