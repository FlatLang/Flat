#ifndef FILE_String_NOVA
#define FILE_String_NOVA

typedef struct String String;

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
#include <string.h>

CCLASS_CLASS
(
	String, 
	
	int nova_String_length;
	struct Private* prv;
)


String* nova_String_String(ExceptionData* exceptionData, char* nova_String_data_11);
void nova_del_String(String** this, ExceptionData* exceptionData);
char* nova_String_toCharArray(String* this, ExceptionData* exceptionData);
String* nova_String_concat(String* this, ExceptionData* exceptionData, String* nova_String_str_27);
#endif