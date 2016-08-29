#include <precompiled.h>
#include <nova/svg/nova_svg_Nova_SvgComponentNode.h>



nova_svg_Extension_VTable_SvgComponentNode nova_svg_Extension_VTable_SvgComponentNode_val =
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


void nova_svg_Nova_SvgComponentNode_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_svg_Nova_SvgComponentNode* nova_svg_Nova_SvgComponentNode_Nova_construct(nova_svg_Nova_SvgComponentNode* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_svg_Nova_SvgComponentNode, this,);
	this->vtable = &nova_svg_Extension_VTable_SvgComponentNode_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_svg_Nova_SvgComponentNode_Nova_super(this, exceptionData);
	
	{
		nova_svg_Nova_SvgComponentNode_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_svg_Nova_SvgComponentNode_Nova_destroy(nova_svg_Nova_SvgComponentNode** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_svg_Nova_SvgComponentNode_Nova_destroy(&(*this)->nova_svg_Nova_SvgComponentNode_Nova_next, exceptionData);
	nova_svg_Nova_SvgComponent_Nova_destroy(&(*this)->nova_svg_Nova_SvgComponentNode_Nova_component, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_svg_Nova_SvgComponentNode_0_Nova_this(nova_svg_Nova_SvgComponentNode* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_svg_Nova_SvgComponentNode_Nova_super(nova_svg_Nova_SvgComponentNode* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_svg_Nova_SvgComponentNode_Nova_next = (nova_svg_Nova_SvgComponentNode*)nova_null;
	this->nova_svg_Nova_SvgComponentNode_Nova_component = (nova_svg_Nova_SvgComponent*)nova_null;
}

