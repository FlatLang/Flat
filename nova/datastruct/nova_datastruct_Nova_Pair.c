#include <precompiled.h>
#include <nova/datastruct/nova_datastruct_Nova_Pair.h>



nova_datastruct_Extension_VTable_Pair nova_datastruct_Extension_VTable_Pair_val =
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
	nova_datastruct_Nova_Pair_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void nova_datastruct_Nova_Pair_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_Nova_Pair* nova_datastruct_Nova_Pair_Nova_construct(nova_datastruct_Nova_Pair* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_Pair_Nova_key, nova_Nova_Object* nova_datastruct_Nova_Pair_Nova_value)
{
	CCLASS_NEW(nova_datastruct_Nova_Pair, this,);
	this->vtable = &nova_datastruct_Extension_VTable_Pair_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_Pair_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_Pair_Nova_this(this, exceptionData, nova_datastruct_Nova_Pair_Nova_key, nova_datastruct_Nova_Pair_Nova_value);
	}
	
	return this;
}

void nova_datastruct_Nova_Pair_Nova_destroy(nova_datastruct_Nova_Pair** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	NOVA_FREE(*this);
}

void nova_datastruct_Nova_Pair_Nova_this(nova_datastruct_Nova_Pair* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_Pair_Nova_key, nova_Nova_Object* nova_datastruct_Nova_Pair_Nova_value)
{
	this->nova_datastruct_Nova_Pair_Nova_key = nova_datastruct_Nova_Pair_Nova_key;
	this->nova_datastruct_Nova_Pair_Nova_value = nova_datastruct_Nova_Pair_Nova_value;
}

nova_Nova_String* nova_datastruct_Nova_Pair_0_Nova_toString(nova_datastruct_Nova_Pair* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Pair (")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((this->nova_datastruct_Nova_Pair_Nova_key)), exceptionData)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((this->nova_datastruct_Nova_Pair_Nova_value)), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(")"))))));
}

void nova_datastruct_Nova_Pair_Nova_super(nova_datastruct_Nova_Pair* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_Nova_Pair_Nova_key = (nova_Nova_Object*)nova_null;
	this->nova_datastruct_Nova_Pair_Nova_value = (nova_Nova_Object*)nova_null;
}

