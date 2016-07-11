#include <precompiled.h>
#include <example/example_Nova_ExceptionHandlingDemo.h>

example_Extension_VTable_ExceptionHandlingDemo example_Extension_VTable_ExceptionHandlingDemo_val =
{
	{
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
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_2_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



int example_Nova_ExceptionHandlingDemo_Nova_divide(example_Nova_ExceptionHandlingDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_ExceptionHandlingDemo_Nova_num, int example_Nova_ExceptionHandlingDemo_Nova_den);
void example_Nova_ExceptionHandlingDemoNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_ExceptionHandlingDemo* example_Nova_ExceptionHandlingDemo_Nova_ExceptionHandlingDemo(example_Nova_ExceptionHandlingDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_ExceptionHandlingDemo, this,);
	this->vtable = &example_Extension_VTable_ExceptionHandlingDemo_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_Nova_ExceptionHandlingDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_ExceptionHandlingDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_ExceptionHandlingDemo_Nova_destroy(example_Nova_ExceptionHandlingDemo** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_ExceptionHandlingDemo_Nova_main(example_Nova_ExceptionHandlingDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_Nova_ExceptionHandlingDemo_Nova_args)
{
	TRY
	{
		novaEnv.nova_standard_exception_ExceptionData.addCode(exceptionData, exceptionData, 9);
		
		{
			int l1_Nova_result;
			
			l1_Nova_result = example_Nova_ExceptionHandlingDemo_Nova_divide(0, exceptionData, 100, 5);
			nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "After working example: "), exceptionData, nova_standard_primitive_number_Nova_Int_3_Nova_toString(0, exceptionData, l1_Nova_result)));
			l1_Nova_result = example_Nova_ExceptionHandlingDemo_Nova_divide(0, exceptionData, 100, 3);
			nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "this output will not show."));
		}
	}
	CATCH (9)
	{
		example_Nova_NonWholeDivisionException* l2_Nova_e;
		
		l2_Nova_e = (example_Nova_NonWholeDivisionException*)(exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException);
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "You used the divide() method incorrectly."));
	}
	FINALLY
	{
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Exiting the try block."));
	}
	END_TRY;
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "After the try block."));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

int example_Nova_ExceptionHandlingDemo_Nova_divide(example_Nova_ExceptionHandlingDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int example_Nova_ExceptionHandlingDemo_Nova_num, int example_Nova_ExceptionHandlingDemo_Nova_den)
{
	int nova_zero_check4;
	
	if (example_Nova_ExceptionHandlingDemo_Nova_num % example_Nova_ExceptionHandlingDemo_Nova_den != 0)
	{
		THROW(9, example_Nova_NonWholeDivisionException_Nova_NonWholeDivisionException(0, exceptionData));
	}
	nova_zero_check4 = example_Nova_ExceptionHandlingDemo_Nova_den;
	if (nova_zero_check4 == 0)
	{
		THROW(2, nova_standard_exception_Nova_DivideByZeroException_Nova_DivideByZeroException(0, exceptionData));
	}
	return example_Nova_ExceptionHandlingDemo_Nova_num / nova_zero_check4;
}

void example_Nova_ExceptionHandlingDemo_0_Nova_this(example_Nova_ExceptionHandlingDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_ExceptionHandlingDemo_Nova_super(example_Nova_ExceptionHandlingDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

