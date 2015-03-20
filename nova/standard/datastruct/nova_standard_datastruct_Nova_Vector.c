#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Vector.h>


nova_standard_datastruct_Extension_VTable_Vector nova_standard_datastruct_Extension_VTable_Vector_val =
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





void nova_standard_datastruct_Nova_VectorNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_Nova_Vector* nova_standard_datastruct_Nova_Vector_2_Nova_construct(nova_standard_datastruct_Nova_Vector* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_datastruct_Nova_Vector, this,);
	this->vtable = &nova_standard_datastruct_Extension_VTable_Vector_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_datastruct_Nova_Vector_Nova_super(this, exceptionData);
	
	{
		nova_standard_datastruct_Nova_Vector_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_datastruct_Nova_Vector_Nova_destroy(nova_standard_datastruct_Nova_Vector** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_datastruct_Nova_Vector_Nova_scale(nova_standard_datastruct_Nova_Vector* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Number* l0_Nova_scalar){}
void nova_standard_datastruct_Nova_Vector_Nova_normalize(nova_standard_datastruct_Nova_Vector* this, nova_standard_exception_Nova_ExceptionData* exceptionData){}
nova_standard_datastruct_Nova_Vector* nova_standard_datastruct_Nova_Vector_0_Nova_crossProduct(nova_standard_datastruct_Nova_Vector* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Vector* l0_Nova_v1, nova_standard_datastruct_Nova_Vector* l0_Nova_v2)
{
	return nova_standard_datastruct_Nova_Vector_1_Nova_crossProduct(l0_Nova_v1, exceptionData, l0_Nova_v2);
}

nova_standard_datastruct_Nova_Vector* nova_standard_datastruct_Nova_Vector_1_Nova_crossProduct(nova_standard_datastruct_Nova_Vector* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_Nova_Vector* l0_Nova_vector){}
void nova_standard_datastruct_Nova_Vector_2_Nova_this(nova_standard_datastruct_Nova_Vector* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}



void nova_standard_datastruct_Nova_Vector_Nova_super(nova_standard_datastruct_Nova_Vector* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

