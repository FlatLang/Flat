#include <precompiled.h>
#include <nova/standard/process/nova_standard_process_NovaProcess.h>


nova_VTable_nova_standard_process_NovaProcess nova_VTable_nova_standard_process_NovaProcess_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_process_NovaProcessNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_process_NovaProcess* nova_standard_process_NovaProcess_Novaconstruct(nova_standard_process_NovaProcess* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaStreamReader* l0_Novareader)
{
	CCLASS_NEW(nova_standard_process_NovaProcess, this,);
	this->vtable = &nova_VTable_nova_standard_process_NovaProcess_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_process_NovaProcess_Novasuper(this, 0);
	
	{
		nova_standard_process_NovaProcess_Novathis(this, exceptionData, l0_Novareader);
	}
	
	return this;
}

void nova_del_Process(nova_standard_process_NovaProcess** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_StreamReader(&(*this)->nova_standard_process_NovaProcess_Novareader, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_process_NovaProcess_Novathis(nova_standard_process_NovaProcess* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_io_NovaStreamReader* l0_Novareader)
{
	this->nova_standard_process_NovaProcess_Novareader = l0_Novareader;
}

void nova_standard_process_NovaProcess_Novasuper(nova_standard_process_NovaProcess* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_process_NovaProcess_Novareader = (nova_standard_io_NovaStreamReader*)nova_null;
}
