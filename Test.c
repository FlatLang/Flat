#include "CClass.h"
#include "Test.h"
#include "IO.h"
#include "String.h"

Test* __static__Test;

static void __FATHOM__main(Test* __o__, String** args);
static int test(Test* __o__);
static int test2(Test* __o__);
Test* new_Test();

PRIVATE
(
	int fieldVar;
)

Test* new_Test()
{
	NEW(Test, __o__);
	
	__o__->__FATHOM__main = __FATHOM__main;
	__o__->test = test;
	__o__->test2 = test2;
	
	
	return __o__;
}

static void __FATHOM__main(Test* __o__, String** args)
{
	__static__IO->print(__static__IO, new_String("Hello, world\n\n"));
	__static__IO->print(__static__IO, new_String("Command line arg #1 is\n"));
	__static__IO->print(__static__IO, args[0]);
	__static__IO->waitForEnter(__static__IO);
}

static int test(Test* __o__)
{
	int newVar;
	
	newVar = 43;
	return newVar;
}

static int test2(Test* __o__)
{
	return test(__o__);
}


int main(int argc, char** argvs)
{
	String** args = (String**)malloc(argc * sizeof(String));
	int      i;
	
	__static__Test = new_Test();
	__static__IO = new_IO();
	
	for (i = 0; i < argc; i++)
	{
		args[i] = new_String(argvs[i]);
	}
	
	__static__Test->__FATHOM__main(__static__Test, args);
}