#include <precompiled.h>
#include <example/ackermann/example_ackermann_Nova_Ackermann.h>

example_ackermann_Extension_VTable_Ackermann example_ackermann_Extension_VTable_Ackermann_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void example_ackermann_Nova_AckermannNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_ackermann_Nova_Ackermann* example_ackermann_Nova_Ackermann_Nova_Ackermann(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_ackermann_Nova_Ackermann, this,);
	this->vtable = &example_ackermann_Extension_VTable_Ackermann_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_ackermann_Nova_Ackermann_Nova_super(this, exceptionData);
	
	{
		example_ackermann_Nova_Ackermann_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_ackermann_Nova_Ackermann_Nova_destroy(example_ackermann_Nova_Ackermann** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_ackermann_Nova_Ackermann_Nova_main(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_Array* example_ackermann_Nova_Ackermann_Nova_args)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Ackermann: "), exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, example_ackermann_Nova_Ackermann_Nova_run(0, exceptionData, 4, 1))));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

int example_ackermann_Nova_Ackermann_Nova_run(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_ackermann_Nova_Ackermann_Nova_m, int example_ackermann_Nova_Ackermann_Nova_n)
{
	if (example_ackermann_Nova_Ackermann_Nova_m == 0)
	{
		return example_ackermann_Nova_Ackermann_Nova_n + 1;
	}
	else if (example_ackermann_Nova_Ackermann_Nova_m > 0)
	{
		if (example_ackermann_Nova_Ackermann_Nova_n == 0)
		{
			return example_ackermann_Nova_Ackermann_Nova_run(0, exceptionData, example_ackermann_Nova_Ackermann_Nova_m - 1, 1);
		}
		else if (example_ackermann_Nova_Ackermann_Nova_n > 0)
		{
			return example_ackermann_Nova_Ackermann_Nova_run(0, exceptionData, example_ackermann_Nova_Ackermann_Nova_m - 1, example_ackermann_Nova_Ackermann_Nova_run(0, exceptionData, example_ackermann_Nova_Ackermann_Nova_m, example_ackermann_Nova_Ackermann_Nova_n - 1));
		}
	}
	return (int)0;
}

int example_ackermann_Nova_Ackermann_Nova_run2(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_ackermann_Nova_Ackermann_Nova_m, int example_ackermann_Nova_Ackermann_Nova_n)
{
	if (example_ackermann_Nova_Ackermann_Nova_m == 0)
	{
		return example_ackermann_Nova_Ackermann_Nova_n + 1;
	}
	else if (example_ackermann_Nova_Ackermann_Nova_m > 0)
	{
		if (example_ackermann_Nova_Ackermann_Nova_n == 0)
		{
			return example_ackermann_Nova_Ackermann_Nova_run(0, exceptionData, example_ackermann_Nova_Ackermann_Nova_m - 1, 1);
		}
		else if (example_ackermann_Nova_Ackermann_Nova_n > 0)
		{
			return example_ackermann_Nova_Ackermann_Nova_run(0, exceptionData, example_ackermann_Nova_Ackermann_Nova_m - 1, example_ackermann_Nova_Ackermann_Nova_run(0, exceptionData, example_ackermann_Nova_Ackermann_Nova_m, example_ackermann_Nova_Ackermann_Nova_n - 1));
		}
	}
	return (int)0;
}

void example_ackermann_Nova_Ackermann_0_Nova_this(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_ackermann_Nova_Ackermann_Nova_super(example_ackermann_Nova_Ackermann* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

