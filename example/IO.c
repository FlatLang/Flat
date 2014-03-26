#include "IO.h"
#include <stdio.h>
#include <stdlib.h>
#include "String.h"
#include <Fathom.h>

IO* __static__IO;

IO* new_IO(ExceptionData* __FATHOM__exception_data);
void del_IO(IO* __o__, ExceptionData* __FATHOM__exception_data);
static void println(IO* __o__, ExceptionData* __FATHOM__exception_data, String* text);
static void print(IO* __o__, ExceptionData* __FATHOM__exception_data, String* text);
static void printi(IO* __o__, ExceptionData* __FATHOM__exception_data, int j);
static int getInt(IO* __o__, ExceptionData* __FATHOM__exception_data);
static String* getLine(IO* __o__, ExceptionData* __FATHOM__exception_data);
static void waitForEnter(IO* __o__, ExceptionData* __FATHOM__exception_data);

NO_PRIVATE

IO* new_IO(ExceptionData* __FATHOM__exception_data)
{
NEW(IO, __o__);

__o__->println = println;
__o__->print = print;
__o__->printi = printi;
__o__->getInt = getInt;
__o__->getLine = getLine;
__o__->waitForEnter = waitForEnter;


return __o__;
}

void del_IO(IO* __o__, ExceptionData* __FATHOM__exception_data)
{
if (!__o__)
{
return;
}


free(__o__);
}

static void println(IO* __o__, ExceptionData* __FATHOM__exception_data, String* text)
{
}

static void print(IO* __o__, ExceptionData* __FATHOM__exception_data, String* text)
{
char* cText;

cText = text->toCharArray(text, __FATHOM__exception_data);
printf(cText);
}

static void printi(IO* __o__, ExceptionData* __FATHOM__exception_data, int j)
{
printf("%d", j);
}

static int getInt(IO* __o__, ExceptionData* __FATHOM__exception_data)
{
String* s;
char* data;
int num;

s = getLine(__o__, __FATHOM__exception_data);
data = s->toCharArray(s, __FATHOM__exception_data);
num = atoi(data);
return num;
}

static String* getLine(IO* __o__, ExceptionData* __FATHOM__exception_data)
{
char* line;
String* s;

line = ufgets(stdin);
s = new_String(__FATHOM__exception_data, line);
return s;
}

static void waitForEnter(IO* __o__, ExceptionData* __FATHOM__exception_data)
{
char* c;

c = (char*)malloc(sizeof(char) * 2);
fgets(c, 2, stdin);
}
