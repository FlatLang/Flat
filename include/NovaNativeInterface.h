#ifndef NOVA_NATIVE_INTERFACE
#define NOVA_NATIVE_INTERFACE

#include <nova/nova_Nova_Class.h>
#include <nova/nova_Nova_Object.h>
#include <nova/nova_Nova_String.h>
#include <nova/nova_Nova_System.h>
#include <nova/database/nova_database_Nova_DBConnector.h>
#include <nova/database/nova_database_Nova_ResultSet.h>
#include <nova/datastruct/nova_datastruct_Nova_BinaryNode.h>
#include <nova/datastruct/nova_datastruct_Nova_BinaryTree.h>
#include <nova/datastruct/nova_datastruct_Nova_Bounds.h>
#include <nova/datastruct/nova_datastruct_Nova_Comparable.h>
#include <nova/datastruct/nova_datastruct_Nova_HashMap.h>
#include <nova/datastruct/nova_datastruct_Nova_Node.h>
#include <nova/datastruct/nova_datastruct_Nova_Pair.h>
#include <nova/datastruct/nova_datastruct_Nova_ReversibleHashMap.h>
#include <nova/datastruct/nova_datastruct_Nova_Tree.h>
#include <nova/datastruct/nova_datastruct_Nova_Vector.h>
#include <nova/datastruct/nova_datastruct_Nova_Vector2D.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Array.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_ArrayIterator.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_CharArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_CharArrayIterator.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_CompiledList.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_DoubleArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_DoubleArrayIterator.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_EmptyStackException.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntArrayIterator.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntRange.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntRangeIterator.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Iterable.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Iterator.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_LinkedList.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_LinkedListIterator.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_List.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_ListNode.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_NoSuchElementException.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Queue.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Stack.h>
#include <nova/exception/nova_exception_Nova_DivideByZeroException.h>
#include <nova/exception/nova_exception_Nova_Exception.h>
#include <nova/exception/nova_exception_Nova_ExceptionData.h>
#include <nova/exception/nova_exception_Nova_UnimplementedOperationException.h>
#include <nova/gc/nova_gc_Nova_GC.h>
#include <nova/io/nova_io_Nova_Console.h>
#include <nova/io/nova_io_Nova_File.h>
#include <nova/io/nova_io_Nova_FileNotFoundException.h>
#include <nova/io/nova_io_Nova_InputStream.h>
#include <nova/io/nova_io_Nova_OutputStream.h>
#include <nova/io/nova_io_Nova_StreamReader.h>
#include <nova/math/nova_math_Nova_ArithmeticSequence.h>
#include <nova/math/nova_math_Nova_Diekstra.h>
#include <nova/math/nova_math_Nova_GeometricSequence.h>
#include <nova/math/nova_math_Nova_Graph.h>
#include <nova/math/nova_math_Nova_InvalidNumericStatementException.h>
#include <nova/math/nova_math_Nova_Math.h>
#include <nova/math/nova_math_Nova_Matrix.h>
#include <nova/math/nova_math_Nova_NumericOperand.h>
#include <nova/math/nova_math_Nova_NumericOperation.h>
#include <nova/math/nova_math_Nova_NumericStatement.h>
#include <nova/math/nova_math_Nova_NumericTree.h>
#include <nova/math/nova_math_Nova_Polynomial.h>
#include <nova/math/nova_math_Nova_Sequence.h>
#include <nova/math/nova_math_Nova_Statement.h>
#include <nova/math/nova_math_Nova_StatementComponent.h>
#include <nova/math/nova_math_Nova_VariableOperand.h>
#include <nova/math/calculus/nova_math_calculus_Nova_Calculus.h>
#include <nova/math/huffman/nova_math_huffman_Nova_HuffmanTree.h>
#include <nova/math/logic/nova_math_logic_Nova_Conclusion.h>
#include <nova/math/logic/nova_math_logic_Nova_Hypothesis.h>
#include <nova/math/logic/nova_math_logic_Nova_InvalidFormulaException.h>
#include <nova/math/logic/nova_math_logic_Nova_LogicalConnective.h>
#include <nova/math/logic/nova_math_logic_Nova_LogicalStatement.h>
#include <nova/math/logic/nova_math_logic_Nova_StatementComponent.h>
#include <nova/math/logic/nova_math_logic_Nova_StatementGroup.h>
#include <nova/math/logic/nova_math_logic_Nova_StatementLetter.h>
#include <nova/math/logic/nova_math_logic_Nova_WFF.h>
#include <nova/network/nova_network_Nova_ClientSocket.h>
#include <nova/network/nova_network_Nova_ConnectionSocket.h>
#include <nova/network/nova_network_Nova_NetworkInputStream.h>
#include <nova/network/nova_network_Nova_NetworkOutputStream.h>
#include <nova/network/nova_network_Nova_ServerSocket.h>
#include <nova/network/nova_network_Nova_Socket.h>
#include <nova/operators/nova_operators_Nova_Equals.h>
#include <nova/operators/nova_operators_Nova_Multiply.h>
#include <nova/primitive/nova_primitive_Nova_Bool.h>
#include <nova/primitive/nova_primitive_Nova_Null.h>
#include <nova/primitive/nova_primitive_Nova_Primitive.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Byte.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Char.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Double.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Float.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Int.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Integer.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Long.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Number.h>
#include <nova/primitive/number/nova_primitive_number_Nova_RealNumber.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Short.h>
#include <nova/process/nova_process_Nova_Process.h>
#include <nova/security/nova_security_Nova_MD5.h>
#include <nova/star/nova_star_Nova_Window.h>
#include <nova/star/nova_star_Nova_WindowThread.h>
#include <nova/svg/nova_svg_Nova_Svg.h>
#include <nova/svg/nova_svg_Nova_SvgCircle.h>
#include <nova/svg/nova_svg_Nova_SvgComponent.h>
#include <nova/svg/nova_svg_Nova_SvgComponentList.h>
#include <nova/svg/nova_svg_Nova_SvgComponentNode.h>
#include <nova/svg/nova_svg_Nova_SvgMainComponent.h>
#include <nova/svg/no3/nova_svg_no3_Nova_No3.h>
#include <nova/svg/no3/nova_svg_no3_Nova_No3Node.h>
#include <nova/svg/no3/nova_svg_no3_Nova_No3Selection.h>
#include <nova/thread/nova_thread_Nova_Thread.h>
#include <nova/thread/nova_thread_Nova_UncaughtExceptionHandler.h>
#include <nova/thread/async/nova_thread_async_Nova_Async.h>
#include <nova/thread/async/nova_thread_async_Nova_AsyncResult.h>
#include <nova/time/nova_time_Nova_Date.h>
#include <nova/time/nova_time_Nova_Time.h>
#include <nova/time/nova_time_Nova_Timer.h>
#include <nova/web/js/json/nova_web_js_json_Nova_Json.h>
#include <nova/web/svg/nova_web_svg_Nova_Svg.h>
#include <nova/web/svg/nova_web_svg_Nova_SvgCircle.h>
#include <nova/web/svg/nova_web_svg_Nova_SvgComponent.h>
#include <nova/web/svg/nova_web_svg_Nova_SvgComponentList.h>
#include <nova/web/svg/nova_web_svg_Nova_SvgComponentNode.h>
#include <nova/web/svg/nova_web_svg_Nova_SvgMainComponent.h>
#include <nova/web/svg/no3/nova_web_svg_no3_Nova_No3.h>
#include <nova/web/svg/no3/nova_web_svg_no3_Nova_No3Node.h>
#include <nova/web/svg/no3/nova_web_svg_no3_Nova_No3Select.h>
#include <nova/web/svg/no3/nova_web_svg_no3_Nova_No3SelectAll.h>
#include <example/example_Nova_Animal.h>
#include <example/example_Nova_ArrayDemo.h>
#include <example/example_Nova_BodyBuilder.h>
#include <example/example_Nova_ClosureDemo.h>
#include <example/example_Nova_Dog.h>
#include <example/example_Nova_ExceptionHandlingDemo.h>
#include <example/example_Nova_FileTest.h>
#include <example/example_Nova_GenericDemo.h>
#include <example/example_Nova_HashMapDemo.h>
#include <example/example_Nova_IntegerTest.h>
#include <example/example_Nova_Lab.h>
#include <example/example_Nova_MathDemo.h>
#include <example/example_Nova_NonWholeDivisionException.h>
#include <example/example_Nova_Person.h>
#include <example/example_Nova_Polygon.h>
#include <example/example_Nova_PolymorphismDemo.h>
#include <example/example_Nova_QueueDemo.h>
#include <example/example_Nova_Spider.h>
#include <example/example_Nova_Square.h>
#include <example/example_Nova_SvgChart.h>
#include <example/example_Nova_SvgFractal.h>
#include <example/example_Nova_T1.h>
#include <example/example_Nova_T2.h>
#include <example/example_Nova_Test.h>
#include <example/example_Nova_ThreadDemo.h>
#include <example/example_Nova_ThreadDemoImplementation.h>
#include <example/ackermann/example_ackermann_Nova_Ackermann.h>
#include <example/copy/example_copy_Nova_Dog.h>
#include <example/database/example_database_Nova_DatabaseDemo.h>
#include <example/network/example_network_Nova_ClientDemo.h>
#include <example/network/example_network_Nova_ConnectionThread.h>
#include <example/network/example_network_Nova_OutputThread.h>
#include <example/network/example_network_Nova_ServerDemo.h>
#include <stabilitytest/stabilitytest_Nova_AssignmentStability.h>
#include <stabilitytest/stabilitytest_Nova_ClassWithProperties.h>
#include <stabilitytest/stabilitytest_Nova_ClientThread.h>
#include <stabilitytest/stabilitytest_Nova_ClosureStability.h>
#include <stabilitytest/stabilitytest_Nova_ExceptionStability.h>
#include <stabilitytest/stabilitytest_Nova_FileStability.h>
#include <stabilitytest/stabilitytest_Nova_LambdaStability.h>
#include <stabilitytest/stabilitytest_Nova_NetworkStability.h>
#include <stabilitytest/stabilitytest_Nova_PolymorphicSubClass.h>
#include <stabilitytest/stabilitytest_Nova_PolymorphicSuperClass.h>
#include <stabilitytest/stabilitytest_Nova_PolymorphismStability.h>
#include <stabilitytest/stabilitytest_Nova_StabilityExceptionHandler.h>
#include <stabilitytest/stabilitytest_Nova_StabilityTest.h>
#include <stabilitytest/stabilitytest_Nova_StabilityTestCase.h>
#include <stabilitytest/stabilitytest_Nova_StabilityTestException.h>
#include <stabilitytest/stabilitytest_Nova_SyntaxStability.h>
#include <stabilitytest/stabilitytest_Nova_ThreadImplementation.h>
#include <stabilitytest/stabilitytest_Nova_ThreadStability.h>
#include <stabilitytest/stabilitytest_Nova_TimeStability.h>
#include <stabilitytest/stabilitytest_Nova_ToStringStability.h>
#include <stabilitytest/stabilitytest_Nova_UnstableException.h>

typedef nova_Nova_Class* (*nova_Nova_Class_native_Nova_Class)(nova_Nova_Class*, nova_exception_Nova_ExceptionData*);

typedef struct nova_native_Class
{
nova_Nova_Class_native_Nova_Class Class;
} nova_native_Class;

typedef nova_Nova_String* (*nova_Nova_Object_native0_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_Nova_Object_native0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_Nova_Object_native_Nova_Object)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);

typedef struct nova_native_Object
{
nova_Nova_Object_native0_Nova_toString toString;
nova_Nova_Object_native0_Nova_equals equals__nova_Object;
nova_Nova_Object_native_Nova_Object Object;
} nova_native_Object;

typedef nova_Nova_String* (*nova_Nova_String_native0_Nova_concat)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef char (*nova_Nova_String_native_Nova_equals)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef char (*nova_Nova_String_native_Nova_contains)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef int (*nova_Nova_String_native1_Nova_indexOf)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef int (*nova_Nova_String_native2_Nova_indexOf)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, int);
typedef int (*nova_Nova_String_native_Nova_lastIndexOf)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_Nova_String_native0_Nova_substring)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, int, int);
typedef nova_Nova_String* (*nova_Nova_String_native1_Nova_substring)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, int);
typedef char (*nova_Nova_String_native_Nova_lastChar)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_Nova_String_native_Nova_charAt)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_trim)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_toLowerCase)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_toUpperCase)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_capitalize)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_transform)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String_closure3_Nova_transform nova_Nova_String_Nova_transform, void* nova_Nova_String_ref_Nova_transform, void* transform_context);
typedef nova_Nova_String* (*nova_Nova_String_native0_Nova_getStringBetween)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_Nova_String_native1_Nova_getStringBetween)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, int);
typedef int (*nova_Nova_String_native_Nova_compareTo)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_Nova_String_native0_Nova_toString)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_Nova_String_native0_Nova_String)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, char);
typedef nova_Nova_String* (*nova_Nova_String_native1_Nova_String)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, char*);

typedef struct nova_native_String
{
nova_Nova_String_native0_Nova_concat concat__nova_String;
nova_Nova_String_native_Nova_equals equals;
nova_Nova_String_native_Nova_contains contains;
nova_Nova_String_native1_Nova_indexOf indexOf__nova_String;
nova_Nova_String_native2_Nova_indexOf indexOf__nova_String__nova_primitive_number_Int;
nova_Nova_String_native_Nova_lastIndexOf lastIndexOf;
nova_Nova_String_native0_Nova_substring substring__nova_primitive_number_Int__nova_primitive_number_Int;
nova_Nova_String_native1_Nova_substring substring__nova_primitive_number_Int;
nova_Nova_String_native_Nova_lastChar lastChar;
nova_Nova_String_native_Nova_charAt charAt;
nova_Nova_String_native_Nova_trim trim;
nova_Nova_String_native_Nova_toLowerCase toLowerCase;
nova_Nova_String_native_Nova_toUpperCase toUpperCase;
nova_Nova_String_native_Nova_capitalize capitalize;
nova_Nova_String_native_Nova_transform transform;
nova_Nova_String_native0_Nova_getStringBetween getStringBetween__nova_String__nova_String;
nova_Nova_String_native1_Nova_getStringBetween getStringBetween__nova_String__nova_String__nova_primitive_number_Int;
nova_Nova_String_native_Nova_compareTo compareTo;
nova_Nova_String_native0_Nova_toString toString;
nova_Nova_String_native0_Nova_String String__nova_primitive_number_Char;
nova_Nova_String_native1_Nova_String String__Array1d_nova_primitive_number_Char;
} nova_native_String;

typedef void (*nova_Nova_System_native0_Nova_exit)(nova_Nova_System*, nova_exception_Nova_ExceptionData*, int);
typedef void (*nova_Nova_System_native1_Nova_exit)(nova_Nova_System*, nova_exception_Nova_ExceptionData*, int, nova_Nova_String*);
typedef void (*nova_Nova_System_native2_Nova_exit)(nova_Nova_System*, nova_exception_Nova_ExceptionData*, int, nova_Nova_String*, char);
typedef nova_process_Nova_Process* (*nova_Nova_System_native_Nova_execute)(nova_Nova_System*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_System* (*nova_Nova_System_native_Nova_System)(nova_Nova_System*, nova_exception_Nova_ExceptionData*);

typedef struct nova_native_System
{
nova_Nova_System_native0_Nova_exit exit__nova_primitive_number_Int;
nova_Nova_System_native1_Nova_exit exit__nova_primitive_number_Int__nova_String;
nova_Nova_System_native2_Nova_exit exit__nova_primitive_number_Int__nova_String__nova_primitive_Bool;
nova_Nova_System_native_Nova_execute execute;
nova_Nova_System_native_Nova_System System;
} nova_native_System;

typedef void (*nova_database_Nova_DBConnector_native0_Nova_connect)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*);
typedef void (*nova_database_Nova_DBConnector_native1_Nova_connect)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*);
typedef void (*nova_database_Nova_DBConnector_native2_Nova_connect)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*, int, nova_Nova_String*, int);
typedef void (*nova_database_Nova_DBConnector_native_Nova_updateError)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_database_Nova_DBConnector_native_Nova_changeUser)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*);
typedef nova_database_Nova_ResultSet* (*nova_database_Nova_DBConnector_native_Nova_query)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef void (*nova_database_Nova_DBConnector_native_Nova_close)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*);
typedef nova_database_Nova_DBConnector* (*nova_database_Nova_DBConnector_native_Nova_DBConnector)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*);

typedef struct nova_database_native_DBConnector
{
nova_database_Nova_DBConnector_native0_Nova_connect connect__nova_String__nova_String__nova_String;
nova_database_Nova_DBConnector_native1_Nova_connect connect__nova_String__nova_String__nova_String__nova_String;
nova_database_Nova_DBConnector_native2_Nova_connect connect__nova_String__nova_String__nova_String__nova_String__nova_primitive_number_Int__nova_String__nova_primitive_number_Int;
nova_database_Nova_DBConnector_native_Nova_updateError updateError;
nova_database_Nova_DBConnector_native_Nova_changeUser changeUser;
nova_database_Nova_DBConnector_native_Nova_query query;
nova_database_Nova_DBConnector_native_Nova_close close;
nova_database_Nova_DBConnector_native_Nova_DBConnector DBConnector;
} nova_database_native_DBConnector;

typedef nova_database_Nova_ResultSet* (*nova_database_Nova_ResultSet_native_Nova_ResultSet)(nova_database_Nova_ResultSet*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*, int);

typedef struct nova_database_native_ResultSet
{
nova_database_Nova_ResultSet_native_Nova_ResultSet ResultSet;
} nova_database_native_ResultSet;

typedef void (*nova_datastruct_Nova_BinaryNode_native_Nova_addChild)(nova_datastruct_Nova_BinaryNode*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Comparable*);
typedef nova_datastruct_Nova_BinaryNode* (*nova_datastruct_Nova_BinaryNode_native0_Nova_BinaryNode)(nova_datastruct_Nova_BinaryNode*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_BinaryNode* (*nova_datastruct_Nova_BinaryNode_native1_Nova_BinaryNode)(nova_datastruct_Nova_BinaryNode*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Comparable*);

typedef struct nova_datastruct_native_BinaryNode
{
nova_datastruct_Nova_BinaryNode_native_Nova_addChild addChild;
nova_datastruct_Nova_BinaryNode_native0_Nova_BinaryNode BinaryNode;
nova_datastruct_Nova_BinaryNode_native1_Nova_BinaryNode BinaryNode__nova_datastruct_Comparable;
} nova_datastruct_native_BinaryNode;

typedef nova_datastruct_Nova_BinaryTree* (*nova_datastruct_Nova_BinaryTree_native_Nova_addNode)(nova_datastruct_Nova_BinaryTree*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Comparable*);
typedef nova_datastruct_Nova_BinaryTree* (*nova_datastruct_Nova_BinaryTree_native_Nova_addNodes)(nova_datastruct_Nova_BinaryTree*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef nova_datastruct_Nova_BinaryTree* (*nova_datastruct_Nova_BinaryTree_native_Nova_BinaryTree)(nova_datastruct_Nova_BinaryTree*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);

typedef struct nova_datastruct_native_BinaryTree
{
nova_datastruct_Nova_BinaryTree_native_Nova_addNode addNode;
nova_datastruct_Nova_BinaryTree_native_Nova_addNodes addNodes;
nova_datastruct_Nova_BinaryTree_native_Nova_BinaryTree BinaryTree;
} nova_datastruct_native_BinaryTree;

typedef nova_Nova_String* (*nova_datastruct_Nova_Bounds_native_Nova_extractString)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_datastruct_Nova_Bounds_native_Nova_extractPreString)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_datastruct_Nova_Bounds_native_Nova_extractPostString)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_datastruct_Nova_Bounds_native_Nova_trimString)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef void (*nova_datastruct_Nova_Bounds_native_Nova_invalidate)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_datastruct_Nova_Bounds_native0_Nova_equals)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Bounds*);
typedef nova_Nova_String* (*nova_datastruct_Nova_Bounds_native0_Nova_toString)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_datastruct_Nova_Bounds_native_Nova_cloneTo)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Bounds*);
typedef nova_datastruct_Nova_Bounds* (*nova_datastruct_Nova_Bounds_native_Nova_clone)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Bounds* (*nova_datastruct_Nova_Bounds_native0_Nova_Bounds)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Bounds* (*nova_datastruct_Nova_Bounds_native1_Nova_Bounds)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct nova_datastruct_native_Bounds
{
nova_datastruct_Nova_Bounds_native_Nova_extractString extractString;
nova_datastruct_Nova_Bounds_native_Nova_extractPreString extractPreString;
nova_datastruct_Nova_Bounds_native_Nova_extractPostString extractPostString;
nova_datastruct_Nova_Bounds_native_Nova_trimString trimString;
nova_datastruct_Nova_Bounds_native_Nova_invalidate invalidate;
nova_datastruct_Nova_Bounds_native0_Nova_equals equals__nova_datastruct_Bounds;
nova_datastruct_Nova_Bounds_native0_Nova_toString toString;
nova_datastruct_Nova_Bounds_native_Nova_cloneTo cloneTo;
nova_datastruct_Nova_Bounds_native_Nova_clone clone;
nova_datastruct_Nova_Bounds_native0_Nova_Bounds Bounds;
nova_datastruct_Nova_Bounds_native1_Nova_Bounds Bounds__nova_primitive_number_Int__nova_primitive_number_Int;
} nova_datastruct_native_Bounds;

