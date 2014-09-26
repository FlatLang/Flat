#pragma once
#ifndef FILE_SVGComponentList_NOVA
#define FILE_SVGComponentList_NOVA

typedef struct nova_standard_NovaSVGComponentList nova_standard_NovaSVGComponentList;

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
#include <nova/standard/nova_standard_NovaFile.h>
#include <nova/standard/nova_standard_NovaSVGComponent.h>
#include <nova/standard/nova_standard_NovaSVGComponentNode.h>

typedef struct nova_VTable_nova_standard_NovaSVGComponentList
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_NovaSVGComponentList;

CCLASS_CLASS
(
	nova_standard_NovaSVGComponentList, 
	
	nova_VTable_nova_standard_NovaSVGComponentList* vtable;
	nova_standard_NovaSVGComponentNode* nova_standard_NovaSVGComponentList_Novastart;
)

nova_standard_NovaSVGComponentList* nova_standard_NovaSVGComponentList_Novanull0_construct(nova_standard_NovaSVGComponentList* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_SVGComponentList(nova_standard_NovaSVGComponentList** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaSVGComponentList_NovagenerateOutput(nova_standard_NovaSVGComponentList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaFile* l0_Novafile);
void nova_standard_NovaSVGComponentList_NovaaddChild(nova_standard_NovaSVGComponentList* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaSVGComponent* l0_Novacomponent);
void nova_standard_NovaSVGComponentList_Novathis(nova_standard_NovaSVGComponentList* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaSVGComponentList_Novasuper(nova_standard_NovaSVGComponentList* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif