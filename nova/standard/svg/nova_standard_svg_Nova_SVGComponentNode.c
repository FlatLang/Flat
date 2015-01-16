#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_Nova_SVGComponentNode.h>


nova_standard_svg_VTable_SVGComponentNode nova_standard_svg_VTable_SVGComponentNode_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
void nova_standard_svg_Nova_SVGComponentNodeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_svg_Nova_SVGComponentNode* nova_standard_svg_Nova_SVGComponentNode_2_Nova_construct(nova_standard_svg_Nova_SVGComponentNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_svg_Nova_SVGComponentNode, this,);
	this->vtable = &nova_standard_svg_VTable_SVGComponentNode_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_svg_Nova_SVGComponentNode_Nova_super(this, exceptionData);
	
	{
		nova_standard_svg_Nova_SVGComponentNode_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_svg_Nova_SVGComponentNode_Nova_destroy(nova_standard_svg_Nova_SVGComponentNode** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_svg_Nova_SVGComponentNode_Nova_destroy(&(*this)->nova_standard_svg_Nova_SVGComponentNode_Nova_next, exceptionData);
	nova_standard_svg_Nova_SVGComponent_Nova_destroy(&(*this)->nova_standard_svg_Nova_SVGComponentNode_Nova_component, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_svg_Nova_SVGComponentNode_2_Nova_this(nova_standard_svg_Nova_SVGComponentNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_svg_Nova_SVGComponentNode_Nova_super(nova_standard_svg_Nova_SVGComponentNode* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_svg_Nova_SVGComponentNode_Nova_next = (nova_standard_svg_Nova_SVGComponentNode*)nova_null;
	this->nova_standard_svg_Nova_SVGComponentNode_Nova_component = (nova_standard_svg_Nova_SVGComponent*)nova_null;
}

