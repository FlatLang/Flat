#pragma once
#ifndef FILE_String_NOVA
#define FILE_String_NOVA

typedef struct String String;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"
#include <string.h>

CCLASS_CLASS
(
	String, 
	
	int nova_String_length;
	struct Private* prv;
)

String* nova_String_String(ExceptionData* exceptionData, char* nova_0_data);
void nova_del_String(String** this, ExceptionData* exceptionData);
char* nova_String_toCharArray(String* this, ExceptionData* exceptionData);
String* nova_String_concat(String* this, ExceptionData* exceptionData, String* nova_0_str);

#endif