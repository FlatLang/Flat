#pragma once
#ifndef FILE_ClientSocket_NOVA
#define FILE_ClientSocket_NOVA

typedef struct nova_standard_network_NovaClientSocket nova_standard_network_NovaClientSocket;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/io/nova_standard_io_NovaConsole.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaNumber.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaByte.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaShort.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaInt.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaLong.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaFloat.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaDouble.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaNull.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaChar.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaBool.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaArray.h>
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/network/NativeClientSocket.h>
#include <nova/standard/network/NativeSocket.h>
#include <nova/standard/network/nova_standard_network_NovaSocket.h>
#include <nova/standard/network/nova_standard_network_NovaConnectionSocket.h>

typedef struct nova_VTable_nova_standard_network_NovaClientSocket
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_network_NovaClientSocket;

CCLASS_CLASS
(
	nova_standard_network_NovaClientSocket, 
	
	nova_VTable_nova_standard_network_NovaClientSocket* vtable;
	nova_standard_NovaString* nova_standard_network_NovaSocket_Novaip;
	int nova_standard_network_NovaSocket_Novaport;
	nova_standard_network_NovaConnectionSocket* nova_standard_network_NovaClientSocket_Novaconnection;
	struct Private* prv;
)

void nova_standard_network_NovaClientSocketNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_network_NovaClientSocket* nova_standard_network_NovaClientSocket_Nova0_construct(nova_standard_network_NovaClientSocket* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_ClientSocket(nova_standard_network_NovaClientSocket** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_network_NovaClientSocket_Novathis(nova_standard_network_NovaClientSocket* this, nova_standard_exception_NovaExceptionData* exceptionData);
char nova_standard_network_NovaClientSocket_Novaconnect(nova_standard_network_NovaClientSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_NovaipAddress, int l0_Novaport);
char nova_standard_network_NovaClientSocket_Novaclose(nova_standard_network_NovaClientSocket* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_network_NovaClientSocket_Novasuper(nova_standard_network_NovaClientSocket* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif