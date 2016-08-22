#include <precompiled.h>
#include <nova/standard/exception/nova_standard_exception_Nova_ExceptionData.h>

typedef struct
{
	/* Int code */ int* nova_standard_exception_Nova_ExceptionData_Nova_code;
} Context1;


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

char nova_standard_exception_Nova_ExceptionData_Nova_testLambda27(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_exception_Nova_ExceptionData_Nova__1, Context1* context);
void nova_standard_exception_Nova_ExceptionData_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
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
	nova_standard_datastruct_list_Nova_IntArray_Nova_destroy(&(*this)->nova_standard_exception_Nova_ExceptionData_Nova_codes, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_exception_Nova_ExceptionData_Nova_this(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, buffer* nova_standard_exception_Nova_ExceptionData_Nova_buf)
{
	this->prv->nova_standard_exception_Nova_ExceptionData_Nova_buf = nova_standard_exception_Nova_ExceptionData_Nova_buf;
	this->nova_standard_exception_Nova_ExceptionData_Nova_codes = nova_standard_datastruct_list_Nova_IntArray_0_Nova_IntArray(0, exceptionData);
}

void nova_standard_exception_Nova_ExceptionData_Nova_addCode(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_exception_Nova_ExceptionData_Nova_code)
{
	nova_standard_datastruct_list_Nova_Array_0_Nova_add((nova_standard_datastruct_list_Nova_Array*)(this->nova_standard_exception_Nova_ExceptionData_Nova_codes), exceptionData, (nova_standard_Nova_Object*)(nova_standard_exception_Nova_ExceptionData_Nova_code));
}

buffer* nova_standard_exception_Nova_ExceptionData_Nova_getBuffer(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_exception_Nova_ExceptionData_Nova_buf;
}

nova_standard_exception_Nova_ExceptionData* nova_standard_exception_Nova_ExceptionData_Nova_getCorrectData(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_exception_Nova_ExceptionData_Nova_code)
{
	nova_standard_exception_Nova_ExceptionData* l1_Nova_data = (nova_standard_exception_Nova_ExceptionData*)nova_null;
	
	l1_Nova_data = this;
	while (l1_Nova_data != (nova_standard_exception_Nova_ExceptionData*)nova_null)
	{
		Context1 contextArg30 = 
		{
			&nova_standard_exception_Nova_ExceptionData_Nova_code,
		};
		
		if (nova_standard_datastruct_list_Nova_List_virtual0_Nova_any((nova_standard_datastruct_list_Nova_List*)(l1_Nova_data->nova_standard_exception_Nova_ExceptionData_Nova_codes), exceptionData, (nova_standard_datastruct_list_Nova_List_closure9_Nova_anyFunc)&nova_standard_exception_Nova_ExceptionData_Nova_testLambda27, this, &contextArg30))
		{
			return l1_Nova_data;
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
	nova_standard_exception_Nova_ExceptionData* l1_Nova_data = (nova_standard_exception_Nova_ExceptionData*)nova_null;
	
	l1_Nova_data = nova_standard_exception_Nova_ExceptionData_Nova_getCorrectData(this, exceptionData, nova_standard_exception_Nova_ExceptionData_Nova_code);
	if (l1_Nova_data == (nova_standard_exception_Nova_ExceptionData*)nova_null)
	{
		return (buffer*)0;
	}
	return nova_standard_exception_Nova_ExceptionData_Nova_getBuffer(l1_Nova_data, exceptionData);
}

void nova_standard_exception_Nova_ExceptionData_Nova_jumpToBuffer(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_exception_Nova_ExceptionData_Nova_code)
{
	nova_standard_exception_Nova_ExceptionData* l1_Nova_data = (nova_standard_exception_Nova_ExceptionData*)nova_null;
	buffer* l1_Nova_b = (buffer*)nova_null;
	
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

char nova_standard_exception_Nova_ExceptionData_Nova_testLambda27(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_exception_Nova_ExceptionData_Nova__1, Context1* context)
{
	return nova_standard_exception_Nova_ExceptionData_Nova__1 == (*context->nova_standard_exception_Nova_ExceptionData_Nova_code);
}

void nova_standard_exception_Nova_ExceptionData_Nova_super(nova_standard_exception_Nova_ExceptionData* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_exception_Nova_ExceptionData_Nova_thrownException = (nova_standard_exception_Nova_Exception*)nova_null;
	this->nova_standard_exception_Nova_ExceptionData_Nova_codes = (nova_standard_datastruct_list_Nova_IntArray*)nova_null;
	this->prv->nova_standard_exception_Nova_ExceptionData_Nova_buf = 0;
	this->prv->nova_standard_exception_Nova_ExceptionData_Nova_parent = (nova_standard_exception_Nova_ExceptionData*)nova_null;
}

