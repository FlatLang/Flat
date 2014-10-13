#include <precompiled.h>
#include <example/example_NovaArrayListDemo.h>


nova_VTable_example_NovaArrayListDemo nova_VTable_example_NovaArrayListDemo_val =
{
		nova_standard_NovaObject_Nova0_getHashCodeLong,
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};
void example_NovaArrayListDemoNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
		{
		}
}

example_NovaArrayListDemo* example_NovaArrayListDemo_Nova0_construct(example_NovaArrayListDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(example_NovaArrayListDemo, this,);
		this->vtable = &nova_VTable_example_NovaArrayListDemo_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		example_NovaArrayListDemo_Novasuper(this, 0);
		
		{
				example_NovaArrayListDemo_Novathis(this, exceptionData);
		}
		
		return this;
}

void nova_del_ArrayListDemo(example_NovaArrayListDemo** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void example_NovaArrayListDemo_static_Novamain(example_NovaArrayListDemo* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		char l1_Novac;
		
		l1_Novac = 'y';
		while (l1_Novac == 'y' || l1_Novac == 'Y')
		{
				nova_standard_datastruct_NovaArrayList* l2_Novalist;
				example_NovaAnimal* l2_Novaanimal;
				int l3_Novai;
				nova_standard_NovaString* nova_local_1;
				int l4_Novai;
				int l5_Novai;
				
				l2_Novalist = nova_standard_datastruct_NovaArrayList_Nova0_construct(0, exceptionData);
				nova_standard_datastruct_NovaArrayList_Nova0_add(l2_Novalist, exceptionData, (nova_standard_NovaObject*)(example_NovaDog_Nova0_construct(0, exceptionData)));
				nova_standard_datastruct_NovaArrayList_Nova0_add(l2_Novalist, exceptionData, (nova_standard_NovaObject*)(example_NovaDog_Nova0_construct(0, exceptionData)));
				nova_standard_datastruct_NovaArrayList_Nova0_add(l2_Novalist, exceptionData, (nova_standard_NovaObject*)(example_NovaSpider_Nova0_construct(0, exceptionData)));
				nova_standard_datastruct_NovaArrayList_Nova0_add(l2_Novalist, exceptionData, (nova_standard_NovaObject*)(example_NovaDog_Nova0_construct(0, exceptionData)));
				nova_standard_datastruct_NovaArrayList_Nova0_add(l2_Novalist, exceptionData, (nova_standard_NovaObject*)(example_NovaAnimal_Nova0_construct(0, exceptionData)));
				nova_standard_datastruct_NovaArrayList_Nova0_add(l2_Novalist, exceptionData, (nova_standard_NovaObject*)(example_NovaSpider_Nova0_construct(0, exceptionData)));
				l3_Novai = 0;
				for (; l3_Novai < l2_Novalist->nova_standard_datastruct_NovaArrayList_Novasize; l3_Novai++)
				{
						example_NovaAnimal* nova_local_0;
						
						nova_local_0 = (example_NovaAnimal*)(((example_NovaAnimal*)nova_standard_datastruct_NovaArrayList_Novaget(l2_Novalist, exceptionData, l3_Novai)));
						nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Contains: "), exceptionData, nova_local_0->vtable->example_NovaAnimal_Novavirtual0_toString(nova_local_0, exceptionData)));
				}
				l2_Novaanimal = (example_NovaAnimal*)(((example_NovaAnimal*)nova_standard_datastruct_NovaArrayList_Novaremove(l2_Novalist, exceptionData, 2)));
				nova_local_1 = l2_Novaanimal->vtable->example_NovaAnimal_Novavirtual0_toString(l2_Novaanimal, exceptionData);
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "--------- Removed: "), exceptionData, nova_local_1->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_1, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, " ----------"))));
				l4_Novai = 0;
				for (; l4_Novai < l2_Novalist->nova_standard_datastruct_NovaArrayList_Novasize; l4_Novai++)
				{
						example_NovaAnimal* nova_local_2;
						
						nova_local_2 = (example_NovaAnimal*)(((example_NovaAnimal*)nova_standard_datastruct_NovaArrayList_Novaget(l2_Novalist, exceptionData, l4_Novai)));
						nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Contains: "), exceptionData, nova_local_2->vtable->example_NovaAnimal_Novavirtual0_toString(nova_local_2, exceptionData)));
				}
				nova_standard_datastruct_NovaArrayList_Nova1_add(l2_Novalist, exceptionData, 1, (nova_standard_NovaObject*)(example_NovaSpider_Nova0_construct(0, exceptionData)));
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "--------- Added a new spider at index 1 ----------"));
				l5_Novai = 0;
				for (; l5_Novai < l2_Novalist->nova_standard_datastruct_NovaArrayList_Novasize; l5_Novai++)
				{
						example_NovaAnimal* nova_local_3;
						
						nova_local_3 = (example_NovaAnimal*)(((example_NovaAnimal*)nova_standard_datastruct_NovaArrayList_Novaget(l2_Novalist, exceptionData, l5_Novai)));
						nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Contains: "), exceptionData, nova_local_3->vtable->example_NovaAnimal_Novavirtual0_toString(nova_local_3, exceptionData)));
				}
				nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Run again? (Y/N)"));
				l1_Novac = nova_standard_io_NovaConsole_static_NovareadChar(0, exceptionData);
		}
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "\nFinished"));
		nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
}

