#include <precompiled.h>
#include "NativeObject.h"

char* hashCode(nova_standard_NovaObject* obj)
{
	char* code = (char*)malloc(8 + 1);

	sprintf(code, "%p", obj);

	code[8] = '\0';

	return code;
}
