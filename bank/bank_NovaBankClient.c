#include <precompiled.h>
#include <bank/bank_NovaBankClient.h>


nova_VTable_bank_NovaBankClient nova_VTable_bank_NovaBankClient_val =
{
		nova_standard_NovaObject_Nova0_getHashCodeLong,
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};
void bank_NovaBankClientNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
		{
		}
}

bank_NovaBankClient* bank_NovaBankClient_Nova0_construct(bank_NovaBankClient* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(bank_NovaBankClient, this,);
		this->vtable = &nova_VTable_bank_NovaBankClient_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		bank_NovaBankClient_Novasuper(this, exceptionData);
		
		{
				bank_NovaBankClient_Novathis(this, exceptionData);
		}
		
		return this;
}

void nova_del_BankClient(bank_NovaBankClient** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void bank_NovaBankClient_static_Novamain(bank_NovaBankClient* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		nova_standard_network_NovaClientSocket* l1_Novasocket;
		nova_standard_NovaString** l1_Novaips;
		int l1_Novaport;
		int l2_Novai;
		
		l1_Novasocket = nova_standard_network_NovaClientSocket_Nova0_construct(0, exceptionData);
		l1_Novaips = (nova_standard_NovaString**)NOVA_MALLOC(sizeof(nova_standard_NovaString*[4]));
		l1_Novaips[0] = nova_standard_NovaString_Nova1_construct(0, exceptionData, "192.168.1.139");
		l1_Novaips[1] = nova_standard_NovaString_Nova1_construct(0, exceptionData, "192.168.1.101");
		l1_Novaips[2] = nova_standard_NovaString_Nova1_construct(0, exceptionData, "10.0.53.54");
		l1_Novaips[3] = nova_standard_NovaString_Nova1_construct(0, exceptionData, "127.0.0.1");
		l1_Novaport = 5675;
		l2_Novai = 0;
		for (; l2_Novai < 4; l2_Novai++)
		{
				nova_standard_NovaString* l2_Novaip;
				
				l2_Novaip = l1_Novaips[l2_Novai];
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Attempting connection on IP: "), exceptionData, l2_Novaip));
				if (nova_standard_network_NovaClientSocket_Novaconnect(l1_Novasocket, exceptionData, l2_Novaip, l1_Novaport))
				{
						bank_NovaClientConnectionThread* l3_Novathread;
						nova_standard_NovaString* l3_Novainput;
						
						nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Connected!"));
						l3_Novathread = bank_NovaClientConnectionThread_Novaconstruct(0, exceptionData, l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection);
						l3_Novainput = (nova_standard_NovaString*)((nova_standard_NovaObject*)nova_null);
						while (l3_Novainput == (nova_standard_NovaString*)nova_null || l3_Novainput->vtable->nova_standard_NovaString_Novavirtual_equals(l3_Novainput, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "YOU IMPOSTER!\n")))
						{
								nova_standard_NovaString* l4_Novausername;
								nova_standard_NovaString* l4_Novapassword;
								
								nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_Novavirtual0_readString(l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain, exceptionData));
								l4_Novausername = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
								l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout->vtable->nova_standard_io_NovaOutputStream_Novavirtual1_write(l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout, exceptionData, l4_Novausername);
								nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_Novavirtual0_readString(l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain, exceptionData));
								l4_Novapassword = nova_standard_security_NovaMD5_static_Novaencrypt(0, exceptionData, nova_standard_io_NovaConsole_static_NovareadPassword(0, exceptionData));
								l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout->vtable->nova_standard_io_NovaOutputStream_Novavirtual1_write(l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout, exceptionData, l4_Novapassword);
								nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_Novavirtual0_readString(l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain, exceptionData));
								l3_Novainput = l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_Novavirtual0_readString(l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novain, exceptionData);
								nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, l3_Novainput);
						}
						nova_standard_thread_NovaThread_Novastart((nova_standard_thread_NovaThread*)(l3_Novathread), exceptionData);
						while (1)
						{
								l3_Novainput = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
								if (!l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaconnected)
								{
										break;
								}
								l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout->vtable->nova_standard_io_NovaOutputStream_Novavirtual1_write(l1_Novasocket->nova_standard_network_NovaClientSocket_Novaconnection->nova_standard_network_NovaConnectionSocket_Novaout, exceptionData, l3_Novainput);
						}
						nova_standard_thread_NovaThread_Novajoin((nova_standard_thread_NovaThread*)(l3_Novathread), exceptionData);
						break;
				}
		}
}

void bank_NovaBankClient_Novathis(bank_NovaBankClient* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void bank_NovaBankClient_Novasuper(bank_NovaBankClient* this, nova_standard_exception_NovaExceptionData* exceptionData)
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
		nova_standard_gc_NovaGCNova_init_static(exceptionData);
		bank_NovaBankQueryExceptionNova_init_static(exceptionData);
		bank_NovaBankClientNova_init_static(exceptionData);
		bank_NovaClientConnectionThreadNova_init_static(exceptionData);
		bank_NovaClientInputThreadNova_init_static(exceptionData);
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_Nova1_construct(0, 0, str);
		}
		
		TRY
		{
				bank_NovaBankClient_static_Novamain(0, exceptionData, args);
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
		nova_standard_gc_NovaGC_static_Novacollect(0, exceptionData);
		
		
		return 0;
}