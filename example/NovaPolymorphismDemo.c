#include <precompiled.h>
#include "NovaPolymorphismDemo.h"


nova_VTable_PolymorphismDemo nova_VTable_PolymorphismDemo_val =
{
		nova_4_Object_toString,
		nova_2_Object_equals,
};

String* nova_static_PolymorphismDemo_getDescriptionOfAnimalWithNumberOfLegs(PolymorphismDemo* this, ExceptionData* exceptionData, Animal** nova_0_animals, int nova_0_numLegs);

PolymorphismDemo* nova_PolymorphismDemo_PolymorphismDemo(PolymorphismDemo* this, ExceptionData* exceptionData)
{
		CCLASS_NEW(PolymorphismDemo, this,);
		
		this->vtable = &nova_VTable_PolymorphismDemo_val;
		{
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
		nova_1_dog = nova_Dog_Dog(0, exceptionData);
		nova_1_spider = nova_Spider_Spider(0, exceptionData);
		nova_1_animals[0] = (Animal*)(nova_1_dog);
		nova_1_animals[1] = (Animal*)(nova_1_spider);
		nova_1_description = nova_static_PolymorphismDemo_getDescriptionOfAnimalWithNumberOfLegs((PolymorphismDemo*)0, exceptionData, nova_1_animals, 8);
		nova_static_1_Console_writeLine(0, exceptionData, nova_String_String(0, exceptionData, (char*)("Searched for animal with 8 legs and found:")));
		nova_static_1_Console_writeLine(0, exceptionData, nova_1_description);
		nova_static_Console_waitForEnter(0, exceptionData);
}

String* nova_static_PolymorphismDemo_getDescriptionOfAnimalWithNumberOfLegs(PolymorphismDemo* this, ExceptionData* exceptionData, Animal** nova_0_animals, int nova_0_numLegs)
{
		int nova_1_i;
		
		nova_1_i = 0;
		for (; nova_1_i < 2; nova_1_i++)
		{
				if (nova_0_animals[nova_1_i]->vtable->nova_virtual_2_getNumLegs(nova_0_animals[nova_1_i], exceptionData) == nova_0_numLegs)
				{
						return nova_0_animals[nova_1_i]->vtable->nova_virtual_2_getDescription(nova_0_animals[nova_1_i], exceptionData);
				}
		}
}



int main(int argc, char** argvs)
{
		String** args;
		int      i;
		
		ExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_static_GC_init(0, exceptionData);
		
		args = (String**)NOVA_MALLOC(argc * sizeof(String));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_String_String(0, 0, str);
		}
		
		TRY
		{
				nova_static_PolymorphismDemo_main(0, exceptionData, args);
		}
		CATCH (1)
		{
				printf("You broke it.");
				nova_static_Console_waitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		GC_gcollect();
		
		return 0;
}