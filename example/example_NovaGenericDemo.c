#include <precompiled.h>
#include <example/example_NovaGenericDemo.h>


nova_VTable_example_NovaGenericDemo nova_VTable_example_NovaGenericDemo_val =
{
		nova_standard_NovaObject_Novanull0_toString,
		nova_standard_NovaObject_Novanull0_equals,
};

example_NovaGenericDemo* example_NovaGenericDemo_Novanull0_construct(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(example_NovaGenericDemo, this,);
		this->vtable = &nova_VTable_example_NovaGenericDemo_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		example_NovaGenericDemo_Novasuper(this, 0);
		
		{
				example_NovaGenericDemo_Novathis(this, exceptionData);
		}
		
		return this;
}

void nova_del_GenericDemo(example_NovaGenericDemo** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void example_NovaGenericDemo_static_Novamain(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		nova_standard_datastruct_NovaStack* l1_Novastrs;
		
		l1_Novastrs = nova_standard_datastruct_NovaStack_Novanull0_construct(0, exceptionData);
		nova_standard_NovaConsole_static_Novanull0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Pushing \"test\""));
		nova_standard_datastruct_NovaStack_Novapush(l1_Novastrs, exceptionData, (nova_standard_NovaObject*)(nova_standard_NovaString_Novaconstruct(0, exceptionData, "test")));
		nova_standard_NovaConsole_static_Novanull0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Pushing \"ASDFASDF\""));
		nova_standard_datastruct_NovaStack_Novapush(l1_Novastrs, exceptionData, (nova_standard_NovaObject*)(nova_standard_NovaString_Novaconstruct(0, exceptionData, "ASDFASDF")));
		nova_standard_NovaConsole_static_Novanull0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Pushing \"3!\""));
		nova_standard_datastruct_NovaStack_Novapush(l1_Novastrs, exceptionData, (nova_standard_NovaObject*)(nova_standard_NovaString_Novaconstruct(0, exceptionData, "3!")));
		nova_standard_NovaConsole_static_Novanull0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Pushing \"34!!4334\""));
		nova_standard_datastruct_NovaStack_Novapush(l1_Novastrs, exceptionData, (nova_standard_NovaObject*)(nova_standard_NovaString_Novaconstruct(0, exceptionData, "34!!4334")));
		nova_standard_NovaConsole_static_Novanull0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Pushing null"));
		nova_standard_datastruct_NovaStack_Novapush(l1_Novastrs, exceptionData, (nova_standard_NovaObject*)nova_null);
		while (0 == nova_standard_datastruct_NovaStack_NovaisEmpty(l1_Novastrs, exceptionData))
		{
				nova_standard_NovaString* l2_Novapopped;
				
				l2_Novapopped = ((nova_standard_NovaString*)nova_standard_datastruct_NovaStack_Novapop(l1_Novastrs, exceptionData));
				if (l2_Novapopped != (nova_standard_NovaString*)nova_null)
				{
						l2_Novapopped = nova_standard_NovaString_Novanull0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "\""), exceptionData, l2_Novapopped->vtable->nova_standard_NovaString_Novavirtual0_concat(l2_Novapopped, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "\"")));
				}
				nova_standard_NovaConsole_static_Novanull0_writeLine(0, exceptionData, nova_standard_NovaString_Novanull0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "Popping: "), exceptionData, l2_Novapopped));
		}
		nova_standard_NovaConsole_static_NovawaitForEnter(0, exceptionData);
}

void example_NovaGenericDemo_Novathis(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_NovaGenericDemo_Novasuper(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}


nova_standard_NovaNull* nova_null;

int main(int argc, char** argvs)
{
		nova_standard_NovaString** args;
		int      i;
		
		nova_standard_exception_NovaExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_null = nova_standard_NovaNull_Novanull0_construct(0, exceptionData);
		nova_standard_NovaGC_static_Novainit(0, exceptionData);
		
		MathNova_init_static();
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_Novaconstruct(0, 0, str);
		}
		
		TRY
		{
				example_NovaGenericDemo_static_Novamain(0, exceptionData, args);
		}
		CATCH (1)
		{
				printf("You broke it.");
				nova_standard_NovaConsole_static_NovawaitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		GC_gcollect();
		
		return 0;
}