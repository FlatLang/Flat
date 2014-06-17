#include <precompiled.h>

#include "NovaSVGTest.h"

SVGTest* nova_SVGTest_SVGTest(ExceptionData* exceptionData)
{
		SVGTest* this = NULL;
		
		{
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

void nova_SVGTest_main(ExceptionData* exceptionData, String** nova_0_args)
{
		SVG* nova_1_s;
		double nova_1_pi;
		double nova_1_pi2;
		int nova_1_numVerts;
		double nova_1_coefficient;
		int nova_1_numPoints;
		long_long nova_1_start;
		double* nova_1_points;
		double nova_1_radius;
		double nova_1_offset;
		int nova_2_j;
		int nova_6_k;
		double nova_1_cx;
		double nova_1_cy;
		int nova_7_i;
		File* nova_1_f;
		long_long nova_1_end;
		
		nova_1_s = nova_SVG_SVG(exceptionData);
		nova_1_pi = 3.141592653;
		nova_1_pi2 = nova_1_pi * 2;
		nova_IO_print(exceptionData, nova_String_String(exceptionData, "Enter the number of vertices: "));
		nova_1_numVerts = nova_IO_getInt(exceptionData);
		nova_IO_print(exceptionData, nova_String_String(exceptionData, "Enter the coefficient: "));
		nova_1_coefficient = nova_IO_getDouble(exceptionData);
		nova_IO_print(exceptionData, nova_String_String(exceptionData, "Enter the number of points: "));
		nova_1_numPoints = nova_IO_getInt(exceptionData);
		nova_1_start = currentTimeMillis();
		nova_1_points = (double*)NOVA_MALLOC(sizeof(double) * (nova_1_numVerts * 2));
		nova_1_radius = 450;
		nova_1_offset = nova_1_pi2 / 12;
		nova_2_j = 0;
		
		for (; nova_2_j < nova_1_numVerts; nova_2_j++)
		{
				double nova_2_rad;
				double nova_zero_check3;
				
				nova_zero_check3 = (nova_1_numVerts * 1.0);
				if (nova_zero_check3 == 0)
				{
						THROW(2);
				}
				nova_2_rad = nova_1_pi2 * (nova_2_j / nova_zero_check3) + nova_1_offset;
				nova_1_points[nova_2_j * 2 + 0] = nova_1_radius * cos(nova_2_rad) + nova_1_radius + 10;
				nova_1_points[nova_2_j * 2 + 1] = nova_1_radius * sin(nova_2_rad) + nova_1_radius + 10;
		}
		nova_6_k = 0;
		
		for (; nova_6_k < nova_1_numVerts; nova_6_k++)
		{
				double nova_6_x;
				double nova_6_y;
				SVGCircle* nova_6_circle2;
				
				nova_6_x = nova_1_points[nova_6_k * 2 + 0];
				nova_6_y = nova_1_points[nova_6_k * 2 + 1];
				nova_6_circle2 = nova_SVGCircle_SVGCircle(exceptionData, nova_6_x, nova_6_y, 3);
				nova_IO_println(exceptionData, nova_String_concat(nova_SVGCircle_toString(nova_6_circle2, exceptionData), exceptionData, nova_String_String(exceptionData, "")));
				nova_SVGComponentList_addChild(nova_1_s->nova_SVG_root->nova_SVGComponent_children, exceptionData, nova_6_circle2);
		}
		nova_1_cx = nova_1_radius + 10;
		nova_1_cy = nova_1_radius + 10;
		nova_7_i = 0;
		
		for (; nova_7_i < nova_1_numPoints; nova_7_i++)
		{
				int nova_7_rand;
				double nova_7_x;
				double nova_7_y;
				
				nova_7_rand = nova_Math_random(exceptionData, nova_1_numVerts);
				nova_7_x = nova_1_points[nova_7_rand * 2 + 0];
				nova_7_y = nova_1_points[nova_7_rand * 2 + 1];
				nova_1_cx = nova_1_cx - (nova_1_cx - nova_7_x) * nova_1_coefficient;
				nova_1_cy = nova_1_cy - (nova_1_cy - nova_7_y) * nova_1_coefficient;
				if (nova_7_i > 15)
				{
						SVGCircle* nova_8_circle;
						
						if (nova_7_i % 1000 == 0)
						{
								nova_IO_println(exceptionData, nova_String_concat(nova_String_String(exceptionData, ""), exceptionData, nova_Integer_toString(nova_Integer_Integer(exceptionData, nova_7_i), exceptionData)));
						}
						nova_8_circle = nova_SVGCircle_SVGCircle(exceptionData, nova_1_cx, nova_1_cy, 1);
						nova_SVGComponentList_addChild(nova_1_s->nova_SVG_root->nova_SVGComponent_children, exceptionData, nova_8_circle);
				}
		}
		nova_1_f = nova_File_File(exceptionData, nova_String_String(exceptionData, "C:/Users/Braden Steffaniak/svgoutput.html"));
		nova_File_create(nova_1_f, exceptionData);
		nova_SVG_generateHTMLOutput(nova_1_s, exceptionData, nova_1_f);
		nova_1_end = currentTimeMillis();
		nova_IO_print(exceptionData, nova_String_concat(nova_String_String(exceptionData, "Done generating "), exceptionData, nova_Long_toString(nova_Long_Long(exceptionData, (nova_1_end - nova_1_start)), exceptionData)));
		nova_IO_waitForEnter(exceptionData);
}



int main(int argc, char** argvs)
{
		String** args;
		int      i;
		
		ExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_GC_init(exceptionData);
		
		args = (String**)NOVA_MALLOC(argc * sizeof(String));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_String_String(0, str);
		}
		
		TRY
		{
				nova_SVGTest_main(exceptionData, args);
		}
		CATCH (1)
		{
				printf("You broke it.");
				nova_IO_waitForEnter(0);
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		GC_gcollect();
		
		return 0;
}