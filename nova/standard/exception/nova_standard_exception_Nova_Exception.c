#include <precompiled.h>
#include <nova/standard/exception/nova_standard_exception_Nova_Exception.h>

nova_standard_exception_Extension_VTable_Exception nova_standard_exception_Extension_VTable_Exception_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_exception_Nova_ExceptionNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_exception_Nova_Exception* nova_standard_exception_Nova_Exception_0_Nova_construct(nova_standard_exception_Nova_Exception* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_exception_Nova_Exception, this,);
	this->vtable = &nova_standard_exception_Extension_VTable_Exception_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_exception_Nova_Exception_Nova_super(this, exceptionData);
	
	{
		nova_standard_exception_Nova_Exception_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_exception_Nova_Exception* nova_standard_exception_Nova_Exception_1_Nova_construct(nova_standard_exception_Nova_Exception* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_exception_Nova_Exception_Nova_message)
{
	CCLASS_NEW(nova_standard_exception_Nova_Exception, this,);
	this->vtable = &nova_standard_exception_Extension_VTable_Exception_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_exception_Nova_Exception_Nova_super(this, exceptionData);
	
	{
		nova_standard_exception_Nova_Exception_1_Nova_this(this, exceptionData, nova_standard_exception_Nova_Exception_Nova_message);
	}
	
	return this;
}

void nova_standard_exception_Nova_Exception_Nova_destroy(nova_standard_exception_Nova_Exception** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_Nova_String_Nova_destroy(&(*this)->nova_standard_exception_Nova_Exception_Nova_message, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_exception_Nova_Exception_0_Nova_this(nova_standard_exception_Nova_Exception* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_exception_Nova_Exception_1_Nova_this(this, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ""));
}

void nova_standard_exception_Nova_Exception_1_Nova_this(nova_standard_exception_Nova_Exception* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_exception_Nova_Exception_Nova_message)
{
	this->nova_standard_exception_Nova_Exception_Nova_message = nova_standard_exception_Nova_Exception_Nova_message;
}

void nova_standard_exception_Nova_Exception_Nova_super(nova_standard_exception_Nova_Exception* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_exception_Nova_Exception_Nova_message = (nova_standard_Nova_String*)nova_null;
}

