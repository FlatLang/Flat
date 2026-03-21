#ifndef NATIVE_CONSOLE
#define NATIVE_CONSOLE

#ifdef _WIN32
	#include <windows.h>
#else
	#include <stdio.h>
	#include <stdlib.h>
	#include <unistd.h>
	#include <termios.h>
	#include <ctype.h>
#endif

void nova_setEcho(char echo);
void nova_clearScreen();

#endif
