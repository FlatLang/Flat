#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Bounds.h>


nova_standard_datastruct_VTable_Bounds nova_standard_datastruct_VTable_Bounds_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_datastruct_Nova_Bounds_0_Nova_toString,
	nova_standard_datastruct_Nova_Bounds_0_Nova_equals,
};
void nova_standard_datastruct_Nova_BoundsNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Bounds* nova_standard_datastruct_Nova_Bounds_2_Nova_construct(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Bounds, this,);
	this->vtable = &nova_standard_datastruct_VTable_Bounds_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Bounds_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Bounds_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

nova_standard_datastruct_Nova_Bounds* nova_standard_datastruct_Nova_Bounds_3_Nova_construct(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_start, int l0_Nova_end)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Bounds, this,);
	this->vtable = &nova_standard_datastruct_VTable_Bounds_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Bounds_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Bounds_3_Nova_this(this, exceptionData, l0_Nova_start, l0_Nova_end);
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

void nova_standard_datastruct_Nova_Bounds_2_Nova_this(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_datastruct_Nova_Bounds_3_Nova_this(this, exceptionData, 0, 0);
}

void nova_standard_datastruct_Nova_Bounds_3_Nova_this(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_start, int l0_Nova_end)
{
	this->nova_standard_datastruct_Nova_Bounds_Nova_start = l0_Nova_start;
	this->nova_standard_datastruct_Nova_Bounds_Nova_end = l0_Nova_end;
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_extractString(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_source)
{
	if (!nova_standard_datastruct_Nova_Bounds_Nova_isValid(this, exceptionData))
	{
		return (nova_standard_Nova_String*)nova_null;
	}
	return nova_standard_Nova_String_0_Nova_substring(l0_Nova_source, exceptionData, this->nova_standard_datastruct_Nova_Bounds_Nova_start, this->nova_standard_datastruct_Nova_Bounds_Nova_end);
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_extractPreString(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_source)
{
	if (!nova_standard_datastruct_Nova_Bounds_Nova_isValid(this, exceptionData))
	{
		return (nova_standard_Nova_String*)nova_null;
	}
	return nova_standard_Nova_String_0_Nova_substring(l0_Nova_source, exceptionData, 0, this->nova_standard_datastruct_Nova_Bounds_Nova_start);
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_extractPostString(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_source)
{
	if (!nova_standard_datastruct_Nova_Bounds_Nova_isValid(this, exceptionData))
	{
		return l0_Nova_source;
	}
	return nova_standard_Nova_String_1_Nova_substring(l0_Nova_source, exceptionData, this->nova_standard_datastruct_Nova_Bounds_Nova_end);
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_Nova_trimString(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_source)
{
	nova_standard_Nova_String* nova_local_0;
	
	if (!nova_standard_datastruct_Nova_Bounds_Nova_isValid(this, exceptionData))
	{
		return l0_Nova_source;
	}
	nova_local_0 = nova_standard_datastruct_Nova_Bounds_Nova_extractPreString(this, exceptionData, l0_Nova_source);
	return nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_datastruct_Nova_Bounds_Nova_extractPostString(this, exceptionData, l0_Nova_source));
}

char nova_standard_datastruct_Nova_Bounds_Nova_isEndless(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_standard_datastruct_Nova_Bounds_Nova_end < 0;
}

char nova_standard_datastruct_Nova_Bounds_Nova_isOptional(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_Nova_Bounds_Nova_start == 0;
}

int nova_standard_datastruct_Nova_Bounds_Nova_length(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_Nova_Bounds_Nova_end - this->nova_standard_datastruct_Nova_Bounds_Nova_start;
}

char nova_standard_datastruct_Nova_Bounds_Nova_isValid(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->nova_standard_datastruct_Nova_Bounds_Nova_start >= 0 && this->nova_standard_datastruct_Nova_Bounds_Nova_end > 0;
}

void nova_standard_datastruct_Nova_Bounds_Nova_setInvalid(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Bounds_Nova_start = -1;
	this->nova_standard_datastruct_Nova_Bounds_Nova_end = -1;
}

char nova_standard_datastruct_Nova_Bounds_0_Nova_equals(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Bounds* l0_Nova_bounds)
{
	return l0_Nova_bounds != (nova_standard_datastruct_Nova_Bounds*)nova_null && l0_Nova_bounds->nova_standard_datastruct_Nova_Bounds_Nova_start == this->nova_standard_datastruct_Nova_Bounds_Nova_start && l0_Nova_bounds->nova_standard_datastruct_Nova_Bounds_Nova_end == this->nova_standard_datastruct_Nova_Bounds_Nova_end;
}

nova_standard_Nova_String* nova_standard_datastruct_Nova_Bounds_0_Nova_toString(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* nova_local_0;
	nova_standard_Nova_String* nova_local_1;
	
	nova_local_0 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, this->nova_standard_datastruct_Nova_Bounds_Nova_start);
	nova_local_1 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, this->nova_standard_datastruct_Nova_Bounds_Nova_end);
	return nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "["), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, ", "), exceptionData, nova_local_1->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_1, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "]")))));
}

void nova_standard_datastruct_Nova_Bounds_Nova_cloneTo(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Bounds* l0_Nova_bounds)
{
	l0_Nova_bounds->nova_standard_datastruct_Nova_Bounds_Nova_start = this->nova_standard_datastruct_Nova_Bounds_Nova_start;
	l0_Nova_bounds->nova_standard_datastruct_Nova_Bounds_Nova_end = this->nova_standard_datastruct_Nova_Bounds_Nova_end;
}

nova_standard_datastruct_Nova_Bounds* nova_standard_datastruct_Nova_Bounds_Nova_clone(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_datastruct_Nova_Bounds_3_Nova_construct(0, exceptionData, this->nova_standard_datastruct_Nova_Bounds_Nova_start, this->nova_standard_datastruct_Nova_Bounds_Nova_end);
}

void nova_standard_datastruct_Nova_Bounds_Nova_super(nova_standard_datastruct_Nova_Bounds* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Bounds_Nova_start = 0;
	this->nova_standard_datastruct_Nova_Bounds_Nova_end = 0;
}

