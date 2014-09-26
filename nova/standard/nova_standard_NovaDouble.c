#include <precompiled.h>
#include <nova/standard/nova_standard_NovaDouble.h>


nova_VTable_nova_standard_NovaDouble nova_VTable_nova_standard_NovaDouble_val =
{
	nova_standard_NovaDouble_Novanull3_toString,
	nova_standard_NovaObject_Novanull0_equals,
	nova_standard_NovaNumber_static_Novanull0_numDigits,
	nova_standard_NovaNumber_static_Novanull1_toString,
};

nova_standard_NovaString* nova_standard_NovaDouble_static_NovagenString(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novabuffer, int l0_NovalastIndex);
char* nova_standard_NovaDouble_static_NovagenBuffer(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue);
int nova_standard_NovaDouble_static_Novarepetition(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novabuffer, int l0_Novastart);
int nova_standard_NovaDouble_static_NovalastSignificantDigit(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novabuffer, int l0_Novastart);

nova_standard_NovaDouble* nova_standard_NovaDouble_Novaconstruct(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue)
{
	CCLASS_NEW(nova_standard_NovaDouble, this,);
	this->vtable = &nova_VTable_nova_standard_NovaDouble_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaNumber_Novasuper((nova_standard_NovaNumber*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaNumber_Novathis((nova_standard_NovaNumber*)(this), exceptionData);
	nova_standard_NovaDouble_Novasuper(this, 0);
	
	{
		nova_standard_NovaDouble_Novathis(this, exceptionData, l0_Novavalue);
	}
	
	return this;
}

void nova_del_Double(nova_standard_NovaDouble** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaDouble_Novathis(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue)
{
	this->nova_standard_NovaDouble_Novavalue = l0_Novavalue;
}

int nova_standard_NovaDouble_static_NovanumDigits(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	int l1_Novasize;
	
	if (l0_Novanumber < 0)
	{
		return nova_standard_NovaDouble_static_NovanumDigits((nova_standard_NovaDouble*)nova_null, exceptionData, -l0_Novanumber) + 1;
	}
	l0_Novanumber = l0_Novanumber / 10;
	l1_Novasize = 1;
	for (; l0_Novanumber > 0; l1_Novasize++)
	{
		l0_Novanumber = l0_Novanumber / 10;
	}
	return l1_Novasize;
}

nova_standard_NovaString* nova_standard_NovaDouble_static_NovagenString(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novabuffer, int l0_NovalastIndex)
{
	l0_Novabuffer = realloc(l0_Novabuffer, ++l0_NovalastIndex + 1);
	l0_Novabuffer[l0_NovalastIndex] = '\0';
	return nova_standard_NovaString_Novaconstruct(0, exceptionData, l0_Novabuffer);
}

char* nova_standard_NovaDouble_static_NovagenBuffer(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue)
{
	int l1_Novasize;
	char* l1_Novabuffer;
	
	l1_Novasize = 11 + 1 + 15;
	l1_Novabuffer = (char*)NOVA_MALLOC(sizeof(char) * (l1_Novasize));
	sprintf(l1_Novabuffer, "%.15f", l0_Novavalue);
	return l1_Novabuffer;
}

int nova_standard_NovaDouble_static_Novarepetition(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novabuffer, int l0_Novastart)
{
	int l1_Novaindex;
	char l1_Novac;
	
	l1_Novaindex = l0_Novastart;
	l1_Novac = l0_Novabuffer[l1_Novaindex];
	while (l0_Novabuffer[--l1_Novaindex] == l1_Novac);
	return l0_Novastart - l1_Novaindex - 1;
}

int nova_standard_NovaDouble_static_NovalastSignificantDigit(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novabuffer, int l0_Novastart)
{
	while (l0_Novabuffer[l0_Novastart--] == '0');
	return l0_Novastart + 1;
}

nova_standard_NovaString* nova_standard_NovaDouble_static_Novanull2_toString(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue)
{
	char* l1_Novabuffer;
	int l1_Novasize;
	int l1_NovalastIndex;
	char l1_Novac;
	
	l1_Novabuffer = nova_standard_NovaDouble_static_NovagenBuffer((nova_standard_NovaDouble*)nova_null, exceptionData, l0_Novavalue);
	l1_Novasize = strlen(l1_Novabuffer);
	l1_NovalastIndex = l1_Novasize - 1;
	l1_Novac = l1_Novabuffer[--l1_NovalastIndex];
	if (l1_Novac == '0' || l1_Novac == '9')
	{
		while (l1_Novabuffer[l1_NovalastIndex--] == l1_Novac);
		if (l1_Novabuffer[++l1_NovalastIndex] == '.')
		{
			l1_NovalastIndex++;
			return nova_standard_NovaDouble_static_NovagenString((nova_standard_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_NovalastIndex);
		}
		else
		{
			if (l1_NovalastIndex >= l1_Novasize - 3 - 4)
			{
				l1_NovalastIndex = l1_Novasize - 1;
			}
			else if (l1_Novac == '9')
			{
				l1_Novabuffer[l1_NovalastIndex]++;
				return nova_standard_NovaDouble_static_NovagenString((nova_standard_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_NovalastIndex);
			}
		}
	}
	else
	{
		int l9_Novarep;
		
		l9_Novarep = nova_standard_NovaDouble_static_Novarepetition((nova_standard_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_NovalastIndex);
		if (l9_Novarep > 5)
		{
			l1_Novabuffer[l1_NovalastIndex] = l1_Novac;
			if (l1_Novac >= '5')
			{
				l1_Novac++;
			}
			l1_Novabuffer[++l1_NovalastIndex] = l1_Novac;
			return nova_standard_NovaDouble_static_NovagenString((nova_standard_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_NovalastIndex);
		}
	}
	l1_NovalastIndex = nova_standard_NovaDouble_static_NovalastSignificantDigit((nova_standard_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_Novasize - 1);
	return nova_standard_NovaDouble_static_NovagenString((nova_standard_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_NovalastIndex);
}

nova_standard_NovaString* nova_standard_NovaDouble_Novanull3_toString(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaDouble_static_Novanull2_toString(this, exceptionData, this->nova_standard_NovaDouble_Novavalue);
}

void nova_standard_NovaDouble_Novasuper(nova_standard_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_NovaDouble_Novavalue = 0;
}
