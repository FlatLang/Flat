#include <precompiled.h>
#include <example/example_Nova_PolymorphismDemo.h>

example_Extension_VTable_PolymorphismDemo example_Extension_VTable_PolymorphismDemo_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



nova_standard_Nova_String* example_Nova_PolymorphismDemo_Nova_getDescriptionOfAnimalWithNumberOfLegs(example_Nova_PolymorphismDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Animal** example_Nova_PolymorphismDemo_Nova_animals, int example_Nova_PolymorphismDemo_Nova_numLegs);
void example_Nova_PolymorphismDemoNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_PolymorphismDemo* example_Nova_PolymorphismDemo_0_Nova_construct(example_Nova_PolymorphismDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_PolymorphismDemo, this,);
	this->vtable = &example_Extension_VTable_PolymorphismDemo_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_PolymorphismDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_PolymorphismDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_PolymorphismDemo_Nova_destroy(example_Nova_PolymorphismDemo** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void example_Nova_PolymorphismDemo_Nova_main(example_Nova_PolymorphismDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_PolymorphismDemo_Nova_args)
{
	example_Nova_Animal** l1_Nova_animals;
	example_Nova_Dog* l1_Nova_dog;
	example_Nova_Spider* l1_Nova_spider;
	nova_standard_Nova_String* l1_Nova_description;
	
	l1_Nova_animals = (example_Nova_Animal**)NOVA_MALLOC(sizeof(example_Nova_Animal) * 2);
	l1_Nova_dog = example_Nova_Dog_0_Nova_construct(0, exceptionData);
	l1_Nova_spider = example_Nova_Spider_0_Nova_construct(0, exceptionData);
	l1_Nova_animals[0] = (example_Nova_Animal*)(l1_Nova_dog);
	l1_Nova_animals[1] = (example_Nova_Animal*)(l1_Nova_spider);
	l1_Nova_description = example_Nova_PolymorphismDemo_Nova_getDescriptionOfAnimalWithNumberOfLegs((example_Nova_PolymorphismDemo*)nova_null, exceptionData, l1_Nova_animals, 8);
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Searched for animal with 8 legs and found:"));
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, l1_Nova_description);
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

nova_standard_Nova_String* example_Nova_PolymorphismDemo_Nova_getDescriptionOfAnimalWithNumberOfLegs(example_Nova_PolymorphismDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Animal** example_Nova_PolymorphismDemo_Nova_animals, int example_Nova_PolymorphismDemo_Nova_numLegs)
{
	int l2_Nova_i;
	
	l2_Nova_i = 0;
	for (; l2_Nova_i < 2; l2_Nova_i++)
	{
		if (example_Nova_PolymorphismDemo_Nova_animals[l2_Nova_i]->vtable->example_Nova_Animal_virtual0_Nova_getNumLegs(example_Nova_PolymorphismDemo_Nova_animals[l2_Nova_i], exceptionData) == example_Nova_PolymorphismDemo_Nova_numLegs)
		{
			return example_Nova_PolymorphismDemo_Nova_animals[l2_Nova_i]->vtable->example_Nova_Animal_virtual0_Nova_getDescription(example_Nova_PolymorphismDemo_Nova_animals[l2_Nova_i], exceptionData);
		}
	}
	return (nova_standard_Nova_String*)nova_null;
}

void example_Nova_PolymorphismDemo_0_Nova_this(example_Nova_PolymorphismDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_PolymorphismDemo_Nova_super(example_Nova_PolymorphismDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