typedef int (*nova_datastruct_Nova_Comparable_native0_Nova_compareTo)(nova_datastruct_Nova_Comparable*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_datastruct_native_Comparable
{
nova_datastruct_Nova_Comparable_native0_Nova_compareTo compareTo__nova_Object;
} nova_datastruct_native_Comparable;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_HashMap_native_Nova_toArray)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_datastruct_Nova_HashMap_native_Nova_contains)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*);
typedef char (*nova_datastruct_Nova_HashMap_native_Nova_any)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure3_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_Nova_HashMap_native_Nova_all)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure6_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_HashMap_native_Nova_map)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure9_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_HashMap_native_Nova_filter)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure12_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
typedef nova_Nova_String* (*nova_datastruct_Nova_HashMap_native_Nova_join)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_datastruct_Nova_Pair* (*nova_datastruct_Nova_HashMap_native_Nova_skip)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_Nova_Pair* (*nova_datastruct_Nova_HashMap_native_Nova_take)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_HashMap_native_Nova_reverse)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Pair* (*nova_datastruct_Nova_HashMap_native_Nova_firstWhere)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure15_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
typedef void (*nova_datastruct_Nova_HashMap_native_Nova_forEach)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure18_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
typedef nova_datastruct_Nova_HashMap* (*nova_datastruct_Nova_HashMap_native0_Nova_put)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashMap_native_Nova_get)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashMap_native_Nova_remove)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef char (*nova_datastruct_Nova_HashMap_native_Nova_containsKey)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_Nova_HashMap* (*nova_datastruct_Nova_HashMap_native0_Nova_HashMap)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_HashMap* (*nova_datastruct_Nova_HashMap_native1_Nova_HashMap)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct nova_datastruct_native_HashMap
{
nova_datastruct_Nova_HashMap_native_Nova_toArray toArray;
nova_datastruct_Nova_HashMap_native_Nova_contains contains;
nova_datastruct_Nova_HashMap_native_Nova_any any;
nova_datastruct_Nova_HashMap_native_Nova_all all;
nova_datastruct_Nova_HashMap_native_Nova_map map;
nova_datastruct_Nova_HashMap_native_Nova_filter filter;
nova_datastruct_Nova_HashMap_native_Nova_join join;
nova_datastruct_Nova_HashMap_native_Nova_skip skip;
nova_datastruct_Nova_HashMap_native_Nova_take take;
nova_datastruct_Nova_HashMap_native_Nova_reverse reverse;
nova_datastruct_Nova_HashMap_native_Nova_firstWhere firstWhere;
nova_datastruct_Nova_HashMap_native_Nova_forEach forEach;
nova_datastruct_Nova_HashMap_native0_Nova_put put__nova_Object__nova_Object;
nova_datastruct_Nova_HashMap_native_Nova_get get;
nova_datastruct_Nova_HashMap_native_Nova_remove remove;
nova_datastruct_Nova_HashMap_native_Nova_containsKey containsKey;
nova_datastruct_Nova_HashMap_native0_Nova_HashMap HashMap;
nova_datastruct_Nova_HashMap_native1_Nova_HashMap HashMap__nova_primitive_number_Int__nova_primitive_number_Int;
} nova_datastruct_native_HashMap;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Node_native0_Nova_preorder)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Node_native0_Nova_inorder)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Node_native0_Nova_postorder)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Node_native0_Nova_levelorder)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_Nova_Node_native0_Nova_toString)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Node* (*nova_datastruct_Nova_Node_native0_Nova_Node)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Node* (*nova_datastruct_Nova_Node_native1_Nova_Node)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_Nova_Node* (*nova_datastruct_Nova_Node_native2_Nova_Node)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_Nova_Node* (*nova_datastruct_Nova_Node_native3_Nova_Node)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, int);

typedef struct nova_datastruct_native_Node
{
nova_datastruct_Nova_Node_native0_Nova_preorder preorder;
nova_datastruct_Nova_Node_native0_Nova_inorder inorder;
nova_datastruct_Nova_Node_native0_Nova_postorder postorder;
nova_datastruct_Nova_Node_native0_Nova_levelorder levelorder;
nova_datastruct_Nova_Node_native0_Nova_toString toString;
nova_datastruct_Nova_Node_native0_Nova_Node Node;
nova_datastruct_Nova_Node_native1_Nova_Node Node__nova_Object;
nova_datastruct_Nova_Node_native2_Nova_Node Node__nova_primitive_number_Int;
nova_datastruct_Nova_Node_native3_Nova_Node Node__nova_Object__nova_primitive_number_Int;
} nova_datastruct_native_Node;

typedef nova_Nova_String* (*nova_datastruct_Nova_Pair_native0_Nova_toString)(nova_datastruct_Nova_Pair*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Pair* (*nova_datastruct_Nova_Pair_native_Nova_Pair)(nova_datastruct_Nova_Pair*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, nova_Nova_Object*);

typedef struct nova_datastruct_native_Pair
{
nova_datastruct_Nova_Pair_native0_Nova_toString toString;
nova_datastruct_Nova_Pair_native_Nova_Pair Pair;
} nova_datastruct_native_Pair;

typedef void (*nova_datastruct_Nova_ReversibleHashMap_native_Nova_put)(nova_datastruct_Nova_ReversibleHashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_ReversibleHashMap_native_Nova_getKey)(nova_datastruct_Nova_ReversibleHashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_ReversibleHashMap_native_Nova_getValue)(nova_datastruct_Nova_ReversibleHashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_Nova_ReversibleHashMap* (*nova_datastruct_Nova_ReversibleHashMap_native_Nova_ReversibleHashMap)(nova_datastruct_Nova_ReversibleHashMap*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_native_ReversibleHashMap
{
nova_datastruct_Nova_ReversibleHashMap_native_Nova_put put;
nova_datastruct_Nova_ReversibleHashMap_native_Nova_getKey getKey;
nova_datastruct_Nova_ReversibleHashMap_native_Nova_getValue getValue;
nova_datastruct_Nova_ReversibleHashMap_native_Nova_ReversibleHashMap ReversibleHashMap;
} nova_datastruct_native_ReversibleHashMap;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Tree_native_Nova_preorder)(nova_datastruct_Nova_Tree*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Tree_native_Nova_inorder)(nova_datastruct_Nova_Tree*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Tree_native_Nova_postorder)(nova_datastruct_Nova_Tree*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Tree_native_Nova_levelorder)(nova_datastruct_Nova_Tree*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Tree* (*nova_datastruct_Nova_Tree_native_Nova_Tree)(nova_datastruct_Nova_Tree*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_native_Tree
{
nova_datastruct_Nova_Tree_native_Nova_preorder preorder;
nova_datastruct_Nova_Tree_native_Nova_inorder inorder;
nova_datastruct_Nova_Tree_native_Nova_postorder postorder;
nova_datastruct_Nova_Tree_native_Nova_levelorder levelorder;
nova_datastruct_Nova_Tree_native_Nova_Tree Tree;
} nova_datastruct_native_Tree;

typedef nova_datastruct_Nova_Vector* (*nova_datastruct_Nova_Vector_native_Nova_Vector)(nova_datastruct_Nova_Vector*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_native_Vector
{
nova_datastruct_Nova_Vector_native_Nova_Vector Vector;
} nova_datastruct_native_Vector;

typedef nova_datastruct_Nova_Vector2D* (*nova_datastruct_Nova_Vector2D_native_Nova_Vector2D)(nova_datastruct_Nova_Vector2D*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_native_Vector2D
{
nova_datastruct_Nova_Vector2D_native_Nova_Vector2D Vector2D;
} nova_datastruct_native_Vector2D;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native_Nova_fillRemaining)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native_Nova_addAll)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_add)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef void (*nova_datastruct_list_Nova_Array_native1_Nova_add)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Array_native_Nova_remove)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int);
typedef void (*nova_datastruct_list_Nova_Array_native_Nova_swap)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int, int);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Array_native0_Nova_get)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int);
typedef void (*nova_datastruct_list_Nova_Array_native_Nova_set)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int, nova_Nova_Object*);
typedef char (*nova_datastruct_list_Nova_Array_native0_Nova_contains)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_toArray)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_map)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array_closure3_Nova_mapFunc nova_datastruct_list_Nova_Array_Nova_mapFunc, void* nova_datastruct_list_Nova_Array_ref_Nova_mapFunc, void* mapFunc_context);
typedef void (*nova_datastruct_list_Nova_Array_native0_Nova_forEach)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array_closure6_Nova_func nova_datastruct_list_Nova_Array_Nova_func, void* nova_datastruct_list_Nova_Array_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_list_Nova_Array_native0_Nova_any)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array_closure9_Nova_anyFunc nova_datastruct_list_Nova_Array_Nova_anyFunc, void* nova_datastruct_list_Nova_Array_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_Array_native0_Nova_all)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array_closure12_Nova_allFunc nova_datastruct_list_Nova_Array_Nova_allFunc, void* nova_datastruct_list_Nova_Array_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_filter)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array_closure15_Nova_filterFunc nova_datastruct_list_Nova_Array_Nova_filterFunc, void* nova_datastruct_list_Nova_Array_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_take)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_skip)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Array_native0_Nova_firstWhere)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array_closure18_Nova_func nova_datastruct_list_Nova_Array_Nova_func, void* nova_datastruct_list_Nova_Array_ref_Nova_func, void* func_context);
typedef long_long (*nova_datastruct_list_Nova_Array_native_Nova_sumSize)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_reverse)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_Array_native0_Nova_join)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_Array_native0_Nova_toString)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_Array)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native1_Nova_Array)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native2_Nova_Array)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_Nova_Object**, int);

typedef struct nova_datastruct_list_native_Array
{
nova_datastruct_list_Nova_Array_native_Nova_fillRemaining fillRemaining;
nova_datastruct_list_Nova_Array_native_Nova_addAll addAll;
nova_datastruct_list_Nova_Array_native0_Nova_add add__nova_Object;
nova_datastruct_list_Nova_Array_native1_Nova_add add__nova_primitive_number_Int__nova_Object;
nova_datastruct_list_Nova_Array_native_Nova_remove remove;
nova_datastruct_list_Nova_Array_native_Nova_swap swap;
nova_datastruct_list_Nova_Array_native0_Nova_get get__nova_primitive_number_Int;
nova_datastruct_list_Nova_Array_native_Nova_set set;
nova_datastruct_list_Nova_Array_native0_Nova_contains contains__nova_Object;
nova_datastruct_list_Nova_Array_native0_Nova_toArray toArray;
nova_datastruct_list_Nova_Array_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_Array_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_Array_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_list_Nova_Array_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_list_Nova_Array_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_Array_native0_Nova_take take__nova_primitive_number_Int;
nova_datastruct_list_Nova_Array_native0_Nova_skip skip__nova_primitive_number_Int;
nova_datastruct_list_Nova_Array_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_list_Nova_Array_native_Nova_sumSize sumSize;
nova_datastruct_list_Nova_Array_native0_Nova_reverse reverse;
nova_datastruct_list_Nova_Array_native0_Nova_join join__nova_String;
nova_datastruct_list_Nova_Array_native0_Nova_toString toString;
nova_datastruct_list_Nova_Array_native0_Nova_Array Array;
nova_datastruct_list_Nova_Array_native1_Nova_Array Array__nova_primitive_number_Int;
nova_datastruct_list_Nova_Array_native2_Nova_Array Array__Array1d_nova_Object__nova_primitive_number_Int;
} nova_datastruct_list_native_Array;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_ArrayIterator_native_Nova_reset)(nova_datastruct_list_Nova_ArrayIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_ArrayIterator* (*nova_datastruct_list_Nova_ArrayIterator_native_Nova_ArrayIterator)(nova_datastruct_list_Nova_ArrayIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);

typedef struct nova_datastruct_list_native_ArrayIterator
{
nova_datastruct_list_Nova_ArrayIterator_native_Nova_reset reset;
nova_datastruct_list_Nova_ArrayIterator_native_Nova_ArrayIterator ArrayIterator;
} nova_datastruct_list_native_ArrayIterator;

typedef char (*nova_datastruct_list_Nova_CharArray_native_Nova_get)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_CharArray_native0_Nova_map)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure3_Nova_mapFunc nova_datastruct_list_Nova_CharArray_Nova_mapFunc, void* nova_datastruct_list_Nova_CharArray_ref_Nova_mapFunc, void* mapFunc_context);
typedef void (*nova_datastruct_list_Nova_CharArray_native0_Nova_forEach)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure6_Nova_func nova_datastruct_list_Nova_CharArray_Nova_func, void* nova_datastruct_list_Nova_CharArray_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_list_Nova_CharArray_native0_Nova_any)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure9_Nova_anyFunc nova_datastruct_list_Nova_CharArray_Nova_anyFunc, void* nova_datastruct_list_Nova_CharArray_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_CharArray_native0_Nova_all)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure12_Nova_allFunc nova_datastruct_list_Nova_CharArray_Nova_allFunc, void* nova_datastruct_list_Nova_CharArray_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native0_Nova_filter)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure15_Nova_filterFunc nova_datastruct_list_Nova_CharArray_Nova_filterFunc, void* nova_datastruct_list_Nova_CharArray_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native0_Nova_take)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native0_Nova_skip)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, int);
typedef char (*nova_datastruct_list_Nova_CharArray_native0_Nova_firstWhere)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure18_Nova_func nova_datastruct_list_Nova_CharArray_Nova_func, void* nova_datastruct_list_Nova_CharArray_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native0_Nova_reverse)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_CharArray_native0_Nova_join)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native0_Nova_CharArray)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native1_Nova_CharArray)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native2_Nova_CharArray)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, char*, int);

typedef struct nova_datastruct_list_native_CharArray
{
nova_datastruct_list_Nova_CharArray_native_Nova_get get;
nova_datastruct_list_Nova_CharArray_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_CharArray_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_CharArray_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_list_Nova_CharArray_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_list_Nova_CharArray_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_CharArray_native0_Nova_take take__nova_primitive_number_Int;
nova_datastruct_list_Nova_CharArray_native0_Nova_skip skip__nova_primitive_number_Int;
nova_datastruct_list_Nova_CharArray_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_list_Nova_CharArray_native0_Nova_reverse reverse;
nova_datastruct_list_Nova_CharArray_native0_Nova_join join__nova_String;
nova_datastruct_list_Nova_CharArray_native0_Nova_CharArray CharArray;
nova_datastruct_list_Nova_CharArray_native1_Nova_CharArray CharArray__nova_primitive_number_Int;
nova_datastruct_list_Nova_CharArray_native2_Nova_CharArray CharArray__Array1d_nova_primitive_number_Char__nova_primitive_number_Int;
} nova_datastruct_list_native_CharArray;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_CharArrayIterator_native0_Nova_reset)(nova_datastruct_list_Nova_CharArrayIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_CharArrayIterator* (*nova_datastruct_list_Nova_CharArrayIterator_native_Nova_CharArrayIterator)(nova_datastruct_list_Nova_CharArrayIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray*);

typedef struct nova_datastruct_list_native_CharArrayIterator
{
nova_datastruct_list_Nova_CharArrayIterator_native0_Nova_reset reset;
nova_datastruct_list_Nova_CharArrayIterator_native_Nova_CharArrayIterator CharArrayIterator;
} nova_datastruct_list_native_CharArrayIterator;

typedef nova_datastruct_list_Nova_CompiledList* (*nova_datastruct_list_Nova_CompiledList_native_Nova_CompiledList)(nova_datastruct_list_Nova_CompiledList*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_list_native_CompiledList
{
nova_datastruct_list_Nova_CompiledList_native_Nova_CompiledList CompiledList;
} nova_datastruct_list_native_CompiledList;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_map)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure3_Nova_mapFunc nova_datastruct_list_Nova_DoubleArray_Nova_mapFunc, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_mapFunc, void* mapFunc_context);
typedef void (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_forEach)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure6_Nova_func nova_datastruct_list_Nova_DoubleArray_Nova_func, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_any)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure9_Nova_anyFunc nova_datastruct_list_Nova_DoubleArray_Nova_anyFunc, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_all)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure12_Nova_allFunc nova_datastruct_list_Nova_DoubleArray_Nova_allFunc, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_filter)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure15_Nova_filterFunc nova_datastruct_list_Nova_DoubleArray_Nova_filterFunc, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_take)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_skip)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, int);
typedef double (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_firstWhere)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure18_Nova_func nova_datastruct_list_Nova_DoubleArray_Nova_func, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_reverse)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_join)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_DoubleArray)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native1_Nova_DoubleArray)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native2_Nova_DoubleArray)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, double*, int);

