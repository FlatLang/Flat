#pragma once
#ifndef FILE_Bank_NOVA
#define FILE_Bank_NOVA

typedef struct bank_NovaBank bank_NovaBank;

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
#include <nova/standard/database/nova_standard_database_NovaDBConnector.h>
#include <nova/standard/database/nova_standard_database_NovaResultSet.h>
#include <nova/standard/time/nova_standard_time_NovaDate.h>
#include <nova/standard/network/nova_standard_network_NovaServerSocket.h>
#include <nova/standard/network/nova_standard_network_NovaConnectionSocket.h>
#include <bank/bank_NovaBankQueryException.h>
#include <bank/bank_NovaServerOutputThread.h>

typedef struct nova_VTable_bank_NovaBank
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_bank_NovaBank;

CCLASS_CLASS
(
	bank_NovaBank, 
	
	nova_VTable_bank_NovaBank* vtable;
	struct Private* prv;
)

void bank_NovaBankNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
bank_NovaBank* bank_NovaBank_Nova0_construct(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData);
bank_NovaBank* bank_NovaBank_Nova1_construct(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket);
void nova_del_Bank(bank_NovaBank** this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaBank_static_Novamain(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs);
void bank_NovaBank_Novathis(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaBank_Nova0_this(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket);
void bank_NovaBank_NovarunRemote(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket);
void bank_NovaBank_Novasuper(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif