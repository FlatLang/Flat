#include "CClass.h"
#include "String.h"

String* new_String();
static char* toCharArray(String* __o__);

PRIVATE
(
	char* data;
)

String* new_String()
{
	NEW(String, __o__);
	
	
	return __o__;
}

static char* toCharArray(String* __o__)
{
	return __o__->prv->data;
}
