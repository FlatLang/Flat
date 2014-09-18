#pragma once
#ifndef FILE_DivideByZeroException_NOVA
#define FILE_DivideByZeroException_NOVA

typedef struct DivideByZeroException DivideByZeroException;

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
#include "NovaDivideByZeroException.h"

typedef struct nova_VTable_DivideByZeroException
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_DivideByZeroException;

CCLASS_CLASS
(
	DivideByZeroException, 
	
	nova_VTable_DivideByZeroException* vtable;
)

DivideByZeroException* nova_DivideByZeroException_construct(DivideByZeroException* this, ExceptionData* exceptionData);
void nova_del_DivideByZeroException(DivideByZeroException** this, ExceptionData* exceptionData);
void nova_DivideByZeroException_this(DivideByZeroException* this, ExceptionData* exceptionData);
void nova_DivideByZeroException_super(DivideByZeroException* this, ExceptionData* exceptionData);

#endif