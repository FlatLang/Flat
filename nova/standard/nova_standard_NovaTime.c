#include <precompiled.h>
#include <nova/standard/nova_standard_NovaTime.h>


nova_VTable_nova_standard_NovaTime nova_VTable_nova_standard_NovaTime_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};

nova_standard_NovaTime* nova_standard_NovaTime_Novaconstruct(nova_standard_NovaTime* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaTime, this,);
	this->vtable = &nova_VTable_nova_standard_NovaTime_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaTime_Novasuper(this, 0);
	
	{
		nova_standard_NovaTime_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Time(nova_standard_NovaTime** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

long_long nova_standard_NovaTime_static_NovacurrentTimeMillis(nova_standard_NovaTime* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return currentTimeMillis();
}

void nova_standard_NovaTime_Novathis(nova_standard_NovaTime* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaTime_Novasuper(nova_standard_NovaTime* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
