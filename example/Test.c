#include "Test.h"
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

void nova_Test_main(ExceptionData* exceptionData, String** nova_Test_args_6)
{
		char nova_Test_c_6;
		
		nova_Test_c_6 = 'y';
		while (nova_Test_c_6 == 'y' || nova_Test_c_6 == 'Y')
		{
				long_long nova_Test_start_237;
				int nova_Test_i_237;
				long_long nova_Test_end_237;
				
				nova_Test_start_237 = nova_Time_currentTimeMillis(exceptionData);
				nova_Test_i_237 = 0;
				
				for (; nova_Test_i_237 < 9999999; nova_Test_i_237++)
				{
						nova_Math_sin(exceptionData, nova_Test_i_237);
				}
				nova_Test_end_237 = nova_Time_currentTimeMillis(exceptionData);
				nova_IO_printl(exceptionData, nova_Test_end_237 - nova_Test_start_237);
				nova_IO_println(exceptionData, nova_String_String(exceptionData, ""));
				nova_IO_print(exceptionData, nova_String_String(exceptionData, "Run again? (Y/N)"));
				nova_Test_c_6 = nova_IO_getChar(exceptionData);
		}
		nova_IO_println(exceptionData, nova_String_String(exceptionData, ""));
		nova_IO_println(exceptionData, nova_String_String(exceptionData, "Finished"));
		nova_IO_waitForEnter(exceptionData);
}


#include <stdio.h>
#include <string.h>

int main(int argc, char** argvs)
{
		String** args = (String**)malloc(argc * sizeof(String));
		int      i;
		
		ExceptionData* exceptionData = 0;
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)malloc(sizeof(char) * strlen(argvs[i]) + 1);
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
		
		return 0;
}