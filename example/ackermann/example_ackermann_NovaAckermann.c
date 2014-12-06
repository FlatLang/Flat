#include <precompiled.h>
#include <example/ackermann/example_ackermann_NovaAckermann.h>


nova_VTable_example_ackermann_NovaAckermann nova_VTable_example_ackermann_NovaAckermann_val =
{
		nova_standard_NovaObject_0_NovagetHashCodeLong,
		nova_standard_NovaObject_0_NovatoString,
		nova_standard_NovaObject_0_Novaequals,
};
void example_ackermann_NovaAckermannNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
		{
		}
}

example_ackermann_NovaAckermann* example_ackermann_NovaAckermann_0_Novaconstruct(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(example_ackermann_NovaAckermann, this,);
		this->vtable = &nova_VTable_example_ackermann_NovaAckermann_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		example_ackermann_NovaAckermann_Novasuper(this, exceptionData);
		
		{
				example_ackermann_NovaAckermann_Novathis(this, exceptionData);
		}
		
		return this;
}

void example_ackermann_NovaAckermann_Novadestroy(example_ackermann_NovaAckermann** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void example_ackermann_NovaAckermann_static_Novamain(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_0_Novaconcat(nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Ackermann: "), exceptionData, nova_standard_primitive_number_NovaInt_static_1_NovatoString(0, exceptionData, example_ackermann_NovaAckermann_static_Novarun((example_ackermann_NovaAckermann*)nova_null, exceptionData, 4, 1))));
		nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
}

int example_ackermann_NovaAckermann_static_Novarun(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novam, int l0_Novan)
{
		if (l0_Novam == 0)
		{
				return l0_Novan + 1;
		}
		else if (l0_Novam > 0)
		{
				if (l0_Novan == 0)
				{
						return example_ackermann_NovaAckermann_static_Novarun((example_ackermann_NovaAckermann*)nova_null, exceptionData, l0_Novam - 1, 1);
				}
				else if (l0_Novan > 0)
				{
						return example_ackermann_NovaAckermann_static_Novarun((example_ackermann_NovaAckermann*)nova_null, exceptionData, l0_Novam - 1, example_ackermann_NovaAckermann_static_Novarun((example_ackermann_NovaAckermann*)nova_null, exceptionData, l0_Novam, l0_Novan - 1));
				}
		}
		return 0;
}

int example_ackermann_NovaAckermann_static_Novarun2(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novam, int l0_Novan)
{
		if (l0_Novam == 0)
		{
				return l0_Novan + 1;
		}
		else if (l0_Novam > 0)
		{
				if (l0_Novan == 0)
				{
						return example_ackermann_NovaAckermann_static_Novarun((example_ackermann_NovaAckermann*)nova_null, exceptionData, l0_Novam - 1, 1);
				}
				else if (l0_Novan > 0)
				{
						return example_ackermann_NovaAckermann_static_Novarun((example_ackermann_NovaAckermann*)nova_null, exceptionData, l0_Novam - 1, example_ackermann_NovaAckermann_static_Novarun((example_ackermann_NovaAckermann*)nova_null, exceptionData, l0_Novam, l0_Novan - 1));
				}
		}
		return 0;
}

void example_ackermann_NovaAckermann_Novathis(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_ackermann_NovaAckermann_Novasuper(example_ackermann_NovaAckermann* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}


nova_standard_primitive_NovaNull* nova_null;

int main(int argc, char** argvs)
{
		nova_standard_NovaString** args;
		int      i;
		
		nova_standard_exception_NovaExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_null = nova_standard_primitive_NovaNull_0_Novaconstruct(0, exceptionData);
		nova_standard_gc_NovaGC_static_Novainit(0, exceptionData);
		
		nova_standard_NovaStringNova_init_static(exceptionData);
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
		nova_standard_math_NovaMathNova_init_static(exceptionData);
		nova_standard_math_logic_NovaConclusionNova_init_static(exceptionData);
		nova_standard_math_logic_NovaHypothesisNova_init_static(exceptionData);
		nova_standard_math_logic_NovaLogicalConnectiveNova_init_static(exceptionData);
		nova_standard_math_logic_NovaStatementNova_init_static(exceptionData);
		nova_standard_math_logic_NovaStatementComponentNova_init_static(exceptionData);
		nova_standard_math_logic_NovaStatementLetterNova_init_static(exceptionData);
		nova_standard_math_logic_NovaWFFNova_init_static(exceptionData);
		nova_standard_math_logic_NovaStatementGroupNova_init_static(exceptionData);
		nova_standard_math_logic_NovaInvalidFormulaExceptionNova_init_static(exceptionData);
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
		nova_standard_star_NovaWindowNova_init_static(exceptionData);
		nova_standard_star_NovaWindowThreadNova_init_static(exceptionData);
		nova_standard_gc_NovaGCNova_init_static(exceptionData);
		example_ackermann_NovaAckermannNova_init_static(exceptionData);
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_1_Novaconstruct(0, 0, str);
		}
		
		TRY
		{
				example_ackermann_NovaAckermann_static_Novamain(0, exceptionData, args);
		}
		CATCH (1)
		{
				nova_standard_exception_NovaException* base = (nova_standard_exception_NovaException*)exceptionData->nova_standard_exception_NovaExceptionData_NovathrownException;
				printf("Exception in Thread 'main': %s", base->nova_standard_exception_NovaException_Novamessage->nova_standard_NovaString_Novachars);
				nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		FreeConsole();
		NOVA_FREE(args);
		nova_standard_gc_NovaGC_static_Novacollect(0, exceptionData);
		
		
		return 0;
}