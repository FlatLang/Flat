#pragma once
#ifndef FILE_MathDemo_NOVA
#define FILE_MathDemo_NOVA

typedef struct MathDemo MathDemo;

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
#include <NovaTime.h>

MathDemo* nova_MathDemo_MathDemo(ExceptionData* exceptionData);
void nova_del_MathDemo(MathDemo** this, ExceptionData* exceptionData);
void nova_static_MathDemo_main(MathDemo* this, ExceptionData* exceptionData, String** nova_0_args);

#endif