#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaSyntaxStability.h>


nova_VTable_stabilitytest_NovaSyntaxStability nova_VTable_stabilitytest_NovaSyntaxStability_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};

void stabilitytest_NovaSyntaxStability_static_NovacheckLoops(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaSyntaxStability_static_NovacheckWhileLoops(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaSyntaxStability_static_NovacheckForLoops(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaSyntaxStability_static_NovacheckUntil(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
nova_standard_NovaObject* stabilitytest_NovaSyntaxStability_static_Novatest1(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaObject* stabilitytest_NovaSyntaxStability_static_Novatest2(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaObject* stabilitytest_NovaSyntaxStability_static_Novatest3(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaSyntaxStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaSyntaxStability* stabilitytest_NovaSyntaxStability_Nova0_construct(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaSyntaxStability, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaSyntaxStability_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaSyntaxStability_Novasuper(this, 0);
	
	{
		stabilitytest_NovaSyntaxStability_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_SyntaxStability(stabilitytest_NovaSyntaxStability** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaSyntaxStability_static_Novatest(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	stabilitytest_NovaSyntaxStability_static_NovacheckLoops((stabilitytest_NovaSyntaxStability*)nova_null, exceptionData, l0_Novaprogram);
	stabilitytest_NovaSyntaxStability_static_NovacheckUntil((stabilitytest_NovaSyntaxStability*)nova_null, exceptionData, l0_Novaprogram);
}

void stabilitytest_NovaSyntaxStability_static_NovacheckLoops(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	stabilitytest_NovaSyntaxStability_static_NovacheckWhileLoops((stabilitytest_NovaSyntaxStability*)nova_null, exceptionData, l0_Novaprogram);
	stabilitytest_NovaSyntaxStability_static_NovacheckForLoops((stabilitytest_NovaSyntaxStability*)nova_null, exceptionData, l0_Novaprogram);
}

void stabilitytest_NovaSyntaxStability_static_NovacheckWhileLoops(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	int l1_Novanum;
	int l1_Novanum2;
	int l1_Novanum3;
	
	nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Checking inline while loop... "));
	l1_Novanum = 0;
	l1_Novanum2 = 0;
	l1_Novanum3 = 0;
	while (l1_Novanum < 100)
	{
		l1_Novanum++;
	}
	while (l1_Novanum2 < 100)
	{
		l1_Novanum2++;
	}
	while (l1_Novanum3 < 100)
	{
		if (1)
		{
			if (1)
			{
				l1_Novanum3++;
			}
		}
	}
	if (l1_Novanum != 100 || l1_Novanum2 != 100 || l1_Novanum3 != 100)
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Inline while loop failed."));
	}
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "OK"));
}

void stabilitytest_NovaSyntaxStability_static_NovacheckForLoops(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	int l1_Novanum;
	int l1_Novanum2;
	int l1_Novanum3;
	int l5_Novai;
	int l6_Novai;
	int l7_Novai;
	
	nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Checking inline for loop... "));
	l1_Novanum = 0;
	l1_Novanum2 = 0;
	l1_Novanum3 = 0;
	l5_Novai = 0;
	for (; l5_Novai < 100; l5_Novai++)
	{
		l1_Novanum++;
	}
	l6_Novai = 0;
	for (; l6_Novai < 100; l6_Novai++)
	{
		l1_Novanum2++;
	}
	l7_Novai = 0;
	for (; l7_Novai < 100; l7_Novai++)
	{
		if (1)
		{
			if (1)
			{
				l1_Novanum3++;
			}
		}
	}
	if (l1_Novanum != 100 || l1_Novanum2 != 100 || l1_Novanum3 != 100)
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Inline for loop failed."));
	}
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "OK"));
}

void stabilitytest_NovaSyntaxStability_static_NovacheckUntil(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	nova_standard_NovaObject* l1_Novaobj;
	
	nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Checking until statement... "));
	l1_Novaobj = (nova_standard_NovaObject*)nova_null;
	if (!(l1_Novaobj != (nova_standard_NovaObject*)nova_null))
	{
		l1_Novaobj = stabilitytest_NovaSyntaxStability_static_Novatest1((stabilitytest_NovaSyntaxStability*)nova_null, exceptionData);
		if (!(l1_Novaobj != (nova_standard_NovaObject*)nova_null))
		{
			l1_Novaobj = stabilitytest_NovaSyntaxStability_static_Novatest2((stabilitytest_NovaSyntaxStability*)nova_null, exceptionData);
			if (!(l1_Novaobj != (nova_standard_NovaObject*)nova_null))
			{
				l1_Novaobj = stabilitytest_NovaSyntaxStability_static_Novatest3((stabilitytest_NovaSyntaxStability*)nova_null, exceptionData);
				if (!(l1_Novaobj != (nova_standard_NovaObject*)nova_null))
				{
					stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to stop after correct condition"));
				}
			}
		}
	}
	if (l1_Novaobj == (nova_standard_NovaObject*)nova_null)
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to reach correct condition"));
	}
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "OK"));
}

nova_standard_NovaObject* stabilitytest_NovaSyntaxStability_static_Novatest1(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return (nova_standard_NovaObject*)nova_null;
}

nova_standard_NovaObject* stabilitytest_NovaSyntaxStability_static_Novatest2(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return (nova_standard_NovaObject*)nova_null;
}

nova_standard_NovaObject* stabilitytest_NovaSyntaxStability_static_Novatest3(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaObject_Nova0_construct(0, exceptionData);
}

void stabilitytest_NovaSyntaxStability_Novathis(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaSyntaxStability_Novasuper(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
