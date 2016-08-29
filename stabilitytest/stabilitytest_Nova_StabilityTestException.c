#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_StabilityTestException.h>



stabilitytest_Extension_VTable_StabilityTestException stabilitytest_Extension_VTable_StabilityTestException_val =
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


void stabilitytest_Nova_StabilityTestException_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_StabilityTestException* stabilitytest_Nova_StabilityTestException_Nova_construct(stabilitytest_Nova_StabilityTestException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_Nova_StabilityTestException, this,);
	this->vtable = &stabilitytest_Extension_VTable_StabilityTestException_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_Exception_Nova_super((nova_exception_Nova_Exception*)this, exceptionData);
	stabilitytest_Nova_StabilityTestException_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_StabilityTestException_3_Nova_this(this, exceptionData);
	}
	
	return this;
}

void stabilitytest_Nova_StabilityTestException_Nova_destroy(stabilitytest_Nova_StabilityTestException** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_StabilityTestException_3_Nova_this(stabilitytest_Nova_StabilityTestException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void stabilitytest_Nova_StabilityTestException_0_Nova_super(stabilitytest_Nova_StabilityTestException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

