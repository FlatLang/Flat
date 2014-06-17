#pragma once
#ifndef FILE_SVGComponentList_NOVA
#define FILE_SVGComponentList_NOVA

typedef struct SVGComponentList SVGComponentList;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"
#include "NovaSVGComponentNode.h"
#include "NovaSVGComponent.h"
#include "NovaFile.h"

CCLASS_CLASS
(
	SVGComponentList, 
	
	SVGComponentNode* nova_SVGComponentList_start;
)

SVGComponentList* nova_SVGComponentList_SVGComponentList(ExceptionData* exceptionData);
void nova_del_SVGComponentList(SVGComponentList** this, ExceptionData* exceptionData);
void nova_SVGComponentList_generateOutput(SVGComponentList* this, ExceptionData* exceptionData, File* nova_0_file);
void nova_SVGComponentList_addChild(SVGComponentList* this, ExceptionData* exceptionData, SVGComponent* nova_0_component);

#endif