#include "NativeObject.h"

char* hashCode(nova_Nova_Object* obj)
{
	char* code = (char*)malloc(10 + 1);

	sprintf(&code[2], "%p", obj);

	code[0] = '0';
	code[1] = 'x';

	code[10] = '\0';

	return code;
}
