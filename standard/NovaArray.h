#pragma once
#ifndef FILE_Array_NOVA
#define FILE_Array_NOVA

typedef struct Array Array;

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

CCLASS_CLASS
(
	Array, 
	
	int nova_Array_length;
)

Array* nova_Array_Array(ExceptionData* exceptionData);
void nova_del_Array(Array** this, ExceptionData* exceptionData);

#endif