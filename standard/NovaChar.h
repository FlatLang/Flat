#pragma once
#ifndef FILE_Char_NOVA
#define FILE_Char_NOVA

typedef struct Char Char;

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
#include <NovaDouble.h>
#include "NovaChar.h"
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Char
{
	String* (*nova_virtual_1_toString)(Char*, ExceptionData*, char);
	String* (*nova_virtual_2_toString)(Char*, ExceptionData*);
	int (*nova_virtual_2_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_3_toString)(Number*, ExceptionData*, int);
} nova_VTable_Char;

CCLASS_CLASS
(
	Char, 
	
	char nova_Char_value;
	nova_VTable_Char* vtable;
)

Char* nova_Char_Char(Char* this, ExceptionData* exceptionData, char nova_0_value);
void nova_del_Char(Char** this, ExceptionData* exceptionData);
String* nova_1_Char_toString(Char* this, ExceptionData* exceptionData, char nova_0_c);
String* nova_2_Char_toString(Char* this, ExceptionData* exceptionData);

#endif