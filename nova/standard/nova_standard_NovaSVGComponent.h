#pragma once
#ifndef FILE_SVGComponent_NOVA
#define FILE_SVGComponent_NOVA

typedef struct nova_standard_NovaSVGComponent nova_standard_NovaSVGComponent;

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
#include <nova/standard/nova_standard_NovaSVGComponentList.h>

typedef struct nova_VTable_nova_standard_NovaSVGComponent
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	void (*nova_standard_NovaSVGComponent_Novavirtual0_generateOutput)(nova_standard_NovaSVGComponent*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaFile*);
} nova_VTable_nova_standard_NovaSVGComponent;

CCLASS_CLASS
(
	nova_standard_NovaSVGComponent, 
	
	nova_VTable_nova_standard_NovaSVGComponent* vtable;
	nova_standard_NovaSVGComponentList* nova_standard_NovaSVGComponent_Novachildren;
)

nova_standard_NovaSVGComponent* nova_standard_NovaSVGComponent_Novanull0_construct(nova_standard_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_SVGComponent(nova_standard_NovaSVGComponent** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaSVGComponent_Novathis(nova_standard_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaSVGComponent_Novanull0_generateOutput(nova_standard_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaFile* l0_Novafile);
void nova_standard_NovaSVGComponent_Novasuper(nova_standard_NovaSVGComponent* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif