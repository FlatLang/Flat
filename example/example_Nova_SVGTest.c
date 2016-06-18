#include <precompiled.h>
#include <example/example_Nova_SVGTest.h>

example_Extension_VTable_SVGTest example_Extension_VTable_SVGTest_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void example_Nova_SVGTestNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_SVGTest* example_Nova_SVGTest_0_Nova_construct(example_Nova_SVGTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_SVGTest, this,);
	this->vtable = &example_Extension_VTable_SVGTest_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_SVGTest_Nova_super(this, exceptionData);
	
	{
		example_Nova_SVGTest_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_SVGTest_Nova_destroy(example_Nova_SVGTest** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_SVGTest_Nova_main(example_Nova_SVGTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_SVGTest_Nova_args)
{
	nova_standard_svg_Nova_SVG* l1_Nova_s;
	double l1_Nova_pi2;
	int l1_Nova_numVerts;
	double l1_Nova_coefficient;
	int l1_Nova_numPoints;
	nova_standard_time_Nova_Timer* l1_Nova_timer;
	double* l1_Nova_points;
	double l1_Nova_radius;
	double l1_Nova_offset;
	double l1_Nova_cx;
	double l1_Nova_cy;
	nova_standard_io_Nova_File* l1_Nova_f;
	int l2_Nova_i;
	int l6_Nova_i;
	int l8_Nova_i;
	
	l1_Nova_s = nova_standard_svg_Nova_SVG_0_Nova_construct(0, exceptionData);
	l1_Nova_pi2 = (double)(nova_standard_math_Nova_Math_Nova_PI * 2);
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Enter the number of vertices: "));
	l1_Nova_numVerts = nova_standard_io_Nova_Console_Nova_readInt(0, exceptionData);
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Enter the coefficient: "));
	l1_Nova_coefficient = nova_standard_io_Nova_Console_Nova_readDouble(0, exceptionData);
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Enter the number of points: "));
	l1_Nova_numPoints = nova_standard_io_Nova_Console_Nova_readInt(0, exceptionData);
	l1_Nova_timer = nova_standard_time_Nova_Timer_Nova_start(nova_standard_time_Nova_Timer_0_Nova_construct(0, exceptionData), exceptionData);
	l1_Nova_points = (double*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Double) * l1_Nova_numVerts * 2);
	l1_Nova_radius = (double)(450);
	l1_Nova_offset = l1_Nova_pi2 / 12;
	l2_Nova_i = 0;
	for (; l2_Nova_i < l1_Nova_numVerts; l2_Nova_i++)
	{
		double l2_Nova_rad;
		double nova_zero_check5;
		
		nova_zero_check5 = (l1_Nova_numVerts * 1.0);
		if (nova_zero_check5 == 0)
		{
			THROW(8, nova_standard_exception_Nova_DivideByZeroException_3_Nova_construct(0, exceptionData));
		}
		l2_Nova_rad = l1_Nova_pi2 * (l2_Nova_i / nova_zero_check5) + l1_Nova_offset;
		l1_Nova_points[l2_Nova_i * 2 + 0] = l1_Nova_radius * nova_standard_math_Nova_Math_Nova_cos(0, exceptionData, l2_Nova_rad) + l1_Nova_radius + 10;
		l1_Nova_points[l2_Nova_i * 2 + 1] = l1_Nova_radius * nova_standard_math_Nova_Math_Nova_sin(0, exceptionData, l2_Nova_rad) + l1_Nova_radius + 10;
	}
	l6_Nova_i = 0;
	for (; l6_Nova_i < l1_Nova_numVerts; l6_Nova_i++)
	{
		double l6_Nova_x;
		double l6_Nova_y;
		nova_standard_svg_Nova_SVGCircle* l6_Nova_circle2;
		
		l6_Nova_x = l1_Nova_points[l6_Nova_i * 2 + 0];
		l6_Nova_y = l1_Nova_points[l6_Nova_i * 2 + 1];
		l6_Nova_circle2 = nova_standard_svg_Nova_SVGCircle_1_Nova_construct(0, exceptionData, l6_Nova_x, l6_Nova_y, 3);
		nova_standard_svg_Nova_SVGComponentList_Nova_addChild(l1_Nova_s->nova_standard_svg_Nova_SVG_Nova_root->nova_standard_svg_Nova_SVGComponent_Nova_children, exceptionData, (nova_standard_svg_Nova_SVGComponent*)(l6_Nova_circle2));
	}
	l1_Nova_cx = l1_Nova_radius + 10;
	l1_Nova_cy = l1_Nova_radius + 10;
	l8_Nova_i = 0;
	for (; l8_Nova_i < l1_Nova_numPoints; l8_Nova_i++)
	{
		int l8_Nova_rand;
		double l8_Nova_x;
		double l8_Nova_y;
		
		l8_Nova_rand = nova_standard_math_Nova_Math_Nova_random(0, exceptionData, (long)(l1_Nova_numVerts));
		l8_Nova_x = l1_Nova_points[l8_Nova_rand * 2 + 0];
		l8_Nova_y = l1_Nova_points[l8_Nova_rand * 2 + 1];
		l1_Nova_cx = l1_Nova_cx - (l1_Nova_cx - l8_Nova_x) * l1_Nova_coefficient;
		l1_Nova_cy = l1_Nova_cy - (l1_Nova_cy - l8_Nova_y) * l1_Nova_coefficient;
		if (l8_Nova_i > 15)
		{
			nova_standard_svg_Nova_SVGCircle* l9_Nova_circle;
			
			if ((l8_Nova_i + 1) % 1000 == 0)
			{
				nova_standard_io_Nova_Console_2_Nova_writeLine(0, exceptionData, (nova_standard_Nova_Object*)(nova_standard_primitive_number_Nova_Int_Nova_construct(0, exceptionData, l8_Nova_i + 1)));
			}
			l9_Nova_circle = nova_standard_svg_Nova_SVGCircle_1_Nova_construct(0, exceptionData, l1_Nova_cx, l1_Nova_cy, 1);
			nova_standard_svg_Nova_SVGComponentList_Nova_addChild(l1_Nova_s->nova_standard_svg_Nova_SVG_Nova_root->nova_standard_svg_Nova_SVGComponent_Nova_children, exceptionData, (nova_standard_svg_Nova_SVGComponent*)(l9_Nova_circle));
		}
	}
	l1_Nova_f = nova_standard_io_Nova_File_1_Nova_construct(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "C:/Users/Braden Steffaniak/svgoutput.html"));
	nova_standard_io_Nova_File_Nova_create(l1_Nova_f, exceptionData);
	nova_standard_io_Nova_File_Nova_clearContents(l1_Nova_f, exceptionData);
	nova_standard_svg_Nova_SVG_Nova_generateHTMLOutput(l1_Nova_s, exceptionData, l1_Nova_f);
	nova_standard_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData);
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Done generating "), exceptionData, nova_standard_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_standard_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData))));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_SVGTest_0_Nova_this(example_Nova_SVGTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_SVGTest_Nova_super(example_Nova_SVGTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

