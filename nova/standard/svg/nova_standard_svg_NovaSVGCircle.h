#pragma once
#ifndef FILE_SVGCircle_NOVA
#define FILE_SVGCircle_NOVA

typedef struct nova_standard_svg_NovaSVGCircle nova_standard_svg_NovaSVGCircle;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/io/nova_standard_io_NovaConsole.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaNumber.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaByte.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaShort.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaInt.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaLong.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaFloat.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaDouble.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaNull.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaChar.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaBool.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaArray.h>
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/io/nova_standard_io_NovaFile.h>
#include <nova/standard/svg/nova_standard_svg_NovaSVGComponent.h>

typedef struct nova_VTable_nova_standard_svg_NovaSVGCircle
{
	long (*nova_standard_NovaObject_Novavirtual0_getHashCodeLong)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	nova_standard_NovaString* (*nova_standard_svg_NovaSVGCircle_Novavirtual0_toString)(nova_standard_svg_NovaSVGCircle*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
	void (*nova_standard_svg_NovaSVGCircle_Novavirtual0_generateOutput)(nova_standard_svg_NovaSVGCircle*, nova_standard_exception_NovaExceptionData*, nova_standard_io_NovaFile*);
} nova_VTable_nova_standard_svg_NovaSVGCircle;

CCLASS_CLASS
(
	nova_standard_svg_NovaSVGCircle, 
	
	nova_VTable_nova_standard_svg_NovaSVGCircle* vtable;
	nova_standard_svg_NovaSVGComponentList* nova_standard_svg_NovaSVGComponent_Novachildren;
	double nova_standard_svg_NovaSVGCircle_Novax;
	double nova_standard_svg_NovaSVGCircle_Novay;
	int nova_standard_svg_NovaSVGCircle_Novar;
)

void nova_standard_svg_NovaSVGCircleNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_svg_NovaSVGCircle* nova_standard_svg_NovaSVGCircle_Novaconstruct(nova_standard_svg_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novax, double l0_Novay, int l0_Novar);
void nova_del_SVGCircle(nova_standard_svg_NovaSVGCircle** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_svg_NovaSVGCircle_Novathis(nova_standard_svg_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novax, double l0_Novay, int l0_Novar);
void nova_standard_svg_NovaSVGCircle_Nova0_generateOutput(nova_standard_svg_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaFile* l0_Novafile);
nova_standard_NovaString* nova_standard_svg_NovaSVGCircle_Nova0_toString(nova_standard_svg_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_svg_NovaSVGCircle_Novasuper(nova_standard_svg_NovaSVGCircle* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif