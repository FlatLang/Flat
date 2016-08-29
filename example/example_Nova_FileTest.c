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
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void example_Nova_FileTest_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_FileTest* example_Nova_FileTest_Nova_construct(example_Nova_FileTest* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_FileTest, this,);
	this->vtable = &example_Extension_VTable_FileTest_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_FileTest_Nova_super(this, exceptionData);
	
	{
		example_Nova_FileTest_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_FileTest_Nova_destroy(example_Nova_FileTest** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_FileTest_Nova_main(example_Nova_FileTest* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_FileTest_Nova_args)
{
	nova_io_Nova_File* l1_Nova_f = (nova_io_Nova_File*)nova_null;
	
	l1_Nova_f = nova_io_Nova_File_0_Nova_construct(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("C:/Users/Braden Steffaniak/test3.txt")));
	if (nova_io_Nova_File_Accessor_Nova_exists(l1_Nova_f, exceptionData))
	{
		nova_Nova_String* l1_Nova_data = (nova_Nova_String*)nova_null;
		
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Your file exists!")));
		nova_io_Nova_File_Nova_writeLine(l1_Nova_f, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Entering data..")));
		nova_io_Nova_File_Nova_writeLine(l1_Nova_f, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("asdfasdf thing.")));
		nova_io_Nova_File_Nova_reopen(l1_Nova_f, exceptionData);
		l1_Nova_data = nova_io_Nova_File_Nova_readAllContents(l1_Nova_f, exceptionData);
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, l1_Nova_data);
	}
	else
	{
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Your file does not exist..")));
	}
	nova_io_Nova_File_Nova_close(l1_Nova_f, exceptionData);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Press enter to exit...")));
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_FileTest_0_Nova_this(example_Nova_FileTest* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_FileTest_Nova_super(example_Nova_FileTest* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

