#pragma once
#ifndef FILE_NetworkOutputStream_NOVA
#define FILE_NetworkOutputStream_NOVA

typedef struct nova_standard_network_NovaNetworkOutputStream nova_standard_network_NovaNetworkOutputStream;

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
#include <nova/standard/network/NativeSocket.h>
#include <nova/standard/io/nova_standard_io_NovaOutputStream.h>
#include <nova/standard/network/nova_standard_network_NovaConnectionSocket.h>

typedef struct nova_VTable_nova_standard_network_NovaNetworkOutputStream
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	char (*nova_standard_network_NovaNetworkOutputStream_Novavirtual_write)(nova_standard_network_NovaNetworkOutputStream*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaString*);
	char (*nova_standard_network_NovaNetworkOutputStream_Novavirtual0_write)(nova_standard_network_NovaNetworkOutputStream*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_network_NovaNetworkOutputStream;

CCLASS_CLASS
(
	nova_standard_network_NovaNetworkOutputStream, 
	
	nova_VTable_nova_standard_network_NovaNetworkOutputStream* vtable;
	struct Private* prv;
)

void nova_standard_network_NovaNetworkOutputStreamNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_network_NovaNetworkOutputStream* nova_standard_network_NovaNetworkOutputStream_Nova1_construct(nova_standard_network_NovaNetworkOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket);
void nova_del_NetworkOutputStream(nova_standard_network_NovaNetworkOutputStream** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_network_NovaNetworkOutputStream_Novathis(nova_standard_network_NovaNetworkOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket);
char nova_standard_network_NovaNetworkOutputStream_Novawrite(nova_standard_network_NovaNetworkOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novadata);
char nova_standard_network_NovaNetworkOutputStream_Nova0_write(nova_standard_network_NovaNetworkOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novadata);
void nova_standard_network_NovaNetworkOutputStream_Novasuper(nova_standard_network_NovaNetworkOutputStream* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif