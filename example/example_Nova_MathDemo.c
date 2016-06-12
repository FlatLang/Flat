#include <precompiled.h>
#include <example/example_Nova_MathDemo.h>

example_Extension_VTable_MathDemo example_Extension_VTable_MathDemo_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
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
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void example_Nova_MathDemoNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_MathDemo* example_Nova_MathDemo_2_Nova_construct(example_Nova_MathDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_MathDemo, this,);
	this->vtable = &example_Extension_VTable_MathDemo_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_MathDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_MathDemo_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_MathDemo_Nova_destroy(example_Nova_MathDemo** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void example_Nova_MathDemo_Nova_main(example_Nova_MathDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_MathDemo_Nova_args)
{
	int l1_Nova_iterations;
	nova_standard_time_Nova_Timer* l1_Nova_timer;
	int l2_Nova_i;
	nova_standard_Nova_String* nova_local_0;
	nova_standard_Nova_String* nova_local_1;
	
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Beginning benchmark..."));
	l1_Nova_iterations = 999999;
	l1_Nova_timer = nova_standard_time_Nova_Timer_Nova_start(nova_standard_time_Nova_Timer_2_Nova_construct(0, exceptionData), exceptionData);
	l2_Nova_i = 0;
	for (; l2_Nova_i < l1_Nova_iterations; l2_Nova_i++)
	{
		TRY
		{
			novaEnv.nova_standard_exception_ExceptionData.addCode(exceptionData, exceptionData, 1);
			
			{
				nova_standard_math_Nova_Math_Nova_sin(0, exceptionData, (double)(l2_Nova_i));
			}
		}
		CATCH (1)
		{
			nova_standard_exception_Nova_Exception* l4_Nova_e;
			
			l4_Nova_e = exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException;
		}
		FINALLY
		{
		}
		END_TRY;
	}
	nova_standard_time_Nova_Timer_Nova_stop(0, exceptionData);
	nova_local_0 = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, l1_Nova_iterations);
	nova_local_1 = nova_standard_primitive_number_Nova_Long_1_Nova_toString(0, exceptionData, nova_standard_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData));
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, " iterations of Math.sin() in "), exceptionData, nova_local_1->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_1, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "ms")))));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void example_Nova_MathDemo_2_Nova_this(example_Nova_MathDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_MathDemo_Nova_super(example_Nova_MathDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

