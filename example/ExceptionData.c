#include "ExceptionData.h"
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"
#include <setjmp.h>
#include "ArrayList.h"

CCLASS_PRIVATE
(
	jmp_buf* buffer;
	ExceptionData* nova_ExceptionData_parent;
	ArrayList* nova_ExceptionData_codes;
	
)

ExceptionData* nova_ExceptionData_ExceptionData(ExceptionData* exceptionData, jmp_buf* buf)
{
	CCLASS_NEW(ExceptionData, this);
	
	this->prv->nova_ExceptionData_parent = 0;
	this->prv->nova_ExceptionData_codes = 0;
	{
		this->prv->buffer = buf;
		this->prv->nova_ExceptionData_codes = nova_ArrayList_ArrayList(exceptionData);
	}
	
	return this;
}

void nova_del_ExceptionData(ExceptionData** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_ExceptionData(&(*this)->prv->nova_ExceptionData_parent, exceptionData);
	nova_del_ArrayList(&(*this)->prv->nova_ExceptionData_codes, exceptionData);
	free((*this)->prv);
	
	{
	}
	free(*this);
}

ArrayList* nova_ExceptionData_getCodes(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->nova_ExceptionData_codes;
}

void nova_ExceptionData_addCode(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code)
{
	ArrayList* nova_ExceptionData_codes;
}

jmp_buf* nova_ExceptionData_getBuffer(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->buffer;
}

ExceptionData* nova_ExceptionData_getCorrectData(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code)
{
	ExceptionData* nova_160_data;
	
	nova_160_data = this;
	while (nova_160_data != 0)
	{
		ArrayList* nova_259_list;
		int nova_260_i;
		
		nova_259_list = nova_ExceptionData_getCodes(nova_160_data, exceptionData);
		nova_260_i = 0;
		
		for (; nova_260_i < nova_ArrayList_getSize(nova_259_list, exceptionData); nova_260_i++)
		{
			if (nova_ArrayList_get(nova_259_list, exceptionData, nova_260_i) == nova_0_code)
			{
				return nova_160_data;
			}
		}
		if (nova_ExceptionData_getParent(nova_160_data, exceptionData) == 0)
		{
			return nova_160_data;
		}
		nova_160_data = nova_ExceptionData_getParent(nova_160_data, exceptionData);
	}
	return 0;
}

jmp_buf* nova_ExceptionData_getCorrectBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code)
{
	ExceptionData* nova_165_data;
	
	nova_165_data = nova_ExceptionData_getCorrectData(this, exceptionData, nova_0_code);
	if (nova_165_data == 0)
	{
		return 0;
	}
	return nova_ExceptionData_getBuffer(nova_165_data, exceptionData);
}

void nova_ExceptionData_jumpToBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code)
{
	ExceptionData* nova_168_data;
	jmp_buf* buf;
	
	nova_168_data = nova_ExceptionData_getCorrectData(this, exceptionData, nova_0_code);
	if (nova_ExceptionData_getParent(nova_168_data, exceptionData) == 0)
	{
		nova_0_code = 1;
	}
	buf = nova_ExceptionData_getBuffer(nova_168_data, exceptionData);
	longjmp(*buf, nova_0_code);
}

ExceptionData* nova_ExceptionData_getParent(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->nova_ExceptionData_parent;
}

void nova_ExceptionData_setParent(ExceptionData* this, ExceptionData* exceptionData, ExceptionData* nova_0_p)
{
	this->prv->nova_ExceptionData_parent = nova_0_p;
}
