#include <precompiled.h>
#include <example/example_NovaGenericDemo.h>


nova_VTable_example_NovaGenericDemo nova_VTable_example_NovaGenericDemo_val =
{
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};

example_NovaGenericDemo* example_NovaGenericDemo_Nova0_construct(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
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
		int l1_Novalen;
		nova_standard_NovaString**** l1_Novas;
		
		l1_Novastrs = nova_standard_datastruct_NovaStack_Nova0_construct(0, exceptionData);
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Pushing \"test\""));
		nova_standard_datastruct_NovaStack_Novapush(l1_Novastrs, exceptionData, (nova_standard_NovaObject*)(nova_standard_NovaString_Novaconstruct(0, exceptionData, "test")));
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Pushing \"ASDFASDF\""));
		nova_standard_datastruct_NovaStack_Novapush(l1_Novastrs, exceptionData, (nova_standard_NovaObject*)(nova_standard_NovaString_Novaconstruct(0, exceptionData, "ASDFASDF")));
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Pushing \"3!\""));
		nova_standard_datastruct_NovaStack_Novapush(l1_Novastrs, exceptionData, (nova_standard_NovaObject*)(nova_standard_NovaString_Novaconstruct(0, exceptionData, "3!")));
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Pushing \"34!!4334\""));
		nova_standard_datastruct_NovaStack_Novapush(l1_Novastrs, exceptionData, (nova_standard_NovaObject*)(nova_standard_NovaString_Novaconstruct(0, exceptionData, "34!!4334")));
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Pushing null"));
		nova_standard_datastruct_NovaStack_Novapush(l1_Novastrs, exceptionData, (nova_standard_NovaObject*)nova_null);
		l1_Novalen = 2;
		l1_Novas = (nova_standard_NovaString****)nova_gen_array(NOVA_MALLOC(sizeof(nova_standard_NovaString[5][l1_Novalen][5])), (int[]) { l1_Novalen, 5 }, 0, 2, sizeof(nova_standard_NovaString));
		l1_Novas[0][1][0] = nova_standard_NovaString_Novaconstruct(0, exceptionData, "test");
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "It is "), exceptionData, l1_Novas[0][1][0]));
		while (0 == nova_standard_datastruct_NovaStack_NovaisEmpty(l1_Novastrs, exceptionData))
		{
				nova_standard_NovaString* l2_Novapopped;
				
				l2_Novapopped = ((nova_standard_NovaString*)nova_standard_datastruct_NovaStack_Novapop(l1_Novastrs, exceptionData));
				if (l2_Novapopped != (nova_standard_NovaString*)nova_null)
				{
						l2_Novapopped = nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "\""), exceptionData, l2_Novapopped->vtable->nova_standard_NovaString_Novavirtual0_concat(l2_Novapopped, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "\"")));
				}
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "Popping: "), exceptionData, l2_Novapopped));
		}
		nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
}

void example_NovaGenericDemo_Novathis(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_NovaGenericDemo_Novasuper(example_NovaGenericDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
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