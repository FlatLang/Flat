#ifndef NOVA_NATIVE_INTERFACE
#define NOVA_NATIVE_INTERFACE

#include <nova/nova_Nova_Class.h>
#include <nova/nova_Nova_Object.h>
#include <nova/nova_Nova_String.h>
#include <nova/nova_Nova_System.h>
#include <nova/ar/nova_ar_Nova_ImageTracker.h>
#include <nova/ar/nova_ar_Nova_TrackPoint.h>
#include <nova/database/nova_database_Nova_DBConnector.h>
#include <nova/database/nova_database_Nova_ResultSet.h>
#include <nova/datastruct/nova_datastruct_Nova_BinaryNode.h>
#include <nova/datastruct/nova_datastruct_Nova_BinaryTree.h>
#include <nova/datastruct/nova_datastruct_Nova_Bounds.h>
#include <nova/datastruct/nova_datastruct_Nova_Comparable.h>
#include <nova/datastruct/nova_datastruct_Nova_HashMap.h>
#include <nova/datastruct/nova_datastruct_Nova_HashSet.h>
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
#include <nova/datastruct/list/nova_datastruct_list_Nova_StringCharArray.h>
#include <nova/exception/nova_exception_Nova_DivideByZeroException.h>
#include <nova/exception/nova_exception_Nova_Exception.h>
#include <nova/exception/nova_exception_Nova_ExceptionData.h>
#include <nova/exception/nova_exception_Nova_InvalidArgumentException.h>
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
#include <nova/math/nova_math_Nova_Vector.h>
#include <nova/math/nova_math_Nova_Vector2D.h>
#include <nova/math/nova_math_Nova_Vector3D.h>
#include <nova/math/nova_math_Nova_Vector4D.h>
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
#include <nova/star/nova_star_Nova_Frame.h>
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
#include <compiler/compiler_Nova_Compiler.h>
#include <compiler/compiler_Nova_SyntaxErrorException.h>
#include <compiler/error/compiler_error_Nova_UnimplementedOperationException.h>
#include <compiler/tree/compiler_tree_Nova_SyntaxTree.h>
#include <compiler/tree/node/compiler_tree_node_Nova_Import.h>
#include <compiler/tree/node/compiler_tree_node_Nova_ImportList.h>
#include <compiler/tree/node/compiler_tree_node_Nova_Listener.h>
#include <compiler/tree/node/compiler_tree_node_Nova_Node.h>
#include <compiler/tree/node/compiler_tree_node_Nova_NodeList.h>
#include <compiler/tree/node/compiler_tree_node_Nova_NovaClass.h>
#include <compiler/tree/node/compiler_tree_node_Nova_NovaFile.h>
#include <compiler/tree/node/compiler_tree_node_Nova_Package.h>
#include <compiler/tree/node/compiler_tree_node_Nova_Program.h>
#include <compiler/tree/node/compiler_tree_node_Nova_Scope.h>
#include <compiler/tree/node/compiler_tree_node_Nova_ValidationResult.h>
#include <compiler/tree/node/annotation/compiler_tree_node_annotation_Nova_Annotatable.h>
#include <compiler/tree/node/annotation/compiler_tree_node_annotation_Nova_Annotation.h>
#include <compiler/tree/node/closures/compiler_tree_node_closures_Nova_ClosureContext.h>
#include <compiler/tree/node/closures/compiler_tree_node_closures_Nova_ClosureDeclaration.h>
#include <compiler/tree/node/exceptionhandling/compiler_tree_node_exceptionhandling_Nova_ExceptionHandler.h>
#include <compiler/tree/node/exceptionhandling/compiler_tree_node_exceptionhandling_Nova_Try.h>
#include <compiler/tree/node/functions/compiler_tree_node_functions_Nova_NovaFunction.h>
#include <compiler/util/compiler_util_Nova_FileUtils.h>
#include <compiler/util/compiler_util_Nova_Location.h>
#include <compiler/util/compiler_util_Nova_OS.h>
#include <compiler/util/compiler_util_Nova_StringUtils.h>
#include <compiler/util/compiler_util_Nova_SyntaxUtils.h>
#include <example/example_Nova_Animal.h>
#include <example/example_Nova_ArrayDemo.h>
#include <example/example_Nova_BodyBuilder.h>
#include <example/example_Nova_ClosureDemo.h>
#include <example/example_Nova_Dog.h>
#include <example/example_Nova_ExceptionHandlingDemo.h>
#include <example/example_Nova_FileTest.h>
#include <example/example_Nova_GenericDemo.h>
#include <example/example_Nova_HashMapDemo.h>
#include <example/example_Nova_HashSetDemo.h>
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

typedef nova_Nova_Class* (*nova_Nova_Class_native_Nova_construct)(nova_Nova_Class*, nova_exception_Nova_ExceptionData*);

typedef struct nova_native_Class
{
nova_Nova_Class_native_Nova_construct Class;
} nova_native_Class;

typedef nova_Nova_String* (*nova_Nova_Object_native_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_Nova_Object_native_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_Nova_Object_native_Nova_construct)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);

typedef struct nova_native_Object
{
nova_Nova_Object_native_Nova_toString toString;
nova_Nova_Object_native_Nova_equals equals;
nova_Nova_Object_native_Nova_construct Object;
} nova_native_Object;

typedef nova_Nova_String* (*nova_Nova_String_native_Nova_concat)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef char (*nova_Nova_String_native_Nova_equals)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_replace)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*);
typedef char (*nova_Nova_String_native_Nova_startsWith)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef char (*nova_Nova_String_native_Nova_endsWith)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef char (*nova_Nova_String_native_Nova_contains)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef int (*nova_Nova_String_native_Nova_indexOf)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, int, int, int);
typedef int (*nova_Nova_String_native_Nova_lastIndexOf)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, int);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_substring)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, int, int);
typedef char (*nova_Nova_String_native_Nova_lastChar)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_Nova_String_native_Nova_charAt)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_trim)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, int, int);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_toLowerCase)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_toUpperCase)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_capitalize)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_transform)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String_closure3_Nova_transform nova_Nova_String_Nova_transform, void* nova_Nova_String_ref_Nova_transform, void* transform_context);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_getStringBetween)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, int);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_surroundWith)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, char);
typedef int (*nova_Nova_String_native_Nova_compareTo)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_Nova_String_native_Nova_toString)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_Nova_String_native0_Nova_construct)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, char);
typedef nova_Nova_String* (*nova_Nova_String_native1_Nova_construct)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, char*);
typedef nova_Nova_String* (*nova_Nova_String_native2_Nova_construct)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray*);

typedef struct nova_native_String
{
nova_Nova_String_native_Nova_concat concat;
nova_Nova_String_native_Nova_equals equals;
nova_Nova_String_native_Nova_replace replace;
nova_Nova_String_native_Nova_startsWith startsWith;
nova_Nova_String_native_Nova_endsWith endsWith;
nova_Nova_String_native_Nova_contains contains;
nova_Nova_String_native_Nova_indexOf indexOf;
nova_Nova_String_native_Nova_lastIndexOf lastIndexOf;
nova_Nova_String_native_Nova_substring substring;
nova_Nova_String_native_Nova_lastChar lastChar;
nova_Nova_String_native_Nova_charAt charAt;
nova_Nova_String_native_Nova_trim trim;
nova_Nova_String_native_Nova_toLowerCase toLowerCase;
nova_Nova_String_native_Nova_toUpperCase toUpperCase;
nova_Nova_String_native_Nova_capitalize capitalize;
nova_Nova_String_native_Nova_transform transform;
nova_Nova_String_native_Nova_getStringBetween getStringBetween;
nova_Nova_String_native_Nova_surroundWith surroundWith;
nova_Nova_String_native_Nova_compareTo compareTo;
nova_Nova_String_native_Nova_toString toString;
nova_Nova_String_native0_Nova_construct String__nova_primitive_number_Char;
nova_Nova_String_native1_Nova_construct String__Array1d_nova_primitive_number_Char;
nova_Nova_String_native2_Nova_construct String__nova_datastruct_list_CharArray;
} nova_native_String;

typedef void (*nova_Nova_System_native0_static_Nova_exit)(nova_Nova_System*, nova_exception_Nova_ExceptionData*, int);
typedef void (*nova_Nova_System_native1_static_Nova_exit)(nova_Nova_System*, nova_exception_Nova_ExceptionData*, int, nova_Nova_String*);
typedef void (*nova_Nova_System_native2_static_Nova_exit)(nova_Nova_System*, nova_exception_Nova_ExceptionData*, int, nova_Nova_String*, char);
typedef nova_process_Nova_Process* (*nova_Nova_System_native_static_Nova_execute)(nova_Nova_System*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_System* (*nova_Nova_System_native_Nova_construct)(nova_Nova_System*, nova_exception_Nova_ExceptionData*);

typedef struct nova_native_System
{
nova_Nova_System_native_Nova_construct System;
} nova_native_System;

typedef nova_ar_Nova_ImageTracker* (*nova_ar_Nova_ImageTracker_native_Nova_construct)(nova_ar_Nova_ImageTracker*, nova_exception_Nova_ExceptionData*);

typedef struct nova_ar_native_ImageTracker
{
nova_ar_Nova_ImageTracker_native_Nova_construct ImageTracker;
} nova_ar_native_ImageTracker;

typedef nova_ar_Nova_TrackPoint* (*nova_ar_Nova_TrackPoint_native_Nova_construct)(nova_ar_Nova_TrackPoint*, nova_exception_Nova_ExceptionData*);

typedef struct nova_ar_native_TrackPoint
{
nova_ar_Nova_TrackPoint_native_Nova_construct TrackPoint;
} nova_ar_native_TrackPoint;

typedef void (*nova_database_Nova_DBConnector_native0_Nova_connect)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*);
typedef void (*nova_database_Nova_DBConnector_native1_Nova_connect)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*);
typedef void (*nova_database_Nova_DBConnector_native2_Nova_connect)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*, int, nova_Nova_String*, int);
typedef void (*nova_database_Nova_DBConnector_native_Nova_updateError)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_database_Nova_DBConnector_native_Nova_changeUser)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*);
typedef nova_database_Nova_ResultSet* (*nova_database_Nova_DBConnector_native_Nova_query)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef void (*nova_database_Nova_DBConnector_native_Nova_close)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*);
typedef nova_database_Nova_DBConnector* (*nova_database_Nova_DBConnector_native_Nova_construct)(nova_database_Nova_DBConnector*, nova_exception_Nova_ExceptionData*);

typedef struct nova_database_native_DBConnector
{
nova_database_Nova_DBConnector_native0_Nova_connect connect__nova_String__nova_String__nova_String;
nova_database_Nova_DBConnector_native1_Nova_connect connect__nova_String__nova_String__nova_String__nova_String;
nova_database_Nova_DBConnector_native2_Nova_connect connect__nova_String__nova_String__nova_String__nova_String__nova_primitive_number_Int__nova_String__nova_primitive_number_Int;
nova_database_Nova_DBConnector_native_Nova_updateError updateError;
nova_database_Nova_DBConnector_native_Nova_changeUser changeUser;
nova_database_Nova_DBConnector_native_Nova_query query;
nova_database_Nova_DBConnector_native_Nova_close close;
nova_database_Nova_DBConnector_native_Nova_construct DBConnector;
} nova_database_native_DBConnector;

typedef nova_database_Nova_ResultSet* (*nova_database_Nova_ResultSet_native_Nova_construct)(nova_database_Nova_ResultSet*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*, int);

typedef struct nova_database_native_ResultSet
{
nova_database_Nova_ResultSet_native_Nova_construct ResultSet;
} nova_database_native_ResultSet;

typedef void (*nova_datastruct_Nova_BinaryNode_native_Nova_addChild)(nova_datastruct_Nova_BinaryNode*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Comparable*);
typedef nova_datastruct_Nova_BinaryNode* (*nova_datastruct_Nova_BinaryNode_native0_Nova_construct)(nova_datastruct_Nova_BinaryNode*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Comparable*);

typedef struct nova_datastruct_native_BinaryNode
{
nova_datastruct_Nova_BinaryNode_native_Nova_addChild addChild;
nova_datastruct_Nova_BinaryNode_native0_Nova_construct BinaryNode__nova_datastruct_Comparable;
} nova_datastruct_native_BinaryNode;

typedef nova_datastruct_Nova_BinaryTree* (*nova_datastruct_Nova_BinaryTree_native_Nova_addNode)(nova_datastruct_Nova_BinaryTree*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Comparable*);
typedef nova_datastruct_Nova_BinaryTree* (*nova_datastruct_Nova_BinaryTree_native_Nova_addNodes)(nova_datastruct_Nova_BinaryTree*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef nova_datastruct_Nova_BinaryTree* (*nova_datastruct_Nova_BinaryTree_native_Nova_construct)(nova_datastruct_Nova_BinaryTree*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);

typedef struct nova_datastruct_native_BinaryTree
{
nova_datastruct_Nova_BinaryTree_native_Nova_addNode addNode;
nova_datastruct_Nova_BinaryTree_native_Nova_addNodes addNodes;
nova_datastruct_Nova_BinaryTree_native_Nova_construct BinaryTree;
} nova_datastruct_native_BinaryTree;

typedef nova_Nova_String* (*nova_datastruct_Nova_Bounds_native_Nova_extractString)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_datastruct_Nova_Bounds_native_Nova_extractPreString)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_datastruct_Nova_Bounds_native_Nova_extractPostString)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_datastruct_Nova_Bounds_native_Nova_trimString)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef void (*nova_datastruct_Nova_Bounds_native_Nova_invalidate)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_datastruct_Nova_Bounds_native_Nova_equals)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Bounds*);
typedef nova_Nova_String* (*nova_datastruct_Nova_Bounds_native_Nova_toString)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_datastruct_Nova_Bounds_native_Nova_cloneTo)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Bounds*);
typedef nova_datastruct_Nova_Bounds* (*nova_datastruct_Nova_Bounds_native_Nova_clone)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Bounds* (*nova_datastruct_Nova_Bounds_native0_Nova_construct)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Bounds* (*nova_datastruct_Nova_Bounds_native1_Nova_construct)(nova_datastruct_Nova_Bounds*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct nova_datastruct_native_Bounds
{
nova_datastruct_Nova_Bounds_native_Nova_extractString extractString;
nova_datastruct_Nova_Bounds_native_Nova_extractPreString extractPreString;
nova_datastruct_Nova_Bounds_native_Nova_extractPostString extractPostString;
nova_datastruct_Nova_Bounds_native_Nova_trimString trimString;
nova_datastruct_Nova_Bounds_native_Nova_invalidate invalidate;
nova_datastruct_Nova_Bounds_native_Nova_equals equals;
nova_datastruct_Nova_Bounds_native_Nova_toString toString;
nova_datastruct_Nova_Bounds_native_Nova_cloneTo cloneTo;
nova_datastruct_Nova_Bounds_native_Nova_clone clone;
nova_datastruct_Nova_Bounds_native0_Nova_construct Bounds;
nova_datastruct_Nova_Bounds_native1_Nova_construct Bounds__nova_primitive_number_Int__nova_primitive_number_Int;
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
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashMap_native_Nova_remove)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef char (*nova_datastruct_Nova_HashMap_native_Nova_containsKey)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashMap_native_Nova_get)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashMap_native_Nova_set)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, nova_Nova_Object*);
typedef nova_datastruct_Nova_HashMap* (*nova_datastruct_Nova_HashMap_native0_Nova_construct)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_HashMap* (*nova_datastruct_Nova_HashMap_native1_Nova_construct)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, int, int);

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
nova_datastruct_Nova_HashMap_native_Nova_remove remove;
nova_datastruct_Nova_HashMap_native_Nova_containsKey containsKey;
nova_datastruct_Nova_HashMap_native_Nova_get get;
nova_datastruct_Nova_HashMap_native_Nova_set set;
nova_datastruct_Nova_HashMap_native0_Nova_construct HashMap;
nova_datastruct_Nova_HashMap_native1_Nova_construct HashMap__nova_primitive_number_Int__nova_primitive_number_Int;
} nova_datastruct_native_HashMap;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_HashSet_native_Nova_toArray)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_datastruct_Nova_HashSet_native0_Nova_any)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashSet_closure3_Nova_func nova_datastruct_Nova_HashSet_Nova_func, void* nova_datastruct_Nova_HashSet_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_Nova_HashSet_native0_Nova_all)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashSet_closure6_Nova_func nova_datastruct_Nova_HashSet_Nova_func, void* nova_datastruct_Nova_HashSet_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_HashSet_native0_Nova_map)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashSet_closure9_Nova_func nova_datastruct_Nova_HashSet_Nova_func, void* nova_datastruct_Nova_HashSet_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_HashSet_native0_Nova_filter)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashSet_closure12_Nova_func nova_datastruct_Nova_HashSet_Nova_func, void* nova_datastruct_Nova_HashSet_ref_Nova_func, void* func_context);
typedef nova_Nova_String* (*nova_datastruct_Nova_HashSet_native_Nova_join)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashSet_native_Nova_skip)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashSet_native_Nova_take)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_HashSet_native_Nova_reverse)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashSet_native0_Nova_firstWhere)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashSet_closure15_Nova_func nova_datastruct_Nova_HashSet_Nova_func, void* nova_datastruct_Nova_HashSet_ref_Nova_func, void* func_context);
typedef void (*nova_datastruct_Nova_HashSet_native0_Nova_forEach)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashSet_closure18_Nova_func nova_datastruct_Nova_HashSet_Nova_func, void* nova_datastruct_Nova_HashSet_ref_Nova_func, void* func_context);
typedef nova_datastruct_Nova_HashSet* (*nova_datastruct_Nova_HashSet_native_Nova_add)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashSet_native_Nova_get)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashSet_native_Nova_remove)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef char (*nova_datastruct_Nova_HashSet_native0_Nova_contains)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_String* (*nova_datastruct_Nova_HashSet_native_Nova_toString)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_HashSet* (*nova_datastruct_Nova_HashSet_native0_Nova_construct)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_HashSet* (*nova_datastruct_Nova_HashSet_native1_Nova_construct)(nova_datastruct_Nova_HashSet*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct nova_datastruct_native_HashSet
{
nova_datastruct_Nova_HashSet_native_Nova_toArray toArray;
nova_datastruct_Nova_HashSet_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_Nova_HashSet_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_Nova_HashSet_native0_Nova_map map__nova_Object;
nova_datastruct_Nova_HashSet_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_Nova_HashSet_native_Nova_join join;
nova_datastruct_Nova_HashSet_native_Nova_skip skip;
nova_datastruct_Nova_HashSet_native_Nova_take take;
nova_datastruct_Nova_HashSet_native_Nova_reverse reverse;
nova_datastruct_Nova_HashSet_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_Nova_HashSet_native0_Nova_forEach forEach__void;
nova_datastruct_Nova_HashSet_native_Nova_add add;
nova_datastruct_Nova_HashSet_native_Nova_get get;
nova_datastruct_Nova_HashSet_native_Nova_remove remove;
nova_datastruct_Nova_HashSet_native0_Nova_contains contains__nova_Object;
nova_datastruct_Nova_HashSet_native_Nova_toString toString;
nova_datastruct_Nova_HashSet_native0_Nova_construct HashSet;
nova_datastruct_Nova_HashSet_native1_Nova_construct HashSet__nova_primitive_number_Int__nova_primitive_number_Int;
} nova_datastruct_native_HashSet;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Node_native0_Nova_preorder)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Node_native0_Nova_inorder)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Node_native0_Nova_postorder)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Node_native0_Nova_levelorder)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_Nova_Node_native_Nova_toString)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Node* (*nova_datastruct_Nova_Node_native_Nova_construct)(nova_datastruct_Nova_Node*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, int);

typedef struct nova_datastruct_native_Node
{
nova_datastruct_Nova_Node_native0_Nova_preorder preorder;
nova_datastruct_Nova_Node_native0_Nova_inorder inorder;
nova_datastruct_Nova_Node_native0_Nova_postorder postorder;
nova_datastruct_Nova_Node_native0_Nova_levelorder levelorder;
nova_datastruct_Nova_Node_native_Nova_toString toString;
nova_datastruct_Nova_Node_native_Nova_construct Node;
} nova_datastruct_native_Node;

