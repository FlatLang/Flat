#include <precompiled.h>
#include "NovaExceptionData.h"


nova_VTable_ExceptionData nova_VTable_ExceptionData_val =
{
	nova_4_Object_toString,
	nova_2_Object_equals,
};
CCLASS_PRIVATE
(
	buffer* nova_ExceptionData_buf;
	ExceptionData* nova_ExceptionData_parent;
	
)

ExceptionData* nova_ExceptionData_ExceptionData(ExceptionData* this, ExceptionData* exceptionData, buffer* nova_0_buf)
{
	CCLASS_NEW(ExceptionData, this);
	
	this->nova_ExceptionData_codes = (ArrayList*)0;
	this->prv->nova_ExceptionData_buf = (buffer*)0;
	this->prv->nova_ExceptionData_parent = (ExceptionData*)0;
	this->vtable = &nova_VTable_ExceptionData_val;
	{
		this->prv->nova_ExceptionData_buf = nova_0_buf;
		this->nova_ExceptionData_codes = nova_ArrayList_ArrayList(0, exceptionData);
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
	NOVA_FREE((*this)->prv);
	nova_del_ArrayList(&(*this)->nova_ExceptionData_codes, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_ExceptionData_addCode(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code)
{
	nova_ArrayList_add(this->nova_ExceptionData_codes, exceptionData, nova_0_code);
}

buffer* nova_ExceptionData_getBuffer(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->nova_ExceptionData_buf;
}

ExceptionData* nova_ExceptionData_getCorrectData(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code)
{
	ExceptionData* nova_1_data;
	
	nova_1_data = this;
	while (nova_1_data != (ExceptionData*)0)
	{
		ArrayList* nova_2_list;
		int nova_2_i;
		
		nova_2_list = nova_1_data->nova_ExceptionData_codes;
		nova_2_i = 0;
		for (; nova_2_i < nova_2_list->nova_ArrayList_size; nova_2_i++)
		{
			if (nova_ArrayList_get(nova_2_list, exceptionData, nova_2_i) == nova_0_code)
			{
				return nova_1_data;
			}
		}
		if (nova_ExceptionData_getParent(nova_1_data, exceptionData) == (ExceptionData*)0)
		{
			return nova_1_data;
		}
		nova_1_data = nova_ExceptionData_getParent(nova_1_data, exceptionData);
	}
	return 0;
}

buffer* nova_ExceptionData_getCorrectBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code)
{
	ExceptionData* nova_1_data;
	
	nova_1_data = nova_ExceptionData_getCorrectData(this, exceptionData, nova_0_code);
	if (nova_1_data == (ExceptionData*)0)
	{
		return 0;
	}
	return nova_ExceptionData_getBuffer(nova_1_data, exceptionData);
}

void nova_ExceptionData_jumpToBuffer(ExceptionData* this, ExceptionData* exceptionData, int nova_0_code)
{
	ExceptionData* nova_1_data;
	buffer* nova_1_b;
	
	nova_1_data = nova_ExceptionData_getCorrectData(this, exceptionData, nova_0_code);
	if (nova_ExceptionData_getParent(nova_1_data, exceptionData) == (ExceptionData*)0)
	{
		nova_0_code = 1;
	}
	nova_1_b = nova_ExceptionData_getBuffer(nova_1_data, exceptionData);
	jump(*nova_1_b, nova_0_code);
}

ExceptionData* nova_ExceptionData_getParent(ExceptionData* this, ExceptionData* exceptionData)
{
	return this->prv->nova_ExceptionData_parent;
}

void nova_ExceptionData_setParent(ExceptionData* this, ExceptionData* exceptionData, ExceptionData* nova_0_p)
{
	this->prv->nova_ExceptionData_parent = nova_0_p;
}
