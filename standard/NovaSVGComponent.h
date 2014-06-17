#pragma once
#ifndef FILE_SVGComponent_NOVA
#define FILE_SVGComponent_NOVA

typedef struct SVGComponent SVGComponent;

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
#include "NovaSVGComponentList.h"
#include "NovaFile.h"

CCLASS_CLASS
(
	SVGComponent, 
	
	SVGComponentList* nova_SVGComponent_children;
)

SVGComponent* nova_SVGComponent_SVGComponent(ExceptionData* exceptionData);
void nova_del_SVGComponent(SVGComponent** this, ExceptionData* exceptionData);
void nova_SVGComponent_generateOutput(SVGComponent* this, ExceptionData* exceptionData, File* nova_0_file);

#endif