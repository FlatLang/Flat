#ifndef FILE_String_FATHOM
#define FILE_String_FATHOM

#include "CClass.h"
#include "ExceptionHandler.h"

CLASS
(
String, 

FUNC(char*, toCharArray, String* __o__, jmp_buf __Fathom__jmp_buf);
)

String* new_String(jmp_buf __Fathom__jmp_bufchar* d);
void del_String(String* __o__);
#endif