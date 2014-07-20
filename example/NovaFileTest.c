#include <precompiled.h>
#include "NovaFileTest.h"



FileTest* nova_FileTest_FileTest(ExceptionData* exceptionData)
{
	FileTest* this = (FileTest*)1;
	
	{
	}
	
	return this;
}

void nova_del_FileTest(FileTest** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_FileTest_main(FileTest* this, ExceptionData* exceptionData, String** nova_0_args)
{
	File* nova_1_f;
	
	nova_1_f = nova_File_File(exceptionData, nova_String_String(exceptionData, "C:/Users/Braden Steffaniak/test3.txt"));
	if (nova_File_exists(nova_1_f, exceptionData))
	{
		String* nova_2_data;
		
		nova_static_Console_writeLine(0, exceptionData, nova_String_String(exceptionData, "Your file exists!"));
		nova_File_writeLine(nova_1_f, exceptionData, nova_String_String(exceptionData, "Entering data.."));
		nova_File_writeLine(nova_1_f, exceptionData, nova_String_String(exceptionData, "asdfasdf thing."));
		nova_File_reopen(nova_1_f, exceptionData);
		nova_2_data = nova_File_readAllContents(nova_1_f, exceptionData);
		nova_static_Console_writeLine(0, exceptionData, nova_2_data);
	}
	else
	{
		nova_static_Console_writeLine(0, exceptionData, nova_String_String(exceptionData, "Your file does not exist.."));
	}
	nova_File_close(nova_1_f, exceptionData);
	nova_static_Console_writeLine(0, exceptionData, nova_String_String(exceptionData, "Press enter to exit..."));
	nova_static_Console_waitForEnter(0, exceptionData);
}
