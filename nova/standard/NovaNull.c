#include <precompiled.h>
#include "NovaNull.h"


nova_VTable_Null nova_VTable_Null_val =
{
	nova_0_Null_toString,
	nova_0_String_equals,
	nova_0_Null_concat,
};
CCLASS_PRIVATE
(
	char* nova_String_data;
	
)

Null* nova_0_Null_construct(Null* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(Null, this);
	this->vtable = &nova_VTable_Null_val;
	nova_Object_super((Object*)this, 0);
	nova_String_super((String*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_Null_super(this, 0);
	
	{
		nova_Null_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_Null(Null** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

String* nova_0_Null_toString(Null* this, ExceptionData* exceptionData)
{
	return nova_String_construct(0, exceptionData, "null");
}

String* nova_0_Null_concat(Null* this, ExceptionData* exceptionData, String* nova_0_other)
{
	return nova_0_String_concat(nova_String_construct(0, exceptionData, "null"), exceptionData, nova_0_other);
}

void nova_Null_this(Null* this, ExceptionData* exceptionData)
{
}

void nova_Null_super(Null* this, ExceptionData* exceptionData)
{
}
