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
	
	, struct Private* prv;
)

String* fathom_String_String(ExceptionData* exceptionData, char* fathom_data_38);
void fathom_del_String(String** reference, ExceptionData* exceptionData);
char* fathom_String_toCharArray(String* reference, ExceptionData* exceptionData);
void fathom_String_do1(String* reference, ExceptionData* exceptionData);
void fathom_String_do2(String* reference, ExceptionData* exceptionData);
void fathom_String_do3(String* reference, ExceptionData* exceptionData);
void fathom_String_do4(String* reference, ExceptionData* exceptionData);
void fathom_String_do5(String* reference, ExceptionData* exceptionData);
void fathom_String_do6(String* reference, ExceptionData* exceptionData);
#endif