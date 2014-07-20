#pragma once
#ifndef FILE_PolymorphismStability_NOVA
#define FILE_PolymorphismStability_NOVA

typedef struct PolymorphismStability PolymorphismStability;

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
#include <NovaPolymorphicSubClass.h>
#include <NovaStabilityTest.h>
#include <NovaPolymorphicSuperClass.h>



PolymorphismStability* nova_PolymorphismStability_PolymorphismStability(ExceptionData* exceptionData);
void nova_del_PolymorphismStability(PolymorphismStability** this, ExceptionData* exceptionData);
void nova_static_PolymorphismStability_test(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif