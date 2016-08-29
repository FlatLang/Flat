#include <precompiled.h>
#include <nova/datastruct/nova_datastruct_Nova_Bounds.h>



nova_datastruct_Extension_VTable_Bounds nova_datastruct_Extension_VTable_Bounds_val =
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
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_datastruct_Nova_Bounds_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_datastruct_Nova_Bounds_0_Nova_toString,
	nova_datastruct_Nova_Bounds_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};







void nova_datastruct_Nova_Bounds_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_datastruct_Nova_Bounds* nova_datastruct_Nova_Bounds_0_Nova_construct(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_datastruct_Nova_Bounds, this,);
	this->vtable = &nova_datastruct_Extension_VTable_Bounds_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_Bounds_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_Bounds_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_datastruct_Nova_Bounds* nova_datastruct_Nova_Bounds_1_Nova_construct(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_Bounds_Nova_start, int nova_datastruct_Nova_Bounds_Nova_end)
{
	CCLASS_NEW(nova_datastruct_Nova_Bounds, this,);
	this->vtable = &nova_datastruct_Extension_VTable_Bounds_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_datastruct_Nova_Bounds_Nova_super(this, exceptionData);
	
	{
		nova_datastruct_Nova_Bounds_1_Nova_this(this, exceptionData, nova_datastruct_Nova_Bounds_Nova_start, nova_datastruct_Nova_Bounds_Nova_end);
	}
	
	return this;
}

void nova_datastruct_Nova_Bounds_Nova_destroy(nova_datastruct_Nova_Bounds** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	NOVA_FREE(*this);
}

void nova_datastruct_Nova_Bounds_0_Nova_this(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_datastruct_Nova_Bounds_1_Nova_this(this, exceptionData, 0, 0);
}

void nova_datastruct_Nova_Bounds_1_Nova_this(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_Bounds_Nova_start, int nova_datastruct_Nova_Bounds_Nova_end)
{
	this->nova_datastruct_Nova_Bounds_Nova_start = nova_datastruct_Nova_Bounds_Nova_start;
	this->nova_datastruct_Nova_Bounds_Nova_end = nova_datastruct_Nova_Bounds_Nova_end;
}

nova_Nova_String* nova_datastruct_Nova_Bounds_Nova_extractString(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_datastruct_Nova_Bounds_Nova_source)
{
	if (!nova_datastruct_Nova_Bounds_Accessor_Nova_valid(this, exceptionData))
	{
		return (nova_Nova_String*)nova_null;
	}
	return nova_Nova_String_0_Nova_substring(nova_datastruct_Nova_Bounds_Nova_source, exceptionData, this->nova_datastruct_Nova_Bounds_Nova_start, this->nova_datastruct_Nova_Bounds_Nova_end);
}

nova_Nova_String* nova_datastruct_Nova_Bounds_Nova_extractPreString(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_datastruct_Nova_Bounds_Nova_source)
{
	if (!nova_datastruct_Nova_Bounds_Accessor_Nova_valid(this, exceptionData))
	{
		return (nova_Nova_String*)nova_null;
	}
	return nova_Nova_String_0_Nova_substring(nova_datastruct_Nova_Bounds_Nova_source, exceptionData, 0, this->nova_datastruct_Nova_Bounds_Nova_start);
}

nova_Nova_String* nova_datastruct_Nova_Bounds_Nova_extractPostString(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_datastruct_Nova_Bounds_Nova_source)
{
	if (!nova_datastruct_Nova_Bounds_Accessor_Nova_valid(this, exceptionData))
	{
		return nova_datastruct_Nova_Bounds_Nova_source;
	}
	return nova_Nova_String_1_Nova_substring(nova_datastruct_Nova_Bounds_Nova_source, exceptionData, this->nova_datastruct_Nova_Bounds_Nova_end);
}

nova_Nova_String* nova_datastruct_Nova_Bounds_Nova_trimString(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_datastruct_Nova_Bounds_Nova_source)
{
	if (!nova_datastruct_Nova_Bounds_Accessor_Nova_valid(this, exceptionData))
	{
		return nova_datastruct_Nova_Bounds_Nova_source;
	}
	return nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_datastruct_Nova_Bounds_Nova_extractPreString(this, exceptionData, nova_datastruct_Nova_Bounds_Nova_source)), exceptionData, nova_datastruct_Nova_Bounds_Nova_extractPostString(this, exceptionData, nova_datastruct_Nova_Bounds_Nova_source));
}

void nova_datastruct_Nova_Bounds_Nova_invalidate(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_Nova_Bounds_Nova_start = (int)(-1);
	this->nova_datastruct_Nova_Bounds_Nova_end = (int)(-1);
}

char nova_datastruct_Nova_Bounds_0_Nova_equals(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Bounds* nova_datastruct_Nova_Bounds_Nova_bounds)
{
	return nova_datastruct_Nova_Bounds_Nova_bounds != (nova_datastruct_Nova_Bounds*)nova_null && nova_datastruct_Nova_Bounds_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_start == this->nova_datastruct_Nova_Bounds_Nova_start && nova_datastruct_Nova_Bounds_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_end == this->nova_datastruct_Nova_Bounds_Nova_end;
}

nova_Nova_String* nova_datastruct_Nova_Bounds_0_Nova_toString(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("[")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, this->nova_datastruct_Nova_Bounds_Nova_start)), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, this->nova_datastruct_Nova_Bounds_Nova_end)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("]"))))));
}

void nova_datastruct_Nova_Bounds_Nova_cloneTo(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Bounds* nova_datastruct_Nova_Bounds_Nova_bounds)
{
	nova_datastruct_Nova_Bounds_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_start = this->nova_datastruct_Nova_Bounds_Nova_start;
	nova_datastruct_Nova_Bounds_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_end = this->nova_datastruct_Nova_Bounds_Nova_end;
}

nova_datastruct_Nova_Bounds* nova_datastruct_Nova_Bounds_Nova_clone(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_datastruct_Nova_Bounds_1_Nova_construct(0, exceptionData, this->nova_datastruct_Nova_Bounds_Nova_start, this->nova_datastruct_Nova_Bounds_Nova_end);
}

int nova_datastruct_Nova_Bounds_Accessor_Nova_size(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_datastruct_Nova_Bounds_Nova_end - this->nova_datastruct_Nova_Bounds_Nova_start;
}


char nova_datastruct_Nova_Bounds_Accessor_Nova_valid(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_datastruct_Nova_Bounds_Nova_start >= 0 && this->nova_datastruct_Nova_Bounds_Nova_end > 0;
}


char nova_datastruct_Nova_Bounds_Accessor_Nova_endless(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_datastruct_Nova_Bounds_Nova_end < 0;
}


char nova_datastruct_Nova_Bounds_Accessor_Nova_optional(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_datastruct_Nova_Bounds_Nova_start == 0;
}


void nova_datastruct_Nova_Bounds_Nova_super(nova_datastruct_Nova_Bounds* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_datastruct_Nova_Bounds_Nova_start = 0;
	this->nova_datastruct_Nova_Bounds_Nova_end = 0;
}

