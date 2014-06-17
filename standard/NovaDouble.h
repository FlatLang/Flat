#pragma once
#ifndef FILE_Double_NOVA
#define FILE_Double_NOVA

typedef struct Double Double;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"

CCLASS_CLASS
(
	Double, 
	
	double nova_Double_value;
)

Double* nova_Double_Double(ExceptionData* exceptionData, double nova_0_value);
void nova_del_Double(Double** this, ExceptionData* exceptionData);
int nova_Double_numDigits(ExceptionData* exceptionData, double nova_0_number);
String* nova_Double_toAString(ExceptionData* exceptionData, double nova_0_value);
String* nova_Double_toString(Double* this, ExceptionData* exceptionData);

#endif