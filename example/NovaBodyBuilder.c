#include <precompiled.h>
#include "NovaBodyBuilder.h"


nova_VTable_BodyBuilder nova_VTable_BodyBuilder_val =
{
	nova_BodyBuilder_sayHello,
};

BodyBuilder* nova_BodyBuilder_BodyBuilder(ExceptionData* exceptionData, int nova_0_weightClass, String* nova_0_name)
{
	CCLASS_NEW(BodyBuilder, this,);
	
	this->nova_BodyBuilder_weightClass = 0;
	this->vtable = &nova_VTable_BodyBuilder_val;
	{
		this->nova_Person_age = 5;
		this->nova_BodyBuilder_weightClass = nova_0_weightClass;
		this->nova_Person_name = nova_0_name;
	}
	
	return this;
}

void nova_del_BodyBuilder(BodyBuilder** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_BodyBuilder_sayHello(BodyBuilder* this, ExceptionData* exceptionData)
{
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)("Hello from ")), exceptionData, nova_String_concat(this->nova_Person_name, exceptionData, nova_String_String(exceptionData, (char*)(" the BodyBuilder")))));
}
