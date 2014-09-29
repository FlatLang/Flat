#pragma once
#ifndef FILE_Hypothesis_NOVA
#define FILE_Hypothesis_NOVA

typedef struct nova_standard_logic_NovaHypothesis nova_standard_logic_NovaHypothesis;

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
#include <nova/standard/primitive/nova_standard_primitive_NovaChar.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaBool.h>
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/logic/nova_standard_logic_NovaStatement.h>

typedef struct nova_VTable_nova_standard_logic_NovaHypothesis
{
	nova_standard_NovaString* (*nova_standard_logic_NovaStatement_Novavirtual0_toString)(nova_standard_logic_NovaStatement*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_logic_NovaHypothesis;

CCLASS_CLASS
(
	nova_standard_logic_NovaHypothesis, 
	
	nova_VTable_nova_standard_logic_NovaHypothesis* vtable;
	struct Private* prv;
)

nova_standard_logic_NovaHypothesis* nova_standard_logic_NovaHypothesis_Nova0_construct(nova_standard_logic_NovaHypothesis* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastatement);
void nova_del_Hypothesis(nova_standard_logic_NovaHypothesis** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_logic_NovaHypothesis_Novathis(nova_standard_logic_NovaHypothesis* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastatement);
void nova_standard_logic_NovaHypothesis_Novasuper(nova_standard_logic_NovaHypothesis* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif