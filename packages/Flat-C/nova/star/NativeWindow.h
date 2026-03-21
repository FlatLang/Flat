#ifndef NOVA_NATIVE_WINDOW
#define NOVA_NATIVE_WINDOW

#ifdef _WIN32
#	define WINDOW_ID_TYPE HWND
#	define MODULE_ID_TYPE HINSTANCE
#	define DEVICE_CONTEXT HDC
#	define PAINT_CONTEXT  PAINTSTRUCT

#	define WM_USER_INVALRECT (WM_USER + 100)
#	define WM_ADD_COMPONENT (WM_USER + 101)
#else
#   define WINDOW_ID_TYPE void*
#	define MODULE_ID_TYPE void*
#	define DEVICE_CONTEXT void*
#	define PAINT_CONTEXT  void*
#endif

#include <Nova.h>

#include <nova/star/NativeScrollBar.h>

#include <string.h>
#include <stdlib.h>

#ifdef _WIN32
typedef void (*nova_star_window_function)(void*, void*);
#endif

void nova_gui_init();

WINDOW_ID_TYPE nova_createWindow(nova_star_Nova_Window* window, nova_funcStruct* paintFunc, nova_funcStruct* addedFunc);
void nova_showWindow(WINDOW_ID_TYPE hwnd);

void GetDesktopResolution(int* horizontal, int* vertical);

#endif