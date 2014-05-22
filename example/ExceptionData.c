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

void nova_ExceptionData_addCode(ExceptionData* this, ExceptionData* exceptionData, int nova_ExceptionData_code_72)
{
	ArrayList* nova_ExceptionData_codes;
}

jmp_buf* nova_ExceptionData_getBuffer(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->buffer;
}

ExceptionData* nova_ExceptionData_getCorrectData(ExceptionData* this, ExceptionData* exceptionData, int nova_ExceptionData_code_85)
{
	ExceptionData* nova_ExceptionData_data_85;
	
	nova_ExceptionData_data_85 = this;
	while (nova_ExceptionData_data_85 != 0)
	{
		ArrayList* nova_ExceptionData_list_189;
		int nova_ExceptionData_i_189;
		
		nova_ExceptionData_list_189 = nova_ExceptionData_getCodes(nova_ExceptionData_data_85, exceptionData);
		nova_ExceptionData_i_189 = 0;
		
		for (; nova_ExceptionData_i_189 < nova_ArrayList_getSize(nova_ExceptionData_list_189, exceptionData); nova_ExceptionData_i_189++)
		{
			if (nova_ArrayList_get(nova_ExceptionData_list_189, exceptionData, nova_ExceptionData_i_189) == nova_ExceptionData_code_85)
			{
				return nova_ExceptionData_data_85;
			}
		}
		if (nova_ExceptionData_getParent(nova_ExceptionData_data_85, exceptionData) == 0)
		{
			return nova_ExceptionData_data_85;
		}
		nova_ExceptionData_data_85 = nova_ExceptionData_getParent(nova_ExceptionData_data_85, exceptionData);
	}
	return 0;
}

jmp_buf* nova_ExceptionData_getCorrectBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_ExceptionData_code_92)
{
	ExceptionData* nova_ExceptionData_data_92;
	
	nova_ExceptionData_data_92 = nova_ExceptionData_getCorrectData(this, exceptionData, nova_ExceptionData_code_92);
	if (nova_ExceptionData_data_92 == 0)
	{
		return 0;
	}
	return nova_ExceptionData_getBuffer(nova_ExceptionData_data_92, exceptionData);
}

void nova_ExceptionData_jumpToBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_ExceptionData_code_108)
{
	ExceptionData* nova_ExceptionData_data_108;
	jmp_buf* buf;
	
	nova_ExceptionData_data_108 = nova_ExceptionData_getCorrectData(this, exceptionData, nova_ExceptionData_code_108);
	if (nova_ExceptionData_getParent(nova_ExceptionData_data_108, exceptionData) == 0)
	{
		nova_ExceptionData_code_108 = 1;
	}
	buf = nova_ExceptionData_getBuffer(nova_ExceptionData_data_108, exceptionData);
	longjmp(*buf, nova_ExceptionData_code_108);
}

ExceptionData* nova_ExceptionData_getParent(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->nova_ExceptionData_parent;
}

void nova_ExceptionData_setParent(ExceptionData* this, ExceptionData* exceptionData, ExceptionData* nova_ExceptionData_p_165)
{
	this->prv->nova_ExceptionData_parent = nova_ExceptionData_p_165;
}
