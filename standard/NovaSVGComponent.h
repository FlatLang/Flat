#pragma once
#ifndef FILE_SVGComponent_NOVA
#define FILE_SVGComponent_NOVA

typedef struct SVGComponent SVGComponent;

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
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaSVGComponentList.h>
#include <NovaFile.h>

typedef struct nova_VTable_SVGComponent
{
	void (*nova_virtual_2_generateOutput)(SVGComponent*, ExceptionData*, File*);
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_SVGComponent;

CCLASS_CLASS
(
	SVGComponent, 
	
	SVGComponentList* nova_SVGComponent_children;
	nova_VTable_SVGComponent* vtable;
)

SVGComponent* nova_SVGComponent_SVGComponent(SVGComponent* this, ExceptionData* exceptionData);
void nova_del_SVGComponent(SVGComponent** this, ExceptionData* exceptionData);
void nova_2_SVGComponent_generateOutput(SVGComponent* this, ExceptionData* exceptionData, File* nova_0_file);

#endif