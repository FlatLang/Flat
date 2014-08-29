#include <precompiled.h>
#include "NovaBodyBuilder.h"


nova_VTable_BodyBuilder nova_VTable_BodyBuilder_val =
{
	nova_0_BodyBuilder_sayHello,
};

BodyBuilder* nova_BodyBuilder_construct(BodyBuilder* this, ExceptionData* exceptionData, int nova_0_weightClass, String* nova_0_name)
{
	CCLASS_NEW(BodyBuilder, this,);
	this->vtable = &nova_VTable_BodyBuilder_val;
	nova_Object_super((Object*)this, 0);
	nova_Person_super((Person*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_BodyBuilder_super(this, 0);
	
	{
		nova_BodyBuilder_this(this, exceptionData, nova_0_weightClass, nova_0_name);
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

void nova_BodyBuilder_this(BodyBuilder* this, ExceptionData* exceptionData, int nova_0_weightClass, String* nova_0_name)
{
	this->nova_Person_age = 5;
	this->nova_BodyBuilder_weightClass = nova_0_weightClass;
	this->nova_Person_name = nova_0_name;
}

void nova_0_BodyBuilder_sayHello(BodyBuilder* this, ExceptionData* exceptionData)
{
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "Hello from "), exceptionData, nova_String_concat(this->nova_Person_name, exceptionData, nova_String_construct(0, exceptionData, " the BodyBuilder"))));
}

void nova_BodyBuilder_super(BodyBuilder* this, ExceptionData* exceptionData)
{
	this->nova_BodyBuilder_weightClass = 0;
}
