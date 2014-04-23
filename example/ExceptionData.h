#ifndef FILE_ExceptionData_FATHOM
#define FILE_ExceptionData_FATHOM

typedef struct ExceptionData ExceptionData;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include <setjmp.h>
#include "ArrayList.h"

CLASS
(
	ExceptionData, 
	
	, struct Private* prv;
)

ExceptionData* fathom_ExceptionData_ExceptionData(ExceptionData* exceptionData, jmp_buf* fathom_buf_61);
void fathom_del_ExceptionData(ExceptionData** reference, ExceptionData* exceptionData);
ArrayList* fathom_ExceptionData_getCodes(ExceptionData* reference, ExceptionData* exceptionData);
void fathom_ExceptionData_addCode(ExceptionData* reference, ExceptionData* exceptionData, int fathom_code_67);
jmp_buf* fathom_ExceptionData_getBuffer(ExceptionData* reference, ExceptionData* exceptionData);
ExceptionData* fathom_ExceptionData_getCorrectData(ExceptionData* reference, ExceptionData* exceptionData, int fathom_code_73);
jmp_buf* fathom_ExceptionData_getCorrectBuffer(ExceptionData* reference, ExceptionData* exceptionData, int fathom_code_76);
void fathom_ExceptionData_jumpToBuffer(ExceptionData* reference, ExceptionData* exceptionData, int fathom_code_79);
ExceptionData* fathom_ExceptionData_getParent(ExceptionData* reference, ExceptionData* exceptionData);
void fathom_ExceptionData_setParent(ExceptionData* reference, ExceptionData* exceptionData, ExceptionData* fathom_p_85);
#endif