#include <precompiled.h>
#include "NovaSVGTest.h"


nova_VTable_SVGTest nova_VTable_SVGTest_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

SVGTest* nova_SVGTest_construct(SVGTest* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(SVGTest, this,);
	this->vtable = &nova_VTable_SVGTest_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_SVGTest_super(this, 0);
	
	{
		nova_SVGTest_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_SVGTest(SVGTest** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_SVGTest_main(SVGTest* this, ExceptionData* exceptionData, String** nova_0_args)
{
	SVG* nova_1_s;
	double nova_1_pi2;
	int nova_1_numVerts;
	double nova_1_coefficient;
	int nova_1_numPoints;
	long_long nova_1_start;
	double* nova_1_points;
	double nova_1_radius;
	double nova_1_offset;
	int nova_1_j;
	int nova_1_k;
	double nova_1_cx;
	double nova_1_cy;
	int nova_1_i;
	File* nova_1_f;
	long_long nova_1_end;
	
	nova_1_s = nova_0_SVG_construct(0, exceptionData);
	nova_1_pi2 = (double)(nova_static_Math_PI * 2);
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Enter the number of vertices: "));
	nova_1_numVerts = nova_static_Console_readInt(0, exceptionData);
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Enter the coefficient: "));
	nova_1_coefficient = nova_static_Console_readDouble(0, exceptionData);
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Enter the number of points: "));
	nova_1_numPoints = nova_static_Console_readInt(0, exceptionData);
	nova_1_start = nova_static_Time_currentTimeMillis(0, exceptionData);
	nova_1_points = (double*)NOVA_MALLOC(sizeof(double) * (nova_1_numVerts * 2));
	nova_1_radius = (double)(450);
	nova_1_offset = nova_1_pi2 / 12;
	nova_1_j = 0;
	for (; nova_1_j < nova_1_numVerts; nova_1_j++)
	{
		double nova_2_rad;
		double nova_zero_check3;
		
		nova_zero_check3 = (nova_1_numVerts * 1.0);
		if (nova_zero_check3 == 0)
		{
			THROW(3);
		}
		nova_2_rad = nova_1_pi2 * (nova_1_j / nova_zero_check3) + nova_1_offset;
		nova_1_points[nova_1_j * 2 + 0] = (double)(nova_1_radius * nova_static_Math_cos(0, exceptionData, nova_2_rad) + nova_1_radius + 10);
		nova_1_points[nova_1_j * 2 + 1] = (double)(nova_1_radius * nova_static_Math_sin(0, exceptionData, nova_2_rad) + nova_1_radius + 10);
	}
	nova_1_k = 0;
	for (; nova_1_k < nova_1_numVerts; nova_1_k++)
	{
		double nova_6_x;
		double nova_6_y;
		SVGCircle* nova_6_circle2;
		
		nova_6_x = nova_1_points[nova_1_k * 2 + 0];
		nova_6_y = nova_1_points[nova_1_k * 2 + 1];
		nova_6_circle2 = nova_SVGCircle_construct(0, exceptionData, nova_6_x, nova_6_y, 3);
		nova_SVGComponentList_addChild(nova_1_s->nova_SVG_root->nova_SVGComponent_children, exceptionData, (SVGComponent*)(nova_6_circle2));
	}
	nova_1_cx = nova_1_radius + 10;
	nova_1_cy = nova_1_radius + 10;
	nova_1_i = 0;
	for (; nova_1_i < nova_1_numPoints; nova_1_i++)
	{
		int nova_7_rand;
		double nova_7_x;
		double nova_7_y;
		
		nova_7_rand = nova_static_Math_random(0, exceptionData, (long_long)(nova_1_numVerts));
		nova_7_x = nova_1_points[nova_7_rand * 2 + 0];
		nova_7_y = nova_1_points[nova_7_rand * 2 + 1];
		nova_1_cx = nova_1_cx - (nova_1_cx - nova_7_x) * nova_1_coefficient;
		nova_1_cy = nova_1_cy - (nova_1_cy - nova_7_y) * nova_1_coefficient;
		if (nova_1_i > 15)
		{
			SVGCircle* nova_8_circle;
			
			if (nova_1_i % 1000 == 0)
			{
				nova_static_2_Console_writeLine(0, exceptionData, (double)(nova_1_i));
			}
			nova_8_circle = nova_SVGCircle_construct(0, exceptionData, nova_1_cx, nova_1_cy, 1);
			nova_SVGComponentList_addChild(nova_1_s->nova_SVG_root->nova_SVGComponent_children, exceptionData, (SVGComponent*)(nova_8_circle));
		}
	}
	nova_1_f = nova_1_File_construct(0, exceptionData, nova_String_construct(0, exceptionData, "C:/Users/Braden Steffaniak/svgoutput.html"));
	nova_File_create(nova_1_f, exceptionData);
	nova_File_clearContents(nova_1_f, exceptionData);
	nova_SVG_generateHTMLOutput(nova_1_s, exceptionData, nova_1_f);
	nova_1_end = nova_static_Time_currentTimeMillis(0, exceptionData);
	nova_static_0_Console_write(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "Done generating "), exceptionData, nova_3_Long_toString(nova_Long_construct(0, exceptionData, (nova_1_end - nova_1_start)), exceptionData)));
	nova_static_Console_waitForEnter(0, exceptionData);
}

void nova_SVGTest_this(SVGTest* this, ExceptionData* exceptionData)
{
}

void nova_SVGTest_super(SVGTest* this, ExceptionData* exceptionData)
{
}
