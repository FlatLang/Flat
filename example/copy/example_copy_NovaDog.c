#include <precompiled.h>
#include <example/copy/example_copy_NovaDog.h>


nova_VTable_example_copy_NovaDog nova_VTable_example_copy_NovaDog_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
};
void example_copy_NovaDogNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

example_copy_NovaDog* example_copy_NovaDog_Novaconstruct(example_copy_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaa, int l0_Novab)
{
	CCLASS_NEW(example_copy_NovaDog, this,);
	this->vtable = &nova_VTable_example_copy_NovaDog_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	example_copy_NovaDog_Novasuper(this, exceptionData);
	
	{
		example_copy_NovaDog_Novathis(this, exceptionData, l0_Novaa, l0_Novab);
	}
	
	return this;
}

void example_copy_NovaDog_Novadestroy(example_copy_NovaDog** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void example_copy_NovaDog_Novathis(example_copy_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaa, int l0_Novab)
{
	nova_standard_NovaString* nova_local_0;
	
	nova_local_0 = nova_standard_primitive_number_NovaInt_static_1_NovatoString(0, exceptionData, l0_Novaa);
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_local_0->vtable->nova_standard_NovaString_virtual0_Novaconcat(nova_local_0, exceptionData, nova_standard_NovaString_0_Novaconcat(nova_standard_NovaString_1_Novaconstruct(0, exceptionData, ", "), exceptionData, nova_standard_primitive_number_NovaInt_static_1_NovatoString(0, exceptionData, l0_Novab))));
}

void example_copy_NovaDog_Novasuper(example_copy_NovaDog* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
