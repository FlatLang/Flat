#pragma once
#ifndef FILE_nova_database_Nova_DBConnector_NOVA
#define FILE_nova_database_Nova_DBConnector_NOVA

typedef struct nova_database_Nova_DBConnector nova_database_Nova_DBConnector;


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
#include <nova/database/NativeDBConnector.h>
#include <nova/database/nova_database_Nova_ResultSet.h>


typedef struct nova_database_Extension_VTable_DBConnector nova_database_Extension_VTable_DBConnector;
struct nova_database_Extension_VTable_DBConnector
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
};

extern nova_database_Extension_VTable_DBConnector nova_database_Extension_VTable_DBConnector_val;


CCLASS_CLASS
(
	nova_database_Nova_DBConnector, 
	
	nova_database_Extension_VTable_DBConnector* vtable;
	nova_Nova_String* nova_database_Nova_DBConnector_Nova_error;
	struct Private* prv;
)

void nova_database_Nova_DBConnector_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_database_Nova_DBConnector* nova_database_Nova_DBConnector_Nova_construct(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_database_Nova_DBConnector_Nova_destroy(nova_database_Nova_DBConnector** this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_database_Nova_DBConnector_0_Nova_this(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_database_Nova_DBConnector_0_Nova_connect(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_database_Nova_DBConnector_Nova_host, nova_Nova_String* nova_database_Nova_DBConnector_Nova_user, nova_Nova_String* nova_database_Nova_DBConnector_Nova_password);
void nova_database_Nova_DBConnector_1_Nova_connect(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_database_Nova_DBConnector_Nova_host, nova_Nova_String* nova_database_Nova_DBConnector_Nova_user, nova_Nova_String* nova_database_Nova_DBConnector_Nova_password, nova_Nova_String* nova_database_Nova_DBConnector_Nova_database);
void nova_database_Nova_DBConnector_2_Nova_connect(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_database_Nova_DBConnector_Nova_host, nova_Nova_String* nova_database_Nova_DBConnector_Nova_user, nova_Nova_String* nova_database_Nova_DBConnector_Nova_password, nova_Nova_String* nova_database_Nova_DBConnector_Nova_database, int nova_database_Nova_DBConnector_Nova_port, nova_Nova_String* nova_database_Nova_DBConnector_Nova_unixSocket, int nova_database_Nova_DBConnector_Nova_clientFlag);
void nova_database_Nova_DBConnector_Nova_updateError(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_database_Nova_DBConnector_Nova_changeUser(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_database_Nova_DBConnector_Nova_username, nova_Nova_String* nova_database_Nova_DBConnector_Nova_password, nova_Nova_String* nova_database_Nova_DBConnector_Nova_database);
nova_database_Nova_ResultSet* nova_database_Nova_DBConnector_Nova_query(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_database_Nova_DBConnector_Nova_query);
void nova_database_Nova_DBConnector_Nova_close(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_database_Nova_DBConnector_Nova_super(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData);

#endif
