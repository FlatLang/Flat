#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_Nova_SVGComponentList.h>


nova_standard_svg_Extension_VTable_SVGComponentList nova_standard_svg_Extension_VTable_SVGComponentList_val =
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
	},
	nova_standard_Nova_Object_3_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_svg_Nova_SVGComponentListNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_svg_Nova_SVGComponentList* nova_standard_svg_Nova_SVGComponentList_2_Nova_construct(nova_standard_svg_Nova_SVGComponentList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_svg_Nova_SVGComponentList, this,);
	this->vtable = &nova_standard_svg_Extension_VTable_SVGComponentList_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_svg_Nova_SVGComponentList_Nova_super(this, exceptionData);
	
	{
		nova_standard_svg_Nova_SVGComponentList_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_svg_Nova_SVGComponentList_Nova_destroy(nova_standard_svg_Nova_SVGComponentList** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_svg_Nova_SVGComponentNode_Nova_destroy(&(*this)->nova_standard_svg_Nova_SVGComponentList_Nova_start, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_svg_Nova_SVGComponentList_Nova_generateOutput(nova_standard_svg_Nova_SVGComponentList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_file)
{
	nova_standard_svg_Nova_SVGComponentNode* l1_Nova_current;
	
	l1_Nova_current = this->nova_standard_svg_Nova_SVGComponentList_Nova_start;
	while (l1_Nova_current != (nova_standard_svg_Nova_SVGComponentNode*)nova_null)
	{
		l1_Nova_current->nova_standard_svg_Nova_SVGComponentNode_Nova_component->vtable->nova_standard_svg_Nova_SVGComponent_virtual0_Nova_generateOutput(l1_Nova_current->nova_standard_svg_Nova_SVGComponentNode_Nova_component, exceptionData, l0_Nova_file);
		l1_Nova_current = l1_Nova_current->nova_standard_svg_Nova_SVGComponentNode_Nova_next;
	}
}

void nova_standard_svg_Nova_SVGComponentList_Nova_addChild(nova_standard_svg_Nova_SVGComponentList* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_svg_Nova_SVGComponent* l0_Nova_component)
{
	nova_standard_svg_Nova_SVGComponentNode* l1_Nova_node;
	
	l1_Nova_node = nova_standard_svg_Nova_SVGComponentNode_2_Nova_construct(0, exceptionData);
	l1_Nova_node->nova_standard_svg_Nova_SVGComponentNode_Nova_component = l0_Nova_component;
	if (this->nova_standard_svg_Nova_SVGComponentList_Nova_start == (nova_standard_svg_Nova_SVGComponentNode*)nova_null)
	{
		this->nova_standard_svg_Nova_SVGComponentList_Nova_start = l1_Nova_node;
	}
	else
	{
		nova_standard_svg_Nova_SVGComponentNode* l2_Nova_current;
		
		l2_Nova_current = this->nova_standard_svg_Nova_SVGComponentList_Nova_start;
		while (l2_Nova_current->nova_standard_svg_Nova_SVGComponentNode_Nova_next != (nova_standard_svg_Nova_SVGComponentNode*)nova_null)
		{
			l2_Nova_current = l2_Nova_current->nova_standard_svg_Nova_SVGComponentNode_Nova_next;
		}
		l2_Nova_current->nova_standard_svg_Nova_SVGComponentNode_Nova_next = l1_Nova_node;
	}
}

void nova_standard_svg_Nova_SVGComponentList_2_Nova_this(nova_standard_svg_Nova_SVGComponentList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_svg_Nova_SVGComponentList_Nova_super(nova_standard_svg_Nova_SVGComponentList* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_svg_Nova_SVGComponentList_Nova_start = (nova_standard_svg_Nova_SVGComponentNode*)nova_null;
}

