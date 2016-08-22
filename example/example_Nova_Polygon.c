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
	example_Nova_Polygon_virtual1_Nova_numberSides,
	example_Nova_Polygon_virtual1_Nova_calculateArea,
};


void example_Nova_Polygon_Nova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}



int example_Nova_Polygon_virtual1_Nova_numberSides(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.example_Nova_Polygon_virtual1_Nova_numberSides((example_Nova_Polygon*)(this), exceptionData);
}

double example_Nova_Polygon_virtual1_Nova_calculateArea(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->itable.example_Nova_Polygon_virtual1_Nova_calculateArea((example_Nova_Polygon*)(this), exceptionData);
}

