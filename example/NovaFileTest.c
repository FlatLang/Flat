#include <precompiled.h>
#include "NovaFileTest.h"


nova_VTable_FileTest nova_VTable_FileTest_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

FileTest* nova_FileTest_construct(FileTest* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(FileTest, this,);
	this->vtable = &nova_VTable_FileTest_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_FileTest_super(this, 0);
	
	{
		nova_FileTest_this(this, exceptionData);
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
	
	nova_1_f = nova_1_File_construct(0, exceptionData, nova_String_construct(0, exceptionData, "C:/Users/Braden Steffaniak/test3.txt"));
	if (nova_File_exists(nova_1_f, exceptionData))
	{
		String* nova_2_data;
		
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Your file exists!"));
		nova_File_writeLine(nova_1_f, exceptionData, nova_String_construct(0, exceptionData, "Entering data.."));
		nova_File_writeLine(nova_1_f, exceptionData, nova_String_construct(0, exceptionData, "asdfasdf thing."));
		nova_File_reopen(nova_1_f, exceptionData);
		nova_2_data = nova_File_readAllContents(nova_1_f, exceptionData);
		nova_static_0_Console_writeLine(0, exceptionData, nova_2_data);
	}
	else
	{
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Your file does not exist.."));
	}
	nova_File_close(nova_1_f, exceptionData);
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Press enter to exit..."));
	nova_static_Console_waitForEnter(0, exceptionData);
}

void nova_FileTest_this(FileTest* this, ExceptionData* exceptionData)
{
}

void nova_FileTest_super(FileTest* this, ExceptionData* exceptionData)
{
}
