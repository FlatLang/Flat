#include <precompiled.h>
#include "NovaFileStability.h"


nova_VTable_FileStability nova_VTable_FileStability_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

void nova_static_FileStability_createFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
void nova_static_FileStability_writeToFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
void nova_static_FileStability_readFromFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
void nova_static_FileStability_deleteFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f);
String* nova_static_FileStability_inputString;
int nova_static_FileStability_lines;

FileStability* nova_0_FileStability_construct(FileStability* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(FileStability, this,);
	this->vtable = &nova_VTable_FileStability_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_FileStability_super(this, 0);
	
	{
		nova_FileStability_this(this, exceptionData);
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
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Checking File IO..."));
	nova_static_FileStability_inputString = nova_String_construct(0, exceptionData, "This is test input...");
	nova_static_FileStability_lines = 100;
	TRY
	{
		nova_ExceptionData_addCode(exceptionData, exceptionData, 1);
		
		{
			File* nova_2_f;
			
			nova_2_f = nova_1_File_construct(0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, "temp"), exceptionData, nova_3_Long_toString(nova_Long_construct(0, exceptionData, nova_static_Time_currentTimeMillis(0, exceptionData)), exceptionData)));
			nova_static_FileStability_createFile((FileStability*)nova_null, exceptionData, nova_0_program, nova_2_f);
			nova_static_FileStability_writeToFile((FileStability*)nova_null, exceptionData, nova_0_program, nova_2_f);
			nova_static_FileStability_readFromFile((FileStability*)nova_null, exceptionData, nova_0_program, nova_2_f);
			nova_static_FileStability_deleteFile((FileStability*)nova_null, exceptionData, nova_0_program, nova_2_f);
		}
	}
	CATCH (1)
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Failed; Exception thrown"));
	}
	FINALLY
	{
	}
	END_TRY;
}

void nova_static_FileStability_createFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Creating file... "));
	if (!nova_File_create(nova_0_f, exceptionData))
	{
		nova_0_StabilityTest_fail(nova_0_program, exceptionData);
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "OK"));
}

void nova_static_FileStability_writeToFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	int nova_1_i;
	String* nova_local_0;
	
	nova_local_0 = nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_static_FileStability_lines), exceptionData);
	nova_static_0_Console_write(0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, "Writing "), exceptionData, nova_local_0->vtable->nova_virtual_0_concat(nova_local_0, exceptionData, nova_String_construct(0, exceptionData, " lines of data to file... "))));
	nova_1_i = 0;
	for (; nova_1_i < nova_static_FileStability_lines; nova_1_i++)
	{
		nova_File_writeLine(nova_0_f, exceptionData, nova_static_FileStability_inputString);
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "OK"));
}

void nova_static_FileStability_readFromFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	int nova_1_times;
	String* nova_1_line;
	
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Reading lines from file... "));
	nova_File_reopen(nova_0_f, exceptionData);
	nova_1_times = 0;
	nova_1_line = nova_File_readLine(nova_0_f, exceptionData);
	while (nova_1_line != (String*)nova_null)
	{
		if (!nova_1_line->vtable->nova_virtual_0_equals(nova_1_line, exceptionData, nova_static_FileStability_inputString))
		{
			nova_0_StabilityTest_fail(nova_0_program, exceptionData);
		}
		nova_1_line = nova_File_readLine(nova_0_f, exceptionData);
		nova_1_times++;
	}
	if (nova_1_times != nova_static_FileStability_lines)
	{
		String* nova_local_0;
		String* nova_local_1;
		
		nova_local_0 = nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_1_times), exceptionData);
		nova_local_1 = nova_2_Int_toString(nova_Int_construct(0, exceptionData, 100), exceptionData);
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, "Failed; only read "), exceptionData, nova_local_0->vtable->nova_virtual_0_concat(nova_local_0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, "/"), exceptionData, nova_local_1->vtable->nova_virtual_0_concat(nova_local_1, exceptionData, nova_String_construct(0, exceptionData, " lines"))))));
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "OK"));
}

void nova_static_FileStability_deleteFile(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, File* nova_0_f)
{
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Deleting file... "));
	if (!nova_File_delete(nova_0_f, exceptionData))
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Failed to delete file"));
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "OK"));
}

void nova_FileStability_this(FileStability* this, ExceptionData* exceptionData)
{
}

void nova_FileStability_super(FileStability* this, ExceptionData* exceptionData)
{
}
