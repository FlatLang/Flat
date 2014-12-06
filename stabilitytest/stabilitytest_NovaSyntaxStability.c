#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaSyntaxStability.h>


nova_VTable_stabilitytest_NovaSyntaxStability nova_VTable_stabilitytest_NovaSyntaxStability_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
};

void stabilitytest_NovaSyntaxStability_static_NovacheckSwitchStatements(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
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

stabilitytest_NovaSyntaxStability* stabilitytest_NovaSyntaxStability_0_Novaconstruct(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaSyntaxStability, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaSyntaxStability_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaSyntaxStability_Novasuper(this, exceptionData);
	
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
	stabilitytest_NovaSyntaxStability_static_NovacheckSwitchStatements((stabilitytest_NovaSyntaxStability*)nova_null, exceptionData, l0_Novaprogram);
}

void stabilitytest_NovaSyntaxStability_static_NovacheckSwitchStatements(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	int l1_Novanum;
	char l1_Novaworked;
	int l1_Novaval;
	char l1_Novaworked2;
	char nova_local_0;
	int nova_local_1;
	
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking simple switch statement... "));
	l1_Novanum = 3;
	l1_Novaworked = 0;
	switch (l1_Novanum)
	{
		case 1:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 2:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 3:
		l1_Novaworked = 1;
		break;
		case 4:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		default:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch failed to accept the correct case"));
	}
	if (!l1_Novaworked)
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "FAIL"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking simple switch statement with indentation... "));
	l1_Novanum = 3;
	l1_Novaworked = 0;
	switch (l1_Novanum)
	{
		case 1:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 2:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 3:
		l1_Novaworked = 1;
		break;
		case 4:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		default:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch failed to accept the correct case"));
	}
	if (!l1_Novaworked)
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "FAIL"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking simple switch statement with scopes... "));
	l1_Novanum = 3;
	l1_Novaworked = 0;
	switch (l1_Novanum)
	{
		case 1:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 2:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 3:
		l1_Novaworked = 1;
		break;
		case 4:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		default:
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch failed to accept the correct case"));
	}
	if (!l1_Novaworked)
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "FAIL"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking switch statement with variable case values... "));
	l1_Novaval = 1;
	l1_Novanum = 3;
	l1_Novaworked = 0;
	if (l1_Novanum == (l1_Novaval++))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
	}
	else if (l1_Novanum == l1_Novaval++)
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
	}
	else if (l1_Novanum == (l1_Novaval++))
	{
		l1_Novaworked = 1;
	}
	else if (l1_Novanum == l1_Novaval++)
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
	}
	else
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch failed to accept the correct case"));
	}
	if (!l1_Novaworked)
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "FAIL"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking switch statement with variable case values and fallthrough... "));
	l1_Novaval = 1;
	l1_Novanum = 3;
	l1_Novaworked = 0;
	l1_Novaworked2 = 0;
	nova_local_1 = l1_Novanum++;
	do
	{
		if (nova_local_1 == l1_Novaval++)
		{
			stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
			break;
		}
		else if (nova_local_1 == l1_Novaval++)
		{
			stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch accepted incorrect case"));
			break;
		}
		else if (nova_local_1 == l1_Novaval++)
		{
			l1_Novaworked = 1;
			nova_local_0 = 1;
		}
		if (nova_local_0 || nova_local_1 == l1_Novaval++)
		{
			l1_Novaworked2 = 1;
			break;
		}
		else
		{
			stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Switch failed to accept the correct case"));
		}
	}
	while (0);
	if (!l1_Novaworked || !l1_Novaworked2)
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "FAIL"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
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
	
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking inline while loop... "));
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
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Inline while loop failed."));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
}

void stabilitytest_NovaSyntaxStability_static_NovacheckForLoops(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	int l1_Novanum;
	int l1_Novanum2;
	int l1_Novanum3;
	int l5_Novai;
	int l6_Novai;
	int l7_Novai;
	
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking inline for loop... "));
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
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Inline for loop failed."));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
}

void stabilitytest_NovaSyntaxStability_static_NovacheckUntil(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	nova_standard_NovaObject* l1_Novaobj;
	
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking until statement... "));
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
					stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Failed to stop after correct condition"));
				}
			}
		}
	}
	if (l1_Novaobj == (nova_standard_NovaObject*)nova_null)
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Failed to reach correct condition"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
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
	return nova_standard_NovaObject_0_Novaconstruct(0, exceptionData);
}

void stabilitytest_NovaSyntaxStability_Novathis(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaSyntaxStability_Novasuper(stabilitytest_NovaSyntaxStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