typedef nova_Nova_String* (*nova_datastruct_Nova_Pair_native_Nova_toString)(nova_datastruct_Nova_Pair*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Pair* (*nova_datastruct_Nova_Pair_native_Nova_construct)(nova_datastruct_Nova_Pair*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, nova_Nova_Object*);

typedef struct nova_datastruct_native_Pair
{
nova_datastruct_Nova_Pair_native_Nova_toString toString;
nova_datastruct_Nova_Pair_native_Nova_construct Pair;
} nova_datastruct_native_Pair;

typedef void (*nova_datastruct_Nova_ReversibleHashMap_native_Nova_put)(nova_datastruct_Nova_ReversibleHashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_ReversibleHashMap_native_Nova_getKey)(nova_datastruct_Nova_ReversibleHashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_ReversibleHashMap_native_Nova_getValue)(nova_datastruct_Nova_ReversibleHashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_Nova_ReversibleHashMap* (*nova_datastruct_Nova_ReversibleHashMap_native_Nova_construct)(nova_datastruct_Nova_ReversibleHashMap*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_native_ReversibleHashMap
{
nova_datastruct_Nova_ReversibleHashMap_native_Nova_put put;
nova_datastruct_Nova_ReversibleHashMap_native_Nova_getKey getKey;
nova_datastruct_Nova_ReversibleHashMap_native_Nova_getValue getValue;
nova_datastruct_Nova_ReversibleHashMap_native_Nova_construct ReversibleHashMap;
} nova_datastruct_native_ReversibleHashMap;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Tree_native_Nova_preorder)(nova_datastruct_Nova_Tree*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Tree_native_Nova_inorder)(nova_datastruct_Nova_Tree*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Tree_native_Nova_postorder)(nova_datastruct_Nova_Tree*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_Nova_Tree_native_Nova_levelorder)(nova_datastruct_Nova_Tree*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_Nova_Tree* (*nova_datastruct_Nova_Tree_native_Nova_construct)(nova_datastruct_Nova_Tree*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_native_Tree
{
nova_datastruct_Nova_Tree_native_Nova_preorder preorder;
nova_datastruct_Nova_Tree_native_Nova_inorder inorder;
nova_datastruct_Nova_Tree_native_Nova_postorder postorder;
nova_datastruct_Nova_Tree_native_Nova_levelorder levelorder;
nova_datastruct_Nova_Tree_native_Nova_construct Tree;
} nova_datastruct_native_Tree;

typedef nova_datastruct_Nova_Vector* (*nova_datastruct_Nova_Vector_native_Nova_construct)(nova_datastruct_Nova_Vector*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_native_Vector
{
nova_datastruct_Nova_Vector_native_Nova_construct Vector;
} nova_datastruct_native_Vector;

typedef nova_datastruct_Nova_Vector2D* (*nova_datastruct_Nova_Vector2D_native_Nova_construct)(nova_datastruct_Nova_Vector2D*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_native_Vector2D
{
nova_datastruct_Nova_Vector2D_native_Nova_construct Vector2D;
} nova_datastruct_native_Vector2D;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native_Nova_fillRemaining)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native_Nova_addAll)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_add)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef void (*nova_datastruct_list_Nova_Array_native1_Nova_add)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Array_native0_Nova_remove)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Array_native1_Nova_remove)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef int (*nova_datastruct_list_Nova_Array_native_Nova_indexOf)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef void (*nova_datastruct_list_Nova_Array_native_Nova_swap)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int, int);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native_Nova_toArray)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_map)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array_closure3_Nova_mapFunc nova_datastruct_list_Nova_Array_Nova_mapFunc, void* nova_datastruct_list_Nova_Array_ref_Nova_mapFunc, void* mapFunc_context);
typedef void (*nova_datastruct_list_Nova_Array_native0_Nova_forEach)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array_closure6_Nova_func nova_datastruct_list_Nova_Array_Nova_func, void* nova_datastruct_list_Nova_Array_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_filter)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array_closure9_Nova_filterFunc nova_datastruct_list_Nova_Array_Nova_filterFunc, void* nova_datastruct_list_Nova_Array_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native_Nova_take)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native_Nova_skip)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int);
typedef long_long (*nova_datastruct_list_Nova_Array_native_Nova_sumSize)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native_Nova_reverse)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_Array_native_Nova_toString)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Array_native_Nova_get)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Array_native_Nova_set)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int, nova_Nova_Object*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native0_Nova_construct)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native1_Nova_construct)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_Array_native2_Nova_construct)(nova_datastruct_list_Nova_Array*, nova_exception_Nova_ExceptionData*, nova_Nova_Object**, int);

typedef struct nova_datastruct_list_native_Array
{
nova_datastruct_list_Nova_Array_native_Nova_fillRemaining fillRemaining;
nova_datastruct_list_Nova_Array_native_Nova_addAll addAll;
nova_datastruct_list_Nova_Array_native0_Nova_add add__nova_Object;
nova_datastruct_list_Nova_Array_native1_Nova_add add__nova_primitive_number_Int__nova_Object;
nova_datastruct_list_Nova_Array_native0_Nova_remove remove__nova_primitive_number_Int;
nova_datastruct_list_Nova_Array_native1_Nova_remove remove__nova_Object;
nova_datastruct_list_Nova_Array_native_Nova_indexOf indexOf;
nova_datastruct_list_Nova_Array_native_Nova_swap swap;
nova_datastruct_list_Nova_Array_native_Nova_toArray toArray;
nova_datastruct_list_Nova_Array_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_Array_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_Array_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_Array_native_Nova_take take;
nova_datastruct_list_Nova_Array_native_Nova_skip skip;
nova_datastruct_list_Nova_Array_native_Nova_sumSize sumSize;
nova_datastruct_list_Nova_Array_native_Nova_reverse reverse;
nova_datastruct_list_Nova_Array_native_Nova_toString toString;
nova_datastruct_list_Nova_Array_native_Nova_get get;
nova_datastruct_list_Nova_Array_native_Nova_set set;
nova_datastruct_list_Nova_Array_native0_Nova_construct Array;
nova_datastruct_list_Nova_Array_native1_Nova_construct Array__nova_primitive_number_Int;
nova_datastruct_list_Nova_Array_native2_Nova_construct Array__Array1d_nova_Object__nova_primitive_number_Int;
} nova_datastruct_list_native_Array;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_ArrayIterator_native_Nova_reset)(nova_datastruct_list_Nova_ArrayIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_ArrayIterator* (*nova_datastruct_list_Nova_ArrayIterator_native_Nova_construct)(nova_datastruct_list_Nova_ArrayIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);

typedef struct nova_datastruct_list_native_ArrayIterator
{
nova_datastruct_list_Nova_ArrayIterator_native_Nova_reset reset;
nova_datastruct_list_Nova_ArrayIterator_native_Nova_construct ArrayIterator;
} nova_datastruct_list_native_ArrayIterator;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_CharArray_native_Nova_map)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure3_Nova_mapFunc nova_datastruct_list_Nova_CharArray_Nova_mapFunc, void* nova_datastruct_list_Nova_CharArray_ref_Nova_mapFunc, void* mapFunc_context);
typedef void (*nova_datastruct_list_Nova_CharArray_native_Nova_forEach)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure6_Nova_func nova_datastruct_list_Nova_CharArray_Nova_func, void* nova_datastruct_list_Nova_CharArray_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_list_Nova_CharArray_native_Nova_any)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure9_Nova_anyFunc nova_datastruct_list_Nova_CharArray_Nova_anyFunc, void* nova_datastruct_list_Nova_CharArray_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_CharArray_native_Nova_all)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure12_Nova_allFunc nova_datastruct_list_Nova_CharArray_Nova_allFunc, void* nova_datastruct_list_Nova_CharArray_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native_Nova_filter)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure15_Nova_filterFunc nova_datastruct_list_Nova_CharArray_Nova_filterFunc, void* nova_datastruct_list_Nova_CharArray_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native_Nova_take)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native_Nova_skip)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, int);
typedef char (*nova_datastruct_list_Nova_CharArray_native_Nova_firstWhere)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray_closure18_Nova_func nova_datastruct_list_Nova_CharArray_Nova_func, void* nova_datastruct_list_Nova_CharArray_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native_Nova_reverse)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_datastruct_list_Nova_CharArray_native_Nova_get)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, int);
typedef char (*nova_datastruct_list_Nova_CharArray_native_Nova_set)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, int, char);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native0_Nova_construct)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native1_Nova_construct)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_CharArray* (*nova_datastruct_list_Nova_CharArray_native2_Nova_construct)(nova_datastruct_list_Nova_CharArray*, nova_exception_Nova_ExceptionData*, char*, int);

typedef struct nova_datastruct_list_native_CharArray
{
nova_datastruct_list_Nova_CharArray_native_Nova_map map;
nova_datastruct_list_Nova_CharArray_native_Nova_forEach forEach;
nova_datastruct_list_Nova_CharArray_native_Nova_any any;
nova_datastruct_list_Nova_CharArray_native_Nova_all all;
nova_datastruct_list_Nova_CharArray_native_Nova_filter filter;
nova_datastruct_list_Nova_CharArray_native_Nova_take take;
nova_datastruct_list_Nova_CharArray_native_Nova_skip skip;
nova_datastruct_list_Nova_CharArray_native_Nova_firstWhere firstWhere;
nova_datastruct_list_Nova_CharArray_native_Nova_reverse reverse;
nova_datastruct_list_Nova_CharArray_native_Nova_get get;
nova_datastruct_list_Nova_CharArray_native_Nova_set set;
nova_datastruct_list_Nova_CharArray_native0_Nova_construct CharArray;
nova_datastruct_list_Nova_CharArray_native1_Nova_construct CharArray__nova_primitive_number_Int;
nova_datastruct_list_Nova_CharArray_native2_Nova_construct CharArray__Array1d_nova_primitive_number_Char__nova_primitive_number_Int;
} nova_datastruct_list_native_CharArray;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_CharArrayIterator_native_Nova_reset)(nova_datastruct_list_Nova_CharArrayIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_CharArrayIterator* (*nova_datastruct_list_Nova_CharArrayIterator_native_Nova_construct)(nova_datastruct_list_Nova_CharArrayIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray*);

typedef struct nova_datastruct_list_native_CharArrayIterator
{
nova_datastruct_list_Nova_CharArrayIterator_native_Nova_reset reset;
nova_datastruct_list_Nova_CharArrayIterator_native_Nova_construct CharArrayIterator;
} nova_datastruct_list_native_CharArrayIterator;

typedef nova_datastruct_list_Nova_CompiledList* (*nova_datastruct_list_Nova_CompiledList_native_Nova_construct)(nova_datastruct_list_Nova_CompiledList*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_list_native_CompiledList
{
nova_datastruct_list_Nova_CompiledList_native_Nova_construct CompiledList;
} nova_datastruct_list_native_CompiledList;

typedef double (*nova_datastruct_list_Nova_DoubleArray_native_Nova_sum)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure3_Nova_func nova_datastruct_list_Nova_DoubleArray_Nova_func, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_DoubleArray_native_Nova_map)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure6_Nova_mapFunc nova_datastruct_list_Nova_DoubleArray_Nova_mapFunc, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_mapFunc, void* mapFunc_context);
typedef void (*nova_datastruct_list_Nova_DoubleArray_native_Nova_forEach)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure9_Nova_func nova_datastruct_list_Nova_DoubleArray_Nova_func, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_list_Nova_DoubleArray_native_Nova_any)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure12_Nova_anyFunc nova_datastruct_list_Nova_DoubleArray_Nova_anyFunc, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_DoubleArray_native_Nova_all)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure15_Nova_allFunc nova_datastruct_list_Nova_DoubleArray_Nova_allFunc, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native_Nova_filter)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure18_Nova_filterFunc nova_datastruct_list_Nova_DoubleArray_Nova_filterFunc, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native_Nova_take)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native_Nova_skip)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, int);
typedef double (*nova_datastruct_list_Nova_DoubleArray_native_Nova_firstWhere)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray_closure21_Nova_func nova_datastruct_list_Nova_DoubleArray_Nova_func, void* nova_datastruct_list_Nova_DoubleArray_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native_Nova_reverse)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_DoubleArray_native_Nova_join)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef double (*nova_datastruct_list_Nova_DoubleArray_native_Nova_get)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, int);
typedef double (*nova_datastruct_list_Nova_DoubleArray_native_Nova_set)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, int, double);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native0_Nova_construct)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native1_Nova_construct)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_DoubleArray* (*nova_datastruct_list_Nova_DoubleArray_native2_Nova_construct)(nova_datastruct_list_Nova_DoubleArray*, nova_exception_Nova_ExceptionData*, double*, int);

typedef struct nova_datastruct_list_native_DoubleArray
{
nova_datastruct_list_Nova_DoubleArray_native_Nova_sum sum;
nova_datastruct_list_Nova_DoubleArray_native_Nova_map map;
nova_datastruct_list_Nova_DoubleArray_native_Nova_forEach forEach;
nova_datastruct_list_Nova_DoubleArray_native_Nova_any any;
nova_datastruct_list_Nova_DoubleArray_native_Nova_all all;
nova_datastruct_list_Nova_DoubleArray_native_Nova_filter filter;
nova_datastruct_list_Nova_DoubleArray_native_Nova_take take;
nova_datastruct_list_Nova_DoubleArray_native_Nova_skip skip;
nova_datastruct_list_Nova_DoubleArray_native_Nova_firstWhere firstWhere;
nova_datastruct_list_Nova_DoubleArray_native_Nova_reverse reverse;
nova_datastruct_list_Nova_DoubleArray_native_Nova_join join;
nova_datastruct_list_Nova_DoubleArray_native_Nova_get get;
nova_datastruct_list_Nova_DoubleArray_native_Nova_set set;
nova_datastruct_list_Nova_DoubleArray_native0_Nova_construct DoubleArray;
nova_datastruct_list_Nova_DoubleArray_native1_Nova_construct DoubleArray__nova_primitive_number_Int;
nova_datastruct_list_Nova_DoubleArray_native2_Nova_construct DoubleArray__Array1d_nova_primitive_number_Double__nova_primitive_number_Int;
} nova_datastruct_list_native_DoubleArray;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_DoubleArrayIterator_native_Nova_reset)(nova_datastruct_list_Nova_DoubleArrayIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_DoubleArrayIterator* (*nova_datastruct_list_Nova_DoubleArrayIterator_native_Nova_construct)(nova_datastruct_list_Nova_DoubleArrayIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray*);

typedef struct nova_datastruct_list_native_DoubleArrayIterator
{
nova_datastruct_list_Nova_DoubleArrayIterator_native_Nova_reset reset;
nova_datastruct_list_Nova_DoubleArrayIterator_native_Nova_construct DoubleArrayIterator;
} nova_datastruct_list_native_DoubleArrayIterator;

typedef nova_datastruct_list_Nova_EmptyStackException* (*nova_datastruct_list_Nova_EmptyStackException_native0_Nova_construct)(nova_datastruct_list_Nova_EmptyStackException*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_EmptyStackException* (*nova_datastruct_list_Nova_EmptyStackException_native1_Nova_construct)(nova_datastruct_list_Nova_EmptyStackException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_datastruct_list_native_EmptyStackException
{
nova_datastruct_list_Nova_EmptyStackException_native0_Nova_construct EmptyStackException;
nova_datastruct_list_Nova_EmptyStackException_native1_Nova_construct EmptyStackException__nova_String;
} nova_datastruct_list_native_EmptyStackException;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_IntArray_native_Nova_map)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure3_Nova_mapFunc nova_datastruct_list_Nova_IntArray_Nova_mapFunc, void* nova_datastruct_list_Nova_IntArray_ref_Nova_mapFunc, void* mapFunc_context);
typedef void (*nova_datastruct_list_Nova_IntArray_native_Nova_forEach)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure6_Nova_func nova_datastruct_list_Nova_IntArray_Nova_func, void* nova_datastruct_list_Nova_IntArray_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_list_Nova_IntArray_native_Nova_any)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure9_Nova_anyFunc nova_datastruct_list_Nova_IntArray_Nova_anyFunc, void* nova_datastruct_list_Nova_IntArray_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_IntArray_native_Nova_all)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure12_Nova_allFunc nova_datastruct_list_Nova_IntArray_Nova_allFunc, void* nova_datastruct_list_Nova_IntArray_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native_Nova_filter)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure15_Nova_filterFunc nova_datastruct_list_Nova_IntArray_Nova_filterFunc, void* nova_datastruct_list_Nova_IntArray_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native_Nova_take)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native_Nova_skip)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, int);
typedef int (*nova_datastruct_list_Nova_IntArray_native_Nova_firstWhere)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray_closure18_Nova_func nova_datastruct_list_Nova_IntArray_Nova_func, void* nova_datastruct_list_Nova_IntArray_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native_Nova_reverse)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_IntArray_native_Nova_join)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native0_Nova_construct)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native1_Nova_construct)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntArray_native2_Nova_construct)(nova_datastruct_list_Nova_IntArray*, nova_exception_Nova_ExceptionData*, int*, int);

