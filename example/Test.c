#include "CClass.h"
#include "Test.h"
#include "IO.h"
#include "String.h"

Test* __static__Test;

Test* new_Test();
void del_Test(Test* __o__);
static void* __FATHOM__main(Test* __o__, String** args);
static int divide(Test* __o__, int numerator, int denominator);
static int test(Test* __o__);
static int test2(Test* __o__);

PRIVATE
(
	int fieldVar;
)

Test* new_Test()
{
	NEW(Test, __o__);
	
	__o__->__FATHOM__main = __FATHOM__main;
	__o__->divide = divide;
	__o__->test = test;
	__o__->test2 = test2;
	
	__o__->publicVariable = 0;
	__o__->publicVariable = new_String("hello");
	
	return __o__;
}

void del_Test(Test* __o__)
{
	if (!__o__)
	{
		return;
	}
	
	free(__o__->prv);
	del_String(__o__->publicVariable);
	
	free(__o__);
}

static void* __FATHOM__main(Test* __o__, String** args)
{
	int i;
	String* s;
	
	__static__IO->print(__static__IO, new_String("Hello, world\n\n"));
	__static__IO->print(__static__IO, new_String("Command line arg #1 is\n"));
	__static__IO->print(__static__IO, args[0]);
	TRY
	{
		divide(__o__, 5, 0);
	}
	CATCH (1)
	{
	}
	FINALLY
	{
	}
	END_TRY;
	s = __static__IO->getLine(__static__IO);
	__static__IO->print(__static__IO, s);
	__static__IO->waitForEnter(__static__IO);
}

static int divide(Test* __o__, int numerator, int denominator)
{
	if (denominator == 0)
	{
		THROW(1);
	}
	return numerator / denominator;
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


#include "Fathom.h"
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
	
	del_Test(__static__Test);
	del_IO(__static__IO);
	free(args);
}