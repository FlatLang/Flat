#pragma once
#ifndef FILE_SVGMainComponent_NOVA
#define FILE_SVGMainComponent_NOVA

typedef struct SVGMainComponent SVGMainComponent;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaNull.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaSVGComponent.h>
#include <NovaSVGComponentList.h>
#include <NovaFile.h>

typedef struct nova_VTable_SVGMainComponent
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
	void (*nova_virtual_0_generateOutput)(SVGMainComponent*, ExceptionData*, File*);
} nova_VTable_SVGMainComponent;

CCLASS_CLASS
(
	SVGMainComponent, 
	
	nova_VTable_SVGMainComponent* vtable;
	SVGComponentList* nova_SVGComponent_children;
)

SVGMainComponent* nova_0_SVGMainComponent_construct(SVGMainComponent* this, ExceptionData* exceptionData);
void nova_del_SVGMainComponent(SVGMainComponent** this, ExceptionData* exceptionData);
void nova_SVGMainComponent_this(SVGMainComponent* this, ExceptionData* exceptionData);
void nova_0_SVGMainComponent_generateOutput(SVGMainComponent* this, ExceptionData* exceptionData, File* nova_0_file);
void nova_SVGMainComponent_super(SVGMainComponent* this, ExceptionData* exceptionData);

#endif