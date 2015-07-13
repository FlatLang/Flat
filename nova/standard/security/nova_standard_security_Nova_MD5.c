#include <precompiled.h>
#include <nova/standard/security/nova_standard_security_Nova_MD5.h>


nova_standard_security_Extension_VTable_MD5 nova_standard_security_Extension_VTable_MD5_val =
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_security_Nova_MD5Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_security_Nova_MD5* nova_standard_security_Nova_MD5_2_Nova_construct(nova_standard_security_Nova_MD5* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_security_Nova_MD5, this,);
	this->vtable = &nova_standard_security_Extension_VTable_MD5_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_security_Nova_MD5_Nova_super(this, exceptionData);
	
	{
		nova_standard_security_Nova_MD5_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_security_Nova_MD5_Nova_destroy(nova_standard_security_Nova_MD5** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_standard_Nova_String* nova_standard_security_Nova_MD5_Nova_encrypt(nova_standard_security_Nova_MD5* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_str)
{
	char* l1_Nova_data;
	
	l1_Nova_data = (char*)(nova_md5((char*)(l0_Nova_str->nova_standard_Nova_String_Nova_chars)));
	if (l1_Nova_data == 0)
	{
		return (nova_standard_Nova_String*)nova_null;
	}
	return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_data);
}

void nova_standard_security_Nova_MD5_2_Nova_this(nova_standard_security_Nova_MD5* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_security_Nova_MD5_Nova_super(nova_standard_security_Nova_MD5* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

