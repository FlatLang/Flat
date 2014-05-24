#ifndef FILE_ExceptionData_NOVA
#define FILE_ExceptionData_NOVA

typedef struct ExceptionData ExceptionData;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"
#include <setjmp.h>
#include "ArrayList.h"

CCLASS_CLASS
(
	ExceptionData, 
	
	struct Private* prv;
)


ExceptionData* nova_ExceptionData_ExceptionData(ExceptionData* exceptionData, jmp_buf* buf);
void nova_del_ExceptionData(ExceptionData** this, ExceptionData* exceptionData);
ArrayList* nova_ExceptionData_getCodes(ExceptionData* this, ExceptionData* exceptionData);
void nova_ExceptionData_addCode(ExceptionData* this, ExceptionData* exceptionData, int nova_ExceptionData_code_160);
jmp_buf* nova_ExceptionData_getBuffer(ExceptionData* this, ExceptionData* exceptionData);
ExceptionData* nova_ExceptionData_getCorrectData(ExceptionData* this, ExceptionData* exceptionData, int nova_ExceptionData_code_172);
jmp_buf* nova_ExceptionData_getCorrectBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_ExceptionData_code_178);
void nova_ExceptionData_jumpToBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_ExceptionData_code_181);
ExceptionData* nova_ExceptionData_getParent(ExceptionData* this, ExceptionData* exceptionData);
void nova_ExceptionData_setParent(ExceptionData* this, ExceptionData* exceptionData, ExceptionData* nova_ExceptionData_p_187);
#endif