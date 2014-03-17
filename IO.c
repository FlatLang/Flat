#include "CClass.h"
#include "IO.h"
#include "stdio.h"
#include "String.h"

IO* __static__IO;

static void println(IO* __o__, String* text);
static void print(IO* __o__, String* text);
static void waitForEnter(IO* __o__);
IO* new_IO();

NO_PRIVATE

IO* new_IO()
{
NEW(IO, __o__);

__o__->println = println;
__o__->print = print;
__o__->waitForEnter = waitForEnter;


return __o__;
}

static void println(IO* __o__, String* text)
{
}

static void print(IO* __o__, String* text)
{
char* cText;

cText = text->toCharArray(text);
printf(cText);
}

static void waitForEnter(IO* __o__)
{
char* c;

c = (char*)malloc(sizeof(char) * 2);
fgets(c, 2, stdin);
}
