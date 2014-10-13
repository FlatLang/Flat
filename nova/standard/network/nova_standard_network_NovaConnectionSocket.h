#pragma once
#ifndef FILE_ConnectionSocket_NOVA
#define FILE_ConnectionSocket_NOVA

typedef struct nova_standard_network_NovaConnectionSocket nova_standard_network_NovaConnectionSocket;

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
#include <nova/standard/io/nova_standard_io_NovaInputStream.h>
#include <nova/standard/io/nova_standard_io_NovaOutputStream.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaQueue.h>
#include <nova/standard/network/nova_standard_network_NovaSocket.h>
#include <nova/standard/network/nova_standard_network_NovaNetworkInputStream.h>
#include <nova/standard/network/nova_standard_network_NovaNetworkOutputStream.h>

typedef struct nova_VTable_nova_standard_network_NovaConnectionSocket
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_network_NovaConnectionSocket;

CCLASS_CLASS
(
	nova_standard_network_NovaConnectionSocket, 
	
	nova_VTable_nova_standard_network_NovaConnectionSocket* vtable;
	nova_standard_NovaString* nova_standard_network_NovaSocket_Novaip;
	int nova_standard_network_NovaSocket_Novaport;
	char nova_standard_network_NovaConnectionSocket_Novaconnected;
	nova_standard_io_NovaInputStream* nova_standard_network_NovaConnectionSocket_Novain;
	nova_standard_io_NovaOutputStream* nova_standard_network_NovaConnectionSocket_Novaout;
	struct Private* prv;
)

void nova_standard_network_NovaConnectionSocketNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_network_NovaConnectionSocket* nova_standard_network_NovaConnectionSocket_Novaconstruct(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, SOCKET_ID_TYPE l0_Novasocket);
void nova_del_ConnectionSocket(nova_standard_network_NovaConnectionSocket** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_network_NovaConnectionSocket_Novathis(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, SOCKET_ID_TYPE l0_Novasocket);
char nova_standard_network_NovaConnectionSocket_NovavalidateConnection(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_network_NovaConnectionSocket_Nova0_readString(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData);
char nova_standard_network_NovaConnectionSocket_Novawrite(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novadata);
void nova_standard_network_NovaConnectionSocket_Novasuper(nova_standard_network_NovaConnectionSocket* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif