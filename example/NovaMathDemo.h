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
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaTime.h>

typedef struct nova_VTable_MathDemo
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_MathDemo;

CCLASS_CLASS
(
	MathDemo, 
	
	nova_VTable_MathDemo* vtable;
)

MathDemo* nova_MathDemo_construct(MathDemo* this, ExceptionData* exceptionData);
void nova_del_MathDemo(MathDemo** this, ExceptionData* exceptionData);
void nova_static_MathDemo_main(MathDemo* this, ExceptionData* exceptionData, String** nova_0_args);
void nova_MathDemo_this(MathDemo* this, ExceptionData* exceptionData);
void nova_MathDemo_super(MathDemo* this, ExceptionData* exceptionData);

#endif