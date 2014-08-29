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
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_ClosureDemo
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_ClosureDemo;

CCLASS_CLASS
(
	ClosureDemo, 
	
	nova_VTable_ClosureDemo* vtable;
)

ClosureDemo* nova_ClosureDemo_construct(ClosureDemo* this, ExceptionData* exceptionData);
void nova_del_ClosureDemo(ClosureDemo** this, ExceptionData* exceptionData);
void nova_static_ClosureDemo_main(ClosureDemo* this, ExceptionData* exceptionData, String** nova_0_args);
void nova_ClosureDemo_this(ClosureDemo* this, ExceptionData* exceptionData);
void nova_ClosureDemo_super(ClosureDemo* this, ExceptionData* exceptionData);

#endif