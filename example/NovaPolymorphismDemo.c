#include <precompiled.h>
#include "NovaPolymorphismDemo.h"


nova_VTable_PolymorphismDemo nova_VTable_PolymorphismDemo_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

String* nova_static_PolymorphismDemo_getDescriptionOfAnimalWithNumberOfLegs(PolymorphismDemo* this, ExceptionData* exceptionData, Animal** nova_0_animals, int nova_0_numLegs);

PolymorphismDemo* nova_PolymorphismDemo_construct(PolymorphismDemo* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(PolymorphismDemo, this,);
	this->vtable = &nova_VTable_PolymorphismDemo_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_PolymorphismDemo_super(this, 0);
	
	{
		nova_PolymorphismDemo_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_PolymorphismDemo(PolymorphismDemo** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_PolymorphismDemo_main(PolymorphismDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
	Animal** nova_1_animals;
	Dog* nova_1_dog;
	Spider* nova_1_spider;
	String* nova_1_description;
	
	nova_1_animals = (Animal**)NOVA_MALLOC(sizeof(Animal) * (2));
	nova_1_dog = nova_0_Dog_construct(0, exceptionData);
	nova_1_spider = nova_Spider_construct(0, exceptionData);
	nova_1_animals[0] = (Animal*)(nova_1_dog);
	nova_1_animals[1] = (Animal*)(nova_1_spider);
	nova_1_description = nova_static_PolymorphismDemo_getDescriptionOfAnimalWithNumberOfLegs((PolymorphismDemo*)0, exceptionData, nova_1_animals, 8);
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Searched for animal with 8 legs and found:"));
	nova_static_0_Console_writeLine(0, exceptionData, nova_1_description);
	nova_static_Console_waitForEnter(0, exceptionData);
}

String* nova_static_PolymorphismDemo_getDescriptionOfAnimalWithNumberOfLegs(PolymorphismDemo* this, ExceptionData* exceptionData, Animal** nova_0_animals, int nova_0_numLegs)
{
	int nova_1_i;
	
	nova_1_i = 0;
	for (; nova_1_i < 2; nova_1_i++)
	{
		if (nova_0_animals[nova_1_i]->vtable->nova_virtual_0_getNumLegs(nova_0_animals[nova_1_i], exceptionData) == nova_0_numLegs)
		{
			return nova_0_animals[nova_1_i]->vtable->nova_virtual_0_getDescription(nova_0_animals[nova_1_i], exceptionData);
		}
	}
}

void nova_PolymorphismDemo_this(PolymorphismDemo* this, ExceptionData* exceptionData)
{
}

void nova_PolymorphismDemo_super(PolymorphismDemo* this, ExceptionData* exceptionData)
{
}
