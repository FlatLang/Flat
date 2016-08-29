#include <precompiled.h>
#include <nova/time/nova_time_Nova_Date.h>



nova_time_Extension_VTable_Date nova_time_Extension_VTable_Date_val =
{
	{
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
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void nova_time_Nova_Date_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_time_Nova_Date* nova_time_Nova_Date_Nova_construct(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_time_Nova_Date, this,);
	this->vtable = &nova_time_Extension_VTable_Date_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_time_Nova_Date_Nova_super(this, exceptionData);
	
	{
		nova_time_Nova_Date_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_time_Nova_Date_Nova_destroy(nova_time_Nova_Date** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	
	
	
	
	NOVA_FREE(*this);
}

void nova_time_Nova_Date_0_Nova_this(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_time_Nova_Date_Nova_updateTime(this, exceptionData);
}

void nova_time_Nova_Date_Nova_decodeDate(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_time_Nova_Date_Nova_prototype, nova_Nova_String* nova_time_Nova_Date_Nova_date)
{
}

void nova_time_Nova_Date_Nova_updateTime(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_updateTime();
	this->nova_time_Nova_Date_Nova_year = (int)(nova_getYear());
	this->nova_time_Nova_Date_Nova_month = (int)(nova_getMonth());
	this->nova_time_Nova_Date_Nova_day = (int)(nova_getDay());
	this->nova_time_Nova_Date_Nova_hour = (int)(nova_getHour());
	this->nova_time_Nova_Date_Nova_minute = (int)(nova_getMinute());
	this->nova_time_Nova_Date_Nova_second = (int)(nova_getSecond());
}

nova_Nova_String* nova_time_Nova_Date_0_Nova_formatDate(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_time_Nova_Date_Nova_str)
{
	return nova_time_Nova_Date_1_Nova_formatDate(this, exceptionData, nova_time_Nova_Date_Nova_str, this->nova_time_Nova_Date_Nova_month, this->nova_time_Nova_Date_Nova_day, this->nova_time_Nova_Date_Nova_year, this->nova_time_Nova_Date_Nova_hour, this->nova_time_Nova_Date_Nova_minute, this->nova_time_Nova_Date_Nova_second);
}

nova_Nova_String* nova_time_Nova_Date_1_Nova_formatDate(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_time_Nova_Date_Nova_str, int nova_time_Nova_Date_Nova_first, int nova_time_Nova_Date_Nova_second, int nova_time_Nova_Date_Nova_third, int nova_time_Nova_Date_Nova_fourth, int nova_time_Nova_Date_Nova_fifth, int nova_time_Nova_Date_Nova_sixth)
{
	char* l1_Nova_data = (char*)nova_null;
	
	
	l1_Nova_data = (char*)(nova_formatDate((char*)(nova_time_Nova_Date_Nova_str->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), nova_time_Nova_Date_Nova_first, nova_time_Nova_Date_Nova_second, nova_time_Nova_Date_Nova_third, nova_time_Nova_Date_Nova_fourth, nova_time_Nova_Date_Nova_fifth, nova_time_Nova_Date_Nova_sixth));
	return nova_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_data);
}

void nova_time_Nova_Date_Nova_super(nova_time_Nova_Date* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_time_Nova_Date_Nova_year = 0;
	this->nova_time_Nova_Date_Nova_month = 0;
	this->nova_time_Nova_Date_Nova_day = 0;
	this->nova_time_Nova_Date_Nova_hour = 0;
	this->nova_time_Nova_Date_Nova_minute = 0;
	this->nova_time_Nova_Date_Nova_second = 0;
}

