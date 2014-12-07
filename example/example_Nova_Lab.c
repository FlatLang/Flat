#include <precompiled.h>
#include <example/example_Nova_Lab.h>


nova_VTable_example_Nova_Lab nova_VTable_example_Nova_Lab_val =
{
		nova_standard_Nova_Object_0_Nova_getHashCodeLong,
		nova_standard_Nova_Object_0_Nova_toString,
		nova_standard_Nova_Object_0_Nova_equals,
};

double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* l0_Nova_p);
void example_Nova_LabNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		{
		}
}

example_Nova_Lab* example_Nova_Lab_2_Nova_construct(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		CCLASS_NEW(example_Nova_Lab, this,);
		this->vtable = &nova_VTable_example_Nova_Lab_val;
		nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
		nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
		example_Nova_Lab_Nova_super(this, exceptionData);
		
		{
				example_Nova_Lab_Nova_this(this, exceptionData);
		}
		
		return this;
}

void example_Nova_Lab_Nova_destroy(example_Nova_Lab** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void example_Nova_Lab_Nova_main(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** l0_Nova_args)
{
		nova_standard_datastruct_Nova_BinaryTree* l1_Nova_tree;
		example_Nova_Square* l1_Nova_sq;
		nova_standard_math_Nova_NumericStatement* l1_Nova_s;
		
		l1_Nova_tree = nova_standard_datastruct_Nova_BinaryTree_2_Nova_construct(0, exceptionData);
		nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(l1_Nova_tree, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, 5)));
		nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(l1_Nova_tree, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, 7)));
		nova_standard_datastruct_Nova_BinaryTree_Nova_addNode(l1_Nova_tree, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, 3)));
		nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_datastruct_Nova_Tree_Nova_preorder((nova_standard_datastruct_Nova_Tree*)(l1_Nova_tree), exceptionData));
		l1_Nova_sq = example_Nova_Square_Nova_construct(0, exceptionData, 4);
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Double_Nova_construct(0, exceptionData, example_Nova_Lab_Nova_getArea((example_Nova_Lab*)nova_null, exceptionData, (example_Nova_Polygon*)(l1_Nova_sq)))));
		l1_Nova_s = nova_standard_math_Nova_NumericStatement_Nova_construct(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "5a + (3 * 2)"));
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, (nova_standard_Nova_Object*)(l1_Nova_s));
		nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

double example_Nova_Lab_Nova_getArea(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData, example_Nova_Polygon* l0_Nova_p)
{
		return l0_Nova_p->vtable->example_Nova_Polygon_virtual0_Nova_calculateArea(l0_Nova_p, exceptionData);
}

void example_Nova_Lab_Nova_this(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_Lab_Nova_super(example_Nova_Lab* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
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
		novaEnv.nova_standard_io_InputStream.readString = nova_VTable_nova_standard_io_Nova_InputStream_val.nova_standard_io_Nova_InputStream_virtual0_Nova_readString;
		novaEnv.nova_standard_io_InputStream.readBytes = nova_VTable_nova_standard_io_Nova_InputStream_val.nova_standard_io_Nova_InputStream_virtual0_Nova_readBytes;
		novaEnv.nova_standard_io_OutputStream.write__nova_standard_String = nova_VTable_nova_standard_io_Nova_OutputStream_val.nova_standard_io_Nova_OutputStream_virtual1_Nova_write;
		novaEnv.nova_standard_io_OutputStream.write__nova_standard_Object = nova_VTable_nova_standard_io_Nova_OutputStream_val.nova_standard_io_Nova_OutputStream_virtual2_Nova_write;
		novaEnv.nova_standard_svg_SVGComponent.generateOutput__nova_standard_io_File = nova_VTable_nova_standard_svg_Nova_SVGComponent_val.nova_standard_svg_Nova_SVGComponent_virtual0_Nova_generateOutput;
		novaEnv.example_Polygon.numberSides = nova_VTable_example_Nova_Polygon_val.example_Nova_Polygon_virtual0_Nova_numberSides;
		novaEnv.example_Polygon.calculateArea = nova_VTable_example_Nova_Polygon_val.example_Nova_Polygon_virtual0_Nova_calculateArea;
		
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
		nova_standard_datastruct_Nova_ComparableNova_init_static(exceptionData);
		nova_standard_security_Nova_MD5Nova_init_static(exceptionData);
		nova_standard_star_Nova_WindowNova_init_static(exceptionData);
		nova_standard_star_Nova_WindowThreadNova_init_static(exceptionData);
		nova_standard_gc_Nova_GCNova_init_static(exceptionData);
		example_Nova_LabNova_init_static(exceptionData);
		example_Nova_PolygonNova_init_static(exceptionData);
		example_Nova_SquareNova_init_static(exceptionData);
		
		args = (nova_standard_Nova_String**)NOVA_MALLOC(argc * sizeof(nova_standard_Nova_String));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_Nova_String_1_Nova_construct(0, 0, str);
		}
		
		TRY
		{
				example_Nova_Lab_Nova_main(0, exceptionData, args);
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
