#pragma once
#ifndef FILE_Long_NOVA
#define FILE_Long_NOVA

typedef struct Long Long;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"

CCLASS_CLASS
(
	Long, 
	
	long_long nova_Long_value;
)

Long* nova_Long_Long(ExceptionData* exceptionData, long_long nova_0_value);
void nova_del_Long(Long** this, ExceptionData* exceptionData);
int nova_Long_numDigits(ExceptionData* exceptionData, long_long nova_0_number);
String* nova_Long_toAString(ExceptionData* exceptionData, long_long nova_0_value);
String* nova_Long_toString(Long* this, ExceptionData* exceptionData);

#endif