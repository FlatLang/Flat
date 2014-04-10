#ifndef FILE_ExceptionData_FATHOM
#define FILE_ExceptionData_FATHOM

typedef struct ExceptionData ExceptionData;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include <setjmp.h>
#include "ArrayList.h"

CLASS
(
ExceptionData, 

FUNC(ArrayList*, getCodes, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
FUNC(void, addCode, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
FUNC(jmp_buf*, getBuffer, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
FUNC(ExceptionData*, getCorrectData, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
FUNC(jmp_buf*, getCorrectBuffer, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
FUNC(void, jumpToBuffer, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, int code);
FUNC(ExceptionData*, getParent, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
FUNC(void, setParent, ExceptionData* __o__, ExceptionData* __FATHOM__exception_data, ExceptionData* p);
)

ExceptionData* new_ExceptionData(ExceptionData* __FATHOM__exception_data, jmp_buf* buf);
void del_ExceptionData(ExceptionData* __o__, ExceptionData* __FATHOM__exception_data);
#endif