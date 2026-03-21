#include "NativeDate.h"

struct tm *timeObj;

void nova_updateTime()
{
	time_t t = time(NULL);
	timeObj = localtime(&t);
}

int nova_getMonth()
{
	return timeObj->tm_mon + 1;
}

int nova_getDay()
{
	return timeObj->tm_mday;
}

int nova_getYear()
{
	return timeObj->tm_year + 1900;
}

int nova_getHour()
{
	return timeObj->tm_hour;
}

int nova_getMinute()
{
	return timeObj->tm_min;
}

int nova_getSecond()
{
	return timeObj->tm_sec;
}

char* nova_formatDate(char* str, int first, int second, int third, int fourth, int fifth, int sixth)
{
	char* out = malloc(sizeof(char[80]));

	sprintf(out, str, first, second, third, fourth, fifth, sixth);

	return out;
}

