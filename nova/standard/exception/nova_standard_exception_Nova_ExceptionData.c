#include <precompiled.h>
#include <nova/standard/exception/nova_standard_exception_Nova_ExceptionData.h>

nova_standard_exception_Extension_VTable_ExceptionData nova_standard_exception_Extension_VTable_ExceptionData_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	buffer* nova_standard_exception_Nova_ExceptionData_Nova_buf;
	nova_standard_exception_Nova_ExceptionData* nova_standard_exception_Nova_ExceptionData_Nova_parent;
	
)
void nova_standard_exception_Nova_ExceptionDataNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_exception_Nova_ExceptionData* nova_standard_exception_Nova_ExceptionData_Nova_ExceptionData(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, buffer* nova_standard_exception_Nova_ExceptionData_Nova_buf)
{
	CCLASS_NEW(nova_standard_exception_Nova_ExceptionData, this);
	this->vtable = &nova_standard_exception_Extension_VTable_ExceptionData_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_exception_Nova_ExceptionData_Nova_super(this, exceptionData);
	
	{
		nova_standard_exception_Nova_ExceptionData_Nova_this(this, exceptionData, nova_standard_exception_Nova_ExceptionData_Nova_buf);
	}
	
	return this;
}

void nova_standard_exception_Nova_ExceptionData_Nova_destroy(nova_standard_exception_Nova_ExceptionData** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_standard_exception_Nova_ExceptionData_Nova_destroy(&(*this)->prv->nova_standard_exception_Nova_ExceptionData_Nova_parent, exceptionData);
	NOVA_FREE((*this)->prv);
	nova_standard_exception_Nova_Exception_Nova_destroy(&(*this)->nova_standard_exception_Nova_ExceptionData_Nova_thrownException, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_destroy(&(*this)->nova_standard_exception_Nova_ExceptionData_Nova_codes, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_exception_Nova_ExceptionData_Nova_this(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, buffer* nova_standard_exception_Nova_ExceptionData_Nova_buf)
{
	this->prv->nova_standard_exception_Nova_ExceptionData_Nova_buf = nova_standard_exception_Nova_ExceptionData_Nova_buf;
	this->nova_standard_exception_Nova_ExceptionData_Nova_codes = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
}

void nova_standard_exception_Nova_ExceptionData_Nova_addCode(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_exception_Nova_ExceptionData_Nova_code)
{
	nova_standard_datastruct_list_Nova_Array_0_Nova_add(this->nova_standard_exception_Nova_ExceptionData_Nova_codes, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_Int(0, exceptionData, nova_standard_exception_Nova_ExceptionData_Nova_code)));
}

buffer* nova_standard_exception_Nova_ExceptionData_Nova_getBuffer(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_exception_Nova_ExceptionData_Nova_buf;
}

nova_standard_exception_Nova_ExceptionData* nova_standard_exception_Nova_ExceptionData_Nova_getCorrectData(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_exception_Nova_ExceptionData_Nova_code)
{
	nova_standard_exception_Nova_ExceptionData* l1_Nova_data;
	
	l1_Nova_data = this;
	while (l1_Nova_data != (nova_standard_exception_Nova_ExceptionData*)nova_null)
	{
		nova_standard_datastruct_list_Nova_Array* l1_Nova_list;
		int l3_Nova_i;
		
		l1_Nova_list = l1_Nova_data->nova_standard_exception_Nova_ExceptionData_Nova_codes;
		l3_Nova_i = (int)(0);
		for (; l3_Nova_i < l1_Nova_list->nova_standard_datastruct_list_Nova_Array_Nova_size; l3_Nova_i++)
		{
			if ((int)(((nova_standard_primitive_number_Nova_Int*)nova_standard_datastruct_list_Nova_Array_Nova_get(l1_Nova_list, exceptionData, l3_Nova_i))->nova_standard_primitive_number_Nova_Int_Nova_value) == nova_standard_exception_Nova_ExceptionData_Nova_code)
			{
				return l1_Nova_data;
			}
		}
		if (nova_standard_exception_Nova_ExceptionData_Nova_getParent(l1_Nova_data, exceptionData) == (nova_standard_exception_Nova_ExceptionData*)nova_null)
		{
			return l1_Nova_data;
		}
		l1_Nova_data = nova_standard_exception_Nova_ExceptionData_Nova_getParent(l1_Nova_data, exceptionData);
	}
	return (nova_standard_exception_Nova_ExceptionData*)0;
}

buffer* nova_standard_exception_Nova_ExceptionData_Nova_getCorrectBuffer(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_exception_Nova_ExceptionData_Nova_code)
{
	nova_standard_exception_Nova_ExceptionData* l1_Nova_data;
	
	l1_Nova_data = nova_standard_exception_Nova_ExceptionData_Nova_getCorrectData(this, exceptionData, nova_standard_exception_Nova_ExceptionData_Nova_code);
	if (l1_Nova_data == (nova_standard_exception_Nova_ExceptionData*)nova_null)
	{
		return (buffer*)0;
	}
	return nova_standard_exception_Nova_ExceptionData_Nova_getBuffer(l1_Nova_data, exceptionData);
}

void nova_standard_exception_Nova_ExceptionData_Nova_jumpToBuffer(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_exception_Nova_ExceptionData_Nova_code)
{
	nova_standard_exception_Nova_ExceptionData* l1_Nova_data;
	buffer* l1_Nova_b;
	
	l1_Nova_data = nova_standard_exception_Nova_ExceptionData_Nova_getCorrectData(this, exceptionData, nova_standard_exception_Nova_ExceptionData_Nova_code);
	if (nova_standard_exception_Nova_ExceptionData_Nova_getParent(l1_Nova_data, exceptionData) == (nova_standard_exception_Nova_ExceptionData*)nova_null)
	{
		nova_standard_exception_Nova_ExceptionData_Nova_code = (int)(1);
	}
	l1_Nova_b = nova_standard_exception_Nova_ExceptionData_Nova_getBuffer(l1_Nova_data, exceptionData);
	jump(*l1_Nova_b, nova_standard_exception_Nova_ExceptionData_Nova_code);
}

nova_standard_exception_Nova_ExceptionData* nova_standard_exception_Nova_ExceptionData_Nova_getParent(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_exception_Nova_ExceptionData_Nova_parent;
}

void nova_standard_exception_Nova_ExceptionData_Nova_setParent(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_exception_Nova_ExceptionData* nova_standard_exception_Nova_ExceptionData_Nova_p)
{
	this->prv->nova_standard_exception_Nova_ExceptionData_Nova_parent = nova_standard_exception_Nova_ExceptionData_Nova_p;
}

void nova_standard_exception_Nova_ExceptionData_Nova_super(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_exception_Nova_ExceptionData_Nova_thrownException = (nova_standard_exception_Nova_Exception*)nova_null;
	this->nova_standard_exception_Nova_ExceptionData_Nova_codes = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	this->prv->nova_standard_exception_Nova_ExceptionData_Nova_buf = 0;
	this->prv->nova_standard_exception_Nova_ExceptionData_Nova_parent = (nova_standard_exception_Nova_ExceptionData*)nova_null;
}

