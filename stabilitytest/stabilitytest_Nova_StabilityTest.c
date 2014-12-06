#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_StabilityTest.h>


nova_VTable_stabilitytest_Nova_StabilityTest nova_VTable_stabilitytest_Nova_StabilityTest_val =
{
		nova_standard_Nova_Object_0_Nova_getHashCodeLong,
		nova_standard_Nova_Object_0_Nova_toString,
		nova_standard_Nova_Object_0_Nova_equals,
};
void stabilitytest_Nova_StabilityTestNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		{
		}
}

stabilitytest_Nova_StabilityTest* stabilitytest_Nova_StabilityTest_2_Nova_construct(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		CCLASS_NEW(stabilitytest_Nova_StabilityTest, this,);
		this->vtable = &nova_VTable_stabilitytest_Nova_StabilityTest_val;
		nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
		nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
		stabilitytest_Nova_StabilityTest_Nova_super(this, exceptionData);
		
		{
				stabilitytest_Nova_StabilityTest_Nova_this(this, exceptionData);
		}
		
		return this;
}

void stabilitytest_Nova_StabilityTest_Nova_destroy(stabilitytest_Nova_StabilityTest** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void stabilitytest_Nova_StabilityTest_Nova_main(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** l0_Nova_args)
{
		stabilitytest_Nova_StabilityTest* l1_Nova_test;
		long l1_Nova_start;
		int l1_Nova_result;
		long l1_Nova_time;
		nova_standard_Nova_String* nova_local_0;
		
		l1_Nova_test = stabilitytest_Nova_StabilityTest_2_Nova_construct(0, exceptionData);
		l1_Nova_start = nova_standard_time_Nova_Time_Nova_currentTimeMillis(0, exceptionData);
		l1_Nova_result = stabilitytest_Nova_StabilityTest_Nova_runTests(l1_Nova_test, exceptionData);
		l1_Nova_time = nova_standard_time_Nova_Time_Nova_currentTimeMillis(0, exceptionData) - l1_Nova_start;
		nova_local_0 = nova_standard_primitive_number_Nova_Long_1_Nova_toString(0, exceptionData, l1_Nova_time);
		nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Took "), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "ms"))));
		nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

int stabilitytest_Nova_StabilityTest_Nova_runTests(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		TRY
		{
				novaEnv.nova_standard_exception_ExceptionData.addCode(exceptionData, exceptionData, 5);
				
				{
						stabilitytest_Nova_ExceptionStability_Nova_test(0, exceptionData, this);
						stabilitytest_Nova_TimeStability_Nova_test(0, exceptionData, this);
						stabilitytest_Nova_ThreadStability_Nova_test(0, exceptionData, this);
						stabilitytest_Nova_FileStability_Nova_test(0, exceptionData, this);
						stabilitytest_Nova_SyntaxStability_Nova_test(0, exceptionData, this);
						stabilitytest_Nova_ClosureStability_Nova_test(0, exceptionData, this);
						stabilitytest_Nova_PolymorphismStability_Nova_test(0, exceptionData, this);
						stabilitytest_Nova_NetworkStability_Nova_test(0, exceptionData, this);
						nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "All OK"));
						return 0;
				}
		}
		CATCH (5)
		{
				stabilitytest_Nova_UnstableException* l3_Nova_e;
				
				l3_Nova_e = (stabilitytest_Nova_UnstableException*)(exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException);
				return 1;
		}
		FINALLY
		{
		}
		END_TRY;
}

void stabilitytest_Nova_StabilityTest_0_Nova_fail(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed"));
}

void stabilitytest_Nova_StabilityTest_1_Nova_fail(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_message)
{
		nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, l0_Nova_message);
		THROW(5, stabilitytest_Nova_UnstableException_0_Nova_construct(0, exceptionData));
}

