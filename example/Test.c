#include "Test.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "IO.h"
#include "String.h"
#include "ArrayList.h"
#include "Math.h"
#include "Time.h"

Test* __static__Test;

Test* new_Test(ExceptionData* __FATHOM__exception_data);
void del_Test(Test* __o__, ExceptionData* __FATHOM__exception_data);
static void __FATHOM__main(Test* __o__, ExceptionData* __FATHOM__exception_data, String** args);
static int __FATHOM__divide(Test* __o__, ExceptionData* __FATHOM__exception_data, int numerator, int denominator);
static int __FATHOM__getEvenNumber(Test* __o__, ExceptionData* __FATHOM__exception_data, int num);
static int __FATHOM__test(Test* __o__, ExceptionData* __FATHOM__exception_data);
static int __FATHOM__test2(Test* __o__, ExceptionData* __FATHOM__exception_data);

PRIVATE
(
	int fieldVar;
)

Test* new_Test(ExceptionData* __FATHOM__exception_data)
{
	NEW(Test, __o__);
	
	__o__->main = __FATHOM__main;
	__o__->divide = __FATHOM__divide;
	__o__->getEvenNumber = __FATHOM__getEvenNumber;
	__o__->test = __FATHOM__test;
	__o__->test2 = __FATHOM__test2;
	
	__o__->publicVariable = 0;
	__o__->prv->fieldVar = 0;
	{
		__o__->publicVariable = new_String(__FATHOM__exception_data, "hello");
	}
	
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
	
	{
	}
	free(__o__);
}

static void __FATHOM__main(Test* __o__, ExceptionData* __FATHOM__exception_data, String** args)
{
	long_long start;
	int q;
	long_long end;
	int i;
	
	__static__IO->println(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "Hello, world\n"));
	__static__IO->println(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "Command line arg #1 is"));
	__static__IO->println(__static__IO, __FATHOM__exception_data, args[0]);
	start = __static__Time->currentTimeMillis(__static__Time, __FATHOM__exception_data);
	for (q = 99999999; q >= 0; --q)
	{
		TRY
		{
			__FATHOM__exception_data->addCode(__FATHOM__exception_data, __FATHOM__exception_data, 2);
			
			{
				__static__Math->sin(__static__Math, __FATHOM__exception_data, q);
			}
		}
		CATCH (2)
		{
			SomethingStupid* e;
		}
		FINALLY
		{
		}
		END_TRY;
	}
	end = __static__Time->currentTimeMillis(__static__Time, __FATHOM__exception_data);
	__static__IO->printl(__static__IO, __FATHOM__exception_data, end - start);
	TRY
	{
		__FATHOM__exception_data->addCode(__FATHOM__exception_data, __FATHOM__exception_data, 4);
		__FATHOM__exception_data->addCode(__FATHOM__exception_data, __FATHOM__exception_data, 3);
		
		{
			int den;
			int num;
			
			den = 1;
			if (den == 0)
			{
				THROW(3);
			}
			num = 23 / den;
			TRY
			{
				
				{
					int even;
					
					even = __static__IO->getInt(__static__IO, __FATHOM__exception_data);
					__o__->getEvenNumber(__o__, __FATHOM__exception_data, even);
				}
			}
			FINALLY
			{
				{
					__static__IO->println(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "An error has occurred!!!"));
				}
			}
			END_TRY;
		}
	}
	CATCH (4)
	{
		{
			__static__IO->println(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "Caught even num exception"));
		}
		NotEvenNumberException* e;
	}
	CATCH (3)
	{
		{
			__static__IO->println(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "You cant divide by zero idiot."));
		}
		DivideByZeroException* e;
	}
	FINALLY
	{
	}
	END_TRY;
	__static__IO->print(__static__IO, __FATHOM__exception_data, new_String(__FATHOM__exception_data, "Done\n"));
	__static__IO->waitForEnter(__static__IO, __FATHOM__exception_data);
}

static int __FATHOM__divide(Test* __o__, ExceptionData* __FATHOM__exception_data, int numerator, int denominator)
{
	if (denominator == 0)
	{
		THROW(3);
	}
	return numerator / denominator;
}

static int __FATHOM__getEvenNumber(Test* __o__, ExceptionData* __FATHOM__exception_data, int num)
{
	if (num % 2 != 0)
	{
		THROW(4);
	}
	return num;
}

static int __FATHOM__test(Test* __o__, ExceptionData* __FATHOM__exception_data)
{
	int newVar;
	
	newVar = 43;
	return newVar;
}

static int __FATHOM__test2(Test* __o__, ExceptionData* __FATHOM__exception_data)
{
	return __o__->test(__o__, __FATHOM__exception_data);
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
	__static__Math = new_Math(0);
	__static__Time = new_Time(0);
	
	for (i = 0; i < argc; i++)
	{
		args[i] = new_String(0, argvs[i]);
	}
	
	TRY
	{
		__static__Test->main(__static__Test, __FATHOM__exception_data, args);
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
	del_Math(__static__Math, 0);
	del_Time(__static__Time, 0);
	free(args);
	return 0;
}