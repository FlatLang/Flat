#pragma once
#ifndef FILE_BankQueryException_NOVA
#define FILE_BankQueryException_NOVA

typedef struct bank_NovaBankQueryException bank_NovaBankQueryException;

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

typedef struct nova_VTable_bank_NovaBankQueryException
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_bank_NovaBankQueryException;

CCLASS_CLASS
(
	bank_NovaBankQueryException, 
	
	nova_VTable_bank_NovaBankQueryException* vtable;
	nova_standard_NovaString* nova_standard_exception_NovaException_Novamessage;
)

void bank_NovaBankQueryExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
bank_NovaBankQueryException* bank_NovaBankQueryException_Nova1_construct(bank_NovaBankQueryException* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novamessage);
void nova_del_BankQueryException(bank_NovaBankQueryException** this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaBankQueryException_Nova0_this(bank_NovaBankQueryException* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novamessage);
void bank_NovaBankQueryException_Novasuper(bank_NovaBankQueryException* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif