#include <precompiled.h>
#include "NovaFileStability.h"




void nova_static_FileStability_createFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
void nova_static_FileStability_writeToFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
void nova_static_FileStability_readFromFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
void nova_static_FileStability_deleteFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
String* nova_static_FileStability_inputString;
int nova_static_FileStability_lines;

FileStability* nova_FileStability_FileStability(ExceptionData* exceptionData)
{
	FileStability* this = (FileStability*)1;
	
	{
	}
	
	return this;
}

void nova_del_FileStability(FileStability** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_FileStability_test(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "Checking File IO..."));
	nova_static_FileStability_inputString = nova_String_String(exceptionData, "This is test input...");
	nova_static_FileStability_lines = 100;
	TRY
	{
		nova_ExceptionData_addCode(exceptionData, exceptionData, 1);
		
		{
			File* nova_2_f;
			
			nova_2_f = nova_File_File(exceptionData, nova_String_concat(nova_String_String(exceptionData, "temp"), exceptionData, nova_Long_toString(nova_Long_Long(exceptionData, nova_static_Time_currentTimeMillis((Object*)0, exceptionData)), exceptionData)));
			nova_static_FileStability_createFile((Object*)0, exceptionData, nova_0_program, nova_2_f);
			nova_static_FileStability_writeToFile((Object*)0, exceptionData, nova_0_program, nova_2_f);
			nova_static_FileStability_readFromFile((Object*)0, exceptionData, nova_0_program, nova_2_f);
			nova_static_FileStability_deleteFile((Object*)0, exceptionData, nova_0_program, nova_2_f);
		}
	}
	CATCH (1)
	{
		nova_StabilityTest_fail(nova_0_program, exceptionData, nova_String_String(exceptionData, "Failed; Exception thrown"));
	}
	FINALLY
	{
	}
	END_TRY;
}

void nova_static_FileStability_createFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	nova_static_Console_write((Object*)0, exceptionData, nova_String_String(exceptionData, "Creating file... "));
	nova_File_create(nova_0_f, exceptionData);
	nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "OK"));
}

void nova_static_FileStability_writeToFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	int nova_1_i;
	
	nova_static_Console_write((Object*)0, exceptionData, nova_String_concat(nova_String_String(exceptionData, "Writing "), exceptionData, nova_String_concat(nova_Integer_toString(nova_Integer_Integer(exceptionData, nova_static_FileStability_lines), exceptionData), exceptionData, nova_String_String(exceptionData, " lines of data to file... "))));
	nova_1_i = 0;
	for (; nova_1_i < nova_static_FileStability_lines; nova_1_i++)
	{
		nova_File_writeLine(nova_0_f, exceptionData, nova_static_FileStability_inputString);
	}
	nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "OK"));
}

void nova_static_FileStability_readFromFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	int nova_1_times;
	String* nova_1_line;
	
	nova_static_Console_write((Object*)0, exceptionData, nova_String_String(exceptionData, "Reading lines from file... "));
	nova_File_reopen(nova_0_f, exceptionData);
	nova_1_times = 0;
	nova_1_line = nova_File_readLine(nova_0_f, exceptionData);
	while (nova_1_line != (Object*)0)
	{
		if (!nova_1_line->vtable->nova_virtual_equals(nova_1_line, exceptionData, nova_static_FileStability_inputString))
		{
			nova_StabilityTest_fail(nova_0_program, exceptionData, nova_String_String(exceptionData, ""));
		}
		nova_1_line = nova_File_readLine(nova_0_f, exceptionData);
		nova_1_times++;
	}
	if (nova_1_times != nova_static_FileStability_lines)
	{
		nova_StabilityTest_fail(nova_0_program, exceptionData, nova_String_concat(nova_String_String(exceptionData, "Failed; only read "), exceptionData, nova_String_concat(nova_Integer_toString(nova_Integer_Integer(exceptionData, nova_1_times), exceptionData), exceptionData, nova_String_concat(nova_String_String(exceptionData, "/"), exceptionData, nova_String_concat(nova_Integer_toString(nova_Integer_Integer(exceptionData, 100), exceptionData), exceptionData, nova_String_String(exceptionData, " lines"))))));
	}
	nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "OK"));
}

void nova_static_FileStability_deleteFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	nova_static_Console_write((Object*)0, exceptionData, nova_String_String(exceptionData, "Deleting file... "));
	if (!nova_File_delete(nova_0_f, exceptionData))
	{
		nova_StabilityTest_fail(nova_0_program, exceptionData, nova_String_String(exceptionData, "Failed to delete file"));
	}
	nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "OK"));
}
