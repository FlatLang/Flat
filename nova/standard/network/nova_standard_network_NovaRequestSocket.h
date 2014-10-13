#pragma once
#ifndef FILE_RequestSocket_NOVA
#define FILE_RequestSocket_NOVA

typedef struct nova_standard_network_NovaRequestSocket nova_standard_network_NovaRequestSocket;

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
#include <nova/standard/network/nova_standard_network_NovaSocket.h>

typedef struct nova_VTable_nova_standard_network_NovaRequestSocket
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_network_NovaRequestSocket;

CCLASS_CLASS
(
	nova_standard_network_NovaRequestSocket, 
	
	nova_VTable_nova_standard_network_NovaRequestSocket* vtable;
	nova_standard_NovaString* nova_standard_network_NovaSocket_Novaip;
	int nova_standard_network_NovaSocket_Novaport;
	nova_standard_io_NovaInputStream* nova_standard_network_NovaRequestSocket_Novain;
	nova_standard_io_NovaOutputStream* nova_standard_network_NovaRequestSocket_Novaout;
)

void nova_standard_network_NovaRequestSocketNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_network_NovaRequestSocket* nova_standard_network_NovaRequestSocket_Novaconstruct(nova_standard_network_NovaRequestSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaInputStream* l0_Novain, nova_standard_io_NovaOutputStream* l0_Novaout);
void nova_del_RequestSocket(nova_standard_network_NovaRequestSocket** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_network_NovaRequestSocket_Novathis(nova_standard_network_NovaRequestSocket* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaInputStream* l0_Novain, nova_standard_io_NovaOutputStream* l0_Novaout);
void nova_standard_network_NovaRequestSocket_Novasuper(nova_standard_network_NovaRequestSocket* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif