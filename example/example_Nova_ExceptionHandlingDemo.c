#include <precompiled.h>
#include <example/example_Nova_ExceptionHandlingDemo.h>



example_Extension_VTable_ExceptionHandlingDemo example_Extension_VTable_ExceptionHandlingDemo_val =
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



int example_Nova_ExceptionHandlingDemo_Nova_divide(example_Nova_ExceptionHandlingDemo* this, nova_exception_Nova_ExceptionData* exceptionData, int example_Nova_ExceptionHandlingDemo_Nova_num, int example_Nova_ExceptionHandlingDemo_Nova_den);
void example_Nova_ExceptionHandlingDemo_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_Nova_ExceptionHandlingDemo* example_Nova_ExceptionHandlingDemo_Nova_ExceptionHandlingDemo(example_Nova_ExceptionHandlingDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_Nova_ExceptionHandlingDemo, this,);
	this->vtable = &example_Extension_VTable_ExceptionHandlingDemo_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_Nova_ExceptionHandlingDemo_Nova_super(this, exceptionData);
	
	{
		example_Nova_ExceptionHandlingDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_Nova_ExceptionHandlingDemo_Nova_destroy(example_Nova_ExceptionHandlingDemo** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_Nova_ExceptionHandlingDemo_Nova_main(example_Nova_ExceptionHandlingDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_Nova_ExceptionHandlingDemo_Nova_args)
{
	TRY
	{
		novaEnv.nova_exception_ExceptionData.addCode(exceptionData, exceptionData, 7);
		
		{
			int l1_Nova_result = 0;
			
			l1_Nova_result = example_Nova_ExceptionHandlingDemo_Nova_divide(0, exceptionData, 100, 5);
			nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_String(0, exceptionData, "After working example: "), exceptionData, nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, l1_Nova_result)));
			l1_Nova_result = example_Nova_ExceptionHandlingDemo_Nova_divide(0, exceptionData, 100, 3);
			nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "this output will not show."));
		}
	}
	CATCH (7)
	{
		example_Nova_NonWholeDivisionException* l2_Nova_e = (example_Nova_NonWholeDivisionException*)nova_null;
		
		l2_Nova_e = (example_Nova_NonWholeDivisionException*)exceptionData->nova_exception_Nova_ExceptionData_Nova_thrownException;
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "You used the divide() method incorrectly."));
	}
	FINALLY
	{
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Exiting the try block."));
	}
	END_TRY;
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "After the try block."));
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

int example_Nova_ExceptionHandlingDemo_Nova_divide(example_Nova_ExceptionHandlingDemo* this, nova_exception_Nova_ExceptionData* exceptionData, int example_Nova_ExceptionHandlingDemo_Nova_num, int example_Nova_ExceptionHandlingDemo_Nova_den)
{
	int nova_zero_check4 = 0;
	
	if (example_Nova_ExceptionHandlingDemo_Nova_num % example_Nova_ExceptionHandlingDemo_Nova_den != 0)
	{
		THROW(7, example_Nova_NonWholeDivisionException_Nova_NonWholeDivisionException(0, exceptionData));
	}
	nova_zero_check4 = example_Nova_ExceptionHandlingDemo_Nova_den;
	if (nova_zero_check4 == 0)
	{
		THROW(8, nova_exception_Nova_DivideByZeroException_Nova_DivideByZeroException(0, exceptionData));
	}
	return example_Nova_ExceptionHandlingDemo_Nova_num / nova_zero_check4;
}

void example_Nova_ExceptionHandlingDemo_0_Nova_this(example_Nova_ExceptionHandlingDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_Nova_ExceptionHandlingDemo_Nova_super(example_Nova_ExceptionHandlingDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

