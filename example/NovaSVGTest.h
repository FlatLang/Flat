#pragma once
#ifndef FILE_SVGTest_NOVA
#define FILE_SVGTest_NOVA

typedef struct SVGTest SVGTest;

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
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaSVG.h>
#include <NovaSVGCircle.h>
#include <NovaFile.h>
#include <NovaTime.h>

typedef struct nova_VTable_SVGTest
{
	String* (*nova_virtual_4_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_SVGTest;

CCLASS_CLASS
(
	SVGTest, 
	
	nova_VTable_SVGTest* vtable;
)

SVGTest* nova_SVGTest_construct(SVGTest* this, ExceptionData* exceptionData);
void nova_del_SVGTest(SVGTest** this, ExceptionData* exceptionData);
void nova_static_SVGTest_main(SVGTest* this, ExceptionData* exceptionData, String** nova_0_args);

#endif