typedef struct nova_datastruct_list_native_DoubleArray
{
nova_datastruct_list_Nova_DoubleArray_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_take take__nova_primitive_number_Int;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_skip skip__nova_primitive_number_Int;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_reverse reverse;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_join join__nova_String;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_DoubleArray DoubleArray;
nova_datastruct_list_Nova_DoubleArray_native1_Nova_DoubleArray DoubleArray__nova_primitive_number_Int;
nova_datastruct_list_Nova_DoubleArray_native2_Nova_DoubleArray DoubleArray__Array1d_nova_primitive_number_Double__nova_primitive_number_Int;
} nova_datastruct_list_native_DoubleArray;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_DoubleArrayIterator_native0_Nova_reset)(nova_datastruct_list_Nova_DoubleArrayIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_DoubleArrayIterator* (*nova_datastruct_list_Nova_DoubleArrayIterator_native_Nova_DoubleArrayIterator)(nova_datastruct_list_Nova_DoubleArrayIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray*);

typedef struct nova_datastruct_list_native_DoubleArrayIterator
{
nova_datastruct_list_Nova_DoubleArrayIterator_native0_Nova_reset reset;
nova_datastruct_list_Nova_DoubleArrayIterator_native_Nova_DoubleArrayIterator DoubleArrayIterator;
} nova_datastruct_list_native_DoubleArrayIterator;

typedef nova_datastruct_list_Nova_EmptyStackException* (*nova_datastruct_list_Nova_EmptyStackException_native0_Nova_EmptyStackException)(nova_datastruct_list_Nova_EmptyStackException*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_EmptyStackException* (*nova_datastruct_list_Nova_EmptyStackException_native1_Nova_EmptyStackException)(nova_datastruct_list_Nova_EmptyStackException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_datastruct_list_native_EmptyStackException
{
nova_datastruct_list_Nova_EmptyStackException_native0_Nova_EmptyStackException EmptyStackException;
nova_datastruct_list_Nova_EmptyStackException_native1_Nova_EmptyStackException EmptyStackException__nova_String;
} nova_datastruct_list_native_EmptyStackException;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_IntArray_native0_Nova_map)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure3_Nova_mapFunc nova_datastruct_list_Nova_IntArray_Nova_mapFunc, void* nova_datastruct_list_Nova_IntArray_ref_Nova_mapFunc, void* mapFunc_context);
typedef void (*nova_datastruct_list_Nova_IntArray_native0_Nova_forEach)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure6_Nova_func nova_datastruct_list_Nova_IntArray_Nova_func, void* nova_datastruct_list_Nova_IntArray_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_list_Nova_IntArray_native0_Nova_any)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure9_Nova_anyFunc nova_datastruct_list_Nova_IntArray_Nova_anyFunc, void* nova_datastruct_list_Nova_IntArray_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_IntArray_native0_Nova_all)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure12_Nova_allFunc nova_datastruct_list_Nova_IntArray_Nova_allFunc, void* nova_datastruct_list_Nova_IntArray_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native0_Nova_filter)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure15_Nova_filterFunc nova_datastruct_list_Nova_IntArray_Nova_filterFunc, void* nova_datastruct_list_Nova_IntArray_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native0_Nova_take)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native0_Nova_skip)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, int);
typedef int (*nova_datastruct_list_Nova_IntArray_native0_Nova_firstWhere)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure18_Nova_func nova_datastruct_list_Nova_IntArray_Nova_func, void* nova_datastruct_list_Nova_IntArray_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native0_Nova_reverse)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_IntArray_native0_Nova_join)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native0_Nova_IntArray)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native1_Nova_IntArray)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native2_Nova_IntArray)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, int*, int);

typedef struct nova_datastruct_list_native_IntArray
{
nova_datastruct_list_Nova_IntArray_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_IntArray_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_IntArray_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_list_Nova_IntArray_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_list_Nova_IntArray_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_IntArray_native0_Nova_take take__nova_primitive_number_Int;
nova_datastruct_list_Nova_IntArray_native0_Nova_skip skip__nova_primitive_number_Int;
nova_datastruct_list_Nova_IntArray_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_list_Nova_IntArray_native0_Nova_reverse reverse;
nova_datastruct_list_Nova_IntArray_native0_Nova_join join__nova_String;
nova_datastruct_list_Nova_IntArray_native0_Nova_IntArray IntArray;
nova_datastruct_list_Nova_IntArray_native1_Nova_IntArray IntArray__nova_primitive_number_Int;
nova_datastruct_list_Nova_IntArray_native2_Nova_IntArray IntArray__Array1d_nova_primitive_number_Int__nova_primitive_number_Int;
} nova_datastruct_list_native_IntArray;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_IntArrayIterator_native0_Nova_reset)(nova_datastruct_list_Nova_IntArrayIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_IntArrayIterator* (*nova_datastruct_list_Nova_IntArrayIterator_native_Nova_IntArrayIterator)(nova_datastruct_list_Nova_IntArrayIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray*);

typedef struct nova_datastruct_list_native_IntArrayIterator
{
nova_datastruct_list_Nova_IntArrayIterator_native0_Nova_reset reset;
nova_datastruct_list_Nova_IntArrayIterator_native_Nova_IntArrayIterator IntArrayIterator;
} nova_datastruct_list_native_IntArrayIterator;

typedef char (*nova_datastruct_list_Nova_IntRange_native0_Nova_contains)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntRange_native0_Nova_toArray)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_datastruct_list_Nova_IntRange_native0_Nova_forEach)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure3_Nova_func nova_datastruct_list_Nova_IntRange_Nova_func, void* nova_datastruct_list_Nova_IntRange_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_IntRange_native0_Nova_map)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure6_Nova_mapFunc nova_datastruct_list_Nova_IntRange_Nova_mapFunc, void* nova_datastruct_list_Nova_IntRange_ref_Nova_mapFunc, void* mapFunc_context);
typedef char (*nova_datastruct_list_Nova_IntRange_native0_Nova_any)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure9_Nova_anyFunc nova_datastruct_list_Nova_IntRange_Nova_anyFunc, void* nova_datastruct_list_Nova_IntRange_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_IntRange_native0_Nova_all)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure12_Nova_allFunc nova_datastruct_list_Nova_IntRange_Nova_allFunc, void* nova_datastruct_list_Nova_IntRange_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntRange_native0_Nova_filter)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure15_Nova_filterFunc nova_datastruct_list_Nova_IntRange_Nova_filterFunc, void* nova_datastruct_list_Nova_IntRange_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_IntRange* (*nova_datastruct_list_Nova_IntRange_native0_Nova_take)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_IntRange* (*nova_datastruct_list_Nova_IntRange_native0_Nova_skip)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, int);
typedef int (*nova_datastruct_list_Nova_IntRange_native0_Nova_firstWhere)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure18_Nova_func nova_datastruct_list_Nova_IntRange_Nova_func, void* nova_datastruct_list_Nova_IntRange_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_IntRange* (*nova_datastruct_list_Nova_IntRange_native0_Nova_reverse)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_IntRange_native0_Nova_join)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_IntRange_native0_Nova_toString)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_IntRange* (*nova_datastruct_list_Nova_IntRange_native0_Nova_IntRange)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_IntRange* (*nova_datastruct_list_Nova_IntRange_native1_Nova_IntRange)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct nova_datastruct_list_native_IntRange
{
nova_datastruct_list_Nova_IntRange_native0_Nova_contains contains__nova_primitive_number_Int;
nova_datastruct_list_Nova_IntRange_native0_Nova_toArray toArray;
nova_datastruct_list_Nova_IntRange_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_IntRange_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_IntRange_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_list_Nova_IntRange_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_list_Nova_IntRange_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_IntRange_native0_Nova_take take__nova_primitive_number_Int;
nova_datastruct_list_Nova_IntRange_native0_Nova_skip skip__nova_primitive_number_Int;
nova_datastruct_list_Nova_IntRange_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_list_Nova_IntRange_native0_Nova_reverse reverse;
nova_datastruct_list_Nova_IntRange_native0_Nova_join join__nova_String;
nova_datastruct_list_Nova_IntRange_native0_Nova_toString toString;
nova_datastruct_list_Nova_IntRange_native0_Nova_IntRange IntRange;
nova_datastruct_list_Nova_IntRange_native1_Nova_IntRange IntRange__nova_primitive_number_Int__nova_primitive_number_Int;
} nova_datastruct_list_native_IntRange;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_IntRangeIterator_native0_Nova_reset)(nova_datastruct_list_Nova_IntRangeIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_IntRangeIterator* (*nova_datastruct_list_Nova_IntRangeIterator_native_Nova_IntRangeIterator)(nova_datastruct_list_Nova_IntRangeIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange*);

typedef struct nova_datastruct_list_native_IntRangeIterator
{
nova_datastruct_list_Nova_IntRangeIterator_native0_Nova_reset reset;
nova_datastruct_list_Nova_IntRangeIterator_native_Nova_IntRangeIterator IntRangeIterator;
} nova_datastruct_list_native_IntRangeIterator;


typedef struct nova_datastruct_list_native_Iterable
{
} nova_datastruct_list_native_Iterable;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_Iterator_native0_Nova_reset)(nova_datastruct_list_Nova_Iterator*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_list_native_Iterator
{
nova_datastruct_list_Nova_Iterator_native0_Nova_reset reset;
} nova_datastruct_list_native_Iterator;

typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_addAll)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_add)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_remove)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef char (*nova_datastruct_list_Nova_LinkedList_native0_Nova_contains)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_toArray)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_map)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure3_Nova_mapFunc nova_datastruct_list_Nova_LinkedList_Nova_mapFunc, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_mapFunc, void* mapFunc_context);
typedef void (*nova_datastruct_list_Nova_LinkedList_native0_Nova_forEach)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure6_Nova_func nova_datastruct_list_Nova_LinkedList_Nova_func, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_list_Nova_LinkedList_native0_Nova_any)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure9_Nova_anyFunc nova_datastruct_list_Nova_LinkedList_Nova_anyFunc, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_LinkedList_native0_Nova_all)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure12_Nova_allFunc nova_datastruct_list_Nova_LinkedList_Nova_allFunc, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_filter)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure15_Nova_filterFunc nova_datastruct_list_Nova_LinkedList_Nova_filterFunc, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_take)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_skip)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_firstWhere)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure18_Nova_func nova_datastruct_list_Nova_LinkedList_Nova_func, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_reverse)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_join)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_LinkedList)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_list_native_LinkedList
{
nova_datastruct_list_Nova_LinkedList_native_Nova_addAll addAll;
nova_datastruct_list_Nova_LinkedList_native_Nova_add add;
nova_datastruct_list_Nova_LinkedList_native_Nova_remove remove;
nova_datastruct_list_Nova_LinkedList_native0_Nova_contains contains__nova_Object;
nova_datastruct_list_Nova_LinkedList_native0_Nova_toArray toArray;
nova_datastruct_list_Nova_LinkedList_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_LinkedList_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_LinkedList_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_list_Nova_LinkedList_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_list_Nova_LinkedList_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_LinkedList_native0_Nova_take take__nova_primitive_number_Int;
nova_datastruct_list_Nova_LinkedList_native0_Nova_skip skip__nova_primitive_number_Int;
nova_datastruct_list_Nova_LinkedList_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_list_Nova_LinkedList_native0_Nova_reverse reverse;
nova_datastruct_list_Nova_LinkedList_native0_Nova_join join__nova_String;
nova_datastruct_list_Nova_LinkedList_native_Nova_LinkedList LinkedList;
} nova_datastruct_list_native_LinkedList;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_LinkedListIterator_native0_Nova_reset)(nova_datastruct_list_Nova_LinkedListIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_LinkedListIterator* (*nova_datastruct_list_Nova_LinkedListIterator_native_Nova_LinkedListIterator)(nova_datastruct_list_Nova_LinkedListIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList*);

typedef struct nova_datastruct_list_native_LinkedListIterator
{
nova_datastruct_list_Nova_LinkedListIterator_native0_Nova_reset reset;
nova_datastruct_list_Nova_LinkedListIterator_native_Nova_LinkedListIterator LinkedListIterator;
} nova_datastruct_list_native_LinkedListIterator;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_native0_Nova_toArray)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_datastruct_list_Nova_List_native0_Nova_contains)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef void (*nova_datastruct_list_Nova_List_native0_Nova_forEach)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure3_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_List* (*nova_datastruct_list_Nova_List_native0_Nova_map)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure6_Nova_mapFunc nova_datastruct_list_Nova_List_Nova_mapFunc, void* nova_datastruct_list_Nova_List_ref_Nova_mapFunc, void* mapFunc_context);
typedef char (*nova_datastruct_list_Nova_List_native0_Nova_any)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure9_Nova_anyFunc nova_datastruct_list_Nova_List_Nova_anyFunc, void* nova_datastruct_list_Nova_List_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_List_native0_Nova_all)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure12_Nova_allFunc nova_datastruct_list_Nova_List_Nova_allFunc, void* nova_datastruct_list_Nova_List_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_List* (*nova_datastruct_list_Nova_List_native0_Nova_filter)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure15_Nova_filterFunc nova_datastruct_list_Nova_List_Nova_filterFunc, void* nova_datastruct_list_Nova_List_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_List* (*nova_datastruct_list_Nova_List_native0_Nova_take)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_List* (*nova_datastruct_list_Nova_List_native0_Nova_skip)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_List_native0_Nova_firstWhere)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure18_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_List* (*nova_datastruct_list_Nova_List_native0_Nova_reverse)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_List_native0_Nova_join)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_datastruct_list_native_List
{
nova_datastruct_list_Nova_List_native0_Nova_toArray toArray;
nova_datastruct_list_Nova_List_native0_Nova_contains contains__nova_Object;
nova_datastruct_list_Nova_List_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_List_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_List_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_list_Nova_List_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_list_Nova_List_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_List_native0_Nova_take take__nova_primitive_number_Int;
nova_datastruct_list_Nova_List_native0_Nova_skip skip__nova_primitive_number_Int;
nova_datastruct_list_Nova_List_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_list_Nova_List_native0_Nova_reverse reverse;
nova_datastruct_list_Nova_List_native0_Nova_join join__nova_String;
} nova_datastruct_list_native_List;

typedef nova_datastruct_list_Nova_ListNode* (*nova_datastruct_list_Nova_ListNode_native_Nova_clone)(nova_datastruct_list_Nova_ListNode*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_ListNode* (*nova_datastruct_list_Nova_ListNode_native_Nova_ListNode)(nova_datastruct_list_Nova_ListNode*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_datastruct_list_native_ListNode
{
nova_datastruct_list_Nova_ListNode_native_Nova_clone clone;
nova_datastruct_list_Nova_ListNode_native_Nova_ListNode ListNode;
} nova_datastruct_list_native_ListNode;

typedef nova_datastruct_list_Nova_NoSuchElementException* (*nova_datastruct_list_Nova_NoSuchElementException_native0_Nova_NoSuchElementException)(nova_datastruct_list_Nova_NoSuchElementException*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_NoSuchElementException* (*nova_datastruct_list_Nova_NoSuchElementException_native1_Nova_NoSuchElementException)(nova_datastruct_list_Nova_NoSuchElementException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_datastruct_list_native_NoSuchElementException
{
nova_datastruct_list_Nova_NoSuchElementException_native0_Nova_NoSuchElementException NoSuchElementException;
nova_datastruct_list_Nova_NoSuchElementException_native1_Nova_NoSuchElementException NoSuchElementException__nova_String;
} nova_datastruct_list_native_NoSuchElementException;

typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Queue_native_Nova_dequeue)(nova_datastruct_list_Nova_Queue*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_datastruct_list_Nova_Queue_native_Nova_enqueue)(nova_datastruct_list_Nova_Queue*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_Queue_native0_Nova_toString)(nova_datastruct_list_Nova_Queue*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Queue* (*nova_datastruct_list_Nova_Queue_native0_Nova_Queue)(nova_datastruct_list_Nova_Queue*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Queue* (*nova_datastruct_list_Nova_Queue_native1_Nova_Queue)(nova_datastruct_list_Nova_Queue*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);

typedef struct nova_datastruct_list_native_Queue
{
nova_datastruct_list_Nova_Queue_native_Nova_dequeue dequeue;
nova_datastruct_list_Nova_Queue_native_Nova_enqueue enqueue;
nova_datastruct_list_Nova_Queue_native0_Nova_toString toString;
nova_datastruct_list_Nova_Queue_native0_Nova_Queue Queue;
nova_datastruct_list_Nova_Queue_native1_Nova_Queue Queue__nova_datastruct_list_Array;
} nova_datastruct_list_native_Queue;

typedef void (*nova_datastruct_list_Nova_Stack_native_Nova_push)(nova_datastruct_list_Nova_Stack*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Stack_native_Nova_pop)(nova_datastruct_list_Nova_Stack*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Stack* (*nova_datastruct_list_Nova_Stack_native_Nova_Stack)(nova_datastruct_list_Nova_Stack*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_list_native_Stack
{
nova_datastruct_list_Nova_Stack_native_Nova_push push;
nova_datastruct_list_Nova_Stack_native_Nova_pop pop;
nova_datastruct_list_Nova_Stack_native_Nova_Stack Stack;
} nova_datastruct_list_native_Stack;

typedef nova_exception_Nova_DivideByZeroException* (*nova_exception_Nova_DivideByZeroException_native_Nova_DivideByZeroException)(nova_exception_Nova_DivideByZeroException*, nova_exception_Nova_ExceptionData*);

typedef struct nova_exception_native_DivideByZeroException
{
nova_exception_Nova_DivideByZeroException_native_Nova_DivideByZeroException DivideByZeroException;
} nova_exception_native_DivideByZeroException;

typedef nova_exception_Nova_Exception* (*nova_exception_Nova_Exception_native0_Nova_Exception)(nova_exception_Nova_Exception*, nova_exception_Nova_ExceptionData*);
typedef nova_exception_Nova_Exception* (*nova_exception_Nova_Exception_native1_Nova_Exception)(nova_exception_Nova_Exception*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_exception_native_Exception
{
nova_exception_Nova_Exception_native0_Nova_Exception Exception;
nova_exception_Nova_Exception_native1_Nova_Exception Exception__nova_String;
} nova_exception_native_Exception;

typedef void (*nova_exception_Nova_ExceptionData_native_Nova_addCode)(nova_exception_Nova_ExceptionData*, nova_exception_Nova_ExceptionData*, int);
typedef void (*nova_exception_Nova_ExceptionData_native_Nova_jumpToBuffer)(nova_exception_Nova_ExceptionData*, nova_exception_Nova_ExceptionData*, int);
typedef nova_exception_Nova_ExceptionData* (*nova_exception_Nova_ExceptionData_native_Nova_ExceptionData)(nova_exception_Nova_ExceptionData*, nova_exception_Nova_ExceptionData*, buffer*);

typedef struct nova_exception_native_ExceptionData
{
nova_exception_Nova_ExceptionData_native_Nova_addCode addCode;
nova_exception_Nova_ExceptionData_native_Nova_jumpToBuffer jumpToBuffer;
nova_exception_Nova_ExceptionData_native_Nova_ExceptionData ExceptionData;
} nova_exception_native_ExceptionData;

typedef nova_exception_Nova_UnimplementedOperationException* (*nova_exception_Nova_UnimplementedOperationException_native_Nova_UnimplementedOperationException)(nova_exception_Nova_UnimplementedOperationException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_exception_native_UnimplementedOperationException
{
nova_exception_Nova_UnimplementedOperationException_native_Nova_UnimplementedOperationException UnimplementedOperationException;
} nova_exception_native_UnimplementedOperationException;

typedef void (*nova_gc_Nova_GC_native_Nova_init)(nova_gc_Nova_GC*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_gc_Nova_GC_native_Nova_collect)(nova_gc_Nova_GC*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_gc_Nova_GC_native_Nova_enableIncremental)(nova_gc_Nova_GC*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_gc_Nova_GC_native_Nova_dump)(nova_gc_Nova_GC*, nova_exception_Nova_ExceptionData*);
typedef nova_gc_Nova_GC* (*nova_gc_Nova_GC_native_Nova_GC)(nova_gc_Nova_GC*, nova_exception_Nova_ExceptionData*);

typedef struct nova_gc_native_GC
{
nova_gc_Nova_GC_native_Nova_init init;
nova_gc_Nova_GC_native_Nova_collect collect;
nova_gc_Nova_GC_native_Nova_enableIncremental enableIncremental;
nova_gc_Nova_GC_native_Nova_dump dump;
nova_gc_Nova_GC_native_Nova_GC GC;
} nova_gc_native_GC;

typedef void (*nova_io_Nova_Console_native0_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_Console_native1_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef void (*nova_io_Nova_Console_native2_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef void (*nova_io_Nova_Console_native3_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, double);
typedef void (*nova_io_Nova_Console_native4_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, float);
typedef void (*nova_io_Nova_Console_native5_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, long_long);
typedef void (*nova_io_Nova_Console_native6_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, int);
typedef void (*nova_io_Nova_Console_native7_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, short);
typedef void (*nova_io_Nova_Console_native8_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, char);
typedef void (*nova_io_Nova_Console_native9_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, char);
typedef void (*nova_io_Nova_Console_native0_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef void (*nova_io_Nova_Console_native1_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef void (*nova_io_Nova_Console_native2_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, double);
typedef void (*nova_io_Nova_Console_native3_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, float);
typedef void (*nova_io_Nova_Console_native4_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, long_long);
typedef void (*nova_io_Nova_Console_native5_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, int);
typedef void (*nova_io_Nova_Console_native6_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, short);
typedef void (*nova_io_Nova_Console_native7_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, char);
typedef void (*nova_io_Nova_Console_native8_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, char);
typedef int (*nova_io_Nova_Console_native_Nova_readInt)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef double (*nova_io_Nova_Console_native_Nova_readDouble)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_io_Nova_Console_native_Nova_readChar)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_io_Nova_Console_native_Nova_readLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_io_Nova_Console_native_Nova_readPassword)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_Console_native_Nova_setEcho)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, char);
typedef void (*nova_io_Nova_Console_native_Nova_clearScreen)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_Console_native_Nova_waitForEnter)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef nova_io_Nova_Console* (*nova_io_Nova_Console_native_Nova_Console)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);

typedef struct nova_io_native_Console
{
nova_io_Nova_Console_native0_Nova_writeLine writeLine;
nova_io_Nova_Console_native1_Nova_writeLine writeLine__nova_String;
nova_io_Nova_Console_native2_Nova_writeLine writeLine__nova_Object;
nova_io_Nova_Console_native3_Nova_writeLine writeLine__nova_primitive_number_Double;
nova_io_Nova_Console_native4_Nova_writeLine writeLine__nova_primitive_number_Float;
nova_io_Nova_Console_native5_Nova_writeLine writeLine__nova_primitive_number_Long;
nova_io_Nova_Console_native6_Nova_writeLine writeLine__nova_primitive_number_Int;
nova_io_Nova_Console_native7_Nova_writeLine writeLine__nova_primitive_number_Short;
nova_io_Nova_Console_native8_Nova_writeLine writeLine__nova_primitive_number_Byte;
nova_io_Nova_Console_native9_Nova_writeLine writeLine__nova_primitive_number_Char;
nova_io_Nova_Console_native0_Nova_write write__nova_String;
nova_io_Nova_Console_native1_Nova_write write__nova_Object;
nova_io_Nova_Console_native2_Nova_write write__nova_primitive_number_Double;
nova_io_Nova_Console_native3_Nova_write write__nova_primitive_number_Float;
nova_io_Nova_Console_native4_Nova_write write__nova_primitive_number_Long;
nova_io_Nova_Console_native5_Nova_write write__nova_primitive_number_Int;
nova_io_Nova_Console_native6_Nova_write write__nova_primitive_number_Short;
nova_io_Nova_Console_native7_Nova_write write__nova_primitive_number_Byte;
nova_io_Nova_Console_native8_Nova_write write__nova_primitive_number_Char;
nova_io_Nova_Console_native_Nova_readInt readInt;
nova_io_Nova_Console_native_Nova_readDouble readDouble;
nova_io_Nova_Console_native_Nova_readChar readChar;
nova_io_Nova_Console_native_Nova_readLine readLine;
nova_io_Nova_Console_native_Nova_readPassword readPassword;
nova_io_Nova_Console_native_Nova_setEcho setEcho;
nova_io_Nova_Console_native_Nova_clearScreen clearScreen;
nova_io_Nova_Console_native_Nova_waitForEnter waitForEnter;
nova_io_Nova_Console_native_Nova_Console Console;
} nova_io_native_Console;

typedef char (*nova_io_Nova_File_native_Nova_delete)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_File_native_Nova_reopen)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_File_native_Nova_rewind)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_File_native_Nova_clearContents)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_io_Nova_File_native_Nova_create)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_io_Nova_File_native_Nova_readAllContents)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_io_Nova_File_native_Nova_readLine)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_File_native_Nova_writeLine)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef void (*nova_io_Nova_File_native_Nova_write)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef void (*nova_io_Nova_File_native_Nova_flush)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_File_native_Nova_close)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*);
typedef nova_io_Nova_File* (*nova_io_Nova_File_native0_Nova_File)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_io_Nova_File* (*nova_io_Nova_File_native1_Nova_File)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*, FILE*);

