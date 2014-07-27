#pragma once
#ifndef FILE_SVGCircle_NOVA
#define FILE_SVGCircle_NOVA

typedef struct SVGCircle SVGCircle;

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
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaSVGComponent.h>
#include <NovaSVGComponentList.h>
#include <NovaFile.h>

typedef struct nova_VTable_SVGCircle
{
	void (*nova_virtual_1_generateOutput)(SVGCircle*, ExceptionData*, File*);
	String* (*nova_virtual_1_toString)(SVGCircle*, ExceptionData*);
} nova_VTable_SVGCircle;

CCLASS_CLASS
(
	SVGCircle, 
	
	SVGComponentList* nova_SVGComponent_children;
	double nova_SVGCircle_x;
	double nova_SVGCircle_y;
	int nova_SVGCircle_r;
	nova_VTable_SVGCircle* vtable;
)

SVGCircle* nova_SVGCircle_SVGCircle(ExceptionData* exceptionData, double nova_0_x, double nova_0_y, int nova_0_r);
void nova_del_SVGCircle(SVGCircle** this, ExceptionData* exceptionData);
void nova_1_SVGCircle_generateOutput(SVGCircle* this, ExceptionData* exceptionData, File* nova_0_file);
String* nova_1_SVGCircle_toString(SVGCircle* this, ExceptionData* exceptionData);

#endif