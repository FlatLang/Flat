#pragma once
#ifndef FILE_String_NOVA
#define FILE_String_NOVA

typedef struct nova_standard_NovaString nova_standard_NovaString;

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

typedef struct nova_VTable_nova_standard_NovaString
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_NovaString_Novavirtual0_toString)(nova_standard_NovaString*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaString_Novavirtual_equals)(nova_standard_NovaString*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaString*);
	nova_standard_NovaString* (*nova_standard_NovaString_Novavirtual0_concat)(nova_standard_NovaString*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaString*);
} nova_VTable_nova_standard_NovaString;

CCLASS_CLASS
(
	nova_standard_NovaString, 
	
	nova_VTable_nova_standard_NovaString* vtable;
	int nova_standard_NovaString_Novalength;
	struct Private* prv;
)

void nova_standard_NovaStringNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_NovaString_Nova0_construct(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac);
nova_standard_NovaString* nova_standard_NovaString_Nova1_construct(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novadata);
void nova_del_String(nova_standard_NovaString** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaString_Nova0_this(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac);
void nova_standard_NovaString_Nova1_this(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novadata);
char* nova_standard_NovaString_NovatoCharArray(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_NovaString_Nova0_concat(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastr);
char nova_standard_NovaString_Novaequals(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaother);
int nova_standard_NovaString_Nova1_indexOf(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasearch);
int nova_standard_NovaString_Nova2_indexOf(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasearch, int l0_Novastart);
int nova_standard_NovaString_NovalastIndexOf(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasearch);
nova_standard_NovaString* nova_standard_NovaString_Nova0_substring(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novastart, int l0_Novaend);
nova_standard_NovaString* nova_standard_NovaString_Nova1_substring(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novastart);
char nova_standard_NovaString_NovalastChar(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData);
char nova_standard_NovaString_NovacharAt(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novaindex);
nova_standard_NovaString* nova_standard_NovaString_Novatrim(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_NovaString_NovatoLowerCase(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_NovaString_NovatoUpperCase(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_NovaString_Nova0_getDataBetween(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novabefore, nova_standard_NovaString* l0_Novaafter);
nova_standard_NovaString* nova_standard_NovaString_Nova1_getDataBetween(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novabefore, nova_standard_NovaString* l0_Novaafter, int l0_Novastart);
nova_standard_NovaString* nova_standard_NovaString_Nova0_toString(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaString_Novasuper(nova_standard_NovaString* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif