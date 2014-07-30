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
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaThread.h>
#include <NovaTime.h>
#include <NovaThreadDemoImplementation.h>

typedef struct nova_VTable_ThreadDemo
{
	String* (*nova_virtual_4_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_ThreadDemo;

CCLASS_CLASS
(
	ThreadDemo, 
	
	nova_VTable_ThreadDemo* vtable;
)

ThreadDemo* nova_ThreadDemo_ThreadDemo(ThreadDemo* this, ExceptionData* exceptionData);
void nova_del_ThreadDemo(ThreadDemo** this, ExceptionData* exceptionData);
void nova_static_ThreadDemo_main(ThreadDemo* this, ExceptionData* exceptionData, String** nova_0_args);

#endif