typedef struct nova_io_native_File
{
nova_io_Nova_File_native_Nova_delete delete;
nova_io_Nova_File_native_Nova_reopen reopen;
nova_io_Nova_File_native_Nova_rewind rewind;
nova_io_Nova_File_native_Nova_clearContents clearContents;
nova_io_Nova_File_native_Nova_create create;
nova_io_Nova_File_native_Nova_readAllContents readAllContents;
nova_io_Nova_File_native_Nova_readLine readLine;
nova_io_Nova_File_native_Nova_writeLine writeLine;
nova_io_Nova_File_native_Nova_write write;
nova_io_Nova_File_native_Nova_flush flush;
nova_io_Nova_File_native_Nova_close close;
nova_io_Nova_File_native0_Nova_File File__nova_String;
nova_io_Nova_File_native1_Nova_File File__FILE;
} nova_io_native_File;

typedef nova_io_Nova_FileNotFoundException* (*nova_io_Nova_FileNotFoundException_native_Nova_FileNotFoundException)(nova_io_Nova_FileNotFoundException*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);

typedef struct nova_io_native_FileNotFoundException
{
nova_io_Nova_FileNotFoundException_native_Nova_FileNotFoundException FileNotFoundException;
} nova_io_native_FileNotFoundException;

typedef nova_Nova_String* (*nova_io_Nova_InputStream_native0_Nova_readString)(nova_io_Nova_InputStream*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_io_Nova_InputStream_native0_Nova_readBytes)(nova_io_Nova_InputStream*, nova_exception_Nova_ExceptionData*);

typedef struct nova_io_native_InputStream
{
nova_io_Nova_InputStream_native0_Nova_readString readString;
nova_io_Nova_InputStream_native0_Nova_readBytes readBytes;
} nova_io_native_InputStream;

typedef char (*nova_io_Nova_OutputStream_native0_Nova_write)(nova_io_Nova_OutputStream*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef char (*nova_io_Nova_OutputStream_native1_Nova_write)(nova_io_Nova_OutputStream*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_io_Nova_OutputStream* (*nova_io_Nova_OutputStream_native_Nova_OutputStream)(nova_io_Nova_OutputStream*, nova_exception_Nova_ExceptionData*);

typedef struct nova_io_native_OutputStream
{
nova_io_Nova_OutputStream_native0_Nova_write write__nova_String;
nova_io_Nova_OutputStream_native1_Nova_write write__nova_Object;
nova_io_Nova_OutputStream_native_Nova_OutputStream OutputStream;
} nova_io_native_OutputStream;

typedef nova_datastruct_list_Nova_Array* (*nova_io_Nova_StreamReader_native_Nova_readBytes)(nova_io_Nova_StreamReader*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_io_Nova_StreamReader_native_Nova_readString)(nova_io_Nova_StreamReader*, nova_exception_Nova_ExceptionData*);
typedef nova_io_Nova_StreamReader* (*nova_io_Nova_StreamReader_native_Nova_StreamReader)(nova_io_Nova_StreamReader*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);

typedef struct nova_io_native_StreamReader
{
nova_io_Nova_StreamReader_native_Nova_readBytes readBytes;
nova_io_Nova_StreamReader_native_Nova_readString readString;
nova_io_Nova_StreamReader_native_Nova_StreamReader StreamReader;
} nova_io_native_StreamReader;

typedef nova_math_Nova_ArithmeticSequence* (*nova_math_Nova_ArithmeticSequence_native_Nova_ArithmeticSequence)(nova_math_Nova_ArithmeticSequence*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_ArithmeticSequence
{
nova_math_Nova_ArithmeticSequence_native_Nova_ArithmeticSequence ArithmeticSequence;
} nova_math_native_ArithmeticSequence;

typedef nova_math_Nova_Diekstra* (*nova_math_Nova_Diekstra_native_Nova_Diekstra)(nova_math_Nova_Diekstra*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_Diekstra
{
nova_math_Nova_Diekstra_native_Nova_Diekstra Diekstra;
} nova_math_native_Diekstra;

typedef nova_math_Nova_GeometricSequence* (*nova_math_Nova_GeometricSequence_native_Nova_GeometricSequence)(nova_math_Nova_GeometricSequence*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_GeometricSequence
{
nova_math_Nova_GeometricSequence_native_Nova_GeometricSequence GeometricSequence;
} nova_math_native_GeometricSequence;

typedef nova_math_Nova_Graph* (*nova_math_Nova_Graph_native_Nova_Graph)(nova_math_Nova_Graph*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_Graph
{
nova_math_Nova_Graph_native_Nova_Graph Graph;
} nova_math_native_Graph;

typedef nova_math_Nova_InvalidNumericStatementException* (*nova_math_Nova_InvalidNumericStatementException_native_Nova_InvalidNumericStatementException)(nova_math_Nova_InvalidNumericStatementException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_native_InvalidNumericStatementException
{
nova_math_Nova_InvalidNumericStatementException_native_Nova_InvalidNumericStatementException InvalidNumericStatementException;
} nova_math_native_InvalidNumericStatementException;

typedef long_long (*nova_math_Nova_Math_native_Nova_max)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, long_long, long_long);
typedef long_long (*nova_math_Nova_Math_native_Nova_min)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, long_long, long_long);
typedef char (*nova_math_Nova_Math_native_Nova_sign)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, long_long);
typedef int (*nova_math_Nova_Math_native_Nova_random)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, long_long);
typedef long_long (*nova_math_Nova_Math_native0_Nova_abs)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, long_long);
typedef double (*nova_math_Nova_Math_native1_Nova_abs)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_Nova_sqrt)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_Nova_pow)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double, double);
typedef double (*nova_math_Nova_Math_native_Nova_sin)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_Nova_cos)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_Nova_tan)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_Nova_asin)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_Nova_acos)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_Nova_atan)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef long_long (*nova_math_Nova_Math_native_Nova_round)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef long_long (*nova_math_Nova_Math_native_Nova_floor)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef long_long (*nova_math_Nova_Math_native_Nova_ceil)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef nova_math_Nova_Math* (*nova_math_Nova_Math_native_Nova_Math)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_Math
{
nova_math_Nova_Math_native_Nova_max max;
nova_math_Nova_Math_native_Nova_min min;
nova_math_Nova_Math_native_Nova_sign sign;
nova_math_Nova_Math_native_Nova_random random;
nova_math_Nova_Math_native0_Nova_abs abs__nova_primitive_number_Long;
nova_math_Nova_Math_native1_Nova_abs abs__nova_primitive_number_Double;
nova_math_Nova_Math_native_Nova_sqrt sqrt;
nova_math_Nova_Math_native_Nova_pow pow;
nova_math_Nova_Math_native_Nova_sin sin;
nova_math_Nova_Math_native_Nova_cos cos;
nova_math_Nova_Math_native_Nova_tan tan;
nova_math_Nova_Math_native_Nova_asin asin;
nova_math_Nova_Math_native_Nova_acos acos;
nova_math_Nova_Math_native_Nova_atan atan;
nova_math_Nova_Math_native_Nova_round round;
nova_math_Nova_Math_native_Nova_floor floor;
nova_math_Nova_Math_native_Nova_ceil ceil;
nova_math_Nova_Math_native_Nova_Math Math;
} nova_math_native_Math;

typedef nova_primitive_number_Nova_Number* (*nova_math_Nova_Matrix_native_Nova_sum)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_Matrix* (*nova_math_Nova_Matrix_native_Nova_Matrix)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct nova_math_native_Matrix
{
nova_math_Nova_Matrix_native_Nova_sum sum;
nova_math_Nova_Matrix_native_Nova_Matrix Matrix;
} nova_math_native_Matrix;

