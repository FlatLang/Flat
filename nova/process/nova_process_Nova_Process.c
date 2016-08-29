#include <precompiled.h>
#include <nova/process/nova_process_Nova_Process.h>



nova_process_Extension_VTable_Process nova_process_Extension_VTable_Process_val =
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


void nova_process_Nova_Process_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_process_Nova_Process* nova_process_Nova_Process_Nova_construct(nova_process_Nova_Process* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_StreamReader* nova_process_Nova_Process_Nova_reader)
{
	CCLASS_NEW(nova_process_Nova_Process, this,);
	this->vtable = &nova_process_Extension_VTable_Process_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_process_Nova_Process_Nova_super(this, exceptionData);
	
	{
		nova_process_Nova_Process_Nova_this(this, exceptionData, nova_process_Nova_Process_Nova_reader);
	}
	
	return this;
}

void nova_process_Nova_Process_Nova_destroy(nova_process_Nova_Process** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_io_Nova_StreamReader_Nova_destroy(&(*this)->nova_process_Nova_Process_Nova_reader, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_process_Nova_Process_Nova_this(nova_process_Nova_Process* this, nova_exception_Nova_ExceptionData* exceptionData, nova_io_Nova_StreamReader* nova_process_Nova_Process_Nova_reader)
{
	this->nova_process_Nova_Process_Nova_reader = nova_process_Nova_Process_Nova_reader;
}

void nova_process_Nova_Process_Nova_super(nova_process_Nova_Process* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_process_Nova_Process_Nova_reader = (nova_io_Nova_StreamReader*)nova_null;
}

