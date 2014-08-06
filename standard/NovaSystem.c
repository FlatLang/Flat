#include <precompiled.h>
#include "NovaSystem.h"

typedef void (*nova_1_0_exit)(void*, ExceptionData*, int, String*, char);

nova_VTable_System nova_VTable_System_val =
{
	nova_4_Object_toString,
	nova_2_Object_equals,
};

System* nova_System_construct(System* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(System, this,);
	
	this->vtable = &nova_VTable_System_val;
	{
	}
	
	return this;
}

void nova_del_System(System** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_1_System_exit(System* this, ExceptionData* exceptionData, int nova_0_code)
{
	exit(nova_0_code);
}

void nova_static_2_System_exit(System* this, ExceptionData* exceptionData, int nova_0_code, String* nova_0_message)
{
	nova_static_3_System_exit((System*)0, exceptionData, nova_0_code, nova_0_message, 0);
}

void nova_static_3_System_exit(System* this, ExceptionData* exceptionData, int nova_0_code, String* nova_0_message, char nova_0_log)
{
	if (nova_0_log)
	{
		File* nova_2_f;
		
		nova_2_f = nova_1_File_construct(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "Log"), exceptionData, nova_String_concat(nova_2_Long_toString(nova_Long_construct(0, exceptionData, nova_static_Time_currentTimeMillis(0, exceptionData)), exceptionData), exceptionData, nova_String_construct(0, exceptionData, ".txt"))));
		if (nova_File_create(nova_2_f, exceptionData))
		{
			nova_File_writeLine(nova_2_f, exceptionData, nova_0_message);
		}
	}
	nova_static_1_Console_writeLine(0, exceptionData, nova_0_message);
	nova_static_1_System_exit((System*)0, exceptionData, nova_0_code);
}

Process* nova_static_System_execute(System* this, ExceptionData* exceptionData, String* nova_0_command)
{
	FILE* nova_1_pipe;
	File* nova_1_f;
	StreamReader* nova_1_reader;
	Process* nova_1_process;
	
	nova_1_pipe = getPipe(nova_String_toCharArray(nova_0_command, exceptionData), (nova_1_0_exit)&nova_static_3_System_exit, (System*)0);
	nova_1_f = nova_2_File_construct(0, exceptionData, nova_1_pipe);
	if (!nova_File_exists(nova_1_f, exceptionData))
	{
		nova_static_2_System_exit((System*)0, exceptionData, 1, nova_String_construct(0, exceptionData, "Unable to open pipe"));
	}
	nova_1_reader = nova_StreamReader_construct(0, exceptionData, nova_1_f);
	nova_1_process = nova_Process_construct(0, exceptionData, nova_1_reader);
	return nova_1_process;
}
