#pragma once
#ifndef FILE_Date_NOVA
#define FILE_Date_NOVA

typedef struct nova_standard_time_NovaDate nova_standard_time_NovaDate;

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
#include <nova/standard/time/NativeDate.h>

typedef struct nova_VTable_nova_standard_time_NovaDate
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_time_NovaDate;

CCLASS_CLASS
(
	nova_standard_time_NovaDate, 
	
	nova_VTable_nova_standard_time_NovaDate* vtable;
	int nova_standard_time_NovaDate_Novayear;
	int nova_standard_time_NovaDate_Novamonth;
	int nova_standard_time_NovaDate_Novaday;
	int nova_standard_time_NovaDate_Novahour;
	int nova_standard_time_NovaDate_Novaminute;
	int nova_standard_time_NovaDate_Novasecond;
)

void nova_standard_time_NovaDateNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_time_NovaDate* nova_standard_time_NovaDate_Nova0_construct(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_Date(nova_standard_time_NovaDate** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_time_NovaDate_Novathis(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_time_NovaDate_NovadecodeDate(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaprototype, nova_standard_NovaString* l0_Novadate);
void nova_standard_time_NovaDate_NovaupdateTime(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_time_NovaDate_Nova0_formatDate(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastr);
nova_standard_NovaString* nova_standard_time_NovaDate_Nova1_formatDate(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastr, int l0_Novafirst, int l0_Novasecond, int l0_Novathird, int l0_Novafourth, int l0_Novafifth, int l0_Novasixth);
void nova_standard_time_NovaDate_Novasuper(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif