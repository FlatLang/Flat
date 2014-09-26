#pragma once
#ifndef FILE_Null_NOVA
#define FILE_Null_NOVA

typedef struct nova_standard_NovaNull nova_standard_NovaNull;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/nova_standard_NovaNull.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/nova_standard_NovaConsole.h>
#include <nova/standard/nova_standard_NovaGC.h>
#include <nova/standard/nova_standard_NovaNumber.h>
#include <nova/standard/nova_standard_NovaByte.h>
#include <nova/standard/nova_standard_NovaShort.h>
#include <nova/standard/nova_standard_NovaInt.h>
#include <nova/standard/nova_standard_NovaLong.h>
#include <nova/standard/nova_standard_NovaFloat.h>
#include <nova/standard/nova_standard_NovaDouble.h>
#include <nova/standard/nova_standard_NovaChar.h>
#include <nova/standard/nova_standard_NovaBool.h>

typedef struct nova_VTable_nova_standard_NovaNull
{
	nova_standard_NovaString* (*nova_standard_NovaNull_Novavirtual0_toString)(nova_standard_NovaNull*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaString_Novavirtual0_equals)(nova_standard_NovaString*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaString*);
	nova_standard_NovaString* (*nova_standard_NovaNull_Novavirtual0_concat)(nova_standard_NovaNull*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaString*);
} nova_VTable_nova_standard_NovaNull;

CCLASS_CLASS
(
	nova_standard_NovaNull, 
	
	nova_VTable_nova_standard_NovaNull* vtable;
	int nova_standard_NovaString_Novalength;
	struct Private* prv;
)

nova_standard_NovaNull* nova_standard_NovaNull_Novanull0_construct(nova_standard_NovaNull* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_Null(nova_standard_NovaNull** this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_NovaNull_Novanull0_toString(nova_standard_NovaNull* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_NovaNull_Novanull0_concat(nova_standard_NovaNull* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaother);
void nova_standard_NovaNull_Novathis(nova_standard_NovaNull* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaNull_Novasuper(nova_standard_NovaNull* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif