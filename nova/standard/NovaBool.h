#pragma once
#ifndef FILE_Bool_NOVA
#define FILE_Bool_NOVA

typedef struct Bool Bool;

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

typedef struct nova_VTable_Bool
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Bool;

CCLASS_CLASS
(
	Bool, 
	
	nova_VTable_Bool* vtable;
)

Bool* nova_0_Bool_construct(Bool* this, ExceptionData* exceptionData);
void nova_del_Bool(Bool** this, ExceptionData* exceptionData);
void nova_Bool_this(Bool* this, ExceptionData* exceptionData);
void nova_Bool_super(Bool* this, ExceptionData* exceptionData);

#endif