#include <precompiled.h>
#include <nova/web/svg/nova_web_svg_Nova_SvgComponentList.h>



nova_web_svg_Extension_VTable_SvgComponentList nova_web_svg_Extension_VTable_SvgComponentList_val =
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


void nova_web_svg_Nova_SvgComponentList_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_web_svg_Nova_SvgComponentList* nova_web_svg_Nova_SvgComponentList_Nova_construct(nova_web_svg_Nova_SvgComponentList* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_web_svg_Nova_SvgComponentList, this,);
	this->vtable = &nova_web_svg_Extension_VTable_SvgComponentList_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_web_svg_Nova_SvgComponentList_Nova_super(this, exceptionData);
	
	{
		nova_web_svg_Nova_SvgComponentList_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_web_svg_Nova_SvgComponentList_Nova_destroy(nova_web_svg_Nova_SvgComponentList** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_web_svg_Nova_SvgComponentNode_Nova_destroy(&(*this)->nova_web_svg_Nova_SvgComponentList_Nova_start, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_web_svg_Nova_SvgComponentList_Nova_generateOutput(nova_web_svg_Nova_SvgComponentList* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_File* nova_web_svg_Nova_SvgComponentList_Nova_file)
{
	nova_web_svg_Nova_SvgComponentNode* l1_Nova_current = (nova_web_svg_Nova_SvgComponentNode*)nova_null;
	
	l1_Nova_current = this->nova_web_svg_Nova_SvgComponentList_Nova_start;
	while (l1_Nova_current != (nova_web_svg_Nova_SvgComponentNode*)nova_null)
	{
		nova_web_svg_Nova_SvgComponent_virtual0_Nova_generateOutput((nova_web_svg_Nova_SvgComponent*)(l1_Nova_current->nova_web_svg_Nova_SvgComponentNode_Nova_component), exceptionData, nova_web_svg_Nova_SvgComponentList_Nova_file);
		l1_Nova_current = l1_Nova_current->nova_web_svg_Nova_SvgComponentNode_Nova_next;
	}
}

void nova_web_svg_Nova_SvgComponentList_Nova_addChild(nova_web_svg_Nova_SvgComponentList* this, nova_exception_Nova_ExceptionData* exceptionData, nova_web_svg_Nova_SvgComponent* nova_web_svg_Nova_SvgComponentList_Nova_component)
{
	nova_web_svg_Nova_SvgComponentNode* l1_Nova_node = (nova_web_svg_Nova_SvgComponentNode*)nova_null;
	
	l1_Nova_node = nova_web_svg_Nova_SvgComponentNode_Nova_construct(0, exceptionData);
	l1_Nova_node->nova_web_svg_Nova_SvgComponentNode_Nova_component = nova_web_svg_Nova_SvgComponentList_Nova_component;
	if (this->nova_web_svg_Nova_SvgComponentList_Nova_start == (nova_web_svg_Nova_SvgComponentNode*)nova_null)
	{
		this->nova_web_svg_Nova_SvgComponentList_Nova_start = l1_Nova_node;
	}
	else
	{
		nova_web_svg_Nova_SvgComponentNode* l2_Nova_current = (nova_web_svg_Nova_SvgComponentNode*)nova_null;
		
		l2_Nova_current = this->nova_web_svg_Nova_SvgComponentList_Nova_start;
		while (l2_Nova_current->nova_web_svg_Nova_SvgComponentNode_Nova_next != (nova_web_svg_Nova_SvgComponentNode*)nova_null)
		{
			l2_Nova_current = l2_Nova_current->nova_web_svg_Nova_SvgComponentNode_Nova_next;
		}
		l2_Nova_current->nova_web_svg_Nova_SvgComponentNode_Nova_next = l1_Nova_node;
	}
}

void nova_web_svg_Nova_SvgComponentList_0_Nova_this(nova_web_svg_Nova_SvgComponentList* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_web_svg_Nova_SvgComponentList_Nova_super(nova_web_svg_Nova_SvgComponentList* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_web_svg_Nova_SvgComponentList_Nova_start = (nova_web_svg_Nova_SvgComponentNode*)nova_null;
}

