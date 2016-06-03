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
		(int(*)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*))example_Nova_Polygon_0_Nova_numberSides,
		(double(*)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*))example_Nova_Polygon_0_Nova_calculateArea,
		0,
	},
	example_Nova_Polygon_0_Nova_numberSides,
	example_Nova_Polygon_0_Nova_calculateArea,
};


void example_Nova_PolygonNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_Polygon* example_Nova_Polygon_4_Nova_construct(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_Polygon, this,);
	this->vtable = &example_Extension_VTable_Polygon_val;
	example_Nova_Polygon_Nova_super(this, exceptionData);
	
	{
		example_Nova_Polygon_4_Nova_this(this, exceptionData);
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
void example_Nova_Polygon_4_Nova_this(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_Polygon_Nova_super(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

