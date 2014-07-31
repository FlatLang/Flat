#pragma once
#ifndef FILE_ExceptionData_NOVA
#define FILE_ExceptionData_NOVA

typedef struct ExceptionData ExceptionData;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include <NovaObject.h>
#include <NovaString.h>
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
#include <NovaArrayList.h>

typedef struct nova_VTable_ExceptionData
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_ExceptionData;

CCLASS_CLASS
(
	ExceptionData, 
	
	ArrayList* nova_ExceptionData_codes;
	nova_VTable_ExceptionData* vtable;
	struct Private* prv;
)

ExceptionData* nova_ExceptionData_ExceptionData(ExceptionData* this, ExceptionData* exceptionData, buffer* nova_0_buf);
void nova_del_ExceptionData(ExceptionData** this, ExceptionData* exceptionData);
void nova_ExceptionData_addCode(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code);
buffer* nova_ExceptionData_getBuffer(ExceptionData* this, ExceptionData* exceptionData);
ExceptionData* nova_ExceptionData_getCorrectData(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code);
buffer* nova_ExceptionData_getCorrectBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code);
void nova_ExceptionData_jumpToBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code);
ExceptionData* nova_ExceptionData_getParent(ExceptionData* this, ExceptionData* exceptionData);
void nova_ExceptionData_setParent(ExceptionData* this, ExceptionData* exceptionData, ExceptionData* nova_0_p);

#endif