typedef nova_Nova_String* (*nova_math_Nova_NumericOperand_native0_Nova_toString)(nova_math_Nova_NumericOperand*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_NumericOperand* (*nova_math_Nova_NumericOperand_native_Nova_NumericOperand)(nova_math_Nova_NumericOperand*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_math_native_NumericOperand
{
nova_math_Nova_NumericOperand_native0_Nova_toString toString;
nova_math_Nova_NumericOperand_native_Nova_NumericOperand NumericOperand;
} nova_math_native_NumericOperand;

typedef nova_Nova_String* (*nova_math_Nova_NumericOperation_native0_Nova_toString)(nova_math_Nova_NumericOperation*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_NumericOperation* (*nova_math_Nova_NumericOperation_native0_Nova_NumericOperation)(nova_math_Nova_NumericOperation*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_math_Nova_NumericOperation* (*nova_math_Nova_NumericOperation_native1_Nova_NumericOperation)(nova_math_Nova_NumericOperation*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*);

typedef struct nova_math_native_NumericOperation
{
nova_math_Nova_NumericOperation_native0_Nova_toString toString;
nova_math_Nova_NumericOperation_native0_Nova_NumericOperation NumericOperation__nova_String;
nova_math_Nova_NumericOperation_native1_Nova_NumericOperation NumericOperation__nova_String__nova_String__nova_String;
} nova_math_native_NumericOperation;

typedef nova_Nova_String* (*nova_math_Nova_NumericStatement_native0_Nova_toString)(nova_math_Nova_NumericStatement*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_NumericStatement* (*nova_math_Nova_NumericStatement_native_Nova_NumericStatement)(nova_math_Nova_NumericStatement*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_native_NumericStatement
{
nova_math_Nova_NumericStatement_native0_Nova_toString toString;
nova_math_Nova_NumericStatement_native_Nova_NumericStatement NumericStatement;
} nova_math_native_NumericStatement;

typedef nova_Nova_String* (*nova_math_Nova_NumericTree_native0_Nova_toString)(nova_math_Nova_NumericTree*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_NumericTree* (*nova_math_Nova_NumericTree_native_Nova_NumericTree)(nova_math_Nova_NumericTree*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_native_NumericTree
{
nova_math_Nova_NumericTree_native0_Nova_toString toString;
nova_math_Nova_NumericTree_native_Nova_NumericTree NumericTree;
} nova_math_native_NumericTree;

typedef nova_math_Nova_Polynomial* (*nova_math_Nova_Polynomial_native_Nova_Polynomial)(nova_math_Nova_Polynomial*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_native_Polynomial
{
nova_math_Nova_Polynomial_native_Nova_Polynomial Polynomial;
} nova_math_native_Polynomial;

typedef double (*nova_math_Nova_Sequence_native_Nova_sum)(nova_math_Nova_Sequence*, nova_exception_Nova_ExceptionData*, int);
typedef nova_math_Nova_Sequence* (*nova_math_Nova_Sequence_native_Nova_Sequence)(nova_math_Nova_Sequence*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray*);

typedef struct nova_math_native_Sequence
{
nova_math_Nova_Sequence_native_Nova_sum sum;
nova_math_Nova_Sequence_native_Nova_Sequence Sequence;
} nova_math_native_Sequence;

typedef nova_math_Nova_Statement* (*nova_math_Nova_Statement_native_Nova_Statement)(nova_math_Nova_Statement*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_Statement
{
nova_math_Nova_Statement_native_Nova_Statement Statement;
} nova_math_native_Statement;

typedef nova_Nova_String* (*nova_math_Nova_StatementComponent_native0_Nova_toString)(nova_math_Nova_StatementComponent*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_StatementComponent* (*nova_math_Nova_StatementComponent_native_Nova_StatementComponent)(nova_math_Nova_StatementComponent*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_math_native_StatementComponent
{
nova_math_Nova_StatementComponent_native0_Nova_toString toString;
nova_math_Nova_StatementComponent_native_Nova_StatementComponent StatementComponent;
} nova_math_native_StatementComponent;

typedef nova_math_Nova_VariableOperand* (*nova_math_Nova_VariableOperand_native_Nova_VariableOperand)(nova_math_Nova_VariableOperand*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_VariableOperand
{
nova_math_Nova_VariableOperand_native_Nova_VariableOperand VariableOperand;
} nova_math_native_VariableOperand;

typedef nova_math_Nova_NumericStatement* (*nova_math_calculus_Nova_Calculus_native_Nova_derivative)(nova_math_calculus_Nova_Calculus*, nova_exception_Nova_ExceptionData*, nova_math_Nova_NumericStatement*);
typedef nova_math_calculus_Nova_Calculus* (*nova_math_calculus_Nova_Calculus_native_Nova_Calculus)(nova_math_calculus_Nova_Calculus*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_calculus_native_Calculus
{
nova_math_calculus_Nova_Calculus_native_Nova_derivative derivative;
nova_math_calculus_Nova_Calculus_native_Nova_Calculus Calculus;
} nova_math_calculus_native_Calculus;

typedef nova_math_huffman_Nova_HuffmanTree* (*nova_math_huffman_Nova_HuffmanTree_native_Nova_HuffmanTree)(nova_math_huffman_Nova_HuffmanTree*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_huffman_native_HuffmanTree
{
nova_math_huffman_Nova_HuffmanTree_native_Nova_HuffmanTree HuffmanTree;
} nova_math_huffman_native_HuffmanTree;

typedef nova_math_logic_Nova_Conclusion* (*nova_math_logic_Nova_Conclusion_native_Nova_Conclusion)(nova_math_logic_Nova_Conclusion*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_logic_native_Conclusion
{
nova_math_logic_Nova_Conclusion_native_Nova_Conclusion Conclusion;
} nova_math_logic_native_Conclusion;

typedef nova_math_logic_Nova_Hypothesis* (*nova_math_logic_Nova_Hypothesis_native_Nova_Hypothesis)(nova_math_logic_Nova_Hypothesis*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_logic_native_Hypothesis
{
nova_math_logic_Nova_Hypothesis_native_Nova_Hypothesis Hypothesis;
} nova_math_logic_native_Hypothesis;

typedef nova_math_logic_Nova_InvalidFormulaException* (*nova_math_logic_Nova_InvalidFormulaException_native_Nova_InvalidFormulaException)(nova_math_logic_Nova_InvalidFormulaException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_logic_native_InvalidFormulaException
{
nova_math_logic_Nova_InvalidFormulaException_native_Nova_InvalidFormulaException InvalidFormulaException;
} nova_math_logic_native_InvalidFormulaException;

typedef nova_math_logic_Nova_LogicalConnective* (*nova_math_logic_Nova_LogicalConnective_native_Nova_LogicalConnective)(nova_math_logic_Nova_LogicalConnective*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_logic_native_LogicalConnective
{
nova_math_logic_Nova_LogicalConnective_native_Nova_LogicalConnective LogicalConnective;
} nova_math_logic_native_LogicalConnective;

typedef nova_Nova_String* (*nova_math_logic_Nova_LogicalStatement_native0_Nova_toString)(nova_math_logic_Nova_LogicalStatement*, nova_exception_Nova_ExceptionData*);
typedef nova_math_logic_Nova_LogicalStatement* (*nova_math_logic_Nova_LogicalStatement_native_Nova_LogicalStatement)(nova_math_logic_Nova_LogicalStatement*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_logic_native_LogicalStatement
{
nova_math_logic_Nova_LogicalStatement_native0_Nova_toString toString;
nova_math_logic_Nova_LogicalStatement_native_Nova_LogicalStatement LogicalStatement;
} nova_math_logic_native_LogicalStatement;

typedef nova_math_logic_Nova_StatementComponent* (*nova_math_logic_Nova_StatementComponent_native_Nova_StatementComponent)(nova_math_logic_Nova_StatementComponent*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_logic_native_StatementComponent
{
nova_math_logic_Nova_StatementComponent_native_Nova_StatementComponent StatementComponent;
} nova_math_logic_native_StatementComponent;

typedef nova_math_logic_Nova_StatementGroup* (*nova_math_logic_Nova_StatementGroup_native_Nova_StatementGroup)(nova_math_logic_Nova_StatementGroup*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, nova_datastruct_Nova_Bounds*);

typedef struct nova_math_logic_native_StatementGroup
{
nova_math_logic_Nova_StatementGroup_native_Nova_StatementGroup StatementGroup;
} nova_math_logic_native_StatementGroup;

typedef nova_math_logic_Nova_StatementLetter* (*nova_math_logic_Nova_StatementLetter_native_Nova_StatementLetter)(nova_math_logic_Nova_StatementLetter*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*);

typedef struct nova_math_logic_native_StatementLetter
{
nova_math_logic_Nova_StatementLetter_native_Nova_StatementLetter StatementLetter;
} nova_math_logic_native_StatementLetter;

typedef nova_math_logic_Nova_WFF* (*nova_math_logic_Nova_WFF_native_Nova_WFF)(nova_math_logic_Nova_WFF*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_datastruct_list_Nova_Array*);

typedef struct nova_math_logic_native_WFF
{
nova_math_logic_Nova_WFF_native_Nova_WFF WFF;
} nova_math_logic_native_WFF;

typedef char (*nova_network_Nova_ClientSocket_native_Nova_connect)(nova_network_Nova_ClientSocket*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, int);
typedef char (*nova_network_Nova_ClientSocket_native_Nova_close)(nova_network_Nova_ClientSocket*, nova_exception_Nova_ExceptionData*);
typedef nova_network_Nova_ClientSocket* (*nova_network_Nova_ClientSocket_native_Nova_ClientSocket)(nova_network_Nova_ClientSocket*, nova_exception_Nova_ExceptionData*);

typedef struct nova_network_native_ClientSocket
{
nova_network_Nova_ClientSocket_native_Nova_connect connect;
nova_network_Nova_ClientSocket_native_Nova_close close;
nova_network_Nova_ClientSocket_native_Nova_ClientSocket ClientSocket;
} nova_network_native_ClientSocket;

typedef void (*nova_network_Nova_ConnectionSocket_native_Nova_close)(nova_network_Nova_ConnectionSocket*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_network_Nova_ConnectionSocket_native_Nova_validateConnection)(nova_network_Nova_ConnectionSocket*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_network_Nova_ConnectionSocket_native0_Nova_readString)(nova_network_Nova_ConnectionSocket*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_network_Nova_ConnectionSocket_native_Nova_write)(nova_network_Nova_ConnectionSocket*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_network_Nova_ConnectionSocket* (*nova_network_Nova_ConnectionSocket_native_Nova_ConnectionSocket)(nova_network_Nova_ConnectionSocket*, nova_exception_Nova_ExceptionData*, SOCKET_ID_TYPE);

typedef struct nova_network_native_ConnectionSocket
{
nova_network_Nova_ConnectionSocket_native_Nova_close close;
nova_network_Nova_ConnectionSocket_native_Nova_validateConnection validateConnection;
nova_network_Nova_ConnectionSocket_native0_Nova_readString readString;
nova_network_Nova_ConnectionSocket_native_Nova_write write;
nova_network_Nova_ConnectionSocket_native_Nova_ConnectionSocket ConnectionSocket;
} nova_network_native_ConnectionSocket;

typedef nova_Nova_String* (*nova_network_Nova_NetworkInputStream_native0_Nova_readString)(nova_network_Nova_NetworkInputStream*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_network_Nova_NetworkInputStream_native0_Nova_readBytes)(nova_network_Nova_NetworkInputStream*, nova_exception_Nova_ExceptionData*);
typedef nova_network_Nova_NetworkInputStream* (*nova_network_Nova_NetworkInputStream_native_Nova_NetworkInputStream)(nova_network_Nova_NetworkInputStream*, nova_exception_Nova_ExceptionData*, nova_network_Nova_ConnectionSocket*);

typedef struct nova_network_native_NetworkInputStream
{
nova_network_Nova_NetworkInputStream_native0_Nova_readString readString;
nova_network_Nova_NetworkInputStream_native0_Nova_readBytes readBytes;
nova_network_Nova_NetworkInputStream_native_Nova_NetworkInputStream NetworkInputStream;
} nova_network_native_NetworkInputStream;

typedef char (*nova_network_Nova_NetworkOutputStream_native0_Nova_write)(nova_network_Nova_NetworkOutputStream*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef char (*nova_network_Nova_NetworkOutputStream_native1_Nova_write)(nova_network_Nova_NetworkOutputStream*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_network_Nova_NetworkOutputStream* (*nova_network_Nova_NetworkOutputStream_native_Nova_NetworkOutputStream)(nova_network_Nova_NetworkOutputStream*, nova_exception_Nova_ExceptionData*, nova_network_Nova_ConnectionSocket*);

typedef struct nova_network_native_NetworkOutputStream
{
nova_network_Nova_NetworkOutputStream_native0_Nova_write write__nova_String;
nova_network_Nova_NetworkOutputStream_native1_Nova_write write__nova_Object;
nova_network_Nova_NetworkOutputStream_native_Nova_NetworkOutputStream NetworkOutputStream;
} nova_network_native_NetworkOutputStream;

typedef char (*nova_network_Nova_ServerSocket_native_Nova_start)(nova_network_Nova_ServerSocket*, nova_exception_Nova_ExceptionData*, int);
typedef char (*nova_network_Nova_ServerSocket_native_Nova_close)(nova_network_Nova_ServerSocket*, nova_exception_Nova_ExceptionData*);
typedef nova_network_Nova_ConnectionSocket* (*nova_network_Nova_ServerSocket_native_Nova_acceptClient)(nova_network_Nova_ServerSocket*, nova_exception_Nova_ExceptionData*);
typedef nova_network_Nova_ServerSocket* (*nova_network_Nova_ServerSocket_native_Nova_ServerSocket)(nova_network_Nova_ServerSocket*, nova_exception_Nova_ExceptionData*);

typedef struct nova_network_native_ServerSocket
{
nova_network_Nova_ServerSocket_native_Nova_start start;
nova_network_Nova_ServerSocket_native_Nova_close close;
nova_network_Nova_ServerSocket_native_Nova_acceptClient acceptClient;
nova_network_Nova_ServerSocket_native_Nova_ServerSocket ServerSocket;
} nova_network_native_ServerSocket;

typedef nova_network_Nova_Socket* (*nova_network_Nova_Socket_native_Nova_Socket)(nova_network_Nova_Socket*, nova_exception_Nova_ExceptionData*);

typedef struct nova_network_native_Socket
{
nova_network_Nova_Socket_native_Nova_Socket Socket;
} nova_network_native_Socket;

typedef char (*nova_operators_Nova_Equals_native0_Nova_equals)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_operators_native_Equals
{
nova_operators_Nova_Equals_native0_Nova_equals equals__nova_Object;
} nova_operators_native_Equals;

typedef nova_Nova_Object* (*nova_operators_Nova_Multiply_native0_Nova_multiply)(nova_operators_Nova_Multiply*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_operators_native_Multiply
{
nova_operators_Nova_Multiply_native0_Nova_multiply multiply__nova_Object;
} nova_operators_native_Multiply;

typedef nova_Nova_String* (*nova_primitive_Nova_Bool_native2_Nova_toString)(nova_primitive_Nova_Bool*, nova_exception_Nova_ExceptionData*, char);
typedef nova_Nova_String* (*nova_primitive_Nova_Bool_native3_Nova_toString)(nova_primitive_Nova_Bool*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_primitive_Nova_Bool_native0_Nova_compareTo)(nova_primitive_Nova_Bool*, nova_exception_Nova_ExceptionData*, char);
typedef nova_primitive_Nova_Bool* (*nova_primitive_Nova_Bool_native_Nova_Bool)(nova_primitive_Nova_Bool*, nova_exception_Nova_ExceptionData*, char);

typedef struct nova_primitive_native_Bool
{
nova_primitive_Nova_Bool_native2_Nova_toString toString__nova_primitive_Bool;
nova_primitive_Nova_Bool_native3_Nova_toString toString;
nova_primitive_Nova_Bool_native0_Nova_compareTo compareTo__nova_primitive_Bool;
nova_primitive_Nova_Bool_native_Nova_Bool Bool;
} nova_primitive_native_Bool;

typedef nova_Nova_String* (*nova_primitive_Nova_Null_native_Nova_toString)(nova_primitive_Nova_Null*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_primitive_Nova_Null_native_Nova_concat)(nova_primitive_Nova_Null*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_primitive_Nova_Null* (*nova_primitive_Nova_Null_native_Nova_Null)(nova_primitive_Nova_Null*, nova_exception_Nova_ExceptionData*);

typedef struct nova_primitive_native_Null
{
nova_primitive_Nova_Null_native_Nova_toString toString;
nova_primitive_Nova_Null_native_Nova_concat concat;
nova_primitive_Nova_Null_native_Nova_Null Null;
} nova_primitive_native_Null;

typedef nova_primitive_Nova_Primitive* (*nova_primitive_Nova_Primitive_native_Nova_Primitive)(nova_primitive_Nova_Primitive*, nova_exception_Nova_ExceptionData*);

typedef struct nova_primitive_native_Primitive
{
nova_primitive_Nova_Primitive_native_Nova_Primitive Primitive;
} nova_primitive_native_Primitive;

typedef int (*nova_primitive_number_Nova_Byte_native_Nova_numDigits)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*, char);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Byte_native2_Nova_toString)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*, char);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Byte_native3_Nova_toString)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_primitive_number_Nova_Byte_native0_Nova_compareTo)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*, char);
typedef char (*nova_primitive_number_Nova_Byte_native_Nova_multiply)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*, char);
typedef nova_primitive_number_Nova_Byte* (*nova_primitive_number_Nova_Byte_native_Nova_Byte)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*, char);

typedef struct nova_primitive_number_native_Byte
{
nova_primitive_number_Nova_Byte_native_Nova_numDigits numDigits;
nova_primitive_number_Nova_Byte_native2_Nova_toString toString__nova_primitive_number_Byte;
nova_primitive_number_Nova_Byte_native3_Nova_toString toString;
nova_primitive_number_Nova_Byte_native0_Nova_compareTo compareTo__nova_primitive_number_Byte;
nova_primitive_number_Nova_Byte_native_Nova_multiply multiply;
nova_primitive_number_Nova_Byte_native_Nova_Byte Byte;
} nova_primitive_number_native_Byte;

typedef nova_Nova_String* (*nova_primitive_number_Nova_Char_native2_Nova_toString)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Char_native3_Nova_toString)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_primitive_number_Nova_Char_native0_Nova_toLowerCase)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_primitive_number_Nova_Char_native0_Nova_toUpperCase)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_primitive_number_Nova_Char_native1_Nova_toLowerCase)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);
typedef char (*nova_primitive_number_Nova_Char_native1_Nova_toUpperCase)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);
typedef int (*nova_primitive_number_Nova_Char_native0_Nova_compareTo)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);
typedef char (*nova_primitive_number_Nova_Char_native0_Nova_multiply)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);
typedef nova_primitive_number_Nova_Char* (*nova_primitive_number_Nova_Char_native_Nova_Char)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);

typedef struct nova_primitive_number_native_Char
{
nova_primitive_number_Nova_Char_native2_Nova_toString toString__nova_primitive_number_Char;
nova_primitive_number_Nova_Char_native3_Nova_toString toString;
nova_primitive_number_Nova_Char_native0_Nova_toLowerCase toLowerCase;
nova_primitive_number_Nova_Char_native0_Nova_toUpperCase toUpperCase;
nova_primitive_number_Nova_Char_native1_Nova_toLowerCase toLowerCase__nova_primitive_number_Char;
nova_primitive_number_Nova_Char_native1_Nova_toUpperCase toUpperCase__nova_primitive_number_Char;
nova_primitive_number_Nova_Char_native0_Nova_compareTo compareTo__nova_primitive_number_Char;
nova_primitive_number_Nova_Char_native0_Nova_multiply multiply__nova_primitive_number_Char;
nova_primitive_number_Nova_Char_native_Nova_Char Char;
} nova_primitive_number_native_Char;

typedef int (*nova_primitive_number_Nova_Double_native0_Nova_numDigits)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Double_native_Nova_genString)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, char*, int);
typedef char* (*nova_primitive_number_Nova_Double_native_Nova_genBuffer)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef int (*nova_primitive_number_Nova_Double_native_Nova_repetition)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, char*, int);
typedef int (*nova_primitive_number_Nova_Double_native_Nova_lastSignificantDigit)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, char*, int);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Double_native2_Nova_toString)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Double_native3_Nova_toString)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*);
typedef double (*nova_primitive_number_Nova_Double_native_Nova_parseDouble)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef double (*nova_primitive_number_Nova_Double_native0_Nova_compareTo)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_primitive_number_Nova_Double_native0_Nova_multiply)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef nova_primitive_number_Nova_Double* (*nova_primitive_number_Nova_Double_native_Nova_Double)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);

typedef struct nova_primitive_number_native_Double
{
nova_primitive_number_Nova_Double_native0_Nova_numDigits numDigits__nova_primitive_number_Double;
nova_primitive_number_Nova_Double_native_Nova_genString genString;
nova_primitive_number_Nova_Double_native_Nova_genBuffer genBuffer;
nova_primitive_number_Nova_Double_native_Nova_repetition repetition;
nova_primitive_number_Nova_Double_native_Nova_lastSignificantDigit lastSignificantDigit;
nova_primitive_number_Nova_Double_native2_Nova_toString toString__nova_primitive_number_Double;
nova_primitive_number_Nova_Double_native3_Nova_toString toString;
nova_primitive_number_Nova_Double_native_Nova_parseDouble parseDouble;
nova_primitive_number_Nova_Double_native0_Nova_compareTo compareTo__nova_primitive_number_Double;
nova_primitive_number_Nova_Double_native0_Nova_multiply multiply__nova_primitive_number_Double;
nova_primitive_number_Nova_Double_native_Nova_Double Double;
} nova_primitive_number_native_Double;

typedef int (*nova_primitive_number_Nova_Float_native0_Nova_numDigits)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*, float);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Float_native2_Nova_toString)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*, float);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Float_native3_Nova_toString)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*);
typedef float (*nova_primitive_number_Nova_Float_native0_Nova_compareTo)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*, float);
typedef float (*nova_primitive_number_Nova_Float_native0_Nova_multiply)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*, float);
typedef nova_primitive_number_Nova_Float* (*nova_primitive_number_Nova_Float_native_Nova_Float)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*, int);

typedef struct nova_primitive_number_native_Float
{
nova_primitive_number_Nova_Float_native0_Nova_numDigits numDigits__nova_primitive_number_Float;
nova_primitive_number_Nova_Float_native2_Nova_toString toString__nova_primitive_number_Float;
nova_primitive_number_Nova_Float_native3_Nova_toString toString;
nova_primitive_number_Nova_Float_native0_Nova_compareTo compareTo__nova_primitive_number_Float;
nova_primitive_number_Nova_Float_native0_Nova_multiply multiply__nova_primitive_number_Float;
nova_primitive_number_Nova_Float_native_Nova_Float Float;
} nova_primitive_number_native_Float;

typedef long_long (*nova_primitive_number_Nova_Int_native_Nova_getHashCodeLong)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*);
typedef int (*nova_primitive_number_Nova_Int_native0_Nova_numDigits)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Int_native2_Nova_toString)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Int_native3_Nova_toString)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*);
typedef int (*nova_primitive_number_Nova_Int_native_Nova_parseInt)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef int (*nova_primitive_number_Nova_Int_native0_Nova_compareTo)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, int);
typedef int (*nova_primitive_number_Nova_Int_native0_Nova_multiply)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, int);
typedef nova_primitive_number_Nova_Int* (*nova_primitive_number_Nova_Int_native_Nova_Int)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, int);

typedef struct nova_primitive_number_native_Int
{
nova_primitive_number_Nova_Int_native_Nova_getHashCodeLong getHashCodeLong;
nova_primitive_number_Nova_Int_native0_Nova_numDigits numDigits__nova_primitive_number_Int;
nova_primitive_number_Nova_Int_native2_Nova_toString toString__nova_primitive_number_Int;
nova_primitive_number_Nova_Int_native3_Nova_toString toString;
nova_primitive_number_Nova_Int_native_Nova_parseInt parseInt;
nova_primitive_number_Nova_Int_native0_Nova_compareTo compareTo__nova_primitive_number_Int;
nova_primitive_number_Nova_Int_native0_Nova_multiply multiply__nova_primitive_number_Int;
nova_primitive_number_Nova_Int_native_Nova_Int Int;
} nova_primitive_number_native_Int;


typedef struct nova_primitive_number_native_Integer
{
} nova_primitive_number_native_Integer;

typedef int (*nova_primitive_number_Nova_Long_native0_Nova_numDigits)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Long_native2_Nova_toString)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Long_native3_Nova_toString)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*);
typedef long_long (*nova_primitive_number_Nova_Long_native0_Nova_compareTo)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);
typedef long_long (*nova_primitive_number_Nova_Long_native0_Nova_multiply)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);
typedef nova_primitive_number_Nova_Long* (*nova_primitive_number_Nova_Long_native_Nova_Long)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);

typedef struct nova_primitive_number_native_Long
{
nova_primitive_number_Nova_Long_native0_Nova_numDigits numDigits__nova_primitive_number_Long;
nova_primitive_number_Nova_Long_native2_Nova_toString toString__nova_primitive_number_Long;
nova_primitive_number_Nova_Long_native3_Nova_toString toString;
nova_primitive_number_Nova_Long_native0_Nova_compareTo compareTo__nova_primitive_number_Long;
nova_primitive_number_Nova_Long_native0_Nova_multiply multiply__nova_primitive_number_Long;
nova_primitive_number_Nova_Long_native_Nova_Long Long;
} nova_primitive_number_native_Long;

typedef int (*nova_primitive_number_Nova_Number_native0_Nova_numDigits)(nova_primitive_number_Nova_Number*, nova_exception_Nova_ExceptionData*, nova_primitive_number_Nova_Number*);
typedef nova_primitive_number_Nova_Number* (*nova_primitive_number_Nova_Number_native_Nova_Number)(nova_primitive_number_Nova_Number*, nova_exception_Nova_ExceptionData*);

typedef struct nova_primitive_number_native_Number
{
nova_primitive_number_Nova_Number_native0_Nova_numDigits numDigits__nova_primitive_number_Number;
nova_primitive_number_Nova_Number_native_Nova_Number Number;
} nova_primitive_number_native_Number;


typedef struct nova_primitive_number_native_RealNumber
{
} nova_primitive_number_native_RealNumber;

typedef int (*nova_primitive_number_Nova_Short_native0_Nova_numDigits)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*, short);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Short_native2_Nova_toString)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*, short);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Short_native3_Nova_toString)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*);
typedef short (*nova_primitive_number_Nova_Short_native0_Nova_compareTo)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*, short);
typedef short (*nova_primitive_number_Nova_Short_native0_Nova_multiply)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*, short);
typedef nova_primitive_number_Nova_Short* (*nova_primitive_number_Nova_Short_native_Nova_Short)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*, short);

typedef struct nova_primitive_number_native_Short
{
nova_primitive_number_Nova_Short_native0_Nova_numDigits numDigits__nova_primitive_number_Short;
nova_primitive_number_Nova_Short_native2_Nova_toString toString__nova_primitive_number_Short;
nova_primitive_number_Nova_Short_native3_Nova_toString toString;
nova_primitive_number_Nova_Short_native0_Nova_compareTo compareTo__nova_primitive_number_Short;
nova_primitive_number_Nova_Short_native0_Nova_multiply multiply__nova_primitive_number_Short;
nova_primitive_number_Nova_Short_native_Nova_Short Short;
} nova_primitive_number_native_Short;

typedef nova_process_Nova_Process* (*nova_process_Nova_Process_native_Nova_Process)(nova_process_Nova_Process*, nova_exception_Nova_ExceptionData*, nova_io_Nova_StreamReader*);

typedef struct nova_process_native_Process
{
nova_process_Nova_Process_native_Nova_Process Process;
} nova_process_native_Process;

