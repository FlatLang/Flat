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
	/*struct sgttyb state;
	ioctl(0, (int)TIOCGETP, (char*)&state);
	
	if (echo)
	{
		state.sg_flags |= ECHO;
	}
	else
	{
		state.sg_flags &= ~ECHO;
	}
	
	ioctl(0, (int)TIOCSETP, (char*)&state);*/
	struct termios tattr;
	tcgetattr (STDIN_FILENO, &tattr);
	if (echo)
	tattr.c_lflag |= (ICANON | ECHO);
	else
	tattr.c_lflag &= ~(ICANON | ECHO);
	tcsetattr (STDIN_FILENO, TCSAFLUSH, &tattr);
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
