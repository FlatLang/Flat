#include <precompiled.h>
#include <example/example_Nova_Spider.h>

example_Extension_VTable_Spider example_Extension_VTable_Spider_val =
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
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	example_Nova_Animal_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	example_Nova_Spider_0_Nova_getNumLegs,
	example_Nova_Spider_0_Nova_getNumEyes,
	example_Nova_Spider_0_Nova_getDescription,
};


void example_Nova_SpiderNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Spider* example_Nova_Spider_0_Nova_construct(example_Nova_Spider* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_Spider, this,);
	this->vtable = &example_Extension_VTable_Spider_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_Animal_Nova_super((example_Nova_Animal*)this, exceptionData);
	example_Nova_Spider_0_Nova_super(this, exceptionData);
	
	{
		example_Nova_Spider_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_Spider_Nova_destroy(example_Nova_Spider** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

int example_Nova_Spider_0_Nova_getNumLegs(example_Nova_Spider* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return 8;
}

int example_Nova_Spider_0_Nova_getNumEyes(example_Nova_Spider* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return 9000;
}

nova_standard_Nova_String* example_Nova_Spider_0_Nova_getDescription(example_Nova_Spider* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "A disgusting thing (Spider)");
}

void example_Nova_Spider_0_Nova_this(example_Nova_Spider* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_Spider_0_Nova_super(example_Nova_Spider* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

