#include <precompiled.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaDouble.h>


nova_VTable_nova_standard_primitive_number_NovaDouble nova_VTable_nova_standard_primitive_number_NovaDouble_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_primitive_number_NovaDouble_Nova2_toString,
	nova_standard_NovaObject_Nova0_equals,
	nova_standard_primitive_number_NovaDouble_static_Nova0_numDigits,
};
void nova_standard_primitive_number_NovaDoubleNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_number_NovaDouble* nova_standard_primitive_number_NovaDouble_Novaconstruct(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue)
{
	CCLASS_NEW(nova_standard_primitive_number_NovaDouble, this,);
	this->vtable = &nova_VTable_nova_standard_primitive_number_NovaDouble_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_primitive_NovaPrimitive_Novasuper((nova_standard_primitive_NovaPrimitive*)this, 0);
	nova_standard_primitive_number_NovaNumber_Novasuper((nova_standard_primitive_number_NovaNumber*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_primitive_NovaPrimitive_Novathis((nova_standard_primitive_NovaPrimitive*)(this), exceptionData);
	nova_standard_primitive_number_NovaNumber_Novathis((nova_standard_primitive_number_NovaNumber*)(this), exceptionData);
	nova_standard_primitive_number_NovaDouble_Novasuper(this, 0);
	
	{
		nova_standard_primitive_number_NovaDouble_Novathis(this, exceptionData, l0_Novavalue);
	}
	
	return this;
}

void nova_del_Double(nova_standard_primitive_number_NovaDouble** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_primitive_number_NovaDouble_Novathis(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue)
{
	this->nova_standard_primitive_number_NovaDouble_Novavalue = l0_Novavalue;
}

int nova_standard_primitive_number_NovaDouble_static_Nova0_numDigits(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanumber)
{
	int l1_Novasize;
	
	if (l0_Novanumber < 0)
	{
		return nova_standard_primitive_number_NovaDouble_static_Nova0_numDigits((nova_standard_primitive_number_NovaDouble*)nova_null, exceptionData, -l0_Novanumber) + 1;
	}
	l0_Novanumber = l0_Novanumber / 10;
	l1_Novasize = 1;
	for (; l0_Novanumber > 0; l1_Novasize++)
	{
		l0_Novanumber = l0_Novanumber / 10;
	}
	return l1_Novasize;
}

nova_standard_NovaString* nova_standard_primitive_number_NovaDouble_static_NovagenString(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novabuffer, int l0_NovalastIndex)
{
	l0_Novabuffer = (char*)(realloc((char*)(l0_Novabuffer), (int)(++l0_NovalastIndex + 1)));
	l0_Novabuffer[l0_NovalastIndex] = '\0';
	return nova_standard_NovaString_Nova1_construct(0, exceptionData, l0_Novabuffer);
}

char* nova_standard_primitive_number_NovaDouble_static_NovagenBuffer(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue)
{
	int l1_Novasize;
	char* l1_Novabuffer;
	
	l1_Novasize = 11 + 1 + 15;
	l1_Novabuffer = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_NovaChar*[l1_Novasize]));
	sprintf((char*)(l1_Novabuffer), (char*)("%.15f"), (double)(l0_Novavalue));
	return l1_Novabuffer;
}

int nova_standard_primitive_number_NovaDouble_static_Novarepetition(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novabuffer, int l0_Novastart)
{
	int l1_Novaindex;
	char l1_Novac;
	
	l1_Novaindex = l0_Novastart;
	l1_Novac = l0_Novabuffer[l1_Novaindex];
	while (l0_Novabuffer[--l1_Novaindex] == l1_Novac);
	return l0_Novastart - l1_Novaindex - 1;
}

int nova_standard_primitive_number_NovaDouble_static_NovalastSignificantDigit(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, char* l0_Novabuffer, int l0_Novastart)
{
	while (l0_Novabuffer[l0_Novastart--] == '0');
	return l0_Novastart + 1;
}

nova_standard_NovaString* nova_standard_primitive_number_NovaDouble_static_Nova1_toString(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novavalue)
{
	char* l1_Novabuffer;
	int l1_Novasize;
	int l1_NovalastIndex;
	char l1_Novac;
	
	l1_Novabuffer = nova_standard_primitive_number_NovaDouble_static_NovagenBuffer((nova_standard_primitive_number_NovaDouble*)nova_null, exceptionData, l0_Novavalue);
	l1_Novasize = (int)(strlen((char*)(l1_Novabuffer)));
	l1_NovalastIndex = l1_Novasize - 1;
	l1_Novac = l1_Novabuffer[--l1_NovalastIndex];
	if (l1_Novac == '0' || l1_Novac == '9')
	{
		while (l1_Novabuffer[l1_NovalastIndex--] == l1_Novac);
		if (l1_Novabuffer[++l1_NovalastIndex] == '.')
		{
			l1_NovalastIndex++;
			return nova_standard_primitive_number_NovaDouble_static_NovagenString((nova_standard_primitive_number_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_NovalastIndex);
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
				return nova_standard_primitive_number_NovaDouble_static_NovagenString((nova_standard_primitive_number_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_NovalastIndex);
			}
		}
	}
	else
	{
		int l9_Novarep;
		
		l9_Novarep = nova_standard_primitive_number_NovaDouble_static_Novarepetition((nova_standard_primitive_number_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_NovalastIndex);
		if (l9_Novarep > 5)
		{
			l1_Novabuffer[l1_NovalastIndex] = l1_Novac;
			if (l1_Novac >= '5')
			{
				l1_Novac++;
			}
			l1_Novabuffer[++l1_NovalastIndex] = l1_Novac;
			return nova_standard_primitive_number_NovaDouble_static_NovagenString((nova_standard_primitive_number_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_NovalastIndex);
		}
	}
	l1_NovalastIndex = nova_standard_primitive_number_NovaDouble_static_NovalastSignificantDigit((nova_standard_primitive_number_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_Novasize - 1);
	return nova_standard_primitive_number_NovaDouble_static_NovagenString((nova_standard_primitive_number_NovaDouble*)nova_null, exceptionData, l1_Novabuffer, l1_NovalastIndex);
}

nova_standard_NovaString* nova_standard_primitive_number_NovaDouble_Nova2_toString(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_primitive_number_NovaDouble_static_Nova1_toString(this, exceptionData, this->nova_standard_primitive_number_NovaDouble_Novavalue);
}

double nova_standard_primitive_number_NovaDouble_static_NovaparseDouble(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastr)
{
	char* l1_NovapEnd;
	
	return strtod((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novastr, exceptionData)), (char**)&(l1_NovapEnd));
}

void nova_standard_primitive_number_NovaDouble_Novasuper(nova_standard_primitive_number_NovaDouble* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_primitive_number_NovaDouble_Novavalue = 0;
}
