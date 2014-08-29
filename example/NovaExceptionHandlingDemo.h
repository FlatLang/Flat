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
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaNonWholeDivisionException.h>

typedef struct nova_VTable_ExceptionHandlingDemo
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_ExceptionHandlingDemo;

CCLASS_CLASS
(
	ExceptionHandlingDemo, 
	
	nova_VTable_ExceptionHandlingDemo* vtable;
)

ExceptionHandlingDemo* nova_ExceptionHandlingDemo_construct(ExceptionHandlingDemo* this, ExceptionData* exceptionData);
void nova_del_ExceptionHandlingDemo(ExceptionHandlingDemo** this, ExceptionData* exceptionData);
void nova_static_ExceptionHandlingDemo_main(ExceptionHandlingDemo* this, ExceptionData* exceptionData, String** nova_0_args);
void nova_ExceptionHandlingDemo_this(ExceptionHandlingDemo* this, ExceptionData* exceptionData);
void nova_ExceptionHandlingDemo_super(ExceptionHandlingDemo* this, ExceptionData* exceptionData);

#endif