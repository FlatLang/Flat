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

void fathom_Test_main(ExceptionData* exceptionData, String** fathom_args_168)
{
		char fathom_c_168;
		
		fathom_c_168 = 'y';
		while (fathom_c_168 == 'y' || fathom_c_168 == 'Y')
		{
				Thread* fathom_thread_191;
				Thread* fathom_thread2_191;
				long_long fathom_start_191;
				long_long fathom_end_191;
				
				fathom_thread_191 = fathom_Thread_Thread(exceptionData, 100, fathom_String_String(exceptionData, "Thread1"));
				fathom_thread2_191 = fathom_Thread_Thread(exceptionData, 100, fathom_String_String(exceptionData, "Thread2"));
				fathom_start_191 = fathom_Time_currentTimeMillis(exceptionData);
				fathom_Thread_start(fathom_thread_191, exceptionData);
				fathom_Thread_start(fathom_thread2_191, exceptionData);
				fathom_Thread_join(fathom_thread_191, exceptionData);
				fathom_Thread_join(fathom_thread2_191, exceptionData);
				fathom_end_191 = fathom_Time_currentTimeMillis(exceptionData);
				fathom_IO_printl(exceptionData, fathom_end_191 - fathom_start_191);
				fathom_IO_println(exceptionData, fathom_String_String(exceptionData, ""));
				fathom_IO_print(exceptionData, fathom_String_String(exceptionData, "Run again? (Y/N)"));
				fathom_c_168 = fathom_IO_getChar(exceptionData);
		}
		fathom_IO_println(exceptionData, fathom_String_String(exceptionData, ""));
		fathom_IO_println(exceptionData, fathom_String_String(exceptionData, "Finished"));
		fathom_IO_waitForEnter(exceptionData);
}


#include "Fathom.h"
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
		free(args);
		
		return 0;
}