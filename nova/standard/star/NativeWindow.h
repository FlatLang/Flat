#ifndef NOVA_NATIVE_WINDOW
#define NOVA_NATIVE_WINDOW

#ifdef _WIN32
#	include <windows.h>

#	define WINDOW_ID_TYPE HWND
#endif

#include <Nova.h>

#include <string.h>
#include <stdlib.h>

WINDOW_ID_TYPE nova_createWindow(int x, int y, int width, int height, char* title);

#endif
