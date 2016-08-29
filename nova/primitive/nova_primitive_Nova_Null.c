#include <precompiled.h>
#include <nova/primitive/nova_primitive_Nova_Null.h>



nova_primitive_Extension_VTable_Null nova_primitive_Extension_VTable_Null_val =
{
	{
		(int(*)(nova_datastruct_Nova_Comparable*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_String_Nova_compareTo,
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
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_String_Nova_equals,
		0,
		0,
		0,
	},
	nova_primitive_Nova_Null_Nova_toString,
	nova_Nova_String_Nova_equals,
	nova_Nova_String_Accessor_Nova_hashCodeLong,
	nova_primitive_Nova_Null_Nova_concat,
	nova_Nova_String_Nova_compareTo,
};


void nova_primitive_Nova_Null_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_primitive_Nova_Null* nova_primitive_Nova_Null_Nova_construct(nova_primitive_Nova_Null* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_primitive_Nova_Null, this,);
	this->vtable = &nova_primitive_Extension_VTable_Null_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_Nova_String_Nova_super((nova_Nova_String*)this, exceptionData);
	nova_primitive_Nova_Null_0_Nova_super(this, exceptionData);
	
	{
		nova_primitive_Nova_Null_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_primitive_Nova_Null_Nova_destroy(nova_primitive_Nova_Null** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_primitive_Nova_Null_0_Nova_this(nova_primitive_Nova_Null* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

nova_Nova_String* nova_primitive_Nova_Null_Nova_toString(nova_primitive_Nova_Null* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("null"));
}

nova_Nova_String* nova_primitive_Nova_Null_Nova_concat(nova_primitive_Nova_Null* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_primitive_Nova_Null_Nova_other)
{
	return nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("null")), exceptionData, nova_primitive_Nova_Null_Nova_other);
}

void nova_primitive_Nova_Null_0_Nova_super(nova_primitive_Nova_Null* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