typedef struct nova_datastruct_list_native_IntArray
{
nova_datastruct_list_Nova_IntArray_native_Nova_map map;
nova_datastruct_list_Nova_IntArray_native_Nova_forEach forEach;
nova_datastruct_list_Nova_IntArray_native_Nova_any any;
nova_datastruct_list_Nova_IntArray_native_Nova_all all;
nova_datastruct_list_Nova_IntArray_native_Nova_filter filter;
nova_datastruct_list_Nova_IntArray_native_Nova_take take;
nova_datastruct_list_Nova_IntArray_native_Nova_skip skip;
nova_datastruct_list_Nova_IntArray_native_Nova_firstWhere firstWhere;
nova_datastruct_list_Nova_IntArray_native_Nova_reverse reverse;
nova_datastruct_list_Nova_IntArray_native_Nova_join join;
nova_datastruct_list_Nova_IntArray_native0_Nova_construct IntArray;
nova_datastruct_list_Nova_IntArray_native1_Nova_construct IntArray__nova_primitive_number_Int;
nova_datastruct_list_Nova_IntArray_native2_Nova_construct IntArray__Array1d_nova_primitive_number_Int__nova_primitive_number_Int;
} nova_datastruct_list_native_IntArray;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_IntArrayIterator_native_Nova_reset)(nova_datastruct_list_Nova_IntArrayIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_IntArrayIterator* (*nova_datastruct_list_Nova_IntArrayIterator_native_Nova_construct)(nova_datastruct_list_Nova_IntArrayIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntArray*);

typedef struct nova_datastruct_list_native_IntArrayIterator
{
nova_datastruct_list_Nova_IntArrayIterator_native_Nova_reset reset;
nova_datastruct_list_Nova_IntArrayIterator_native_Nova_construct IntArrayIterator;
} nova_datastruct_list_native_IntArrayIterator;

typedef char (*nova_datastruct_list_Nova_IntRange_native0_Nova_contains)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntRange_native_Nova_toArray)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_datastruct_list_Nova_IntRange_native0_Nova_forEach)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure3_Nova_func nova_datastruct_list_Nova_IntRange_Nova_func, void* nova_datastruct_list_Nova_IntRange_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_IntRange_native0_Nova_map)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure6_Nova_mapFunc nova_datastruct_list_Nova_IntRange_Nova_mapFunc, void* nova_datastruct_list_Nova_IntRange_ref_Nova_mapFunc, void* mapFunc_context);
typedef char (*nova_datastruct_list_Nova_IntRange_native0_Nova_any)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure9_Nova_anyFunc nova_datastruct_list_Nova_IntRange_Nova_anyFunc, void* nova_datastruct_list_Nova_IntRange_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_IntRange_native0_Nova_all)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure12_Nova_allFunc nova_datastruct_list_Nova_IntRange_Nova_allFunc, void* nova_datastruct_list_Nova_IntRange_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_IntArray* (*nova_datastruct_list_Nova_IntRange_native0_Nova_filter)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure15_Nova_filterFunc nova_datastruct_list_Nova_IntRange_Nova_filterFunc, void* nova_datastruct_list_Nova_IntRange_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_IntRange* (*nova_datastruct_list_Nova_IntRange_native_Nova_take)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_IntRange* (*nova_datastruct_list_Nova_IntRange_native_Nova_skip)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, int);
typedef int (*nova_datastruct_list_Nova_IntRange_native0_Nova_firstWhere)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange_closure18_Nova_func nova_datastruct_list_Nova_IntRange_Nova_func, void* nova_datastruct_list_Nova_IntRange_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_IntRange* (*nova_datastruct_list_Nova_IntRange_native_Nova_reverse)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_IntRange_native_Nova_join)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_IntRange_native_Nova_toString)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_IntRange* (*nova_datastruct_list_Nova_IntRange_native0_Nova_construct)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_IntRange* (*nova_datastruct_list_Nova_IntRange_native1_Nova_construct)(nova_datastruct_list_Nova_IntRange*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct nova_datastruct_list_native_IntRange
{
nova_datastruct_list_Nova_IntRange_native0_Nova_contains contains__nova_primitive_number_Int;
nova_datastruct_list_Nova_IntRange_native_Nova_toArray toArray;
nova_datastruct_list_Nova_IntRange_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_IntRange_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_IntRange_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_list_Nova_IntRange_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_list_Nova_IntRange_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_IntRange_native_Nova_take take;
nova_datastruct_list_Nova_IntRange_native_Nova_skip skip;
nova_datastruct_list_Nova_IntRange_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_list_Nova_IntRange_native_Nova_reverse reverse;
nova_datastruct_list_Nova_IntRange_native_Nova_join join;
nova_datastruct_list_Nova_IntRange_native_Nova_toString toString;
nova_datastruct_list_Nova_IntRange_native0_Nova_construct IntRange;
nova_datastruct_list_Nova_IntRange_native1_Nova_construct IntRange__nova_primitive_number_Int__nova_primitive_number_Int;
} nova_datastruct_list_native_IntRange;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_IntRangeIterator_native_Nova_reset)(nova_datastruct_list_Nova_IntRangeIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_IntRangeIterator* (*nova_datastruct_list_Nova_IntRangeIterator_native_Nova_construct)(nova_datastruct_list_Nova_IntRangeIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_IntRange*);

typedef struct nova_datastruct_list_native_IntRangeIterator
{
nova_datastruct_list_Nova_IntRangeIterator_native_Nova_reset reset;
nova_datastruct_list_Nova_IntRangeIterator_native_Nova_construct IntRangeIterator;
} nova_datastruct_list_native_IntRangeIterator;


typedef struct nova_datastruct_list_native_Iterable
{
} nova_datastruct_list_native_Iterable;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_Iterator_native_Nova_reset)(nova_datastruct_list_Nova_Iterator*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_list_native_Iterator
{
nova_datastruct_list_Nova_Iterator_native_Nova_reset reset;
} nova_datastruct_list_native_Iterator;

typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_addAll)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_add)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_remove)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef char (*nova_datastruct_list_Nova_LinkedList_native0_Nova_contains)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_LinkedList_native_Nova_toArray)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_map)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure3_Nova_mapFunc nova_datastruct_list_Nova_LinkedList_Nova_mapFunc, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_mapFunc, void* mapFunc_context);
typedef void (*nova_datastruct_list_Nova_LinkedList_native0_Nova_forEach)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure6_Nova_func nova_datastruct_list_Nova_LinkedList_Nova_func, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_func, void* func_context);
typedef char (*nova_datastruct_list_Nova_LinkedList_native0_Nova_any)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure9_Nova_anyFunc nova_datastruct_list_Nova_LinkedList_Nova_anyFunc, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_LinkedList_native0_Nova_all)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure12_Nova_allFunc nova_datastruct_list_Nova_LinkedList_Nova_allFunc, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_filter)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure15_Nova_filterFunc nova_datastruct_list_Nova_LinkedList_Nova_filterFunc, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_take)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_skip)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_LinkedList_native0_Nova_firstWhere)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList_closure18_Nova_func nova_datastruct_list_Nova_LinkedList_Nova_func, void* nova_datastruct_list_Nova_LinkedList_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_reverse)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_LinkedList* (*nova_datastruct_list_Nova_LinkedList_native_Nova_construct)(nova_datastruct_list_Nova_LinkedList*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_list_native_LinkedList
{
nova_datastruct_list_Nova_LinkedList_native_Nova_addAll addAll;
nova_datastruct_list_Nova_LinkedList_native_Nova_add add;
nova_datastruct_list_Nova_LinkedList_native_Nova_remove remove;
nova_datastruct_list_Nova_LinkedList_native0_Nova_contains contains__nova_Object;
nova_datastruct_list_Nova_LinkedList_native_Nova_toArray toArray;
nova_datastruct_list_Nova_LinkedList_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_LinkedList_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_LinkedList_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_list_Nova_LinkedList_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_list_Nova_LinkedList_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_LinkedList_native_Nova_take take;
nova_datastruct_list_Nova_LinkedList_native_Nova_skip skip;
nova_datastruct_list_Nova_LinkedList_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_list_Nova_LinkedList_native_Nova_reverse reverse;
nova_datastruct_list_Nova_LinkedList_native_Nova_construct LinkedList;
} nova_datastruct_list_native_LinkedList;

typedef nova_datastruct_list_Nova_Iterator* (*nova_datastruct_list_Nova_LinkedListIterator_native_Nova_reset)(nova_datastruct_list_Nova_LinkedListIterator*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_LinkedListIterator* (*nova_datastruct_list_Nova_LinkedListIterator_native_Nova_construct)(nova_datastruct_list_Nova_LinkedListIterator*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_LinkedList*);

typedef struct nova_datastruct_list_native_LinkedListIterator
{
nova_datastruct_list_Nova_LinkedListIterator_native_Nova_reset reset;
nova_datastruct_list_Nova_LinkedListIterator_native_Nova_construct LinkedListIterator;
} nova_datastruct_list_native_LinkedListIterator;

typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_native_Nova_toArray)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_datastruct_list_Nova_List_native0_Nova_contains)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef void (*nova_datastruct_list_Nova_List_native0_Nova_forEach)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure3_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_List* (*nova_datastruct_list_Nova_List_native0_Nova_map)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure6_Nova_mapFunc nova_datastruct_list_Nova_List_Nova_mapFunc, void* nova_datastruct_list_Nova_List_ref_Nova_mapFunc, void* mapFunc_context);
typedef char (*nova_datastruct_list_Nova_List_native0_Nova_any)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure9_Nova_anyFunc nova_datastruct_list_Nova_List_Nova_anyFunc, void* nova_datastruct_list_Nova_List_ref_Nova_anyFunc, void* anyFunc_context);
typedef char (*nova_datastruct_list_Nova_List_native0_Nova_all)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure12_Nova_allFunc nova_datastruct_list_Nova_List_Nova_allFunc, void* nova_datastruct_list_Nova_List_ref_Nova_allFunc, void* allFunc_context);
typedef nova_datastruct_list_Nova_List* (*nova_datastruct_list_Nova_List_native0_Nova_filter)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure15_Nova_filterFunc nova_datastruct_list_Nova_List_Nova_filterFunc, void* nova_datastruct_list_Nova_List_ref_Nova_filterFunc, void* filterFunc_context);
typedef nova_datastruct_list_Nova_List* (*nova_datastruct_list_Nova_List_native_Nova_take)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, int);
typedef nova_datastruct_list_Nova_List* (*nova_datastruct_list_Nova_List_native_Nova_skip)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_List_native0_Nova_firstWhere)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure18_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_List_native_Nova_firstNonNull)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List_closure21_Nova_func nova_datastruct_list_Nova_List_Nova_func, void* nova_datastruct_list_Nova_List_ref_Nova_func, void* func_context);
typedef nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_native_Nova_zip)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_List*, nova_datastruct_list_Nova_List_closure24_Nova_zipper nova_datastruct_list_Nova_List_Nova_zipper, void* nova_datastruct_list_Nova_List_ref_Nova_zipper, void* zipper_context);
typedef nova_datastruct_list_Nova_List* (*nova_datastruct_list_Nova_List_native_Nova_reverse)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_List_native_Nova_join)(nova_datastruct_list_Nova_List*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_datastruct_list_native_List
{
nova_datastruct_list_Nova_List_native_Nova_toArray toArray;
nova_datastruct_list_Nova_List_native0_Nova_contains contains__nova_Object;
nova_datastruct_list_Nova_List_native0_Nova_forEach forEach__void;
nova_datastruct_list_Nova_List_native0_Nova_map map__nova_Object;
nova_datastruct_list_Nova_List_native0_Nova_any any__nova_primitive_Bool;
nova_datastruct_list_Nova_List_native0_Nova_all all__nova_primitive_Bool;
nova_datastruct_list_Nova_List_native0_Nova_filter filter__nova_primitive_Bool;
nova_datastruct_list_Nova_List_native_Nova_take take;
nova_datastruct_list_Nova_List_native_Nova_skip skip;
nova_datastruct_list_Nova_List_native0_Nova_firstWhere firstWhere__nova_primitive_Bool;
nova_datastruct_list_Nova_List_native_Nova_firstNonNull firstNonNull;
nova_datastruct_list_Nova_List_native_Nova_zip zip;
nova_datastruct_list_Nova_List_native_Nova_reverse reverse;
nova_datastruct_list_Nova_List_native_Nova_join join;
} nova_datastruct_list_native_List;

typedef nova_datastruct_list_Nova_ListNode* (*nova_datastruct_list_Nova_ListNode_native_Nova_clone)(nova_datastruct_list_Nova_ListNode*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_ListNode* (*nova_datastruct_list_Nova_ListNode_native_Nova_construct)(nova_datastruct_list_Nova_ListNode*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_datastruct_list_native_ListNode
{
nova_datastruct_list_Nova_ListNode_native_Nova_clone clone;
nova_datastruct_list_Nova_ListNode_native_Nova_construct ListNode;
} nova_datastruct_list_native_ListNode;

typedef nova_datastruct_list_Nova_NoSuchElementException* (*nova_datastruct_list_Nova_NoSuchElementException_native0_Nova_construct)(nova_datastruct_list_Nova_NoSuchElementException*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_NoSuchElementException* (*nova_datastruct_list_Nova_NoSuchElementException_native1_Nova_construct)(nova_datastruct_list_Nova_NoSuchElementException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_datastruct_list_native_NoSuchElementException
{
nova_datastruct_list_Nova_NoSuchElementException_native0_Nova_construct NoSuchElementException;
nova_datastruct_list_Nova_NoSuchElementException_native1_Nova_construct NoSuchElementException__nova_String;
} nova_datastruct_list_native_NoSuchElementException;

typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Queue_native_Nova_dequeue)(nova_datastruct_list_Nova_Queue*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_datastruct_list_Nova_Queue_native_Nova_enqueue)(nova_datastruct_list_Nova_Queue*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_String* (*nova_datastruct_list_Nova_Queue_native_Nova_toString)(nova_datastruct_list_Nova_Queue*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Queue* (*nova_datastruct_list_Nova_Queue_native0_Nova_construct)(nova_datastruct_list_Nova_Queue*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Queue* (*nova_datastruct_list_Nova_Queue_native1_Nova_construct)(nova_datastruct_list_Nova_Queue*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);

typedef struct nova_datastruct_list_native_Queue
{
nova_datastruct_list_Nova_Queue_native_Nova_dequeue dequeue;
nova_datastruct_list_Nova_Queue_native_Nova_enqueue enqueue;
nova_datastruct_list_Nova_Queue_native_Nova_toString toString;
nova_datastruct_list_Nova_Queue_native0_Nova_construct Queue;
nova_datastruct_list_Nova_Queue_native1_Nova_construct Queue__nova_datastruct_list_Array;
} nova_datastruct_list_native_Queue;

typedef void (*nova_datastruct_list_Nova_Stack_native_Nova_push)(nova_datastruct_list_Nova_Stack*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_Nova_Object* (*nova_datastruct_list_Nova_Stack_native_Nova_pop)(nova_datastruct_list_Nova_Stack*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Stack* (*nova_datastruct_list_Nova_Stack_native_Nova_construct)(nova_datastruct_list_Nova_Stack*, nova_exception_Nova_ExceptionData*);

typedef struct nova_datastruct_list_native_Stack
{
nova_datastruct_list_Nova_Stack_native_Nova_push push;
nova_datastruct_list_Nova_Stack_native_Nova_pop pop;
nova_datastruct_list_Nova_Stack_native_Nova_construct Stack;
} nova_datastruct_list_native_Stack;

typedef nova_datastruct_list_Nova_StringCharArray* (*nova_datastruct_list_Nova_StringCharArray_native0_Nova_construct)(nova_datastruct_list_Nova_StringCharArray*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_CharArray*);
typedef nova_datastruct_list_Nova_StringCharArray* (*nova_datastruct_list_Nova_StringCharArray_native1_Nova_construct)(nova_datastruct_list_Nova_StringCharArray*, nova_exception_Nova_ExceptionData*, char*, int);

typedef struct nova_datastruct_list_native_StringCharArray
{
nova_datastruct_list_Nova_StringCharArray_native0_Nova_construct StringCharArray__nova_datastruct_list_CharArray;
nova_datastruct_list_Nova_StringCharArray_native1_Nova_construct StringCharArray__Array1d_nova_primitive_number_Char__nova_primitive_number_Int;
} nova_datastruct_list_native_StringCharArray;

typedef nova_exception_Nova_DivideByZeroException* (*nova_exception_Nova_DivideByZeroException_native_Nova_construct)(nova_exception_Nova_DivideByZeroException*, nova_exception_Nova_ExceptionData*);

typedef struct nova_exception_native_DivideByZeroException
{
nova_exception_Nova_DivideByZeroException_native_Nova_construct DivideByZeroException;
} nova_exception_native_DivideByZeroException;

typedef nova_exception_Nova_Exception* (*nova_exception_Nova_Exception_native0_Nova_construct)(nova_exception_Nova_Exception*, nova_exception_Nova_ExceptionData*);
typedef nova_exception_Nova_Exception* (*nova_exception_Nova_Exception_native1_Nova_construct)(nova_exception_Nova_Exception*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_exception_native_Exception
{
nova_exception_Nova_Exception_native0_Nova_construct Exception;
nova_exception_Nova_Exception_native1_Nova_construct Exception__nova_String;
} nova_exception_native_Exception;

typedef void (*nova_exception_Nova_ExceptionData_native_Nova_addCode)(nova_exception_Nova_ExceptionData*, nova_exception_Nova_ExceptionData*, int);
typedef void (*nova_exception_Nova_ExceptionData_native_Nova_jumpToBuffer)(nova_exception_Nova_ExceptionData*, nova_exception_Nova_ExceptionData*, int);
typedef nova_exception_Nova_ExceptionData* (*nova_exception_Nova_ExceptionData_native_Nova_construct)(nova_exception_Nova_ExceptionData*, nova_exception_Nova_ExceptionData*, buffer*);

typedef struct nova_exception_native_ExceptionData
{
nova_exception_Nova_ExceptionData_native_Nova_addCode addCode;
nova_exception_Nova_ExceptionData_native_Nova_jumpToBuffer jumpToBuffer;
nova_exception_Nova_ExceptionData_native_Nova_construct ExceptionData;
} nova_exception_native_ExceptionData;

typedef nova_exception_Nova_InvalidArgumentException* (*nova_exception_Nova_InvalidArgumentException_native_Nova_construct)(nova_exception_Nova_InvalidArgumentException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_exception_native_InvalidArgumentException
{
nova_exception_Nova_InvalidArgumentException_native_Nova_construct InvalidArgumentException;
} nova_exception_native_InvalidArgumentException;

typedef nova_exception_Nova_UnimplementedOperationException* (*nova_exception_Nova_UnimplementedOperationException_native_Nova_construct)(nova_exception_Nova_UnimplementedOperationException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_exception_native_UnimplementedOperationException
{
nova_exception_Nova_UnimplementedOperationException_native_Nova_construct UnimplementedOperationException;
} nova_exception_native_UnimplementedOperationException;

typedef void (*nova_gc_Nova_GC_native_static_Nova_init)(nova_gc_Nova_GC*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_gc_Nova_GC_native_static_Nova_collect)(nova_gc_Nova_GC*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_gc_Nova_GC_native_static_Nova_enableIncremental)(nova_gc_Nova_GC*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_gc_Nova_GC_native_static_Nova_dump)(nova_gc_Nova_GC*, nova_exception_Nova_ExceptionData*);
typedef nova_gc_Nova_GC* (*nova_gc_Nova_GC_native_Nova_construct)(nova_gc_Nova_GC*, nova_exception_Nova_ExceptionData*);

typedef struct nova_gc_native_GC
{
nova_gc_Nova_GC_native_Nova_construct GC;
} nova_gc_native_GC;

typedef void (*nova_io_Nova_Console_native0_static_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_Console_native1_static_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef void (*nova_io_Nova_Console_native2_static_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef void (*nova_io_Nova_Console_native3_static_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, double);
typedef void (*nova_io_Nova_Console_native4_static_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, float);
typedef void (*nova_io_Nova_Console_native5_static_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, long_long);
typedef void (*nova_io_Nova_Console_native6_static_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, int);
typedef void (*nova_io_Nova_Console_native7_static_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, short);
typedef void (*nova_io_Nova_Console_native8_static_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, char);
typedef void (*nova_io_Nova_Console_native9_static_Nova_writeLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, char);
typedef void (*nova_io_Nova_Console_native0_static_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef void (*nova_io_Nova_Console_native1_static_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef void (*nova_io_Nova_Console_native2_static_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, double);
typedef void (*nova_io_Nova_Console_native3_static_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, float);
typedef void (*nova_io_Nova_Console_native4_static_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, long_long);
typedef void (*nova_io_Nova_Console_native5_static_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, int);
typedef void (*nova_io_Nova_Console_native6_static_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, short);
typedef void (*nova_io_Nova_Console_native7_static_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, char);
typedef void (*nova_io_Nova_Console_native8_static_Nova_write)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, char);
typedef int (*nova_io_Nova_Console_native_static_Nova_readInt)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef double (*nova_io_Nova_Console_native_static_Nova_readDouble)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_io_Nova_Console_native_static_Nova_readChar)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_io_Nova_Console_native_static_Nova_readLine)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_io_Nova_Console_native_static_Nova_readPassword)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_Console_native_static_Nova_setEcho)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*, char);
typedef void (*nova_io_Nova_Console_native_static_Nova_clearScreen)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_io_Nova_Console_native_static_Nova_waitForEnter)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);
typedef nova_io_Nova_Console* (*nova_io_Nova_Console_native_Nova_construct)(nova_io_Nova_Console*, nova_exception_Nova_ExceptionData*);

typedef struct nova_io_native_Console
{
nova_io_Nova_Console_native_Nova_construct Console;
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
typedef nova_io_Nova_File* (*nova_io_Nova_File_native0_Nova_construct)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_io_Nova_File* (*nova_io_Nova_File_native1_Nova_construct)(nova_io_Nova_File*, nova_exception_Nova_ExceptionData*, FILE*);

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
nova_io_Nova_File_native0_Nova_construct File__nova_String;
nova_io_Nova_File_native1_Nova_construct File__FILE;
} nova_io_native_File;

typedef nova_io_Nova_FileNotFoundException* (*nova_io_Nova_FileNotFoundException_native_Nova_construct)(nova_io_Nova_FileNotFoundException*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);

typedef struct nova_io_native_FileNotFoundException
{
nova_io_Nova_FileNotFoundException_native_Nova_construct FileNotFoundException;
} nova_io_native_FileNotFoundException;

typedef nova_Nova_String* (*nova_io_Nova_InputStream_native_Nova_readString)(nova_io_Nova_InputStream*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_io_Nova_InputStream_native_Nova_readBytes)(nova_io_Nova_InputStream*, nova_exception_Nova_ExceptionData*);

typedef struct nova_io_native_InputStream
{
nova_io_Nova_InputStream_native_Nova_readString readString;
nova_io_Nova_InputStream_native_Nova_readBytes readBytes;
} nova_io_native_InputStream;

typedef char (*nova_io_Nova_OutputStream_native0_Nova_write)(nova_io_Nova_OutputStream*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef char (*nova_io_Nova_OutputStream_native1_Nova_write)(nova_io_Nova_OutputStream*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_io_Nova_OutputStream* (*nova_io_Nova_OutputStream_native_Nova_construct)(nova_io_Nova_OutputStream*, nova_exception_Nova_ExceptionData*);

typedef struct nova_io_native_OutputStream
{
nova_io_Nova_OutputStream_native0_Nova_write write__nova_String;
nova_io_Nova_OutputStream_native1_Nova_write write__nova_Object;
nova_io_Nova_OutputStream_native_Nova_construct OutputStream;
} nova_io_native_OutputStream;

typedef nova_datastruct_list_Nova_Array* (*nova_io_Nova_StreamReader_native_Nova_readBytes)(nova_io_Nova_StreamReader*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_io_Nova_StreamReader_native_Nova_readString)(nova_io_Nova_StreamReader*, nova_exception_Nova_ExceptionData*);
typedef nova_io_Nova_StreamReader* (*nova_io_Nova_StreamReader_native_Nova_construct)(nova_io_Nova_StreamReader*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);

typedef struct nova_io_native_StreamReader
{
nova_io_Nova_StreamReader_native_Nova_readBytes readBytes;
nova_io_Nova_StreamReader_native_Nova_readString readString;
nova_io_Nova_StreamReader_native_Nova_construct StreamReader;
} nova_io_native_StreamReader;

typedef nova_math_Nova_ArithmeticSequence* (*nova_math_Nova_ArithmeticSequence_native_Nova_construct)(nova_math_Nova_ArithmeticSequence*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_ArithmeticSequence
{
nova_math_Nova_ArithmeticSequence_native_Nova_construct ArithmeticSequence;
} nova_math_native_ArithmeticSequence;

typedef nova_math_Nova_Diekstra* (*nova_math_Nova_Diekstra_native_Nova_construct)(nova_math_Nova_Diekstra*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_Diekstra
{
nova_math_Nova_Diekstra_native_Nova_construct Diekstra;
} nova_math_native_Diekstra;

typedef nova_math_Nova_GeometricSequence* (*nova_math_Nova_GeometricSequence_native_Nova_construct)(nova_math_Nova_GeometricSequence*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_GeometricSequence
{
nova_math_Nova_GeometricSequence_native_Nova_construct GeometricSequence;
} nova_math_native_GeometricSequence;

typedef nova_math_Nova_Graph* (*nova_math_Nova_Graph_native_Nova_construct)(nova_math_Nova_Graph*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_Graph
{
nova_math_Nova_Graph_native_Nova_construct Graph;
} nova_math_native_Graph;

typedef nova_math_Nova_InvalidNumericStatementException* (*nova_math_Nova_InvalidNumericStatementException_native_Nova_construct)(nova_math_Nova_InvalidNumericStatementException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_native_InvalidNumericStatementException
{
nova_math_Nova_InvalidNumericStatementException_native_Nova_construct InvalidNumericStatementException;
} nova_math_native_InvalidNumericStatementException;

typedef long_long (*nova_math_Nova_Math_native_static_Nova_max)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, long_long, long_long);
typedef long_long (*nova_math_Nova_Math_native_static_Nova_min)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, long_long, long_long);
typedef char (*nova_math_Nova_Math_native_static_Nova_sign)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, long_long);
typedef long_long (*nova_math_Nova_Math_native_static_Nova_random)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, long_long);
typedef long_long (*nova_math_Nova_Math_native0_static_Nova_abs)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, long_long);
typedef double (*nova_math_Nova_Math_native1_static_Nova_abs)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_static_Nova_sqrt)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_static_Nova_pow)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double, double);
typedef double (*nova_math_Nova_Math_native_static_Nova_sin)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_static_Nova_cos)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_static_Nova_tan)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_static_Nova_asin)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_static_Nova_acos)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_static_Nova_atan)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_static_Nova_round)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_math_Nova_Math_native_static_Nova_floor)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef long_long (*nova_math_Nova_Math_native_static_Nova_ceil)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*, double);
typedef nova_math_Nova_Math* (*nova_math_Nova_Math_native_Nova_construct)(nova_math_Nova_Math*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_Math
{
nova_math_Nova_Math_native_Nova_construct Math;
} nova_math_native_Math;

typedef nova_math_Nova_Matrix* (*nova_math_Nova_Matrix_native_Nova_add)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*, nova_math_Nova_Matrix*, char);
typedef nova_math_Nova_Matrix* (*nova_math_Nova_Matrix_native_Nova_subtract)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*, nova_math_Nova_Matrix*, char);
typedef nova_math_Nova_Matrix* (*nova_math_Nova_Matrix_native_Nova_multiply)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*, nova_math_Nova_Matrix*);
typedef nova_math_Nova_Matrix* (*nova_math_Nova_Matrix_native_Nova_transpose)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*);
typedef double (*nova_math_Nova_Matrix_native_Nova_determinant)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*);
typedef double (*nova_math_Nova_Matrix_native_static_Nova_determinant)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef nova_Nova_String* (*nova_math_Nova_Matrix_native_Nova_toString)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_Matrix* (*nova_math_Nova_Matrix_native0_Nova_construct)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*, int, int);
typedef nova_math_Nova_Matrix* (*nova_math_Nova_Matrix_native1_Nova_construct)(nova_math_Nova_Matrix*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);

