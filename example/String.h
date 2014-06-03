#ifndef FILE_String_NOVA
#define FILE_String_NOVA

typedef struct String String;

#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"
#include <string.h>

CCLASS_CLASS
(
	String, 
	
	int nova_String_length;
	struct Private* prv;
)


String* nova_String_String(ExceptionData* exceptionData, char* nova_0_data);
void nova_del_String(String** this, ExceptionData* exceptionData);
char* nova_String_toCharArray(String* this, ExceptionData* exceptionData);
String* nova_String_concat(String* this, ExceptionData* exceptionData, String* nova_0_str);
#endif