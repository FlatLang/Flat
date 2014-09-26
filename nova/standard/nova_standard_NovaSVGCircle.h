#pragma once
#ifndef FILE_SVGCircle_NOVA
#define FILE_SVGCircle_NOVA

typedef struct nova_standard_NovaSVGCircle nova_standard_NovaSVGCircle;

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

typedef struct nova_VTable_nova_standard_NovaSVGCircle
{
	nova_standard_NovaString* (*nova_standard_NovaSVGCircle_Novavirtual0_toString)(nova_standard_NovaSVGCircle*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	void (*nova_standard_NovaSVGCircle_Novavirtual0_generateOutput)(nova_standard_NovaSVGCircle*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaFile*);
} nova_VTable_nova_standard_NovaSVGCircle;

CCLASS_CLASS
(
	nova_standard_NovaSVGCircle, 
	
	nova_VTable_nova_standard_NovaSVGCircle* vtable;
	nova_standard_NovaSVGComponentList* nova_standard_NovaSVGComponent_Novachildren;
	double nova_standard_NovaSVGCircle_Novax;
	double nova_standard_NovaSVGCircle_Novay;
	int nova_standard_NovaSVGCircle_Novar;
)

nova_standard_NovaSVGCircle* nova_standard_NovaSVGCircle_Novaconstruct(nova_standard_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novax, double l0_Novay, int l0_Novar);
void nova_del_SVGCircle(nova_standard_NovaSVGCircle** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaSVGCircle_Novathis(nova_standard_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novax, double l0_Novay, int l0_Novar);
void nova_standard_NovaSVGCircle_Novanull0_generateOutput(nova_standard_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaFile* l0_Novafile);
nova_standard_NovaString* nova_standard_NovaSVGCircle_Novanull0_toString(nova_standard_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaSVGCircle_Novasuper(nova_standard_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif