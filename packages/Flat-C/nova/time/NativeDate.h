#ifndef NATIVE_DATE
#define NATIVE_DATE

#include <Nova.h>

void nova_updateTime();
int nova_getMonth();
int nova_getDay();
int nova_getYear();
int nova_getHour();
int nova_getMinute();
int nova_getSecond();
char* nova_formatDate(char* str, int first, int second, int third, int fourth, int fifth, int sixth);

#endif
