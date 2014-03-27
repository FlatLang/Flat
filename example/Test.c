#include "Test.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "IO.h"
#include "String.h"
#include "ArrayList.h"

Test* __static__Test;

Test* new_Test(ExceptionData* __FATHOM__exception_data);
void del_Test(Test* __o__, ExceptionData* __FATHOM__exception_data);
static void __FATHOM__main(Test* __o__, ExceptionData* __FATHOM__exception_data, String** args);
static int divide(Test* __o__, ExceptionData* __FATHOM__exception_data, int numerator, int denominator);
static int getEvenNumber(Test* __o__, ExceptionData* __FATHOM__exception_data, int num);
static int test(Test* __o__, ExceptionData* __FATHOM__exception_data);
static int test2(Test* __o__, ExceptionData* __FATHOM__exception_data);

PRIVATE
(
	int fieldVar;
)

Test* new_Test(ExceptionData* __FATHOM__exception_data)
{
	NEW(Test, __o__);
	
	__o__->__FATHOM__main = __FATHOM__main;
	__o__->divide = divide;
	__o__->getEvenNumber = getEvenNumber;
	__o__->test = test;
	__o__->test2 = test2;
	
	__o__->publicVariable = 0;
	__o__->prv->fieldVar = 0;
	__o__->publicVariable = new_String(__FATHOM__exception_data, "hello");
	
	return __o__;
}

void del_Test(Test* __o__, ExceptionData* __FATHOM__exception_data)
{
	if (!__o__)
	{
		return;
	}
	
	free(__o__->prv);
	del_String(__o__->publicVariable, __FATHOM__exception_data);
	
	free(__o__);
}

static void __FATHOM__main(Test* __o__, ExceptionData* __FATHOM__exception_data, String** args)
{
	int i;
	int den;
	int num;
	ArrayList* list;
	int j;
	
	__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "Hello, world\n\n"));
	__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "Command line arg #1 is\n"));
	__static__IO->print(__static__IO, __FATHOM__exception_data, args[0]);
	__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "\n"));
	TRY
	{
		__FATHOM__exception_data->addCode(__FATHOM__exception_data, __FATHOM__exception_data, 2);
		__FATHOM__exception_data->addCode(__FATHOM__exception_data, __FATHOM__exception_data, 3);
		
		den = 0;
		if (den == 0)
		{
			THROW(2);
		}
		num = 23 / den;
		list = new_ArrayList(__FATHOM__exception_data);
		list->add(list, __FATHOM__exception_data, 5);
		list->add(list, __FATHOM__exception_data, 6);
		list->add(list, __FATHOM__exception_data, 7);
		list->add(list, __FATHOM__exception_data, 8);
		list->add(list, __FATHOM__exception_data, 9);
		{
			for (j = 0; j < list->getSize(list, __FATHOM__exception_data); j = j + 1)
			{
				__static__IO->printi(__static__IO, __FATHOM__exception_data, list->get(list, __FATHOM__exception_data, j));
				__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, ", "));
			}
		}
		__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "\n"));
	}
	CATCH (2)
	{
		__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "You cant divide by zero idiot."));
		__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "\n"));
	}
	CATCH (3)
	{
		__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "You didnt pass a fricken even number..."));
		__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "\n"));
	}
	FINALLY
	{
	}
	END_TRY;
	__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "Done\n"));
	__static__IO->waitForEnter(__static__IO, __FATHOM__exception_data);
}

static int divide(Test* __o__, ExceptionData* __FATHOM__exception_data, int numerator, int denominator)
{
	if (denominator == 0)
	{
		THROW(2);
	}
	return numerator / denominator;
}

static int getEvenNumber(Test* __o__, ExceptionData* __FATHOM__exception_data, int num)
{
	if (num % 2 != 0)
	{
		THROW(3);
	}
	return num;
}

static int test(Test* __o__, ExceptionData* __FATHOM__exception_data)
{
	int newVar;
	
	newVar = 43;
	return newVar;
}

static int test2(Test* __o__, ExceptionData* __FATHOM__exception_data)
{
	return test(__o__, __FATHOM__exception_data);
}


#include "Fathom.h"
#include <stdio.h>

int main(int argc, char** argvs)
{
	String** args = (String**)malloc(argc * sizeof(String));
	int      i;
	
	ExceptionData* __FATHOM__exception_data = 0;
	__static__Test = new_Test(0);
	__static__IO = new_IO(0);
	
	for (i = 0; i < argc; i++)
	{
		args[i] = new_String(0, argvs[i]);
	}
	
	TRY
	{
		__static__Test->__FATHOM__main(__static__Test, __FATHOM__exception_data, args);
	}
	CATCH (1)
	{
		printf("You broke it.");
		__static__IO->waitForEnter(__static__IO, 0);
	}
	FINALLY
	{
		
	}
	END_TRY;
	del_Test(__static__Test, 0);
	del_IO(__static__IO, 0);
	free(args);
	return 0;
}