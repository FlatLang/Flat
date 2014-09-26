#include <precompiled.h>
#include <nova/standard/nova_standard_NovaLong.h>


nova_VTable_nova_standard_NovaLong nova_VTable_nova_standard_NovaLong_val =
{
	nova_standard_NovaLong_Novanull3_toString,
	nova_standard_NovaObject_Novanull0_equals,
	nova_standard_NovaNumber_static_Novanull0_numDigits,
	nova_standard_NovaNumber_static_Novanull1_toString,
};

nova_standard_NovaLong* nova_standard_NovaLong_Novaconstruct(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novavalue)
{
	CCLASS_NEW(nova_standard_NovaLong, this,);
	this->vtable = &nova_VTable_nova_standard_NovaLong_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaNumber_Novasuper((nova_standard_NovaNumber*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaNumber_Novathis((nova_standard_NovaNumber*)(this), exceptionData);
	nova_standard_NovaLong_Novasuper(this, 0);
	
	{
		nova_standard_NovaLong_Novathis(this, exceptionData, l0_Novavalue);
	}
	
	return this;
}

void nova_del_Long(nova_standard_NovaLong** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaLong_Novathis(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novavalue)
{
	this->nova_standard_NovaLong_Novavalue = l0_Novavalue;
}

int nova_standard_NovaLong_static_NovanumDigits(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novanumber)
{
	int l1_Novasize;
	
	if (l0_Novanumber < 0)
	{
		return nova_standard_NovaLong_static_NovanumDigits((nova_standard_NovaLong*)nova_null, exceptionData, -l0_Novanumber) + 1;
	}
	l0_Novanumber = l0_Novanumber / 10;
	l1_Novasize = 1;
	for (; l0_Novanumber > 0; l1_Novasize++)
	{
		l0_Novanumber = l0_Novanumber / 10;
	}
	return l1_Novasize;
}

nova_standard_NovaString* nova_standard_NovaLong_static_Novanull2_toString(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novavalue)
{
	int l1_NovacharOffset;
	int l1_Novadigits;
	char* l1_Novadata;
	int l1_Novaoffset;
	int l1_Novanums;
	int l3_Novaindex;
	
	l1_NovacharOffset = (int)('0');
	l1_Novadigits = nova_standard_NovaLong_static_NovanumDigits((nova_standard_NovaLong*)nova_null, exceptionData, l0_Novavalue);
	l1_Novadata = (char*)NOVA_MALLOC(sizeof(char) * (l1_Novadigits + 1));
	l1_Novadata[l1_Novadigits] = '\0';
	l1_Novaoffset = 0;
	if (l0_Novavalue < 0)
	{
		l1_Novadata[0] = '-';
		l0_Novavalue = -l0_Novavalue;
		l1_Novaoffset = 1;
	}
	l1_Novanums = l1_Novadigits-- - l1_Novaoffset;
	l3_Novaindex = 0;
	for (; l3_Novaindex < l1_Novanums; l3_Novaindex++)
	{
		l1_Novadata[l1_Novadigits - l3_Novaindex] = (char)(l1_NovacharOffset + l0_Novavalue % 10);
		l0_Novavalue = l0_Novavalue / 10;
	}
	return nova_standard_NovaString_Novaconstruct(0, exceptionData, l1_Novadata);
}

nova_standard_NovaString* nova_standard_NovaLong_Novanull3_toString(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaLong_static_Novanull2_toString(this, exceptionData, this->nova_standard_NovaLong_Novavalue);
}

void nova_standard_NovaLong_Novasuper(nova_standard_NovaLong* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_NovaLong_Novavalue = 0;
}
