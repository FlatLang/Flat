#include "Test.h"
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
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
		CCLASS_NEW(Test, this,);
		
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
		char nova_198_c;
		
		nova_198_c = 'y';
		while (nova_198_c == 'y' || nova_198_c == 'Y')
		{
				long_long nova_253_start;
				int nova_254_i;
				long_long nova_253_end;
				
				nova_253_start = nova_Time_currentTimeMillis(exceptionData);
				nova_254_i = 0;
				
				for (; nova_254_i < 99999999; nova_254_i++)
				{
						nova_Math_sin(exceptionData, nova_254_i);
				}
				nova_253_end = nova_Time_currentTimeMillis(exceptionData);
				nova_IO_printl(exceptionData, nova_253_end - nova_253_start);
				nova_IO_println(exceptionData, nova_String_String(exceptionData, ""));
				nova_IO_print(exceptionData, nova_String_String(exceptionData, "Run again? (Y/N)"));
				nova_198_c = nova_IO_getChar(exceptionData);
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
		args = NULL;
		GC_gcollect();
		
		return 0;
}