#ifndef FILE_String_FATHOM
#define FILE_String_FATHOM

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"

typedef struct ExceptionData ExceptionData;

CLASS
(
String, 

FUNC(char*, toCharArray, String* __o__, ExceptionData* __FATHOM__exception_data);
)

String* new_String(ExceptionData* __FATHOM__exception_data, char* d);
void del_String(String* __o__, ExceptionData* __FATHOM__exception_data);
#endif