#include "CClass.h"
#include "String.h"

static char* toCharArray(String* __o__);
String* new_String(char* d);

PRIVATE
(
char* data;
)

String* new_String(char* d)
{
NEW(String, __o__);

__o__->toCharArray = toCharArray;

__o__->prv->data = d;

return __o__;
}

static char* toCharArray(String* __o__)
{
return __o__->prv->data;
}
