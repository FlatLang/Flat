#pragma once
#ifndef FILE_SVG_NOVA
#define FILE_SVG_NOVA

typedef struct SVG SVG;

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
#include "NovaSVGMainComponent.h"
#include "NovaFile.h"

CCLASS_CLASS
(
	SVG, 
	
	SVGMainComponent* nova_SVG_root;
)

SVG* nova_SVG_SVG(ExceptionData* exceptionData);
void nova_del_SVG(SVG** this, ExceptionData* exceptionData);
void nova_SVG_generateOutput(SVG* this, ExceptionData* exceptionData, File* nova_0_file);
void nova_SVG_generateHTMLOutput(SVG* this, ExceptionData* exceptionData, File* nova_0_file);

#endif