typedef struct nova_math_native_Matrix
{
nova_math_Nova_Matrix_native_Nova_add add;
nova_math_Nova_Matrix_native_Nova_subtract subtract;
nova_math_Nova_Matrix_native_Nova_multiply multiply;
nova_math_Nova_Matrix_native_Nova_transpose transpose;
nova_math_Nova_Matrix_native_Nova_determinant determinant;
nova_math_Nova_Matrix_native_Nova_toString toString;
nova_math_Nova_Matrix_native0_Nova_construct Matrix__nova_primitive_number_Int__nova_primitive_number_Int;
nova_math_Nova_Matrix_native1_Nova_construct Matrix__nova_datastruct_list_Array;
} nova_math_native_Matrix;

typedef nova_Nova_String* (*nova_math_Nova_NumericOperand_native_Nova_toString)(nova_math_Nova_NumericOperand*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_NumericOperand* (*nova_math_Nova_NumericOperand_native_Nova_construct)(nova_math_Nova_NumericOperand*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_math_native_NumericOperand
{
nova_math_Nova_NumericOperand_native_Nova_toString toString;
nova_math_Nova_NumericOperand_native_Nova_construct NumericOperand;
} nova_math_native_NumericOperand;

typedef nova_Nova_String* (*nova_math_Nova_NumericOperation_native_Nova_toString)(nova_math_Nova_NumericOperation*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_NumericOperation* (*nova_math_Nova_NumericOperation_native0_Nova_construct)(nova_math_Nova_NumericOperation*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_math_Nova_NumericOperation* (*nova_math_Nova_NumericOperation_native1_Nova_construct)(nova_math_Nova_NumericOperation*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*, nova_Nova_String*);

typedef struct nova_math_native_NumericOperation
{
nova_math_Nova_NumericOperation_native_Nova_toString toString;
nova_math_Nova_NumericOperation_native0_Nova_construct NumericOperation__nova_String;
nova_math_Nova_NumericOperation_native1_Nova_construct NumericOperation__nova_String__nova_String__nova_String;
} nova_math_native_NumericOperation;

typedef nova_Nova_String* (*nova_math_Nova_NumericStatement_native_Nova_toString)(nova_math_Nova_NumericStatement*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_NumericStatement* (*nova_math_Nova_NumericStatement_native_Nova_construct)(nova_math_Nova_NumericStatement*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_native_NumericStatement
{
nova_math_Nova_NumericStatement_native_Nova_toString toString;
nova_math_Nova_NumericStatement_native_Nova_construct NumericStatement;
} nova_math_native_NumericStatement;

typedef nova_Nova_String* (*nova_math_Nova_NumericTree_native_Nova_toString)(nova_math_Nova_NumericTree*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_NumericTree* (*nova_math_Nova_NumericTree_native_Nova_construct)(nova_math_Nova_NumericTree*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_native_NumericTree
{
nova_math_Nova_NumericTree_native_Nova_toString toString;
nova_math_Nova_NumericTree_native_Nova_construct NumericTree;
} nova_math_native_NumericTree;

typedef nova_math_Nova_Polynomial* (*nova_math_Nova_Polynomial_native_Nova_construct)(nova_math_Nova_Polynomial*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_native_Polynomial
{
nova_math_Nova_Polynomial_native_Nova_construct Polynomial;
} nova_math_native_Polynomial;

typedef double (*nova_math_Nova_Sequence_native_static_Nova_sum)(nova_math_Nova_Sequence*, nova_exception_Nova_ExceptionData*, int);
typedef nova_math_Nova_Sequence* (*nova_math_Nova_Sequence_native_Nova_construct)(nova_math_Nova_Sequence*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray*);

typedef struct nova_math_native_Sequence
{
nova_math_Nova_Sequence_native_Nova_construct Sequence;
} nova_math_native_Sequence;

typedef nova_math_Nova_Statement* (*nova_math_Nova_Statement_native_Nova_construct)(nova_math_Nova_Statement*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_Statement
{
nova_math_Nova_Statement_native_Nova_construct Statement;
} nova_math_native_Statement;

typedef nova_Nova_String* (*nova_math_Nova_StatementComponent_native_Nova_toString)(nova_math_Nova_StatementComponent*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_StatementComponent* (*nova_math_Nova_StatementComponent_native_Nova_construct)(nova_math_Nova_StatementComponent*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_math_native_StatementComponent
{
nova_math_Nova_StatementComponent_native_Nova_toString toString;
nova_math_Nova_StatementComponent_native_Nova_construct StatementComponent;
} nova_math_native_StatementComponent;

typedef nova_math_Nova_VariableOperand* (*nova_math_Nova_VariableOperand_native_Nova_construct)(nova_math_Nova_VariableOperand*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_native_VariableOperand
{
nova_math_Nova_VariableOperand_native_Nova_construct VariableOperand;
} nova_math_native_VariableOperand;

typedef double (*nova_math_Nova_Vector_native_Nova_dotProduct)(nova_math_Nova_Vector*, nova_exception_Nova_ExceptionData*, nova_math_Nova_Vector*);
typedef double (*nova_math_Nova_Vector_native_Nova_innerProduct)(nova_math_Nova_Vector*, nova_exception_Nova_ExceptionData*, nova_math_Nova_Vector*);
typedef void (*nova_math_Nova_Vector_native_Nova_scale)(nova_math_Nova_Vector*, nova_exception_Nova_ExceptionData*, double);
typedef void (*nova_math_Nova_Vector_native_Nova_normalize)(nova_math_Nova_Vector*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_math_Nova_Vector_native_Nova_toString)(nova_math_Nova_Vector*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_Vector* (*nova_math_Nova_Vector_native0_Nova_construct)(nova_math_Nova_Vector*, nova_exception_Nova_ExceptionData*, int);
typedef nova_math_Nova_Vector* (*nova_math_Nova_Vector_native1_Nova_construct)(nova_math_Nova_Vector*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_DoubleArray*);

typedef struct nova_math_native_Vector
{
nova_math_Nova_Vector_native_Nova_dotProduct dotProduct;
nova_math_Nova_Vector_native_Nova_innerProduct innerProduct;
nova_math_Nova_Vector_native_Nova_scale scale;
nova_math_Nova_Vector_native_Nova_normalize normalize;
nova_math_Nova_Vector_native_Nova_toString toString;
nova_math_Nova_Vector_native0_Nova_construct Vector__nova_primitive_number_Int;
nova_math_Nova_Vector_native1_Nova_construct Vector__nova_datastruct_list_DoubleArray;
} nova_math_native_Vector;

typedef double (*nova_math_Nova_Vector2D_native_Nova_dotProduct)(nova_math_Nova_Vector2D*, nova_exception_Nova_ExceptionData*, nova_math_Nova_Vector3D*);
typedef nova_math_Nova_Vector2D* (*nova_math_Nova_Vector2D_native0_Nova_construct)(nova_math_Nova_Vector2D*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_Vector2D* (*nova_math_Nova_Vector2D_native1_Nova_construct)(nova_math_Nova_Vector2D*, nova_exception_Nova_ExceptionData*, double, double);

typedef struct nova_math_native_Vector2D
{
nova_math_Nova_Vector2D_native_Nova_dotProduct dotProduct;
nova_math_Nova_Vector2D_native0_Nova_construct Vector2D;
nova_math_Nova_Vector2D_native1_Nova_construct Vector2D__nova_primitive_number_Double__nova_primitive_number_Double;
} nova_math_native_Vector2D;

typedef nova_math_Nova_Vector3D* (*nova_math_Nova_Vector3D_native_Nova_crossProduct)(nova_math_Nova_Vector3D*, nova_exception_Nova_ExceptionData*, nova_math_Nova_Vector3D*);
typedef nova_math_Nova_Vector3D* (*nova_math_Nova_Vector3D_native0_Nova_construct)(nova_math_Nova_Vector3D*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_Vector3D* (*nova_math_Nova_Vector3D_native1_Nova_construct)(nova_math_Nova_Vector3D*, nova_exception_Nova_ExceptionData*, double, double, double);

typedef struct nova_math_native_Vector3D
{
nova_math_Nova_Vector3D_native_Nova_crossProduct crossProduct;
nova_math_Nova_Vector3D_native0_Nova_construct Vector3D;
nova_math_Nova_Vector3D_native1_Nova_construct Vector3D__nova_primitive_number_Double__nova_primitive_number_Double__nova_primitive_number_Double;
} nova_math_native_Vector3D;

typedef nova_math_Nova_Vector4D* (*nova_math_Nova_Vector4D_native0_Nova_construct)(nova_math_Nova_Vector4D*, nova_exception_Nova_ExceptionData*);
typedef nova_math_Nova_Vector4D* (*nova_math_Nova_Vector4D_native1_Nova_construct)(nova_math_Nova_Vector4D*, nova_exception_Nova_ExceptionData*, double, double, double, double);

typedef struct nova_math_native_Vector4D
{
nova_math_Nova_Vector4D_native0_Nova_construct Vector4D;
nova_math_Nova_Vector4D_native1_Nova_construct Vector4D__nova_primitive_number_Double__nova_primitive_number_Double__nova_primitive_number_Double__nova_primitive_number_Double;
} nova_math_native_Vector4D;

typedef nova_math_Nova_NumericStatement* (*nova_math_calculus_Nova_Calculus_native_static_Nova_derivative)(nova_math_calculus_Nova_Calculus*, nova_exception_Nova_ExceptionData*, nova_math_Nova_NumericStatement*);
typedef nova_math_calculus_Nova_Calculus* (*nova_math_calculus_Nova_Calculus_native_Nova_construct)(nova_math_calculus_Nova_Calculus*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_calculus_native_Calculus
{
nova_math_calculus_Nova_Calculus_native_Nova_construct Calculus;
} nova_math_calculus_native_Calculus;

typedef nova_math_huffman_Nova_HuffmanTree* (*nova_math_huffman_Nova_HuffmanTree_native_Nova_construct)(nova_math_huffman_Nova_HuffmanTree*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_huffman_native_HuffmanTree
{
nova_math_huffman_Nova_HuffmanTree_native_Nova_construct HuffmanTree;
} nova_math_huffman_native_HuffmanTree;

typedef nova_math_logic_Nova_Conclusion* (*nova_math_logic_Nova_Conclusion_native_Nova_construct)(nova_math_logic_Nova_Conclusion*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_logic_native_Conclusion
{
nova_math_logic_Nova_Conclusion_native_Nova_construct Conclusion;
} nova_math_logic_native_Conclusion;

typedef nova_math_logic_Nova_Hypothesis* (*nova_math_logic_Nova_Hypothesis_native_Nova_construct)(nova_math_logic_Nova_Hypothesis*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_logic_native_Hypothesis
{
nova_math_logic_Nova_Hypothesis_native_Nova_construct Hypothesis;
} nova_math_logic_native_Hypothesis;

typedef nova_math_logic_Nova_InvalidFormulaException* (*nova_math_logic_Nova_InvalidFormulaException_native_Nova_construct)(nova_math_logic_Nova_InvalidFormulaException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_logic_native_InvalidFormulaException
{
nova_math_logic_Nova_InvalidFormulaException_native_Nova_construct InvalidFormulaException;
} nova_math_logic_native_InvalidFormulaException;

typedef nova_math_logic_Nova_LogicalConnective* (*nova_math_logic_Nova_LogicalConnective_native_Nova_construct)(nova_math_logic_Nova_LogicalConnective*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_logic_native_LogicalConnective
{
nova_math_logic_Nova_LogicalConnective_native_Nova_construct LogicalConnective;
} nova_math_logic_native_LogicalConnective;

typedef nova_Nova_String* (*nova_math_logic_Nova_LogicalStatement_native_Nova_toString)(nova_math_logic_Nova_LogicalStatement*, nova_exception_Nova_ExceptionData*);
typedef nova_math_logic_Nova_LogicalStatement* (*nova_math_logic_Nova_LogicalStatement_native_Nova_construct)(nova_math_logic_Nova_LogicalStatement*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_math_logic_native_LogicalStatement
{
nova_math_logic_Nova_LogicalStatement_native_Nova_toString toString;
nova_math_logic_Nova_LogicalStatement_native_Nova_construct LogicalStatement;
} nova_math_logic_native_LogicalStatement;

typedef nova_math_logic_Nova_StatementComponent* (*nova_math_logic_Nova_StatementComponent_native_Nova_construct)(nova_math_logic_Nova_StatementComponent*, nova_exception_Nova_ExceptionData*);

typedef struct nova_math_logic_native_StatementComponent
{
nova_math_logic_Nova_StatementComponent_native_Nova_construct StatementComponent;
} nova_math_logic_native_StatementComponent;

typedef nova_math_logic_Nova_StatementGroup* (*nova_math_logic_Nova_StatementGroup_native_Nova_construct)(nova_math_logic_Nova_StatementGroup*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, nova_datastruct_Nova_Bounds*);

typedef struct nova_math_logic_native_StatementGroup
{
nova_math_logic_Nova_StatementGroup_native_Nova_construct StatementGroup;
} nova_math_logic_native_StatementGroup;

typedef nova_math_logic_Nova_StatementLetter* (*nova_math_logic_Nova_StatementLetter_native_Nova_construct)(nova_math_logic_Nova_StatementLetter*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*);

typedef struct nova_math_logic_native_StatementLetter
{
nova_math_logic_Nova_StatementLetter_native_Nova_construct StatementLetter;
} nova_math_logic_native_StatementLetter;

typedef nova_math_logic_Nova_WFF* (*nova_math_logic_Nova_WFF_native_Nova_construct)(nova_math_logic_Nova_WFF*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_datastruct_list_Nova_Array*);

typedef struct nova_math_logic_native_WFF
{
nova_math_logic_Nova_WFF_native_Nova_construct WFF;
} nova_math_logic_native_WFF;

typedef char (*nova_network_Nova_ClientSocket_native_Nova_connect)(nova_network_Nova_ClientSocket*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, int);
typedef char (*nova_network_Nova_ClientSocket_native_Nova_close)(nova_network_Nova_ClientSocket*, nova_exception_Nova_ExceptionData*);
typedef nova_network_Nova_ClientSocket* (*nova_network_Nova_ClientSocket_native_Nova_construct)(nova_network_Nova_ClientSocket*, nova_exception_Nova_ExceptionData*);

typedef struct nova_network_native_ClientSocket
{
nova_network_Nova_ClientSocket_native_Nova_connect connect;
nova_network_Nova_ClientSocket_native_Nova_close close;
nova_network_Nova_ClientSocket_native_Nova_construct ClientSocket;
} nova_network_native_ClientSocket;

typedef void (*nova_network_Nova_ConnectionSocket_native_Nova_close)(nova_network_Nova_ConnectionSocket*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_network_Nova_ConnectionSocket_native_Nova_validateConnection)(nova_network_Nova_ConnectionSocket*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_network_Nova_ConnectionSocket_native0_Nova_readString)(nova_network_Nova_ConnectionSocket*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_network_Nova_ConnectionSocket_native_Nova_write)(nova_network_Nova_ConnectionSocket*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_network_Nova_ConnectionSocket* (*nova_network_Nova_ConnectionSocket_native_Nova_construct)(nova_network_Nova_ConnectionSocket*, nova_exception_Nova_ExceptionData*, SOCKET_ID_TYPE);

typedef struct nova_network_native_ConnectionSocket
{
nova_network_Nova_ConnectionSocket_native_Nova_close close;
nova_network_Nova_ConnectionSocket_native_Nova_validateConnection validateConnection;
nova_network_Nova_ConnectionSocket_native0_Nova_readString readString;
nova_network_Nova_ConnectionSocket_native_Nova_write write;
nova_network_Nova_ConnectionSocket_native_Nova_construct ConnectionSocket;
} nova_network_native_ConnectionSocket;

typedef nova_Nova_String* (*nova_network_Nova_NetworkInputStream_native_Nova_readString)(nova_network_Nova_NetworkInputStream*, nova_exception_Nova_ExceptionData*);
typedef nova_datastruct_list_Nova_Array* (*nova_network_Nova_NetworkInputStream_native_Nova_readBytes)(nova_network_Nova_NetworkInputStream*, nova_exception_Nova_ExceptionData*);
typedef nova_network_Nova_NetworkInputStream* (*nova_network_Nova_NetworkInputStream_native_Nova_construct)(nova_network_Nova_NetworkInputStream*, nova_exception_Nova_ExceptionData*, nova_network_Nova_ConnectionSocket*);

typedef struct nova_network_native_NetworkInputStream
{
nova_network_Nova_NetworkInputStream_native_Nova_readString readString;
nova_network_Nova_NetworkInputStream_native_Nova_readBytes readBytes;
nova_network_Nova_NetworkInputStream_native_Nova_construct NetworkInputStream;
} nova_network_native_NetworkInputStream;

typedef char (*nova_network_Nova_NetworkOutputStream_native0_Nova_write)(nova_network_Nova_NetworkOutputStream*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef char (*nova_network_Nova_NetworkOutputStream_native1_Nova_write)(nova_network_Nova_NetworkOutputStream*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
typedef nova_network_Nova_NetworkOutputStream* (*nova_network_Nova_NetworkOutputStream_native_Nova_construct)(nova_network_Nova_NetworkOutputStream*, nova_exception_Nova_ExceptionData*, nova_network_Nova_ConnectionSocket*);

typedef struct nova_network_native_NetworkOutputStream
{
nova_network_Nova_NetworkOutputStream_native0_Nova_write write__nova_String;
nova_network_Nova_NetworkOutputStream_native1_Nova_write write__nova_Object;
nova_network_Nova_NetworkOutputStream_native_Nova_construct NetworkOutputStream;
} nova_network_native_NetworkOutputStream;

typedef char (*nova_network_Nova_ServerSocket_native_Nova_start)(nova_network_Nova_ServerSocket*, nova_exception_Nova_ExceptionData*, int);
typedef char (*nova_network_Nova_ServerSocket_native_Nova_close)(nova_network_Nova_ServerSocket*, nova_exception_Nova_ExceptionData*);
typedef nova_network_Nova_ConnectionSocket* (*nova_network_Nova_ServerSocket_native_Nova_acceptClient)(nova_network_Nova_ServerSocket*, nova_exception_Nova_ExceptionData*);
typedef nova_network_Nova_ServerSocket* (*nova_network_Nova_ServerSocket_native_Nova_construct)(nova_network_Nova_ServerSocket*, nova_exception_Nova_ExceptionData*);

typedef struct nova_network_native_ServerSocket
{
nova_network_Nova_ServerSocket_native_Nova_start start;
nova_network_Nova_ServerSocket_native_Nova_close close;
nova_network_Nova_ServerSocket_native_Nova_acceptClient acceptClient;
nova_network_Nova_ServerSocket_native_Nova_construct ServerSocket;
} nova_network_native_ServerSocket;

typedef nova_network_Nova_Socket* (*nova_network_Nova_Socket_native_Nova_construct)(nova_network_Nova_Socket*, nova_exception_Nova_ExceptionData*);

typedef struct nova_network_native_Socket
{
nova_network_Nova_Socket_native_Nova_construct Socket;
} nova_network_native_Socket;

typedef char (*nova_operators_Nova_Equals_native_Nova_equals)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_operators_native_Equals
{
nova_operators_Nova_Equals_native_Nova_equals equals;
} nova_operators_native_Equals;

typedef nova_Nova_Object* (*nova_operators_Nova_Multiply_native0_Nova_multiply)(nova_operators_Nova_Multiply*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);

typedef struct nova_operators_native_Multiply
{
nova_operators_Nova_Multiply_native0_Nova_multiply multiply__nova_Object;
} nova_operators_native_Multiply;

typedef nova_Nova_String* (*nova_primitive_Nova_Bool_native_static_Nova_toString)(nova_primitive_Nova_Bool*, nova_exception_Nova_ExceptionData*, char);
typedef nova_Nova_String* (*nova_primitive_Nova_Bool_native_Nova_toString)(nova_primitive_Nova_Bool*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_primitive_Nova_Bool_native0_Nova_compareTo)(nova_primitive_Nova_Bool*, nova_exception_Nova_ExceptionData*, char);
typedef nova_primitive_Nova_Bool* (*nova_primitive_Nova_Bool_native_Nova_construct)(nova_primitive_Nova_Bool*, nova_exception_Nova_ExceptionData*, char);

typedef struct nova_primitive_native_Bool
{
nova_primitive_Nova_Bool_native_Nova_toString toString;
nova_primitive_Nova_Bool_native0_Nova_compareTo compareTo__nova_primitive_Bool;
nova_primitive_Nova_Bool_native_Nova_construct Bool;
} nova_primitive_native_Bool;

typedef nova_Nova_String* (*nova_primitive_Nova_Null_native_Nova_toString)(nova_primitive_Nova_Null*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_primitive_Nova_Null_native_Nova_concat)(nova_primitive_Nova_Null*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_primitive_Nova_Null* (*nova_primitive_Nova_Null_native_Nova_construct)(nova_primitive_Nova_Null*, nova_exception_Nova_ExceptionData*);

typedef struct nova_primitive_native_Null
{
nova_primitive_Nova_Null_native_Nova_toString toString;
nova_primitive_Nova_Null_native_Nova_concat concat;
nova_primitive_Nova_Null_native_Nova_construct Null;
} nova_primitive_native_Null;

typedef nova_primitive_Nova_Primitive* (*nova_primitive_Nova_Primitive_native_Nova_construct)(nova_primitive_Nova_Primitive*, nova_exception_Nova_ExceptionData*);

typedef struct nova_primitive_native_Primitive
{
nova_primitive_Nova_Primitive_native_Nova_construct Primitive;
} nova_primitive_native_Primitive;

typedef int (*nova_primitive_number_Nova_Byte_native_static_Nova_numDigits)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*, char);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Byte_native_static_Nova_toString)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*, char);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Byte_native_Nova_toString)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_primitive_number_Nova_Byte_native0_Nova_compareTo)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*, char);
typedef char (*nova_primitive_number_Nova_Byte_native_Nova_multiply)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*, char);
typedef nova_primitive_number_Nova_Byte* (*nova_primitive_number_Nova_Byte_native_Nova_construct)(nova_primitive_number_Nova_Byte*, nova_exception_Nova_ExceptionData*, char);

typedef struct nova_primitive_number_native_Byte
{
nova_primitive_number_Nova_Byte_native_Nova_toString toString;
nova_primitive_number_Nova_Byte_native0_Nova_compareTo compareTo__nova_primitive_number_Byte;
nova_primitive_number_Nova_Byte_native_Nova_multiply multiply;
nova_primitive_number_Nova_Byte_native_Nova_construct Byte;
} nova_primitive_number_native_Byte;

typedef nova_Nova_String* (*nova_primitive_number_Nova_Char_native_static_Nova_toString)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Char_native_Nova_toString)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_primitive_number_Nova_Char_native_Nova_toLowerCase)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_primitive_number_Nova_Char_native_Nova_toUpperCase)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*);
typedef char (*nova_primitive_number_Nova_Char_native_static_Nova_toLowerCase)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);
typedef char (*nova_primitive_number_Nova_Char_native_static_Nova_toUpperCase)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);
typedef int (*nova_primitive_number_Nova_Char_native0_Nova_compareTo)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);
typedef char (*nova_primitive_number_Nova_Char_native_Nova_multiply)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);
typedef nova_primitive_number_Nova_Char* (*nova_primitive_number_Nova_Char_native_Nova_construct)(nova_primitive_number_Nova_Char*, nova_exception_Nova_ExceptionData*, char);

typedef struct nova_primitive_number_native_Char
{
nova_primitive_number_Nova_Char_native_Nova_toString toString;
nova_primitive_number_Nova_Char_native_Nova_toLowerCase toLowerCase;
nova_primitive_number_Nova_Char_native_Nova_toUpperCase toUpperCase;
nova_primitive_number_Nova_Char_native0_Nova_compareTo compareTo__nova_primitive_number_Char;
nova_primitive_number_Nova_Char_native_Nova_multiply multiply;
nova_primitive_number_Nova_Char_native_Nova_construct Char;
} nova_primitive_number_native_Char;

typedef int (*nova_primitive_number_Nova_Double_native_static_Nova_numDigits)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Double_native_static_Nova_genString)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, char*, int);
typedef char* (*nova_primitive_number_Nova_Double_native_static_Nova_genBuffer)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef int (*nova_primitive_number_Nova_Double_native_static_Nova_repetition)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, char*, int);
typedef int (*nova_primitive_number_Nova_Double_native_static_Nova_lastSignificantDigit)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, char*, int);
typedef char* (*nova_primitive_number_Nova_Double_native_static_Nova_toCharArray)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Double_native_static_Nova_toString)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Double_native_Nova_toString)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*);
typedef double (*nova_primitive_number_Nova_Double_native_static_Nova_parseDouble)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef double (*nova_primitive_number_Nova_Double_native0_Nova_compareTo)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef double (*nova_primitive_number_Nova_Double_native_Nova_multiply)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);
typedef nova_primitive_number_Nova_Double* (*nova_primitive_number_Nova_Double_native_Nova_construct)(nova_primitive_number_Nova_Double*, nova_exception_Nova_ExceptionData*, double);

typedef struct nova_primitive_number_native_Double
{
nova_primitive_number_Nova_Double_native_Nova_toString toString;
nova_primitive_number_Nova_Double_native0_Nova_compareTo compareTo__nova_primitive_number_Double;
nova_primitive_number_Nova_Double_native_Nova_multiply multiply;
nova_primitive_number_Nova_Double_native_Nova_construct Double;
} nova_primitive_number_native_Double;

typedef int (*nova_primitive_number_Nova_Float_native_static_Nova_numDigits)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*, float);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Float_native_static_Nova_toString)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*, float);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Float_native_Nova_toString)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*);
typedef float (*nova_primitive_number_Nova_Float_native0_Nova_compareTo)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*, float);
typedef float (*nova_primitive_number_Nova_Float_native_Nova_multiply)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*, float);
typedef nova_primitive_number_Nova_Float* (*nova_primitive_number_Nova_Float_native_Nova_construct)(nova_primitive_number_Nova_Float*, nova_exception_Nova_ExceptionData*, int);

