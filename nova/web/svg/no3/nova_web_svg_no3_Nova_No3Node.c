#include <precompiled.h>
#include <nova/web/svg/no3/nova_web_svg_no3_Nova_No3Node.h>



nova_web_svg_no3_Extension_VTable_No3Node nova_web_svg_no3_Extension_VTable_No3Node_val =
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
	nova_web_svg_no3_Nova_No3Node_virtual1_Nova_toJs,
};


void nova_web_svg_no3_Nova_No3Node_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_web_svg_no3_Nova_No3Node* nova_web_svg_no3_Nova_No3Node_Nova_construct(nova_web_svg_no3_Nova_No3Node* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_web_svg_no3_Nova_No3Node, this,);
	this->vtable = &nova_web_svg_no3_Extension_VTable_No3Node_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_web_svg_no3_Nova_No3Node_Nova_super(this, exceptionData);
	
	{
		nova_web_svg_no3_Nova_No3Node_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_web_svg_no3_Nova_No3Node_Nova_destroy(nova_web_svg_no3_Nova_No3Node** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}


void nova_web_svg_no3_Nova_No3Node_0_Nova_this(nova_web_svg_no3_Nova_No3Node* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_web_svg_no3_Nova_No3Node_Nova_super(nova_web_svg_no3_Nova_No3Node* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

nova_Nova_String* nova_web_svg_no3_Nova_No3Node_virtual1_Nova_toJs(nova_web_svg_no3_Nova_No3Node* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->nova_web_svg_no3_Nova_No3Node_virtual1_Nova_toJs((nova_web_svg_no3_Nova_No3Node*)(this), exceptionData);
}

