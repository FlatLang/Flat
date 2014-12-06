#include <precompiled.h>
#include <example/example_NovaDog.h>


nova_VTable_example_NovaDog nova_VTable_example_NovaDog_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	example_NovaAnimal_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
	example_NovaDog_NovagetNumLegs,
	example_NovaDog_NovagetNumEyes,
	example_NovaDog_NovagetDescription,
};
void example_NovaDogNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

example_NovaDog* example_NovaDog_0_Novaconstruct(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(example_NovaDog, this,);
	this->vtable = &nova_VTable_example_NovaDog_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	example_NovaAnimal_Novasuper((example_NovaAnimal*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	example_NovaAnimal_Novathis((example_NovaAnimal*)(this), exceptionData);
	example_NovaDog_Novasuper(this, exceptionData);
	
	{
		example_NovaDog_Novathis(this, exceptionData);
	}
	
	return this;
}

void example_NovaDog_Novadestroy(example_NovaDog** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void example_NovaDog_Novathis(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Regular dog"));
}

int example_NovaDog_NovagetNumLegs(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 4;
}

int example_NovaDog_NovagetNumEyes(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 2;
}

nova_standard_NovaString* example_NovaDog_NovagetDescription(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "A fuzzy dog");
}

void example_NovaDog_Novasuper(example_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
