#include "IO.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include <stdio.h>
#include <stdio.h>
#include <stdlib.h>
#include "String.h"
#include <Fathom.h>

IO* __static__IO;

IO* new_IO(ExceptionData* __FATHOM__exception_data);
void del_IO(IO** __o__, ExceptionData* __FATHOM__exception_data);
static void __FATHOM__println(IO* __o__, ExceptionData* __FATHOM__exception_data, String* text);
static void __FATHOM__print(IO* __o__, ExceptionData* __FATHOM__exception_data, String* text);
static void __FATHOM__printi(IO* __o__, ExceptionData* __FATHOM__exception_data, int j);
static void __FATHOM__printl(IO* __o__, ExceptionData* __FATHOM__exception_data, long_long j);
static int __FATHOM__getInt(IO* __o__, ExceptionData* __FATHOM__exception_data);
static String* __FATHOM__getLine(IO* __o__, ExceptionData* __FATHOM__exception_data);
static void __FATHOM__waitForEnter(IO* __o__, ExceptionData* __FATHOM__exception_data);

NO_PRIVATE

IO* new_IO(ExceptionData* __FATHOM__exception_data)
{
	NEW(IO, __o__);
	
	__o__->println = __FATHOM__println;
	__o__->print = __FATHOM__print;
	__o__->printi = __FATHOM__printi;
	__o__->printl = __FATHOM__printl;
	__o__->getInt = __FATHOM__getInt;
	__o__->getLine = __FATHOM__getLine;
	__o__->waitForEnter = __FATHOM__waitForEnter;
	
	{
	}
	
	return __o__;
}

void del_IO(IO** __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!*__o__)
	{
		return;
	}
	
	
	{
	}
	free(*__o__);
}

static void __FATHOM__println(IO* __o__, ExceptionData* __FATHOM__exception_data, String* text)
{
	__o__->print(__o__, __FATHOM__exception_data, text);
	__o__->print(__o__, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "\n"));
}

static void __FATHOM__print(IO* __o__, ExceptionData* __FATHOM__exception_data, String* text)
{
	const char* cText;
	
	cText = text->toCharArray(text, __FATHOM__exception_data);
	printf(cText);
}

static void __FATHOM__printi(IO* __o__, ExceptionData* __FATHOM__exception_data, int j)
{
	printf("%d", j);
}

static void __FATHOM__printl(IO* __o__, ExceptionData* __FATHOM__exception_data, long_long j)
{
	printf("%llu", j);
}

static int __FATHOM__getInt(IO* __o__, ExceptionData* __FATHOM__exception_data)
{
	String* s;
	char* data;
	int num;
	
	s = __o__->getLine(__o__, __FATHOM__exception_data);
	data = s->toCharArray(s, __FATHOM__exception_data);
	num = atoi(data);
	return num;
}

static String* __FATHOM__getLine(IO* __o__, ExceptionData* __FATHOM__exception_data)
{
	char* line;
	String* s;
	
	line = ufgets(stdin);
	s = new_String(__FATHOM__exception_data, line);
	return s;
}

static void __FATHOM__waitForEnter(IO* __o__, ExceptionData* __FATHOM__exception_data)
{
	char* c;
	
	c = (char*)malloc(sizeof(char) * 2);
	fgets(c, 2, stdin);
}
