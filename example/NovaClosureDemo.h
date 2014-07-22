#pragma once
#ifndef FILE_ClosureDemo_NOVA
#define FILE_ClosureDemo_NOVA

typedef struct ClosureDemo ClosureDemo;

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



ClosureDemo* nova_ClosureDemo_ClosureDemo(ExceptionData* exceptionData);
void nova_del_ClosureDemo(ClosureDemo** this, ExceptionData* exceptionData);
void nova_static_ClosureDemo_main(ClosureDemo* this, ExceptionData* exceptionData, String** nova_0_args);

#endif