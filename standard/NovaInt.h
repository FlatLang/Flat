#pragma once
#ifndef FILE_Int_NOVA
#define FILE_Int_NOVA

typedef struct Int Int;

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
#include "NovaInt.h"
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Int
{
	int (*nova_virtual_numDigits)(Int*, ExceptionData*, int);
	String* (*nova_virtual_0_toString)(Int*, ExceptionData*, int);
	String* (*nova_virtual_1_toString)(Int*, ExceptionData*);
} nova_VTable_Int;

CCLASS_CLASS
(
	Int, 
	
	int nova_Int_value;
	nova_VTable_Int* vtable;
)

Int* nova_Int_construct(Int* this, ExceptionData* exceptionData, int nova_0_value);
void nova_del_Int(Int** this, ExceptionData* exceptionData);
int nova_static_Int_numDigits(Int* this, ExceptionData* exceptionData, int nova_0_number);
String* nova_static_0_Int_toString(Int* this, ExceptionData* exceptionData, int nova_0_value);
String* nova_1_Int_toString(Int* this, ExceptionData* exceptionData);

#endif