#include <precompiled.h>
#include <nova/web/svg/no3/nova_web_svg_no3_Nova_No3SelectAll.h>



nova_web_svg_no3_Extension_VTable_No3SelectAll nova_web_svg_no3_Extension_VTable_No3SelectAll_val =
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
	nova_web_svg_no3_Nova_No3SelectAll_0_Nova_toJs,
};


void nova_web_svg_no3_Nova_No3SelectAll_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_web_svg_no3_Nova_No3SelectAll* nova_web_svg_no3_Nova_No3SelectAll_Nova_construct(nova_web_svg_no3_Nova_No3SelectAll* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_web_svg_no3_Nova_No3SelectAll_Nova_selection)
{
	CCLASS_NEW(nova_web_svg_no3_Nova_No3SelectAll, this,);
	this->vtable = &nova_web_svg_no3_Extension_VTable_No3SelectAll_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_web_svg_no3_Nova_No3Node_Nova_super((nova_web_svg_no3_Nova_No3Node*)this, exceptionData);
	nova_web_svg_no3_Nova_No3SelectAll_0_Nova_super(this, exceptionData);
	
	{
		nova_web_svg_no3_Nova_No3SelectAll_Nova_this(this, exceptionData, nova_web_svg_no3_Nova_No3SelectAll_Nova_selection);
	}
	
	return this;
}

void nova_web_svg_no3_Nova_No3SelectAll_Nova_destroy(nova_web_svg_no3_Nova_No3SelectAll** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_Nova_String_Nova_destroy(&(*this)->nova_web_svg_no3_Nova_No3SelectAll_Nova_selection, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_web_svg_no3_Nova_No3SelectAll_Nova_this(nova_web_svg_no3_Nova_No3SelectAll* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_web_svg_no3_Nova_No3SelectAll_Nova_selection)
{
	this->nova_web_svg_no3_Nova_No3SelectAll_Nova_selection = nova_web_svg_no3_Nova_No3SelectAll_Nova_selection;
}

nova_Nova_String* nova_web_svg_no3_Nova_No3SelectAll_0_Nova_toJs(nova_web_svg_no3_Nova_No3SelectAll* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("d3.selectAll('")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((this->nova_web_svg_no3_Nova_No3SelectAll_Nova_selection)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("')"))));
}

void nova_web_svg_no3_Nova_No3SelectAll_0_Nova_super(nova_web_svg_no3_Nova_No3SelectAll* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_web_svg_no3_Nova_No3SelectAll_Nova_selection = (nova_Nova_String*)nova_null;
}

