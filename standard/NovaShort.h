#pragma once
#ifndef FILE_Short_NOVA
#define FILE_Short_NOVA

typedef struct Short Short;

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
#include <NovaByte.h>
#include "NovaShort.h"
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Short
{
	int (*nova_virtual_1_numDigits)(Short*, ExceptionData*, short);
	String* (*nova_virtual_1_toString)(Short*, ExceptionData*, short);
	String* (*nova_virtual_2_toString)(Short*, ExceptionData*);
	int (*nova_virtual_2_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_3_toString)(Number*, ExceptionData*, int);
} nova_VTable_Short;

CCLASS_CLASS
(
	Short, 
	
	short nova_Short_value;
	nova_VTable_Short* vtable;
)

Short* nova_Short_Short(Short* this, ExceptionData* exceptionData, short nova_0_value);
void nova_del_Short(Short** this, ExceptionData* exceptionData);
int nova_static_1_Short_numDigits(Short* this, ExceptionData* exceptionData, short nova_0_number);
String* nova_static_1_Short_toString(Short* this, ExceptionData* exceptionData, short nova_0_value);
String* nova_2_Short_toString(Short* this, ExceptionData* exceptionData);

#endif