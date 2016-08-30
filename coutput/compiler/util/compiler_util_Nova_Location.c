#include <precompiled.h>
#include <compiler/util/compiler_util_Nova_Location.h>



compiler_util_Extension_VTable_Location compiler_util_Extension_VTable_Location_val =
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
		0,
		0,
	},
	compiler_util_Nova_Location_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


compiler_util_Nova_Location* compiler_util_Nova_Location_Nova_INVALID;
void compiler_util_Nova_Location_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
		compiler_util_Nova_Location_Nova_INVALID = compiler_util_Nova_Location_2_Nova_construct(0, exceptionData, 0, 0, 0, 0);
	}
}

compiler_util_Nova_Location* compiler_util_Nova_Location_0_Nova_construct(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_util_Nova_Location, this,);
	this->vtable = &compiler_util_Extension_VTable_Location_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_util_Nova_Location_Nova_super(this, exceptionData);
	
	{
		compiler_util_Nova_Location_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

compiler_util_Nova_Location* compiler_util_Nova_Location_1_Nova_construct(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, compiler_util_Nova_Location* compiler_util_Nova_Location_Nova_loc)
{
	CCLASS_NEW(compiler_util_Nova_Location, this,);
	this->vtable = &compiler_util_Extension_VTable_Location_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_util_Nova_Location_Nova_super(this, exceptionData);
	
	{
		compiler_util_Nova_Location_1_Nova_this(this, exceptionData, compiler_util_Nova_Location_Nova_loc);
	}
	
	return this;
}

compiler_util_Nova_Location* compiler_util_Nova_Location_2_Nova_construct(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_lineNumber, int compiler_util_Nova_Location_Nova_offset, int compiler_util_Nova_Location_Nova_start, int compiler_util_Nova_Location_Nova_end)
{
	CCLASS_NEW(compiler_util_Nova_Location, this,);
	this->vtable = &compiler_util_Extension_VTable_Location_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_util_Nova_Location_Nova_super(this, exceptionData);
	
	{
		compiler_util_Nova_Location_2_Nova_this(this, exceptionData, compiler_util_Nova_Location_Nova_lineNumber, compiler_util_Nova_Location_Nova_offset, compiler_util_Nova_Location_Nova_start, compiler_util_Nova_Location_Nova_end);
	}
	
	return this;
}

void compiler_util_Nova_Location_Nova_destroy(compiler_util_Nova_Location** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	nova_datastruct_Nova_Bounds_Nova_destroy(&(*this)->compiler_util_Nova_Location_Nova_bounds, exceptionData);
	
	NOVA_FREE(*this);
}

void compiler_util_Nova_Location_0_Nova_this(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->compiler_util_Nova_Location_Nova_bounds = nova_datastruct_Nova_Bounds_1_Nova_construct(0, exceptionData, 0, 0);
}

void compiler_util_Nova_Location_1_Nova_this(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, compiler_util_Nova_Location* compiler_util_Nova_Location_Nova_loc)
{
	compiler_util_Nova_Location_2_Nova_this(this, exceptionData, 0, compiler_util_Nova_Location_Nova_loc->compiler_util_Nova_Location_Nova_offset, compiler_util_Nova_Location_Nova_loc->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_start, compiler_util_Nova_Location_Nova_loc->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_end);
}

void compiler_util_Nova_Location_2_Nova_this(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_lineNumber, int compiler_util_Nova_Location_Nova_offset, int compiler_util_Nova_Location_Nova_start, int compiler_util_Nova_Location_Nova_end)
{
	compiler_util_Nova_Location_0_Nova_this(this, exceptionData);
	this->compiler_util_Nova_Location_Nova_offset = compiler_util_Nova_Location_Nova_offset;
	this->compiler_util_Nova_Location_Nova_lineNumber = compiler_util_Nova_Location_Nova_lineNumber;
	compiler_util_Nova_Location_1_Nova_setBounds(this, exceptionData, compiler_util_Nova_Location_Nova_start, compiler_util_Nova_Location_Nova_end);
}

void compiler_util_Nova_Location_Nova_setLineNumber(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_lineNumber)
{
	this->compiler_util_Nova_Location_Nova_lineNumber = compiler_util_Nova_Location_Nova_lineNumber;
}

int compiler_util_Nova_Location_Nova_getStart(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_start;
}

int compiler_util_Nova_Location_Nova_getEnd(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_end;
}

void compiler_util_Nova_Location_Nova_setOffset(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_offset)
{
	this->compiler_util_Nova_Location_Nova_offset = compiler_util_Nova_Location_Nova_offset;
}

void compiler_util_Nova_Location_Nova_addOffset(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_amount)
{
	this->compiler_util_Nova_Location_Nova_offset += compiler_util_Nova_Location_Nova_amount;
}

void compiler_util_Nova_Location_Nova_subtractOffset(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_amount)
{
	this->compiler_util_Nova_Location_Nova_offset -= compiler_util_Nova_Location_Nova_amount;
}

void compiler_util_Nova_Location_0_Nova_setBounds(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Bounds* compiler_util_Nova_Location_Nova_bounds)
{
	this->compiler_util_Nova_Location_Nova_bounds = compiler_util_Nova_Location_Nova_bounds;
}

void compiler_util_Nova_Location_1_Nova_setBounds(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_start, int compiler_util_Nova_Location_Nova_end)
{
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_start = compiler_util_Nova_Location_Nova_start;
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_end = compiler_util_Nova_Location_Nova_end;
}

void compiler_util_Nova_Location_0_Nova_addBounds(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_amount)
{
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_start += compiler_util_Nova_Location_Nova_amount;
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_end += compiler_util_Nova_Location_Nova_amount;
}

void compiler_util_Nova_Location_Nova_moveBounds(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_startAmount, int compiler_util_Nova_Location_Nova_endAmount)
{
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_start += compiler_util_Nova_Location_Nova_startAmount;
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_end += compiler_util_Nova_Location_Nova_endAmount;
}

void compiler_util_Nova_Location_0_Nova_subtractBounds(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_startAmount, int compiler_util_Nova_Location_Nova_endAmount)
{
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_start -= compiler_util_Nova_Location_Nova_startAmount;
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_end -= compiler_util_Nova_Location_Nova_endAmount;
}

void compiler_util_Nova_Location_1_Nova_subtractBounds(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_amount)
{
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_start -= compiler_util_Nova_Location_Nova_amount;
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_end -= compiler_util_Nova_Location_Nova_amount;
}

void compiler_util_Nova_Location_1_Nova_addBounds(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData, int compiler_util_Nova_Location_Nova_startAmount, int compiler_util_Nova_Location_Nova_endAmount)
{
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_start += compiler_util_Nova_Location_Nova_startAmount;
	this->compiler_util_Nova_Location_Nova_bounds->nova_datastruct_Nova_Bounds_Nova_end += compiler_util_Nova_Location_Nova_endAmount;
}

char compiler_util_Nova_Location_Nova_isValid(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (char)this->compiler_util_Nova_Location_Nova_lineNumber > 0;
}

compiler_util_Nova_Location* compiler_util_Nova_Location_Nova_asNew(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return compiler_util_Nova_Location_1_Nova_construct(0, exceptionData, this);
}

nova_Nova_String* compiler_util_Nova_Location_0_Nova_toString(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Line ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (this->compiler_util_Nova_Location_Nova_lineNumber))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(" ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((this->compiler_util_Nova_Location_Nova_bounds)), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""))))));
}

void compiler_util_Nova_Location_Nova_super(compiler_util_Nova_Location* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->compiler_util_Nova_Location_Nova_lineNumber = 0;
	this->compiler_util_Nova_Location_Nova_offset = 0;
	this->compiler_util_Nova_Location_Nova_bounds = (nova_datastruct_Nova_Bounds*)nova_null;
}

