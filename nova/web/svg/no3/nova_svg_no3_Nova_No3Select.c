#include <precompiled.h>
#include <nova/svg/no3/nova_svg_no3_Nova_No3Select.h>



nova_svg_no3_Extension_VTable_No3Select nova_svg_no3_Extension_VTable_No3Select_val =
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
	nova_Nova_Object_0_Nova_getHashCodeLong,
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_svg_no3_Nova_No3Select_Nova_toJs,
};


void nova_svg_no3_Nova_No3Select_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_svg_no3_Nova_No3Select* nova_svg_no3_Nova_No3Select_Nova_No3Select(nova_svg_no3_Nova_No3Select* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_svg_no3_Nova_No3Select_Nova_selection)
{
	CCLASS_NEW(nova_svg_no3_Nova_No3Select, this,);
	this->vtable = &nova_svg_no3_Extension_VTable_No3Select_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_svg_no3_Nova_No3Node_Nova_super((nova_svg_no3_Nova_No3Node*)this, exceptionData);
	nova_svg_no3_Nova_No3Select_0_Nova_super(this, exceptionData);
	
	{
		nova_svg_no3_Nova_No3Select_Nova_this(this, exceptionData, nova_svg_no3_Nova_No3Select_Nova_selection);
	}
	
	return this;
}

void nova_svg_no3_Nova_No3Select_Nova_destroy(nova_svg_no3_Nova_No3Select** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_Nova_String_Nova_destroy(&(*this)->nova_svg_no3_Nova_No3Select_Nova_selection, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_svg_no3_Nova_No3Select_Nova_this(nova_svg_no3_Nova_No3Select* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_svg_no3_Nova_No3Select_Nova_selection)
{
	this->nova_svg_no3_Nova_No3Select_Nova_selection = nova_svg_no3_Nova_No3Select_Nova_selection;
}

nova_Nova_String* nova_svg_no3_Nova_No3Select_Nova_toJs(nova_svg_no3_Nova_No3Select* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, "d3.select('"), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((this->nova_svg_no3_Nova_No3Select_Nova_selection)), exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "')")));
}

void nova_svg_no3_Nova_No3Select_0_Nova_super(nova_svg_no3_Nova_No3Select* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_svg_no3_Nova_No3Select_Nova_selection = (nova_Nova_String*)nova_null;
}

