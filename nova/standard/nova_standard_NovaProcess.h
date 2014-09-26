#pragma once
#ifndef FILE_Process_NOVA
#define FILE_Process_NOVA

typedef struct nova_standard_NovaProcess nova_standard_NovaProcess;

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
#include <nova/standard/nova_standard_NovaStreamReader.h>

typedef struct nova_VTable_nova_standard_NovaProcess
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_NovaProcess;

CCLASS_CLASS
(
	nova_standard_NovaProcess, 
	
	nova_VTable_nova_standard_NovaProcess* vtable;
	nova_standard_NovaStreamReader* nova_standard_NovaProcess_Novareader;
)

nova_standard_NovaProcess* nova_standard_NovaProcess_Novaconstruct(nova_standard_NovaProcess* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaStreamReader* l0_Novareader);
void nova_del_Process(nova_standard_NovaProcess** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaProcess_Novathis(nova_standard_NovaProcess* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaStreamReader* l0_Novareader);
void nova_standard_NovaProcess_Novasuper(nova_standard_NovaProcess* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif