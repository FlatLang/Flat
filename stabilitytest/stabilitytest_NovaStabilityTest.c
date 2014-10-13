#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaStabilityTest.h>


nova_VTable_stabilitytest_NovaStabilityTest nova_VTable_stabilitytest_NovaStabilityTest_val =
{
		nova_standard_NovaObject_Nova0_getHashCodeLong,
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};
void stabilitytest_NovaStabilityTestNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
		{
		}
}

stabilitytest_NovaStabilityTest* stabilitytest_NovaStabilityTest_Nova0_construct(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(stabilitytest_NovaStabilityTest, this,);
		this->vtable = &nova_VTable_stabilitytest_NovaStabilityTest_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		stabilitytest_NovaStabilityTest_Novasuper(this, 0);
		
		{
				stabilitytest_NovaStabilityTest_Novathis(this, exceptionData);
		}
		
		return this;
}

void nova_del_StabilityTest(stabilitytest_NovaStabilityTest** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void stabilitytest_NovaStabilityTest_static_Novamain(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		stabilitytest_NovaStabilityTest* l1_Novatest;
		long l1_Novastart;
		int l1_Novaresult;
		long l1_Novatime;
		nova_standard_NovaString* nova_local_0;
		
		l1_Novatest = stabilitytest_NovaStabilityTest_Nova0_construct(0, exceptionData);
		l1_Novastart = nova_standard_time_NovaTime_static_NovacurrentTimeMillis(0, exceptionData);
		l1_Novaresult = stabilitytest_NovaStabilityTest_NovarunTests(l1_Novatest, exceptionData);
		l1_Novatime = nova_standard_time_NovaTime_static_NovacurrentTimeMillis(0, exceptionData) - l1_Novastart;
		nova_local_0 = nova_standard_primitive_number_NovaLong_static_Nova1_toString(0, exceptionData, l1_Novatime);
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Took "), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "ms"))));
		nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
}

int stabilitytest_NovaStabilityTest_NovarunTests(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		TRY
		{
				nova_standard_exception_NovaExceptionData_NovaaddCode(exceptionData, exceptionData, 4);
				
				{
						stabilitytest_NovaExceptionStability_static_Novatest(0, exceptionData, this);
						stabilitytest_NovaTimeStability_static_Novatest(0, exceptionData, this);
						stabilitytest_NovaThreadStability_static_Novatest(0, exceptionData, this);
						stabilitytest_NovaFileStability_static_Novatest(0, exceptionData, this);
						stabilitytest_NovaSyntaxStability_static_Novatest(0, exceptionData, this);
						stabilitytest_NovaClosureStability_static_Novatest(0, exceptionData, this);
						stabilitytest_NovaPolymorphismStability_static_Novatest(0, exceptionData, this);
						stabilitytest_NovaNetworkStability_static_Novatest(0, exceptionData, this);
						nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "All OK"));
						return 0;
				}
		}
		CATCH (4)
		{
				stabilitytest_NovaUnstableException* l3_Novae;
				
				l3_Novae = (stabilitytest_NovaUnstableException*)(exceptionData->nova_standard_exception_NovaExceptionData_NovathrownException);
				return 1;
		}
		FINALLY
		{
		}
		END_TRY;
}

void stabilitytest_NovaStabilityTest_Nova0_fail(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		stabilitytest_NovaStabilityTest_Nova1_fail(this, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed"));
}

void stabilitytest_NovaStabilityTest_Nova1_fail(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novamessage)
{
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, l0_Novamessage);
		THROW(4, stabilitytest_NovaUnstableException_Nova0_construct(0, exceptionData));
}

void stabilitytest_NovaStabilityTest_Novathis(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaStabilityTest_Novasuper(stabilitytest_NovaStabilityTest* this, nova_standard_exception_NovaExceptionData* exceptionData)
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
		stabilitytest_NovaStabilityTestNova_init_static(exceptionData);
		stabilitytest_NovaTimeStabilityNova_init_static(exceptionData);
		stabilitytest_NovaFileStabilityNova_init_static(exceptionData);
		stabilitytest_NovaThreadStabilityNova_init_static(exceptionData);
		stabilitytest_NovaExceptionStabilityNova_init_static(exceptionData);
		stabilitytest_NovaSyntaxStabilityNova_init_static(exceptionData);
		stabilitytest_NovaClosureStabilityNova_init_static(exceptionData);
		stabilitytest_NovaPolymorphismStabilityNova_init_static(exceptionData);
		stabilitytest_NovaPolymorphicSuperClassNova_init_static(exceptionData);
		stabilitytest_NovaPolymorphicSubClassNova_init_static(exceptionData);
		stabilitytest_NovaStabilityTestExceptionNova_init_static(exceptionData);
		stabilitytest_NovaStabilityExceptionHandlerNova_init_static(exceptionData);
		stabilitytest_NovaThreadImplementationNova_init_static(exceptionData);
		stabilitytest_NovaUnstableExceptionNova_init_static(exceptionData);
		stabilitytest_NovaNetworkStabilityNova_init_static(exceptionData);
		stabilitytest_NovaClientThreadNova_init_static(exceptionData);
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_Nova1_construct(0, 0, str);
		}
		
		TRY
		{
				stabilitytest_NovaStabilityTest_static_Novamain(0, exceptionData, args);
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