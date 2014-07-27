#include <precompiled.h>
#include "NovaSystem.h"


nova_VTable_System nova_VTable_System_val =
{
	nova_2_Object_toString,
	nova_2_Object_equals,
};

System* nova_System_System(ExceptionData* exceptionData)
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
	nova_static_1_Console_writeLine(0, exceptionData, nova_0_message);
	nova_static_1_System_exit((System*)0, exceptionData, nova_0_code);
}

int nova_static_System_execute(System* this, ExceptionData* exceptionData, String* nova_0_command)
{
	FILE* nova_1_pipe;
	File* nova_1_file;
	StreamReader* nova_1_reader;
	String* nova_1_s;
	
	nova_1_pipe = _popen((char*)("dir"), (char*)("rt"));
	if (nova_1_pipe == NULL)
	{
		nova_static_2_System_exit((System*)0, exceptionData, 1, nova_String_String(exceptionData, "Unable to open pipe"));
	}
	nova_1_file = nova_2_File_File(exceptionData, nova_1_pipe);
	nova_1_reader = nova_StreamReader_StreamReader(exceptionData, nova_1_file);
	nova_1_s = nova_StreamReader_read(nova_1_reader, exceptionData);
	nova_static_1_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)("HOLY FRICKEN: ")), exceptionData, nova_String_concat(nova_1_s, exceptionData, nova_String_String(exceptionData, (char*)(" CRAP")))));
}
