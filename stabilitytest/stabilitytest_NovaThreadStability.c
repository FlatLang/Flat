#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaThreadStability.h>


nova_VTable_stabilitytest_NovaThreadStability nova_VTable_stabilitytest_NovaThreadStability_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
};

void stabilitytest_NovaThreadStability_static_NovacreateThreads(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram, stabilitytest_NovaThreadImplementation** l0_Novathreads, int l0_Novaamount);
void stabilitytest_NovaThreadStability_static_NovacheckMemoryAccess(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaThreadStability_static_NovajoinThreads(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaThreadImplementation** l0_Novathreads, int l0_Novaamount);
void stabilitytest_NovaThreadStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaThreadStability* stabilitytest_NovaThreadStability_0_Novaconstruct(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaThreadStability, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaThreadStability_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaThreadStability_Novasuper(this, exceptionData);
	
	{
		stabilitytest_NovaThreadStability_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_ThreadStability(stabilitytest_NovaThreadStability** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaThreadStability_static_Novatest(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	stabilitytest_NovaThreadImplementation** l1_Novathreads;
	
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking Thread.nova with 20 Threads... "));
	l1_Novathreads = (stabilitytest_NovaThreadImplementation**)NOVA_MALLOC(sizeof(stabilitytest_NovaThreadImplementation*[20]));
	stabilitytest_NovaThreadStability_static_NovacreateThreads((stabilitytest_NovaThreadStability*)nova_null, exceptionData, l0_Novaprogram, l1_Novathreads, 20);
	stabilitytest_NovaThreadStability_static_NovacheckMemoryAccess((stabilitytest_NovaThreadStability*)nova_null, exceptionData);
	stabilitytest_NovaThreadStability_static_NovajoinThreads((stabilitytest_NovaThreadStability*)nova_null, exceptionData, l1_Novathreads, 20);
}

void stabilitytest_NovaThreadStability_static_NovacreateThreads(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram, stabilitytest_NovaThreadImplementation** l0_Novathreads, int l0_Novaamount)
{
	stabilitytest_NovaStabilityExceptionHandler* l1_Novahandler;
	int l2_Novai;
	
	l1_Novahandler = stabilitytest_NovaStabilityExceptionHandler_Novaconstruct(0, exceptionData, l0_Novaprogram);
	l2_Novai = 0;
	for (; l2_Novai < l0_Novaamount; l2_Novai++)
	{
		l0_Novathreads[l2_Novai] = stabilitytest_NovaThreadImplementation_Novaconstruct(0, exceptionData, 10, 10);
		nova_standard_thread_NovaThread_Novastart((nova_standard_thread_NovaThread*)(l0_Novathreads[l2_Novai]), exceptionData);
	}
}

void stabilitytest_NovaThreadStability_static_NovacheckMemoryAccess(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	int l2_Novai;
	
	nova_standard_thread_NovaThread_static_Novasleep(0, exceptionData, (long)(30));
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking memory access with multi-threading... "));
	l2_Novai = 0;
	for (; l2_Novai < 1000; l2_Novai++)
	{
		nova_standard_NovaString* l2_Novas;
		
		l2_Novas = nova_standard_primitive_number_NovaInt_static_1_NovatoString(0, exceptionData, l2_Novai);
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
}

void stabilitytest_NovaThreadStability_static_NovajoinThreads(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaThreadImplementation** l0_Novathreads, int l0_Novaamount)
{
	int l2_Novai;
	
	l2_Novai = 0;
	for (; l2_Novai < l0_Novaamount; l2_Novai++)
	{
		nova_standard_thread_NovaThread_Novajoin((nova_standard_thread_NovaThread*)(l0_Novathreads[l2_Novai]), exceptionData);
	}
}

void stabilitytest_NovaThreadStability_Novathis(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaThreadStability_Novasuper(stabilitytest_NovaThreadStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
