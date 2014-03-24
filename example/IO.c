#include "CClass.h"
#include "IO.h"
#include <stdio.h>
#include <stdlib.h>
#include "String.h"
#include <Fathom.h>

IO* __static__IO;

IO* new_IO(jmp_buf __Fathom__jmp_buf);
void del_IO(IO* __o__);
static void* println(IO* __o__, jmp_buf __Fathom__jmp_buf, String* text);
static void* print(IO* __o__, jmp_buf __Fathom__jmp_buf, String* text);
static void* printi(IO* __o__, jmp_buf __Fathom__jmp_buf, int j);
static int getInt(IO* __o__, jmp_buf __Fathom__jmp_buf);
static String* getLine(IO* __o__, jmp_buf __Fathom__jmp_buf);
static void* waitForEnter(IO* __o__, jmp_buf __Fathom__jmp_buf);

NO_PRIVATE

IO* new_IO(jmp_buf __Fathom__jmp_buf)
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

void del_IO(IO* __o__)
{
if (!__o__)
{
return;
}


free(__o__);
}

static void* println(IO* __o__, jmp_buf __Fathom__jmp_buf, String* text)
{
}

static void* print(IO* __o__, jmp_buf __Fathom__jmp_buf, String* text)
{
char* cText;

cText = text->toCharArray(text);
printf(cText);
}

static void* printi(IO* __o__, jmp_buf __Fathom__jmp_buf, int j)
{
printf("%d", j);
}

static int getInt(IO* __o__, jmp_buf __Fathom__jmp_buf)
{
String* s;
char* data;
int num;

s = getLine(__o__);
data = s->toCharArray(s);
num = atoi(data);
return num;
}

static String* getLine(IO* __o__, jmp_buf __Fathom__jmp_buf)
{
char* line;
String* s;

line = ufgets(stdin);
s = new_String(line);
return s;
}

static void* waitForEnter(IO* __o__, jmp_buf __Fathom__jmp_buf)
{
char* c;

c = (char*)malloc(sizeof(char) * 2);
fgets(c, 2, stdin);
}
