#include <precompiled.h>
#include <example/example_Nova_SvgChart.h>



example_Extension_VTable_SvgChart example_Extension_VTable_SvgChart_val =
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
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void example_Nova_SvgChart_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_SvgChart* example_Nova_SvgChart_Nova_SvgChart(example_Nova_SvgChart* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_SvgChart, this,);
	this->vtable = &example_Extension_VTable_SvgChart_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_SvgChart_Nova_super(this, exceptionData);
	
	{
		example_Nova_SvgChart_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_SvgChart_Nova_destroy(example_Nova_SvgChart** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_SvgChart_Nova_main(example_Nova_SvgChart* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_SvgChart_Nova_args)
{
	nova_time_Nova_Timer* l1_Nova_timer = (nova_time_Nova_Timer*)nova_null;
	nova_web_svg_no3_Nova_No3Select* l1_Nova_selection = (nova_web_svg_no3_Nova_No3Select*)nova_null;
	
	l1_Nova_timer = nova_time_Nova_Timer_Nova_start(nova_time_Nova_Timer_Nova_Timer(0, exceptionData), exceptionData);
	l1_Nova_selection = nova_web_svg_no3_Nova_No3_Nova_select(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "body"));
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_web_svg_no3_Nova_No3Node_virtual1_Nova_toJs((nova_web_svg_no3_Nova_No3Node*)(l1_Nova_selection), exceptionData));
	nova_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData);
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, "Done generating "), exceptionData, nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData))));
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_SvgChart_0_Nova_this(example_Nova_SvgChart* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_SvgChart_Nova_super(example_Nova_SvgChart* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

