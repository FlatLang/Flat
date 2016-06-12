#include <precompiled.h>
#include <example/example_Nova_ArrayDemo.h>

example_Extension_VTable_ArrayDemo example_Extension_VTable_ArrayDemo_val =
{
		{
				0,
				(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
		},
		nova_standard_Nova_Object_0_Nova_getHashCodeLong,
		nova_standard_Nova_Object_0_Nova_toString,
		nova_standard_Nova_Object_0_Nova_equals,
};


void example_Nova_ArrayDemoNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		{
		}
}

example_Nova_ArrayDemo* example_Nova_ArrayDemo_2_Nova_construct(example_Nova_ArrayDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		CCLASS_NEW(example_Nova_ArrayDemo, this,);
		this->vtable = &example_Extension_VTable_ArrayDemo_val;
		nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
		example_Nova_ArrayDemo_Nova_super(this, exceptionData);
		
		{
				example_Nova_ArrayDemo_2_Nova_this(this, exceptionData);
		}
		
		return this;
}

void example_Nova_ArrayDemo_Nova_destroy(example_Nova_ArrayDemo** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void example_Nova_ArrayDemo_Nova_main(example_Nova_ArrayDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_ArrayDemo_Nova_args)
{
		char l1_Nova_c;
		
		l1_Nova_c = 'y';
		while (l1_Nova_c == 'y' || l1_Nova_c == 'Y')
		{
				nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
				example_Nova_Animal* l1_Nova_animal;
				int l3_Nova_i;
				nova_standard_Nova_String* nova_local_1;
				int l5_Nova_i;
				int l7_Nova_i;
				
				l1_Nova_list = nova_standard_datastruct_list_Nova_Array_2_Nova_construct(0, exceptionData);
				nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Dog_2_Nova_construct(0, exceptionData)));
				nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Dog_2_Nova_construct(0, exceptionData)));
				nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Spider_2_Nova_construct(0, exceptionData)));
				nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Dog_2_Nova_construct(0, exceptionData)));
				nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Animal_2_Nova_construct(0, exceptionData)));
				nova_standard_datastruct_list_Nova_Array_0_Nova_add(l1_Nova_list, exceptionData, (nova_standard_Nova_Object*)(example_Nova_Spider_2_Nova_construct(0, exceptionData)));
				l3_Nova_i = 0;
				for (; l3_Nova_i < l1_Nova_list->nova_standard_datastruct_list_Nova_Array_Nova_size; l3_Nova_i++)
				{
						example_Nova_Animal* nova_local_0;
						
						nova_local_0 = (example_Nova_Animal*)(nova_standard_datastruct_list_Nova_Array_Nova_get(l1_Nova_list, exceptionData, l3_Nova_i));
						nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Contains: "), exceptionData, nova_local_0->vtable->example_Nova_Animal_virtual0_Nova_toString(nova_local_0, exceptionData)));
				}
				l1_Nova_animal = (example_Nova_Animal*)(nova_standard_datastruct_list_Nova_Array_Nova_remove(l1_Nova_list, exceptionData, 2));
				nova_local_1 = l1_Nova_animal->vtable->example_Nova_Animal_virtual0_Nova_toString(l1_Nova_animal, exceptionData);
				nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "--------- Removed: "), exceptionData, nova_local_1->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_1, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, " ----------"))));
				l5_Nova_i = 0;
				for (; l5_Nova_i < l1_Nova_list->nova_standard_datastruct_list_Nova_Array_Nova_size; l5_Nova_i++)
				{
						example_Nova_Animal* nova_local_2;
						
						nova_local_2 = (example_Nova_Animal*)(nova_standard_datastruct_list_Nova_Array_Nova_get(l1_Nova_list, exceptionData, l5_Nova_i));
						nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Contains: "), exceptionData, nova_local_2->vtable->example_Nova_Animal_virtual0_Nova_toString(nova_local_2, exceptionData)));
				}
				nova_standard_datastruct_list_Nova_Array_1_Nova_add(l1_Nova_list, exceptionData, 1, (nova_standard_Nova_Object*)(example_Nova_Spider_2_Nova_construct(0, exceptionData)));
				nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "--------- Added a new spider at index 1 ----------"));
				l7_Nova_i = 0;
				for (; l7_Nova_i < l1_Nova_list->nova_standard_datastruct_list_Nova_Array_Nova_size; l7_Nova_i++)
				{
						example_Nova_Animal* nova_local_3;
						
						nova_local_3 = (example_Nova_Animal*)(nova_standard_datastruct_list_Nova_Array_Nova_get(l1_Nova_list, exceptionData, l7_Nova_i));
						nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Contains: "), exceptionData, nova_local_3->vtable->example_Nova_Animal_virtual0_Nova_toString(nova_local_3, exceptionData)));
				}
				nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Run again? (Y/N)"));
				l1_Nova_c = nova_standard_io_Nova_Console_Nova_readChar(0, exceptionData);
		}
		nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "\nFinished"));
		nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_ArrayDemo_2_Nova_this(example_Nova_ArrayDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_ArrayDemo_Nova_super(example_Nova_ArrayDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
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
		nova_garbageData = malloc(sizeof(void*));
		nova_standard_gc_Nova_GC_Nova_init(0, exceptionData);
		
		nova_null = nova_standard_primitive_Nova_Null_2_Nova_construct(0, exceptionData);
		novaEnv.nova_standard_String.concat__nova_standard_String = nova_standard_Extension_VTable_String_val.nova_standard_Nova_String_virtual0_Nova_concat;
		novaEnv.nova_standard_String.toString = nova_standard_Extension_VTable_String_val.nova_standard_Nova_String_virtual0_Nova_toString;
		novaEnv.nova_standard_Object.getHashCodeLong = nova_standard_Extension_VTable_Object_val.nova_standard_Nova_Object_virtual0_Nova_getHashCodeLong;
		novaEnv.nova_standard_Object.toString = nova_standard_Extension_VTable_Object_val.nova_standard_Nova_Object_virtual0_Nova_toString;
		novaEnv.nova_standard_Object.equals__nova_standard_Object = nova_standard_Extension_VTable_Object_val.nova_standard_Nova_Object_virtual0_Nova_equals;
		novaEnv.nova_standard_math_NumericOperand.toString = nova_standard_math_Extension_VTable_NumericOperand_val.nova_standard_math_Nova_NumericOperand_virtual0_Nova_toString;
		novaEnv.nova_standard_primitive_number_Number.numDigits__nova_standard_primitive_number_Number = nova_standard_primitive_number_Extension_VTable_Number_val.nova_standard_primitive_number_Nova_Number_virtual0_Nova_numDigits;
		novaEnv.nova_standard_operators_Multiply.multiply__nova_standard_Object = nova_standard_operators_Extension_VTable_Multiply_val.itable.nova_standard_operators_Nova_Multiply_virtual0_Nova_multiply;
		novaEnv.nova_standard_operators_Equals.equals__nova_standard_Object = nova_standard_operators_Extension_VTable_Equals_val.itable.nova_standard_operators_Nova_Equals_virtual1_Nova_equals;
		novaEnv.nova_standard_thread_Thread.run = nova_standard_thread_Extension_VTable_Thread_val.nova_standard_thread_Nova_Thread_virtual0_Nova_run;
		novaEnv.nova_standard_io_InputStream.readString = nova_standard_io_Extension_VTable_InputStream_val.itable.nova_standard_io_Nova_InputStream_virtual0_Nova_readString;
		novaEnv.nova_standard_io_InputStream.readBytes = nova_standard_io_Extension_VTable_InputStream_val.itable.nova_standard_io_Nova_InputStream_virtual0_Nova_readBytes;
		novaEnv.nova_standard_io_OutputStream.write__nova_standard_String = nova_standard_io_Extension_VTable_OutputStream_val.nova_standard_io_Nova_OutputStream_virtual1_Nova_write;
		novaEnv.nova_standard_io_OutputStream.write__nova_standard_Object = nova_standard_io_Extension_VTable_OutputStream_val.nova_standard_io_Nova_OutputStream_virtual2_Nova_write;
		novaEnv.nova_standard_svg_SVGComponent.generateOutput__nova_standard_io_File = nova_standard_svg_Extension_VTable_SVGComponent_val.nova_standard_svg_Nova_SVGComponent_virtual0_Nova_generateOutput;
		novaEnv.nova_standard_datastruct_list_List.forEach__void = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_forEach;
		novaEnv.nova_standard_datastruct_list_List.any__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_any;
		novaEnv.nova_standard_datastruct_list_List.all__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_all;
		novaEnv.nova_standard_datastruct_list_List.filter__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_filter;
		novaEnv.nova_standard_datastruct_list_List.take__nova_standard_primitive_number_Int = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_take;
		novaEnv.nova_standard_datastruct_list_List.skip__nova_standard_primitive_number_Int = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_skip;
		novaEnv.nova_standard_datastruct_list_List.first = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_first;
		novaEnv.nova_standard_datastruct_list_List.last = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_last;
		novaEnv.nova_standard_datastruct_list_List.firstWhere__nova_standard_primitive_Bool = nova_standard_datastruct_list_Extension_VTable_List_val.itable.nova_standard_datastruct_list_Nova_List_virtual0_Nova_firstWhere;
		novaEnv.nova_standard_datastruct_list_Iterator.reset = nova_standard_datastruct_list_Extension_VTable_Iterator_val.itable.nova_standard_datastruct_list_Nova_Iterator_virtual0_Nova_reset;
		novaEnv.nova_standard_datastruct_Comparable.compareTo__nova_standard_Object = nova_standard_datastruct_Extension_VTable_Comparable_val.itable.nova_standard_datastruct_Nova_Comparable_virtual0_Nova_compareTo;
		novaEnv.example_Animal.getNumLegs = example_Extension_VTable_Animal_val.example_Nova_Animal_virtual0_Nova_getNumLegs;
		novaEnv.example_Animal.getNumEyes = example_Extension_VTable_Animal_val.example_Nova_Animal_virtual0_Nova_getNumEyes;
		novaEnv.example_Animal.getDescription = example_Extension_VTable_Animal_val.example_Nova_Animal_virtual0_Nova_getDescription;
		novaEnv.example_Person.sayHello = example_Extension_VTable_Person_val.example_Nova_Person_virtual0_Nova_sayHello;
		novaEnv.example_Polygon.numberSides = example_Extension_VTable_Polygon_val.itable.example_Nova_Polygon_virtual0_Nova_numberSides;
		novaEnv.example_Polygon.calculateArea = example_Extension_VTable_Polygon_val.itable.example_Nova_Polygon_virtual0_Nova_calculateArea;
		
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
		nova_standard_primitive_number_Nova_IntegerNova_init_static(exceptionData);
		nova_standard_primitive_number_Nova_RealNumberNova_init_static(exceptionData);
		nova_standard_operators_Nova_MultiplyNova_init_static(exceptionData);
		nova_standard_operators_Nova_EqualsNova_init_static(exceptionData);
		nova_standard_time_Nova_TimeNova_init_static(exceptionData);
		nova_standard_time_Nova_TimerNova_init_static(exceptionData);
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
		nova_standard_exception_Nova_UnimplementedOperationExceptionNova_init_static(exceptionData);
		nova_standard_exception_Nova_ExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_ListNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_LinkedListNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_ListNodeNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_ArrayNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IterableNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_IteratorNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_ArrayIteratorNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_NoSuchElementExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_QueueNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_StackNova_init_static(exceptionData);
		nova_standard_datastruct_list_Nova_EmptyStackExceptionNova_init_static(exceptionData);
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
		example_Nova_AnimalNova_init_static(exceptionData);
		example_Nova_ArrayDemoNova_init_static(exceptionData);
		example_Nova_BodyBuilderNova_init_static(exceptionData);
		example_Nova_ClosureDemoNova_init_static(exceptionData);
		example_Nova_DogNova_init_static(exceptionData);
		example_Nova_ExceptionHandlingDemoNova_init_static(exceptionData);
		example_Nova_FileTestNova_init_static(exceptionData);
		example_Nova_GenericDemoNova_init_static(exceptionData);
		example_Nova_HashMapDemoNova_init_static(exceptionData);
		example_Nova_IntegerTestNova_init_static(exceptionData);
		example_Nova_LabNova_init_static(exceptionData);
		example_Nova_MathDemoNova_init_static(exceptionData);
		example_Nova_NonWholeDivisionExceptionNova_init_static(exceptionData);
		example_Nova_PersonNova_init_static(exceptionData);
		example_Nova_PolygonNova_init_static(exceptionData);
		example_Nova_PolymorphismDemoNova_init_static(exceptionData);
		example_Nova_QueueDemoNova_init_static(exceptionData);
		example_Nova_SpiderNova_init_static(exceptionData);
		example_Nova_SquareNova_init_static(exceptionData);
		example_Nova_SVGTestNova_init_static(exceptionData);
		example_Nova_T1Nova_init_static(exceptionData);
		example_Nova_T2Nova_init_static(exceptionData);
		example_Nova_TestNova_init_static(exceptionData);
		example_Nova_ThreadDemoNova_init_static(exceptionData);
		example_Nova_ThreadDemoImplementationNova_init_static(exceptionData);
		example_ackermann_Nova_AckermannNova_init_static(exceptionData);
		example_copy_Nova_DogNova_init_static(exceptionData);
		example_database_Nova_DatabaseDemoNova_init_static(exceptionData);
		example_network_Nova_ClientDemoNova_init_static(exceptionData);
		example_network_Nova_ConnectionThreadNova_init_static(exceptionData);
		example_network_Nova_OutputThreadNova_init_static(exceptionData);
		example_network_Nova_ServerDemoNova_init_static(exceptionData);
		
		args = (nova_standard_Nova_String**)NOVA_MALLOC(argc * sizeof(nova_standard_Nova_String));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_Nova_String_1_Nova_construct(0, 0, str);
		}
		
		TRY
		{
				example_Nova_ArrayDemo_Nova_main(0, exceptionData, args);
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
