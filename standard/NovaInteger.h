#pragma once
#ifndef FILE_Integer_NOVA
#define FILE_Integer_NOVA

typedef struct Integer Integer;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"

CCLASS_CLASS
(
	Integer, 
	
	int nova_Integer_value;
)

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_0_value);
void nova_del_Integer(Integer** this, ExceptionData* exceptionData);
int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_0_number);
String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_0_value);
String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData);

#endif