#include <precompiled.h>
#include <example/example_NovaT1.h>


nova_VTable_example_NovaT1 nova_VTable_example_NovaT1_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
	example_NovaT1_static_Accessor_Novaind,
};
void example_NovaT1Nova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

example_NovaT1* example_NovaT1_0_Novaconstruct(example_NovaT1* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(example_NovaT1, this,);
	this->vtable = &nova_VTable_example_NovaT1_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	example_NovaT1_Novasuper(this, exceptionData);
	
	{
		example_NovaT1_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_T1(example_NovaT1** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void example_NovaT1_Novathis(example_NovaT1* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

int example_NovaT1_static_Accessor_Novaind(example_NovaT1* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 1;
}

void example_NovaT1_Novasuper(example_NovaT1* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
