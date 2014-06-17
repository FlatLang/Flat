#pragma once
#ifndef FILE_Char_NOVA
#define FILE_Char_NOVA

typedef struct Char Char;

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
#include "NovaDivideByZeroException.h"

CCLASS_CLASS
(
	Char, 
	
	char nova_Char_value;
)

Char* nova_Char_Char(ExceptionData* exceptionData, char nova_0_value);
void nova_del_Char(Char** this, ExceptionData* exceptionData);
String* nova_Char_toString(Char* this, ExceptionData* exceptionData);

#endif