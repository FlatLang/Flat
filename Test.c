#include "CClass.h"
#include "Test.h"
#include "IO.h"
#include "String.h"

void main(Test* __o__, String args[]);
static int test(Test* __o__);
static int test2(Test* __o__);

PRIVATE
(
	int fieldVar;
)

void main(Test* __o__, String args[])
{
	print("Hello, world");
}

static int test(Test* __o__)
{
	int newVar;
	
	newVar = 43;
	return newVar;
}

static int test2(Test* __o__)
{
	return test();
}
