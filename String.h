#ifndef FILE_String_FATHOM
#define FILE_String_FATHOM

#include "CClass.h"

CLASS
(
String, 

FUNC(char*, toCharArray, String* __o__);
)


String* new_String(char* d);

#endif