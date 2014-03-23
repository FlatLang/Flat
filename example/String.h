#ifndef FILE_String_FATHOM
#define FILE_String_FATHOM

#include "CClass.h"
#include "ExceptionHandler.h"

CLASS
(
String, 

FUNC(char*, toCharArray, String* __o__);
)

String* new_String(char* d);
void del_String(String* __o__);
#endif