#include "CClass.h"
#include "String.h"

String* new_String(jmp_buf __Fathom__jmp_bufchar* d);
void del_String(String* __o__);
static char* toCharArray(String* __o__, jmp_buf __Fathom__jmp_buf);

PRIVATE
(
char* data;
)

String* new_String(jmp_buf __Fathom__jmp_bufchar* d)
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

static char* toCharArray(String* __o__, jmp_buf __Fathom__jmp_buf)
{
return __o__->prv->data;
}
