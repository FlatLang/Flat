#pragma once
#ifndef FILE_ExceptionHandlingDemo_NOVA
#define FILE_ExceptionHandlingDemo_NOVA

typedef struct ExceptionHandlingDemo ExceptionHandlingDemo;

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
#include <NovaNonWholeDivisionException.h>



ExceptionHandlingDemo* nova_ExceptionHandlingDemo_ExceptionHandlingDemo(ExceptionData* exceptionData);
void nova_del_ExceptionHandlingDemo(ExceptionHandlingDemo** this, ExceptionData* exceptionData);
void nova_static_ExceptionHandlingDemo_main(ExceptionHandlingDemo* this, ExceptionData* exceptionData, String** nova_0_args);

#endif