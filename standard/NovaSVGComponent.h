#pragma once
#ifndef FILE_SVGComponent_NOVA
#define FILE_SVGComponent_NOVA

typedef struct SVGComponent SVGComponent;

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
#include <NovaSVGComponentList.h>
#include <NovaFile.h>

typedef struct nova_VTable_SVGComponent
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
	void (*nova_virtual_0_generateOutput)(SVGComponent*, ExceptionData*, File*);
} nova_VTable_SVGComponent;

CCLASS_CLASS
(
	SVGComponent, 
	
	nova_VTable_SVGComponent* vtable;
	SVGComponentList* nova_SVGComponent_children;
)

SVGComponent* nova_0_SVGComponent_construct(SVGComponent* this, ExceptionData* exceptionData);
void nova_del_SVGComponent(SVGComponent** this, ExceptionData* exceptionData);
void nova_SVGComponent_this(SVGComponent* this, ExceptionData* exceptionData);
void nova_0_SVGComponent_generateOutput(SVGComponent* this, ExceptionData* exceptionData, File* nova_0_file);
void nova_SVGComponent_super(SVGComponent* this, ExceptionData* exceptionData);

#endif