#include <precompiled.h>
#include <nova/svg/no3/nova_svg_no3_Nova_No3.h>



nova_svg_no3_Extension_VTable_No3 nova_svg_no3_Extension_VTable_No3_val =
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
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void nova_svg_no3_Nova_No3_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_svg_no3_Nova_No3* nova_svg_no3_Nova_No3_Nova_construct(nova_svg_no3_Nova_No3* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_svg_no3_Nova_No3, this,);
	this->vtable = &nova_svg_no3_Extension_VTable_No3_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_svg_no3_Nova_No3_Nova_super(this, exceptionData);
	
	{
		nova_svg_no3_Nova_No3_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_svg_no3_Nova_No3_Nova_destroy(nova_svg_no3_Nova_No3** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_svg_no3_Nova_No3Selection* nova_svg_no3_Nova_No3_Nova_select(nova_svg_no3_Nova_No3* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_svg_no3_Nova_No3_Nova_selection)
{
	return nova_svg_no3_Nova_No3Selection_Nova_construct(0, exceptionData, nova_svg_no3_Nova_No3_Nova_selection);
}

void nova_svg_no3_Nova_No3_0_Nova_this(nova_svg_no3_Nova_No3* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_svg_no3_Nova_No3_Nova_super(nova_svg_no3_Nova_No3* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

