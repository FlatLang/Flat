#include <precompiled.h>
#include <nova/standard/nova_standard_NovaSystem.h>

typedef void (*l0_Nova1_exit)(void*, nova_standard_exception_NovaExceptionData*, int, nova_standard_NovaString*, char);

nova_VTable_nova_standard_NovaSystem nova_VTable_nova_standard_NovaSystem_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_NovaSystemNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_NovaSystem* nova_standard_NovaSystem_Nova0_construct(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaSystem, this,);
	this->vtable = &nova_VTable_nova_standard_NovaSystem_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaSystem_Novasuper(this, 0);
	
	{
		nova_standard_NovaSystem_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_System(nova_standard_NovaSystem** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaSystem_static_Nova0_exit(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode)
{
	exit((int)(l0_Novacode));
}

void nova_standard_NovaSystem_static_Nova1_exit(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode, nova_standard_NovaString* l0_Novamessage)
{
	nova_standard_NovaSystem_static_Nova2_exit((nova_standard_NovaSystem*)nova_null, exceptionData, l0_Novacode, l0_Novamessage, 0);
}

void nova_standard_NovaSystem_static_Nova2_exit(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novacode, nova_standard_NovaString* l0_Novamessage, char l0_Novalog)
{
	if (l0_Novalog)
	{
		nova_standard_io_NovaFile* l2_Novaf;
		
		l2_Novaf = nova_standard_io_NovaFile_Nova1_construct(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Log"), exceptionData, nova_standard_primitive_number_NovaLong_static_Nova0_toString(0, exceptionData, nova_standard_time_NovaTime_static_NovacurrentTimeMillis(0, exceptionData))));
		if (nova_standard_io_NovaFile_Novacreate(l2_Novaf, exceptionData))
		{
			nova_standard_io_NovaFile_NovawriteLine(l2_Novaf, exceptionData, l0_Novamessage);
		}
	}
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, l0_Novamessage);
	nova_standard_NovaSystem_static_Nova0_exit((nova_standard_NovaSystem*)nova_null, exceptionData, l0_Novacode);
}

nova_standard_process_NovaProcess* nova_standard_NovaSystem_static_Novaexecute(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novacommand)
{
	FILE* l1_Novapipe;
	nova_standard_io_NovaFile* l1_Novaf;
	nova_standard_io_NovaStreamReader* l1_Novareader;
	nova_standard_process_NovaProcess* l1_Novaprocess;
	
	l1_Novapipe = getPipe((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novacommand, exceptionData)), (l0_Nova1_exit)&nova_standard_NovaSystem_static_Nova2_exit, (nova_standard_NovaSystem*)nova_null);
	l1_Novaf = nova_standard_io_NovaFile_Nova2_construct(0, exceptionData, l1_Novapipe);
	if (!nova_standard_io_NovaFile_Novaexists(l1_Novaf, exceptionData))
	{
		nova_standard_NovaSystem_static_Nova1_exit((nova_standard_NovaSystem*)nova_null, exceptionData, 1, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Unable to open pipe"));
	}
	l1_Novareader = nova_standard_io_NovaStreamReader_Novaconstruct(0, exceptionData, l1_Novaf);
	l1_Novaprocess = nova_standard_process_NovaProcess_Novaconstruct(0, exceptionData, l1_Novareader);
	return l1_Novaprocess;
}

void nova_standard_NovaSystem_Novathis(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaSystem_Novasuper(nova_standard_NovaSystem* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
