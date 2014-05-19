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
	ExceptionData* fathom_parent;
	ArrayList* fathom_codes;
)

ExceptionData* fathom_ExceptionData_ExceptionData(ExceptionData* exceptionData, jmp_buf* buf)
{
	CCLASS_NEW(ExceptionData, this);
	
	this->prv->fathom_parent = 0;
	this->prv->fathom_codes = 0;
	{
		this->prv->buffer = buf;
		this->prv->fathom_codes = fathom_ArrayList_ArrayList(exceptionData);
	}
	
	return this;
}

void fathom_del_ExceptionData(ExceptionData** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	fathom_del_ExceptionData(&(*this)->prv->fathom_parent, exceptionData);
	fathom_del_ArrayList(&(*this)->prv->fathom_codes, exceptionData);
	free((*this)->prv);
	
	{
	}
	free(*this);
}

ArrayList* fathom_ExceptionData_getCodes(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->fathom_codes;
}

void fathom_ExceptionData_addCode(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_32)
{
	ArrayList* fathom_codes;
}

jmp_buf* fathom_ExceptionData_getBuffer(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->buffer;
}

ExceptionData* fathom_ExceptionData_getCorrectData(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_43)
{
	ExceptionData* fathom_data_43;
	
	fathom_data_43 = this;
	while (fathom_data_43 != 0)
	{
		ArrayList* fathom_list_193;
		int fathom_i_193;
		
		fathom_list_193 = fathom_ExceptionData_getCodes(fathom_data_43, exceptionData);
		fathom_i_193 = 0;
		
		for (; fathom_i_193 < fathom_ArrayList_getSize(fathom_list_193, exceptionData); fathom_i_193++)
		{
			if (fathom_ArrayList_get(fathom_list_193, exceptionData, fathom_i_193) == fathom_code_43)
			{
				return fathom_data_43;
			}
		}
		if (fathom_ExceptionData_getParent(fathom_data_43, exceptionData) == 0)
		{
			return fathom_data_43;
		}
		fathom_data_43 = fathom_ExceptionData_getParent(fathom_data_43, exceptionData);
	}
	return 0;
}

jmp_buf* fathom_ExceptionData_getCorrectBuffer(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_50)
{
	ExceptionData* fathom_data_50;
	
	fathom_data_50 = fathom_ExceptionData_getCorrectData(this, exceptionData, fathom_code_50);
	if (fathom_data_50 == 0)
	{
		return 0;
	}
	return fathom_ExceptionData_getBuffer(fathom_data_50, exceptionData);
}

void fathom_ExceptionData_jumpToBuffer(ExceptionData* this, ExceptionData* exceptionData, int fathom_code_56)
{
	ExceptionData* fathom_data_56;
	jmp_buf* buf;
	
	fathom_data_56 = fathom_ExceptionData_getCorrectData(this, exceptionData, fathom_code_56);
	if (fathom_ExceptionData_getParent(fathom_data_56, exceptionData) == 0)
	{
		fathom_code_56 = 1;
	}
	buf = fathom_ExceptionData_getBuffer(fathom_data_56, exceptionData);
	longjmp(*buf, fathom_code_56);
}

ExceptionData* fathom_ExceptionData_getParent(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->fathom_parent;
}

void fathom_ExceptionData_setParent(ExceptionData* this, ExceptionData* exceptionData, ExceptionData* fathom_p_70)
{
	this->prv->fathom_parent = fathom_p_70;
}
