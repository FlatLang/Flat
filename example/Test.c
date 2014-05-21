#include "Test.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include "IO.h"
#include "Time.h"
#include "Thread.h"

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

void nova_Test_main(ExceptionData* exceptionData, String** nova_args_6)
{
		char nova_c_6;
		
		nova_c_6 = 'y';
		while (nova_c_6 == 'y' || nova_c_6 == 'Y')
		{
				Thread* nova_thread_191;
				Thread* nova_thread2_191;
				long_long nova_start_191;
				long_long nova_end_191;
				
				nova_thread_191 = nova_Thread_Thread(exceptionData, 100, nova_String_String(exceptionData, "Thread1"));
				nova_thread2_191 = nova_Thread_Thread(exceptionData, 100, nova_String_String(exceptionData, "Thread2"));
				nova_start_191 = nova_Time_currentTimeMillis(exceptionData);
				nova_Thread_start(nova_thread_191, exceptionData);
				nova_Thread_start(nova_thread2_191, exceptionData);
				nova_Thread_join(nova_thread_191, exceptionData);
				nova_Thread_join(nova_thread2_191, exceptionData);
				nova_end_191 = nova_Time_currentTimeMillis(exceptionData);
				nova_IO_printl(exceptionData, nova_end_191 - nova_start_191);
				nova_IO_println(exceptionData, nova_String_String(exceptionData, ""));
				nova_IO_print(exceptionData, nova_String_String(exceptionData, "Run again? (Y/N)"));
				nova_c_6 = nova_IO_getChar(exceptionData);
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