void stabilitytest_Nova_StabilityTest_Nova_this(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void stabilitytest_Nova_StabilityTest_Nova_super(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}



nova_standard_primitive_Nova_Null* nova_null;
void* nova_garbageData;

int main(int argc, char** argvs)
{
		nova_standard_Nova_String** args;
		int      i;
		
		nova_standard_exception_Nova_ExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_null = nova_standard_primitive_Nova_Null_2_Nova_construct(0, exceptionData);
		nova_garbageData = malloc(sizeof(void*));
		nova_standard_gc_Nova_GC_Nova_init(0, exceptionData);
		
		novaEnv.nova_standard_String.concat__nova_standard_String = nova_VTable_nova_standard_Nova_String_val.nova_standard_Nova_String_virtual0_Nova_concat;
		novaEnv.nova_standard_String.toString = nova_VTable_nova_standard_Nova_String_val.nova_standard_Nova_String_virtual0_Nova_toString;
		novaEnv.nova_standard_Object.getHashCodeLong = nova_VTable_nova_standard_Nova_Object_val.nova_standard_Nova_Object_virtual0_Nova_getHashCodeLong;
		novaEnv.nova_standard_Object.toString = nova_VTable_nova_standard_Nova_Object_val.nova_standard_Nova_Object_virtual0_Nova_toString;
		novaEnv.nova_standard_Object.equals__nova_standard_Object = nova_VTable_nova_standard_Nova_Object_val.nova_standard_Nova_Object_virtual0_Nova_equals;
		novaEnv.nova_standard_math_NumericOperand.toString = nova_VTable_nova_standard_math_Nova_NumericOperand_val.nova_standard_math_Nova_NumericOperand_virtual0_Nova_toString;
		novaEnv.nova_standard_primitive_number_Number.numDigits__nova_standard_primitive_number_Number = nova_VTable_nova_standard_primitive_number_Nova_Number_val.nova_standard_primitive_number_Nova_Number_virtual0_Nova_numDigits;
		novaEnv.nova_standard_thread_Thread.run = nova_VTable_nova_standard_thread_Nova_Thread_val.nova_standard_thread_Nova_Thread_virtual0_Nova_run;
		novaEnv.nova_standard_thread_UncaughtExceptionHandler.uncaughtException__nova_standard_thread_Thread__nova_standard_exception_Exception = nova_VTable_nova_standard_thread_Nova_UncaughtExceptionHandler_val.nova_standard_thread_Nova_UncaughtExceptionHandler_virtual0_Nova_uncaughtException;
		novaEnv.nova_standard_io_InputStream.readString = nova_VTable_nova_standard_io_Nova_InputStream_val.nova_standard_io_Nova_InputStream_virtual0_Nova_readString;
		novaEnv.nova_standard_io_InputStream.readBytes = nova_VTable_nova_standard_io_Nova_InputStream_val.nova_standard_io_Nova_InputStream_virtual0_Nova_readBytes;
		novaEnv.nova_standard_io_OutputStream.write__nova_standard_String = nova_VTable_nova_standard_io_Nova_OutputStream_val.nova_standard_io_Nova_OutputStream_virtual1_Nova_write;
		novaEnv.nova_standard_io_OutputStream.write__nova_standard_Object = nova_VTable_nova_standard_io_Nova_OutputStream_val.nova_standard_io_Nova_OutputStream_virtual2_Nova_write;
		novaEnv.nova_standard_svg_SVGComponent.generateOutput__nova_standard_io_File = nova_VTable_nova_standard_svg_Nova_SVGComponent_val.nova_standard_svg_Nova_SVGComponent_virtual0_Nova_generateOutput;
		novaEnv.stabilitytest_PolymorphicSuperClass.toString = nova_VTable_stabilitytest_Nova_PolymorphicSuperClass_val.stabilitytest_Nova_PolymorphicSuperClass_virtual0_Nova_toString;
		
		nova_standard_Nova_StringNova_init_static(exceptionData);
		nova_standard_Nova_ObjectNova_init_static(exceptionData);
		nova_standard_Nova_SystemNova_init_static(exceptionData);
		nova_standard_database_Nova_DBConnectorNova_init_static(exceptionData);
		nova_standard_database_Nova_ResultSetNova_init_static(exceptionData);
		nova_standard_network_Nova_SocketNova_init_static(exceptionData);
		nova_standard_network_Nova_ServerSocketNova_init_static(exceptionData);
		nova_standard_network_Nova_ClientSocketNova_init_static(exceptionData);
		nova_standard_network_Nova_ConnectionSocketNova_init_static(exceptionData);
		nova_standard_network_Nova_NetworkInputStreamNova_init_static(exceptionData);
		nova_standard_network_Nova_NetworkOutputStreamNova_init_static(exceptionData);
		nova_standard_math_Nova_MathNova_init_static(exceptionData);
		nova_standard_math_Nova_StatementNova_init_static(exceptionData);
		nova_standard_math_Nova_NumericStatementNova_init_static(exceptionData);
		nova_standard_math_Nova_NumericTreeNova_init_static(exceptionData);
		nova_standard_math_Nova_NumericOperationNova_init_static(exceptionData);
		nova_standard_math_Nova_NumericOperandNova_init_static(exceptionData);
		nova_standard_math_Nova_StatementComponentNova_init_static(exceptionData);
		nova_standard_math_Nova_InvalidNumericStatementExceptionNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_ConclusionNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_HypothesisNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_LogicalConnectiveNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_StatementComponentNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_StatementLetterNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_WFFNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_StatementGroupNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_LogicalStatementNova_init_static(exceptionData);
		nova_standard_math_logic_Nova_InvalidFormulaExceptionNova_init_static(exceptionData);
		nova_standard_process_Nova_ProcessNova_init_static(exceptionData);
		nova_standard_primitive_Nova_BoolNova_init_static(exceptionData);
		nova_standard_primitive_Nova_NullNova_init_static(exceptionData);
		nova_standard_primitive_Nova_PrimitiveNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_CharNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_ByteNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_ShortNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_IntNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_LongNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_FloatNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_DoubleNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_NumberNova_init_static(exceptionData);
		nova_standard_time_Nova_TimeNova_init_static(exceptionData);
		nova_standard_time_Nova_DateNova_init_static(exceptionData);
		nova_standard_thread_Nova_ThreadNova_init_static(exceptionData);
		nova_standard_thread_Nova_UncaughtExceptionHandlerNova_init_static(exceptionData);
		nova_standard_io_Nova_InputStreamNova_init_static(exceptionData);
		nova_standard_io_Nova_OutputStreamNova_init_static(exceptionData);
		nova_standard_io_Nova_StreamReaderNova_init_static(exceptionData);
		nova_standard_io_Nova_FileNova_init_static(exceptionData);
		nova_standard_io_Nova_ConsoleNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGComponentNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGComponentListNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGComponentNodeNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGMainComponentNova_init_static(exceptionData);
		nova_standard_svg_Nova_SVGCircleNova_init_static(exceptionData);
		nova_standard_exception_Nova_ExceptionDataNova_init_static(exceptionData);
		nova_standard_exception_Nova_DivideByZeroExceptionNova_init_static(exceptionData);
		nova_standard_exception_Nova_ExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_ArrayListNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_QueueNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_ListNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_ListNodeNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_ArrayNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_StackNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_EmptyStackExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_HashMapNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_BoundsNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_TreeNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_BinaryTreeNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_NodeNova_init_static(exceptionData);
		nova_standard_datastruct_Nova_BinaryNodeNova_init_static(exceptionData);
		nova_standard_security_Nova_MD5Nova_init_static(exceptionData);
		nova_standard_star_Nova_WindowNova_init_static(exceptionData);
		nova_standard_star_Nova_WindowThreadNova_init_static(exceptionData);
		nova_standard_gc_Nova_GCNova_init_static(exceptionData);
		stabilitytest_Nova_StabilityTestNova_init_static(exceptionData);
		stabilitytest_Nova_TimeStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_FileStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_ThreadStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_ExceptionStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_SyntaxStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_ClosureStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_PolymorphismStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_PolymorphicSuperClassNova_init_static(exceptionData);
		stabilitytest_Nova_PolymorphicSubClassNova_init_static(exceptionData);
		stabilitytest_Nova_StabilityTestExceptionNova_init_static(exceptionData);
		stabilitytest_Nova_StabilityExceptionHandlerNova_init_static(exceptionData);
		stabilitytest_Nova_ThreadImplementationNova_init_static(exceptionData);
		stabilitytest_Nova_UnstableExceptionNova_init_static(exceptionData);
		stabilitytest_Nova_NetworkStabilityNova_init_static(exceptionData);
		stabilitytest_Nova_ClientThreadNova_init_static(exceptionData);
		
		args = (nova_standard_Nova_String**)NOVA_MALLOC(argc * sizeof(nova_standard_Nova_String));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_Nova_String_1_Nova_construct(0, 0, str);
		}
		
		TRY
		{
				stabilitytest_Nova_StabilityTest_Nova_main(0, exceptionData, args);
		}
		CATCH (1)
		{
				nova_standard_exception_Nova_Exception* base = (nova_standard_exception_Nova_Exception*)exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException;
				printf("Exception in Thread 'main': %s", base->nova_standard_exception_Nova_Exception_Nova_message->nova_standard_Nova_String_Nova_chars);
				nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		FreeConsole();
		NOVA_FREE(args);
		nova_standard_gc_Nova_GC_Nova_collect(0, exceptionData);
		
		
		return 0;
}
