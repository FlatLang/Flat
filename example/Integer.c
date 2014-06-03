#include "Integer.h"
#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_0_value)
{
	CCLASS_NEW(Integer, this,);
	
	this->nova_Integer_value = 0;
	{
		this->nova_Integer_value = nova_0_value;
	}
	
	return this;
}

void nova_del_Integer(Integer** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}

int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_0_number)
{
	int nova_175_size;
	
	if (nova_0_number < 0)
	{
		return nova_Integer_numDigits(exceptionData, -nova_0_number) + 1;
	}
	nova_0_number = nova_0_number / 10;
	nova_175_size = 1;
	
	for (; nova_0_number > 0; nova_175_size++)
	{
		nova_0_number = nova_0_number / 10;
	}
	return nova_175_size;
}

String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_0_value)
{
	int nova_180_digits;
	char* nova_180_data;
	
	nova_180_digits = nova_Integer_numDigits(exceptionData, nova_0_value);
	nova_180_data = (char*)malloc(sizeof(char) * (nova_180_digits + 1));
}

String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData)
{
	return nova_Integer_toAString(exceptionData, this->nova_Integer_value);
}
