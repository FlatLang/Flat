#include "NativeConsole.h"

char* nova_getPass(char* message)
{
	return getpass(message);
}
