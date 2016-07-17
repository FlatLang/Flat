#pragma once
#ifndef FILE_example_network_Nova_ConnectionThread_NOVA
#define FILE_example_network_Nova_ConnectionThread_NOVA

typedef struct example_network_Nova_ConnectionThread example_network_Nova_ConnectionThread;


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
#include <nova/standard/datastruct/list/nova_standard_datastruct_list_Nova_IntRange.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>
#include <nova/standard/thread/nova_standard_thread_Nova_Thread.h>
#include <nova/standard/network/nova_standard_network_Nova_ConnectionSocket.h>


typedef struct example_network_Extension_VTable_ConnectionThread example_network_Extension_VTable_ConnectionThread;
struct example_network_Extension_VTable_ConnectionThread
{
	nova_Interface_VTable itable;
	long_long (*nova_standard_Nova_Object_virtual1_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual1_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_operators_Nova_Equals_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
	void (*nova_standard_thread_Nova_Thread_virtual0_Nova_run)(example_network_Nova_ConnectionThread*, nova_standard_exception_Nova_ExceptionData*);
};

extern example_network_Extension_VTable_ConnectionThread example_network_Extension_VTable_ConnectionThread_val;


CCLASS_CLASS
(
	example_network_Nova_ConnectionThread, 
	
	example_network_Extension_VTable_ConnectionThread* vtable;
	struct Private* prv;
)

void example_network_Nova_ConnectionThreadNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
example_network_Nova_ConnectionThread* example_network_Nova_ConnectionThread_Nova_ConnectionThread(example_network_Nova_ConnectionThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_network_Nova_ConnectionSocket* example_network_Nova_ConnectionThread_Nova_socket);
void example_network_Nova_ConnectionThread_Nova_destroy(example_network_Nova_ConnectionThread** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_network_Nova_ConnectionThread_Nova_this(example_network_Nova_ConnectionThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_network_Nova_ConnectionSocket* example_network_Nova_ConnectionThread_Nova_socket);
void example_network_Nova_ConnectionThread_0_Nova_run(example_network_Nova_ConnectionThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_network_Nova_ConnectionThread_0_Nova_super(example_network_Nova_ConnectionThread* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
