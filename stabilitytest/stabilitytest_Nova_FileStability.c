#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_FileStability.h>


stabilitytest_Extension_VTable_FileStability stabilitytest_Extension_VTable_FileStability_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_FileStability_0_Nova_test,
};



void stabilitytest_Nova_FileStability_Nova_createFile(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_f);
void stabilitytest_Nova_FileStability_Nova_writeToFile(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_f);
void stabilitytest_Nova_FileStability_Nova_readFromFile(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_f);
void stabilitytest_Nova_FileStability_Nova_deleteFile(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_f);
nova_standard_Nova_String* stabilitytest_Nova_FileStability_Nova_inputString;
int stabilitytest_Nova_FileStability_Nova_lines;
void stabilitytest_Nova_FileStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_FileStability* stabilitytest_Nova_FileStability_0_Nova_construct(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_FileStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_FileStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, l0_Nova_program);
	stabilitytest_Nova_FileStability_2_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_FileStability_0_Nova_this(this, exceptionData, l0_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_FileStability_Nova_destroy(stabilitytest_Nova_FileStability** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_FileStability_0_Nova_this(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, l0_Nova_program);
}

void stabilitytest_Nova_FileStability_0_Nova_test(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking File IO..."));
	stabilitytest_Nova_FileStability_Nova_inputString = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "This is test input...");
	stabilitytest_Nova_FileStability_Nova_lines = 100;
	TRY
	{
		novaEnv.nova_standard_exception_ExceptionData.addCode(exceptionData, exceptionData, 1);
		
		{
			nova_standard_io_Nova_File* l1_Nova_f;
			
			l1_Nova_f = nova_standard_io_Nova_File_3_Nova_construct(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "temp"), exceptionData, nova_standard_primitive_number_Nova_Long_1_Nova_toString(0, exceptionData, nova_standard_time_Nova_Time_Nova_currentTimeMillis(0, exceptionData))));
			stabilitytest_Nova_FileStability_Nova_createFile(this, exceptionData, l1_Nova_f);
			stabilitytest_Nova_FileStability_Nova_writeToFile(this, exceptionData, l1_Nova_f);
			stabilitytest_Nova_FileStability_Nova_readFromFile(this, exceptionData, l1_Nova_f);
			stabilitytest_Nova_FileStability_Nova_deleteFile(this, exceptionData, l1_Nova_f);
		}
	}
	CATCH (1)
	{
		nova_standard_exception_Nova_Exception* l2_Nova_e;
		
		l2_Nova_e = exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException;
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed; Exception thrown"));
	}
	FINALLY
	{
	}
	END_TRY;
}

void stabilitytest_Nova_FileStability_Nova_createFile(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_f)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Creating file... "));
	if (!nova_standard_io_Nova_File_Nova_create(l0_Nova_f, exceptionData))
	{
		stabilitytest_Nova_StabilityTest_0_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData);
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

void stabilitytest_Nova_FileStability_Nova_writeToFile(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_f)
{
	nova_standard_Nova_String* nova_local_0;
	int l1_Nova_i;
	
	nova_local_0 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, stabilitytest_Nova_FileStability_Nova_lines);
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Writing "), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, " lines of data to file... "))));
	l1_Nova_i = 0;
	for (; l1_Nova_i < stabilitytest_Nova_FileStability_Nova_lines; l1_Nova_i++)
	{
		nova_standard_io_Nova_File_Nova_writeLine(l0_Nova_f, exceptionData, stabilitytest_Nova_FileStability_Nova_inputString);
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

void stabilitytest_Nova_FileStability_Nova_readFromFile(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_f)
{
	int l1_Nova_times;
	nova_standard_Nova_String* l1_Nova_line;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Reading lines from file... "));
	nova_standard_io_Nova_File_Nova_reopen(l0_Nova_f, exceptionData);
	l1_Nova_times = 0;
	l1_Nova_line = nova_standard_io_Nova_File_Nova_readLine(l0_Nova_f, exceptionData);
	while (l1_Nova_line != (nova_standard_Nova_String*)nova_null)
	{
		if (!l1_Nova_line->vtable->nova_standard_Nova_String_virtual_Nova_equals(l1_Nova_line, exceptionData, stabilitytest_Nova_FileStability_Nova_inputString))
		{
			stabilitytest_Nova_StabilityTest_0_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData);
		}
		l1_Nova_line = nova_standard_io_Nova_File_Nova_readLine(l0_Nova_f, exceptionData);
		l1_Nova_times++;
	}
	if (l1_Nova_times != stabilitytest_Nova_FileStability_Nova_lines)
	{
		nova_standard_Nova_String* nova_local_0;
		nova_standard_Nova_String* nova_local_1;
		
		nova_local_0 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, l1_Nova_times);
		nova_local_1 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, 100);
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed; only read "), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "/"), exceptionData, nova_local_1->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_1, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, " lines"))))));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

void stabilitytest_Nova_FileStability_Nova_deleteFile(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_io_Nova_File* l0_Nova_f)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Deleting file... "));
	if (!nova_standard_io_Nova_File_Nova_delete(l0_Nova_f, exceptionData))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to delete file"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

void stabilitytest_Nova_FileStability_2_Nova_super(stabilitytest_Nova_FileStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

