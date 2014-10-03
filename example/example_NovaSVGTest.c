#include <precompiled.h>
#include <example/example_NovaSVGTest.h>


nova_VTable_example_NovaSVGTest nova_VTable_example_NovaSVGTest_val =
{
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};

example_NovaSVGTest* example_NovaSVGTest_Nova0_construct(example_NovaSVGTest* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(example_NovaSVGTest, this,);
		this->vtable = &nova_VTable_example_NovaSVGTest_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		example_NovaSVGTest_Novasuper(this, 0);
		
		{
				example_NovaSVGTest_Novathis(this, exceptionData);
		}
		
		return this;
}

void nova_del_SVGTest(example_NovaSVGTest** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void example_NovaSVGTest_static_Novamain(example_NovaSVGTest* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		nova_standard_svg_NovaSVG* l1_Novas;
		double l1_Novapi2;
		int l1_NovanumVerts;
		double l1_Novacoefficient;
		int l1_NovanumPoints;
		long_long l1_Novastart;
		double* l1_Novapoints;
		double l1_Novaradius;
		double l1_Novaoffset;
		double l1_Novacx;
		double l1_Novacy;
		nova_standard_io_NovaFile* l1_Novaf;
		long_long l1_Novaend;
		int l2_Novai;
		int l6_Novai;
		int l7_Novai;
		
		l1_Novas = nova_standard_svg_NovaSVG_Nova0_construct(0, exceptionData);
		l1_Novapi2 = (double)(nova_standard_NovaMath_static_NovaPI * 2);
		nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter the number of vertices: "));
		l1_NovanumVerts = nova_standard_io_NovaConsole_static_NovareadInt(0, exceptionData);
		nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter the coefficient: "));
		l1_Novacoefficient = nova_standard_io_NovaConsole_static_NovareadDouble(0, exceptionData);
		nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter the number of points: "));
		l1_NovanumPoints = nova_standard_io_NovaConsole_static_NovareadInt(0, exceptionData);
		l1_Novastart = nova_standard_time_NovaTime_static_NovacurrentTimeMillis(0, exceptionData);
		l1_Novapoints = (double*)NOVA_MALLOC(sizeof(double[l1_NovanumVerts * 2]));
		l1_Novaradius = (double)(450);
		l1_Novaoffset = l1_Novapi2 / 12;
		l2_Novai = 0;
		for (; l2_Novai < l1_NovanumVerts; l2_Novai++)
		{
				double l2_Novarad;
				double nova_zero_check3;
				
				nova_zero_check3 = (l1_NovanumVerts * 1.0);
				if (nova_zero_check3 == 0)
				{
						THROW(4, nova_standard_exception_NovaDivideByZeroException_Nova0_construct(0, exceptionData));
				}
				l2_Novarad = l1_Novapi2 * (l2_Novai / nova_zero_check3) + l1_Novaoffset;
				l1_Novapoints[l2_Novai * 2 + 0] = l1_Novaradius * nova_standard_NovaMath_static_Novacos(0, exceptionData, l2_Novarad) + l1_Novaradius + 10;
				l1_Novapoints[l2_Novai * 2 + 1] = l1_Novaradius * nova_standard_NovaMath_static_Novasin(0, exceptionData, l2_Novarad) + l1_Novaradius + 10;
		}
		l6_Novai = 0;
		for (; l6_Novai < l1_NovanumVerts; l6_Novai++)
		{
				double l6_Novax;
				double l6_Novay;
				nova_standard_svg_NovaSVGCircle* l6_Novacircle2;
				
				l6_Novax = l1_Novapoints[l6_Novai * 2 + 0];
				l6_Novay = l1_Novapoints[l6_Novai * 2 + 1];
				l6_Novacircle2 = nova_standard_svg_NovaSVGCircle_Novaconstruct(0, exceptionData, l6_Novax, l6_Novay, 3);
				nova_standard_svg_NovaSVGComponentList_NovaaddChild(l1_Novas->nova_standard_svg_NovaSVG_Novaroot->nova_standard_svg_NovaSVGComponent_Novachildren, exceptionData, (nova_standard_svg_NovaSVGComponent*)(l6_Novacircle2));
		}
		l1_Novacx = l1_Novaradius + 10;
		l1_Novacy = l1_Novaradius + 10;
		l7_Novai = 0;
		for (; l7_Novai < l1_NovanumPoints; l7_Novai++)
		{
				int l7_Novarand;
				double l7_Novax;
				double l7_Novay;
				
				l7_Novarand = nova_standard_NovaMath_static_Novarandom(0, exceptionData, (long_long)(l1_NovanumVerts));
				l7_Novax = l1_Novapoints[l7_Novarand * 2 + 0];
				l7_Novay = l1_Novapoints[l7_Novarand * 2 + 1];
				l1_Novacx = l1_Novacx - (l1_Novacx - l7_Novax) * l1_Novacoefficient;
				l1_Novacy = l1_Novacy - (l1_Novacy - l7_Novay) * l1_Novacoefficient;
				if (l7_Novai > 15)
				{
						nova_standard_svg_NovaSVGCircle* l8_Novacircle;
						
						if (l7_Novai % 1000 == 0)
						{
								nova_standard_io_NovaConsole_static_Nova2_writeLine(0, exceptionData, (double)(l7_Novai));
						}
						l8_Novacircle = nova_standard_svg_NovaSVGCircle_Novaconstruct(0, exceptionData, l1_Novacx, l1_Novacy, 1);
						nova_standard_svg_NovaSVGComponentList_NovaaddChild(l1_Novas->nova_standard_svg_NovaSVG_Novaroot->nova_standard_svg_NovaSVGComponent_Novachildren, exceptionData, (nova_standard_svg_NovaSVGComponent*)(l8_Novacircle));
				}
		}
		l1_Novaf = nova_standard_io_NovaFile_Nova1_construct(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "C:/Users/Braden/svgoutput.html"));
		nova_standard_io_NovaFile_Novacreate(l1_Novaf, exceptionData);
		nova_standard_io_NovaFile_NovaclearContents(l1_Novaf, exceptionData);
		nova_standard_svg_NovaSVG_NovagenerateHTMLOutput(l1_Novas, exceptionData, l1_Novaf);
		l1_Novaend = nova_standard_time_NovaTime_static_NovacurrentTimeMillis(0, exceptionData);
		nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "Done generating "), exceptionData, nova_standard_primitive_number_NovaLong_Nova3_toString(nova_standard_primitive_number_NovaLong_Novaconstruct(0, exceptionData, (l1_Novaend - l1_Novastart)), exceptionData)));
		nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
}

