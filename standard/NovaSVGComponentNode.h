#pragma once
#ifndef FILE_SVGComponentNode_NOVA
#define FILE_SVGComponentNode_NOVA

typedef struct SVGComponentNode SVGComponentNode;

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
#include <NovaSVGCircle.h>


CCLASS_CLASS
(
	SVGComponentNode, 
	
	SVGComponentNode* nova_SVGComponentNode_next;
	SVGCircle* nova_SVGComponentNode_component;
)

SVGComponentNode* nova_SVGComponentNode_SVGComponentNode(ExceptionData* exceptionData);
void nova_del_SVGComponentNode(SVGComponentNode** this, ExceptionData* exceptionData);

#endif