typedef struct nova_primitive_number_native_Float
{
nova_primitive_number_Nova_Float_native_Nova_toString toString;
nova_primitive_number_Nova_Float_native0_Nova_compareTo compareTo__nova_primitive_number_Float;
nova_primitive_number_Nova_Float_native_Nova_multiply multiply;
nova_primitive_number_Nova_Float_native_Nova_construct Float;
} nova_primitive_number_native_Float;

typedef long_long (*nova_primitive_number_Nova_Int_native_Nova_getHashCodeLong)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*);
typedef int (*nova_primitive_number_Nova_Int_native_static_Nova_numDigits)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Int_native_static_Nova_toString)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, int);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Int_native_Nova_toString)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*);
typedef int (*nova_primitive_number_Nova_Int_native_static_Nova_parseInt)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef int (*nova_primitive_number_Nova_Int_native0_Nova_compareTo)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, int);
typedef int (*nova_primitive_number_Nova_Int_native_Nova_multiply)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, int);
typedef nova_primitive_number_Nova_Int* (*nova_primitive_number_Nova_Int_native_Nova_construct)(nova_primitive_number_Nova_Int*, nova_exception_Nova_ExceptionData*, int);

typedef struct nova_primitive_number_native_Int
{
nova_primitive_number_Nova_Int_native_Nova_getHashCodeLong getHashCodeLong;
nova_primitive_number_Nova_Int_native_Nova_toString toString;
nova_primitive_number_Nova_Int_native0_Nova_compareTo compareTo__nova_primitive_number_Int;
nova_primitive_number_Nova_Int_native_Nova_multiply multiply;
nova_primitive_number_Nova_Int_native_Nova_construct Int;
} nova_primitive_number_native_Int;


typedef struct nova_primitive_number_native_Integer
{
} nova_primitive_number_native_Integer;

typedef int (*nova_primitive_number_Nova_Long_native_static_Nova_numDigits)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);
typedef char* (*nova_primitive_number_Nova_Long_native_static_Nova_toCharArray)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Long_native_static_Nova_toString)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Long_native_Nova_toString)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*);
typedef long_long (*nova_primitive_number_Nova_Long_native0_Nova_compareTo)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);
typedef long_long (*nova_primitive_number_Nova_Long_native_Nova_multiply)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);
typedef nova_primitive_number_Nova_Long* (*nova_primitive_number_Nova_Long_native_Nova_construct)(nova_primitive_number_Nova_Long*, nova_exception_Nova_ExceptionData*, long_long);

typedef struct nova_primitive_number_native_Long
{
nova_primitive_number_Nova_Long_native_Nova_toString toString;
nova_primitive_number_Nova_Long_native0_Nova_compareTo compareTo__nova_primitive_number_Long;
nova_primitive_number_Nova_Long_native_Nova_multiply multiply;
nova_primitive_number_Nova_Long_native_Nova_construct Long;
} nova_primitive_number_native_Long;

typedef int (*nova_primitive_number_Nova_Number_native_static_Nova_numDigits)(nova_primitive_number_Nova_Number*, nova_exception_Nova_ExceptionData*, nova_primitive_number_Nova_Number*);
typedef nova_primitive_number_Nova_Number* (*nova_primitive_number_Nova_Number_native_Nova_construct)(nova_primitive_number_Nova_Number*, nova_exception_Nova_ExceptionData*);

typedef struct nova_primitive_number_native_Number
{
nova_primitive_number_Nova_Number_native_Nova_construct Number;
} nova_primitive_number_native_Number;


typedef struct nova_primitive_number_native_RealNumber
{
} nova_primitive_number_native_RealNumber;

typedef int (*nova_primitive_number_Nova_Short_native_static_Nova_numDigits)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*, short);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Short_native_static_Nova_toString)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*, short);
typedef nova_Nova_String* (*nova_primitive_number_Nova_Short_native_Nova_toString)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*);
typedef short (*nova_primitive_number_Nova_Short_native0_Nova_compareTo)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*, short);
typedef short (*nova_primitive_number_Nova_Short_native_Nova_multiply)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*, short);
typedef nova_primitive_number_Nova_Short* (*nova_primitive_number_Nova_Short_native_Nova_construct)(nova_primitive_number_Nova_Short*, nova_exception_Nova_ExceptionData*, short);

typedef struct nova_primitive_number_native_Short
{
nova_primitive_number_Nova_Short_native_Nova_toString toString;
nova_primitive_number_Nova_Short_native0_Nova_compareTo compareTo__nova_primitive_number_Short;
nova_primitive_number_Nova_Short_native_Nova_multiply multiply;
nova_primitive_number_Nova_Short_native_Nova_construct Short;
} nova_primitive_number_native_Short;

typedef nova_process_Nova_Process* (*nova_process_Nova_Process_native_Nova_construct)(nova_process_Nova_Process*, nova_exception_Nova_ExceptionData*, nova_io_Nova_StreamReader*);

typedef struct nova_process_native_Process
{
nova_process_Nova_Process_native_Nova_construct Process;
} nova_process_native_Process;

