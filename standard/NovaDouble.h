#pragma once
#ifndef FILE_Double_NOVA
#define FILE_Double_NOVA

typedef struct Double Double;

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
#include "NovaDouble.h"
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Double
{
	String* (*nova_virtual_3_toString)(Double*, ExceptionData*);
	int (*nova_virtual_0_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_1_toString)(Number*, ExceptionData*, int);
} nova_VTable_Double;

CCLASS_CLASS
(
	Double, 
	
	nova_VTable_Double* vtable;
	double nova_Double_value;
)

Double* nova_Double_construct(Double* this, ExceptionData* exceptionData, double nova_0_value);
void nova_del_Double(Double** this, ExceptionData* exceptionData);
void nova_Double_this(Double* this, ExceptionData* exceptionData, double nova_0_value);
int nova_static_Double_numDigits(Double* this, ExceptionData* exceptionData, double nova_0_number);
String* nova_static_2_Double_toString(Double* this, ExceptionData* exceptionData, double nova_0_value);
String* nova_3_Double_toString(Double* this, ExceptionData* exceptionData);
void nova_Double_super(Double* this, ExceptionData* exceptionData);

#endif