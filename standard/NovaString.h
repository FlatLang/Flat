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
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_String
{
	char (*nova_virtual_0_equals)(String*, ExceptionData*, String*);
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
} nova_VTable_String;

CCLASS_CLASS
(
	String, 
	
	nova_VTable_String* vtable;
	int nova_String_length;
	struct Private* prv;
)

String* nova_String_construct(String* this, ExceptionData* exceptionData, char* nova_0_data);
void nova_del_String(String** this, ExceptionData* exceptionData);
void nova_String_this(String* this, ExceptionData* exceptionData, char* nova_0_data);
char* nova_String_toCharArray(String* this, ExceptionData* exceptionData);
String* nova_String_concat(String* this, ExceptionData* exceptionData, String* nova_0_str);
char nova_0_String_equals(String* this, ExceptionData* exceptionData, String* nova_0_other);
void nova_String_super(String* this, ExceptionData* exceptionData);

#endif