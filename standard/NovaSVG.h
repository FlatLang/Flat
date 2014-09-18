#pragma once
#ifndef FILE_SVG_NOVA
#define FILE_SVG_NOVA

typedef struct SVG SVG;

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
#include <NovaSVGMainComponent.h>
#include <NovaFile.h>

typedef struct nova_VTable_SVG
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_SVG;

CCLASS_CLASS
(
	SVG, 
	
	nova_VTable_SVG* vtable;
	SVGMainComponent* nova_SVG_root;
)

SVG* nova_0_SVG_construct(SVG* this, ExceptionData* exceptionData);
void nova_del_SVG(SVG** this, ExceptionData* exceptionData);
void nova_SVG_this(SVG* this, ExceptionData* exceptionData);
void nova_SVG_generateOutput(SVG* this, ExceptionData* exceptionData, File* nova_0_file);
void nova_SVG_generateHTMLOutput(SVG* this, ExceptionData* exceptionData, File* nova_0_file);
void nova_SVG_super(SVG* this, ExceptionData* exceptionData);

#endif