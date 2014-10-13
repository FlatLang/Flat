#include <precompiled.h>
#include <example/example_NovaSpider.h>


nova_VTable_example_NovaSpider nova_VTable_example_NovaSpider_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	example_NovaAnimal_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	example_NovaSpider_NovagetNumLegs,
	example_NovaSpider_NovagetNumEyes,
	example_NovaSpider_NovagetDescription,
};
void example_NovaSpiderNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

example_NovaSpider* example_NovaSpider_Nova0_construct(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(example_NovaSpider, this,);
	this->vtable = &nova_VTable_example_NovaSpider_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	example_NovaAnimal_Novasuper((example_NovaAnimal*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	example_NovaAnimal_Novathis((example_NovaAnimal*)(this), exceptionData);
	example_NovaSpider_Novasuper(this, 0);
	
	{
		example_NovaSpider_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Spider(example_NovaSpider** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int example_NovaSpider_NovagetNumLegs(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 8;
}

int example_NovaSpider_NovagetNumEyes(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 9000;
}

nova_standard_NovaString* example_NovaSpider_NovagetDescription(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_Nova1_construct(0, exceptionData, "A disgusting thing (Spider)");
}

void example_NovaSpider_Novathis(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_NovaSpider_Novasuper(example_NovaSpider* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