typedef nova_Nova_String* (*nova_security_Nova_MD5_native_static_Nova_encrypt)(nova_security_Nova_MD5*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_security_Nova_MD5* (*nova_security_Nova_MD5_native_Nova_construct)(nova_security_Nova_MD5*, nova_exception_Nova_ExceptionData*);

typedef struct nova_security_native_MD5
{
nova_security_Nova_MD5_native_Nova_construct MD5;
} nova_security_native_MD5;

typedef nova_star_Nova_Frame* (*nova_star_Nova_Frame_native_Nova_construct)(nova_star_Nova_Frame*, nova_exception_Nova_ExceptionData*, int, int, int, int);

typedef struct nova_star_native_Frame
{
nova_star_Nova_Frame_native_Nova_construct Frame;
} nova_star_native_Frame;

typedef void (*nova_star_Nova_Window_native_Nova_create)(nova_star_Nova_Window*, nova_exception_Nova_ExceptionData*);
typedef nova_star_Nova_Window* (*nova_star_Nova_Window_native_Nova_construct)(nova_star_Nova_Window*, nova_exception_Nova_ExceptionData*);

typedef struct nova_star_native_Window
{
nova_star_Nova_Window_native_Nova_create create;
nova_star_Nova_Window_native_Nova_construct Window;
} nova_star_native_Window;

typedef void (*nova_star_Nova_WindowThread_native_Nova_run)(nova_star_Nova_WindowThread*, nova_exception_Nova_ExceptionData*);
typedef nova_star_Nova_WindowThread* (*nova_star_Nova_WindowThread_native_Nova_construct)(nova_star_Nova_WindowThread*, nova_exception_Nova_ExceptionData*, nova_star_Nova_Window*);

typedef struct nova_star_native_WindowThread
{
nova_star_Nova_WindowThread_native_Nova_run run;
nova_star_Nova_WindowThread_native_Nova_construct WindowThread;
} nova_star_native_WindowThread;

typedef void (*nova_svg_Nova_Svg_native_Nova_generateOutput)(nova_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef void (*nova_svg_Nova_Svg_native_Nova_generateHTMLOutput)(nova_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_svg_Nova_Svg* (*nova_svg_Nova_Svg_native_Nova_construct)(nova_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_native_Svg
{
nova_svg_Nova_Svg_native_Nova_generateOutput generateOutput;
nova_svg_Nova_Svg_native_Nova_generateHTMLOutput generateHTMLOutput;
nova_svg_Nova_Svg_native_Nova_construct Svg;
} nova_svg_native_Svg;

typedef void (*nova_svg_Nova_SvgCircle_native_Nova_generateOutput)(nova_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_Nova_String* (*nova_svg_Nova_SvgCircle_native_Nova_toString)(nova_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*);
typedef nova_svg_Nova_SvgCircle* (*nova_svg_Nova_SvgCircle_native_Nova_construct)(nova_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*, double, double, int);

typedef struct nova_svg_native_SvgCircle
{
nova_svg_Nova_SvgCircle_native_Nova_generateOutput generateOutput;
nova_svg_Nova_SvgCircle_native_Nova_toString toString;
nova_svg_Nova_SvgCircle_native_Nova_construct SvgCircle;
} nova_svg_native_SvgCircle;

typedef void (*nova_svg_Nova_SvgComponent_native_Nova_generateOutput)(nova_svg_Nova_SvgComponent*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_svg_Nova_SvgComponent* (*nova_svg_Nova_SvgComponent_native_Nova_construct)(nova_svg_Nova_SvgComponent*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_native_SvgComponent
{
nova_svg_Nova_SvgComponent_native_Nova_generateOutput generateOutput;
nova_svg_Nova_SvgComponent_native_Nova_construct SvgComponent;
} nova_svg_native_SvgComponent;

typedef void (*nova_svg_Nova_SvgComponentList_native_Nova_generateOutput)(nova_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef void (*nova_svg_Nova_SvgComponentList_native_Nova_addChild)(nova_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*, nova_svg_Nova_SvgComponent*);
typedef nova_svg_Nova_SvgComponentList* (*nova_svg_Nova_SvgComponentList_native_Nova_construct)(nova_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_native_SvgComponentList
{
nova_svg_Nova_SvgComponentList_native_Nova_generateOutput generateOutput;
nova_svg_Nova_SvgComponentList_native_Nova_addChild addChild;
nova_svg_Nova_SvgComponentList_native_Nova_construct SvgComponentList;
} nova_svg_native_SvgComponentList;

typedef nova_svg_Nova_SvgComponentNode* (*nova_svg_Nova_SvgComponentNode_native_Nova_construct)(nova_svg_Nova_SvgComponentNode*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_native_SvgComponentNode
{
nova_svg_Nova_SvgComponentNode_native_Nova_construct SvgComponentNode;
} nova_svg_native_SvgComponentNode;

typedef void (*nova_svg_Nova_SvgMainComponent_native_Nova_generateOutput)(nova_svg_Nova_SvgMainComponent*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_svg_Nova_SvgMainComponent* (*nova_svg_Nova_SvgMainComponent_native_Nova_construct)(nova_svg_Nova_SvgMainComponent*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_native_SvgMainComponent
{
nova_svg_Nova_SvgMainComponent_native_Nova_generateOutput generateOutput;
nova_svg_Nova_SvgMainComponent_native_Nova_construct SvgMainComponent;
} nova_svg_native_SvgMainComponent;

typedef nova_svg_no3_Nova_No3Selection* (*nova_svg_no3_Nova_No3_native_static_Nova_select)(nova_svg_no3_Nova_No3*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_svg_no3_Nova_No3* (*nova_svg_no3_Nova_No3_native_Nova_construct)(nova_svg_no3_Nova_No3*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_no3_native_No3
{
nova_svg_no3_Nova_No3_native_Nova_construct No3;
} nova_svg_no3_native_No3;

typedef nova_Nova_String* (*nova_svg_no3_Nova_No3Node_native_Nova_toJs)(nova_svg_no3_Nova_No3Node*, nova_exception_Nova_ExceptionData*);
typedef nova_svg_no3_Nova_No3Node* (*nova_svg_no3_Nova_No3Node_native_Nova_construct)(nova_svg_no3_Nova_No3Node*, nova_exception_Nova_ExceptionData*);

typedef struct nova_svg_no3_native_No3Node
{
nova_svg_no3_Nova_No3Node_native_Nova_toJs toJs;
nova_svg_no3_Nova_No3Node_native_Nova_construct No3Node;
} nova_svg_no3_native_No3Node;

typedef nova_Nova_String* (*nova_svg_no3_Nova_No3Selection_native_Nova_toJs)(nova_svg_no3_Nova_No3Selection*, nova_exception_Nova_ExceptionData*);
typedef nova_svg_no3_Nova_No3Selection* (*nova_svg_no3_Nova_No3Selection_native_Nova_construct)(nova_svg_no3_Nova_No3Selection*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_svg_no3_native_No3Selection
{
nova_svg_no3_Nova_No3Selection_native_Nova_toJs toJs;
nova_svg_no3_Nova_No3Selection_native_Nova_construct No3Selection;
} nova_svg_no3_native_No3Selection;

typedef void (*nova_thread_Nova_Thread_native_Nova_start)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_thread_Nova_Thread_native_Nova_join)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_thread_Nova_Thread_native_Nova_kill)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*);
typedef void (*nova_thread_Nova_Thread_native_static_Nova_sleep)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*, long_long);
typedef void (*nova_thread_Nova_Thread_native_Nova_run)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*);
typedef nova_thread_Nova_Thread* (*nova_thread_Nova_Thread_native_Nova_construct)(nova_thread_Nova_Thread*, nova_exception_Nova_ExceptionData*);

typedef struct nova_thread_native_Thread
{
nova_thread_Nova_Thread_native_Nova_start start;
nova_thread_Nova_Thread_native_Nova_join join;
nova_thread_Nova_Thread_native_Nova_kill kill;
nova_thread_Nova_Thread_native_Nova_run run;
nova_thread_Nova_Thread_native_Nova_construct Thread;
} nova_thread_native_Thread;

typedef void (*nova_thread_Nova_UncaughtExceptionHandler_native_Nova_uncaughtException)(nova_thread_Nova_UncaughtExceptionHandler*, nova_exception_Nova_ExceptionData*, nova_thread_Nova_Thread*, nova_exception_Nova_Exception*);
typedef nova_thread_Nova_UncaughtExceptionHandler* (*nova_thread_Nova_UncaughtExceptionHandler_native_Nova_construct)(nova_thread_Nova_UncaughtExceptionHandler*, nova_exception_Nova_ExceptionData*);

typedef struct nova_thread_native_UncaughtExceptionHandler
{
nova_thread_Nova_UncaughtExceptionHandler_native_Nova_uncaughtException uncaughtException;
nova_thread_Nova_UncaughtExceptionHandler_native_Nova_construct UncaughtExceptionHandler;
} nova_thread_native_UncaughtExceptionHandler;

typedef nova_thread_async_Nova_AsyncResult* (*nova_thread_async_Nova_Async_native_static_Nova_execute)(nova_thread_async_Nova_Async*, nova_exception_Nova_ExceptionData*, nova_thread_async_Nova_Async_closure3_Nova_func nova_thread_async_Nova_Async_Nova_func, void* nova_thread_async_Nova_Async_ref_Nova_func, void* func_context);
typedef nova_thread_async_Nova_Async* (*nova_thread_async_Nova_Async_native_Nova_construct)(nova_thread_async_Nova_Async*, nova_exception_Nova_ExceptionData*);

typedef struct nova_thread_async_native_Async
{
nova_thread_async_Nova_Async_native_Nova_construct Async;
} nova_thread_async_native_Async;

typedef nova_thread_async_Nova_AsyncResult* (*nova_thread_async_Nova_AsyncResult_native_Nova_construct)(nova_thread_async_Nova_AsyncResult*, nova_exception_Nova_ExceptionData*);

typedef struct nova_thread_async_native_AsyncResult
{
nova_thread_async_Nova_AsyncResult_native_Nova_construct AsyncResult;
} nova_thread_async_native_AsyncResult;

typedef void (*nova_time_Nova_Date_native_Nova_decodeDate)(nova_time_Nova_Date*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, nova_Nova_String*);
typedef void (*nova_time_Nova_Date_native_Nova_updateTime)(nova_time_Nova_Date*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_time_Nova_Date_native0_Nova_formatDate)(nova_time_Nova_Date*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*nova_time_Nova_Date_native1_Nova_formatDate)(nova_time_Nova_Date*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, int, int, int, int, int, int);
typedef nova_time_Nova_Date* (*nova_time_Nova_Date_native_Nova_construct)(nova_time_Nova_Date*, nova_exception_Nova_ExceptionData*);

typedef struct nova_time_native_Date
{
nova_time_Nova_Date_native_Nova_decodeDate decodeDate;
nova_time_Nova_Date_native_Nova_updateTime updateTime;
nova_time_Nova_Date_native0_Nova_formatDate formatDate__nova_String;
nova_time_Nova_Date_native1_Nova_formatDate formatDate__nova_String__nova_primitive_number_Int__nova_primitive_number_Int__nova_primitive_number_Int__nova_primitive_number_Int__nova_primitive_number_Int__nova_primitive_number_Int;
nova_time_Nova_Date_native_Nova_construct Date;
} nova_time_native_Date;

typedef nova_time_Nova_Time* (*nova_time_Nova_Time_native_Nova_construct)(nova_time_Nova_Time*, nova_exception_Nova_ExceptionData*);

typedef struct nova_time_native_Time
{
nova_time_Nova_Time_native_Nova_construct Time;
} nova_time_native_Time;

typedef nova_time_Nova_Timer* (*nova_time_Nova_Timer_native_Nova_start)(nova_time_Nova_Timer*, nova_exception_Nova_ExceptionData*);
typedef nova_time_Nova_Timer* (*nova_time_Nova_Timer_native_Nova_stop)(nova_time_Nova_Timer*, nova_exception_Nova_ExceptionData*);
typedef nova_time_Nova_Timer* (*nova_time_Nova_Timer_native_Nova_reset)(nova_time_Nova_Timer*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*nova_time_Nova_Timer_native_Nova_toString)(nova_time_Nova_Timer*, nova_exception_Nova_ExceptionData*);
typedef nova_time_Nova_Timer* (*nova_time_Nova_Timer_native_Nova_construct)(nova_time_Nova_Timer*, nova_exception_Nova_ExceptionData*);

typedef struct nova_time_native_Timer
{
nova_time_Nova_Timer_native_Nova_start start;
nova_time_Nova_Timer_native_Nova_stop stop;
nova_time_Nova_Timer_native_Nova_reset reset;
nova_time_Nova_Timer_native_Nova_toString toString;
nova_time_Nova_Timer_native_Nova_construct Timer;
} nova_time_native_Timer;

typedef nova_Nova_String* (*nova_web_js_json_Nova_Json_native_Nova_toString)(nova_web_js_json_Nova_Json*, nova_exception_Nova_ExceptionData*);
typedef nova_web_js_json_Nova_Json* (*nova_web_js_json_Nova_Json_native_Nova_construct)(nova_web_js_json_Nova_Json*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_js_json_native_Json
{
nova_web_js_json_Nova_Json_native_Nova_toString toString;
nova_web_js_json_Nova_Json_native_Nova_construct Json;
} nova_web_js_json_native_Json;

typedef void (*nova_web_svg_Nova_Svg_native_Nova_generateOutput)(nova_web_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef void (*nova_web_svg_Nova_Svg_native_Nova_generateHTMLOutput)(nova_web_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_web_svg_Nova_Svg* (*nova_web_svg_Nova_Svg_native_Nova_construct)(nova_web_svg_Nova_Svg*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_native_Svg
{
nova_web_svg_Nova_Svg_native_Nova_generateOutput generateOutput;
nova_web_svg_Nova_Svg_native_Nova_generateHTMLOutput generateHTMLOutput;
nova_web_svg_Nova_Svg_native_Nova_construct Svg;
} nova_web_svg_native_Svg;

typedef void (*nova_web_svg_Nova_SvgCircle_native_Nova_generateOutput)(nova_web_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_Nova_String* (*nova_web_svg_Nova_SvgCircle_native_Nova_toString)(nova_web_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*);
typedef nova_web_svg_Nova_SvgCircle* (*nova_web_svg_Nova_SvgCircle_native_Nova_construct)(nova_web_svg_Nova_SvgCircle*, nova_exception_Nova_ExceptionData*, double, double, int);

typedef struct nova_web_svg_native_SvgCircle
{
nova_web_svg_Nova_SvgCircle_native_Nova_generateOutput generateOutput;
nova_web_svg_Nova_SvgCircle_native_Nova_toString toString;
nova_web_svg_Nova_SvgCircle_native_Nova_construct SvgCircle;
} nova_web_svg_native_SvgCircle;

typedef void (*nova_web_svg_Nova_SvgComponent_native_Nova_generateOutput)(nova_web_svg_Nova_SvgComponent*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_web_svg_Nova_SvgComponent* (*nova_web_svg_Nova_SvgComponent_native_Nova_construct)(nova_web_svg_Nova_SvgComponent*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_native_SvgComponent
{
nova_web_svg_Nova_SvgComponent_native_Nova_generateOutput generateOutput;
nova_web_svg_Nova_SvgComponent_native_Nova_construct SvgComponent;
} nova_web_svg_native_SvgComponent;

typedef void (*nova_web_svg_Nova_SvgComponentList_native_Nova_generateOutput)(nova_web_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef void (*nova_web_svg_Nova_SvgComponentList_native_Nova_addChild)(nova_web_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*, nova_web_svg_Nova_SvgComponent*);
typedef nova_web_svg_Nova_SvgComponentList* (*nova_web_svg_Nova_SvgComponentList_native_Nova_construct)(nova_web_svg_Nova_SvgComponentList*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_native_SvgComponentList
{
nova_web_svg_Nova_SvgComponentList_native_Nova_generateOutput generateOutput;
nova_web_svg_Nova_SvgComponentList_native_Nova_addChild addChild;
nova_web_svg_Nova_SvgComponentList_native_Nova_construct SvgComponentList;
} nova_web_svg_native_SvgComponentList;

typedef nova_web_svg_Nova_SvgComponentNode* (*nova_web_svg_Nova_SvgComponentNode_native_Nova_construct)(nova_web_svg_Nova_SvgComponentNode*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_native_SvgComponentNode
{
nova_web_svg_Nova_SvgComponentNode_native_Nova_construct SvgComponentNode;
} nova_web_svg_native_SvgComponentNode;

typedef void (*nova_web_svg_Nova_SvgMainComponent_native_Nova_generateOutput)(nova_web_svg_Nova_SvgMainComponent*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*);
typedef nova_web_svg_Nova_SvgMainComponent* (*nova_web_svg_Nova_SvgMainComponent_native_Nova_construct)(nova_web_svg_Nova_SvgMainComponent*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_native_SvgMainComponent
{
nova_web_svg_Nova_SvgMainComponent_native_Nova_generateOutput generateOutput;
nova_web_svg_Nova_SvgMainComponent_native_Nova_construct SvgMainComponent;
} nova_web_svg_native_SvgMainComponent;

typedef nova_web_svg_no3_Nova_No3Select* (*nova_web_svg_no3_Nova_No3_native_static_Nova_select)(nova_web_svg_no3_Nova_No3*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_web_svg_no3_Nova_No3SelectAll* (*nova_web_svg_no3_Nova_No3_native_static_Nova_selectAll)(nova_web_svg_no3_Nova_No3*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_web_svg_no3_Nova_No3* (*nova_web_svg_no3_Nova_No3_native_Nova_construct)(nova_web_svg_no3_Nova_No3*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_no3_native_No3
{
nova_web_svg_no3_Nova_No3_native_Nova_construct No3;
} nova_web_svg_no3_native_No3;

typedef nova_Nova_String* (*nova_web_svg_no3_Nova_No3Node_native_Nova_toJs)(nova_web_svg_no3_Nova_No3Node*, nova_exception_Nova_ExceptionData*);
typedef nova_web_svg_no3_Nova_No3Node* (*nova_web_svg_no3_Nova_No3Node_native_Nova_construct)(nova_web_svg_no3_Nova_No3Node*, nova_exception_Nova_ExceptionData*);

typedef struct nova_web_svg_no3_native_No3Node
{
nova_web_svg_no3_Nova_No3Node_native_Nova_toJs toJs;
nova_web_svg_no3_Nova_No3Node_native_Nova_construct No3Node;
} nova_web_svg_no3_native_No3Node;

typedef nova_Nova_String* (*nova_web_svg_no3_Nova_No3Select_native_Nova_toJs)(nova_web_svg_no3_Nova_No3Select*, nova_exception_Nova_ExceptionData*);
typedef nova_web_svg_no3_Nova_No3Select* (*nova_web_svg_no3_Nova_No3Select_native_Nova_construct)(nova_web_svg_no3_Nova_No3Select*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_web_svg_no3_native_No3Select
{
nova_web_svg_no3_Nova_No3Select_native_Nova_toJs toJs;
nova_web_svg_no3_Nova_No3Select_native_Nova_construct No3Select;
} nova_web_svg_no3_native_No3Select;

typedef nova_Nova_String* (*nova_web_svg_no3_Nova_No3SelectAll_native_Nova_toJs)(nova_web_svg_no3_Nova_No3SelectAll*, nova_exception_Nova_ExceptionData*);
typedef nova_web_svg_no3_Nova_No3SelectAll* (*nova_web_svg_no3_Nova_No3SelectAll_native_Nova_construct)(nova_web_svg_no3_Nova_No3SelectAll*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct nova_web_svg_no3_native_No3SelectAll
{
nova_web_svg_no3_Nova_No3SelectAll_native_Nova_toJs toJs;
nova_web_svg_no3_Nova_No3SelectAll_native_Nova_construct No3SelectAll;
} nova_web_svg_no3_native_No3SelectAll;

typedef void (*compiler_Nova_Compiler_native_static_Nova_main)(compiler_Nova_Compiler*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef void (*compiler_Nova_Compiler_native_Nova_compile)(compiler_Nova_Compiler*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef compiler_Nova_Compiler* (*compiler_Nova_Compiler_native_Nova_construct)(compiler_Nova_Compiler*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_native_Compiler
{
compiler_Nova_Compiler_native_Nova_compile compile;
compiler_Nova_Compiler_native_Nova_construct Compiler;
} compiler_native_Compiler;

typedef compiler_Nova_SyntaxErrorException* (*compiler_Nova_SyntaxErrorException_native_Nova_construct)(compiler_Nova_SyntaxErrorException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, compiler_tree_node_Nova_Node*);

typedef struct compiler_native_SyntaxErrorException
{
compiler_Nova_SyntaxErrorException_native_Nova_construct SyntaxErrorException;
} compiler_native_SyntaxErrorException;

typedef compiler_error_Nova_UnimplementedOperationException* (*compiler_error_Nova_UnimplementedOperationException_native_Nova_construct)(compiler_error_Nova_UnimplementedOperationException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct compiler_error_native_UnimplementedOperationException
{
compiler_error_Nova_UnimplementedOperationException_native_Nova_construct UnimplementedOperationException;
} compiler_error_native_UnimplementedOperationException;

typedef compiler_tree_Nova_SyntaxTree* (*compiler_tree_Nova_SyntaxTree_native_Nova_construct)(compiler_tree_Nova_SyntaxTree*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_tree_native_SyntaxTree
{
compiler_tree_Nova_SyntaxTree_native_Nova_construct SyntaxTree;
} compiler_tree_native_SyntaxTree;

typedef nova_Nova_String* (*compiler_tree_node_Nova_Import_native_Nova_getClassLocation)(compiler_tree_node_Nova_Import*, nova_exception_Nova_ExceptionData*, char);
typedef compiler_tree_node_Nova_Import* (*compiler_tree_node_Nova_Import_native_Nova_tryParse)(compiler_tree_node_Nova_Import*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*, compiler_tree_node_Nova_Import*);
typedef compiler_tree_node_Nova_Import* (*compiler_tree_node_Nova_Import_native_Nova_construct)(compiler_tree_node_Nova_Import*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*);

typedef struct compiler_tree_node_native_Import
{
compiler_tree_node_Nova_Import_native_Nova_getClassLocation getClassLocation;
compiler_tree_node_Nova_Import_native_Nova_tryParse tryParse;
compiler_tree_node_Nova_Import_native_Nova_construct Import;
} compiler_tree_node_native_Import;

typedef compiler_tree_node_Nova_ImportList* (*compiler_tree_node_Nova_ImportList_native_Nova_construct)(compiler_tree_node_Nova_ImportList*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*);

typedef struct compiler_tree_node_native_ImportList
{
compiler_tree_node_Nova_ImportList_native_Nova_construct ImportList;
} compiler_tree_node_native_ImportList;


typedef struct compiler_tree_node_native_Listener
{
} compiler_tree_node_native_Listener;

typedef compiler_tree_node_Nova_Node* (*compiler_tree_node_Nova_Node_native_Nova_getAdjacentNode)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, int);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_addAnnotation)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_annotation_Nova_Annotation*);
typedef void (*compiler_tree_node_Nova_Node_native0_Nova_detach)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef char (*compiler_tree_node_Nova_Node_native_Nova_containsChild)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, char);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_onAdded)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_onRemoved)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*);
typedef compiler_tree_node_Nova_Node* (*compiler_tree_node_Nova_Node_native_Nova_addChild)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, int, compiler_tree_node_Nova_Node*, char);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_inheritChildren)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, char);
typedef compiler_tree_node_Nova_Node* (*compiler_tree_node_Nova_Node_native0_Nova_removeChild)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*);
typedef compiler_tree_node_Nova_Node* (*compiler_tree_node_Nova_Node_native1_Nova_removeChild)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, int);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_replaceWith)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_replace)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_tree_node_Nova_Node*, char);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_slaughterChildren)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef char (*compiler_tree_node_Nova_Node_native_Nova_onAfterDecoded)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_onStackPopped)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef char (*compiler_tree_node_Nova_Node_native_Nova_onNextStatementDecoded)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_followedByScope)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, char);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_onChildReplaced)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_tree_node_Nova_Node*);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_onReplaced)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_tree_node_Nova_Node*);
typedef compiler_tree_node_Nova_ValidationResult* (*compiler_tree_node_Nova_Node_native_Nova_validate)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, int);
typedef void (*compiler_tree_node_Nova_Node_native_Nova_rollback)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*);
typedef compiler_tree_node_Nova_Node* (*compiler_tree_node_Nova_Node_native_Nova_clone)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*, char);
typedef compiler_tree_node_Nova_Node* (*compiler_tree_node_Nova_Node_native_Nova_construct)(compiler_tree_node_Nova_Node*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*);

typedef struct compiler_tree_node_native_Node
{
compiler_tree_node_Nova_Node_native_Nova_getAdjacentNode getAdjacentNode;
compiler_tree_node_Nova_Node_native_Nova_addAnnotation addAnnotation;
compiler_tree_node_Nova_Node_native0_Nova_detach detach;
compiler_tree_node_Nova_Node_native_Nova_containsChild containsChild;
compiler_tree_node_Nova_Node_native_Nova_onAdded onAdded;
compiler_tree_node_Nova_Node_native_Nova_onRemoved onRemoved;
compiler_tree_node_Nova_Node_native_Nova_addChild addChild;
compiler_tree_node_Nova_Node_native_Nova_inheritChildren inheritChildren;
compiler_tree_node_Nova_Node_native0_Nova_removeChild removeChild__compiler_tree_node_Node;
compiler_tree_node_Nova_Node_native1_Nova_removeChild removeChild__nova_primitive_number_Int;
compiler_tree_node_Nova_Node_native_Nova_replaceWith replaceWith;
compiler_tree_node_Nova_Node_native_Nova_replace replace;
compiler_tree_node_Nova_Node_native_Nova_slaughterChildren slaughterChildren;
compiler_tree_node_Nova_Node_native_Nova_onAfterDecoded onAfterDecoded;
compiler_tree_node_Nova_Node_native_Nova_onStackPopped onStackPopped;
compiler_tree_node_Nova_Node_native_Nova_onNextStatementDecoded onNextStatementDecoded;
compiler_tree_node_Nova_Node_native_Nova_followedByScope followedByScope;
compiler_tree_node_Nova_Node_native_Nova_onChildReplaced onChildReplaced;
compiler_tree_node_Nova_Node_native_Nova_onReplaced onReplaced;
compiler_tree_node_Nova_Node_native_Nova_validate validate;
compiler_tree_node_Nova_Node_native_Nova_rollback rollback;
compiler_tree_node_Nova_Node_native_Nova_clone clone;
compiler_tree_node_Nova_Node_native_Nova_construct Node;
} compiler_tree_node_native_Node;

typedef compiler_tree_node_Nova_NodeList* (*compiler_tree_node_Nova_NodeList_native_Nova_construct)(compiler_tree_node_Nova_NodeList*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_tree_node_native_NodeList
{
compiler_tree_node_Nova_NodeList_native_Nova_construct NodeList;
} compiler_tree_node_native_NodeList;

typedef compiler_tree_node_Nova_NovaClass* (*compiler_tree_node_Nova_NovaClass_native_Nova_construct)(compiler_tree_node_Nova_NovaClass*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_tree_node_native_NovaClass
{
compiler_tree_node_Nova_NovaClass_native_Nova_construct NovaClass;
} compiler_tree_node_native_NovaClass;

typedef compiler_tree_node_Nova_Import* (*compiler_tree_node_Nova_NovaFile_native_Nova_addImport)(compiler_tree_node_Nova_NovaFile*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef char (*compiler_tree_node_Nova_NovaFile_native_Nova_containsImport)(compiler_tree_node_Nova_NovaFile*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, char, char);
typedef compiler_tree_node_Nova_NovaFile* (*compiler_tree_node_Nova_NovaFile_native_Nova_construct)(compiler_tree_node_Nova_NovaFile*, nova_exception_Nova_ExceptionData*, nova_io_Nova_File*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*);

typedef struct compiler_tree_node_native_NovaFile
{
compiler_tree_node_Nova_NovaFile_native_Nova_addImport addImport;
compiler_tree_node_Nova_NovaFile_native_Nova_containsImport containsImport;
compiler_tree_node_Nova_NovaFile_native_Nova_construct NovaFile;
} compiler_tree_node_native_NovaFile;

typedef compiler_tree_node_Nova_Package* (*compiler_tree_node_Nova_Package_native_static_Nova_generateDefaultPackage)(compiler_tree_node_Nova_Package*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*);
typedef compiler_tree_node_Nova_Package* (*compiler_tree_node_Nova_Package_native_Nova_construct)(compiler_tree_node_Nova_Package*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*);

typedef struct compiler_tree_node_native_Package
{
compiler_tree_node_Nova_Package_native_Nova_construct Package;
} compiler_tree_node_native_Package;

typedef compiler_tree_node_Nova_NovaClass* (*compiler_tree_node_Nova_Program_native_Nova_getClassDeclaration)(compiler_tree_node_Nova_Program*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef compiler_tree_node_Nova_Program* (*compiler_tree_node_Nova_Program_native_Nova_construct)(compiler_tree_node_Nova_Program*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_tree_node_native_Program
{
compiler_tree_node_Nova_Program_native_Nova_getClassDeclaration getClassDeclaration;
compiler_tree_node_Nova_Program_native_Nova_construct Program;
} compiler_tree_node_native_Program;

typedef compiler_tree_node_Nova_Scope* (*compiler_tree_node_Nova_Scope_native_Nova_clone)(compiler_tree_node_Nova_Scope*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*, char);
typedef compiler_tree_node_Nova_Scope* (*compiler_tree_node_Nova_Scope_native_Nova_construct)(compiler_tree_node_Nova_Scope*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_tree_node_native_Scope
{
compiler_tree_node_Nova_Scope_native_Nova_clone clone;
compiler_tree_node_Nova_Scope_native_Nova_construct Scope;
} compiler_tree_node_native_Scope;

typedef compiler_tree_node_Nova_ValidationResult* (*compiler_tree_node_Nova_ValidationResult_native_Nova_construct)(compiler_tree_node_Nova_ValidationResult*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*);

typedef struct compiler_tree_node_native_ValidationResult
{
compiler_tree_node_Nova_ValidationResult_native_Nova_construct ValidationResult;
} compiler_tree_node_native_ValidationResult;


typedef struct compiler_tree_node_annotation_native_Annotatable
{
} compiler_tree_node_annotation_native_Annotatable;

typedef compiler_tree_node_annotation_Nova_Annotation* (*compiler_tree_node_annotation_Nova_Annotation_native_Nova_clone)(compiler_tree_node_annotation_Nova_Annotation*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*, char);
typedef compiler_tree_node_annotation_Nova_Annotation* (*compiler_tree_node_annotation_Nova_Annotation_native_Nova_construct)(compiler_tree_node_annotation_Nova_Annotation*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_tree_node_annotation_native_Annotation
{
compiler_tree_node_annotation_Nova_Annotation_native_Nova_clone clone;
compiler_tree_node_annotation_Nova_Annotation_native_Nova_construct Annotation;
} compiler_tree_node_annotation_native_Annotation;

typedef compiler_tree_node_closures_Nova_ClosureContext* (*compiler_tree_node_closures_Nova_ClosureContext_native_Nova_construct)(compiler_tree_node_closures_Nova_ClosureContext*, nova_exception_Nova_ExceptionData*, compiler_tree_node_Nova_Node*, compiler_util_Nova_Location*);

typedef struct compiler_tree_node_closures_native_ClosureContext
{
compiler_tree_node_closures_Nova_ClosureContext_native_Nova_construct ClosureContext;
} compiler_tree_node_closures_native_ClosureContext;

typedef compiler_tree_node_closures_Nova_ClosureDeclaration* (*compiler_tree_node_closures_Nova_ClosureDeclaration_native_Nova_construct)(compiler_tree_node_closures_Nova_ClosureDeclaration*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_tree_node_closures_native_ClosureDeclaration
{
compiler_tree_node_closures_Nova_ClosureDeclaration_native_Nova_construct ClosureDeclaration;
} compiler_tree_node_closures_native_ClosureDeclaration;

typedef compiler_tree_node_exceptionhandling_Nova_ExceptionHandler* (*compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_native_Nova_construct)(compiler_tree_node_exceptionhandling_Nova_ExceptionHandler*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_tree_node_exceptionhandling_native_ExceptionHandler
{
compiler_tree_node_exceptionhandling_Nova_ExceptionHandler_native_Nova_construct ExceptionHandler;
} compiler_tree_node_exceptionhandling_native_ExceptionHandler;

typedef compiler_tree_node_exceptionhandling_Nova_Try* (*compiler_tree_node_exceptionhandling_Nova_Try_native_Nova_construct)(compiler_tree_node_exceptionhandling_Nova_Try*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_tree_node_exceptionhandling_native_Try
{
compiler_tree_node_exceptionhandling_Nova_Try_native_Nova_construct Try;
} compiler_tree_node_exceptionhandling_native_Try;

typedef compiler_tree_node_functions_Nova_NovaFunction* (*compiler_tree_node_functions_Nova_NovaFunction_native_Nova_construct)(compiler_tree_node_functions_Nova_NovaFunction*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_tree_node_functions_native_NovaFunction
{
compiler_tree_node_functions_Nova_NovaFunction_native_Nova_construct NovaFunction;
} compiler_tree_node_functions_native_NovaFunction;

typedef nova_Nova_String* (*compiler_util_Nova_FileUtils_native_static_Nova_formatPath)(compiler_util_Nova_FileUtils*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*compiler_util_Nova_FileUtils_native_static_Nova_formAbsolutePath)(compiler_util_Nova_FileUtils*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*compiler_util_Nova_FileUtils_native_static_Nova_escapeSpaces)(compiler_util_Nova_FileUtils*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*compiler_util_Nova_FileUtils_native_static_Nova_getWorkingDirectoryPath)(compiler_util_Nova_FileUtils*, nova_exception_Nova_ExceptionData*);
typedef compiler_util_Nova_FileUtils* (*compiler_util_Nova_FileUtils_native_Nova_construct)(compiler_util_Nova_FileUtils*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_util_native_FileUtils
{
compiler_util_Nova_FileUtils_native_Nova_construct FileUtils;
} compiler_util_native_FileUtils;

typedef void (*compiler_util_Nova_Location_native_Nova_setLineNumber)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int);
typedef int (*compiler_util_Nova_Location_native_Nova_getStart)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*);
typedef int (*compiler_util_Nova_Location_native_Nova_getEnd)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*);
typedef void (*compiler_util_Nova_Location_native_Nova_setOffset)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int);
typedef void (*compiler_util_Nova_Location_native_Nova_addOffset)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int);
typedef void (*compiler_util_Nova_Location_native_Nova_subtractOffset)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int);
typedef void (*compiler_util_Nova_Location_native0_Nova_setBounds)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Bounds*);
typedef void (*compiler_util_Nova_Location_native1_Nova_setBounds)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int, int);
typedef void (*compiler_util_Nova_Location_native0_Nova_addBounds)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int);
typedef void (*compiler_util_Nova_Location_native_Nova_moveBounds)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int, int);
typedef void (*compiler_util_Nova_Location_native0_Nova_subtractBounds)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int, int);
typedef void (*compiler_util_Nova_Location_native1_Nova_subtractBounds)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int);
typedef void (*compiler_util_Nova_Location_native1_Nova_addBounds)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int, int);
typedef char (*compiler_util_Nova_Location_native_Nova_isValid)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*);
typedef compiler_util_Nova_Location* (*compiler_util_Nova_Location_native_Nova_asNew)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*compiler_util_Nova_Location_native_Nova_toString)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*);
typedef compiler_util_Nova_Location* (*compiler_util_Nova_Location_native0_Nova_construct)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*);
typedef compiler_util_Nova_Location* (*compiler_util_Nova_Location_native1_Nova_construct)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, compiler_util_Nova_Location*);
typedef compiler_util_Nova_Location* (*compiler_util_Nova_Location_native2_Nova_construct)(compiler_util_Nova_Location*, nova_exception_Nova_ExceptionData*, int, int, int, int);

