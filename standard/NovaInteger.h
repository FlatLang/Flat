#pragma once
#ifndef FILE_Integer_NOVA
#define FILE_Integer_NOVA

typedef struct Integer Integer;

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
#include "NovaInteger.h"
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Integer
{
	int (*nova_virtual_1_numDigits)(Integer*, ExceptionData*, int);
	String* (*nova_virtual_1_toString)(Integer*, ExceptionData*, int);
	String* (*nova_virtual_2_toString)(Integer*, ExceptionData*);
} nova_VTable_Integer;

CCLASS_CLASS
(
	Integer, 
	
	int nova_Integer_value;
	nova_VTable_Integer* vtable;
)

Integer* nova_Integer_Integer(Integer* this, ExceptionData* exceptionData, int nova_0_value);
void nova_del_Integer(Integer** this, ExceptionData* exceptionData);
int nova_static_1_Integer_numDigits(Integer* this, ExceptionData* exceptionData, int nova_0_number);
String* nova_static_1_Integer_toString(Integer* this, ExceptionData* exceptionData, int nova_0_value);
String* nova_2_Integer_toString(Integer* this, ExceptionData* exceptionData);

#endif