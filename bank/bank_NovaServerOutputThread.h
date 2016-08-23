#pragma once
#ifndef FILE_ServerOutputThread_NOVA
#define FILE_ServerOutputThread_NOVA

typedef struct bank_NovaServerOutputThread bank_NovaServerOutputThread;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/exception/nova_exception_NovaExceptionData.h>
#include <nova/exception/nova_exception_NovaException.h>
#include <nova/exception/nova_exception_NovaDivideByZeroException.h>
#include <nova/io/nova_io_NovaConsole.h>
#include <nova/primitive/number/nova_primitive_number_NovaNumber.h>
#include <nova/primitive/number/nova_primitive_number_NovaByte.h>
#include <nova/primitive/number/nova_primitive_number_NovaShort.h>
#include <nova/primitive/number/nova_primitive_number_NovaInt.h>
#include <nova/primitive/number/nova_primitive_number_NovaLong.h>
#include <nova/primitive/number/nova_primitive_number_NovaFloat.h>
#include <nova/primitive/number/nova_primitive_number_NovaDouble.h>
#include <nova/primitive/nova_primitive_NovaNull.h>
#include <nova/primitive/number/nova_primitive_number_NovaChar.h>
#include <nova/primitive/nova_primitive_NovaBool.h>
#include <nova/datastruct/nova_datastruct_NovaArray.h>
#include <nova/gc/nova_gc_NovaGC.h>
#include <nova/nova_NovaObject.h>
#include <nova/nova_NovaString.h>
#include <nova/nova_NovaSystem.h>
#include <nova/nova_NovaMath.h>
#include <nova/thread/nova_thread_NovaThread.h>
#include <nova/network/nova_network_NovaServerSocket.h>
#include <nova/network/nova_network_NovaConnectionSocket.h>
#include <bank/bank_NovaBank.h>
#include <bank/bank_NovaConnectionThread.h>

typedef struct nova_VTable_bank_NovaServerOutputThread
{
	long (*nova_NovaObject_Novavirtual0_getHashCodeLong)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	nova_NovaString* (*nova_NovaObject_Novavirtual0_toString)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	char (*nova_NovaObject_Novavirtual0_equals)(nova_NovaObject*, nova_exception_NovaExceptionData*, nova_NovaObject*);
	void (*bank_NovaServerOutputThread_Novavirtual0_run)(bank_NovaServerOutputThread*, nova_exception_NovaExceptionData*);
} nova_VTable_bank_NovaServerOutputThread;

CCLASS_CLASS
(
	bank_NovaServerOutputThread, 
	
	nova_VTable_bank_NovaServerOutputThread* vtable;
	struct Private* prv;
)

void bank_NovaServerOutputThreadNova_init_static(nova_exception_NovaExceptionData* exceptionData);
bank_NovaServerOutputThread* bank_NovaServerOutputThread_Novaconstruct(bank_NovaServerOutputThread* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaServerSocket* l0_Novasocket);
void nova_del_ServerOutputThread(bank_NovaServerOutputThread** this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaServerOutputThread_Novathis(bank_NovaServerOutputThread* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaServerSocket* l0_Novasocket);
void bank_NovaServerOutputThread_Nova0_run(bank_NovaServerOutputThread* this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaServerOutputThread_Novasuper(bank_NovaServerOutputThread* this, nova_exception_NovaExceptionData* exceptionData);

#endif