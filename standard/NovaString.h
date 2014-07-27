#pragma once
#ifndef FILE_String_NOVA
#define FILE_String_NOVA

typedef struct String String;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include "NovaString.h"
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
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_String
{
	char (*nova_virtual_1_equals)(String*, ExceptionData*, String*);
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_String;

CCLASS_CLASS
(
	String, 
	
	int nova_String_length;
	nova_VTable_String* vtable;
	struct Private* prv;
)

String* nova_String_String(ExceptionData* exceptionData, char* nova_0_data);
void nova_del_String(String** this, ExceptionData* exceptionData);
char* nova_String_toCharArray(String* this, ExceptionData* exceptionData);
String* nova_String_concat(String* this, ExceptionData* exceptionData, String* nova_0_str);
char nova_1_String_equals(String* this, ExceptionData* exceptionData, String* nova_0_other);

#endif