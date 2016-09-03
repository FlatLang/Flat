#include <precompiled.h>
#include "NovaNativeInterface.h"

nova_env novaEnv = {
{
&nova_Nova_Class_Nova_construct,
},

{
0,
0,
&nova_Nova_Object_Nova_construct,
},

{
0,
&nova_Nova_String_Nova_equals,
&nova_Nova_String_Nova_replace,
&nova_Nova_String_Nova_startsWith,
&nova_Nova_String_Nova_endsWith,
&nova_Nova_String_Nova_contains,
&nova_Nova_String_1_Nova_indexOf,
&nova_Nova_String_2_Nova_indexOf,
&nova_Nova_String_Nova_lastIndexOf,
&nova_Nova_String_0_Nova_substring,
&nova_Nova_String_1_Nova_substring,
&nova_Nova_String_Nova_lastChar,
&nova_Nova_String_Nova_charAt,
&nova_Nova_String_Nova_trim,
&nova_Nova_String_Nova_toLowerCase,
&nova_Nova_String_Nova_toUpperCase,
&nova_Nova_String_Nova_capitalize,
&nova_Nova_String_Nova_transform,
&nova_Nova_String_0_Nova_getStringBetween,
&nova_Nova_String_1_Nova_getStringBetween,
&nova_Nova_String_0_Nova_surroundWith,
&nova_Nova_String_1_Nova_surroundWith,
&nova_Nova_String_Nova_compareTo,
0,
&nova_Nova_String_0_Nova_construct,
&nova_Nova_String_1_Nova_construct,
&nova_Nova_String_2_Nova_construct,
},

{
&nova_Nova_System_0_Nova_exit,
&nova_Nova_System_1_Nova_exit,
&nova_Nova_System_2_Nova_exit,
&nova_Nova_System_Nova_execute,
&nova_Nova_System_Nova_construct,
},

{
&nova_database_Nova_DBConnector_0_Nova_connect,
&nova_database_Nova_DBConnector_1_Nova_connect,
&nova_database_Nova_DBConnector_2_Nova_connect,
&nova_database_Nova_DBConnector_Nova_updateError,
&nova_database_Nova_DBConnector_Nova_changeUser,
&nova_database_Nova_DBConnector_Nova_query,
&nova_database_Nova_DBConnector_Nova_close,
&nova_database_Nova_DBConnector_Nova_construct,
},

{
&nova_database_Nova_ResultSet_Nova_construct,
},

{
&nova_datastruct_Nova_BinaryNode_Nova_addChild,
&nova_datastruct_Nova_BinaryNode_0_Nova_construct,
&nova_datastruct_Nova_BinaryNode_1_Nova_construct,
},

{
&nova_datastruct_Nova_BinaryTree_Nova_addNode,
&nova_datastruct_Nova_BinaryTree_Nova_addNodes,
&nova_datastruct_Nova_BinaryTree_Nova_construct,
},

{
&nova_datastruct_Nova_Bounds_Nova_extractString,
&nova_datastruct_Nova_Bounds_Nova_extractPreString,
&nova_datastruct_Nova_Bounds_Nova_extractPostString,
&nova_datastruct_Nova_Bounds_Nova_trimString,
&nova_datastruct_Nova_Bounds_Nova_invalidate,
&nova_datastruct_Nova_Bounds_0_Nova_equals,
&nova_datastruct_Nova_Bounds_0_Nova_toString,
&nova_datastruct_Nova_Bounds_Nova_cloneTo,
&nova_datastruct_Nova_Bounds_Nova_clone,
&nova_datastruct_Nova_Bounds_0_Nova_construct,
&nova_datastruct_Nova_Bounds_1_Nova_construct,
},

{
0,
},

{
&nova_datastruct_Nova_HashMap_Nova_toArray,
&nova_datastruct_Nova_HashMap_Nova_contains,
&nova_datastruct_Nova_HashMap_Nova_any,
&nova_datastruct_Nova_HashMap_Nova_all,
&nova_datastruct_Nova_HashMap_Nova_map,
&nova_datastruct_Nova_HashMap_Nova_filter,
&nova_datastruct_Nova_HashMap_Nova_join,
&nova_datastruct_Nova_HashMap_Nova_skip,
&nova_datastruct_Nova_HashMap_Nova_take,
&nova_datastruct_Nova_HashMap_Nova_reverse,
&nova_datastruct_Nova_HashMap_Nova_firstWhere,
&nova_datastruct_Nova_HashMap_Nova_forEach,
0,
&nova_datastruct_Nova_HashMap_Nova_get,
&nova_datastruct_Nova_HashMap_Nova_remove,
&nova_datastruct_Nova_HashMap_Nova_containsKey,
&nova_datastruct_Nova_HashMap_0_Nova_construct,
&nova_datastruct_Nova_HashMap_1_Nova_construct,
},

{
&nova_datastruct_Nova_HashSet_0_Nova_toArray,
&nova_datastruct_Nova_HashSet_0_Nova_any,
&nova_datastruct_Nova_HashSet_0_Nova_all,
&nova_datastruct_Nova_HashSet_0_Nova_map,
&nova_datastruct_Nova_HashSet_0_Nova_filter,
&nova_datastruct_Nova_HashSet_0_Nova_join,
&nova_datastruct_Nova_HashSet_0_Nova_skip,
&nova_datastruct_Nova_HashSet_0_Nova_take,
&nova_datastruct_Nova_HashSet_0_Nova_reverse,
&nova_datastruct_Nova_HashSet_0_Nova_firstWhere,
&nova_datastruct_Nova_HashSet_0_Nova_forEach,
&nova_datastruct_Nova_HashSet_Nova_add,
&nova_datastruct_Nova_HashSet_Nova_get,
&nova_datastruct_Nova_HashSet_Nova_remove,
&nova_datastruct_Nova_HashSet_0_Nova_contains,
&nova_datastruct_Nova_HashSet_0_Nova_toString,
&nova_datastruct_Nova_HashSet_0_Nova_construct,
&nova_datastruct_Nova_HashSet_1_Nova_construct,
},

{
&nova_datastruct_Nova_Node_0_Nova_preorder,
&nova_datastruct_Nova_Node_0_Nova_inorder,
&nova_datastruct_Nova_Node_0_Nova_postorder,
&nova_datastruct_Nova_Node_0_Nova_levelorder,
&nova_datastruct_Nova_Node_0_Nova_toString,
&nova_datastruct_Nova_Node_0_Nova_construct,
&nova_datastruct_Nova_Node_1_Nova_construct,
&nova_datastruct_Nova_Node_2_Nova_construct,
&nova_datastruct_Nova_Node_3_Nova_construct,
},

{
&nova_datastruct_Nova_Pair_0_Nova_toString,
&nova_datastruct_Nova_Pair_Nova_construct,
},

{
&nova_datastruct_Nova_ReversibleHashMap_Nova_put,
&nova_datastruct_Nova_ReversibleHashMap_Nova_getKey,
&nova_datastruct_Nova_ReversibleHashMap_Nova_getValue,
&nova_datastruct_Nova_ReversibleHashMap_Nova_construct,
},

{
&nova_datastruct_Nova_Tree_Nova_preorder,
&nova_datastruct_Nova_Tree_Nova_inorder,
&nova_datastruct_Nova_Tree_Nova_postorder,
&nova_datastruct_Nova_Tree_Nova_levelorder,
&nova_datastruct_Nova_Tree_Nova_construct,
},

{
&nova_datastruct_Nova_Vector_Nova_construct,
},

{
&nova_datastruct_Nova_Vector2D_Nova_construct,
},

{
&nova_datastruct_list_Nova_Array_Nova_fillRemaining,
&nova_datastruct_list_Nova_Array_Nova_addAll,
&nova_datastruct_list_Nova_Array_0_Nova_add,
&nova_datastruct_list_Nova_Array_1_Nova_add,
&nova_datastruct_list_Nova_Array_0_Nova_remove,
&nova_datastruct_list_Nova_Array_1_Nova_remove,
&nova_datastruct_list_Nova_Array_Nova_indexOf,
&nova_datastruct_list_Nova_Array_Nova_swap,
0,
0,
&nova_datastruct_list_Nova_Array_0_Nova_contains,
&nova_datastruct_list_Nova_Array_0_Nova_toArray,
0,
0,
0,
0,
0,
0,
0,
0,
&nova_datastruct_list_Nova_Array_Nova_sumSize,
0,
0,
&nova_datastruct_list_Nova_Array_0_Nova_toString,
&nova_datastruct_list_Nova_Array_0_Nova_construct,
&nova_datastruct_list_Nova_Array_1_Nova_construct,
&nova_datastruct_list_Nova_Array_2_Nova_construct,
},

{
&nova_datastruct_list_Nova_ArrayIterator_Nova_reset,
&nova_datastruct_list_Nova_ArrayIterator_Nova_construct,
},

{
&nova_datastruct_list_Nova_CharArray_Nova_get,
&nova_datastruct_list_Nova_CharArray_Nova_set,
&nova_datastruct_list_Nova_CharArray_0_Nova_map,
&nova_datastruct_list_Nova_CharArray_0_Nova_forEach,
&nova_datastruct_list_Nova_CharArray_0_Nova_any,
&nova_datastruct_list_Nova_CharArray_0_Nova_all,
&nova_datastruct_list_Nova_CharArray_0_Nova_filter,
&nova_datastruct_list_Nova_CharArray_0_Nova_take,
&nova_datastruct_list_Nova_CharArray_0_Nova_skip,
&nova_datastruct_list_Nova_CharArray_0_Nova_firstWhere,
&nova_datastruct_list_Nova_CharArray_0_Nova_reverse,
&nova_datastruct_list_Nova_CharArray_0_Nova_construct,
&nova_datastruct_list_Nova_CharArray_1_Nova_construct,
&nova_datastruct_list_Nova_CharArray_2_Nova_construct,
},

{
&nova_datastruct_list_Nova_CharArrayIterator_0_Nova_reset,
&nova_datastruct_list_Nova_CharArrayIterator_Nova_construct,
},

{
&nova_datastruct_list_Nova_CompiledList_Nova_construct,
},

{
&nova_datastruct_list_Nova_DoubleArray_0_Nova_map,
&nova_datastruct_list_Nova_DoubleArray_0_Nova_forEach,
&nova_datastruct_list_Nova_DoubleArray_0_Nova_any,
&nova_datastruct_list_Nova_DoubleArray_0_Nova_all,
&nova_datastruct_list_Nova_DoubleArray_0_Nova_filter,
&nova_datastruct_list_Nova_DoubleArray_0_Nova_take,
&nova_datastruct_list_Nova_DoubleArray_0_Nova_skip,
&nova_datastruct_list_Nova_DoubleArray_0_Nova_firstWhere,
&nova_datastruct_list_Nova_DoubleArray_0_Nova_reverse,
&nova_datastruct_list_Nova_DoubleArray_0_Nova_join,
&nova_datastruct_list_Nova_DoubleArray_0_Nova_construct,
&nova_datastruct_list_Nova_DoubleArray_1_Nova_construct,
&nova_datastruct_list_Nova_DoubleArray_2_Nova_construct,
},

{
&nova_datastruct_list_Nova_DoubleArrayIterator_0_Nova_reset,
&nova_datastruct_list_Nova_DoubleArrayIterator_Nova_construct,
},

{
&nova_datastruct_list_Nova_EmptyStackException_0_Nova_construct,
&nova_datastruct_list_Nova_EmptyStackException_1_Nova_construct,
},

{
&nova_datastruct_list_Nova_IntArray_0_Nova_map,
&nova_datastruct_list_Nova_IntArray_0_Nova_forEach,
&nova_datastruct_list_Nova_IntArray_0_Nova_any,
&nova_datastruct_list_Nova_IntArray_0_Nova_all,
&nova_datastruct_list_Nova_IntArray_0_Nova_filter,
&nova_datastruct_list_Nova_IntArray_0_Nova_take,
&nova_datastruct_list_Nova_IntArray_0_Nova_skip,
&nova_datastruct_list_Nova_IntArray_0_Nova_firstWhere,
&nova_datastruct_list_Nova_IntArray_0_Nova_reverse,
&nova_datastruct_list_Nova_IntArray_0_Nova_join,
&nova_datastruct_list_Nova_IntArray_0_Nova_construct,
&nova_datastruct_list_Nova_IntArray_1_Nova_construct,
&nova_datastruct_list_Nova_IntArray_2_Nova_construct,
},

{
&nova_datastruct_list_Nova_IntArrayIterator_0_Nova_reset,
&nova_datastruct_list_Nova_IntArrayIterator_Nova_construct,
},

{
&nova_datastruct_list_Nova_IntRange_0_Nova_contains,
&nova_datastruct_list_Nova_IntRange_0_Nova_toArray,
&nova_datastruct_list_Nova_IntRange_0_Nova_forEach,
&nova_datastruct_list_Nova_IntRange_0_Nova_map,
&nova_datastruct_list_Nova_IntRange_0_Nova_any,
&nova_datastruct_list_Nova_IntRange_0_Nova_all,
&nova_datastruct_list_Nova_IntRange_0_Nova_filter,
&nova_datastruct_list_Nova_IntRange_0_Nova_take,
&nova_datastruct_list_Nova_IntRange_0_Nova_skip,
&nova_datastruct_list_Nova_IntRange_0_Nova_firstWhere,
&nova_datastruct_list_Nova_IntRange_0_Nova_reverse,
&nova_datastruct_list_Nova_IntRange_0_Nova_join,
&nova_datastruct_list_Nova_IntRange_0_Nova_toString,
&nova_datastruct_list_Nova_IntRange_0_Nova_construct,
&nova_datastruct_list_Nova_IntRange_1_Nova_construct,
},

{
&nova_datastruct_list_Nova_IntRangeIterator_0_Nova_reset,
&nova_datastruct_list_Nova_IntRangeIterator_Nova_construct,
},

{
},

{
0,
},

{
&nova_datastruct_list_Nova_LinkedList_Nova_addAll,
&nova_datastruct_list_Nova_LinkedList_Nova_add,
&nova_datastruct_list_Nova_LinkedList_Nova_remove,
&nova_datastruct_list_Nova_LinkedList_0_Nova_contains,
&nova_datastruct_list_Nova_LinkedList_0_Nova_toArray,
&nova_datastruct_list_Nova_LinkedList_0_Nova_map,
&nova_datastruct_list_Nova_LinkedList_0_Nova_forEach,
&nova_datastruct_list_Nova_LinkedList_0_Nova_any,
&nova_datastruct_list_Nova_LinkedList_0_Nova_all,
&nova_datastruct_list_Nova_LinkedList_0_Nova_filter,
&nova_datastruct_list_Nova_LinkedList_0_Nova_take,
&nova_datastruct_list_Nova_LinkedList_0_Nova_skip,
&nova_datastruct_list_Nova_LinkedList_0_Nova_firstWhere,
&nova_datastruct_list_Nova_LinkedList_0_Nova_reverse,
&nova_datastruct_list_Nova_LinkedList_0_Nova_join,
&nova_datastruct_list_Nova_LinkedList_Nova_construct,
},

{
&nova_datastruct_list_Nova_LinkedListIterator_0_Nova_reset,
&nova_datastruct_list_Nova_LinkedListIterator_Nova_construct,
},

{
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

{
&nova_datastruct_list_Nova_ListNode_Nova_clone,
&nova_datastruct_list_Nova_ListNode_Nova_construct,
},

{
&nova_datastruct_list_Nova_NoSuchElementException_0_Nova_construct,
&nova_datastruct_list_Nova_NoSuchElementException_1_Nova_construct,
},

{
&nova_datastruct_list_Nova_Queue_Nova_dequeue,
&nova_datastruct_list_Nova_Queue_Nova_enqueue,
&nova_datastruct_list_Nova_Queue_0_Nova_toString,
&nova_datastruct_list_Nova_Queue_0_Nova_construct,
&nova_datastruct_list_Nova_Queue_1_Nova_construct,
},

{
&nova_datastruct_list_Nova_Stack_Nova_push,
&nova_datastruct_list_Nova_Stack_Nova_pop,
&nova_datastruct_list_Nova_Stack_Nova_construct,
},

{
&nova_exception_Nova_DivideByZeroException_Nova_construct,
},

{
&nova_exception_Nova_Exception_0_Nova_construct,
&nova_exception_Nova_Exception_1_Nova_construct,
},

{
&nova_exception_Nova_ExceptionData_Nova_addCode,
&nova_exception_Nova_ExceptionData_Nova_jumpToBuffer,
&nova_exception_Nova_ExceptionData_Nova_construct,
},

{
&nova_exception_Nova_UnimplementedOperationException_Nova_construct,
},

{
&nova_gc_Nova_GC_Nova_init,
&nova_gc_Nova_GC_Nova_collect,
&nova_gc_Nova_GC_Nova_enableIncremental,
&nova_gc_Nova_GC_Nova_dump,
&nova_gc_Nova_GC_Nova_construct,
},

{
&nova_io_Nova_Console_0_Nova_writeLine,
&nova_io_Nova_Console_1_Nova_writeLine,
&nova_io_Nova_Console_2_Nova_writeLine,
&nova_io_Nova_Console_3_Nova_writeLine,
&nova_io_Nova_Console_4_Nova_writeLine,
&nova_io_Nova_Console_5_Nova_writeLine,
&nova_io_Nova_Console_6_Nova_writeLine,
&nova_io_Nova_Console_7_Nova_writeLine,
&nova_io_Nova_Console_8_Nova_writeLine,
&nova_io_Nova_Console_9_Nova_writeLine,
&nova_io_Nova_Console_0_Nova_write,
&nova_io_Nova_Console_1_Nova_write,
&nova_io_Nova_Console_2_Nova_write,
&nova_io_Nova_Console_3_Nova_write,
&nova_io_Nova_Console_4_Nova_write,
&nova_io_Nova_Console_5_Nova_write,
&nova_io_Nova_Console_6_Nova_write,
&nova_io_Nova_Console_7_Nova_write,
&nova_io_Nova_Console_8_Nova_write,
&nova_io_Nova_Console_Nova_readInt,
&nova_io_Nova_Console_Nova_readDouble,
&nova_io_Nova_Console_Nova_readChar,
&nova_io_Nova_Console_Nova_readLine,
&nova_io_Nova_Console_Nova_readPassword,
&nova_io_Nova_Console_Nova_setEcho,
&nova_io_Nova_Console_Nova_clearScreen,
&nova_io_Nova_Console_Nova_waitForEnter,
&nova_io_Nova_Console_Nova_construct,
},

{
&nova_io_Nova_File_Nova_delete,
&nova_io_Nova_File_Nova_reopen,
&nova_io_Nova_File_Nova_rewind,
&nova_io_Nova_File_Nova_clearContents,
&nova_io_Nova_File_Nova_create,
&nova_io_Nova_File_Nova_readAllContents,
&nova_io_Nova_File_Nova_readLine,
&nova_io_Nova_File_Nova_writeLine,
&nova_io_Nova_File_Nova_write,
&nova_io_Nova_File_Nova_flush,
&nova_io_Nova_File_Nova_close,
&nova_io_Nova_File_0_Nova_construct,
&nova_io_Nova_File_1_Nova_construct,
},

{
&nova_io_Nova_FileNotFoundException_Nova_construct,
},

{
0,
0,
},

{
0,
0,
&nova_io_Nova_OutputStream_Nova_construct,
},

{
&nova_io_Nova_StreamReader_Nova_readBytes,
&nova_io_Nova_StreamReader_Nova_readString,
&nova_io_Nova_StreamReader_Nova_construct,
},

{
&nova_math_Nova_ArithmeticSequence_Nova_construct,
},

{
&nova_math_Nova_Diekstra_Nova_construct,
},

{
&nova_math_Nova_GeometricSequence_Nova_construct,
},

{
&nova_math_Nova_Graph_Nova_construct,
},

{
&nova_math_Nova_InvalidNumericStatementException_Nova_construct,
},

{
&nova_math_Nova_Math_Nova_max,
&nova_math_Nova_Math_Nova_min,
&nova_math_Nova_Math_Nova_sign,
&nova_math_Nova_Math_Nova_random,
&nova_math_Nova_Math_0_Nova_abs,
&nova_math_Nova_Math_1_Nova_abs,
&nova_math_Nova_Math_Nova_sqrt,
&nova_math_Nova_Math_Nova_pow,
&nova_math_Nova_Math_Nova_sin,
&nova_math_Nova_Math_Nova_cos,
&nova_math_Nova_Math_Nova_tan,
&nova_math_Nova_Math_Nova_asin,
&nova_math_Nova_Math_Nova_acos,
&nova_math_Nova_Math_Nova_atan,
&nova_math_Nova_Math_Nova_round,
&nova_math_Nova_Math_Nova_floor,
&nova_math_Nova_Math_Nova_ceil,
&nova_math_Nova_Math_Nova_construct,
},

{
&nova_math_Nova_Matrix_Nova_sum,
&nova_math_Nova_Matrix_Nova_construct,
},

{
0,
&nova_math_Nova_NumericOperand_Nova_construct,
},

{
&nova_math_Nova_NumericOperation_0_Nova_toString,
&nova_math_Nova_NumericOperation_0_Nova_construct,
&nova_math_Nova_NumericOperation_1_Nova_construct,
},

{
&nova_math_Nova_NumericStatement_0_Nova_toString,
&nova_math_Nova_NumericStatement_Nova_construct,
},

{
&nova_math_Nova_NumericTree_0_Nova_toString,
&nova_math_Nova_NumericTree_Nova_construct,
},

{
&nova_math_Nova_Polynomial_Nova_construct,
},

{
&nova_math_Nova_Sequence_Nova_sum,
&nova_math_Nova_Sequence_Nova_construct,
},

{
&nova_math_Nova_Statement_Nova_construct,
},

{
&nova_math_Nova_StatementComponent_0_Nova_toString,
&nova_math_Nova_StatementComponent_Nova_construct,
},

{
&nova_math_Nova_VariableOperand_Nova_construct,
},

{
&nova_math_calculus_Nova_Calculus_Nova_derivative,
&nova_math_calculus_Nova_Calculus_Nova_construct,
},

{
&nova_math_huffman_Nova_HuffmanTree_Nova_construct,
},

{
&nova_math_logic_Nova_Conclusion_Nova_construct,
},

{
&nova_math_logic_Nova_Hypothesis_Nova_construct,
},

{
&nova_math_logic_Nova_InvalidFormulaException_Nova_construct,
},

{
&nova_math_logic_Nova_LogicalConnective_Nova_construct,
},

{
&nova_math_logic_Nova_LogicalStatement_0_Nova_toString,
&nova_math_logic_Nova_LogicalStatement_Nova_construct,
},

{
&nova_math_logic_Nova_StatementComponent_Nova_construct,
},

{
&nova_math_logic_Nova_StatementGroup_Nova_construct,
},

{
&nova_math_logic_Nova_StatementLetter_Nova_construct,
},

{
&nova_math_logic_Nova_WFF_Nova_construct,
},

{
&nova_network_Nova_ClientSocket_Nova_connect,
&nova_network_Nova_ClientSocket_Nova_close,
&nova_network_Nova_ClientSocket_Nova_construct,
},

{
&nova_network_Nova_ConnectionSocket_Nova_close,
&nova_network_Nova_ConnectionSocket_Nova_validateConnection,
&nova_network_Nova_ConnectionSocket_0_Nova_readString,
&nova_network_Nova_ConnectionSocket_Nova_write,
&nova_network_Nova_ConnectionSocket_Nova_construct,
},

{
&nova_network_Nova_NetworkInputStream_0_Nova_readString,
&nova_network_Nova_NetworkInputStream_0_Nova_readBytes,
&nova_network_Nova_NetworkInputStream_Nova_construct,
},

{
&nova_network_Nova_NetworkOutputStream_0_Nova_write,
&nova_network_Nova_NetworkOutputStream_1_Nova_write,
&nova_network_Nova_NetworkOutputStream_Nova_construct,
},

{
&nova_network_Nova_ServerSocket_Nova_start,
&nova_network_Nova_ServerSocket_Nova_close,
&nova_network_Nova_ServerSocket_Nova_acceptClient,
&nova_network_Nova_ServerSocket_Nova_construct,
},

{
&nova_network_Nova_Socket_Nova_construct,
},

{
0,
},

{
0,
},

{
&nova_primitive_Nova_Bool_2_Nova_toString,
&nova_primitive_Nova_Bool_3_Nova_toString,
&nova_primitive_Nova_Bool_0_Nova_compareTo,
&nova_primitive_Nova_Bool_Nova_construct,
},

{
&nova_primitive_Nova_Null_Nova_toString,
&nova_primitive_Nova_Null_Nova_concat,
&nova_primitive_Nova_Null_Nova_construct,
},

{
&nova_primitive_Nova_Primitive_Nova_construct,
},

{
&nova_primitive_number_Nova_Byte_Nova_numDigits,
&nova_primitive_number_Nova_Byte_2_Nova_toString,
&nova_primitive_number_Nova_Byte_3_Nova_toString,
&nova_primitive_number_Nova_Byte_0_Nova_compareTo,
&nova_primitive_number_Nova_Byte_Nova_multiply,
&nova_primitive_number_Nova_Byte_Nova_construct,
},

{
&nova_primitive_number_Nova_Char_2_Nova_toString,
&nova_primitive_number_Nova_Char_3_Nova_toString,
&nova_primitive_number_Nova_Char_0_Nova_toLowerCase,
&nova_primitive_number_Nova_Char_0_Nova_toUpperCase,
&nova_primitive_number_Nova_Char_1_Nova_toLowerCase,
&nova_primitive_number_Nova_Char_1_Nova_toUpperCase,
&nova_primitive_number_Nova_Char_0_Nova_compareTo,
&nova_primitive_number_Nova_Char_0_Nova_multiply,
&nova_primitive_number_Nova_Char_Nova_construct,
},

{
&nova_primitive_number_Nova_Double_0_Nova_numDigits,
&nova_primitive_number_Nova_Double_Nova_genString,
&nova_primitive_number_Nova_Double_Nova_genBuffer,
&nova_primitive_number_Nova_Double_Nova_repetition,
&nova_primitive_number_Nova_Double_Nova_lastSignificantDigit,
&nova_primitive_number_Nova_Double_2_Nova_toString,
&nova_primitive_number_Nova_Double_3_Nova_toString,
&nova_primitive_number_Nova_Double_Nova_parseDouble,
&nova_primitive_number_Nova_Double_0_Nova_compareTo,
&nova_primitive_number_Nova_Double_0_Nova_multiply,
&nova_primitive_number_Nova_Double_Nova_construct,
},

{
&nova_primitive_number_Nova_Float_0_Nova_numDigits,
&nova_primitive_number_Nova_Float_2_Nova_toString,
&nova_primitive_number_Nova_Float_3_Nova_toString,
&nova_primitive_number_Nova_Float_0_Nova_compareTo,
&nova_primitive_number_Nova_Float_0_Nova_multiply,
&nova_primitive_number_Nova_Float_Nova_construct,
},

{
&nova_primitive_number_Nova_Int_Nova_getHashCodeLong,
&nova_primitive_number_Nova_Int_0_Nova_numDigits,
&nova_primitive_number_Nova_Int_2_Nova_toString,
&nova_primitive_number_Nova_Int_3_Nova_toString,
&nova_primitive_number_Nova_Int_Nova_parseInt,
&nova_primitive_number_Nova_Int_0_Nova_compareTo,
&nova_primitive_number_Nova_Int_0_Nova_multiply,
&nova_primitive_number_Nova_Int_Nova_construct,
},

{
},

{
&nova_primitive_number_Nova_Long_0_Nova_numDigits,
&nova_primitive_number_Nova_Long_2_Nova_toString,
&nova_primitive_number_Nova_Long_3_Nova_toString,
&nova_primitive_number_Nova_Long_0_Nova_compareTo,
&nova_primitive_number_Nova_Long_0_Nova_multiply,
&nova_primitive_number_Nova_Long_Nova_construct,
},

{
0,
&nova_primitive_number_Nova_Number_Nova_construct,
},

{
},

{
&nova_primitive_number_Nova_Short_0_Nova_numDigits,
&nova_primitive_number_Nova_Short_2_Nova_toString,
&nova_primitive_number_Nova_Short_3_Nova_toString,
&nova_primitive_number_Nova_Short_0_Nova_compareTo,
&nova_primitive_number_Nova_Short_0_Nova_multiply,
&nova_primitive_number_Nova_Short_Nova_construct,
},

{
&nova_process_Nova_Process_Nova_construct,
},

{
&nova_security_Nova_MD5_Nova_encrypt,
&nova_security_Nova_MD5_Nova_construct,
},

{
&nova_star_Nova_Window_Nova_create,
&nova_star_Nova_Window_Nova_construct,
},

{
&nova_star_Nova_WindowThread_Nova_run,
&nova_star_Nova_WindowThread_Nova_construct,
},

{
&nova_svg_Nova_Svg_Nova_generateOutput,
&nova_svg_Nova_Svg_Nova_generateHTMLOutput,
&nova_svg_Nova_Svg_Nova_construct,
},

{
&nova_svg_Nova_SvgCircle_Nova_generateOutput,
&nova_svg_Nova_SvgCircle_0_Nova_toString,
&nova_svg_Nova_SvgCircle_Nova_construct,
},

{
0,
&nova_svg_Nova_SvgComponent_Nova_construct,
},

{
&nova_svg_Nova_SvgComponentList_Nova_generateOutput,
&nova_svg_Nova_SvgComponentList_Nova_addChild,
&nova_svg_Nova_SvgComponentList_Nova_construct,
},

{
&nova_svg_Nova_SvgComponentNode_Nova_construct,
},

{
&nova_svg_Nova_SvgMainComponent_0_Nova_generateOutput,
&nova_svg_Nova_SvgMainComponent_Nova_construct,
},

{
&nova_svg_no3_Nova_No3_Nova_select,
&nova_svg_no3_Nova_No3_Nova_construct,
},

{
0,
&nova_svg_no3_Nova_No3Node_Nova_construct,
},

{
&nova_svg_no3_Nova_No3Selection_Nova_toJs,
&nova_svg_no3_Nova_No3Selection_Nova_construct,
},

{
&nova_thread_Nova_Thread_Nova_start,
&nova_thread_Nova_Thread_Nova_join,
&nova_thread_Nova_Thread_Nova_kill,
&nova_thread_Nova_Thread_Nova_sleep,
0,
&nova_thread_Nova_Thread_Nova_construct,
},

{
0,
&nova_thread_Nova_UncaughtExceptionHandler_Nova_construct,
},

{
&nova_thread_async_Nova_Async_Nova_execute,
&nova_thread_async_Nova_Async_Nova_construct,
},

{
&nova_thread_async_Nova_AsyncResult_Nova_construct,
},

{
&nova_time_Nova_Date_Nova_decodeDate,
&nova_time_Nova_Date_Nova_updateTime,
&nova_time_Nova_Date_0_Nova_formatDate,
&nova_time_Nova_Date_1_Nova_formatDate,
&nova_time_Nova_Date_Nova_construct,
},

{
&nova_time_Nova_Time_Nova_construct,
},

{
&nova_time_Nova_Timer_Nova_start,
&nova_time_Nova_Timer_Nova_stop,
&nova_time_Nova_Timer_Nova_reset,
&nova_time_Nova_Timer_0_Nova_toString,
&nova_time_Nova_Timer_Nova_construct,
},

{
&nova_web_js_json_Nova_Json_0_Nova_toString,
&nova_web_js_json_Nova_Json_Nova_construct,
},

{
&nova_web_svg_Nova_Svg_Nova_generateOutput,
&nova_web_svg_Nova_Svg_Nova_generateHTMLOutput,
&nova_web_svg_Nova_Svg_Nova_construct,
},

{
&nova_web_svg_Nova_SvgCircle_Nova_generateOutput,
&nova_web_svg_Nova_SvgCircle_0_Nova_toString,
&nova_web_svg_Nova_SvgCircle_Nova_construct,
},

{
0,
&nova_web_svg_Nova_SvgComponent_Nova_construct,
},

{
&nova_web_svg_Nova_SvgComponentList_Nova_generateOutput,
&nova_web_svg_Nova_SvgComponentList_Nova_addChild,
&nova_web_svg_Nova_SvgComponentList_Nova_construct,
},

{
&nova_web_svg_Nova_SvgComponentNode_Nova_construct,
},

{
&nova_web_svg_Nova_SvgMainComponent_0_Nova_generateOutput,
&nova_web_svg_Nova_SvgMainComponent_Nova_construct,
},

{
&nova_web_svg_no3_Nova_No3_Nova_select,
&nova_web_svg_no3_Nova_No3_Nova_selectAll,
&nova_web_svg_no3_Nova_No3_Nova_construct,
},

{
0,
&nova_web_svg_no3_Nova_No3Node_Nova_construct,
},

{
&nova_web_svg_no3_Nova_No3Select_Nova_toJs,
&nova_web_svg_no3_Nova_No3Select_Nova_construct,
},

{
&nova_web_svg_no3_Nova_No3SelectAll_0_Nova_toJs,
&nova_web_svg_no3_Nova_No3SelectAll_Nova_construct,
},

{
0,
0,
0,
&example_Nova_Animal_0_Nova_toString,
&example_Nova_Animal_Nova_construct,
},

{
&example_Nova_ArrayDemo_Nova_main,
&example_Nova_ArrayDemo_Nova_construct,
},

{
&example_Nova_BodyBuilder_Nova_sayHello,
&example_Nova_BodyBuilder_Nova_construct,
},

{
&example_Nova_ClosureDemo_Nova_main,
&example_Nova_ClosureDemo_Nova_construct,
},

{
&example_Nova_Dog_Nova_getNumLegs,
&example_Nova_Dog_Nova_getNumEyes,
&example_Nova_Dog_Nova_getDescription,
&example_Nova_Dog_Nova_construct,
},

{
&example_Nova_ExceptionHandlingDemo_Nova_main,
&example_Nova_ExceptionHandlingDemo_Nova_construct,
},

{
&example_Nova_FileTest_Nova_main,
&example_Nova_FileTest_Nova_construct,
},

{
&example_Nova_GenericDemo_Nova_main,
&example_Nova_GenericDemo_Nova_construct,
},

{
&example_Nova_HashMapDemo_Nova_main,
&example_Nova_HashMapDemo_Nova_construct,
},

{
&example_Nova_HashSetDemo_Nova_main,
&example_Nova_HashSetDemo_Nova_construct,
},

{
&example_Nova_IntegerTest_Nova_main,
&example_Nova_IntegerTest_Nova_construct,
},

{
&example_Nova_Lab_Nova_main,
&example_Nova_Lab_Nova_construct,
},

{
&example_Nova_MathDemo_Nova_main,
&example_Nova_MathDemo_Nova_construct,
},

{
&example_Nova_NonWholeDivisionException_Nova_construct,
},

{
0,
&example_Nova_Person_Nova_construct,
},

{
0,
0,
},

{
&example_Nova_PolymorphismDemo_Nova_main,
&example_Nova_PolymorphismDemo_Nova_construct,
},

{
&example_Nova_QueueDemo_Nova_main,
&example_Nova_QueueDemo_Nova_construct,
},

{
&example_Nova_Spider_0_Nova_getNumLegs,
&example_Nova_Spider_0_Nova_getNumEyes,
&example_Nova_Spider_0_Nova_getDescription,
&example_Nova_Spider_Nova_construct,
},

{
&example_Nova_Square_Nova_numberSides,
&example_Nova_Square_Nova_calculateArea,
&example_Nova_Square_Nova_construct,
},

{
&example_Nova_SvgChart_Nova_main,
&example_Nova_SvgChart_Nova_construct,
},

{
&example_Nova_SvgFractal_Nova_main,
&example_Nova_SvgFractal_Nova_construct,
},

{
&example_Nova_T1_Nova_construct,
},

{
&example_Nova_T2_Nova_construct,
},

{
&example_Nova_Test_Nova_main,
&example_Nova_Test_Nova_construct,
},

{
&example_Nova_ThreadDemo_Nova_main,
&example_Nova_ThreadDemo_Nova_construct,
},

{
&example_Nova_ThreadDemoImplementation_0_Nova_run,
&example_Nova_ThreadDemoImplementation_Nova_construct,
},

{
&example_ackermann_Nova_Ackermann_Nova_main,
&example_ackermann_Nova_Ackermann_Nova_run,
&example_ackermann_Nova_Ackermann_Nova_run2,
&example_ackermann_Nova_Ackermann_Nova_construct,
},

{
&example_copy_Nova_Dog_Nova_construct,
},

{
&example_database_Nova_DatabaseDemo_Nova_main,
&example_database_Nova_DatabaseDemo_Nova_construct,
},

{
&example_network_Nova_ClientDemo_Nova_main,
&example_network_Nova_ClientDemo_Nova_construct,
},

{
&example_network_Nova_ConnectionThread_0_Nova_run,
&example_network_Nova_ConnectionThread_Nova_construct,
},

{
&example_network_Nova_OutputThread_0_Nova_run,
&example_network_Nova_OutputThread_Nova_construct,
},

{
&example_network_Nova_ServerDemo_Nova_main,
&example_network_Nova_ServerDemo_Nova_construct,
},

{
&stabilitytest_Nova_AssignmentStability_Nova_test,
&stabilitytest_Nova_AssignmentStability_Nova_construct,
},

{
&stabilitytest_Nova_ClassWithProperties_Nova_construct,
},

{
&stabilitytest_Nova_ClientThread_0_Nova_run,
&stabilitytest_Nova_ClientThread_Nova_construct,
},

{
&stabilitytest_Nova_ClosureStability_0_Nova_test,
&stabilitytest_Nova_ClosureStability_Nova_construct,
},

{
&stabilitytest_Nova_ExceptionStability_0_Nova_test,
&stabilitytest_Nova_ExceptionStability_Nova_construct,
},

{
&stabilitytest_Nova_FileStability_0_Nova_test,
&stabilitytest_Nova_FileStability_Nova_construct,
},

{
&stabilitytest_Nova_LambdaStability_0_Nova_test,
&stabilitytest_Nova_LambdaStability_Nova_construct,
},

{
&stabilitytest_Nova_NetworkStability_0_Nova_test,
&stabilitytest_Nova_NetworkStability_Nova_construct,
},

{
&stabilitytest_Nova_PolymorphicSubClass_Nova_toString,
&stabilitytest_Nova_PolymorphicSubClass_Nova_construct,
},

{
&stabilitytest_Nova_PolymorphicSuperClass_Nova_giveBirth,
0,
&stabilitytest_Nova_PolymorphicSuperClass_Nova_construct,
},

{
&stabilitytest_Nova_PolymorphismStability_0_Nova_test,
&stabilitytest_Nova_PolymorphismStability_Nova_construct,
},

{
&stabilitytest_Nova_StabilityExceptionHandler_Nova_uncaughtException,
&stabilitytest_Nova_StabilityExceptionHandler_Nova_construct,
},

{
&stabilitytest_Nova_StabilityTest_Nova_main,
&stabilitytest_Nova_StabilityTest_0_Nova_test,
&stabilitytest_Nova_StabilityTest_0_Nova_fail,
&stabilitytest_Nova_StabilityTest_1_Nova_fail,
&stabilitytest_Nova_StabilityTest_Nova_construct,
},

{
0,
&stabilitytest_Nova_StabilityTestCase_Nova_construct,
},

{
&stabilitytest_Nova_StabilityTestException_Nova_construct,
},

{
&stabilitytest_Nova_SyntaxStability_0_Nova_test,
&stabilitytest_Nova_SyntaxStability_Nova_construct,
},

{
&stabilitytest_Nova_ThreadImplementation_0_Nova_run,
&stabilitytest_Nova_ThreadImplementation_Nova_construct,
},

{
&stabilitytest_Nova_ThreadStability_0_Nova_test,
&stabilitytest_Nova_ThreadStability_Nova_construct,
},

{
&stabilitytest_Nova_TimeStability_0_Nova_test,
&stabilitytest_Nova_TimeStability_Nova_construct,
},

{
&stabilitytest_Nova_ToStringStability_0_Nova_test,
&stabilitytest_Nova_ToStringStability_Nova_construct,
},

{
&stabilitytest_Nova_UnstableException_Nova_construct,
},

};
