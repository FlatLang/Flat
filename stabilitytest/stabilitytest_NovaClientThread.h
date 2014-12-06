#pragma once
#ifndef FILE_ClientThread_NOVA
#define FILE_ClientThread_NOVA

typedef struct stabilitytest_NovaClientThread stabilitytest_NovaClientThread;

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
#include <nova/standard/network/nova_standard_network_NovaClientSocket.h>
#include <nova/standard/thread/nova_standard_thread_NovaThread.h>
#include <stabilitytest/stabilitytest_NovaStabilityTest.h>
#include <stabilitytest/stabilitytest_NovaNetworkStability.h>

typedef struct nova_VTable_stabilitytest_NovaClientThread
{
	long (*nova_standard_NovaObject_virtual0_NovagetHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_virtual0_NovatoString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_virtual0_Novaequals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	void (*stabilitytest_NovaClientThread_virtual0_Novarun)(stabilitytest_NovaClientThread*, nova_standard_exception_NovaExceptionData*);
} nova_VTable_stabilitytest_NovaClientThread;

CCLASS_CLASS
(
	stabilitytest_NovaClientThread, 
	
	nova_VTable_stabilitytest_NovaClientThread* vtable;
	struct Private* prv;
)

void stabilitytest_NovaClientThreadNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
stabilitytest_NovaClientThread* stabilitytest_NovaClientThread_Novaconstruct(stabilitytest_NovaClientThread* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram, int l0_Novaport);
void nova_del_ClientThread(stabilitytest_NovaClientThread** this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaClientThread_Novathis(stabilitytest_NovaClientThread* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram, int l0_Novaport);
void stabilitytest_NovaClientThread_0_Novarun(stabilitytest_NovaClientThread* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaClientThread_Novasuper(stabilitytest_NovaClientThread* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif