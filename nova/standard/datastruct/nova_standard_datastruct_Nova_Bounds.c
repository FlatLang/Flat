#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Bounds.h>

nova_standard_datastruct_Extension_VTable_Bounds nova_standard_datastruct_Extension_VTable_Bounds_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_datastruct_Nova_Bounds_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_datastruct_Nova_Bounds_1_Nova_toString,
	nova_standard_datastruct_Nova_Bounds_0_Nova_equals,
};







void nova_standard_datastruct_Nova_BoundsNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Bounds* nova_standard_datastruct_Nova_Bounds_0_Nova_construct(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Bounds, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Bounds_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Bounds_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Bounds_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_datastruct_Nova_Bounds* nova_standard_datastruct_Nova_Bounds_1_Nova_construct(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_Nova_Bounds_Nova_start, int nova_standard_datastruct_Nova_Bounds_Nova_end)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Bounds, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Bounds_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_datastruct_Nova_Bounds_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Bounds_1_Nova_this(this, exceptionData, nova_standard_datastruct_Nova_Bounds_Nova_start, nova_standard_datastruct_Nova_Bounds_Nova_end);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_Bounds_Nova_destroy(nova_standard_datastruct_Nova_Bounds** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_Bounds_0_Nova_this(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_Nova_Bounds_1_Nova_this(this, exceptionData, 0, 0);
}

void nova_standard_datastruct_Nova_Bounds_1_Nova_this(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_datastruct_Nova_Bounds_Nova_start, int nova_standard_datastruct_Nova_Bounds_Nova_end)
{
	this->nova_standard_datastruct_Nova_Bounds_Nova_start = nova_standard_datastruct_Nova_Bounds_Nova_start;
	this->nova_standard_datastruct_Nova_Bounds_Nova_end = nova_standard_datastruct_Nova_Bounds_Nova_end;
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_extractString(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_source)
{
	if (!nova_standard_datastruct_Nova_Bounds_Accessor_Nova_valid(this, exceptionData))
	{
		return (nova_standard_Nova_String*)nova_null;
	}
	return nova_standard_Nova_String_0_Nova_substring(nova_standard_datastruct_Nova_Bounds_Nova_source, exceptionData, this->nova_standard_datastruct_Nova_Bounds_Nova_start, this->nova_standard_datastruct_Nova_Bounds_Nova_end);
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_extractPreString(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_source)
{
	if (!nova_standard_datastruct_Nova_Bounds_Accessor_Nova_valid(this, exceptionData))
	{
		return (nova_standard_Nova_String*)nova_null;
	}
	return nova_standard_Nova_String_0_Nova_substring(nova_standard_datastruct_Nova_Bounds_Nova_source, exceptionData, 0, this->nova_standard_datastruct_Nova_Bounds_Nova_start);
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_extractPostString(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_source)
{
	if (!nova_standard_datastruct_Nova_Bounds_Accessor_Nova_valid(this, exceptionData))
	{
		return nova_standard_datastruct_Nova_Bounds_Nova_source;
	}
	return nova_standard_Nova_String_1_Nova_substring(nova_standard_datastruct_Nova_Bounds_Nova_source, exceptionData, this->nova_standard_datastruct_Nova_Bounds_Nova_end);
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_trimString(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_source)
{
	if (!nova_standard_datastruct_Nova_Bounds_Accessor_Nova_valid(this, exceptionData))
	{
		return nova_standard_datastruct_Nova_Bounds_Nova_source;
	}
	return nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(nova_standard_datastruct_Nova_Bounds_Nova_extractPreString(this, exceptionData, nova_standard_datastruct_Nova_Bounds_Nova_source)), exceptionData, nova_standard_datastruct_Nova_Bounds_Nova_extractPostString(this, exceptionData, nova_standard_datastruct_Nova_Bounds_Nova_source));
}

void nova_standard_datastruct_Nova_Bounds_Nova_invalidate(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Bounds_Nova_start = -1;
	this->nova_standard_datastruct_Nova_Bounds_Nova_end = -1;
}

char nova_standard_datastruct_Nova_Bounds_0_Nova_equals(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Bounds* nova_standard_datastruct_Nova_Bounds_Nova_bounds)
{
	return nova_standard_datastruct_Nova_Bounds_Nova_bounds != (nova_standard_datastruct_Nova_Bounds*)nova_null && nova_standard_datastruct_Nova_Bounds_Nova_bounds->nova_standard_datastruct_Nova_Bounds_Nova_start == this->nova_standard_datastruct_Nova_Bounds_Nova_start && nova_standard_datastruct_Nova_Bounds_Nova_bounds->nova_standard_datastruct_Nova_Bounds_Nova_end == this->nova_standard_datastruct_Nova_Bounds_Nova_end;
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_1_Nova_toString(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "["), exceptionData, nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, this->nova_standard_datastruct_Nova_Bounds_Nova_start)), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "), exceptionData, nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, this->nova_standard_datastruct_Nova_Bounds_Nova_end)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "]")))));
}

void nova_standard_datastruct_Nova_Bounds_Nova_cloneTo(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Bounds* nova_standard_datastruct_Nova_Bounds_Nova_bounds)
{
	nova_standard_datastruct_Nova_Bounds_Nova_bounds->nova_standard_datastruct_Nova_Bounds_Nova_start = this->nova_standard_datastruct_Nova_Bounds_Nova_start;
	nova_standard_datastruct_Nova_Bounds_Nova_bounds->nova_standard_datastruct_Nova_Bounds_Nova_end = this->nova_standard_datastruct_Nova_Bounds_Nova_end;
}

nova_standard_datastruct_Nova_Bounds* nova_standard_datastruct_Nova_Bounds_Nova_clone(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_Nova_Bounds_1_Nova_construct(0, exceptionData, this->nova_standard_datastruct_Nova_Bounds_Nova_start, this->nova_standard_datastruct_Nova_Bounds_Nova_end);
}

int nova_standard_datastruct_Nova_Bounds_Accessor_Nova_size(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_Nova_Bounds_Nova_end - this->nova_standard_datastruct_Nova_Bounds_Nova_start;
}


char nova_standard_datastruct_Nova_Bounds_Accessor_Nova_valid(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_standard_datastruct_Nova_Bounds_Nova_start >= 0 && this->nova_standard_datastruct_Nova_Bounds_Nova_end > 0;
}


char nova_standard_datastruct_Nova_Bounds_Accessor_Nova_endless(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_standard_datastruct_Nova_Bounds_Nova_end < 0;
}


char nova_standard_datastruct_Nova_Bounds_Accessor_Nova_optional(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_Nova_Bounds_Nova_start == 0;
}


void nova_standard_datastruct_Nova_Bounds_Nova_super(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Bounds_Nova_start = 0;
	this->nova_standard_datastruct_Nova_Bounds_Nova_end = 0;
}

