#include <precompiled.h>
#include "NovaFileStability.h"


nova_VTable_FileStability nova_VTable_FileStability_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};

void nova_static_FileStability_createFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
void nova_static_FileStability_writeToFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
void nova_static_FileStability_readFromFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
void nova_static_FileStability_deleteFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
String* nova_static_FileStability_inputString;
int nova_static_FileStability_lines;

FileStability* nova_FileStability_construct(FileStability* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(FileStability, this,);
	
	this->vtable = &nova_VTable_FileStability_val;
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
	nova_static_1_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, (char*)("Checking File IO...")));
	nova_static_FileStability_inputString = nova_String_construct(0, exceptionData, (char*)("This is test input..."));
	nova_static_FileStability_lines = 100;
	TRY
	{
		nova_ExceptionData_addCode(exceptionData, exceptionData, 1);
		
		{
			File* nova_2_f;
			
			nova_2_f = nova_1_File_construct(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, (char*)("temp")), exceptionData, nova_2_Long_toString(nova_Long_construct(0, exceptionData, nova_static_Time_currentTimeMillis(0, exceptionData)), exceptionData)));
			nova_static_FileStability_createFile((FileStability*)0, exceptionData, nova_0_program, nova_2_f);
			nova_static_FileStability_writeToFile((FileStability*)0, exceptionData, nova_0_program, nova_2_f);
			nova_static_FileStability_readFromFile((FileStability*)0, exceptionData, nova_0_program, nova_2_f);
			nova_static_FileStability_deleteFile((FileStability*)0, exceptionData, nova_0_program, nova_2_f);
		}
	}
	CATCH (1)
	{
		nova_2_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, (char*)("Failed; Exception thrown")));
	}
	FINALLY
	{
	}
	END_TRY;
}

void nova_static_FileStability_createFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	nova_static_1_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, (char*)("Creating file... ")));
	if (!nova_File_create(nova_0_f, exceptionData))
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData);
	}
	nova_static_1_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, (char*)("OK")));
}

void nova_static_FileStability_writeToFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	int nova_1_i;
	
	nova_static_1_Console_write(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, (char*)("Writing ")), exceptionData, nova_String_concat(nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_static_FileStability_lines), exceptionData), exceptionData, nova_String_construct(0, exceptionData, (char*)(" lines of data to file... ")))));
	nova_1_i = 0;
	for (; nova_1_i < nova_static_FileStability_lines; nova_1_i++)
	{
		nova_File_writeLine(nova_0_f, exceptionData, nova_static_FileStability_inputString);
	}
	nova_static_1_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, (char*)("OK")));
}

void nova_static_FileStability_readFromFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	int nova_1_times;
	String* nova_1_line;
	
	nova_static_1_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, (char*)("Reading lines from file... ")));
	nova_File_reopen(nova_0_f, exceptionData);
	nova_1_times = 0;
	nova_1_line = nova_File_readLine(nova_0_f, exceptionData);
	while (nova_1_line != (String*)0)
	{
		if (!nova_1_line->vtable->nova_virtual_1_equals(nova_1_line, exceptionData, nova_static_FileStability_inputString))
		{
			nova_1_StabilityTest_fail(nova_0_program, exceptionData);
		}
		nova_1_line = nova_File_readLine(nova_0_f, exceptionData);
		nova_1_times++;
	}
	if (nova_1_times != nova_static_FileStability_lines)
	{
		nova_2_StabilityTest_fail(nova_0_program, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, (char*)("Failed; only read ")), exceptionData, nova_String_concat(nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_1_times), exceptionData), exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, (char*)("/")), exceptionData, nova_String_concat(nova_2_Int_toString(nova_Int_construct(0, exceptionData, 100), exceptionData), exceptionData, nova_String_construct(0, exceptionData, (char*)(" lines")))))));
	}
	nova_static_1_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, (char*)("OK")));
}

void nova_static_FileStability_deleteFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	nova_static_1_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, (char*)("Deleting file... ")));
	if (!nova_File_delete(nova_0_f, exceptionData))
	{
		nova_2_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, (char*)("Failed to delete file")));
	}
	nova_static_1_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, (char*)("OK")));
}
