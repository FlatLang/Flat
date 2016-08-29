#pragma once
#ifndef FILE_nova_network_Nova_NetworkInputStream_NOVA
#define FILE_nova_network_Nova_NetworkInputStream_NOVA

typedef struct nova_network_Nova_NetworkInputStream nova_network_Nova_NetworkInputStream;


#include <Nova.h>
#include <ExceptionHandler.h>
#include <InterfaceVTable.h>
#include <nova/exception/nova_exception_Nova_ExceptionData.h>
#include <nova/exception/nova_exception_Nova_Exception.h>
#include <nova/exception/nova_exception_Nova_DivideByZeroException.h>
#include <nova/io/nova_io_Nova_Console.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Number.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Byte.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Short.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Int.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Long.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Float.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Double.h>
#include <nova/primitive/nova_primitive_Nova_Null.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Char.h>
#include <nova/primitive/nova_primitive_Nova_Bool.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Array.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_CharArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_DoubleArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntRange.h>
#include <nova/thread/nova_thread_Nova_Thread.h>
#include <nova/thread/async/nova_thread_async_Nova_Async.h>
#include <nova/gc/nova_gc_Nova_GC.h>
#include <nova/math/nova_math_Nova_Math.h>
#include <nova/nova_Nova_Object.h>
#include <nova/nova_Nova_String.h>
#include <nova/nova_Nova_System.h>
#include <nova/nova_Nova_Class.h>
#include <nova/network/NativeSocket.h>
#include <nova/io/nova_io_Nova_InputStream.h>
#include <nova/network/nova_network_Nova_ConnectionSocket.h>


typedef struct nova_network_Extension_VTable_NetworkInputStream nova_network_Extension_VTable_NetworkInputStream;
struct nova_network_Extension_VTable_NetworkInputStream
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_io_Nova_InputStream_virtual1_Nova_readString)(nova_network_Nova_NetworkInputStream*, nova_exception_Nova_ExceptionData*);
	nova_datastruct_list_Nova_Array* (*nova_io_Nova_InputStream_virtual1_Nova_readBytes)(nova_network_Nova_NetworkInputStream*, nova_exception_Nova_ExceptionData*);
};

extern nova_network_Extension_VTable_NetworkInputStream nova_network_Extension_VTable_NetworkInputStream_val;


CCLASS_CLASS
(
	nova_network_Nova_NetworkInputStream, 
	
	nova_network_Extension_VTable_NetworkInputStream* vtable;
	struct Private* prv;
)

void nova_network_Nova_NetworkInputStream_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_network_Nova_NetworkInputStream* nova_network_Nova_NetworkInputStream_Nova_construct(nova_network_Nova_NetworkInputStream* this, nova_exception_Nova_ExceptionData* exceptionData, nova_network_Nova_ConnectionSocket* nova_network_Nova_NetworkInputStream_Nova_socket);
void nova_network_Nova_NetworkInputStream_Nova_destroy(nova_network_Nova_NetworkInputStream** this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_network_Nova_NetworkInputStream_Nova_this(nova_network_Nova_NetworkInputStream* this, nova_exception_Nova_ExceptionData* exceptionData, nova_network_Nova_ConnectionSocket* nova_network_Nova_NetworkInputStream_Nova_socket);
nova_Nova_String* nova_network_Nova_NetworkInputStream_0_Nova_readString(nova_network_Nova_NetworkInputStream* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_list_Nova_Array* nova_network_Nova_NetworkInputStream_0_Nova_readBytes(nova_network_Nova_NetworkInputStream* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_network_Nova_NetworkInputStream_Nova_super(nova_network_Nova_NetworkInputStream* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
