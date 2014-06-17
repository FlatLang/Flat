#pragma once
#ifndef FILE_ExceptionData_NOVA
#define FILE_ExceptionData_NOVA

typedef struct ExceptionData ExceptionData;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"
#include "NovaArrayList.h"

CCLASS_CLASS
(
	ExceptionData, 
	
	ArrayList* nova_ExceptionData_codes;
	struct Private* prv;
)

ExceptionData* nova_ExceptionData_ExceptionData(ExceptionData* exceptionData, jmp_buf* nova_0_buf);
void nova_del_ExceptionData(ExceptionData** this, ExceptionData* exceptionData);
void nova_ExceptionData_addCode(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code);
jmp_buf* nova_ExceptionData_getBuffer(ExceptionData* this, ExceptionData* exceptionData);
ExceptionData* nova_ExceptionData_getCorrectData(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code);
jmp_buf* nova_ExceptionData_getCorrectBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code);
void nova_ExceptionData_jumpToBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code);
ExceptionData* nova_ExceptionData_getParent(ExceptionData* this, ExceptionData* exceptionData);
void nova_ExceptionData_setParent(ExceptionData* this, ExceptionData* exceptionData, ExceptionData* nova_0_p);

#endif