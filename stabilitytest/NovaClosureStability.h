#pragma once
#ifndef FILE_ClosureStability_NOVA
#define FILE_ClosureStability_NOVA

typedef struct ClosureStability ClosureStability;

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
#include <NovaStabilityTest.h>


CCLASS_CLASS
(
	ClosureStability, 
	
	struct Private* prv;
)

ClosureStability* nova_ClosureStability_ClosureStability(ExceptionData* exceptionData);
void nova_del_ClosureStability(ClosureStability** this, ExceptionData* exceptionData);
void nova_static_ClosureStability_test(ClosureStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif