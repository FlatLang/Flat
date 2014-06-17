#pragma once
#ifndef FILE_SVGMainComponent_NOVA
#define FILE_SVGMainComponent_NOVA

typedef struct SVGMainComponent SVGMainComponent;

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
#include "NovaSVGComponent.h"
#include "NovaSVGComponentList.h"
#include "NovaFile.h"

CCLASS_CLASS
(
	SVGMainComponent, 
	
	SVGComponentList* nova_SVGComponent_children;
)

SVGMainComponent* nova_SVGMainComponent_SVGMainComponent(ExceptionData* exceptionData);
void nova_del_SVGMainComponent(SVGMainComponent** this, ExceptionData* exceptionData);
void nova_SVGMainComponent_generateOutput(SVGMainComponent* this, ExceptionData* exceptionData, File* nova_0_file);

#endif