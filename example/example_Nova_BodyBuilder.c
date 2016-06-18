#include <precompiled.h>
#include <example/example_Nova_BodyBuilder.h>

example_Extension_VTable_BodyBuilder example_Extension_VTable_BodyBuilder_val =
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
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	example_Nova_BodyBuilder_Nova_sayHello,
};


void example_Nova_BodyBuilderNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_BodyBuilder* example_Nova_BodyBuilder_1_Nova_construct(example_Nova_BodyBuilder* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_BodyBuilder_Nova_weightClass, nova_standard_Nova_String* example_Nova_BodyBuilder_Nova_name)
{
	CCLASS_NEW(example_Nova_BodyBuilder, this,);
	this->vtable = &example_Extension_VTable_BodyBuilder_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_Person_Nova_super((example_Nova_Person*)this, exceptionData);
	example_Nova_BodyBuilder_0_Nova_super(this, exceptionData);
	
	{
		example_Nova_BodyBuilder_1_Nova_this(this, exceptionData, example_Nova_BodyBuilder_Nova_weightClass, example_Nova_BodyBuilder_Nova_name);
	}
	
	return this;
}

void example_Nova_BodyBuilder_Nova_destroy(example_Nova_BodyBuilder** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE(*this);
}

void example_Nova_BodyBuilder_1_Nova_this(example_Nova_BodyBuilder* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_BodyBuilder_Nova_weightClass, nova_standard_Nova_String* example_Nova_BodyBuilder_Nova_name)
{
	this->example_Nova_Person_Nova_age = 5;
	this->example_Nova_BodyBuilder_Nova_weightClass = example_Nova_BodyBuilder_Nova_weightClass;
	this->example_Nova_Person_Nova_name = example_Nova_BodyBuilder_Nova_name;
}

void example_Nova_BodyBuilder_Nova_sayHello(example_Nova_BodyBuilder* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Hello from "), exceptionData, this->example_Nova_Person_Nova_name->vtable->nova_standard_Nova_String_virtual0_Nova_concat(this->example_Nova_Person_Nova_name, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, " the BodyBuilder"))));
}

void example_Nova_BodyBuilder_0_Nova_super(example_Nova_BodyBuilder* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->example_Nova_BodyBuilder_Nova_weightClass = 0;
}

