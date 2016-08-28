#pragma once
#ifndef FILE_ClientInputThread_NOVA
#define FILE_ClientInputThread_NOVA

typedef struct bank_NovaClientInputThread bank_NovaClientInputThread;

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
#include <nova/network/nova_network_NovaConnectionSocket.h>
#include <nova/security/nova_security_NovaMD5.h>

typedef struct nova_VTable_bank_NovaClientInputThread
{
	long (*nova_NovaObject_Novavirtual0_getHashCodeLong)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	nova_NovaString* (*nova_NovaObject_Novavirtual0_toString)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	char (*nova_NovaObject_Novavirtual0_equals)(nova_NovaObject*, nova_exception_NovaExceptionData*, nova_NovaObject*);
	void (*bank_NovaClientInputThread_Novavirtual0_run)(bank_NovaClientInputThread*, nova_exception_NovaExceptionData*);
} nova_VTable_bank_NovaClientInputThread;

CCLASS_CLASS
(
	bank_NovaClientInputThread, 
	
	nova_VTable_bank_NovaClientInputThread* vtable;
	struct Private* prv;
)

void bank_NovaClientInputThreadNova_init_static(nova_exception_NovaExceptionData* exceptionData);
bank_NovaClientInputThread* bank_NovaClientInputThread_Novaconstruct(bank_NovaClientInputThread* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaConnectionSocket* l0_Novasocket);
void nova_del_ClientInputThread(bank_NovaClientInputThread** this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaClientInputThread_Novathis(bank_NovaClientInputThread* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaConnectionSocket* l0_Novasocket);
void bank_NovaClientInputThread_Nova0_run(bank_NovaClientInputThread* this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaClientInputThread_Novasuper(bank_NovaClientInputThread* this, nova_exception_NovaExceptionData* exceptionData);

#endif