#pragma once
#ifndef FILE_Bank_NOVA
#define FILE_Bank_NOVA

typedef struct bank_NovaBank bank_NovaBank;

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
#include <nova/database/nova_database_NovaDBConnector.h>
#include <nova/database/nova_database_NovaResultSet.h>
#include <nova/time/nova_time_NovaDate.h>
#include <nova/network/nova_network_NovaServerSocket.h>
#include <nova/network/nova_network_NovaConnectionSocket.h>
#include <bank/bank_NovaBankQueryException.h>
#include <bank/bank_NovaServerOutputThread.h>

typedef struct nova_VTable_bank_NovaBank
{
	long (*nova_NovaObject_Novavirtual0_getHashCodeLong)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	nova_NovaString* (*nova_NovaObject_Novavirtual0_toString)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	char (*nova_NovaObject_Novavirtual0_equals)(nova_NovaObject*, nova_exception_NovaExceptionData*, nova_NovaObject*);
} nova_VTable_bank_NovaBank;

CCLASS_CLASS
(
	bank_NovaBank, 
	
	nova_VTable_bank_NovaBank* vtable;
	struct Private* prv;
)

void bank_NovaBankNova_init_static(nova_exception_NovaExceptionData* exceptionData);
bank_NovaBank* bank_NovaBank_Nova0_construct(bank_NovaBank* this, nova_exception_NovaExceptionData* exceptionData);
bank_NovaBank* bank_NovaBank_Nova1_construct(bank_NovaBank* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaConnectionSocket* l0_Novasocket);
void nova_del_Bank(bank_NovaBank** this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaBank_static_Novamain(bank_NovaBank* this, nova_exception_NovaExceptionData* exceptionData, nova_NovaString** l0_Novaargs);
void bank_NovaBank_Novathis(bank_NovaBank* this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaBank_Nova0_this(bank_NovaBank* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaConnectionSocket* l0_Novasocket);
void bank_NovaBank_NovarunRemote(bank_NovaBank* this, nova_exception_NovaExceptionData* exceptionData, nova_network_NovaConnectionSocket* l0_Novasocket);
void bank_NovaBank_Novasuper(bank_NovaBank* this, nova_exception_NovaExceptionData* exceptionData);

#endif