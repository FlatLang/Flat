#pragma once
#ifndef FILE_SVGComponentNode_NOVA
#define FILE_SVGComponentNode_NOVA

typedef struct SVGComponentNode SVGComponentNode;

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

typedef struct nova_VTable_SVGComponentNode
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_SVGComponentNode;

CCLASS_CLASS
(
	SVGComponentNode, 
	
	nova_VTable_SVGComponentNode* vtable;
	SVGComponentNode* nova_SVGComponentNode_next;
	SVGComponent* nova_SVGComponentNode_component;
)

SVGComponentNode* nova_0_SVGComponentNode_construct(SVGComponentNode* this, ExceptionData* exceptionData);
void nova_del_SVGComponentNode(SVGComponentNode** this, ExceptionData* exceptionData);
void nova_SVGComponentNode_this(SVGComponentNode* this, ExceptionData* exceptionData);
void nova_SVGComponentNode_super(SVGComponentNode* this, ExceptionData* exceptionData);

#endif