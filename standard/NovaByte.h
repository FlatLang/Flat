#pragma once
#ifndef FILE_Byte_NOVA
#define FILE_Byte_NOVA

typedef struct Byte Byte;

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
#include "NovaByte.h"
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Byte
{
	int (*nova_virtual_numDigits)(Byte*, ExceptionData*, char);
	String* (*nova_virtual_0_toString)(Byte*, ExceptionData*, char);
	String* (*nova_virtual_1_toString)(Byte*, ExceptionData*);
	int (*nova_virtual_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_toString)(Number*, ExceptionData*, int);
} nova_VTable_Byte;

CCLASS_CLASS
(
	Byte, 
	
	char nova_Byte_value;
	nova_VTable_Byte* vtable;
)

Byte* nova_Byte_construct(Byte* this, ExceptionData* exceptionData, char nova_0_value);
void nova_del_Byte(Byte** this, ExceptionData* exceptionData);
int nova_static_Byte_numDigits(Byte* this, ExceptionData* exceptionData, char nova_0_number);
String* nova_static_0_Byte_toString(Byte* this, ExceptionData* exceptionData, char nova_0_value);
String* nova_1_Byte_toString(Byte* this, ExceptionData* exceptionData);

#endif