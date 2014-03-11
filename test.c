include "CClass.h"
include "IO.h"
include "stdio.h"

static void println(IO* o, String text);
static void print(IO* o, String text);

NO_PRIVATE

static void println(IO* o, String text)
{
	print(text + '\n');
}

static void print(IO* o, String text)
{
	char cText[];
	
	cText[] = text.toCharArray();
	stdio.printf(cText);
}
