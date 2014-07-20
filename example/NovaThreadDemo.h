#pragma once
#ifndef FILE_ThreadDemo_NOVA
#define FILE_ThreadDemo_NOVA

typedef struct ThreadDemo ThreadDemo;

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
#include <NovaThread.h>
#include <NovaTime.h>

ThreadDemo* nova_ThreadDemo_ThreadDemo(ExceptionData* exceptionData);
void nova_del_ThreadDemo(ThreadDemo** this, ExceptionData* exceptionData);
void nova_static_ThreadDemo_main(ThreadDemo* this, ExceptionData* exceptionData, String** nova_0_args);

#endif