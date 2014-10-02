#include <precompiled.h>
#include <nova/standard/svg/nova_standard_svg_NovaSVGComponentList.h>


nova_VTable_nova_standard_svg_NovaSVGComponentList nova_VTable_nova_standard_svg_NovaSVGComponentList_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_svg_NovaSVGComponentListNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_svg_NovaSVGComponentList* nova_standard_svg_NovaSVGComponentList_Nova0_construct(nova_standard_svg_NovaSVGComponentList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_svg_NovaSVGComponentList, this,);
	this->vtable = &nova_VTable_nova_standard_svg_NovaSVGComponentList_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_svg_NovaSVGComponentList_Novasuper(this, 0);
	
	{
		nova_standard_svg_NovaSVGComponentList_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_SVGComponentList(nova_standard_svg_NovaSVGComponentList** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_SVGComponentNode(&(*this)->nova_standard_svg_NovaSVGComponentList_Novastart, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_svg_NovaSVGComponentList_NovagenerateOutput(nova_standard_svg_NovaSVGComponentList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaFile* l0_Novafile)
{
	nova_standard_svg_NovaSVGComponentNode* l1_Novacurrent;
	
	l1_Novacurrent = this->nova_standard_svg_NovaSVGComponentList_Novastart;
	while (l1_Novacurrent != (nova_standard_svg_NovaSVGComponentNode*)nova_null)
	{
		l1_Novacurrent->nova_standard_svg_NovaSVGComponentNode_Novacomponent->vtable->nova_standard_svg_NovaSVGComponent_Novavirtual0_generateOutput(l1_Novacurrent->nova_standard_svg_NovaSVGComponentNode_Novacomponent, exceptionData, l0_Novafile);
		l1_Novacurrent = l1_Novacurrent->nova_standard_svg_NovaSVGComponentNode_Novanext;
	}
}

void nova_standard_svg_NovaSVGComponentList_NovaaddChild(nova_standard_svg_NovaSVGComponentList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_svg_NovaSVGComponent* l0_Novacomponent)
{
	nova_standard_svg_NovaSVGComponentNode* l1_Novanode;
	
	l1_Novanode = nova_standard_svg_NovaSVGComponentNode_Nova0_construct(0, exceptionData);
	l1_Novanode->nova_standard_svg_NovaSVGComponentNode_Novacomponent = l0_Novacomponent;
	if (this->nova_standard_svg_NovaSVGComponentList_Novastart == (nova_standard_svg_NovaSVGComponentNode*)nova_null)
	{
		this->nova_standard_svg_NovaSVGComponentList_Novastart = l1_Novanode;
	}
	else
	{
		nova_standard_svg_NovaSVGComponentNode* l3_Novacurrent;
		
		l3_Novacurrent = this->nova_standard_svg_NovaSVGComponentList_Novastart;
		while (l3_Novacurrent->nova_standard_svg_NovaSVGComponentNode_Novanext != (nova_standard_svg_NovaSVGComponentNode*)nova_null)
		{
			l3_Novacurrent = l3_Novacurrent->nova_standard_svg_NovaSVGComponentNode_Novanext;
		}
		l3_Novacurrent->nova_standard_svg_NovaSVGComponentNode_Novanext = l1_Novanode;
	}
}

void nova_standard_svg_NovaSVGComponentList_Novathis(nova_standard_svg_NovaSVGComponentList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_svg_NovaSVGComponentList_Novasuper(nova_standard_svg_NovaSVGComponentList* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_svg_NovaSVGComponentList_Novastart = (nova_standard_svg_NovaSVGComponentNode*)nova_null;
}
