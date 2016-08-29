#include <precompiled.h>
#include <nova/math/nova_math_Nova_Matrix.h>



nova_math_Extension_VTable_Matrix nova_math_Extension_VTable_Matrix_val =
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


CCLASS_PRIVATE
(
	nova_datastruct_list_Nova_Array* nova_math_Nova_Matrix_Nova_matrix;
	
)
void nova_math_Nova_Matrix_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_Nova_Matrix* nova_math_Nova_Matrix_Nova_construct(nova_math_Nova_Matrix* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_math_Nova_Matrix_Nova_rows, int nova_math_Nova_Matrix_Nova_cols)
{
	CCLASS_NEW(nova_math_Nova_Matrix, this);
	this->vtable = &nova_math_Extension_VTable_Matrix_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_Nova_Matrix_Nova_super(this, exceptionData);
	
	{
		nova_math_Nova_Matrix_Nova_this(this, exceptionData, nova_math_Nova_Matrix_Nova_rows, nova_math_Nova_Matrix_Nova_cols);
	}
	
	return this;
}

void nova_math_Nova_Matrix_Nova_destroy(nova_math_Nova_Matrix** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_datastruct_list_Nova_Array_Nova_destroy(&(*this)->prv->nova_math_Nova_Matrix_Nova_matrix, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_math_Nova_Matrix_Nova_this(nova_math_Nova_Matrix* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_math_Nova_Matrix_Nova_rows, int nova_math_Nova_Matrix_Nova_cols)
{
	this->prv->nova_math_Nova_Matrix_Nova_matrix = nova_datastruct_list_Nova_Array_1_Nova_construct(0, exceptionData, nova_math_Nova_Matrix_Nova_rows);
}

nova_primitive_number_Nova_Number* nova_math_Nova_Matrix_Nova_sum(nova_math_Nova_Matrix* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_primitive_number_Nova_Number*)0;
}

void nova_math_Nova_Matrix_Nova_super(nova_math_Nova_Matrix* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_math_Nova_Matrix_Nova_matrix = (nova_datastruct_list_Nova_Array*)nova_null;
}