typedef struct compiler_util_native_Location
{
compiler_util_Nova_Location_native_Nova_setLineNumber setLineNumber;
compiler_util_Nova_Location_native_Nova_getStart getStart;
compiler_util_Nova_Location_native_Nova_getEnd getEnd;
compiler_util_Nova_Location_native_Nova_setOffset setOffset;
compiler_util_Nova_Location_native_Nova_addOffset addOffset;
compiler_util_Nova_Location_native_Nova_subtractOffset subtractOffset;
compiler_util_Nova_Location_native0_Nova_setBounds setBounds__nova_datastruct_Bounds;
compiler_util_Nova_Location_native1_Nova_setBounds setBounds__nova_primitive_number_Int__nova_primitive_number_Int;
compiler_util_Nova_Location_native0_Nova_addBounds addBounds__nova_primitive_number_Int;
compiler_util_Nova_Location_native_Nova_moveBounds moveBounds;
compiler_util_Nova_Location_native0_Nova_subtractBounds subtractBounds__nova_primitive_number_Int__nova_primitive_number_Int;
compiler_util_Nova_Location_native1_Nova_subtractBounds subtractBounds__nova_primitive_number_Int;
compiler_util_Nova_Location_native1_Nova_addBounds addBounds__nova_primitive_number_Int__nova_primitive_number_Int;
compiler_util_Nova_Location_native_Nova_isValid isValid;
compiler_util_Nova_Location_native_Nova_asNew asNew;
compiler_util_Nova_Location_native_Nova_toString toString;
compiler_util_Nova_Location_native0_Nova_construct Location;
compiler_util_Nova_Location_native1_Nova_construct Location__compiler_util_Location;
compiler_util_Nova_Location_native2_Nova_construct Location__nova_primitive_number_Int__nova_primitive_number_Int__nova_primitive_number_Int__nova_primitive_number_Int;
} compiler_util_native_Location;

