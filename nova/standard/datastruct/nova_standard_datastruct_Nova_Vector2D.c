#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Vector2D.h>


nova_standard_datastruct_Extension_VTable_Vector2D nova_standard_datastruct_Extension_VTable_Vector2D_val =
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
	},
	nova_standard_Nova_Object_3_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};




void nova_standard_datastruct_Nova_Vector2DNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Vector2D* nova_standard_datastruct_Nova_Vector2D_Nova_construct(nova_standard_datastruct_Nova_Vector2D* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Number* l0_Nova_x, nova_standard_primitive_number_Nova_Number* l0_Nova_y)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Vector2D, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Vector2D_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Vector2D_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Vector2D_Nova_this(this, exceptionData, l0_Nova_x, l0_Nova_y);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_Vector2D_Nova_destroy(nova_standard_datastruct_Nova_Vector2D** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_Vector2D_Nova_this(nova_standard_datastruct_Nova_Vector2D* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Number* l0_Nova_x, nova_standard_primitive_number_Nova_Number* l0_Nova_y)
{
	this->nova_standard_datastruct_Nova_Vector2D_Nova_x = l0_Nova_x;
	this->nova_standard_datastruct_Nova_Vector2D_Nova_y = l0_Nova_y;
}

void nova_standard_datastruct_Nova_Vector2D_Nova_scale(nova_standard_datastruct_Nova_Vector2D* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Number* l0_Nova_scalar)
{
	this->nova_standard_datastruct_Nova_Vector2D_Nova_x = (nova_standard_primitive_number_Nova_Number*)(this->nova_standard_datastruct_Nova_Vector2D_Nova_x * l0_Nova_scalar);
	this->nova_standard_datastruct_Nova_Vector2D_Nova_y = (nova_standard_primitive_number_Nova_Number*)(this->nova_standard_datastruct_Nova_Vector2D_Nova_y * l0_Nova_scalar);
}

void nova_standard_datastruct_Nova_Vector2D_Nova_normalize(nova_standard_datastruct_Nova_Vector2D* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_primitive_number_Nova_Number* l1_Nova_mag;
	nova_standard_primitive_number_Nova_Number* nova_zero_check1;
	nova_standard_primitive_number_Nova_Number* nova_zero_check4;
	
	l1_Nova_mag = (nova_standard_primitive_number_Nova_Number*)(nova_standard_datastruct_Nova_Vector2D_Accessor_Nova_magnitude(this, exceptionData));
	nova_zero_check1 = l1_Nova_mag;
	if (nova_zero_check1 == 0)
	{
		THROW(6, nova_standard_exception_Nova_DivideByZeroException_0_Nova_construct(0, exceptionData));
	}
	this->nova_standard_datastruct_Nova_Vector2D_Nova_x = (nova_standard_primitive_number_Nova_Number*)(this->nova_standard_datastruct_Nova_Vector2D_Nova_x / nova_zero_check1);
	nova_zero_check4 = l1_Nova_mag;
	if (nova_zero_check4 == 0)
	{
		THROW(6, nova_standard_exception_Nova_DivideByZeroException_0_Nova_construct(0, exceptionData));
	}
	this->nova_standard_datastruct_Nova_Vector2D_Nova_y = (nova_standard_primitive_number_Nova_Number*)(this->nova_standard_datastruct_Nova_Vector2D_Nova_y / nova_zero_check4);
}

nova_standard_datastruct_Nova_Vector2D* nova_standard_datastruct_Nova_Vector2D_0_Nova_crossProduct(nova_standard_datastruct_Nova_Vector2D* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Vector2D* l0_Nova_v1, nova_standard_datastruct_Nova_Vector2D* l0_Nova_v2)
{
	return nova_standard_datastruct_Nova_Vector2D_1_Nova_crossProduct(l0_Nova_v1, exceptionData, l0_Nova_v2);
}

nova_standard_datastruct_Nova_Vector2D* nova_standard_datastruct_Nova_Vector2D_1_Nova_crossProduct(nova_standard_datastruct_Nova_Vector2D* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Vector2D* l0_Nova_vector)
{
	nova_standard_datastruct_Nova_Vector2D* l1_Nova_cross;
	
	l1_Nova_cross = nova_standard_datastruct_Nova_Vector2D_Nova_construct(0, exceptionData, (nova_standard_primitive_number_Nova_Number*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, 0)), (nova_standard_primitive_number_Nova_Number*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, 0)));
	l1_Nova_cross->nova_standard_datastruct_Nova_Vector2D_Nova_x = (nova_standard_primitive_number_Nova_Number*)(this->nova_standard_datastruct_Nova_Vector2D_Nova_x * l0_Nova_vector->nova_standard_datastruct_Nova_Vector2D_Nova_y);
	l1_Nova_cross->nova_standard_datastruct_Nova_Vector2D_Nova_y = (nova_standard_primitive_number_Nova_Number*)(this->nova_standard_datastruct_Nova_Vector2D_Nova_y * l0_Nova_vector->nova_standard_datastruct_Nova_Vector2D_Nova_x);
	return l1_Nova_cross;
}

nova_standard_primitive_number_Nova_Number* nova_standard_datastruct_Nova_Vector2D_Accessor_Nova_magnitude(nova_standard_datastruct_Nova_Vector2D* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_primitive_number_Nova_Number*)nova_standard_math_Nova_Math_Nova_sqrt(0, exceptionData, (double)*(this->nova_standard_datastruct_Nova_Vector2D_Nova_x * this->nova_standard_datastruct_Nova_Vector2D_Nova_x + this->nova_standard_datastruct_Nova_Vector2D_Nova_y * this->nova_standard_datastruct_Nova_Vector2D_Nova_y));
}


void nova_standard_datastruct_Nova_Vector2D_Nova_super(nova_standard_datastruct_Nova_Vector2D* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_datastruct_Nova_Vector2D_Nova_x = (nova_standard_primitive_number_Nova_Number*)nova_null;
	this->nova_standard_datastruct_Nova_Vector2D_Nova_y = (nova_standard_primitive_number_Nova_Number*)nova_null;
}

