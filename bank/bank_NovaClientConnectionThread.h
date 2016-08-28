#pragma once
#ifndef FILE_ClientConnectionThread_NOVA
#define FILE_ClientConnectionThread_NOVA

typedef struct bank_NovaClientConnectionThread bank_NovaClientConnectionThread;

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

typedef struct nova_VTable_bank_NovaClientConnectionThread
{
	long (*nova_NovaObject_Novavirtual0_getHashCodeLong)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	nova_NovaString* (*nova_NovaObject_Novavirtual0_toString)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	char (*nova_NovaObject_Novavirtual0_equals)(nova_NovaObject*, nova_exception_NovaExceptionData*, nova_NovaObject*);
	void (*bank_NovaClientConnectionThread_Novavirtual_run)(bank_NovaClientConnectionThread*, nova_exception_NovaExceptionData*);
} nova_VTable_bank_NovaClientConnectionThread;

CCLASS_CLASS
(
	bank_NovaClientConnectionThread, 
	
	nova_VTable_bank_NovaClientConnectionThread* vtable;
	struct Private* prv;
)

void bank_NovaClientConnectionThreadNova_init_static(nova_exception_NovaExceptionData* exceptionData);
bank_NovaClientConnectionThread* bank_NovaClientConnectionThread_Novaconstruct(bank_NovaClientConnectionThread* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaConnectionSocket* l0_Novasocket);
void nova_del_ClientConnectionThread(bank_NovaClientConnectionThread** this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaClientConnectionThread_Novathis(bank_NovaClientConnectionThread* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaConnectionSocket* l0_Novasocket);
void bank_NovaClientConnectionThread_Novarun(bank_NovaClientConnectionThread* this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaClientConnectionThread_Novasuper(bank_NovaClientConnectionThread* this, nova_exception_NovaExceptionData* exceptionData);

#endif