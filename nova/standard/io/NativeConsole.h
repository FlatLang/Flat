#ifndef NATIVE_CONSOLE
#define NATIVE_CONSOLE

#include <unistd.h>

#ifdef _WIN32
	#include <windows.h>
#endif

void nova_setEcho(char echo);
void nova_clearScreen();

#endif