void example_NovaArrayListDemo_Novathis(example_NovaArrayListDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_NovaArrayListDemo_Novasuper(example_NovaArrayListDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
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
		
		nova_standard_NovaStringNova_init_static(exceptionData);
		nova_standard_NovaMathNova_init_static(exceptionData);
		nova_standard_NovaObjectNova_init_static(exceptionData);
		nova_standard_NovaSystemNova_init_static(exceptionData);
		nova_standard_database_NovaDBConnectorNova_init_static(exceptionData);
		nova_standard_database_NovaResultSetNova_init_static(exceptionData);
		nova_standard_network_NovaSocketNova_init_static(exceptionData);
		nova_standard_network_NovaServerSocketNova_init_static(exceptionData);
		nova_standard_network_NovaClientSocketNova_init_static(exceptionData);
		nova_standard_network_NovaConnectionSocketNova_init_static(exceptionData);
		nova_standard_network_NovaNetworkInputStreamNova_init_static(exceptionData);
		nova_standard_network_NovaNetworkOutputStreamNova_init_static(exceptionData);
		nova_standard_logic_NovaConclusionNova_init_static(exceptionData);
		nova_standard_logic_NovaHypothesisNova_init_static(exceptionData);
		nova_standard_logic_NovaLogicalConnectiveNova_init_static(exceptionData);
		nova_standard_logic_NovaStatementNova_init_static(exceptionData);
		nova_standard_logic_NovaStatementComponentNova_init_static(exceptionData);
		nova_standard_logic_NovaStatementLetterNova_init_static(exceptionData);
		nova_standard_logic_NovaWFFNova_init_static(exceptionData);
		nova_standard_logic_NovaStatementGroupNova_init_static(exceptionData);
		nova_standard_logic_NovaInvalidFormulaExceptionNova_init_static(exceptionData);
		nova_standard_process_NovaProcessNova_init_static(exceptionData);
		nova_standard_primitive_NovaBoolNova_init_static(exceptionData);
		nova_standard_primitive_NovaNullNova_init_static(exceptionData);
		nova_standard_primitive_NovaPrimitiveNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaCharNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaByteNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaShortNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaIntNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaLongNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaFloatNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaDoubleNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaNumberNova_init_static(exceptionData);
		nova_standard_gc_NovaGCNova_init_static(exceptionData);
		nova_standard_time_NovaTimeNova_init_static(exceptionData);
		nova_standard_time_NovaDateNova_init_static(exceptionData);
		nova_standard_thread_NovaThreadNova_init_static(exceptionData);
		nova_standard_thread_NovaUncaughtExceptionHandlerNova_init_static(exceptionData);
		nova_standard_io_NovaInputStreamNova_init_static(exceptionData);
		nova_standard_io_NovaOutputStreamNova_init_static(exceptionData);
		nova_standard_io_NovaStreamReaderNova_init_static(exceptionData);
		nova_standard_io_NovaFileNova_init_static(exceptionData);
		nova_standard_io_NovaConsoleNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGComponentNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGComponentListNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGComponentNodeNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGMainComponentNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGCircleNova_init_static(exceptionData);
		nova_standard_exception_NovaExceptionDataNova_init_static(exceptionData);
		nova_standard_exception_NovaDivideByZeroExceptionNova_init_static(exceptionData);
		nova_standard_exception_NovaExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_NovaArrayListNova_init_static(exceptionData);
		nova_standard_datastruct_NovaListNova_init_static(exceptionData);
		nova_standard_datastruct_NovaListNodeNova_init_static(exceptionData);
		nova_standard_datastruct_NovaArrayNova_init_static(exceptionData);
		nova_standard_datastruct_NovaStackNova_init_static(exceptionData);
		nova_standard_datastruct_NovaEmptyStackExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_NovaHashMapNova_init_static(exceptionData);
		nova_standard_datastruct_NovaBoundsNova_init_static(exceptionData);
		example_NovaAnimalNova_init_static(exceptionData);
		example_NovaSpiderNova_init_static(exceptionData);
		example_NovaDogNova_init_static(exceptionData);
		example_NovaArrayListDemoNova_init_static(exceptionData);
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_Nova1_construct(0, 0, str);
		}
		
		TRY
		{
				example_NovaArrayListDemo_static_Novamain(0, exceptionData, args);
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