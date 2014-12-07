#include <precompiled.h>
#include <example/example_Nova_Polygon.h>


nova_VTable_example_Nova_Polygon nova_VTable_example_Nova_Polygon_val =
{
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	example_Nova_Polygon_0_Nova_numberSides,
	example_Nova_Polygon_0_Nova_calculateArea,
};
void example_Nova_PolygonNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Polygon* example_Nova_Polygon_2_Nova_construct(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_Polygon, this,);
	this->vtable = &nova_VTable_example_Nova_Polygon_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	example_Nova_Polygon_Nova_super(this, exceptionData);
	
	{
		example_Nova_Polygon_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_Polygon_Nova_destroy(example_Nova_Polygon** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int example_Nova_Polygon_0_Nova_numberSides(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData){}
double example_Nova_Polygon_0_Nova_calculateArea(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData){}
void example_Nova_Polygon_Nova_this(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_Polygon_Nova_super(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

