#include "CClass.h"
#include "String.h"

String* new_String(char* d);
void del_String(String* __o__);
static char* toCharArray(String* __o__);

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

void del_String(String* __o__)
{
if (!__o__)
{
return;
}

free(__o__->prv->data);
free(__o__->prv);

free(__o__);
}

static char* toCharArray(String* __o__)
{
return __o__->prv->data;
}
