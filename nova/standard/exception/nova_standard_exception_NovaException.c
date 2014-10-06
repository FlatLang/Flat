#include <precompiled.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>


nova_VTable_nova_standard_exception_NovaException nova_VTable_nova_standard_exception_NovaException_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_exception_NovaExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_exception_NovaException* nova_standard_exception_NovaException_Nova0_construct(nova_standard_exception_NovaException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_exception_NovaException, this,);
	this->vtable = &nova_VTable_nova_standard_exception_NovaException_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaException_Novasuper(this, 0);
	
	{
		nova_standard_exception_NovaException_Novathis(this, exceptionData);
	}
	
	return this;
}

nova_standard_exception_NovaException* nova_standard_exception_NovaException_Nova1_construct(nova_standard_exception_NovaException* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novamessage)
{
	CCLASS_NEW(nova_standard_exception_NovaException, this,);
	this->vtable = &nova_VTable_nova_standard_exception_NovaException_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaException_Novasuper(this, 0);
	
	{
		nova_standard_exception_NovaException_Nova0_this(this, exceptionData, l0_Novamessage);
	}
	
	return this;
}

void nova_del_Exception(nova_standard_exception_NovaException** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_String(&(*this)->nova_standard_exception_NovaException_Novamessage, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_exception_NovaException_Novathis(nova_standard_exception_NovaException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_exception_NovaException_Nova0_this(this, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, ""));
}

void nova_standard_exception_NovaException_Nova0_this(nova_standard_exception_NovaException* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novamessage)
{
	this->nova_standard_exception_NovaException_Novamessage = l0_Novamessage;
}

void nova_standard_exception_NovaException_Novasuper(nova_standard_exception_NovaException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_exception_NovaException_Novamessage = (nova_standard_NovaString*)nova_null;
}
