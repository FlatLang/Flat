#include "ExceptionData.h"
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include <setjmp.h>
#include "ArrayList.h"

CCLASS_PRIVATE
(
	jmp_buf* buffer;
	ExceptionData* nova_parent;
	ArrayList* nova_codes;
)

ExceptionData* nova_ExceptionData_ExceptionData(ExceptionData* exceptionData, jmp_buf* buf)
{
	CCLASS_NEW(ExceptionData, this);
	
	this->prv->nova_parent = 0;
	this->prv->nova_codes = 0;
	{
		this->prv->buffer = buf;
		this->prv->nova_codes = nova_ArrayList_ArrayList(exceptionData);
	}
	
	return this;
}

void nova_del_ExceptionData(ExceptionData** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_ExceptionData(&(*this)->prv->nova_parent, exceptionData);
	nova_del_ArrayList(&(*this)->prv->nova_codes, exceptionData);
	free((*this)->prv);
	
	{
	}
	free(*this);
}

ArrayList* nova_ExceptionData_getCodes(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->nova_codes;
}

void nova_ExceptionData_addCode(ExceptionData* this, ExceptionData* exceptionData, int nova_code_136)
{
	ArrayList* nova_codes;
}

jmp_buf* nova_ExceptionData_getBuffer(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->buffer;
}

ExceptionData* nova_ExceptionData_getCorrectData(ExceptionData* this, ExceptionData* exceptionData, int nova_code_142)
{
	ExceptionData* nova_data_142;
	
	nova_data_142 = this;
	while (nova_data_142 != 0)
	{
		ArrayList* nova_list_192;
		int nova_i_192;
		
		nova_list_192 = nova_ExceptionData_getCodes(nova_data_142, exceptionData);
		nova_i_192 = 0;
		
		for (; nova_i_192 < nova_ArrayList_getSize(nova_list_192, exceptionData); nova_i_192++)
		{
			if (nova_ArrayList_get(nova_list_192, exceptionData, nova_i_192) == nova_code_142)
			{
				return nova_data_142;
			}
		}
		if (nova_ExceptionData_getParent(nova_data_142, exceptionData) == 0)
		{
			return nova_data_142;
		}
		nova_data_142 = nova_ExceptionData_getParent(nova_data_142, exceptionData);
	}
	return 0;
}

jmp_buf* nova_ExceptionData_getCorrectBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_code_145)
{
	ExceptionData* nova_data_145;
	
	nova_data_145 = nova_ExceptionData_getCorrectData(this, exceptionData, nova_code_145);
	if (nova_data_145 == 0)
	{
		return 0;
	}
	return nova_ExceptionData_getBuffer(nova_data_145, exceptionData);
}

void nova_ExceptionData_jumpToBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_code_148)
{
	ExceptionData* nova_data_148;
	jmp_buf* buf;
	
	nova_data_148 = nova_ExceptionData_getCorrectData(this, exceptionData, nova_code_148);
	if (nova_ExceptionData_getParent(nova_data_148, exceptionData) == 0)
	{
		nova_code_148 = 1;
	}
	buf = nova_ExceptionData_getBuffer(nova_data_148, exceptionData);
	longjmp(*buf, nova_code_148);
}

ExceptionData* nova_ExceptionData_getParent(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->nova_parent;
}

void nova_ExceptionData_setParent(ExceptionData* this, ExceptionData* exceptionData, ExceptionData* nova_p_154)
{
	this->prv->nova_parent = nova_p_154;
}
