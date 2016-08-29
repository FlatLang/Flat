#include <precompiled.h>
#include <nova/exception/nova_exception_Nova_Exception.h>



nova_exception_Extension_VTable_Exception nova_exception_Extension_VTable_Exception_val =
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


void nova_exception_Nova_Exception_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_exception_Nova_Exception* nova_exception_Nova_Exception_0_Nova_construct(nova_exception_Nova_Exception* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_exception_Nova_Exception, this,);
	this->vtable = &nova_exception_Extension_VTable_Exception_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_Exception_Nova_super(this, exceptionData);
	
	{
		nova_exception_Nova_Exception_3_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_exception_Nova_Exception* nova_exception_Nova_Exception_1_Nova_construct(nova_exception_Nova_Exception* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_exception_Nova_Exception_Nova_message)
{
	CCLASS_NEW(nova_exception_Nova_Exception, this,);
	this->vtable = &nova_exception_Extension_VTable_Exception_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_Exception_Nova_super(this, exceptionData);
	
	{
		nova_exception_Nova_Exception_4_Nova_this(this, exceptionData, nova_exception_Nova_Exception_Nova_message);
	}
	
	return this;
}

void nova_exception_Nova_Exception_Nova_destroy(nova_exception_Nova_Exception** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_Nova_String_Nova_destroy(&(*this)->nova_exception_Nova_Exception_Nova_message, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_exception_Nova_Exception_3_Nova_this(nova_exception_Nova_Exception* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_exception_Nova_Exception_4_Nova_this(this, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")));
}

void nova_exception_Nova_Exception_4_Nova_this(nova_exception_Nova_Exception* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_exception_Nova_Exception_Nova_message)
{
	this->nova_exception_Nova_Exception_Nova_message = nova_exception_Nova_Exception_Nova_message;
}

void nova_exception_Nova_Exception_Nova_super(nova_exception_Nova_Exception* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_exception_Nova_Exception_Nova_message = (nova_Nova_String*)nova_null;
}

