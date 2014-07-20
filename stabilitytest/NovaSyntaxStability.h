#pragma once
#ifndef FILE_SyntaxStability_NOVA
#define FILE_SyntaxStability_NOVA

typedef struct SyntaxStability SyntaxStability;

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



SyntaxStability* nova_SyntaxStability_SyntaxStability(ExceptionData* exceptionData);
void nova_del_SyntaxStability(SyntaxStability** this, ExceptionData* exceptionData);
void nova_static_SyntaxStability_test(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif