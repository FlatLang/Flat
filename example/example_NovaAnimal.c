#include <precompiled.h>
#include <example/example_NovaAnimal.h>


nova_VTable_example_NovaAnimal nova_VTable_example_NovaAnimal_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	example_NovaAnimal_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	example_NovaAnimal_Nova0_getNumLegs,
	example_NovaAnimal_Nova0_getNumEyes,
	example_NovaAnimal_Nova0_getDescription,
};
void example_NovaAnimalNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

example_NovaAnimal* example_NovaAnimal_Nova0_construct(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(example_NovaAnimal, this,);
	this->vtable = &nova_VTable_example_NovaAnimal_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	example_NovaAnimal_Novasuper(this, 0);
	
	{
		example_NovaAnimal_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Animal(example_NovaAnimal** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int example_NovaAnimal_Nova0_getNumLegs(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 0;
}

int example_NovaAnimal_Nova0_getNumEyes(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 0;
}

nova_standard_NovaString* example_NovaAnimal_Nova0_getDescription(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_Nova1_construct(0, exceptionData, "Its just a stupid animal...");
}

nova_standard_NovaString* example_NovaAnimal_Nova0_toString(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->vtable->example_NovaAnimal_Novavirtual0_getDescription(this, exceptionData);
}

void example_NovaAnimal_Novathis(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_NovaAnimal_Novasuper(example_NovaAnimal* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
