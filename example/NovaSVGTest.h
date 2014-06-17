#pragma once
#ifndef FILE_SVGTest_NOVA
#define FILE_SVGTest_NOVA

typedef struct SVGTest SVGTest;

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
#include "NovaSVG.h"
#include "NovaSVGCircle.h"
#include "NovaSVGComponentList.h"
#include "NovaFile.h"
#include "NovaTime.h"

SVGTest* nova_SVGTest_SVGTest(ExceptionData* exceptionData);
void nova_del_SVGTest(SVGTest** this, ExceptionData* exceptionData);
void nova_SVGTest_main(ExceptionData* exceptionData, String** nova_0_args);

#endif