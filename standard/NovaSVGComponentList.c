#include <precompiled.h>
#include "NovaSVGComponentList.h"


nova_VTable_SVGComponentList nova_VTable_SVGComponentList_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

SVGComponentList* nova_0_SVGComponentList_construct(SVGComponentList* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(SVGComponentList, this,);
	this->vtable = &nova_VTable_SVGComponentList_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_SVGComponentList_super(this, 0);
	
	{
		nova_SVGComponentList_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_SVGComponentList(SVGComponentList** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_SVGComponentNode(&(*this)->nova_SVGComponentList_start, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_SVGComponentList_generateOutput(SVGComponentList* this, ExceptionData* exceptionData, File* nova_0_file)
{
	SVGComponentNode* nova_1_current;
	
	nova_1_current = this->nova_SVGComponentList_start;
	while (nova_1_current != (SVGComponentNode*)nova_null)
	{
		nova_1_current->nova_SVGComponentNode_component->vtable->nova_virtual_0_generateOutput(nova_1_current->nova_SVGComponentNode_component, exceptionData, nova_0_file);
		nova_1_current = nova_1_current->nova_SVGComponentNode_next;
	}
}

void nova_SVGComponentList_addChild(SVGComponentList* this, ExceptionData* exceptionData, SVGComponent* nova_0_component)
{
	SVGComponentNode* nova_1_node;
	
	nova_1_node = nova_0_SVGComponentNode_construct(0, exceptionData);
	nova_1_node->nova_SVGComponentNode_component = nova_0_component;
	if (this->nova_SVGComponentList_start == (SVGComponentNode*)nova_null)
	{
		this->nova_SVGComponentList_start = nova_1_node;
	}
	else
	{
		SVGComponentNode* nova_3_current;
		
		nova_3_current = this->nova_SVGComponentList_start;
		while (nova_3_current->nova_SVGComponentNode_next != (SVGComponentNode*)nova_null)
		{
			nova_3_current = nova_3_current->nova_SVGComponentNode_next;
		}
		nova_3_current->nova_SVGComponentNode_next = nova_1_node;
	}
}

void nova_SVGComponentList_this(SVGComponentList* this, ExceptionData* exceptionData)
{
}

void nova_SVGComponentList_super(SVGComponentList* this, ExceptionData* exceptionData)
{
	this->nova_SVGComponentList_start = (SVGComponentNode*)nova_null;
}
