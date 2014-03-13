#include "CClass.h"
#include "IO.h"
#include "stdio.h"
#include "String.h"

void println(IO* __o__, String text);
void print(IO* __o__, String text);

NO_PRIVATE

void println(IO* __o__, String text)
{
	print(text + '\n');
}

void print(IO* __o__, String text)
{
	cText = text.toCharArray();
	printf(char cText;);
}
