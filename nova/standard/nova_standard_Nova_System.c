#include <precompiled.h>
#include <nova/standard/nova_standard_Nova_System.h>
typedef struct nova_standard_exception_Nova_ExceptionData nova_standard_exception_Nova_ExceptionData;
typedef struct nova_standard_Nova_String nova_standard_Nova_String;

typedef void (*nova_standard_Nova_System_closure1_Nova_exit)(void*, nova_standard_exception_Nova_ExceptionData*, int, nova_standard_Nova_String*, char);

nova_standard_Extension_VTable_System nova_standard_Extension_VTable_System_val =
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
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_Nova_SystemNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_Nova_System* nova_standard_Nova_System_0_Nova_construct(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_Nova_System, this,);
	this->vtable = &nova_standard_Extension_VTable_System_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_System_Nova_super(this, exceptionData);
	
	{
		nova_standard_Nova_System_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_Nova_System_Nova_destroy(nova_standard_Nova_System** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_Nova_System_0_Nova_exit(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_Nova_System_Nova_code)
{
	exit((int)(nova_standard_Nova_System_Nova_code));
}

void nova_standard_Nova_System_1_Nova_exit(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_Nova_System_Nova_code, nova_standard_Nova_String* nova_standard_Nova_System_Nova_message)
{
	nova_standard_Nova_System_2_Nova_exit((nova_standard_Nova_System*)nova_null, exceptionData, nova_standard_Nova_System_Nova_code, nova_standard_Nova_System_Nova_message, 0);
}

void nova_standard_Nova_System_2_Nova_exit(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_Nova_System_Nova_code, nova_standard_Nova_String* nova_standard_Nova_System_Nova_message, char nova_standard_Nova_System_Nova_log)
{
	if (nova_standard_Nova_System_Nova_log)
	{
		nova_standard_io_Nova_File* l1_Nova_f;
		
		l1_Nova_f = nova_standard_io_Nova_File_1_Nova_construct(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Log"), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_standard_time_Nova_Time_Accessor_Nova_currentTimeMillis(0, exceptionData)), exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ".txt"))));
		if (nova_standard_io_Nova_File_Nova_create(l1_Nova_f, exceptionData))
		{
			nova_standard_io_Nova_File_Nova_writeLine(l1_Nova_f, exceptionData, nova_standard_Nova_System_Nova_message);
		}
	}
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_System_Nova_message);
	nova_standard_Nova_System_0_Nova_exit((nova_standard_Nova_System*)nova_null, exceptionData, nova_standard_Nova_System_Nova_code);
}

nova_standard_process_Nova_Process* nova_standard_Nova_System_Nova_execute(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_Nova_System_Nova_command)
{
	FILE* l1_Nova_pipe;
	nova_standard_io_Nova_File* l1_Nova_f;
	nova_standard_io_Nova_StreamReader* l1_Nova_reader;
	nova_standard_process_Nova_Process* l1_Nova_process;
	
	l1_Nova_pipe = getPipe((char*)(nova_standard_Nova_System_Nova_command->nova_standard_Nova_String_Nova_chars), (nova_standard_Nova_System_closure1_Nova_exit)&nova_standard_Nova_System_0_Nova_exit, (nova_standard_Nova_System*)nova_null);
	l1_Nova_f = nova_standard_io_Nova_File_2_Nova_construct(0, exceptionData, l1_Nova_pipe);
	if (!nova_standard_io_Nova_File_Accessor_Nova_exists(l1_Nova_f, exceptionData))
	{
		nova_standard_Nova_System_1_Nova_exit((nova_standard_Nova_System*)nova_null, exceptionData, 1, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Unable to open pipe"));
	}
	l1_Nova_reader = nova_standard_io_Nova_StreamReader_Nova_construct(0, exceptionData, l1_Nova_f);
	l1_Nova_process = nova_standard_process_Nova_Process_Nova_construct(0, exceptionData, l1_Nova_reader);
	return l1_Nova_process;
}

void nova_standard_Nova_System_0_Nova_this(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_Nova_System_Nova_super(nova_standard_Nova_System* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

