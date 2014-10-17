#pragma once
#ifndef FILE_BankClient_NOVA
#define FILE_BankClient_NOVA

typedef struct bank_NovaBankClient bank_NovaBankClient;

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
#include <nova/standard/security/nova_standard_security_NovaMD5.h>
#include <bank/bank_NovaClientConnectionThread.h>

typedef struct nova_VTable_bank_NovaBankClient
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_bank_NovaBankClient;

CCLASS_CLASS
(
	bank_NovaBankClient, 
	
	nova_VTable_bank_NovaBankClient* vtable;
)

void bank_NovaBankClientNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
bank_NovaBankClient* bank_NovaBankClient_Nova0_construct(bank_NovaBankClient* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_BankClient(bank_NovaBankClient** this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaBankClient_static_Novamain(bank_NovaBankClient* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs);
void bank_NovaBankClient_Novathis(bank_NovaBankClient* this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaBankClient_Novasuper(bank_NovaBankClient* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif