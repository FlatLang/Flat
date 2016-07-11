#include <precompiled.h>
#include <nova/standard/time/nova_standard_time_Nova_Date.h>

nova_standard_time_Extension_VTable_Date nova_standard_time_Extension_VTable_Date_val =
{
	{
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_2_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_time_Nova_DateNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_time_Nova_Date* nova_standard_time_Nova_Date_Nova_Date(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_time_Nova_Date, this,);
	this->vtable = &nova_standard_time_Extension_VTable_Date_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_time_Nova_Date_Nova_super(this, exceptionData);
	
	{
		nova_standard_time_Nova_Date_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_time_Nova_Date_Nova_destroy(nova_standard_time_Nova_Date** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_time_Nova_Date_0_Nova_this(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_time_Nova_Date_Nova_updateTime(this, exceptionData);
}

void nova_standard_time_Nova_Date_Nova_decodeDate(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_time_Nova_Date_Nova_prototype, nova_standard_Nova_String* nova_standard_time_Nova_Date_Nova_date)
{
}

void nova_standard_time_Nova_Date_Nova_updateTime(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_updateTime();
	this->nova_standard_time_Nova_Date_Nova_year = (int)(nova_getYear());
	this->nova_standard_time_Nova_Date_Nova_month = (int)(nova_getMonth());
	this->nova_standard_time_Nova_Date_Nova_day = (int)(nova_getDay());
	this->nova_standard_time_Nova_Date_Nova_hour = (int)(nova_getHour());
	this->nova_standard_time_Nova_Date_Nova_minute = (int)(nova_getMinute());
	this->nova_standard_time_Nova_Date_Nova_second = (int)(nova_getSecond());
}

nova_standard_Nova_String* nova_standard_time_Nova_Date_0_Nova_formatDate(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_time_Nova_Date_Nova_str)
{
	return nova_standard_time_Nova_Date_1_Nova_formatDate(this, exceptionData, nova_standard_time_Nova_Date_Nova_str, this->nova_standard_time_Nova_Date_Nova_month, this->nova_standard_time_Nova_Date_Nova_day, this->nova_standard_time_Nova_Date_Nova_year, this->nova_standard_time_Nova_Date_Nova_hour, this->nova_standard_time_Nova_Date_Nova_minute, this->nova_standard_time_Nova_Date_Nova_second);
}

nova_standard_Nova_String* nova_standard_time_Nova_Date_1_Nova_formatDate(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_time_Nova_Date_Nova_str, int nova_standard_time_Nova_Date_Nova_first, int nova_standard_time_Nova_Date_Nova_second, int nova_standard_time_Nova_Date_Nova_third, int nova_standard_time_Nova_Date_Nova_fourth, int nova_standard_time_Nova_Date_Nova_fifth, int nova_standard_time_Nova_Date_Nova_sixth)
{
	char* l1_Nova_data;
	
	l1_Nova_data = (char*)(nova_formatDate((char*)(nova_standard_time_Nova_Date_Nova_str->nova_standard_Nova_String_Nova_chars), nova_standard_time_Nova_Date_Nova_first, nova_standard_time_Nova_Date_Nova_second, nova_standard_time_Nova_Date_Nova_third, nova_standard_time_Nova_Date_Nova_fourth, nova_standard_time_Nova_Date_Nova_fifth, nova_standard_time_Nova_Date_Nova_sixth));
	return nova_standard_Nova_String_1_Nova_String(0, exceptionData, l1_Nova_data);
}

void nova_standard_time_Nova_Date_Nova_super(nova_standard_time_Nova_Date* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_time_Nova_Date_Nova_year = 0;
	this->nova_standard_time_Nova_Date_Nova_month = 0;
	this->nova_standard_time_Nova_Date_Nova_day = 0;
	this->nova_standard_time_Nova_Date_Nova_hour = 0;
	this->nova_standard_time_Nova_Date_Nova_minute = 0;
	this->nova_standard_time_Nova_Date_Nova_second = 0;
}

