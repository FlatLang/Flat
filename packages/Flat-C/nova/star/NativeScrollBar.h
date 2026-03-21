#ifndef NOVA_NATIVE_SCROLLBAR
#define NOVA_NATIVE_SCROLLBAR

#ifdef _WIN32
#	ifndef SPI_GETWHEELSCROLLCHARS
#	   define SPI_GETWHEELSCROLLCHARS 0x006C
#	endif
#	ifndef WM_MOUSEHWHEEL
#	   define WM_MOUSEHWHEEL          0x020E
#	endif
#endif

#include <Nova.h>

#include <string.h>
#include <stdlib.h>

#include <limits.h>

#ifdef _WIN32
void nova_init_scrollbar();
LRESULT CALLBACK nova_scroll_proc(HWND hwnd, UINT uMsg, WPARAM wParam, LPARAM lParam);
int WheelScrollLines(HWND hwnd, int iDelta, UINT nPage, BOOL isVertical);
void CustomHandleMouseWheel(HWND hwnd, int iDelta, BOOL isVertical);
#endif

#endif