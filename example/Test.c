#include "Test.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include "IO.h"
#include "String.h"
#include "ArrayList.h"
#include "Math.h"
#include "Time.h"
#include "Person.h"
#include "List.h"
#include "NotEvenNumberException.h"

Test* __static__Test;

Test* fathom_Test_Test(ExceptionData* exceptionData)
{
	CCLASS_NEW(Test, this,);
	
	{
	}
	
	return this;
}

void fathom_del_Test(Test** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}

void fathom_Test_main(ExceptionData* exceptionData, String** fathom_args_3)
{
	List* fathom_list_3;
	ListNode* fathom_current_3;
	long_long fathom_start_3;
	int fathom_q_3;
	long_long fathom_end_3;
	
	fathom_list_3 = fathom_List_List(exceptionData);
	fathom_current_3 = fathom_List_getFirst(fathom_list_3, exceptionData);
	while (fathom_current_3 != 0)
	{
		fathom_ListNode_getData(fathom_current_3, exceptionData);
		fathom_current_3 = fathom_ListNode_getNext(fathom_current_3, exceptionData);
	}
	fathom_IO_println(exceptionData, fathom_String_String(exceptionData, "Starting benchmark..."));
	fathom_start_3 = fathom_Time_currentTimeMillis(exceptionData);
	for (fathom_q_3 = 99999999; fathom_q_3 >= 0; --fathom_q_3)
	{
		fathom_Math_sin(exceptionData, fathom_q_3);
	}
	fathom_end_3 = fathom_Time_currentTimeMillis(exceptionData);
	fathom_IO_printl(exceptionData, fathom_end_3 - fathom_start_3);
	fathom_IO_println(exceptionData, fathom_String_String(exceptionData, ""));
	fathom_IO_print(exceptionData, fathom_String_String(exceptionData, "Press enter to exit..."));
	fathom_IO_waitForEnter(exceptionData);
}

int fathom_Test_divide(Test* this, ExceptionData* exceptionData, int fathom_numerator_54, int fathom_denominator_54)
{
	if (fathom_denominator_54 == 0)
	{
		THROW(2);
	}
	return fathom_numerator_54 / fathom_denominator_54;
}

int fathom_Test_getEvenNumber(Test* this, ExceptionData* exceptionData, int fathom_num_61)
{
	if (fathom_num_61 % 2 != 0)
	{
		THROW(3);
	}
	return fathom_num_61;
}

int fathom_Test_test(Test* this, ExceptionData* exceptionData)
{
	int fathom_newVar_66;
	
	fathom_newVar_66 = 43;
	return fathom_newVar_66;
}

int fathom_Test_test2(Test* this, ExceptionData* exceptionData)
{
	return fathom_Test_test(this, exceptionData);
}


#include "Fathom.h"
#include <stdio.h>
#include <string.h>

int main(int argc, char** argvs)
{
	String** args = (String**)malloc(argc * sizeof(String));
	int      i;
	
	ExceptionData* exceptionData = 0;
	__static__Test = fathom_Test_Test(0);
	__static__IO = fathom_IO_IO(0);
	__static__Math = fathom_Math_Math(0);
	__static__Time = fathom_Time_Time(0);
	
	for (i = 0; i < argc; i++)
	{
		char* str = (char*)malloc(sizeof(char) * strlen(argvs[i]) + 1);
		copy_string(str, argvs[i]);
		args[i] = fathom_String_String(0, str);
	}
	
	TRY
	{
		fathom_Test_main(exceptionData, args);
	}
	CATCH (1)
	{
		printf("You broke it.");
		fathom_IO_waitForEnter(0);
	}
	FINALLY
	{
		
	}
	END_TRY;
	fathom_del_Test(&__static__Test, 0);
	fathom_del_IO(&__static__IO, 0);
	fathom_del_Math(&__static__Math, 0);
	fathom_del_Time(&__static__Time, 0);
	free(args);
	
	return 0;
}