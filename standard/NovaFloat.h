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
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include "NovaFloat.h"
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Float
{
	String* (*nova_virtual_3_toString)(Float*, ExceptionData*);
	int (*nova_virtual_0_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_1_toString)(Number*, ExceptionData*, int);
} nova_VTable_Float;

CCLASS_CLASS
(
	Float, 
	
	nova_VTable_Float* vtable;
	float nova_Float_value;
)

Float* nova_Float_construct(Float* this, ExceptionData* exceptionData, int nova_0_value);
void nova_del_Float(Float** this, ExceptionData* exceptionData);
void nova_Float_this(Float* this, ExceptionData* exceptionData, int nova_0_value);
int nova_static_Float_numDigits(Float* this, ExceptionData* exceptionData, float nova_0_number);
String* nova_static_2_Float_toString(Float* this, ExceptionData* exceptionData, float nova_0_value);
String* nova_3_Float_toString(Float* this, ExceptionData* exceptionData);
void nova_Float_super(Float* this, ExceptionData* exceptionData);

#endif