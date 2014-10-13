#include <precompiled.h>
#include <example/example_NovaQueueDemo.h>


nova_VTable_example_NovaQueueDemo nova_VTable_example_NovaQueueDemo_val =
{
		nova_standard_NovaObject_Nova0_getHashCodeLong,
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};
void example_NovaQueueDemoNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
		{
		}
}

example_NovaQueueDemo* example_NovaQueueDemo_Nova0_construct(example_NovaQueueDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(example_NovaQueueDemo, this,);
		this->vtable = &nova_VTable_example_NovaQueueDemo_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		example_NovaQueueDemo_Novasuper(this, 0);
		
		{
				example_NovaQueueDemo_Novathis(this, exceptionData);
		}
		
		return this;
}

void nova_del_QueueDemo(example_NovaQueueDemo** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void example_NovaQueueDemo_static_Novamain(example_NovaQueueDemo* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		nova_standard_datastruct_NovaQueue* l1_Novaq;
		int l1_Novanum;
		
		l1_Novaq = nova_standard_datastruct_NovaQueue_Nova0_construct(0, exceptionData);
		nova_standard_datastruct_NovaQueue_Novaenqueue(l1_Novaq, exceptionData, (nova_standard_NovaObject*)(nova_standard_primitive_number_NovaInt_Novaconstruct(0, exceptionData, 5)));
		nova_standard_datastruct_NovaQueue_Novaenqueue(l1_Novaq, exceptionData, (nova_standard_NovaObject*)(nova_standard_primitive_number_NovaInt_Novaconstruct(0, exceptionData, 7)));
		nova_standard_datastruct_NovaQueue_Novaenqueue(l1_Novaq, exceptionData, (nova_standard_NovaObject*)(nova_standard_primitive_number_NovaInt_Novaconstruct(0, exceptionData, 3)));
		nova_standard_datastruct_NovaQueue_Novaenqueue(l1_Novaq, exceptionData, (nova_standard_NovaObject*)(nova_standard_primitive_number_NovaInt_Novaconstruct(0, exceptionData, 10)));
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Queue: "), exceptionData, l1_Novaq->vtable->nova_standard_datastruct_NovaQueue_Novavirtual0_toString(l1_Novaq, exceptionData)));
		l1_Novanum = ((nova_standard_primitive_number_NovaInt*)nova_standard_datastruct_NovaQueue_Novadequeue(l1_Novaq, exceptionData))->nova_standard_primitive_number_NovaInt_Novavalue;
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Dequeued: "), exceptionData, nova_standard_primitive_number_NovaInt_static_Nova1_toString(0, exceptionData, l1_Novanum)));
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Queue: "), exceptionData, l1_Novaq->vtable->nova_standard_datastruct_NovaQueue_Novavirtual0_toString(l1_Novaq, exceptionData)));
		nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
}

void example_NovaQueueDemo_Novathis(example_NovaQueueDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_NovaQueueDemo_Novasuper(example_NovaQueueDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
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
		nova_standard_datastruct_NovaQueueNova_init_static(exceptionData);
		nova_standard_datastruct_NovaListNova_init_static(exceptionData);
		nova_standard_datastruct_NovaListNodeNova_init_static(exceptionData);
		nova_standard_datastruct_NovaArrayNova_init_static(exceptionData);
		nova_standard_datastruct_NovaStackNova_init_static(exceptionData);
		nova_standard_datastruct_NovaEmptyStackExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_NovaHashMapNova_init_static(exceptionData);
		nova_standard_datastruct_NovaBoundsNova_init_static(exceptionData);
		example_NovaQueueDemoNova_init_static(exceptionData);
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_Nova1_construct(0, 0, str);
		}
		
		TRY
		{
				example_NovaQueueDemo_static_Novamain(0, exceptionData, args);
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