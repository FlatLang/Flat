#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_UnstableException.h>



stabilitytest_Extension_VTable_UnstableException stabilitytest_Extension_VTable_UnstableException_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void stabilitytest_Nova_UnstableException_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_UnstableException* stabilitytest_Nova_UnstableException_Nova_UnstableException(stabilitytest_Nova_UnstableException* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* stabilitytest_Nova_UnstableException_Nova_message)
{
	CCLASS_NEW(stabilitytest_Nova_UnstableException, this,);
	this->vtable = &stabilitytest_Extension_VTable_UnstableException_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_exception_Nova_Exception_Nova_super((nova_standard_exception_Nova_Exception*)this, exceptionData);
	stabilitytest_Nova_UnstableException_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_UnstableException_4_Nova_this(this, exceptionData, stabilitytest_Nova_UnstableException_Nova_message);
	}
	
	return this;
}

void stabilitytest_Nova_UnstableException_Nova_destroy(stabilitytest_Nova_UnstableException** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_UnstableException_4_Nova_this(stabilitytest_Nova_UnstableException* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* stabilitytest_Nova_UnstableException_Nova_message)
{
	nova_standard_exception_Nova_Exception_4_Nova_this((nova_standard_exception_Nova_Exception*)(this), exceptionData, stabilitytest_Nova_UnstableException_Nova_message);
}

void stabilitytest_Nova_UnstableException_0_Nova_super(stabilitytest_Nova_UnstableException* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

