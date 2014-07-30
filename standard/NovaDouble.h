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
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include "NovaDouble.h"
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Double
{
	String* (*nova_virtual_2_toString)(Double*, ExceptionData*);
	int (*nova_virtual_2_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_3_toString)(Number*, ExceptionData*, int);
} nova_VTable_Double;

CCLASS_CLASS
(
	Double, 
	
	double nova_Double_value;
	nova_VTable_Double* vtable;
)

Double* nova_Double_Double(Double* this, ExceptionData* exceptionData, double nova_0_value);
void nova_del_Double(Double** this, ExceptionData* exceptionData);
int nova_static_1_Double_numDigits(Double* this, ExceptionData* exceptionData, double nova_0_number);
String* nova_static_1_Double_toString(Double* this, ExceptionData* exceptionData, double nova_0_value);
String* nova_2_Double_toString(Double* this, ExceptionData* exceptionData);

#endif