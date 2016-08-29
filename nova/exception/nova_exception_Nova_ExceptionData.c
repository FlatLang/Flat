#include <precompiled.h>
#include <nova/exception/nova_exception_Nova_ExceptionData.h>

typedef struct
{
	/* Int code */ int* nova_exception_Nova_ExceptionData_Nova_code;
} Context1;


nova_exception_Extension_VTable_ExceptionData nova_exception_Extension_VTable_ExceptionData_val =
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



nova_exception_Nova_ExceptionData* nova_exception_Nova_ExceptionData_Nova_getDataByCode(nova_exception_Nova_ExceptionData* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_exception_Nova_ExceptionData_Nova_code);
char nova_exception_Nova_ExceptionData_Nova_testLambda54(nova_exception_Nova_ExceptionData* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_exception_Nova_ExceptionData_Nova__1, Context1* context);
void nova_exception_Nova_ExceptionData_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_exception_Nova_ExceptionData* nova_exception_Nova_ExceptionData_Nova_construct(nova_exception_Nova_ExceptionData* this, nova_exception_Nova_ExceptionData* exceptionData, buffer* nova_exception_Nova_ExceptionData_Nova_buf)
{
	CCLASS_NEW(nova_exception_Nova_ExceptionData, this,);
	this->vtable = &nova_exception_Extension_VTable_ExceptionData_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_ExceptionData_Nova_super(this, exceptionData);
	
	{
		nova_exception_Nova_ExceptionData_Nova_this(this, exceptionData, nova_exception_Nova_ExceptionData_Nova_buf);
	}
	
	return this;
}

void nova_exception_Nova_ExceptionData_Nova_destroy(nova_exception_Nova_ExceptionData** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_exception_Nova_ExceptionData_Nova_destroy(&(*this)->nova_exception_Nova_ExceptionData_Nova_parent, exceptionData);
	nova_datastruct_list_Nova_IntArray_Nova_destroy(&(*this)->nova_exception_Nova_ExceptionData_Nova_codes, exceptionData);
	nova_exception_Nova_Exception_Nova_destroy(&(*this)->nova_exception_Nova_ExceptionData_Nova_thrownException, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_exception_Nova_ExceptionData_Nova_this(nova_exception_Nova_ExceptionData* this, nova_exception_Nova_ExceptionData* exceptionData, buffer* nova_exception_Nova_ExceptionData_Nova_buf)
{
	this->nova_exception_Nova_ExceptionData_Nova_buf = nova_exception_Nova_ExceptionData_Nova_buf;
	this->nova_exception_Nova_ExceptionData_Nova_codes = nova_datastruct_list_Nova_IntArray_0_Nova_construct(0, exceptionData);
}

void nova_exception_Nova_ExceptionData_Nova_addCode(nova_exception_Nova_ExceptionData* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_exception_Nova_ExceptionData_Nova_code)
{
	nova_datastruct_list_Nova_Array_0_Nova_add((nova_datastruct_list_Nova_Array*)(this->nova_exception_Nova_ExceptionData_Nova_codes), exceptionData, (nova_Nova_Object*)(nova_exception_Nova_ExceptionData_Nova_code));
}

nova_exception_Nova_ExceptionData* nova_exception_Nova_ExceptionData_Nova_getDataByCode(nova_exception_Nova_ExceptionData* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_exception_Nova_ExceptionData_Nova_code)
{
	nova_exception_Nova_ExceptionData* l1_Nova_data = (nova_exception_Nova_ExceptionData*)nova_null;
	
	l1_Nova_data = this;
	for (;;)
	{
		Context1 contextArg57 = 
		{
			&nova_exception_Nova_ExceptionData_Nova_code,
		};
		
		if (nova_datastruct_list_Nova_List_virtual0_Nova_any((nova_datastruct_list_Nova_List*)(l1_Nova_data->nova_exception_Nova_ExceptionData_Nova_codes), exceptionData, (nova_datastruct_list_Nova_List_closure9_Nova_anyFunc)&nova_exception_Nova_ExceptionData_Nova_testLambda54, this, &contextArg57) || l1_Nova_data->nova_exception_Nova_ExceptionData_Nova_parent == (nova_exception_Nova_ExceptionData*)nova_null)
		{
			return l1_Nova_data;
		}
		l1_Nova_data = l1_Nova_data->nova_exception_Nova_ExceptionData_Nova_parent;
	}
	return (nova_exception_Nova_ExceptionData*)nova_null;
}

void nova_exception_Nova_ExceptionData_Nova_jumpToBuffer(nova_exception_Nova_ExceptionData* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_exception_Nova_ExceptionData_Nova_code)
{
	nova_exception_Nova_ExceptionData* l1_Nova_data = (nova_exception_Nova_ExceptionData*)nova_null;
	
	l1_Nova_data = nova_exception_Nova_ExceptionData_Nova_getDataByCode(this, exceptionData, nova_exception_Nova_ExceptionData_Nova_code);
	if (l1_Nova_data == NULL)
	{
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Could not get exception buffer!")));
		return;
	}
	if (l1_Nova_data->nova_exception_Nova_ExceptionData_Nova_parent == (nova_exception_Nova_ExceptionData*)nova_null)
	{
		nova_exception_Nova_ExceptionData_Nova_code = (int)(1);
	}
	jump(*l1_Nova_data->nova_exception_Nova_ExceptionData_Nova_buf, nova_exception_Nova_ExceptionData_Nova_code);
}

char nova_exception_Nova_ExceptionData_Nova_testLambda54(nova_exception_Nova_ExceptionData* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_exception_Nova_ExceptionData_Nova__1, Context1* context)
{
	return nova_exception_Nova_ExceptionData_Nova__1 == (*context->nova_exception_Nova_ExceptionData_Nova_code);
}

void nova_exception_Nova_ExceptionData_Nova_super(nova_exception_Nova_ExceptionData* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_exception_Nova_ExceptionData_Nova_buf = 0;
	this->nova_exception_Nova_ExceptionData_Nova_parent = (nova_exception_Nova_ExceptionData*)nova_null;
	this->nova_exception_Nova_ExceptionData_Nova_codes = (nova_datastruct_list_Nova_IntArray*)nova_null;
	this->nova_exception_Nova_ExceptionData_Nova_thrownException = (nova_exception_Nova_Exception*)nova_null;
}

