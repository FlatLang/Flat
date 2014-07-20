#pragma once
#ifndef FILE_SVGMainComponent_NOVA
#define FILE_SVGMainComponent_NOVA

typedef struct SVGMainComponent SVGMainComponent;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaSVGComponent.h>
#include <NovaSVGComponentList.h>
#include <NovaFile.h>

typedef struct nova_VTable_SVGMainComponent
{
	void (*nova_virtual_generateOutput)(SVGMainComponent*, ExceptionData*, File*);
} nova_VTable_SVGMainComponent;

CCLASS_CLASS
(
	SVGMainComponent, 
	
	SVGComponentList* nova_SVGComponent_children;
	nova_VTable_SVGMainComponent* vtable;
)

SVGMainComponent* nova_SVGMainComponent_SVGMainComponent(ExceptionData* exceptionData);
void nova_del_SVGMainComponent(SVGMainComponent** this, ExceptionData* exceptionData);
void nova_SVGMainComponent_generateOutput(SVGMainComponent* this, ExceptionData* exceptionData, File* nova_0_file);

#endif