#include <precompiled.h>
#include <nova/standard/math/nova_standard_math_Nova_Matrix.h>

nova_standard_math_Extension_VTable_Matrix nova_standard_math_Extension_VTable_Matrix_val =
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
	nova_standard_datastruct_list_Nova_Array* nova_standard_math_Nova_Matrix_Nova_matrix;
	
)
void nova_standard_math_Nova_MatrixNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_Nova_Matrix* nova_standard_math_Nova_Matrix_Nova_Matrix(nova_standard_math_Nova_Matrix* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_math_Nova_Matrix_Nova_rows, int nova_standard_math_Nova_Matrix_Nova_cols)
{
	CCLASS_NEW(nova_standard_math_Nova_Matrix, this);
	this->vtable = &nova_standard_math_Extension_VTable_Matrix_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_Nova_Matrix_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_Matrix_Nova_this(this, exceptionData, nova_standard_math_Nova_Matrix_Nova_rows, nova_standard_math_Nova_Matrix_Nova_cols);
	}
	
	return this;
}

void nova_standard_math_Nova_Matrix_Nova_destroy(nova_standard_math_Nova_Matrix** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_list_Nova_Array_Nova_destroy(&(*this)->prv->nova_standard_math_Nova_Matrix_Nova_matrix, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_math_Nova_Matrix_Nova_this(nova_standard_math_Nova_Matrix* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_math_Nova_Matrix_Nova_rows, int nova_standard_math_Nova_Matrix_Nova_cols)
{
	this->prv->nova_standard_math_Nova_Matrix_Nova_matrix = nova_standard_datastruct_list_Nova_Array_1_Nova_Array(0, exceptionData, nova_standard_math_Nova_Matrix_Nova_rows);
}

nova_standard_primitive_number_Nova_Number* nova_standard_math_Nova_Matrix_Nova_sum(nova_standard_math_Nova_Matrix* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_primitive_number_Nova_Number*)0;
}

void nova_standard_math_Nova_Matrix_Nova_super(nova_standard_math_Nova_Matrix* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_math_Nova_Matrix_Nova_matrix = (nova_standard_datastruct_list_Nova_Array*)nova_null;
}

