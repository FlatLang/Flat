#ifndef FILE_ExceptionData_FATHOM
#define FILE_ExceptionData_FATHOM

typedef struct ExceptionData ExceptionData;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include <setjmp.h>
#include "ArrayList.h"

CCLASS_CLASS
(
	ExceptionData, 
	
	struct Private* prv;
)


ExceptionData* fathom_ExceptionData_ExceptionData(ExceptionData* exceptionData, jmp_buf* buf);
void fathom_del_ExceptionData(ExceptionData** this, ExceptionData* exceptionData);
ArrayList* fathom_ExceptionData_getCodes(ExceptionData* this, ExceptionData* exceptionData);
void fathom_ExceptionData_addCode(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_119);
jmp_buf* fathom_ExceptionData_getBuffer(ExceptionData* this, ExceptionData* exceptionData);
ExceptionData* fathom_ExceptionData_getCorrectData(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_125);
jmp_buf* fathom_ExceptionData_getCorrectBuffer(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_128);
void fathom_ExceptionData_jumpToBuffer(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_131);
ExceptionData* fathom_ExceptionData_getParent(ExceptionData* this, ExceptionData* exceptionData);
void fathom_ExceptionData_setParent(ExceptionData* this, ExceptionData* exceptionData, ExceptionData* fathom_p_140);
#endif