typedef nova_Nova_String* (*nova_security_Nova_MD5_native_Nova_encrypt)(nova_security_Nova_MD5*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_security_Nova_MD5* (*nova_security_Nova_MD5_native_Nova_MD5)(nova_security_Nova_MD5*, nova_exception_Nova_ExceptionData*);

typedef struct nova_security_native_MD5
{
nova_security_Nova_MD5_native_Nova_encrypt encrypt;
nova_security_Nova_MD5_native_Nova_MD5 MD5;
} nova_security_native_MD5;

typedef void (*nova_star_Nova_Window_native_Nova_create)(nova_star_Nova_Window*, nova_exception_Nova_ExceptionData*);
typedef nova_star_Nova_Window* (*nova_star_Nova_Window_native_Nova_Window)(nova_star_Nova_Window*, nova_exception_Nova_ExceptionData*);

typedef struct nova_star_native_Window
{
nova_star_Nova_Window_native_Nova_create create;
nova_star_Nova_Window_native_Nova_Window Window;
} nova_star_native_Window;

typedef void (*nova_star_Nova_WindowThread_native_Nova_run)(nova_star_Nova_WindowThread*, nova_exception_Nova_ExceptionData*);
typedef nova_star_Nova_WindowThread* (*nova_star_Nova_WindowThread_native_Nova_WindowThread)(nova_star_Nova_WindowThread*, nova_exception_Nova_ExceptionData*, nova_star_Nova_Window*);

typedef struct nova_star_native_WindowThread
{
nova_star_Nova_WindowThread_native_Nova_run run;
nova_star_Nova_WindowThread_native_Nova_WindowThread WindowThread;
} nova_star_native_WindowThread;

typedef void (*nova_svg_Nova_Svg_native_Nova_generateOutput)(nova_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef void (*nova_svg_Nova_Svg_native_Nova_generateHTMLOutput)(nova_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_svg_Nova_Svg* (*nova_svg_Nova_Svg_native_Nova_Svg)(nova_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_native_Svg
{
nova_svg_Nova_Svg_native_Nova_generateOutput generateOutput;
nova_svg_Nova_Svg_native_Nova_generateHTMLOutput generateHTMLOutput;
nova_svg_Nova_Svg_native_Nova_Svg Svg;
} nova_svg_native_Svg;

typedef void (*nova_svg_Nova_SvgCircle_native_Nova_generateOutput)(nova_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_Nova_String* (*nova_svg_Nova_SvgCircle_native0_Nova_toString)(nova_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*);
typedef nova_svg_Nova_SvgCircle* (*nova_svg_Nova_SvgCircle_native_Nova_SvgCircle)(nova_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*, double, double, int);

typedef struct nova_svg_native_SvgCircle
{
nova_svg_Nova_SvgCircle_native_Nova_generateOutput generateOutput;
nova_svg_Nova_SvgCircle_native0_Nova_toString toString;
nova_svg_Nova_SvgCircle_native_Nova_SvgCircle SvgCircle;
} nova_svg_native_SvgCircle;

typedef void (*nova_svg_Nova_SvgComponent_native0_Nova_generateOutput)(nova_svg_Nova_SvgComponent*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_svg_Nova_SvgComponent* (*nova_svg_Nova_SvgComponent_native_Nova_SvgComponent)(nova_svg_Nova_SvgComponent*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_native_SvgComponent
{
nova_svg_Nova_SvgComponent_native0_Nova_generateOutput generateOutput__nova_io_File;
nova_svg_Nova_SvgComponent_native_Nova_SvgComponent SvgComponent;
} nova_svg_native_SvgComponent;

typedef void (*nova_svg_Nova_SvgComponentList_native_Nova_generateOutput)(nova_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef void (*nova_svg_Nova_SvgComponentList_native_Nova_addChild)(nova_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*, nova_svg_Nova_SvgComponent*);
typedef nova_svg_Nova_SvgComponentList* (*nova_svg_Nova_SvgComponentList_native_Nova_SvgComponentList)(nova_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_native_SvgComponentList
{
nova_svg_Nova_SvgComponentList_native_Nova_generateOutput generateOutput;
nova_svg_Nova_SvgComponentList_native_Nova_addChild addChild;
nova_svg_Nova_SvgComponentList_native_Nova_SvgComponentList SvgComponentList;
} nova_svg_native_SvgComponentList;

typedef nova_svg_Nova_SvgComponentNode* (*nova_svg_Nova_SvgComponentNode_native_Nova_SvgComponentNode)(nova_svg_Nova_SvgComponentNode*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_native_SvgComponentNode
{
nova_svg_Nova_SvgComponentNode_native_Nova_SvgComponentNode SvgComponentNode;
} nova_svg_native_SvgComponentNode;

typedef void (*nova_svg_Nova_SvgMainComponent_native0_Nova_generateOutput)(nova_svg_Nova_SvgMainComponent*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_svg_Nova_SvgMainComponent* (*nova_svg_Nova_SvgMainComponent_native_Nova_SvgMainComponent)(nova_svg_Nova_SvgMainComponent*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_native_SvgMainComponent
{
nova_svg_Nova_SvgMainComponent_native0_Nova_generateOutput generateOutput__nova_io_File;
nova_svg_Nova_SvgMainComponent_native_Nova_SvgMainComponent SvgMainComponent;
} nova_svg_native_SvgMainComponent;

typedef nova_svg_no3_Nova_No3Selection* (*nova_svg_no3_Nova_No3_native_Nova_select)(nova_svg_no3_Nova_No3*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_svg_no3_Nova_No3* (*nova_svg_no3_Nova_No3_native_Nova_No3)(nova_svg_no3_Nova_No3*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_no3_native_No3
{
nova_svg_no3_Nova_No3_native_Nova_select select;
nova_svg_no3_Nova_No3_native_Nova_No3 No3;
} nova_svg_no3_native_No3;

typedef nova_Nova_String* (*nova_svg_no3_Nova_No3Node_native0_Nova_toJs)(nova_svg_no3_Nova_No3Node*, nova_exception_Nova_ExceptionData*);
typedef nova_svg_no3_Nova_No3Node* (*nova_svg_no3_Nova_No3Node_native_Nova_No3Node)(nova_svg_no3_Nova_No3Node*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_no3_native_No3Node
{
nova_svg_no3_Nova_No3Node_native0_Nova_toJs toJs;
nova_svg_no3_Nova_No3Node_native_Nova_No3Node No3Node;
} nova_svg_no3_native_No3Node;

typedef nova_Nova_String* (*nova_svg_no3_Nova_No3Selection_native_Nova_toJs)(nova_svg_no3_Nova_No3Selection*, nova_exception_Nova_ExceptionData*);
typedef nova_svg_no3_Nova_No3Selection* (*nova_svg_no3_Nova_No3Selection_native_Nova_No3Selection)(nova_svg_no3_Nova_No3Selection*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_svg_no3_native_No3Selection
{
nova_svg_no3_Nova_No3Selection_native_Nova_toJs toJs;
nova_svg_no3_Nova_No3Selection_native_Nova_No3Selection No3Selection;
} nova_svg_no3_native_No3Selection;

typedef void (*nova_thread_Nova_Thread_native_Nova_start)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_thread_Nova_Thread_native_Nova_join)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_thread_Nova_Thread_native_Nova_kill)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_thread_Nova_Thread_native_Nova_sleep)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*, long_long);
typedef void (*nova_thread_Nova_Thread_native0_Nova_run)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*);
typedef nova_thread_Nova_Thread* (*nova_thread_Nova_Thread_native_Nova_Thread)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*);

typedef struct nova_thread_native_Thread
{
nova_thread_Nova_Thread_native_Nova_start start;
nova_thread_Nova_Thread_native_Nova_join join;
nova_thread_Nova_Thread_native_Nova_kill kill;
nova_thread_Nova_Thread_native_Nova_sleep sleep;
nova_thread_Nova_Thread_native0_Nova_run run;
nova_thread_Nova_Thread_native_Nova_Thread Thread;
} nova_thread_native_Thread;

typedef void (*nova_thread_Nova_UncaughtExceptionHandler_native0_Nova_uncaughtException)(nova_thread_Nova_UncaughtExceptionHandler*, nova_exception_Nova_ExceptionData*, nova_thread_Nova_Thread*, nova_exception_Nova_Exception*);
typedef nova_thread_Nova_UncaughtExceptionHandler* (*nova_thread_Nova_UncaughtExceptionHandler_native_Nova_UncaughtExceptionHandler)(nova_thread_Nova_UncaughtExceptionHandler*, nova_exception_Nova_ExceptionData*);

typedef struct nova_thread_native_UncaughtExceptionHandler
{
nova_thread_Nova_UncaughtExceptionHandler_native0_Nova_uncaughtException uncaughtException__nova_thread_Thread__nova_exception_Exception;
nova_thread_Nova_UncaughtExceptionHandler_native_Nova_UncaughtExceptionHandler UncaughtExceptionHandler;
} nova_thread_native_UncaughtExceptionHandler;

typedef nova_thread_async_Nova_AsyncResult* (*nova_thread_async_Nova_Async_native_Nova_execute)(nova_thread_async_Nova_Async*, nova_exception_Nova_ExceptionData*, nova_thread_async_Nova_Async_closure3_Nova_func nova_thread_async_Nova_Async_Nova_func, void* nova_thread_async_Nova_Async_ref_Nova_func, void* func_context);
typedef nova_thread_async_Nova_Async* (*nova_thread_async_Nova_Async_native_Nova_Async)(nova_thread_async_Nova_Async*, nova_exception_Nova_ExceptionData*);

typedef struct nova_thread_async_native_Async
{
nova_thread_async_Nova_Async_native_Nova_execute execute;
nova_thread_async_Nova_Async_native_Nova_Async Async;
} nova_thread_async_native_Async;

typedef nova_thread_async_Nova_AsyncResult* (*nova_thread_async_Nova_AsyncResult_native_Nova_AsyncResult)(nova_thread_async_Nova_AsyncResult*, nova_exception_Nova_ExceptionData*);

typedef struct nova_thread_async_native_AsyncResult
{
nova_thread_async_Nova_AsyncResult_native_Nova_AsyncResult AsyncResult;
} nova_thread_async_native_AsyncResult;

typedef void (*nova_time_Nova_Date_native_Nova_decodeDate)(nova_time_Nova_Date*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*);
typedef void (*nova_time_Nova_Date_native_Nova_updateTime)(nova_time_Nova_Date*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_time_Nova_Date_native0_Nova_formatDate)(nova_time_Nova_Date*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_time_Nova_Date_native1_Nova_formatDate)(nova_time_Nova_Date*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, int, int, int, int, int, int);
typedef nova_time_Nova_Date* (*nova_time_Nova_Date_native_Nova_Date)(nova_time_Nova_Date*, nova_exception_Nova_ExceptionData*);

typedef struct nova_time_native_Date
{
nova_time_Nova_Date_native_Nova_decodeDate decodeDate;
nova_time_Nova_Date_native_Nova_updateTime updateTime;
nova_time_Nova_Date_native0_Nova_formatDate formatDate__nova_String;
nova_time_Nova_Date_native1_Nova_formatDate formatDate__nova_String__nova_primitive_number_Int__nova_primitive_number_Int__nova_primitive_number_Int__nova_primitive_number_Int__nova_primitive_number_Int__nova_primitive_number_Int;
nova_time_Nova_Date_native_Nova_Date Date;
} nova_time_native_Date;

typedef nova_time_Nova_Time* (*nova_time_Nova_Time_native_Nova_Time)(nova_time_Nova_Time*, nova_exception_Nova_ExceptionData*);

typedef struct nova_time_native_Time
{
nova_time_Nova_Time_native_Nova_Time Time;
} nova_time_native_Time;

typedef nova_time_Nova_Timer* (*nova_time_Nova_Timer_native_Nova_start)(nova_time_Nova_Timer*, nova_exception_Nova_ExceptionData*);
typedef nova_time_Nova_Timer* (*nova_time_Nova_Timer_native_Nova_stop)(nova_time_Nova_Timer*, nova_exception_Nova_ExceptionData*);
typedef nova_time_Nova_Timer* (*nova_time_Nova_Timer_native_Nova_Timer)(nova_time_Nova_Timer*, nova_exception_Nova_ExceptionData*);

typedef struct nova_time_native_Timer
{
nova_time_Nova_Timer_native_Nova_start start;
nova_time_Nova_Timer_native_Nova_stop stop;
nova_time_Nova_Timer_native_Nova_Timer Timer;
} nova_time_native_Timer;

typedef nova_Nova_String* (*nova_web_js_json_Nova_Json_native0_Nova_toString)(nova_web_js_json_Nova_Json*, nova_exception_Nova_ExceptionData*);
typedef nova_web_js_json_Nova_Json* (*nova_web_js_json_Nova_Json_native_Nova_Json)(nova_web_js_json_Nova_Json*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_js_json_native_Json
{
nova_web_js_json_Nova_Json_native0_Nova_toString toString;
nova_web_js_json_Nova_Json_native_Nova_Json Json;
} nova_web_js_json_native_Json;

typedef void (*nova_web_svg_Nova_Svg_native_Nova_generateOutput)(nova_web_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef void (*nova_web_svg_Nova_Svg_native_Nova_generateHTMLOutput)(nova_web_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_web_svg_Nova_Svg* (*nova_web_svg_Nova_Svg_native_Nova_Svg)(nova_web_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_native_Svg
{
nova_web_svg_Nova_Svg_native_Nova_generateOutput generateOutput;
nova_web_svg_Nova_Svg_native_Nova_generateHTMLOutput generateHTMLOutput;
nova_web_svg_Nova_Svg_native_Nova_Svg Svg;
} nova_web_svg_native_Svg;

typedef void (*nova_web_svg_Nova_SvgCircle_native_Nova_generateOutput)(nova_web_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_Nova_String* (*nova_web_svg_Nova_SvgCircle_native0_Nova_toString)(nova_web_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*);
typedef nova_web_svg_Nova_SvgCircle* (*nova_web_svg_Nova_SvgCircle_native_Nova_SvgCircle)(nova_web_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*, double, double, int);

typedef struct nova_web_svg_native_SvgCircle
{
nova_web_svg_Nova_SvgCircle_native_Nova_generateOutput generateOutput;
nova_web_svg_Nova_SvgCircle_native0_Nova_toString toString;
nova_web_svg_Nova_SvgCircle_native_Nova_SvgCircle SvgCircle;
} nova_web_svg_native_SvgCircle;

typedef void (*nova_web_svg_Nova_SvgComponent_native0_Nova_generateOutput)(nova_web_svg_Nova_SvgComponent*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_web_svg_Nova_SvgComponent* (*nova_web_svg_Nova_SvgComponent_native_Nova_SvgComponent)(nova_web_svg_Nova_SvgComponent*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_native_SvgComponent
{
nova_web_svg_Nova_SvgComponent_native0_Nova_generateOutput generateOutput__nova_io_File;
nova_web_svg_Nova_SvgComponent_native_Nova_SvgComponent SvgComponent;
} nova_web_svg_native_SvgComponent;

typedef void (*nova_web_svg_Nova_SvgComponentList_native_Nova_generateOutput)(nova_web_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef void (*nova_web_svg_Nova_SvgComponentList_native_Nova_addChild)(nova_web_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*, nova_web_svg_Nova_SvgComponent*);
typedef nova_web_svg_Nova_SvgComponentList* (*nova_web_svg_Nova_SvgComponentList_native_Nova_SvgComponentList)(nova_web_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_native_SvgComponentList
{
nova_web_svg_Nova_SvgComponentList_native_Nova_generateOutput generateOutput;
nova_web_svg_Nova_SvgComponentList_native_Nova_addChild addChild;
nova_web_svg_Nova_SvgComponentList_native_Nova_SvgComponentList SvgComponentList;
} nova_web_svg_native_SvgComponentList;

typedef nova_web_svg_Nova_SvgComponentNode* (*nova_web_svg_Nova_SvgComponentNode_native_Nova_SvgComponentNode)(nova_web_svg_Nova_SvgComponentNode*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_native_SvgComponentNode
{
nova_web_svg_Nova_SvgComponentNode_native_Nova_SvgComponentNode SvgComponentNode;
} nova_web_svg_native_SvgComponentNode;

typedef void (*nova_web_svg_Nova_SvgMainComponent_native0_Nova_generateOutput)(nova_web_svg_Nova_SvgMainComponent*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_web_svg_Nova_SvgMainComponent* (*nova_web_svg_Nova_SvgMainComponent_native_Nova_SvgMainComponent)(nova_web_svg_Nova_SvgMainComponent*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_native_SvgMainComponent
{
nova_web_svg_Nova_SvgMainComponent_native0_Nova_generateOutput generateOutput__nova_io_File;
nova_web_svg_Nova_SvgMainComponent_native_Nova_SvgMainComponent SvgMainComponent;
} nova_web_svg_native_SvgMainComponent;

typedef nova_web_svg_no3_Nova_No3Select* (*nova_web_svg_no3_Nova_No3_native_Nova_select)(nova_web_svg_no3_Nova_No3*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_web_svg_no3_Nova_No3SelectAll* (*nova_web_svg_no3_Nova_No3_native_Nova_selectAll)(nova_web_svg_no3_Nova_No3*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_web_svg_no3_Nova_No3* (*nova_web_svg_no3_Nova_No3_native_Nova_No3)(nova_web_svg_no3_Nova_No3*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_no3_native_No3
{
nova_web_svg_no3_Nova_No3_native_Nova_select select;
nova_web_svg_no3_Nova_No3_native_Nova_selectAll selectAll;
nova_web_svg_no3_Nova_No3_native_Nova_No3 No3;
} nova_web_svg_no3_native_No3;

typedef nova_Nova_String* (*nova_web_svg_no3_Nova_No3Node_native0_Nova_toJs)(nova_web_svg_no3_Nova_No3Node*, nova_exception_Nova_ExceptionData*);
typedef nova_web_svg_no3_Nova_No3Node* (*nova_web_svg_no3_Nova_No3Node_native_Nova_No3Node)(nova_web_svg_no3_Nova_No3Node*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_no3_native_No3Node
{
nova_web_svg_no3_Nova_No3Node_native0_Nova_toJs toJs;
nova_web_svg_no3_Nova_No3Node_native_Nova_No3Node No3Node;
} nova_web_svg_no3_native_No3Node;

typedef nova_Nova_String* (*nova_web_svg_no3_Nova_No3Select_native_Nova_toJs)(nova_web_svg_no3_Nova_No3Select*, nova_exception_Nova_ExceptionData*);
typedef nova_web_svg_no3_Nova_No3Select* (*nova_web_svg_no3_Nova_No3Select_native_Nova_No3Select)(nova_web_svg_no3_Nova_No3Select*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_web_svg_no3_native_No3Select
{
nova_web_svg_no3_Nova_No3Select_native_Nova_toJs toJs;
nova_web_svg_no3_Nova_No3Select_native_Nova_No3Select No3Select;
} nova_web_svg_no3_native_No3Select;

typedef nova_Nova_String* (*nova_web_svg_no3_Nova_No3SelectAll_native0_Nova_toJs)(nova_web_svg_no3_Nova_No3SelectAll*, nova_exception_Nova_ExceptionData*);
typedef nova_web_svg_no3_Nova_No3SelectAll* (*nova_web_svg_no3_Nova_No3SelectAll_native_Nova_No3SelectAll)(nova_web_svg_no3_Nova_No3SelectAll*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_web_svg_no3_native_No3SelectAll
{
nova_web_svg_no3_Nova_No3SelectAll_native0_Nova_toJs toJs;
nova_web_svg_no3_Nova_No3SelectAll_native_Nova_No3SelectAll No3SelectAll;
} nova_web_svg_no3_native_No3SelectAll;

typedef int (*example_Nova_Animal_native0_Nova_getNumLegs)(example_Nova_Animal*, nova_exception_Nova_ExceptionData*);
typedef int (*example_Nova_Animal_native0_Nova_getNumEyes)(example_Nova_Animal*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*example_Nova_Animal_native0_Nova_getDescription)(example_Nova_Animal*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*example_Nova_Animal_native0_Nova_toString)(example_Nova_Animal*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_Animal* (*example_Nova_Animal_native_Nova_Animal)(example_Nova_Animal*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Animal
{
example_Nova_Animal_native0_Nova_getNumLegs getNumLegs;
example_Nova_Animal_native0_Nova_getNumEyes getNumEyes;
example_Nova_Animal_native0_Nova_getDescription getDescription;
example_Nova_Animal_native0_Nova_toString toString;
example_Nova_Animal_native_Nova_Animal Animal;
} example_native_Animal;

typedef void (*example_Nova_ArrayDemo_native_Nova_main)(example_Nova_ArrayDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_ArrayDemo* (*example_Nova_ArrayDemo_native_Nova_ArrayDemo)(example_Nova_ArrayDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_ArrayDemo
{
example_Nova_ArrayDemo_native_Nova_main main;
example_Nova_ArrayDemo_native_Nova_ArrayDemo ArrayDemo;
} example_native_ArrayDemo;

typedef void (*example_Nova_BodyBuilder_native_Nova_sayHello)(example_Nova_BodyBuilder*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_BodyBuilder* (*example_Nova_BodyBuilder_native_Nova_BodyBuilder)(example_Nova_BodyBuilder*, nova_exception_Nova_ExceptionData*, int, nova_Nova_String*);

typedef struct example_native_BodyBuilder
{
example_Nova_BodyBuilder_native_Nova_sayHello sayHello;
example_Nova_BodyBuilder_native_Nova_BodyBuilder BodyBuilder;
} example_native_BodyBuilder;

typedef void (*example_Nova_ClosureDemo_native_Nova_main)(example_Nova_ClosureDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_ClosureDemo* (*example_Nova_ClosureDemo_native_Nova_ClosureDemo)(example_Nova_ClosureDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_ClosureDemo
{
example_Nova_ClosureDemo_native_Nova_main main;
example_Nova_ClosureDemo_native_Nova_ClosureDemo ClosureDemo;
} example_native_ClosureDemo;

typedef int (*example_Nova_Dog_native_Nova_getNumLegs)(example_Nova_Dog*, nova_exception_Nova_ExceptionData*);
typedef int (*example_Nova_Dog_native_Nova_getNumEyes)(example_Nova_Dog*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*example_Nova_Dog_native_Nova_getDescription)(example_Nova_Dog*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_Dog* (*example_Nova_Dog_native_Nova_Dog)(example_Nova_Dog*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Dog
{
example_Nova_Dog_native_Nova_getNumLegs getNumLegs;
example_Nova_Dog_native_Nova_getNumEyes getNumEyes;
example_Nova_Dog_native_Nova_getDescription getDescription;
example_Nova_Dog_native_Nova_Dog Dog;
} example_native_Dog;

typedef void (*example_Nova_ExceptionHandlingDemo_native_Nova_main)(example_Nova_ExceptionHandlingDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_ExceptionHandlingDemo* (*example_Nova_ExceptionHandlingDemo_native_Nova_ExceptionHandlingDemo)(example_Nova_ExceptionHandlingDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_ExceptionHandlingDemo
{
example_Nova_ExceptionHandlingDemo_native_Nova_main main;
example_Nova_ExceptionHandlingDemo_native_Nova_ExceptionHandlingDemo ExceptionHandlingDemo;
} example_native_ExceptionHandlingDemo;

typedef void (*example_Nova_FileTest_native_Nova_main)(example_Nova_FileTest*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_FileTest* (*example_Nova_FileTest_native_Nova_FileTest)(example_Nova_FileTest*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_FileTest
{
example_Nova_FileTest_native_Nova_main main;
example_Nova_FileTest_native_Nova_FileTest FileTest;
} example_native_FileTest;

typedef void (*example_Nova_GenericDemo_native_Nova_main)(example_Nova_GenericDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_GenericDemo* (*example_Nova_GenericDemo_native_Nova_GenericDemo)(example_Nova_GenericDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_GenericDemo
{
example_Nova_GenericDemo_native_Nova_main main;
example_Nova_GenericDemo_native_Nova_GenericDemo GenericDemo;
} example_native_GenericDemo;

typedef void (*example_Nova_HashMapDemo_native_Nova_main)(example_Nova_HashMapDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_HashMapDemo* (*example_Nova_HashMapDemo_native_Nova_HashMapDemo)(example_Nova_HashMapDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_HashMapDemo
{
example_Nova_HashMapDemo_native_Nova_main main;
example_Nova_HashMapDemo_native_Nova_HashMapDemo HashMapDemo;
} example_native_HashMapDemo;

typedef void (*example_Nova_IntegerTest_native_Nova_main)(example_Nova_IntegerTest*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_IntegerTest* (*example_Nova_IntegerTest_native_Nova_IntegerTest)(example_Nova_IntegerTest*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_IntegerTest
{
example_Nova_IntegerTest_native_Nova_main main;
example_Nova_IntegerTest_native_Nova_IntegerTest IntegerTest;
} example_native_IntegerTest;

typedef void (*example_Nova_Lab_native_Nova_main)(example_Nova_Lab*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_Lab* (*example_Nova_Lab_native_Nova_Lab)(example_Nova_Lab*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Lab
{
example_Nova_Lab_native_Nova_main main;
example_Nova_Lab_native_Nova_Lab Lab;
} example_native_Lab;

typedef void (*example_Nova_MathDemo_native_Nova_main)(example_Nova_MathDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_MathDemo* (*example_Nova_MathDemo_native_Nova_MathDemo)(example_Nova_MathDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_MathDemo
{
example_Nova_MathDemo_native_Nova_main main;
example_Nova_MathDemo_native_Nova_MathDemo MathDemo;
} example_native_MathDemo;

typedef example_Nova_NonWholeDivisionException* (*example_Nova_NonWholeDivisionException_native_Nova_NonWholeDivisionException)(example_Nova_NonWholeDivisionException*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_NonWholeDivisionException
{
example_Nova_NonWholeDivisionException_native_Nova_NonWholeDivisionException NonWholeDivisionException;
} example_native_NonWholeDivisionException;

typedef void (*example_Nova_Person_native0_Nova_sayHello)(example_Nova_Person*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_Person* (*example_Nova_Person_native_Nova_Person)(example_Nova_Person*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, int);

typedef struct example_native_Person
{
example_Nova_Person_native0_Nova_sayHello sayHello;
example_Nova_Person_native_Nova_Person Person;
} example_native_Person;

typedef int (*example_Nova_Polygon_native0_Nova_numberSides)(example_Nova_Polygon*, nova_exception_Nova_ExceptionData*);
typedef double (*example_Nova_Polygon_native0_Nova_calculateArea)(example_Nova_Polygon*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Polygon
{
example_Nova_Polygon_native0_Nova_numberSides numberSides;
example_Nova_Polygon_native0_Nova_calculateArea calculateArea;
} example_native_Polygon;

typedef void (*example_Nova_PolymorphismDemo_native_Nova_main)(example_Nova_PolymorphismDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_PolymorphismDemo* (*example_Nova_PolymorphismDemo_native_Nova_PolymorphismDemo)(example_Nova_PolymorphismDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_PolymorphismDemo
{
example_Nova_PolymorphismDemo_native_Nova_main main;
example_Nova_PolymorphismDemo_native_Nova_PolymorphismDemo PolymorphismDemo;
} example_native_PolymorphismDemo;

typedef void (*example_Nova_QueueDemo_native_Nova_main)(example_Nova_QueueDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_QueueDemo* (*example_Nova_QueueDemo_native_Nova_QueueDemo)(example_Nova_QueueDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_QueueDemo
{
example_Nova_QueueDemo_native_Nova_main main;
example_Nova_QueueDemo_native_Nova_QueueDemo QueueDemo;
} example_native_QueueDemo;

typedef int (*example_Nova_Spider_native0_Nova_getNumLegs)(example_Nova_Spider*, nova_exception_Nova_ExceptionData*);
typedef int (*example_Nova_Spider_native0_Nova_getNumEyes)(example_Nova_Spider*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*example_Nova_Spider_native0_Nova_getDescription)(example_Nova_Spider*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_Spider* (*example_Nova_Spider_native_Nova_Spider)(example_Nova_Spider*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Spider
{
example_Nova_Spider_native0_Nova_getNumLegs getNumLegs;
example_Nova_Spider_native0_Nova_getNumEyes getNumEyes;
example_Nova_Spider_native0_Nova_getDescription getDescription;
example_Nova_Spider_native_Nova_Spider Spider;
} example_native_Spider;

typedef int (*example_Nova_Square_native_Nova_numberSides)(example_Nova_Square*, nova_exception_Nova_ExceptionData*);
typedef double (*example_Nova_Square_native_Nova_calculateArea)(example_Nova_Square*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_Square* (*example_Nova_Square_native_Nova_Square)(example_Nova_Square*, nova_exception_Nova_ExceptionData*, int);

typedef struct example_native_Square
{
example_Nova_Square_native_Nova_numberSides numberSides;
example_Nova_Square_native_Nova_calculateArea calculateArea;
example_Nova_Square_native_Nova_Square Square;
} example_native_Square;

typedef void (*example_Nova_SvgChart_native_Nova_main)(example_Nova_SvgChart*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_SvgChart* (*example_Nova_SvgChart_native_Nova_SvgChart)(example_Nova_SvgChart*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_SvgChart
{
example_Nova_SvgChart_native_Nova_main main;
example_Nova_SvgChart_native_Nova_SvgChart SvgChart;
} example_native_SvgChart;

typedef void (*example_Nova_SvgFractal_native_Nova_main)(example_Nova_SvgFractal*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_SvgFractal* (*example_Nova_SvgFractal_native_Nova_SvgFractal)(example_Nova_SvgFractal*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_SvgFractal
{
example_Nova_SvgFractal_native_Nova_main main;
example_Nova_SvgFractal_native_Nova_SvgFractal SvgFractal;
} example_native_SvgFractal;

typedef example_Nova_T1* (*example_Nova_T1_native_Nova_T1)(example_Nova_T1*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_T1
{
example_Nova_T1_native_Nova_T1 T1;
} example_native_T1;

typedef example_Nova_T2* (*example_Nova_T2_native_Nova_T2)(example_Nova_T2*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_T2
{
example_Nova_T2_native_Nova_T2 T2;
} example_native_T2;

typedef void (*example_Nova_Test_native_Nova_main)(example_Nova_Test*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_Test* (*example_Nova_Test_native_Nova_Test)(example_Nova_Test*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Test
{
example_Nova_Test_native_Nova_main main;
example_Nova_Test_native_Nova_Test Test;
} example_native_Test;

typedef void (*example_Nova_ThreadDemo_native_Nova_main)(example_Nova_ThreadDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_ThreadDemo* (*example_Nova_ThreadDemo_native_Nova_ThreadDemo)(example_Nova_ThreadDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_ThreadDemo
{
example_Nova_ThreadDemo_native_Nova_main main;
example_Nova_ThreadDemo_native_Nova_ThreadDemo ThreadDemo;
} example_native_ThreadDemo;

typedef void (*example_Nova_ThreadDemoImplementation_native0_Nova_run)(example_Nova_ThreadDemoImplementation*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_ThreadDemoImplementation* (*example_Nova_ThreadDemoImplementation_native_Nova_ThreadDemoImplementation)(example_Nova_ThreadDemoImplementation*, nova_exception_Nova_ExceptionData*, long_long, nova_Nova_String*);

typedef struct example_native_ThreadDemoImplementation
{
example_Nova_ThreadDemoImplementation_native0_Nova_run run;
example_Nova_ThreadDemoImplementation_native_Nova_ThreadDemoImplementation ThreadDemoImplementation;
} example_native_ThreadDemoImplementation;

typedef void (*example_ackermann_Nova_Ackermann_native_Nova_main)(example_ackermann_Nova_Ackermann*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef int (*example_ackermann_Nova_Ackermann_native_Nova_run)(example_ackermann_Nova_Ackermann*, nova_exception_Nova_ExceptionData*, int, int);
typedef int (*example_ackermann_Nova_Ackermann_native_Nova_run2)(example_ackermann_Nova_Ackermann*, nova_exception_Nova_ExceptionData*, int, int);
typedef example_ackermann_Nova_Ackermann* (*example_ackermann_Nova_Ackermann_native_Nova_Ackermann)(example_ackermann_Nova_Ackermann*, nova_exception_Nova_ExceptionData*);

typedef struct example_ackermann_native_Ackermann
{
example_ackermann_Nova_Ackermann_native_Nova_main main;
example_ackermann_Nova_Ackermann_native_Nova_run run;
example_ackermann_Nova_Ackermann_native_Nova_run2 run2;
example_ackermann_Nova_Ackermann_native_Nova_Ackermann Ackermann;
} example_ackermann_native_Ackermann;

typedef example_copy_Nova_Dog* (*example_copy_Nova_Dog_native_Nova_Dog)(example_copy_Nova_Dog*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct example_copy_native_Dog
{
example_copy_Nova_Dog_native_Nova_Dog Dog;
} example_copy_native_Dog;

typedef void (*example_database_Nova_DatabaseDemo_native_Nova_main)(example_database_Nova_DatabaseDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_database_Nova_DatabaseDemo* (*example_database_Nova_DatabaseDemo_native_Nova_DatabaseDemo)(example_database_Nova_DatabaseDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_database_native_DatabaseDemo
{
example_database_Nova_DatabaseDemo_native_Nova_main main;
example_database_Nova_DatabaseDemo_native_Nova_DatabaseDemo DatabaseDemo;
} example_database_native_DatabaseDemo;

typedef void (*example_network_Nova_ClientDemo_native_Nova_main)(example_network_Nova_ClientDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_network_Nova_ClientDemo* (*example_network_Nova_ClientDemo_native_Nova_ClientDemo)(example_network_Nova_ClientDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_network_native_ClientDemo
{
example_network_Nova_ClientDemo_native_Nova_main main;
example_network_Nova_ClientDemo_native_Nova_ClientDemo ClientDemo;
} example_network_native_ClientDemo;

typedef void (*example_network_Nova_ConnectionThread_native0_Nova_run)(example_network_Nova_ConnectionThread*, nova_exception_Nova_ExceptionData*);
typedef example_network_Nova_ConnectionThread* (*example_network_Nova_ConnectionThread_native_Nova_ConnectionThread)(example_network_Nova_ConnectionThread*, nova_exception_Nova_ExceptionData*, nova_network_Nova_ConnectionSocket*);

typedef struct example_network_native_ConnectionThread
{
example_network_Nova_ConnectionThread_native0_Nova_run run;
example_network_Nova_ConnectionThread_native_Nova_ConnectionThread ConnectionThread;
} example_network_native_ConnectionThread;

typedef void (*example_network_Nova_OutputThread_native0_Nova_run)(example_network_Nova_OutputThread*, nova_exception_Nova_ExceptionData*);
typedef example_network_Nova_OutputThread* (*example_network_Nova_OutputThread_native_Nova_OutputThread)(example_network_Nova_OutputThread*, nova_exception_Nova_ExceptionData*, nova_network_Nova_ServerSocket*, nova_network_Nova_ConnectionSocket*);

typedef struct example_network_native_OutputThread
{
example_network_Nova_OutputThread_native0_Nova_run run;
example_network_Nova_OutputThread_native_Nova_OutputThread OutputThread;
} example_network_native_OutputThread;

typedef void (*example_network_Nova_ServerDemo_native_Nova_main)(example_network_Nova_ServerDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_network_Nova_ServerDemo* (*example_network_Nova_ServerDemo_native_Nova_ServerDemo)(example_network_Nova_ServerDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_network_native_ServerDemo
{
example_network_Nova_ServerDemo_native_Nova_main main;
example_network_Nova_ServerDemo_native_Nova_ServerDemo ServerDemo;
} example_network_native_ServerDemo;

typedef void (*stabilitytest_Nova_AssignmentStability_native_Nova_test)(stabilitytest_Nova_AssignmentStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_AssignmentStability* (*stabilitytest_Nova_AssignmentStability_native_Nova_AssignmentStability)(stabilitytest_Nova_AssignmentStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_AssignmentStability
{
stabilitytest_Nova_AssignmentStability_native_Nova_test test;
stabilitytest_Nova_AssignmentStability_native_Nova_AssignmentStability AssignmentStability;
} stabilitytest_native_AssignmentStability;

typedef stabilitytest_Nova_ClassWithProperties* (*stabilitytest_Nova_ClassWithProperties_native_Nova_ClassWithProperties)(stabilitytest_Nova_ClassWithProperties*, nova_exception_Nova_ExceptionData*);

typedef struct stabilitytest_native_ClassWithProperties
{
stabilitytest_Nova_ClassWithProperties_native_Nova_ClassWithProperties ClassWithProperties;
} stabilitytest_native_ClassWithProperties;

typedef void (*stabilitytest_Nova_ClientThread_native0_Nova_run)(stabilitytest_Nova_ClientThread*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ClientThread* (*stabilitytest_Nova_ClientThread_native_Nova_ClientThread)(stabilitytest_Nova_ClientThread*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*, int);

typedef struct stabilitytest_native_ClientThread
{
stabilitytest_Nova_ClientThread_native0_Nova_run run;
stabilitytest_Nova_ClientThread_native_Nova_ClientThread ClientThread;
} stabilitytest_native_ClientThread;

typedef void (*stabilitytest_Nova_ClosureStability_native0_Nova_test)(stabilitytest_Nova_ClosureStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ClosureStability* (*stabilitytest_Nova_ClosureStability_native_Nova_ClosureStability)(stabilitytest_Nova_ClosureStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_ClosureStability
{
stabilitytest_Nova_ClosureStability_native0_Nova_test test;
stabilitytest_Nova_ClosureStability_native_Nova_ClosureStability ClosureStability;
} stabilitytest_native_ClosureStability;

typedef void (*stabilitytest_Nova_ExceptionStability_native0_Nova_test)(stabilitytest_Nova_ExceptionStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ExceptionStability* (*stabilitytest_Nova_ExceptionStability_native_Nova_ExceptionStability)(stabilitytest_Nova_ExceptionStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_ExceptionStability
{
stabilitytest_Nova_ExceptionStability_native0_Nova_test test;
stabilitytest_Nova_ExceptionStability_native_Nova_ExceptionStability ExceptionStability;
} stabilitytest_native_ExceptionStability;

typedef void (*stabilitytest_Nova_FileStability_native0_Nova_test)(stabilitytest_Nova_FileStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_FileStability* (*stabilitytest_Nova_FileStability_native_Nova_FileStability)(stabilitytest_Nova_FileStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_FileStability
{
stabilitytest_Nova_FileStability_native0_Nova_test test;
stabilitytest_Nova_FileStability_native_Nova_FileStability FileStability;
} stabilitytest_native_FileStability;

typedef void (*stabilitytest_Nova_LambdaStability_native0_Nova_test)(stabilitytest_Nova_LambdaStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_LambdaStability* (*stabilitytest_Nova_LambdaStability_native_Nova_LambdaStability)(stabilitytest_Nova_LambdaStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_LambdaStability
{
stabilitytest_Nova_LambdaStability_native0_Nova_test test;
stabilitytest_Nova_LambdaStability_native_Nova_LambdaStability LambdaStability;
} stabilitytest_native_LambdaStability;

typedef void (*stabilitytest_Nova_NetworkStability_native0_Nova_test)(stabilitytest_Nova_NetworkStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_NetworkStability* (*stabilitytest_Nova_NetworkStability_native_Nova_NetworkStability)(stabilitytest_Nova_NetworkStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_NetworkStability
{
stabilitytest_Nova_NetworkStability_native0_Nova_test test;
stabilitytest_Nova_NetworkStability_native_Nova_NetworkStability NetworkStability;
} stabilitytest_native_NetworkStability;

typedef nova_Nova_String* (*stabilitytest_Nova_PolymorphicSubClass_native_Nova_toString)(stabilitytest_Nova_PolymorphicSubClass*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_PolymorphicSubClass* (*stabilitytest_Nova_PolymorphicSubClass_native_Nova_PolymorphicSubClass)(stabilitytest_Nova_PolymorphicSubClass*, nova_exception_Nova_ExceptionData*);

typedef struct stabilitytest_native_PolymorphicSubClass
{
stabilitytest_Nova_PolymorphicSubClass_native_Nova_toString toString;
stabilitytest_Nova_PolymorphicSubClass_native_Nova_PolymorphicSubClass PolymorphicSubClass;
} stabilitytest_native_PolymorphicSubClass;

typedef void (*stabilitytest_Nova_PolymorphicSuperClass_native_Nova_giveBirth)(stabilitytest_Nova_PolymorphicSuperClass*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*stabilitytest_Nova_PolymorphicSuperClass_native0_Nova_toString)(stabilitytest_Nova_PolymorphicSuperClass*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_PolymorphicSuperClass* (*stabilitytest_Nova_PolymorphicSuperClass_native_Nova_PolymorphicSuperClass)(stabilitytest_Nova_PolymorphicSuperClass*, nova_exception_Nova_ExceptionData*);

typedef struct stabilitytest_native_PolymorphicSuperClass
{
stabilitytest_Nova_PolymorphicSuperClass_native_Nova_giveBirth giveBirth;
stabilitytest_Nova_PolymorphicSuperClass_native0_Nova_toString toString;
stabilitytest_Nova_PolymorphicSuperClass_native_Nova_PolymorphicSuperClass PolymorphicSuperClass;
} stabilitytest_native_PolymorphicSuperClass;

typedef void (*stabilitytest_Nova_PolymorphismStability_native0_Nova_test)(stabilitytest_Nova_PolymorphismStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_PolymorphismStability* (*stabilitytest_Nova_PolymorphismStability_native_Nova_PolymorphismStability)(stabilitytest_Nova_PolymorphismStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_PolymorphismStability
{
stabilitytest_Nova_PolymorphismStability_native0_Nova_test test;
stabilitytest_Nova_PolymorphismStability_native_Nova_PolymorphismStability PolymorphismStability;
} stabilitytest_native_PolymorphismStability;

typedef void (*stabilitytest_Nova_StabilityExceptionHandler_native_Nova_uncaughtException)(stabilitytest_Nova_StabilityExceptionHandler*, nova_exception_Nova_ExceptionData*, nova_thread_Nova_Thread*, nova_exception_Nova_Exception*);
typedef stabilitytest_Nova_StabilityExceptionHandler* (*stabilitytest_Nova_StabilityExceptionHandler_native_Nova_StabilityExceptionHandler)(stabilitytest_Nova_StabilityExceptionHandler*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_StabilityExceptionHandler
{
stabilitytest_Nova_StabilityExceptionHandler_native_Nova_uncaughtException uncaughtException;
stabilitytest_Nova_StabilityExceptionHandler_native_Nova_StabilityExceptionHandler StabilityExceptionHandler;
} stabilitytest_native_StabilityExceptionHandler;

typedef void (*stabilitytest_Nova_StabilityTest_native_Nova_main)(stabilitytest_Nova_StabilityTest*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef void (*stabilitytest_Nova_StabilityTest_native0_Nova_test)(stabilitytest_Nova_StabilityTest*, nova_exception_Nova_ExceptionData*);
typedef void (*stabilitytest_Nova_StabilityTest_native0_Nova_fail)(stabilitytest_Nova_StabilityTest*, nova_exception_Nova_ExceptionData*);
typedef void (*stabilitytest_Nova_StabilityTest_native1_Nova_fail)(stabilitytest_Nova_StabilityTest*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef stabilitytest_Nova_StabilityTest* (*stabilitytest_Nova_StabilityTest_native_Nova_StabilityTest)(stabilitytest_Nova_StabilityTest*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_StabilityTest
{
stabilitytest_Nova_StabilityTest_native_Nova_main main;
stabilitytest_Nova_StabilityTest_native0_Nova_test test;
stabilitytest_Nova_StabilityTest_native0_Nova_fail fail;
stabilitytest_Nova_StabilityTest_native1_Nova_fail fail__nova_String;
stabilitytest_Nova_StabilityTest_native_Nova_StabilityTest StabilityTest;
} stabilitytest_native_StabilityTest;

typedef void (*stabilitytest_Nova_StabilityTestCase_native0_Nova_test)(stabilitytest_Nova_StabilityTestCase*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_StabilityTestCase* (*stabilitytest_Nova_StabilityTestCase_native_Nova_StabilityTestCase)(stabilitytest_Nova_StabilityTestCase*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_StabilityTestCase
{
stabilitytest_Nova_StabilityTestCase_native0_Nova_test test;
stabilitytest_Nova_StabilityTestCase_native_Nova_StabilityTestCase StabilityTestCase;
} stabilitytest_native_StabilityTestCase;

typedef stabilitytest_Nova_StabilityTestException* (*stabilitytest_Nova_StabilityTestException_native_Nova_StabilityTestException)(stabilitytest_Nova_StabilityTestException*, nova_exception_Nova_ExceptionData*);

typedef struct stabilitytest_native_StabilityTestException
{
stabilitytest_Nova_StabilityTestException_native_Nova_StabilityTestException StabilityTestException;
} stabilitytest_native_StabilityTestException;

typedef void (*stabilitytest_Nova_SyntaxStability_native0_Nova_test)(stabilitytest_Nova_SyntaxStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_SyntaxStability* (*stabilitytest_Nova_SyntaxStability_native_Nova_SyntaxStability)(stabilitytest_Nova_SyntaxStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_SyntaxStability
{
stabilitytest_Nova_SyntaxStability_native0_Nova_test test;
stabilitytest_Nova_SyntaxStability_native_Nova_SyntaxStability SyntaxStability;
} stabilitytest_native_SyntaxStability;

typedef void (*stabilitytest_Nova_ThreadImplementation_native0_Nova_run)(stabilitytest_Nova_ThreadImplementation*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ThreadImplementation* (*stabilitytest_Nova_ThreadImplementation_native_Nova_ThreadImplementation)(stabilitytest_Nova_ThreadImplementation*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct stabilitytest_native_ThreadImplementation
{
stabilitytest_Nova_ThreadImplementation_native0_Nova_run run;
stabilitytest_Nova_ThreadImplementation_native_Nova_ThreadImplementation ThreadImplementation;
} stabilitytest_native_ThreadImplementation;

typedef void (*stabilitytest_Nova_ThreadStability_native0_Nova_test)(stabilitytest_Nova_ThreadStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ThreadStability* (*stabilitytest_Nova_ThreadStability_native_Nova_ThreadStability)(stabilitytest_Nova_ThreadStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_ThreadStability
{
stabilitytest_Nova_ThreadStability_native0_Nova_test test;
stabilitytest_Nova_ThreadStability_native_Nova_ThreadStability ThreadStability;
} stabilitytest_native_ThreadStability;

typedef void (*stabilitytest_Nova_TimeStability_native0_Nova_test)(stabilitytest_Nova_TimeStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_TimeStability* (*stabilitytest_Nova_TimeStability_native_Nova_TimeStability)(stabilitytest_Nova_TimeStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_TimeStability
{
stabilitytest_Nova_TimeStability_native0_Nova_test test;
stabilitytest_Nova_TimeStability_native_Nova_TimeStability TimeStability;
} stabilitytest_native_TimeStability;

typedef void (*stabilitytest_Nova_ToStringStability_native0_Nova_test)(stabilitytest_Nova_ToStringStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ToStringStability* (*stabilitytest_Nova_ToStringStability_native_Nova_ToStringStability)(stabilitytest_Nova_ToStringStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_ToStringStability
{
stabilitytest_Nova_ToStringStability_native0_Nova_test test;
stabilitytest_Nova_ToStringStability_native_Nova_ToStringStability ToStringStability;
} stabilitytest_native_ToStringStability;

typedef stabilitytest_Nova_UnstableException* (*stabilitytest_Nova_UnstableException_native_Nova_UnstableException)(stabilitytest_Nova_UnstableException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct stabilitytest_native_UnstableException
{
stabilitytest_Nova_UnstableException_native_Nova_UnstableException UnstableException;
} stabilitytest_native_UnstableException;


typedef struct nova_env
{
nova_native_Class nova_Class;
nova_native_Object nova_Object;
nova_native_String nova_String;
nova_native_System nova_System;
nova_database_native_DBConnector nova_database_DBConnector;
nova_database_native_ResultSet nova_database_ResultSet;
nova_datastruct_native_BinaryNode nova_datastruct_BinaryNode;
nova_datastruct_native_BinaryTree nova_datastruct_BinaryTree;
nova_datastruct_native_Bounds nova_datastruct_Bounds;
nova_datastruct_native_Comparable nova_datastruct_Comparable;
nova_datastruct_native_HashMap nova_datastruct_HashMap;
nova_datastruct_native_Node nova_datastruct_Node;
nova_datastruct_native_Pair nova_datastruct_Pair;
nova_datastruct_native_ReversibleHashMap nova_datastruct_ReversibleHashMap;
nova_datastruct_native_Tree nova_datastruct_Tree;
nova_datastruct_native_Vector nova_datastruct_Vector;
nova_datastruct_native_Vector2D nova_datastruct_Vector2D;
nova_datastruct_list_native_Array nova_datastruct_list_Array;
nova_datastruct_list_native_ArrayIterator nova_datastruct_list_ArrayIterator;
nova_datastruct_list_native_CharArray nova_datastruct_list_CharArray;
nova_datastruct_list_native_CharArrayIterator nova_datastruct_list_CharArrayIterator;
nova_datastruct_list_native_CompiledList nova_datastruct_list_CompiledList;
nova_datastruct_list_native_DoubleArray nova_datastruct_list_DoubleArray;
nova_datastruct_list_native_DoubleArrayIterator nova_datastruct_list_DoubleArrayIterator;
nova_datastruct_list_native_EmptyStackException nova_datastruct_list_EmptyStackException;
nova_datastruct_list_native_IntArray nova_datastruct_list_IntArray;
nova_datastruct_list_native_IntArrayIterator nova_datastruct_list_IntArrayIterator;
nova_datastruct_list_native_IntRange nova_datastruct_list_IntRange;
nova_datastruct_list_native_IntRangeIterator nova_datastruct_list_IntRangeIterator;
nova_datastruct_list_native_Iterable nova_datastruct_list_Iterable;
nova_datastruct_list_native_Iterator nova_datastruct_list_Iterator;
nova_datastruct_list_native_LinkedList nova_datastruct_list_LinkedList;
nova_datastruct_list_native_LinkedListIterator nova_datastruct_list_LinkedListIterator;
nova_datastruct_list_native_List nova_datastruct_list_List;
nova_datastruct_list_native_ListNode nova_datastruct_list_ListNode;
nova_datastruct_list_native_NoSuchElementException nova_datastruct_list_NoSuchElementException;
nova_datastruct_list_native_Queue nova_datastruct_list_Queue;
nova_datastruct_list_native_Stack nova_datastruct_list_Stack;
nova_exception_native_DivideByZeroException nova_exception_DivideByZeroException;
nova_exception_native_Exception nova_exception_Exception;
nova_exception_native_ExceptionData nova_exception_ExceptionData;
nova_exception_native_UnimplementedOperationException nova_exception_UnimplementedOperationException;
nova_gc_native_GC nova_gc_GC;
nova_io_native_Console nova_io_Console;
nova_io_native_File nova_io_File;
nova_io_native_FileNotFoundException nova_io_FileNotFoundException;
nova_io_native_InputStream nova_io_InputStream;
nova_io_native_OutputStream nova_io_OutputStream;
nova_io_native_StreamReader nova_io_StreamReader;
nova_math_native_ArithmeticSequence nova_math_ArithmeticSequence;
nova_math_native_Diekstra nova_math_Diekstra;
nova_math_native_GeometricSequence nova_math_GeometricSequence;
nova_math_native_Graph nova_math_Graph;
nova_math_native_InvalidNumericStatementException nova_math_InvalidNumericStatementException;
nova_math_native_Math nova_math_Math;
nova_math_native_Matrix nova_math_Matrix;
nova_math_native_NumericOperand nova_math_NumericOperand;
nova_math_native_NumericOperation nova_math_NumericOperation;
nova_math_native_NumericStatement nova_math_NumericStatement;
nova_math_native_NumericTree nova_math_NumericTree;
nova_math_native_Polynomial nova_math_Polynomial;
nova_math_native_Sequence nova_math_Sequence;
nova_math_native_Statement nova_math_Statement;
nova_math_native_StatementComponent nova_math_StatementComponent;
nova_math_native_VariableOperand nova_math_VariableOperand;
nova_math_calculus_native_Calculus nova_math_calculus_Calculus;
nova_math_huffman_native_HuffmanTree nova_math_huffman_HuffmanTree;
nova_math_logic_native_Conclusion nova_math_logic_Conclusion;
nova_math_logic_native_Hypothesis nova_math_logic_Hypothesis;
nova_math_logic_native_InvalidFormulaException nova_math_logic_InvalidFormulaException;
nova_math_logic_native_LogicalConnective nova_math_logic_LogicalConnective;
nova_math_logic_native_LogicalStatement nova_math_logic_LogicalStatement;
nova_math_logic_native_StatementComponent nova_math_logic_StatementComponent;
nova_math_logic_native_StatementGroup nova_math_logic_StatementGroup;
nova_math_logic_native_StatementLetter nova_math_logic_StatementLetter;
nova_math_logic_native_WFF nova_math_logic_WFF;
nova_network_native_ClientSocket nova_network_ClientSocket;
nova_network_native_ConnectionSocket nova_network_ConnectionSocket;
nova_network_native_NetworkInputStream nova_network_NetworkInputStream;
nova_network_native_NetworkOutputStream nova_network_NetworkOutputStream;
nova_network_native_ServerSocket nova_network_ServerSocket;
nova_network_native_Socket nova_network_Socket;
nova_operators_native_Equals nova_operators_Equals;
nova_operators_native_Multiply nova_operators_Multiply;
nova_primitive_native_Bool nova_primitive_Bool;
nova_primitive_native_Null nova_primitive_Null;
nova_primitive_native_Primitive nova_primitive_Primitive;
nova_primitive_number_native_Byte nova_primitive_number_Byte;
nova_primitive_number_native_Char nova_primitive_number_Char;
nova_primitive_number_native_Double nova_primitive_number_Double;
nova_primitive_number_native_Float nova_primitive_number_Float;
nova_primitive_number_native_Int nova_primitive_number_Int;
nova_primitive_number_native_Integer nova_primitive_number_Integer;
nova_primitive_number_native_Long nova_primitive_number_Long;
nova_primitive_number_native_Number nova_primitive_number_Number;
nova_primitive_number_native_RealNumber nova_primitive_number_RealNumber;
nova_primitive_number_native_Short nova_primitive_number_Short;
nova_process_native_Process nova_process_Process;
nova_security_native_MD5 nova_security_MD5;
nova_star_native_Window nova_star_Window;
nova_star_native_WindowThread nova_star_WindowThread;
nova_svg_native_Svg nova_svg_Svg;
nova_svg_native_SvgCircle nova_svg_SvgCircle;
nova_svg_native_SvgComponent nova_svg_SvgComponent;
nova_svg_native_SvgComponentList nova_svg_SvgComponentList;
nova_svg_native_SvgComponentNode nova_svg_SvgComponentNode;
nova_svg_native_SvgMainComponent nova_svg_SvgMainComponent;
nova_svg_no3_native_No3 nova_svg_no3_No3;
nova_svg_no3_native_No3Node nova_svg_no3_No3Node;
nova_svg_no3_native_No3Selection nova_svg_no3_No3Selection;
nova_thread_native_Thread nova_thread_Thread;
nova_thread_native_UncaughtExceptionHandler nova_thread_UncaughtExceptionHandler;
nova_thread_async_native_Async nova_thread_async_Async;
nova_thread_async_native_AsyncResult nova_thread_async_AsyncResult;
nova_time_native_Date nova_time_Date;
nova_time_native_Time nova_time_Time;
nova_time_native_Timer nova_time_Timer;
nova_web_js_json_native_Json nova_web_js_json_Json;
nova_web_svg_native_Svg nova_web_svg_Svg;
nova_web_svg_native_SvgCircle nova_web_svg_SvgCircle;
nova_web_svg_native_SvgComponent nova_web_svg_SvgComponent;
nova_web_svg_native_SvgComponentList nova_web_svg_SvgComponentList;
nova_web_svg_native_SvgComponentNode nova_web_svg_SvgComponentNode;
nova_web_svg_native_SvgMainComponent nova_web_svg_SvgMainComponent;
nova_web_svg_no3_native_No3 nova_web_svg_no3_No3;
nova_web_svg_no3_native_No3Node nova_web_svg_no3_No3Node;
nova_web_svg_no3_native_No3Select nova_web_svg_no3_No3Select;
nova_web_svg_no3_native_No3SelectAll nova_web_svg_no3_No3SelectAll;
example_native_Animal example_Animal;
example_native_ArrayDemo example_ArrayDemo;
example_native_BodyBuilder example_BodyBuilder;
example_native_ClosureDemo example_ClosureDemo;
example_native_Dog example_Dog;
example_native_ExceptionHandlingDemo example_ExceptionHandlingDemo;
example_native_FileTest example_FileTest;
example_native_GenericDemo example_GenericDemo;
example_native_HashMapDemo example_HashMapDemo;
example_native_IntegerTest example_IntegerTest;
example_native_Lab example_Lab;
example_native_MathDemo example_MathDemo;
example_native_NonWholeDivisionException example_NonWholeDivisionException;
example_native_Person example_Person;
example_native_Polygon example_Polygon;
example_native_PolymorphismDemo example_PolymorphismDemo;
example_native_QueueDemo example_QueueDemo;
example_native_Spider example_Spider;
example_native_Square example_Square;
example_native_SvgChart example_SvgChart;
example_native_SvgFractal example_SvgFractal;
example_native_T1 example_T1;
example_native_T2 example_T2;
example_native_Test example_Test;
example_native_ThreadDemo example_ThreadDemo;
example_native_ThreadDemoImplementation example_ThreadDemoImplementation;
example_ackermann_native_Ackermann example_ackermann_Ackermann;
example_copy_native_Dog example_copy_Dog;
example_database_native_DatabaseDemo example_database_DatabaseDemo;
example_network_native_ClientDemo example_network_ClientDemo;
example_network_native_ConnectionThread example_network_ConnectionThread;
example_network_native_OutputThread example_network_OutputThread;
example_network_native_ServerDemo example_network_ServerDemo;
stabilitytest_native_AssignmentStability stabilitytest_AssignmentStability;
stabilitytest_native_ClassWithProperties stabilitytest_ClassWithProperties;
stabilitytest_native_ClientThread stabilitytest_ClientThread;
stabilitytest_native_ClosureStability stabilitytest_ClosureStability;
stabilitytest_native_ExceptionStability stabilitytest_ExceptionStability;
stabilitytest_native_FileStability stabilitytest_FileStability;
stabilitytest_native_LambdaStability stabilitytest_LambdaStability;
stabilitytest_native_NetworkStability stabilitytest_NetworkStability;
stabilitytest_native_PolymorphicSubClass stabilitytest_PolymorphicSubClass;
stabilitytest_native_PolymorphicSuperClass stabilitytest_PolymorphicSuperClass;
stabilitytest_native_PolymorphismStability stabilitytest_PolymorphismStability;
stabilitytest_native_StabilityExceptionHandler stabilitytest_StabilityExceptionHandler;
stabilitytest_native_StabilityTest stabilitytest_StabilityTest;
stabilitytest_native_StabilityTestCase stabilitytest_StabilityTestCase;
stabilitytest_native_StabilityTestException stabilitytest_StabilityTestException;
stabilitytest_native_SyntaxStability stabilitytest_SyntaxStability;
stabilitytest_native_ThreadImplementation stabilitytest_ThreadImplementation;
stabilitytest_native_ThreadStability stabilitytest_ThreadStability;
stabilitytest_native_TimeStability stabilitytest_TimeStability;
stabilitytest_native_ToStringStability stabilitytest_ToStringStability;
stabilitytest_native_UnstableException stabilitytest_UnstableException;
} nova_env;

extern nova_env novaEnv;


#endif
