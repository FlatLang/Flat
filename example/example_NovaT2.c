#include <precompiled.h>
#include <example/example_NovaT2.h>


nova_VTable_example_NovaT2 nova_VTable_example_NovaT2_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
	example_NovaT2_static_Accessor_Novaind,
};
void example_NovaT2Nova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

example_NovaT2* example_NovaT2_0_Novaconstruct(example_NovaT2* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(example_NovaT2, this,);
	this->vtable = &nova_VTable_example_NovaT2_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	example_NovaT1_Novasuper((example_NovaT1*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	example_NovaT1_Novathis((example_NovaT1*)(this), exceptionData);
	example_NovaT2_Novasuper(this, exceptionData);
	
	{
		example_NovaT2_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_T2(example_NovaT2** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void example_NovaT2_Novathis(example_NovaT2* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

int example_NovaT2_static_Accessor_Novaind(example_NovaT2* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return 2;
}

void example_NovaT2_Novasuper(example_NovaT2* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
