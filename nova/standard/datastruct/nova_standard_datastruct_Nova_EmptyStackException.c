#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_EmptyStackException.h>


nova_VTable_nova_standard_datastruct_Nova_EmptyStackException nova_VTable_nova_standard_datastruct_Nova_EmptyStackException_val =
{
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
void nova_standard_datastruct_Nova_EmptyStackExceptionNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_EmptyStackException* nova_standard_datastruct_Nova_EmptyStackException_0_Nova_construct(nova_standard_datastruct_Nova_EmptyStackException* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_EmptyStackException, this,);
	this->vtable = &nova_VTable_nova_standard_datastruct_Nova_EmptyStackException_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_exception_Nova_Exception_Nova_super((nova_standard_exception_Nova_Exception*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_exception_Nova_Exception_Nova_this((nova_standard_exception_Nova_Exception*)(this), exceptionData);
	nova_standard_datastruct_Nova_EmptyStackException_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_EmptyStackException_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_EmptyStackException_Nova_destroy(nova_standard_datastruct_Nova_EmptyStackException** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_EmptyStackException_Nova_this(nova_standard_datastruct_Nova_EmptyStackException* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_datastruct_Nova_EmptyStackException_Nova_super(nova_standard_datastruct_Nova_EmptyStackException* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

