#pragma once
#ifndef FILE_BankQueryException_NOVA
#define FILE_BankQueryException_NOVA

typedef struct bank_NovaBankQueryException bank_NovaBankQueryException;

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

typedef struct nova_VTable_bank_NovaBankQueryException
{
	long (*nova_NovaObject_Novavirtual0_getHashCodeLong)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	nova_NovaString* (*nova_NovaObject_Novavirtual0_toString)(nova_NovaObject*, nova_exception_NovaExceptionData*);
	char (*nova_NovaObject_Novavirtual0_equals)(nova_NovaObject*, nova_exception_NovaExceptionData*, nova_NovaObject*);
} nova_VTable_bank_NovaBankQueryException;

CCLASS_CLASS
(
	bank_NovaBankQueryException, 
	
	nova_VTable_bank_NovaBankQueryException* vtable;
	nova_NovaString* nova_exception_NovaException_Novamessage;
)

void bank_NovaBankQueryExceptionNova_init_static(nova_exception_NovaExceptionData* exceptionData);
bank_NovaBankQueryException* bank_NovaBankQueryException_Nova1_construct(bank_NovaBankQueryException* this, nova_exception_NovaExceptionData* exceptionData, nova_NovaString* l0_Novamessage);
void nova_del_BankQueryException(bank_NovaBankQueryException** this, nova_exception_NovaExceptionData* exceptionData);
void bank_NovaBankQueryException_Nova0_this(bank_NovaBankQueryException* this, nova_exception_NovaExceptionData* exceptionData, nova_NovaString* l0_Novamessage);
void bank_NovaBankQueryException_Novasuper(bank_NovaBankQueryException* this, nova_exception_NovaExceptionData* exceptionData);

#endif