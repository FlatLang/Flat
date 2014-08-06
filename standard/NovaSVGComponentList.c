#include <precompiled.h>
#include "NovaSVGComponentList.h"


nova_VTable_SVGComponentList nova_VTable_SVGComponentList_val =
{
	nova_4_Object_toString,
	nova_2_Object_equals,
};

SVGComponentList* nova_SVGComponentList_construct(SVGComponentList* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(SVGComponentList, this,);
	
	this->nova_SVGComponentList_start = (SVGComponentNode*)0;
	this->vtable = &nova_VTable_SVGComponentList_val;
	{
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
	while (nova_1_current != (SVGComponentNode*)0)
	{
		SVGComponent* nova_2_component;
		SVGCircle* nova_2_c;
		
		nova_2_component = nova_1_current->nova_SVGComponentNode_component;
		nova_2_c = (SVGCircle*)nova_2_component;
		nova_static_1_Console_writeLine(0, exceptionData, nova_String_concat(nova_2_Double_toString(nova_Double_construct(0, exceptionData, nova_2_c->nova_SVGCircle_x), exceptionData), exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, ", "), exceptionData, nova_2_Double_toString(nova_Double_construct(0, exceptionData, nova_2_c->nova_SVGCircle_y), exceptionData))));
		nova_2_component->vtable->nova_virtual_2_generateOutput(nova_2_component, exceptionData, nova_0_file);
		nova_1_current = nova_1_current->nova_SVGComponentNode_next;
	}
}

void nova_SVGComponentList_addChild(SVGComponentList* this, ExceptionData* exceptionData, SVGComponent* nova_0_component)
{
	SVGComponentNode* nova_1_node;
	
	nova_1_node = nova_SVGComponentNode_construct(0, exceptionData);
	nova_1_node->nova_SVGComponentNode_component = nova_0_component;
	if (this->nova_SVGComponentList_start == (SVGComponentNode*)0)
	{
		this->nova_SVGComponentList_start = nova_1_node;
	}
	else
	{
		SVGComponentNode* nova_3_current;
		
		nova_3_current = this->nova_SVGComponentList_start;
		while (nova_3_current->nova_SVGComponentNode_next != (SVGComponentNode*)0)
		{
			nova_3_current = nova_3_current->nova_SVGComponentNode_next;
		}
		nova_3_current->nova_SVGComponentNode_next = nova_1_node;
	}
}
