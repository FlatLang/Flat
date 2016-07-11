#include <precompiled.h>
#include <nova/standard/exception/nova_standard_exception_Nova_UnimplementedOperationException.h>

nova_standard_exception_Extension_VTable_UnimplementedOperationException nova_standard_exception_Extension_VTable_UnimplementedOperationException_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_exception_Nova_UnimplementedOperationExceptionNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_exception_Nova_UnimplementedOperationException* nova_standard_exception_Nova_UnimplementedOperationException_Nova_UnimplementedOperationException(nova_standard_exception_Nova_UnimplementedOperationException* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_exception_Nova_UnimplementedOperationException_Nova_message)
{
	CCLASS_NEW(nova_standard_exception_Nova_UnimplementedOperationException, this,);
	this->vtable = &nova_standard_exception_Extension_VTable_UnimplementedOperationException_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_exception_Nova_Exception_Nova_super((nova_standard_exception_Nova_Exception*)this, exceptionData);
	nova_standard_exception_Nova_UnimplementedOperationException_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_exception_Nova_UnimplementedOperationException_4_Nova_this(this, exceptionData, nova_standard_exception_Nova_UnimplementedOperationException_Nova_message);
	}
	
	return this;
}

void nova_standard_exception_Nova_UnimplementedOperationException_Nova_destroy(nova_standard_exception_Nova_UnimplementedOperationException** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_exception_Nova_UnimplementedOperationException_4_Nova_this(nova_standard_exception_Nova_UnimplementedOperationException* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_exception_Nova_UnimplementedOperationException_Nova_message)
{
	nova_standard_exception_Nova_Exception_4_Nova_this((nova_standard_exception_Nova_Exception*)(this), exceptionData, nova_standard_exception_Nova_UnimplementedOperationException_Nova_message);
}

void nova_standard_exception_Nova_UnimplementedOperationException_0_Nova_super(nova_standard_exception_Nova_UnimplementedOperationException* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

