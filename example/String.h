#ifndef FILE_String_FATHOM
#define FILE_String_FATHOM

typedef struct String String;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

CLASS
(
	String, 
	
	FUNC(char*, toCharArray, String* __o__, ExceptionData* __FATHOM__exception_data);
)

String* new_String(ExceptionData* __FATHOM__exception_data, char* data);
void del_String(String** __o__, ExceptionData* __FATHOM__exception_data);
#endif