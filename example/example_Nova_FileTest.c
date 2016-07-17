#include <precompiled.h>
#include <example/example_Nova_FileTest.h>

example_Extension_VTable_FileTest example_Extension_VTable_FileTest_val =
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
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void example_Nova_FileTestNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_FileTest* example_Nova_FileTest_Nova_FileTest(example_Nova_FileTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_FileTest, this,);
	this->vtable = &example_Extension_VTable_FileTest_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_FileTest_Nova_super(this, exceptionData);
	
	{
		example_Nova_FileTest_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_FileTest_Nova_destroy(example_Nova_FileTest** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_FileTest_Nova_main(example_Nova_FileTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_FileTest_Nova_args)
{
	nova_standard_io_Nova_File* l1_Nova_f = (nova_standard_io_Nova_File*)nova_null;
	
	l1_Nova_f = nova_standard_io_Nova_File_0_Nova_File(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "C:/Users/Braden Steffaniak/test3.txt"));
	if (nova_standard_io_Nova_File_Accessor_Nova_exists(l1_Nova_f, exceptionData))
	{
		nova_standard_Nova_String* l1_Nova_data = (nova_standard_Nova_String*)nova_null;
		
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Your file exists!"));
		nova_standard_io_Nova_File_Nova_writeLine(l1_Nova_f, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Entering data.."));
		nova_standard_io_Nova_File_Nova_writeLine(l1_Nova_f, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "asdfasdf thing."));
		nova_standard_io_Nova_File_Nova_reopen(l1_Nova_f, exceptionData);
		l1_Nova_data = nova_standard_io_Nova_File_Nova_readAllContents(l1_Nova_f, exceptionData);
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, l1_Nova_data);
	}
	else
	{
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Your file does not exist.."));
	}
	nova_standard_io_Nova_File_Nova_close(l1_Nova_f, exceptionData);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Press enter to exit..."));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_FileTest_0_Nova_this(example_Nova_FileTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_FileTest_Nova_super(example_Nova_FileTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

