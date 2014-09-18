#pragma once
#ifndef FILE_GenericDemo_NOVA
#define FILE_GenericDemo_NOVA

typedef struct GenericDemo GenericDemo;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaNull.h>
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
#include <NovaStack.h>

typedef struct nova_VTable_GenericDemo
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_GenericDemo;

CCLASS_CLASS
(
	GenericDemo, 
	
	nova_VTable_GenericDemo* vtable;
)

GenericDemo* nova_0_GenericDemo_construct(GenericDemo* this, ExceptionData* exceptionData);
void nova_del_GenericDemo(GenericDemo** this, ExceptionData* exceptionData);
void nova_static_GenericDemo_main(GenericDemo* this, ExceptionData* exceptionData, String** nova_0_args);
void nova_GenericDemo_this(GenericDemo* this, ExceptionData* exceptionData);
void nova_GenericDemo_super(GenericDemo* this, ExceptionData* exceptionData);

#endif