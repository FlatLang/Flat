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
		(int(*)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*))example_Nova_Polygon_0_Nova_numberSides,
		(double(*)(example_Nova_Polygon*, nova_standard_exception_Nova_ExceptionData*))example_Nova_Polygon_0_Nova_calculateArea,
	},
	example_Nova_Polygon_0_Nova_numberSides,
	example_Nova_Polygon_0_Nova_calculateArea,
};


void example_Nova_PolygonNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

int example_Nova_Polygon_0_Nova_numberSides(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData){return 0;}
double example_Nova_Polygon_0_Nova_calculateArea(example_Nova_Polygon* this, nova_standard_exception_Nova_ExceptionData* exceptionData){return 0;}
