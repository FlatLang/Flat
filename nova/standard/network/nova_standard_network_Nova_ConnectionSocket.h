#pragma once
#ifndef FILE_nova_standard_network_Nova_ConnectionSocket_NOVA
#define FILE_nova_standard_network_Nova_ConnectionSocket_NOVA

typedef struct nova_standard_network_Nova_ConnectionSocket nova_standard_network_Nova_ConnectionSocket;

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
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Array.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>
#include <nova/standard/io/nova_standard_io_Nova_InputStream.h>
#include <nova/standard/io/nova_standard_io_Nova_OutputStream.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Queue.h>
#include <nova/standard/network/nova_standard_network_Nova_Socket.h>
#include <nova/standard/network/nova_standard_network_Nova_NetworkInputStream.h>
#include <nova/standard/network/nova_standard_network_Nova_NetworkOutputStream.h>


typedef struct nova_standard_network_Extension_VTable_ConnectionSocket nova_standard_network_Extension_VTable_ConnectionSocket;
struct nova_standard_network_Extension_VTable_ConnectionSocket
{
	nova_Interface_VTable itable;
	long (*nova_standard_Nova_Object_virtual3_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual0_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_Nova_Object_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
};

extern nova_standard_network_Extension_VTable_ConnectionSocket nova_standard_network_Extension_VTable_ConnectionSocket_val;


CCLASS_CLASS
(
	nova_standard_network_Nova_ConnectionSocket, 
	
	nova_standard_network_Extension_VTable_ConnectionSocket* vtable;
	nova_standard_Nova_String* nova_standard_network_Nova_Socket_Nova_ip;
	int nova_standard_network_Nova_Socket_Nova_port;
	char nova_standard_network_Nova_ConnectionSocket_Nova_connected;
	nova_standard_io_Nova_InputStream* nova_standard_network_Nova_ConnectionSocket_Nova_in;
	nova_standard_io_Nova_OutputStream* nova_standard_network_Nova_ConnectionSocket_Nova_out;
	struct Private* prv;
)

void nova_standard_network_Nova_ConnectionSocketNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_network_Nova_ConnectionSocket* nova_standard_network_Nova_ConnectionSocket_Nova_construct(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData, SOCKET_ID_TYPE l0_Nova_socket);
void nova_standard_network_Nova_ConnectionSocket_Nova_destroy(nova_standard_network_Nova_ConnectionSocket** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_network_Nova_ConnectionSocket_Nova_this(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData, SOCKET_ID_TYPE l0_Nova_socket);
void nova_standard_network_Nova_ConnectionSocket_Nova_close(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
char nova_standard_network_Nova_ConnectionSocket_Nova_validateConnection(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_String* nova_standard_network_Nova_ConnectionSocket_0_Nova_readString(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
char nova_standard_network_Nova_ConnectionSocket_Nova_write(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_data);
void nova_standard_network_Nova_ConnectionSocket_2_Nova_super(nova_standard_network_Nova_ConnectionSocket* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
