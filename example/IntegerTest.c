#include "IntegerTest.h"
#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"
#include "IO.h"
#include "Time.h"
#include "Thread.h"
#include "Person.h"
#include <stdio.h>
#include "List.h"
#include "ListNode.h"
#include "BodyBuilder.h"
#include "GC.h"


Test* nova_Test_Test(ExceptionData* exceptionData)
{
		Test* this = NULL;
		
		{
		}
		
		return this;
}

void nova_del_Test(Test** this, ExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		free(*this);
}

void nova_Test_main(ExceptionData* exceptionData, String** nova_0_args)
{
		char nova_173_c;
		
		nova_173_c = 'y';
		while (nova_173_c == 'y' || nova_173_c == 'Y')
		{
				long_long nova_366_start;
				int nova_367_i;
				long_long nova_366_end;
				
				nova_366_start = currentTimeMillis();
				nova_367_i = 0;
				
				for (; nova_367_i < 999999; nova_367_i++)
				{
						nova_Integer_toAString(exceptionData, nova_367_i);
				}
				nova_366_end = currentTimeMillis();
				nova_IO_printl(exceptionData, nova_366_end - nova_366_start);
				nova_IO_println(exceptionData, nova_String_String(exceptionData, ""));
				nova_IO_print(exceptionData, nova_String_String(exceptionData, "Run again? (Y/N)"));
				nova_173_c = nova_IO_getChar(exceptionData);
		}
		nova_IO_println(exceptionData, nova_String_String(exceptionData, "\nFinished"));
		nova_IO_waitForEnter(exceptionData);
}


#include <stdio.h>
#include <string.h>

int main(int argc, char** argvs)
{
		String** args;
		int      i;
		
		ExceptionData* exceptionData = 0;
		nova_GC_init(exceptionData);
		
		args = (String**)GC_MALLOC(argc * sizeof(String));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)GC_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_String_String(0, str);
		}
		
		TRY
		{
				nova_Test_main(exceptionData, args);
		}
		CATCH (1)
		{
				printf("You broke it.");
				nova_IO_waitForEnter(0);
		}
		FINALLY
		{
				
		}
		END_TRY;
		free(args);
		GC_gcollect();
		
		return 0;
}