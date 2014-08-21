#include <precompiled.h>
#include "NativeObject.h"

char* hashCode(Object* obj)
{
	char* code = (char*)malloc(8 + 1);

	sprintf(code, "%p", obj);

	code[8] = '\0';

	return code;
}
