#pragma once
#ifndef FILE_SVGComponentList_NOVA
#define FILE_SVGComponentList_NOVA

typedef struct SVGComponentList SVGComponentList;

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
#include <NovaFile.h>
#include <NovaSVGComponent.h>
#include <NovaSVGComponentNode.h>

typedef struct nova_VTable_SVGComponentList
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_SVGComponentList;

CCLASS_CLASS
(
	SVGComponentList, 
	
	nova_VTable_SVGComponentList* vtable;
	SVGComponentNode* nova_SVGComponentList_start;
)

SVGComponentList* nova_0_SVGComponentList_construct(SVGComponentList* this, ExceptionData* exceptionData);
void nova_del_SVGComponentList(SVGComponentList** this, ExceptionData* exceptionData);
void nova_SVGComponentList_generateOutput(SVGComponentList* this, ExceptionData* exceptionData, File* nova_0_file);
void nova_SVGComponentList_addChild(SVGComponentList* this, ExceptionData* exceptionData, SVGComponent* nova_0_component);
void nova_SVGComponentList_this(SVGComponentList* this, ExceptionData* exceptionData);
void nova_SVGComponentList_super(SVGComponentList* this, ExceptionData* exceptionData);

#endif