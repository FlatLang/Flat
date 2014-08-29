#pragma once
#ifndef FILE_IntegerTest_NOVA
#define FILE_IntegerTest_NOVA

typedef struct IntegerTest IntegerTest;

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

typedef struct nova_VTable_IntegerTest
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_IntegerTest;

CCLASS_CLASS
(
	IntegerTest, 
	
	nova_VTable_IntegerTest* vtable;
)

IntegerTest* nova_IntegerTest_construct(IntegerTest* this, ExceptionData* exceptionData);
void nova_del_IntegerTest(IntegerTest** this, ExceptionData* exceptionData);
void nova_static_IntegerTest_main(IntegerTest* this, ExceptionData* exceptionData, String** nova_0_args);
void nova_IntegerTest_this(IntegerTest* this, ExceptionData* exceptionData);
void nova_IntegerTest_super(IntegerTest* this, ExceptionData* exceptionData);

#endif