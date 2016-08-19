#pragma once
#ifndef FILE_stabilitytest_Nova_ClientThread_NOVA
#define FILE_stabilitytest_Nova_ClientThread_NOVA

typedef struct stabilitytest_Nova_ClientThread stabilitytest_Nova_ClientThread;


#include <Nova.h>
#include <ExceptionHandler.h>
#include <InterfaceVTable.h>
#include <nova/standard/exception/nova_standard_exception_Nova_ExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_Nova_Exception.h>
#include <nova/standard/exception/nova_standard_exception_Nova_DivideByZeroException.h>
#include <nova/standard/io/nova_standard_io_Nova_Console.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Number.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Byte.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Short.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Int.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Long.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Float.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Double.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Null.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Char.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Bool.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_Array.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntArray.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_CharArray.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_DoubleArray.h>
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntRange.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>
#include <nova/standard/network/nova_standard_network_Nova_ClientSocket.h>
#include <nova/standard/thread/nova_standard_thread_Nova_Thread.h>
#include <stabilitytest/stabilitytest_Nova_NetworkStability.h>
#include <stabilitytest/stabilitytest_Nova_StabilityTest.h>


typedef struct stabilitytest_Extension_VTable_ClientThread stabilitytest_Extension_VTable_ClientThread;
struct stabilitytest_Extension_VTable_ClientThread
{
	nova_Interface_VTable itable;
	long_long (*nova_standard_Nova_Object_virtual1_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual1_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_operators_Nova_Equals_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
	void (*nova_standard_thread_Nova_Thread_virtual0_Nova_run)(stabilitytest_Nova_ClientThread*, nova_standard_exception_Nova_ExceptionData*);
};

extern stabilitytest_Extension_VTable_ClientThread stabilitytest_Extension_VTable_ClientThread_val;


CCLASS_CLASS
(
	stabilitytest_Nova_ClientThread, 
	
	stabilitytest_Extension_VTable_ClientThread* vtable;
	struct Private* prv;
)

void stabilitytest_Nova_ClientThreadNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
stabilitytest_Nova_ClientThread* stabilitytest_Nova_ClientThread_Nova_ClientThread(stabilitytest_Nova_ClientThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ClientThread_Nova_program, int stabilitytest_Nova_ClientThread_Nova_port);
void stabilitytest_Nova_ClientThread_Nova_destroy(stabilitytest_Nova_ClientThread** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ClientThread_Nova_this(stabilitytest_Nova_ClientThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ClientThread_Nova_program, int stabilitytest_Nova_ClientThread_Nova_port);
void stabilitytest_Nova_ClientThread_0_Nova_run(stabilitytest_Nova_ClientThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ClientThread_0_Nova_super(stabilitytest_Nova_ClientThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
