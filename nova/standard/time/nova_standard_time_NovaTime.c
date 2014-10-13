#include <precompiled.h>
#include <nova/standard/time/nova_standard_time_NovaTime.h>


nova_VTable_nova_standard_time_NovaTime nova_VTable_nova_standard_time_NovaTime_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_time_NovaTimeNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_time_NovaTime* nova_standard_time_NovaTime_Nova0_construct(nova_standard_time_NovaTime* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_time_NovaTime, this,);
	this->vtable = &nova_VTable_nova_standard_time_NovaTime_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_time_NovaTime_Novasuper(this, 0);
	
	{
		nova_standard_time_NovaTime_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Time(nova_standard_time_NovaTime** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

long nova_standard_time_NovaTime_static_NovacurrentTimeMillis(nova_standard_time_NovaTime* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return currentTimeMillis();
}

void nova_standard_time_NovaTime_Novathis(nova_standard_time_NovaTime* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_time_NovaTime_Novasuper(nova_standard_time_NovaTime* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
