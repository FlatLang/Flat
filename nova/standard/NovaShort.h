#pragma once
#ifndef FILE_Short_NOVA
#define FILE_Short_NOVA

typedef struct Short Short;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaNull.h>
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
	String* (*nova_virtual_2_toString)(Short*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
	int (*nova_virtual_0_numDigits)(Short*, ExceptionData*, short);
	String* (*nova_virtual_1_toString)(Short*, ExceptionData*, short);
} nova_VTable_Short;

CCLASS_CLASS
(
	Short, 
	
	nova_VTable_Short* vtable;
	short nova_Short_value;
)

Short* nova_Short_construct(Short* this, ExceptionData* exceptionData, short nova_0_value);
void nova_del_Short(Short** this, ExceptionData* exceptionData);
void nova_Short_this(Short* this, ExceptionData* exceptionData, short nova_0_value);
int nova_static_0_Short_numDigits(Short* this, ExceptionData* exceptionData, short nova_0_number);
String* nova_static_1_Short_toString(Short* this, ExceptionData* exceptionData, short nova_0_value);
String* nova_2_Short_toString(Short* this, ExceptionData* exceptionData);
void nova_Short_super(Short* this, ExceptionData* exceptionData);

#endif