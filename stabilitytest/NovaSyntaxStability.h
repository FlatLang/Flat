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
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaStabilityTest.h>

typedef struct nova_VTable_SyntaxStability
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_SyntaxStability;

CCLASS_CLASS
(
	SyntaxStability, 
	
	nova_VTable_SyntaxStability* vtable;
)

SyntaxStability* nova_SyntaxStability_SyntaxStability(SyntaxStability* this, ExceptionData* exceptionData);
void nova_del_SyntaxStability(SyntaxStability** this, ExceptionData* exceptionData);
void nova_static_SyntaxStability_test(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif