#include <precompiled.h>
#include <example/example_Nova_Polygon.h>


example_Extension_VTable_Polygon example_Extension_VTable_Polygon_val =
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
		(int(*)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*))example_Nova_Polygon_0_Nova_numberSides,
		(double(*)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*))example_Nova_Polygon_0_Nova_calculateArea,
		0,
	},
	nova_standard_Nova_Object_3_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void example_Nova_PolygonNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Polygon* example_Nova_Polygon_2_Nova_construct(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_Polygon, this,);
	this->vtable = &example_Extension_VTable_Polygon_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	example_Nova_Polygon_Nova_super(this, exceptionData);
	
	{
		example_Nova_Polygon_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_Polygon_Nova_destroy(example_Nova_Polygon** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

int example_Nova_Polygon_0_Nova_numberSides(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData){}
double example_Nova_Polygon_0_Nova_calculateArea(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData){}
void example_Nova_Polygon_2_Nova_this(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_Polygon_Nova_super(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

