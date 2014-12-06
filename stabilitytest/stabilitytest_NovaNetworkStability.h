#pragma once
#ifndef FILE_NetworkStability_NOVA
#define FILE_NetworkStability_NOVA

typedef struct stabilitytest_NovaNetworkStability stabilitytest_NovaNetworkStability;

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
#include <nova/standard/network/nova_standard_network_NovaServerSocket.h>
#include <nova/standard/network/nova_standard_network_NovaConnectionSocket.h>
#include <stabilitytest/stabilitytest_NovaStabilityTest.h>
#include <stabilitytest/stabilitytest_NovaClientThread.h>

typedef struct nova_VTable_stabilitytest_NovaNetworkStability
{
	long (*nova_standard_NovaObject_virtual0_NovagetHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_virtual0_NovatoString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_virtual0_Novaequals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_stabilitytest_NovaNetworkStability;

CCLASS_CLASS
(
	stabilitytest_NovaNetworkStability, 
	
	nova_VTable_stabilitytest_NovaNetworkStability* vtable;
)
extern nova_standard_NovaString* stabilitytest_NovaNetworkStability_static_Novareceived;

void stabilitytest_NovaNetworkStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
stabilitytest_NovaNetworkStability* stabilitytest_NovaNetworkStability_0_Novaconstruct(stabilitytest_NovaNetworkStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_NetworkStability(stabilitytest_NovaNetworkStability** this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaNetworkStability_static_Novatest(stabilitytest_NovaNetworkStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaNetworkStability_Novathis(stabilitytest_NovaNetworkStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaNetworkStability_Novasuper(stabilitytest_NovaNetworkStability* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif