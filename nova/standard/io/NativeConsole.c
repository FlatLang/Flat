#include "NativeConsole.h"

void nova_setEcho(char echo)
{
#ifdef _WIN32
	HANDLE hStdin = GetStdHandle(STD_INPUT_HANDLE);
	DWORD mode = 0;
	GetConsoleMode(hStdin, &mode);

	if (echo)
	{
		SetConsoleMode(hStdin, mode | (ENABLE_ECHO_INPUT));
	}
	else
	{
		SetConsoleMode(hStdin, mode & (~ENABLE_ECHO_INPUT));
	}
#else
	if (echo)
	{
		system("echo on");
	}
	else
	{
		system("echo off");
	}
#endif
}

void nova_clearScreen()
{
#ifdef _WIN32
	system("cls");
#else
	system("clear");
#endif
}
