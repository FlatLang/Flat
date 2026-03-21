#include "NativeScrollBar.h"

#ifdef _WIN32
// Lock protecting the static variables. Note you have to initialize the
// critical section before calling the function WheelScrollLines() below
// for the first time.
static CRITICAL_SECTION csWheelLock;

int scrollUnit = 10;

void nova_init_scrollbar() {
    InitializeCriticalSection(&csWheelLock);
}

LRESULT CALLBACK nova_scroll_proc(HWND hwnd, UINT uMsg, WPARAM wParam, LPARAM lParam)
{
    switch(uMsg) {
        case WM_MOUSEWHEEL:
            CustomHandleMouseWheel(hwnd, GET_WHEEL_DELTA_WPARAM(wParam), TRUE);
            break;
        case WM_MOUSEHWHEEL:
            CustomHandleMouseWheel(hwnd, GET_WHEEL_DELTA_WPARAM(wParam), FALSE);
            break;
    }
    
    return 0;
}

// Helper function for calculation of scrolling lines for provided mouse wheel
// delta value. This function is quite generic and can be used/shared among
// many controls.
int WheelScrollLines(HWND hwnd, int iDelta, UINT nPage, BOOL isVertical)
{
    // We accumulate the wheel_delta until there is enough to scroll for
    // at least a single line. This improves the feel for strange values
    // of SPI_GETWHEELSCROLLLINES and for some mouses.
    static HWND hwndCurrent = NULL;         // HWND we accumulate the delta for.
    static int iAccumulator[2] = { 0, 0 };  // The accumulated value (vert. and horiz.).
    static DWORD dwLastActivity[2] = { 0, 0 };

    UINT uSysParam;
    UINT uLinesPerWHEELDELTA;   // Scrolling speed (how much to scroll per WHEEL_DELTA).
    int iLines;                 // How much to scroll for currently accumulated value.
    int iDirIndex = (isVertical ? 0 : 1);  // The index into iAccumulator[].
    DWORD dwNow;

    dwNow = GetTickCount();

    // Even when nPage is below one line, we still want to scroll at least a little.
    if (nPage < 1)
        nPage = 1;

    // Ask the system for scrolling speed.
    uSysParam = (isVertical ? SPI_GETWHEELSCROLLLINES : SPI_GETWHEELSCROLLCHARS);
    if (!SystemParametersInfo(uSysParam, 0, &uLinesPerWHEELDELTA, 0))
        uLinesPerWHEELDELTA = 3;  // default when SystemParametersInfo() fails.
    if (uLinesPerWHEELDELTA == WHEEL_PAGESCROLL) {
        // System tells to scroll over whole pages.
        uLinesPerWHEELDELTA = nPage;
    }
    if (uLinesPerWHEELDELTA > nPage) {
        // Slow down if page is too small. We don't want to scroll over multiple
        // pages at once.
        uLinesPerWHEELDELTA = nPage;
    }

    EnterCriticalSection(&csWheelLock);

    // In some cases, we do want to reset the accumulated value(s).
    if (hwnd != hwndCurrent) {
        // Do not carry accumulated values between different HWNDs.
        hwndCurrent = hwnd;
        iAccumulator[0] = 0;
        iAccumulator[1] = 0;
    } else if (dwNow - dwLastActivity[iDirIndex] > GetDoubleClickTime() * 2) {
        // Reset the accumulator if there was a long time of wheel inactivity.
        iAccumulator[iDirIndex] = 0;
    } else if ((iAccumulator[iDirIndex] > 0) == (iDelta < 0)) {
        // Reset the accumulator if scrolling direction has been reversed.
        iAccumulator[iDirIndex] = 0;
    }

    if (uLinesPerWHEELDELTA > 0) {
        // Accumulate the delta.
        iAccumulator[iDirIndex] += iDelta;

        // Compute the lines to scroll.
        iLines = (iAccumulator[iDirIndex] * (int)uLinesPerWHEELDELTA) / WHEEL_DELTA;

        // Decrease the accumulator for the consumed amount.
        // (Corresponds to the remainder of the integer divide above.)
        iAccumulator[iDirIndex] -= (iLines * WHEEL_DELTA) / (int)uLinesPerWHEELDELTA;
    } else {
        // uLinesPerWHEELDELTA == 0, i.e. likely configured to no scrolling
        // with mouse wheel.
        iLines = 0;
        iAccumulator[iDirIndex] = 0;
    }

    dwLastActivity[iDirIndex] = dwNow;
    LeaveCriticalSection(&csWheelLock);

    // Note that for vertical wheel, Windows provides the delta with opposite
    // sign. Hence the minus.
    return (isVertical ? -iLines : iLines);
}

void CustomHandleMouseWheel(HWND hwnd, int iDelta, BOOL isVertical)
{
    SCROLLINFO si;
    int nPos;
    int nOldPos;

    si.cbSize = sizeof(SCROLLINFO);
    si.fMask = SIF_PAGE | SIF_POS;
    GetScrollInfo(hwnd, (isVertical ? SB_VERT : SB_HORZ), &si);

    // Compute how many lines to scroll.
    nOldPos = si.nPos;
    nPos = nOldPos + WheelScrollLines(hwnd, iDelta, si.nPage, isVertical);

    // Scroll to the desired location.
    SetScrollPos(hwnd, (isVertical ? SB_VERT : SB_HORZ), si.nPos, isVertical);

    // Update the client area.
    ScrollWindowEx(hwnd, 
                   (isVertical ? 0 : (nOldPos - nPos) * scrollUnit),
                   (isVertical ? (nOldPos - nPos) * scrollUnit : 0),
                   NULL, NULL, NULL, NULL, SW_ERASE | SW_INVALIDATE | SW_SCROLLCHILDREN);
}

#endif