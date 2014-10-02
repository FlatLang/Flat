#include <precompiled.h>
#include <nova/standard/time/nova_standard_time_NovaDate.h>


nova_VTable_nova_standard_time_NovaDate nova_VTable_nova_standard_time_NovaDate_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_time_NovaDateNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_time_NovaDate* nova_standard_time_NovaDate_Nova0_construct(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_time_NovaDate, this,);
	this->vtable = &nova_VTable_nova_standard_time_NovaDate_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_time_NovaDate_Novasuper(this, 0);
	
	{
		nova_standard_time_NovaDate_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Date(nova_standard_time_NovaDate** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_time_NovaDate_Novathis(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_time_NovaDate_NovaupdateTime(this, exceptionData);
}

void nova_standard_time_NovaDate_NovadecodeDate(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaprototype, nova_standard_NovaString* l0_Novadate)
{
}

void nova_standard_time_NovaDate_NovaupdateTime(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_updateTime();
	this->nova_standard_time_NovaDate_Novayear = nova_getYear();
	this->nova_standard_time_NovaDate_Novamonth = nova_getMonth();
	this->nova_standard_time_NovaDate_Novaday = nova_getDay();
	this->nova_standard_time_NovaDate_Novahour = nova_getHour();
	this->nova_standard_time_NovaDate_Novaminute = nova_getMinute();
	this->nova_standard_time_NovaDate_Novasecond = nova_getSecond();
}

nova_standard_NovaString* nova_standard_time_NovaDate_Nova0_formatDate(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastr)
{
	nova_standard_time_NovaDate_Nova1_formatDate(this, exceptionData, l0_Novastr, this->nova_standard_time_NovaDate_Novamonth, this->nova_standard_time_NovaDate_Novaday, this->nova_standard_time_NovaDate_Novayear, this->nova_standard_time_NovaDate_Novahour, this->nova_standard_time_NovaDate_Novaminute, this->nova_standard_time_NovaDate_Novasecond);
}

nova_standard_NovaString* nova_standard_time_NovaDate_Nova1_formatDate(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastr, int l0_Novafirst, int l0_Novasecond, int l0_Novathird, int l0_Novafourth, int l0_Novafifth, int l0_Novasixth)
{
	char* l1_Novadata;
	
	l1_Novadata = nova_formatDate(nova_standard_NovaString_NovatoCharArray(l0_Novastr, exceptionData), l0_Novafirst, l0_Novasecond, l0_Novathird, l0_Novafourth, l0_Novafifth, l0_Novasixth);
	return nova_standard_NovaString_Novaconstruct(0, exceptionData, l1_Novadata);
}

void nova_standard_time_NovaDate_Novasuper(nova_standard_time_NovaDate* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_time_NovaDate_Novayear = 0;
	this->nova_standard_time_NovaDate_Novamonth = 0;
	this->nova_standard_time_NovaDate_Novaday = 0;
	this->nova_standard_time_NovaDate_Novahour = 0;
	this->nova_standard_time_NovaDate_Novaminute = 0;
	this->nova_standard_time_NovaDate_Novasecond = 0;
}
