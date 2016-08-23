#pragma once
#ifndef FILE_BankClient_NOVA
#define FILE_BankClient_NOVA

typedef struct bank_NovaBankClient bank_NovaBankClient;

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
#include <nova/network/nova_network_NovaClientSocket.h>
#include <nova/security/nova_security_NovaMD5.h>
#include <bank/bank_NovaClientConnectionThread.h>

typedef struct nova_VTable_bank_NovaBankClient
{
	long (*nova_NovaObject_Novavirtual0_getHashCodeLong)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	nova_NovaString* (*nova_NovaObject_Novavirtual0_toString)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	char (*nova_NovaObject_Novavirtual0_equals)(nova_NovaObject*, nova_exception_NovaExceptionData*, nova_NovaObject*);
} nova_VTable_bank_NovaBankClient;

CCLASS_CLASS
(
	bank_NovaBankClient, 
	
	nova_VTable_bank_NovaBankClient* vtable;
)

void bank_NovaBankClientNova_init_static(nova_exception_NovaExceptionData* exceptionData);
bank_NovaBankClient* bank_NovaBankClient_Nova0_construct(bank_NovaBankClient* this, nova_exception_NovaExceptionData* exceptionData);
void nova_del_BankClient(bank_NovaBankClient** this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaBankClient_static_Novamain(bank_NovaBankClient* this, nova_exception_NovaExceptionData* exceptionData, nova_NovaString** l0_Novaargs);
void bank_NovaBankClient_Novathis(bank_NovaBankClient* this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaBankClient_Novasuper(bank_NovaBankClient* this, nova_exception_NovaExceptionData* exceptionData);

#endif