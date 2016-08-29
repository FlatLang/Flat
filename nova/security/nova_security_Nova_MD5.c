#include <precompiled.h>
#include <nova/security/nova_security_Nova_MD5.h>



nova_security_Extension_VTable_MD5 nova_security_Extension_VTable_MD5_val =
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


void nova_security_Nova_MD5_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_security_Nova_MD5* nova_security_Nova_MD5_Nova_construct(nova_security_Nova_MD5* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_security_Nova_MD5, this,);
	this->vtable = &nova_security_Extension_VTable_MD5_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_security_Nova_MD5_Nova_super(this, exceptionData);
	
	{
		nova_security_Nova_MD5_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_security_Nova_MD5_Nova_destroy(nova_security_Nova_MD5** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_Nova_String* nova_security_Nova_MD5_Nova_encrypt(nova_security_Nova_MD5* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_security_Nova_MD5_Nova_str)
{
	char* l1_Nova_data = (char*)nova_null;
	
	
	l1_Nova_data = (char*)(nova_md5((char*)(nova_security_Nova_MD5_Nova_str->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data)));
	if (l1_Nova_data == 0)
	{
		return (nova_Nova_String*)nova_null;
	}
	return nova_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_data);
}

void nova_security_Nova_MD5_0_Nova_this(nova_security_Nova_MD5* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_security_Nova_MD5_Nova_super(nova_security_Nova_MD5* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

