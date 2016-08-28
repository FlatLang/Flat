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
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};



nova_Nova_String* example_Nova_PolymorphismDemo_Nova_getDescriptionOfAnimalWithNumberOfLegs(example_Nova_PolymorphismDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_PolymorphismDemo_Nova_animals, int example_Nova_PolymorphismDemo_Nova_numLegs);
void example_Nova_PolymorphismDemo_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_PolymorphismDemo* example_Nova_PolymorphismDemo_Nova_PolymorphismDemo(example_Nova_PolymorphismDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_PolymorphismDemo, this,);
	this->vtable = &example_Extension_VTable_PolymorphismDemo_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_PolymorphismDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_PolymorphismDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_PolymorphismDemo_Nova_destroy(example_Nova_PolymorphismDemo** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_PolymorphismDemo_Nova_main(example_Nova_PolymorphismDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_PolymorphismDemo_Nova_args)
{
	nova_datastruct_list_Nova_Array* l1_Nova_animals = (nova_datastruct_list_Nova_Array*)nova_null;
	example_Nova_Dog* l1_Nova_dog = (example_Nova_Dog*)nova_null;
	example_Nova_Spider* l1_Nova_spider = (example_Nova_Spider*)nova_null;
	nova_Nova_String* l1_Nova_description = (nova_Nova_String*)nova_null;
	
	l1_Nova_animals = nova_datastruct_list_Nova_Array_1_Nova_Array(0, exceptionData, 2);
	l1_Nova_dog = example_Nova_Dog_Nova_Dog(0, exceptionData);
	l1_Nova_spider = example_Nova_Spider_Nova_Spider(0, exceptionData);
	nova_datastruct_list_Nova_Array_Nova_set((nova_datastruct_list_Nova_Array*)(l1_Nova_animals), exceptionData, 0, (nova_Nova_Object*)(l1_Nova_dog));
	nova_datastruct_list_Nova_Array_Nova_set((nova_datastruct_list_Nova_Array*)(l1_Nova_animals), exceptionData, 1, (nova_Nova_Object*)(l1_Nova_spider));
	l1_Nova_description = example_Nova_PolymorphismDemo_Nova_getDescriptionOfAnimalWithNumberOfLegs(0, exceptionData, l1_Nova_animals, 8);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Searched for animal with 8 legs and found: ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)((l1_Nova_description)), exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("")))));
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

nova_Nova_String* example_Nova_PolymorphismDemo_Nova_getDescriptionOfAnimalWithNumberOfLegs(example_Nova_PolymorphismDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_PolymorphismDemo_Nova_animals, int example_Nova_PolymorphismDemo_Nova_numLegs)
{
	int l2_Nova_i = 0;
	
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)2; l2_Nova_i++)
	{
		if (example_Nova_Animal_virtual1_Nova_getNumLegs((example_Nova_Animal*)(example_Nova_PolymorphismDemo_Nova_animals->nova_datastruct_list_Nova_Array_Nova_data[l2_Nova_i]), exceptionData) == example_Nova_PolymorphismDemo_Nova_numLegs)
		{
			return example_Nova_Animal_virtual1_Nova_getDescription((example_Nova_Animal*)(example_Nova_PolymorphismDemo_Nova_animals->nova_datastruct_list_Nova_Array_Nova_data[l2_Nova_i]), exceptionData);
		}
	}
	return nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, (char*)("Could not find animal with ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (example_Nova_PolymorphismDemo_Nova_numLegs))), exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, (char*)(" legs"))));
}

void example_Nova_PolymorphismDemo_0_Nova_this(example_Nova_PolymorphismDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_PolymorphismDemo_Nova_super(example_Nova_PolymorphismDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

