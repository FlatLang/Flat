#include <precompiled.h>
#include <example/example_NovaAnimal.h>


nova_VTable_example_NovaAnimal nova_VTable_example_NovaAnimal_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	example_NovaAnimal_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
	example_NovaAnimal_0_NovagetNumLegs,
	example_NovaAnimal_0_NovagetNumEyes,
	example_NovaAnimal_0_NovagetDescription,
};
void example_NovaAnimalNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

example_NovaAnimal* example_NovaAnimal_0_Novaconstruct(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(example_NovaAnimal, this,);
	this->vtable = &nova_VTable_example_NovaAnimal_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	example_NovaAnimal_Novasuper(this, exceptionData);
	
	{
		example_NovaAnimal_Novathis(this, exceptionData);
	}
	
	return this;
}

void example_NovaAnimal_Novadestroy(example_NovaAnimal** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int example_NovaAnimal_0_NovagetNumLegs(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 0;
}

int example_NovaAnimal_0_NovagetNumEyes(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 0;
}

nova_standard_NovaString* example_NovaAnimal_0_NovagetDescription(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Its just a stupid animal...");
}

nova_standard_NovaString* example_NovaAnimal_0_NovatoString(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->vtable->example_NovaAnimal_virtual0_NovagetDescription(this, exceptionData);
}

void example_NovaAnimal_Novathis(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_NovaAnimal_Novasuper(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
