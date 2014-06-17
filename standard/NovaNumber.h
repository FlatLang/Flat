#pragma once
#ifndef FILE_Number_NOVA
#define FILE_Number_NOVA

typedef struct Number Number;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"

Number* nova_Number_Number(ExceptionData* exceptionData);
void nova_del_Number(Number** this, ExceptionData* exceptionData);
int nova_Number_numDigits(ExceptionData* exceptionData, int nova_0_number);
String* nova_Number_toAString(ExceptionData* exceptionData, int nova_0_value);

#endif