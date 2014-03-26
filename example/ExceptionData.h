#ifndef FILE_ExceptionData_FATHOM
#define FILE_ExceptionData_FATHOM

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include <setjmp.h>

CLASS
(
ExceptionData, 

FUNC(jmp_buf*, getBuffer, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
FUNC(jmp_buf*, getCorrectBuffer, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
FUNC(int, getNumCodes, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
FUNC(int*, getExceptionCodes, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
FUNC(ExceptionData*, getParent, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
FUNC(void, setParent, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, ExceptionData* p);
)

ExceptionData* new_ExceptionData(ExceptionData* __FATHOM__exception_data, jmp_buf* buf, int* codes, int num);
void del_ExceptionData(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
#endif