typedef compiler_util_Nova_OS* (*compiler_util_Nova_OS_native_Nova_construct)(compiler_util_Nova_OS*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_util_native_OS
{
compiler_util_Nova_OS_native_Nova_construct OS;
} compiler_util_native_OS;

typedef char (*compiler_util_Nova_StringUtils_native_static_Nova_isSurroundedByQuotes)(compiler_util_Nova_StringUtils*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef nova_Nova_String* (*compiler_util_Nova_StringUtils_native_static_Nova_removeSurroundingQuotes)(compiler_util_Nova_StringUtils*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef compiler_util_Nova_StringUtils* (*compiler_util_Nova_StringUtils_native_Nova_construct)(compiler_util_Nova_StringUtils*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_util_native_StringUtils
{
compiler_util_Nova_StringUtils_native_Nova_construct StringUtils;
} compiler_util_native_StringUtils;

typedef compiler_util_Nova_SyntaxUtils* (*compiler_util_Nova_SyntaxUtils_native_Nova_construct)(compiler_util_Nova_SyntaxUtils*, nova_exception_Nova_ExceptionData*);

typedef struct compiler_util_native_SyntaxUtils
{
compiler_util_Nova_SyntaxUtils_native_Nova_construct SyntaxUtils;
} compiler_util_native_SyntaxUtils;

typedef int (*example_Nova_Animal_native_Nova_getNumLegs)(example_Nova_Animal*, nova_exception_Nova_ExceptionData*);
typedef int (*example_Nova_Animal_native_Nova_getNumEyes)(example_Nova_Animal*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*example_Nova_Animal_native_Nova_getDescription)(example_Nova_Animal*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*example_Nova_Animal_native_Nova_toString)(example_Nova_Animal*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_Animal* (*example_Nova_Animal_native_Nova_construct)(example_Nova_Animal*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Animal
{
example_Nova_Animal_native_Nova_getNumLegs getNumLegs;
example_Nova_Animal_native_Nova_getNumEyes getNumEyes;
example_Nova_Animal_native_Nova_getDescription getDescription;
example_Nova_Animal_native_Nova_toString toString;
example_Nova_Animal_native_Nova_construct Animal;
} example_native_Animal;

typedef void (*example_Nova_ArrayDemo_native_static_Nova_main)(example_Nova_ArrayDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_ArrayDemo* (*example_Nova_ArrayDemo_native_Nova_construct)(example_Nova_ArrayDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_ArrayDemo
{
example_Nova_ArrayDemo_native_Nova_construct ArrayDemo;
} example_native_ArrayDemo;

typedef void (*example_Nova_BodyBuilder_native_Nova_sayHello)(example_Nova_BodyBuilder*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_BodyBuilder* (*example_Nova_BodyBuilder_native_Nova_construct)(example_Nova_BodyBuilder*, nova_exception_Nova_ExceptionData*, int, nova_Nova_String*);

typedef struct example_native_BodyBuilder
{
example_Nova_BodyBuilder_native_Nova_sayHello sayHello;
example_Nova_BodyBuilder_native_Nova_construct BodyBuilder;
} example_native_BodyBuilder;

typedef void (*example_Nova_ClosureDemo_native_static_Nova_main)(example_Nova_ClosureDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_ClosureDemo* (*example_Nova_ClosureDemo_native_Nova_construct)(example_Nova_ClosureDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_ClosureDemo
{
example_Nova_ClosureDemo_native_Nova_construct ClosureDemo;
} example_native_ClosureDemo;

typedef int (*example_Nova_Dog_native_Nova_getNumLegs)(example_Nova_Dog*, nova_exception_Nova_ExceptionData*);
typedef int (*example_Nova_Dog_native_Nova_getNumEyes)(example_Nova_Dog*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*example_Nova_Dog_native_Nova_getDescription)(example_Nova_Dog*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_Dog* (*example_Nova_Dog_native_Nova_construct)(example_Nova_Dog*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Dog
{
example_Nova_Dog_native_Nova_getNumLegs getNumLegs;
example_Nova_Dog_native_Nova_getNumEyes getNumEyes;
example_Nova_Dog_native_Nova_getDescription getDescription;
example_Nova_Dog_native_Nova_construct Dog;
} example_native_Dog;

typedef void (*example_Nova_ExceptionHandlingDemo_native_static_Nova_main)(example_Nova_ExceptionHandlingDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_ExceptionHandlingDemo* (*example_Nova_ExceptionHandlingDemo_native_Nova_construct)(example_Nova_ExceptionHandlingDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_ExceptionHandlingDemo
{
example_Nova_ExceptionHandlingDemo_native_Nova_construct ExceptionHandlingDemo;
} example_native_ExceptionHandlingDemo;

typedef void (*example_Nova_FileTest_native_static_Nova_main)(example_Nova_FileTest*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_FileTest* (*example_Nova_FileTest_native_Nova_construct)(example_Nova_FileTest*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_FileTest
{
example_Nova_FileTest_native_Nova_construct FileTest;
} example_native_FileTest;

typedef void (*example_Nova_GenericDemo_native_static_Nova_main)(example_Nova_GenericDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_GenericDemo* (*example_Nova_GenericDemo_native_Nova_construct)(example_Nova_GenericDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_GenericDemo
{
example_Nova_GenericDemo_native_Nova_construct GenericDemo;
} example_native_GenericDemo;

typedef void (*example_Nova_HashMapDemo_native_static_Nova_main)(example_Nova_HashMapDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_HashMapDemo* (*example_Nova_HashMapDemo_native_Nova_construct)(example_Nova_HashMapDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_HashMapDemo
{
example_Nova_HashMapDemo_native_Nova_construct HashMapDemo;
} example_native_HashMapDemo;

typedef void (*example_Nova_HashSetDemo_native_static_Nova_main)(example_Nova_HashSetDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_HashSetDemo* (*example_Nova_HashSetDemo_native_Nova_construct)(example_Nova_HashSetDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_HashSetDemo
{
example_Nova_HashSetDemo_native_Nova_construct HashSetDemo;
} example_native_HashSetDemo;

typedef void (*example_Nova_IntegerTest_native_static_Nova_main)(example_Nova_IntegerTest*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_IntegerTest* (*example_Nova_IntegerTest_native_Nova_construct)(example_Nova_IntegerTest*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_IntegerTest
{
example_Nova_IntegerTest_native_Nova_construct IntegerTest;
} example_native_IntegerTest;

typedef void (*example_Nova_Lab_native_static_Nova_main)(example_Nova_Lab*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_Lab* (*example_Nova_Lab_native_Nova_construct)(example_Nova_Lab*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Lab
{
example_Nova_Lab_native_Nova_construct Lab;
} example_native_Lab;

typedef void (*example_Nova_MathDemo_native_static_Nova_main)(example_Nova_MathDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_MathDemo* (*example_Nova_MathDemo_native_Nova_construct)(example_Nova_MathDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_MathDemo
{
example_Nova_MathDemo_native_Nova_construct MathDemo;
} example_native_MathDemo;

typedef example_Nova_NonWholeDivisionException* (*example_Nova_NonWholeDivisionException_native_Nova_construct)(example_Nova_NonWholeDivisionException*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_NonWholeDivisionException
{
example_Nova_NonWholeDivisionException_native_Nova_construct NonWholeDivisionException;
} example_native_NonWholeDivisionException;

typedef void (*example_Nova_Person_native_Nova_sayHello)(example_Nova_Person*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_Person* (*example_Nova_Person_native_Nova_construct)(example_Nova_Person*, nova_exception_Nova_ExceptionData*, nova_Nova_String*, int);

typedef struct example_native_Person
{
example_Nova_Person_native_Nova_sayHello sayHello;
example_Nova_Person_native_Nova_construct Person;
} example_native_Person;

typedef int (*example_Nova_Polygon_native_Nova_numberSides)(example_Nova_Polygon*, nova_exception_Nova_ExceptionData*);
typedef double (*example_Nova_Polygon_native_Nova_calculateArea)(example_Nova_Polygon*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Polygon
{
example_Nova_Polygon_native_Nova_numberSides numberSides;
example_Nova_Polygon_native_Nova_calculateArea calculateArea;
} example_native_Polygon;

typedef void (*example_Nova_PolymorphismDemo_native_static_Nova_main)(example_Nova_PolymorphismDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_PolymorphismDemo* (*example_Nova_PolymorphismDemo_native_Nova_construct)(example_Nova_PolymorphismDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_PolymorphismDemo
{
example_Nova_PolymorphismDemo_native_Nova_construct PolymorphismDemo;
} example_native_PolymorphismDemo;

typedef void (*example_Nova_QueueDemo_native_static_Nova_main)(example_Nova_QueueDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_QueueDemo* (*example_Nova_QueueDemo_native_Nova_construct)(example_Nova_QueueDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_QueueDemo
{
example_Nova_QueueDemo_native_Nova_construct QueueDemo;
} example_native_QueueDemo;

typedef int (*example_Nova_Spider_native_Nova_getNumLegs)(example_Nova_Spider*, nova_exception_Nova_ExceptionData*);
typedef int (*example_Nova_Spider_native_Nova_getNumEyes)(example_Nova_Spider*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*example_Nova_Spider_native_Nova_getDescription)(example_Nova_Spider*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_Spider* (*example_Nova_Spider_native_Nova_construct)(example_Nova_Spider*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Spider
{
example_Nova_Spider_native_Nova_getNumLegs getNumLegs;
example_Nova_Spider_native_Nova_getNumEyes getNumEyes;
example_Nova_Spider_native_Nova_getDescription getDescription;
example_Nova_Spider_native_Nova_construct Spider;
} example_native_Spider;

typedef int (*example_Nova_Square_native_Nova_numberSides)(example_Nova_Square*, nova_exception_Nova_ExceptionData*);
typedef double (*example_Nova_Square_native_Nova_calculateArea)(example_Nova_Square*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_Square* (*example_Nova_Square_native_Nova_construct)(example_Nova_Square*, nova_exception_Nova_ExceptionData*, int);

typedef struct example_native_Square
{
example_Nova_Square_native_Nova_numberSides numberSides;
example_Nova_Square_native_Nova_calculateArea calculateArea;
example_Nova_Square_native_Nova_construct Square;
} example_native_Square;

typedef void (*example_Nova_SvgChart_native_static_Nova_main)(example_Nova_SvgChart*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_SvgChart* (*example_Nova_SvgChart_native_Nova_construct)(example_Nova_SvgChart*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_SvgChart
{
example_Nova_SvgChart_native_Nova_construct SvgChart;
} example_native_SvgChart;

typedef void (*example_Nova_SvgFractal_native_static_Nova_main)(example_Nova_SvgFractal*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_SvgFractal* (*example_Nova_SvgFractal_native_Nova_construct)(example_Nova_SvgFractal*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_SvgFractal
{
example_Nova_SvgFractal_native_Nova_construct SvgFractal;
} example_native_SvgFractal;

typedef example_Nova_T1* (*example_Nova_T1_native_Nova_construct)(example_Nova_T1*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_T1
{
example_Nova_T1_native_Nova_construct T1;
} example_native_T1;

typedef example_Nova_T2* (*example_Nova_T2_native_Nova_construct)(example_Nova_T2*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_T2
{
example_Nova_T2_native_Nova_construct T2;
} example_native_T2;

typedef void (*example_Nova_Test_native_static_Nova_main)(example_Nova_Test*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_Test* (*example_Nova_Test_native_Nova_construct)(example_Nova_Test*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_Test
{
example_Nova_Test_native_Nova_construct Test;
} example_native_Test;

typedef void (*example_Nova_ThreadDemo_native_static_Nova_main)(example_Nova_ThreadDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_Nova_ThreadDemo* (*example_Nova_ThreadDemo_native_Nova_construct)(example_Nova_ThreadDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_native_ThreadDemo
{
example_Nova_ThreadDemo_native_Nova_construct ThreadDemo;
} example_native_ThreadDemo;

typedef void (*example_Nova_ThreadDemoImplementation_native_Nova_run)(example_Nova_ThreadDemoImplementation*, nova_exception_Nova_ExceptionData*);
typedef example_Nova_ThreadDemoImplementation* (*example_Nova_ThreadDemoImplementation_native_Nova_construct)(example_Nova_ThreadDemoImplementation*, nova_exception_Nova_ExceptionData*, long_long, nova_Nova_String*);

typedef struct example_native_ThreadDemoImplementation
{
example_Nova_ThreadDemoImplementation_native_Nova_run run;
example_Nova_ThreadDemoImplementation_native_Nova_construct ThreadDemoImplementation;
} example_native_ThreadDemoImplementation;

typedef void (*example_ackermann_Nova_Ackermann_native_static_Nova_main)(example_ackermann_Nova_Ackermann*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef int (*example_ackermann_Nova_Ackermann_native_static_Nova_run)(example_ackermann_Nova_Ackermann*, nova_exception_Nova_ExceptionData*, int, int);
typedef int (*example_ackermann_Nova_Ackermann_native_static_Nova_run2)(example_ackermann_Nova_Ackermann*, nova_exception_Nova_ExceptionData*, int, int);
typedef example_ackermann_Nova_Ackermann* (*example_ackermann_Nova_Ackermann_native_Nova_construct)(example_ackermann_Nova_Ackermann*, nova_exception_Nova_ExceptionData*);

typedef struct example_ackermann_native_Ackermann
{
example_ackermann_Nova_Ackermann_native_Nova_construct Ackermann;
} example_ackermann_native_Ackermann;

typedef example_copy_Nova_Dog* (*example_copy_Nova_Dog_native_Nova_construct)(example_copy_Nova_Dog*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct example_copy_native_Dog
{
example_copy_Nova_Dog_native_Nova_construct Dog;
} example_copy_native_Dog;

typedef void (*example_database_Nova_DatabaseDemo_native_static_Nova_main)(example_database_Nova_DatabaseDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_database_Nova_DatabaseDemo* (*example_database_Nova_DatabaseDemo_native_Nova_construct)(example_database_Nova_DatabaseDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_database_native_DatabaseDemo
{
example_database_Nova_DatabaseDemo_native_Nova_construct DatabaseDemo;
} example_database_native_DatabaseDemo;

typedef void (*example_network_Nova_ClientDemo_native_static_Nova_main)(example_network_Nova_ClientDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_network_Nova_ClientDemo* (*example_network_Nova_ClientDemo_native_Nova_construct)(example_network_Nova_ClientDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_network_native_ClientDemo
{
example_network_Nova_ClientDemo_native_Nova_construct ClientDemo;
} example_network_native_ClientDemo;

typedef void (*example_network_Nova_ConnectionThread_native_Nova_run)(example_network_Nova_ConnectionThread*, nova_exception_Nova_ExceptionData*);
typedef example_network_Nova_ConnectionThread* (*example_network_Nova_ConnectionThread_native_Nova_construct)(example_network_Nova_ConnectionThread*, nova_exception_Nova_ExceptionData*, nova_network_Nova_ConnectionSocket*);

typedef struct example_network_native_ConnectionThread
{
example_network_Nova_ConnectionThread_native_Nova_run run;
example_network_Nova_ConnectionThread_native_Nova_construct ConnectionThread;
} example_network_native_ConnectionThread;

typedef void (*example_network_Nova_OutputThread_native_Nova_run)(example_network_Nova_OutputThread*, nova_exception_Nova_ExceptionData*);
typedef example_network_Nova_OutputThread* (*example_network_Nova_OutputThread_native_Nova_construct)(example_network_Nova_OutputThread*, nova_exception_Nova_ExceptionData*, nova_network_Nova_ServerSocket*, nova_network_Nova_ConnectionSocket*);

typedef struct example_network_native_OutputThread
{
example_network_Nova_OutputThread_native_Nova_run run;
example_network_Nova_OutputThread_native_Nova_construct OutputThread;
} example_network_native_OutputThread;

typedef void (*example_network_Nova_ServerDemo_native_static_Nova_main)(example_network_Nova_ServerDemo*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef example_network_Nova_ServerDemo* (*example_network_Nova_ServerDemo_native_Nova_construct)(example_network_Nova_ServerDemo*, nova_exception_Nova_ExceptionData*);

typedef struct example_network_native_ServerDemo
{
example_network_Nova_ServerDemo_native_Nova_construct ServerDemo;
} example_network_native_ServerDemo;

typedef void (*stabilitytest_Nova_AssignmentStability_native_Nova_test)(stabilitytest_Nova_AssignmentStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_AssignmentStability* (*stabilitytest_Nova_AssignmentStability_native_Nova_construct)(stabilitytest_Nova_AssignmentStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_AssignmentStability
{
stabilitytest_Nova_AssignmentStability_native_Nova_test test;
stabilitytest_Nova_AssignmentStability_native_Nova_construct AssignmentStability;
} stabilitytest_native_AssignmentStability;

typedef stabilitytest_Nova_ClassWithProperties* (*stabilitytest_Nova_ClassWithProperties_native_Nova_construct)(stabilitytest_Nova_ClassWithProperties*, nova_exception_Nova_ExceptionData*);

typedef struct stabilitytest_native_ClassWithProperties
{
stabilitytest_Nova_ClassWithProperties_native_Nova_construct ClassWithProperties;
} stabilitytest_native_ClassWithProperties;

typedef void (*stabilitytest_Nova_ClientThread_native_Nova_run)(stabilitytest_Nova_ClientThread*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ClientThread* (*stabilitytest_Nova_ClientThread_native_Nova_construct)(stabilitytest_Nova_ClientThread*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*, int);

typedef struct stabilitytest_native_ClientThread
{
stabilitytest_Nova_ClientThread_native_Nova_run run;
stabilitytest_Nova_ClientThread_native_Nova_construct ClientThread;
} stabilitytest_native_ClientThread;

typedef void (*stabilitytest_Nova_ClosureStability_native_Nova_test)(stabilitytest_Nova_ClosureStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ClosureStability* (*stabilitytest_Nova_ClosureStability_native_Nova_construct)(stabilitytest_Nova_ClosureStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_ClosureStability
{
stabilitytest_Nova_ClosureStability_native_Nova_test test;
stabilitytest_Nova_ClosureStability_native_Nova_construct ClosureStability;
} stabilitytest_native_ClosureStability;

typedef void (*stabilitytest_Nova_ExceptionStability_native_Nova_test)(stabilitytest_Nova_ExceptionStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ExceptionStability* (*stabilitytest_Nova_ExceptionStability_native_Nova_construct)(stabilitytest_Nova_ExceptionStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_ExceptionStability
{
stabilitytest_Nova_ExceptionStability_native_Nova_test test;
stabilitytest_Nova_ExceptionStability_native_Nova_construct ExceptionStability;
} stabilitytest_native_ExceptionStability;

typedef void (*stabilitytest_Nova_FileStability_native_Nova_test)(stabilitytest_Nova_FileStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_FileStability* (*stabilitytest_Nova_FileStability_native_Nova_construct)(stabilitytest_Nova_FileStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_FileStability
{
stabilitytest_Nova_FileStability_native_Nova_test test;
stabilitytest_Nova_FileStability_native_Nova_construct FileStability;
} stabilitytest_native_FileStability;

typedef void (*stabilitytest_Nova_LambdaStability_native_Nova_test)(stabilitytest_Nova_LambdaStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_LambdaStability* (*stabilitytest_Nova_LambdaStability_native_Nova_construct)(stabilitytest_Nova_LambdaStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_LambdaStability
{
stabilitytest_Nova_LambdaStability_native_Nova_test test;
stabilitytest_Nova_LambdaStability_native_Nova_construct LambdaStability;
} stabilitytest_native_LambdaStability;

typedef void (*stabilitytest_Nova_NetworkStability_native_Nova_test)(stabilitytest_Nova_NetworkStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_NetworkStability* (*stabilitytest_Nova_NetworkStability_native_Nova_construct)(stabilitytest_Nova_NetworkStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_NetworkStability
{
stabilitytest_Nova_NetworkStability_native_Nova_test test;
stabilitytest_Nova_NetworkStability_native_Nova_construct NetworkStability;
} stabilitytest_native_NetworkStability;

typedef nova_Nova_String* (*stabilitytest_Nova_PolymorphicSubClass_native_Nova_toString)(stabilitytest_Nova_PolymorphicSubClass*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_PolymorphicSubClass* (*stabilitytest_Nova_PolymorphicSubClass_native_Nova_construct)(stabilitytest_Nova_PolymorphicSubClass*, nova_exception_Nova_ExceptionData*);

typedef struct stabilitytest_native_PolymorphicSubClass
{
stabilitytest_Nova_PolymorphicSubClass_native_Nova_toString toString;
stabilitytest_Nova_PolymorphicSubClass_native_Nova_construct PolymorphicSubClass;
} stabilitytest_native_PolymorphicSubClass;

typedef void (*stabilitytest_Nova_PolymorphicSuperClass_native_Nova_giveBirth)(stabilitytest_Nova_PolymorphicSuperClass*, nova_exception_Nova_ExceptionData*);
typedef nova_Nova_String* (*stabilitytest_Nova_PolymorphicSuperClass_native_Nova_toString)(stabilitytest_Nova_PolymorphicSuperClass*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_PolymorphicSuperClass* (*stabilitytest_Nova_PolymorphicSuperClass_native_Nova_construct)(stabilitytest_Nova_PolymorphicSuperClass*, nova_exception_Nova_ExceptionData*);

typedef struct stabilitytest_native_PolymorphicSuperClass
{
stabilitytest_Nova_PolymorphicSuperClass_native_Nova_giveBirth giveBirth;
stabilitytest_Nova_PolymorphicSuperClass_native_Nova_toString toString;
stabilitytest_Nova_PolymorphicSuperClass_native_Nova_construct PolymorphicSuperClass;
} stabilitytest_native_PolymorphicSuperClass;

typedef void (*stabilitytest_Nova_PolymorphismStability_native_Nova_test)(stabilitytest_Nova_PolymorphismStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_PolymorphismStability* (*stabilitytest_Nova_PolymorphismStability_native_Nova_construct)(stabilitytest_Nova_PolymorphismStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_PolymorphismStability
{
stabilitytest_Nova_PolymorphismStability_native_Nova_test test;
stabilitytest_Nova_PolymorphismStability_native_Nova_construct PolymorphismStability;
} stabilitytest_native_PolymorphismStability;

typedef void (*stabilitytest_Nova_StabilityExceptionHandler_native_Nova_uncaughtException)(stabilitytest_Nova_StabilityExceptionHandler*, nova_exception_Nova_ExceptionData*, nova_thread_Nova_Thread*, nova_exception_Nova_Exception*);
typedef stabilitytest_Nova_StabilityExceptionHandler* (*stabilitytest_Nova_StabilityExceptionHandler_native_Nova_construct)(stabilitytest_Nova_StabilityExceptionHandler*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_StabilityExceptionHandler
{
stabilitytest_Nova_StabilityExceptionHandler_native_Nova_uncaughtException uncaughtException;
stabilitytest_Nova_StabilityExceptionHandler_native_Nova_construct StabilityExceptionHandler;
} stabilitytest_native_StabilityExceptionHandler;

typedef void (*stabilitytest_Nova_StabilityTest_native_static_Nova_main)(stabilitytest_Nova_StabilityTest*, nova_exception_Nova_ExceptionData*, nova_datastruct_list_Nova_Array*);
typedef void (*stabilitytest_Nova_StabilityTest_native_Nova_test)(stabilitytest_Nova_StabilityTest*, nova_exception_Nova_ExceptionData*);
typedef void (*stabilitytest_Nova_StabilityTest_native0_Nova_fail)(stabilitytest_Nova_StabilityTest*, nova_exception_Nova_ExceptionData*);
typedef void (*stabilitytest_Nova_StabilityTest_native1_Nova_fail)(stabilitytest_Nova_StabilityTest*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
typedef stabilitytest_Nova_StabilityTest* (*stabilitytest_Nova_StabilityTest_native_Nova_construct)(stabilitytest_Nova_StabilityTest*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_StabilityTest
{
stabilitytest_Nova_StabilityTest_native_Nova_test test;
stabilitytest_Nova_StabilityTest_native0_Nova_fail fail;
stabilitytest_Nova_StabilityTest_native1_Nova_fail fail__nova_String;
stabilitytest_Nova_StabilityTest_native_Nova_construct StabilityTest;
} stabilitytest_native_StabilityTest;

typedef void (*stabilitytest_Nova_StabilityTestCase_native_Nova_test)(stabilitytest_Nova_StabilityTestCase*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_StabilityTestCase* (*stabilitytest_Nova_StabilityTestCase_native_Nova_construct)(stabilitytest_Nova_StabilityTestCase*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_StabilityTestCase
{
stabilitytest_Nova_StabilityTestCase_native_Nova_test test;
stabilitytest_Nova_StabilityTestCase_native_Nova_construct StabilityTestCase;
} stabilitytest_native_StabilityTestCase;

typedef stabilitytest_Nova_StabilityTestException* (*stabilitytest_Nova_StabilityTestException_native_Nova_construct)(stabilitytest_Nova_StabilityTestException*, nova_exception_Nova_ExceptionData*);

typedef struct stabilitytest_native_StabilityTestException
{
stabilitytest_Nova_StabilityTestException_native_Nova_construct StabilityTestException;
} stabilitytest_native_StabilityTestException;

typedef void (*stabilitytest_Nova_SyntaxStability_native_Nova_test)(stabilitytest_Nova_SyntaxStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_SyntaxStability* (*stabilitytest_Nova_SyntaxStability_native_Nova_construct)(stabilitytest_Nova_SyntaxStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_SyntaxStability
{
stabilitytest_Nova_SyntaxStability_native_Nova_test test;
stabilitytest_Nova_SyntaxStability_native_Nova_construct SyntaxStability;
} stabilitytest_native_SyntaxStability;

typedef void (*stabilitytest_Nova_ThreadImplementation_native_Nova_run)(stabilitytest_Nova_ThreadImplementation*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ThreadImplementation* (*stabilitytest_Nova_ThreadImplementation_native_Nova_construct)(stabilitytest_Nova_ThreadImplementation*, nova_exception_Nova_ExceptionData*, int, int);

typedef struct stabilitytest_native_ThreadImplementation
{
stabilitytest_Nova_ThreadImplementation_native_Nova_run run;
stabilitytest_Nova_ThreadImplementation_native_Nova_construct ThreadImplementation;
} stabilitytest_native_ThreadImplementation;

typedef void (*stabilitytest_Nova_ThreadStability_native_Nova_test)(stabilitytest_Nova_ThreadStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ThreadStability* (*stabilitytest_Nova_ThreadStability_native_Nova_construct)(stabilitytest_Nova_ThreadStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_ThreadStability
{
stabilitytest_Nova_ThreadStability_native_Nova_test test;
stabilitytest_Nova_ThreadStability_native_Nova_construct ThreadStability;
} stabilitytest_native_ThreadStability;

typedef void (*stabilitytest_Nova_TimeStability_native_Nova_test)(stabilitytest_Nova_TimeStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_TimeStability* (*stabilitytest_Nova_TimeStability_native_Nova_construct)(stabilitytest_Nova_TimeStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_TimeStability
{
stabilitytest_Nova_TimeStability_native_Nova_test test;
stabilitytest_Nova_TimeStability_native_Nova_construct TimeStability;
} stabilitytest_native_TimeStability;

typedef void (*stabilitytest_Nova_ToStringStability_native_Nova_test)(stabilitytest_Nova_ToStringStability*, nova_exception_Nova_ExceptionData*);
typedef stabilitytest_Nova_ToStringStability* (*stabilitytest_Nova_ToStringStability_native_Nova_construct)(stabilitytest_Nova_ToStringStability*, nova_exception_Nova_ExceptionData*, stabilitytest_Nova_StabilityTest*);

typedef struct stabilitytest_native_ToStringStability
{
stabilitytest_Nova_ToStringStability_native_Nova_test test;
stabilitytest_Nova_ToStringStability_native_Nova_construct ToStringStability;
} stabilitytest_native_ToStringStability;

typedef stabilitytest_Nova_UnstableException* (*stabilitytest_Nova_UnstableException_native_Nova_construct)(stabilitytest_Nova_UnstableException*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);

typedef struct stabilitytest_native_UnstableException
{
stabilitytest_Nova_UnstableException_native_Nova_construct UnstableException;
} stabilitytest_native_UnstableException;


typedef struct nova_env
{
nova_native_Class nova_Class;
nova_native_Object nova_Object;
nova_native_String nova_String;
nova_native_System nova_System;
nova_ar_native_ImageTracker nova_ar_ImageTracker;
nova_ar_native_TrackPoint nova_ar_TrackPoint;
nova_database_native_DBConnector nova_database_DBConnector;
nova_database_native_ResultSet nova_database_ResultSet;
nova_datastruct_native_BinaryNode nova_datastruct_BinaryNode;
nova_datastruct_native_BinaryTree nova_datastruct_BinaryTree;
nova_datastruct_native_Bounds nova_datastruct_Bounds;
nova_datastruct_native_Comparable nova_datastruct_Comparable;
nova_datastruct_native_HashMap nova_datastruct_HashMap;
nova_datastruct_native_HashSet nova_datastruct_HashSet;
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
nova_datastruct_list_native_StringCharArray nova_datastruct_list_StringCharArray;
nova_exception_native_DivideByZeroException nova_exception_DivideByZeroException;
nova_exception_native_Exception nova_exception_Exception;
nova_exception_native_ExceptionData nova_exception_ExceptionData;
nova_exception_native_InvalidArgumentException nova_exception_InvalidArgumentException;
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
nova_math_native_Vector nova_math_Vector;
nova_math_native_Vector2D nova_math_Vector2D;
nova_math_native_Vector3D nova_math_Vector3D;
nova_math_native_Vector4D nova_math_Vector4D;
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
nova_star_native_Frame nova_star_Frame;
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
compiler_native_Compiler compiler_Compiler;
compiler_native_SyntaxErrorException compiler_SyntaxErrorException;
compiler_error_native_UnimplementedOperationException compiler_error_UnimplementedOperationException;
compiler_tree_native_SyntaxTree compiler_tree_SyntaxTree;
compiler_tree_node_native_Import compiler_tree_node_Import;
compiler_tree_node_native_ImportList compiler_tree_node_ImportList;
compiler_tree_node_native_Listener compiler_tree_node_Listener;
compiler_tree_node_native_Node compiler_tree_node_Node;
compiler_tree_node_native_NodeList compiler_tree_node_NodeList;
compiler_tree_node_native_NovaClass compiler_tree_node_NovaClass;
compiler_tree_node_native_NovaFile compiler_tree_node_NovaFile;
compiler_tree_node_native_Package compiler_tree_node_Package;
compiler_tree_node_native_Program compiler_tree_node_Program;
compiler_tree_node_native_Scope compiler_tree_node_Scope;
compiler_tree_node_native_ValidationResult compiler_tree_node_ValidationResult;
compiler_tree_node_annotation_native_Annotatable compiler_tree_node_annotation_Annotatable;
compiler_tree_node_annotation_native_Annotation compiler_tree_node_annotation_Annotation;
compiler_tree_node_closures_native_ClosureContext compiler_tree_node_closures_ClosureContext;
compiler_tree_node_closures_native_ClosureDeclaration compiler_tree_node_closures_ClosureDeclaration;
compiler_tree_node_exceptionhandling_native_ExceptionHandler compiler_tree_node_exceptionhandling_ExceptionHandler;
compiler_tree_node_exceptionhandling_native_Try compiler_tree_node_exceptionhandling_Try;
compiler_tree_node_functions_native_NovaFunction compiler_tree_node_functions_NovaFunction;
compiler_util_native_FileUtils compiler_util_FileUtils;
compiler_util_native_Location compiler_util_Location;
compiler_util_native_OS compiler_util_OS;
compiler_util_native_StringUtils compiler_util_StringUtils;
compiler_util_native_SyntaxUtils compiler_util_SyntaxUtils;
example_native_Animal example_Animal;
example_native_ArrayDemo example_ArrayDemo;
example_native_BodyBuilder example_BodyBuilder;
example_native_ClosureDemo example_ClosureDemo;
example_native_Dog example_Dog;
example_native_ExceptionHandlingDemo example_ExceptionHandlingDemo;
example_native_FileTest example_FileTest;
example_native_GenericDemo example_GenericDemo;
example_native_HashMapDemo example_HashMapDemo;
example_native_HashSetDemo example_HashSetDemo;
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
