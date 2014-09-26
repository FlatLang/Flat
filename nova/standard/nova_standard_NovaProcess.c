#include <precompiled.h>
#include <nova/standard/nova_standard_NovaProcess.h>


nova_VTable_nova_standard_NovaProcess nova_VTable_nova_standard_NovaProcess_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};

nova_standard_NovaProcess* nova_standard_NovaProcess_Novaconstruct(nova_standard_NovaProcess* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaStreamReader* l0_Novareader)
{
	CCLASS_NEW(nova_standard_NovaProcess, this,);
	this->vtable = &nova_VTable_nova_standard_NovaProcess_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaProcess_Novasuper(this, 0);
	
	{
		nova_standard_NovaProcess_Novathis(this, exceptionData, l0_Novareader);
	}
	
	return this;
}

void nova_del_Process(nova_standard_NovaProcess** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_StreamReader(&(*this)->nova_standard_NovaProcess_Novareader, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaProcess_Novathis(nova_standard_NovaProcess* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaStreamReader* l0_Novareader)
{
	this->nova_standard_NovaProcess_Novareader = l0_Novareader;
}

void nova_standard_NovaProcess_Novasuper(nova_standard_NovaProcess* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_NovaProcess_Novareader = (nova_standard_NovaStreamReader*)nova_null;
}
