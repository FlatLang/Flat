#include <precompiled.h>
#include <nova/nova_Nova_System.h>


typedef struct nova_exception_Nova_ExceptionData nova_exception_Nova_ExceptionData;
typedef struct nova_Nova_String nova_Nova_String;

typedef void (*nova_Nova_System_closure1_Nova_exit)(void*, nova_exception_Nova_ExceptionData*, int, nova_Nova_String*, char, void*);

nova_Extension_VTable_System nova_Extension_VTable_System_val =
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


void nova_Nova_System_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_Nova_System* nova_Nova_System_Nova_construct(nova_Nova_System* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_Nova_System, this,);
	this->vtable = &nova_Extension_VTable_System_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_Nova_System_Nova_super(this, exceptionData);
	
	{
		nova_Nova_System_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_Nova_System_Nova_destroy(nova_Nova_System** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_Nova_System_0_Nova_exit(nova_Nova_System* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_Nova_System_Nova_code)
{
	exit(nova_Nova_System_Nova_code);
}

void nova_Nova_System_1_Nova_exit(nova_Nova_System* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_Nova_System_Nova_code, nova_Nova_String* nova_Nova_System_Nova_message)
{
	nova_Nova_System_2_Nova_exit(0, exceptionData, nova_Nova_System_Nova_code, nova_Nova_System_Nova_message, 0);
}

void nova_Nova_System_2_Nova_exit(nova_Nova_System* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_Nova_System_Nova_code, nova_Nova_String* nova_Nova_System_Nova_message, char nova_Nova_System_Nova_log)
{
	if (nova_Nova_System_Nova_log)
	{
		nova_io_Nova_File* l1_Nova_f = (nova_io_Nova_File*)nova_null;
		
		l1_Nova_f = nova_io_Nova_File_0_Nova_construct(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Log")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_time_Nova_Time_Accessor_Nova_currentTimeMillis(0, exceptionData))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(".txt")))));
		if (nova_io_Nova_File_Nova_create(l1_Nova_f, exceptionData))
		{
			nova_io_Nova_File_Nova_writeLine(l1_Nova_f, exceptionData, nova_Nova_System_Nova_message);
		}
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_System_Nova_message);
	nova_Nova_System_0_Nova_exit(0, exceptionData, nova_Nova_System_Nova_code);
}

nova_process_Nova_Process* nova_Nova_System_Nova_execute(nova_Nova_System* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_System_Nova_command)
{
	FILE* l1_Nova_pipe = (FILE*)nova_null;
	nova_io_Nova_File* l1_Nova_f = (nova_io_Nova_File*)nova_null;
	
	l1_Nova_pipe = getPipe((char*)(nova_Nova_System_Nova_command->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (nova_Nova_System_closure1_Nova_exit)&nova_Nova_System_2_Nova_exit, nova_null, nova_null);
	l1_Nova_f = nova_io_Nova_File_1_Nova_construct(0, exceptionData, l1_Nova_pipe);
	if (!nova_io_Nova_File_Accessor_Nova_exists(l1_Nova_f, exceptionData))
	{
		nova_Nova_System_1_Nova_exit(0, exceptionData, 1, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Unable to open pipe")));
	}
	return nova_process_Nova_Process_Nova_construct(0, exceptionData, nova_io_Nova_StreamReader_Nova_construct(0, exceptionData, l1_Nova_f));
}

void nova_Nova_System_0_Nova_this(nova_Nova_System* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_Nova_System_Nova_super(nova_Nova_System* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

