#include <precompiled.h>
#include <example/network/example_network_NovaClientDemo.h>


nova_VTable_example_network_NovaClientDemo nova_VTable_example_network_NovaClientDemo_val =
{
		nova_standard_NovaObject_Nova0_getHashCodeLong,
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};
void example_network_NovaClientDemoNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
		{
		}
}

example_network_NovaClientDemo* example_network_NovaClientDemo_Nova0_construct(example_network_NovaClientDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(example_network_NovaClientDemo, this,);
		this->vtable = &nova_VTable_example_network_NovaClientDemo_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		example_network_NovaClientDemo_Novasuper(this, 0);
		
		{
				example_network_NovaClientDemo_Novathis(this, exceptionData);
		}
		
		return this;
}

void nova_del_ClientDemo(example_network_NovaClientDemo** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void example_network_NovaClientDemo_static_Novamain(example_network_NovaClientDemo* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		nova_standard_network_NovaClientSocket* l1_Novasocket;
		nova_standard_NovaString* l1_Novaip;
		int l1_Novaport;
		char l1_Novaconnected;
		
		l1_Novasocket = nova_standard_network_NovaClientSocket_Nova0_construct(0, exceptionData);
		l1_Novaip = nova_standard_NovaString_Nova1_construct(0, exceptionData, "127.0.0.1");
		l1_Novaport = 5675;
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Attempting to connect to "), exceptionData, l1_Novaip->vtable->nova_standard_NovaString_Novavirtual0_concat(l1_Novaip, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, ":"), exceptionData, nova_standard_primitive_number_NovaInt_static_Nova1_toString(0, exceptionData, l1_Novaport)))));
		l1_Novaconnected = nova_standard_network_NovaClientSocket_Novaconnect(l1_Novasocket, exceptionData, l1_Novaip, l1_Novaport);
		if (l1_Novaconnected)
		{
				example_network_NovaConnectionThread* l2_Novathread;
				
				l2_Novathread = example_network_NovaConnectionThread_Novaconstruct(0, exceptionData, l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection);
				nova_standard_thread_NovaThread_Novastart((nova_standard_thread_NovaThread*)(l2_Novathread), exceptionData);
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Connected!"));
				while (l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaconnected)
				{
						nova_standard_NovaString* l3_Novamessage;
						
						l3_Novamessage = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
						if (l3_Novamessage->vtable->nova_standard_NovaString_Novavirtual_equals(l3_Novamessage, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "q")))
						{
								nova_standard_network_NovaClientSocket_Novaclose(l1_Novasocket, exceptionData);
								break;
						}
						l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout->vtable->nova_standard_io_NovaOutputStream_Novavirtual1_write(l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout, exceptionData, l3_Novamessage);
				}
				nova_standard_network_NovaClientSocket_Novaclose(l1_Novasocket, exceptionData);
		}
		else
		{
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to connect"));
				nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
		}
}

void example_network_NovaClientDemo_Novathis(example_network_NovaClientDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_network_NovaClientDemo_Novasuper(example_network_NovaClientDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
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
		nova_standard_security_NovaMD5Nova_init_static(exceptionData);
		example_network_NovaConnectionThreadNova_init_static(exceptionData);
		example_network_NovaClientDemoNova_init_static(exceptionData);
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_Nova1_construct(0, 0, str);
		}
		
		TRY
		{
				example_network_NovaClientDemo_static_Novamain(0, exceptionData, args);
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
		
		return 0;
}