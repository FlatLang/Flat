#include <precompiled.h>
#include <example/example_NovaLab.h>


nova_VTable_example_NovaLab nova_VTable_example_NovaLab_val =
{
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};

example_NovaLab* example_NovaLab_Nova0_construct(example_NovaLab* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(example_NovaLab, this,);
		this->vtable = &nova_VTable_example_NovaLab_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		example_NovaLab_Novasuper(this, 0);
		
		{
				example_NovaLab_Novathis(this, exceptionData);
		}
		
		return this;
}

void nova_del_Lab(example_NovaLab** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void example_NovaLab_static_Novamain(example_NovaLab* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		nova_standard_logic_NovaStatementLetter** l1_Novaletters;
		nova_standard_logic_NovaWFF* l1_Novaf;
		
		l1_Novaletters = (nova_standard_logic_NovaStatementLetter**)NOVA_MALLOC(sizeof(nova_standard_logic_NovaStatementLetter[4]));
		l1_Novaletters[0] = nova_standard_logic_NovaStatementLetter_Novaconstruct(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "A"), nova_standard_NovaString_Novaconstruct(0, exceptionData, "McDonald's is open"));
		l1_Novaletters[1] = nova_standard_logic_NovaStatementLetter_Novaconstruct(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "B"), nova_standard_NovaString_Novaconstruct(0, exceptionData, "Sally is awake"));
		l1_Novaletters[2] = nova_standard_logic_NovaStatementLetter_Novaconstruct(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "C"), nova_standard_NovaString_Novaconstruct(0, exceptionData, "Sally is at McDonald's"));
		l1_Novaletters[4] = nova_standard_logic_NovaStatementLetter_Novaconstruct(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "D"), nova_standard_NovaString_Novaconstruct(0, exceptionData, "Sally is fat"));
		l1_Novaf = nova_standard_logic_NovaWFF_Novaconstruct(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "A & B & C -> D"), l1_Novaletters);
		nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
}

void example_NovaLab_Novathis(example_NovaLab* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_NovaLab_Novasuper(example_NovaLab* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}


nova_standard_primitive_NovaNull* nova_null;

int main(int argc, char** argvs)
{
		nova_standard_NovaString** args;
		int      i;
		
		nova_standard_exception_NovaExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_null = nova_standard_primitive_NovaNull_Nova0_construct(0, exceptionData);
		nova_standard_gc_NovaGC_static_Novainit(0, exceptionData);
		
		nova_standard_NovaMathNova_init_static(exceptionData);
		nova_standard_logic_NovaWFFNova_init_static(exceptionData);
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_Novaconstruct(0, 0, str);
		}
		
		TRY
		{
				example_NovaLab_static_Novamain(0, exceptionData, args);
		}
		CATCH (1)
		{
				nova_standard_exception_NovaException* base = (nova_standard_exception_NovaException*)exceptionData->nova_standard_exception_NovaExceptionData_NovathrownException;
				printf("Exception in Thread 'main': %s", nova_standard_NovaString_NovatoCharArray(base->nova_standard_exception_NovaException_Novamessage, 0));
				nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		GC_gcollect();
		
		return 0;
}