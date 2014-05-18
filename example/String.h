#ifndef FILE_String_FATHOM
#define FILE_String_FATHOM

typedef struct String String;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include <string.h>

CCLASS_CLASS
(
	String, 
	
	int fathom_length;
	struct Private* prv;
)


String* fathom_String_String(ExceptionData* exceptionData, char* fathom_data_59);
void fathom_del_String(String** this, ExceptionData* exceptionData);
int fathom_String_getLength(String* this, ExceptionData* exceptionData);
char* fathom_String_toCharArray(String* this, ExceptionData* exceptionData);
String* fathom_String_concat(String* this, ExceptionData* exceptionData, String* fathom_str_78);
#endif