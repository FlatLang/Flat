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
	getPipe((char*)("ls"), (char*)("r"));
}
