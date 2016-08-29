#include <precompiled.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_EmptyStackException.h>



nova_datastruct_list_Extension_VTable_EmptyStackException nova_datastruct_list_Extension_VTable_EmptyStackException_val =
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


void nova_datastruct_list_Nova_EmptyStackException_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_list_Nova_EmptyStackException* nova_datastruct_list_Nova_EmptyStackException_0_Nova_construct(nova_datastruct_list_Nova_EmptyStackException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_datastruct_list_Nova_EmptyStackException, this,);
	this->vtable = &nova_datastruct_list_Extension_VTable_EmptyStackException_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_Exception_Nova_super((nova_exception_Nova_Exception*)this, exceptionData);
	nova_datastruct_list_Nova_NoSuchElementException_0_Nova_super((nova_datastruct_list_Nova_NoSuchElementException*)this, exceptionData);
	nova_datastruct_list_Nova_EmptyStackException_3_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_list_Nova_EmptyStackException_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_datastruct_list_Nova_EmptyStackException* nova_datastruct_list_Nova_EmptyStackException_1_Nova_construct(nova_datastruct_list_Nova_EmptyStackException* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_datastruct_list_Nova_EmptyStackException_Nova_message)
{
	CCLASS_NEW(nova_datastruct_list_Nova_EmptyStackException, this,);
	this->vtable = &nova_datastruct_list_Extension_VTable_EmptyStackException_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_Exception_Nova_super((nova_exception_Nova_Exception*)this, exceptionData);
	nova_datastruct_list_Nova_NoSuchElementException_0_Nova_super((nova_datastruct_list_Nova_NoSuchElementException*)this, exceptionData);
	nova_datastruct_list_Nova_EmptyStackException_3_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_list_Nova_EmptyStackException_0_Nova_this(this, exceptionData, nova_datastruct_list_Nova_EmptyStackException_Nova_message);
	}
	
	return this;
}

void nova_datastruct_list_Nova_EmptyStackException_Nova_destroy(nova_datastruct_list_Nova_EmptyStackException** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_datastruct_list_Nova_EmptyStackException_Nova_this(nova_datastruct_list_Nova_EmptyStackException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_datastruct_list_Nova_EmptyStackException_0_Nova_this(this, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")));
}

void nova_datastruct_list_Nova_EmptyStackException_0_Nova_this(nova_datastruct_list_Nova_EmptyStackException* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_datastruct_list_Nova_EmptyStackException_Nova_message)
{
	nova_datastruct_list_Nova_NoSuchElementException_2_Nova_this((nova_datastruct_list_Nova_NoSuchElementException*)(this), exceptionData, nova_datastruct_list_Nova_EmptyStackException_Nova_message);
}

void nova_datastruct_list_Nova_EmptyStackException_3_Nova_super(nova_datastruct_list_Nova_EmptyStackException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

