#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaTimeStability.h>


nova_VTable_stabilitytest_NovaTimeStability nova_VTable_stabilitytest_NovaTimeStability_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
};
void stabilitytest_NovaTimeStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaTimeStability* stabilitytest_NovaTimeStability_0_Novaconstruct(stabilitytest_NovaTimeStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaTimeStability, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaTimeStability_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaTimeStability_Novasuper(this, exceptionData);
	
	{
		stabilitytest_NovaTimeStability_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_TimeStability(stabilitytest_NovaTimeStability** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaTimeStability_static_Novatest(stabilitytest_NovaTimeStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	long l1_Novastart;
	long l1_Novatime;
	
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking Time.nova... "));
	l1_Novastart = nova_standard_time_NovaTime_static_NovacurrentTimeMillis(0, exceptionData);
	nova_standard_thread_NovaThread_static_Novasleep(0, exceptionData, (long)(100));
	l1_Novatime = nova_standard_time_NovaTime_static_NovacurrentTimeMillis(0, exceptionData) - l1_Novastart;
	if (l1_Novatime >= 100 && l1_Novatime < 130)
	{
		nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
	}
	else
	{
		nova_standard_NovaString* nova_local_0;
		
		nova_local_0 = nova_standard_primitive_number_NovaLong_static_1_NovatoString(0, exceptionData, l1_Novatime);
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_0_Novaconcat(nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Failed; expected 100ms, found "), exceptionData, nova_local_0->vtable->nova_standard_NovaString_virtual0_Novaconcat(nova_local_0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "ms"))));
	}
}

void stabilitytest_NovaTimeStability_Novathis(stabilitytest_NovaTimeStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaTimeStability_Novasuper(stabilitytest_NovaTimeStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
