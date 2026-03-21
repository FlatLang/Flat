#include "NativeWindow.h"

#ifdef _WIN32
HINSTANCE nova_hInstance;

__thread nova_star_Nova_Window* threadWindow;
__thread nova_funcStruct* threadPaintFunc;
__thread nova_funcStruct* threadAddedFunc;

LRESULT CALLBACK WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	PAINTSTRUCT ps;
	HDC hdc;
	INITCOMMONCONTROLSEX icex;
	LRESULT result;
	
	switch (msg)
	{
		case WM_CREATE:
		    icex.dwSize = sizeof(INITCOMMONCONTROLSEX);
		    icex.dwICC = ICC_STANDARD_CLASSES;
		    InitCommonControlsEx(&icex);
		    nova_init_scrollbar();
		    
		    break;
		case WM_USER_INVALRECT:
			InvalidateRect(hwnd, NULL, FALSE);
        	UpdateWindow(hwnd);
        	break;
		case WM_ADD_COMPONENT:
			nova_star_Nova_UIComponent_virtual_Nova_onAdded((nova_star_Nova_UIComponent*)threadAddedFunc->ref);
        	break;
		case WM_ERASEBKGND:
			// {
			// 	RECT r;
			// 	hdc = BeginPaint(hwnd, &ps);
			// 	GetClientRect(hwnd, &r);
			// 	HBRUSH brush = CreateSolidBrush(RGB(255, 255, 255));
			// 	FillRect(hdc, &r, brush);
			// 	DeleteObject(brush);
			// 	return 1;
			// }
			break;
		case WM_COMMAND:
			nova_star_Nova_UIComponent_virtual_Nova_searchActionTarget((nova_star_Nova_UIComponent*)threadWindow->frame, (int)LOWORD(wParam));
			
            break;
		case WM_PAINT:
			hdc = BeginPaint(hwnd, &ps);
			
			threadWindow->ps = ps;
			threadWindow->hdc = hdc;
			
			SetBkMode(hdc, TRANSPARENT);
			
			// ((nova_star_window_function)threadPaintFunc->func)(threadPaintFunc->ref, threadPaintFunc->context);
			
			EndPaint(hwnd, &ps);
			break;
		case WM_DESTROY:
			PostQuitMessage(0);
			return 0;
		default: 
			if (result = nova_scroll_proc(hwnd, msg, wParam, lParam)) {
				return result;
			}
	}

	return DefWindowProcW(hwnd, msg, wParam, lParam);
}

LRESULT CALLBACK EmptyWndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	PAINTSTRUCT ps;
	HDC hdc;
	INITCOMMONCONTROLSEX icex;
	LRESULT result;
	
	switch (msg)
	{
		case WM_CREATE:
		    break;
		case WM_USER_INVALRECT:
			// InvalidateRect(hwnd, NULL, FALSE);
   //      	UpdateWindow(hwnd);
        	break;
		case WM_ADD_COMPONENT:
			((nova_star_window_function)threadAddedFunc->func)(threadAddedFunc->ref, threadAddedFunc->context);
        	break;
		case WM_ERASEBKGND:
			{
				// RECT r;
				// GetClientRect(hwnd, &r);
				// HBRUSH brush = CreateSolidBrush(RGB(255, 255, 255));
				// FillRect(threadWindow->hdc, &r, brush);
				// DeleteObject(brush);
			}
			break;
		case WM_COMMAND:
			// nova_star_Nova_UIComponent_virtual_Nova_searchActionTarget((nova_star_Nova_UIComponent*)threadWindow->frame, (int)LOWORD(wParam));
			
            break;
		case WM_PAINT:
			hdc = BeginPaint(hwnd, &ps);
			
			threadWindow->ps = ps;
			threadWindow->hdc = hdc;
			
			SetBkMode(hdc, TRANSPARENT);
			
			nova_star_Nova_UIComponent_virtual_Nova_draw((nova_star_Nova_UIComponent*)GetProp(hwnd, "reference"));
			
			EndPaint(hwnd, &ps);
			break;
		case WM_DESTROY:
			PostQuitMessage(0);
			return 0;
		// default: 
		// 	if (result = nova_scroll_proc(hwnd, msg, wParam, lParam)) {
		// 		return result;
		// 	}
	}

	return DefWindowProcW(hwnd, msg, wParam, lParam);
}

#endif

void nova_gui_init() {
#ifdef _WIN32
	nova_hInstance = GetModuleHandle(NULL);
	
	WNDCLASSW wc;
	WNDCLASSW emptyPanel;

	wc.style         = CS_HREDRAW | CS_VREDRAW;
	wc.cbClsExtra    = 0;
	wc.cbWndExtra    = 0;
	wc.lpszClassName = L"Window";
	wc.hInstance     = nova_hInstance;
	wc.hbrBackground = GetSysColorBrush(COLOR_3DFACE);
	wc.lpszMenuName  = NULL;
	wc.lpfnWndProc   = WndProc;
	wc.hCursor       = LoadCursor(NULL, IDC_ARROW);
	wc.hIcon         = LoadIcon(NULL, IDI_APPLICATION);

	ZeroMemory(&emptyPanel, sizeof(WNDCLASSW));
	emptyPanel.style         = CS_HREDRAW | CS_VREDRAW;
	emptyPanel.lpszClassName = L"Empty Panel";
	emptyPanel.hInstance     = nova_hInstance;
	emptyPanel.hbrBackground = GetSysColorBrush(COLOR_3DFACE);
	emptyPanel.lpfnWndProc   = EmptyWndProc;
	emptyPanel.hCursor       = LoadCursor(NULL, IDC_ARROW);
	emptyPanel.hIcon         = LoadIcon(NULL, IDI_APPLICATION);
	
	RegisterClassW(&wc);
	RegisterClassW(&emptyPanel);
#endif
}

WINDOW_ID_TYPE nova_createWindow(nova_star_Nova_Window* window, nova_funcStruct* paintFunc, nova_funcStruct* addedFunc)
{
#ifdef _WIN32
	MSG msg;
	HWND hwnd;
	
	threadWindow = window;
	threadPaintFunc = paintFunc;
	threadAddedFunc = addedFunc;
	
	hwnd = CreateWindowW(L"Window",
		nova_Nova_String_Nova_toWide(window->title),
		WS_OVERLAPPEDWINDOW | WS_VSCROLL | ES_AUTOVSCROLL,
		window->x, window->y,
		window->width, window->height,
		NULL,
		NULL,
		nova_hInstance,
		NULL);
	
	window->hwnd = hwnd;
	window->hinstance = nova_hInstance;
	
	while (GetMessage(&msg, NULL, 0, 0))
	{
		DispatchMessage(&msg);
	}
	
	return hwnd;
#else
    return 0;
#endif
}

void nova_showWindow(WINDOW_ID_TYPE hwnd) {
#ifdef _WIN32
	ShowWindow(hwnd, SW_SHOWDEFAULT);
	UpdateWindow(hwnd);
#endif
}

// Get the horizontal and vertical screen sizes in pixel
void GetDesktopResolution(int* horizontal, int* vertical) {
#ifdef _WIN32
	RECT desktop;
	// Get a handle to the desktop window
	const HWND hDesktop = GetDesktopWindow();
	// Get the size of screen to the variable desktop
	GetWindowRect(hDesktop, &desktop);
	// The top left corner will have coordinates (0,0)
	// and the bottom right corner will have coordinates
	// (horizontal, vertical)
	*horizontal = desktop.right;
	*vertical = desktop.bottom;
#endif
}