void example_NovaSVGTest_Novathis(example_NovaSVGTest* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_NovaSVGTest_Novasuper(example_NovaSVGTest* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}


nova_standard_primitive_NovaNull* nova_null;

int main(int argc, char** argvs)
{
		nova_standard_NovaString** args;
		int      i;
		
		nova_standard_exception_NovaExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_null = nova_standard_primitive_NovaNull_Nova0_construct(0, exceptionData);
		nova_standard_gc_NovaGC_static_Novainit(0, exceptionData);
		
		nova_standard_NovaStringNova_init_static(exceptionData);
		nova_standard_NovaMathNova_init_static(exceptionData);
		nova_standard_logic_NovaWFFNova_init_static(exceptionData);
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_Novaconstruct(0, 0, str);
		}
		
		TRY
		{
				example_NovaSVGTest_static_Novamain(0, exceptionData, args);
		}
		CATCH (1)
		{
				nova_standard_exception_NovaException* base = (nova_standard_exception_NovaException*)exceptionData->nova_standard_exception_NovaExceptionData_NovathrownException;
				printf("Exception in Thread 'main': %s", nova_standard_NovaString_NovatoCharArray(base->nova_standard_exception_NovaException_Novamessage, 0));
				nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		GC_gcollect();
		
		return 0;
}