#pragma once
#ifndef FILE_ServerOutputThread_NOVA
#define FILE_ServerOutputThread_NOVA

typedef struct bank_NovaServerOutputThread bank_NovaServerOutputThread;

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
#include <nova/standard/network/nova_standard_network_NovaServerSocket.h>
#include <nova/standard/network/nova_standard_network_NovaConnectionSocket.h>
#include <bank/bank_NovaBank.h>
#include <bank/bank_NovaConnectionThread.h>

typedef struct nova_VTable_bank_NovaServerOutputThread
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	void (*bank_NovaServerOutputThread_Novavirtual0_run)(bank_NovaServerOutputThread*, nova_standard_exception_NovaExceptionData*);
} nova_VTable_bank_NovaServerOutputThread;

CCLASS_CLASS
(
	bank_NovaServerOutputThread, 
	
	nova_VTable_bank_NovaServerOutputThread* vtable;
	struct Private* prv;
)

void bank_NovaServerOutputThreadNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
bank_NovaServerOutputThread* bank_NovaServerOutputThread_Novaconstruct(bank_NovaServerOutputThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaServerSocket* l0_Novasocket);
void nova_del_ServerOutputThread(bank_NovaServerOutputThread** this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaServerOutputThread_Novathis(bank_NovaServerOutputThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaServerSocket* l0_Novasocket);
void bank_NovaServerOutputThread_Nova0_run(bank_NovaServerOutputThread* this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaServerOutputThread_Novasuper(bank_NovaServerOutputThread* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif