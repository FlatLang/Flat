#pragma once
#ifndef FILE_ClientInputThread_NOVA
#define FILE_ClientInputThread_NOVA

typedef struct bank_NovaClientInputThread bank_NovaClientInputThread;

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
#include <nova/standard/security/nova_standard_security_NovaMD5.h>

typedef struct nova_VTable_bank_NovaClientInputThread
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	void (*bank_NovaClientInputThread_Novavirtual0_run)(bank_NovaClientInputThread*, nova_standard_exception_NovaExceptionData*);
} nova_VTable_bank_NovaClientInputThread;

CCLASS_CLASS
(
	bank_NovaClientInputThread, 
	
	nova_VTable_bank_NovaClientInputThread* vtable;
	struct Private* prv;
)

void bank_NovaClientInputThreadNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
bank_NovaClientInputThread* bank_NovaClientInputThread_Novaconstruct(bank_NovaClientInputThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket);
void nova_del_ClientInputThread(bank_NovaClientInputThread** this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaClientInputThread_Novathis(bank_NovaClientInputThread* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket);
void bank_NovaClientInputThread_Nova0_run(bank_NovaClientInputThread* this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaClientInputThread_Novasuper(bank_NovaClientInputThread* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif