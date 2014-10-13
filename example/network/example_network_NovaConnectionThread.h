#pragma once
#ifndef FILE_ConnectionThread_NOVA
#define FILE_ConnectionThread_NOVA

typedef struct example_network_NovaConnectionThread example_network_NovaConnectionThread;

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
#include <nova/standard/thread/nova_standard_thread_NovaThread.h>
#include <nova/standard/network/nova_standard_network_NovaConnectionSocket.h>

typedef struct nova_VTable_example_network_NovaConnectionThread
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	void (*example_network_NovaConnectionThread_Novavirtual_run)(example_network_NovaConnectionThread*, nova_standard_exception_NovaExceptionData*);
} nova_VTable_example_network_NovaConnectionThread;

CCLASS_CLASS
(
	example_network_NovaConnectionThread, 
	
	nova_VTable_example_network_NovaConnectionThread* vtable;
	struct Private* prv;
)

void example_network_NovaConnectionThreadNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
example_network_NovaConnectionThread* example_network_NovaConnectionThread_Novaconstruct(example_network_NovaConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket);
void nova_del_ConnectionThread(example_network_NovaConnectionThread** this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_network_NovaConnectionThread_Novathis(example_network_NovaConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket);
void example_network_NovaConnectionThread_Novarun(example_network_NovaConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_network_NovaConnectionThread_Novasuper(example_network_NovaConnectionThread* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif