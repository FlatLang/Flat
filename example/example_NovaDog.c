#include <precompiled.h>
#include <example/example_NovaDog.h>


nova_VTable_example_NovaDog nova_VTable_example_NovaDog_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	example_NovaAnimal_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	example_NovaDog_Nova0_getNumLegs,
	example_NovaDog_Nova0_getNumEyes,
	example_NovaDog_Nova0_getDescription,
};
void example_NovaDogNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

example_NovaDog* example_NovaDog_Nova0_construct(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(example_NovaDog, this,);
	this->vtable = &nova_VTable_example_NovaDog_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	example_NovaAnimal_Novasuper((example_NovaAnimal*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	example_NovaAnimal_Novathis((example_NovaAnimal*)(this), exceptionData);
	example_NovaDog_Novasuper(this, 0);
	
	{
		example_NovaDog_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Dog(example_NovaDog** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int example_NovaDog_Nova0_getNumLegs(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 4;
}

int example_NovaDog_Nova0_getNumEyes(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 2;
}

nova_standard_NovaString* example_NovaDog_Nova0_getDescription(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_Nova1_construct(0, exceptionData, "A fuzzy dog");
}

void example_NovaDog_Novathis(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_NovaDog_Novasuper(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
