#pragma once
#ifndef FILE_Float_NOVA
#define FILE_Float_NOVA

typedef struct Float Float;

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
#include "NovaFloat.h"
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Float
{
	String* (*nova_virtual_2_toString)(Float*, ExceptionData*);
	int (*nova_virtual_2_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_3_toString)(Number*, ExceptionData*, int);
} nova_VTable_Float;

CCLASS_CLASS
(
	Float, 
	
	float nova_Float_value;
	nova_VTable_Float* vtable;
)

Float* nova_Float_Float(ExceptionData* exceptionData, int nova_0_value);
void nova_del_Float(Float** this, ExceptionData* exceptionData);
int nova_static_1_Float_numDigits(Float* this, ExceptionData* exceptionData, float nova_0_number);
String* nova_static_1_Float_toString(Float* this, ExceptionData* exceptionData, float nova_0_value);
String* nova_2_Float_toString(Float* this, ExceptionData* exceptionData);

#endif