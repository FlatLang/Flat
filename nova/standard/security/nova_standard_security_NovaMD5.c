#include <precompiled.h>
#include <nova/standard/security/nova_standard_security_NovaMD5.h>


nova_VTable_nova_standard_security_NovaMD5 nova_VTable_nova_standard_security_NovaMD5_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_security_NovaMD5Nova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_security_NovaMD5* nova_standard_security_NovaMD5_Nova0_construct(nova_standard_security_NovaMD5* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_security_NovaMD5, this,);
	this->vtable = &nova_VTable_nova_standard_security_NovaMD5_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_security_NovaMD5_Novasuper(this, exceptionData);
	
	{
		nova_standard_security_NovaMD5_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_MD5(nova_standard_security_NovaMD5** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

nova_standard_NovaString* nova_standard_security_NovaMD5_static_Novaencrypt(nova_standard_security_NovaMD5* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastr)
{
	char* l1_Novadata;
	
	l1_Novadata = (char*)(nova_md5((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novastr, exceptionData))));
	if (l1_Novadata == 0)
	{
		return (nova_standard_NovaString*)nova_null;
	}
	return nova_standard_NovaString_Nova1_construct(0, exceptionData, l1_Novadata);
}

void nova_standard_security_NovaMD5_Novathis(nova_standard_security_NovaMD5* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_security_NovaMD5_Novasuper(nova_standard_security_NovaMD5* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
