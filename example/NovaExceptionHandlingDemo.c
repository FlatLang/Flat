#include <precompiled.h>
#include "NovaExceptionHandlingDemo.h"


nova_VTable_ExceptionHandlingDemo nova_VTable_ExceptionHandlingDemo_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

int nova_static_ExceptionHandlingDemo_divide(ExceptionHandlingDemo* this, ExceptionData* exceptionData, int nova_0_num, int nova_0_den);

ExceptionHandlingDemo* nova_ExceptionHandlingDemo_construct(ExceptionHandlingDemo* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(ExceptionHandlingDemo, this,);
	this->vtable = &nova_VTable_ExceptionHandlingDemo_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_ExceptionHandlingDemo_super(this, 0);
	
	{
		nova_ExceptionHandlingDemo_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_ExceptionHandlingDemo(ExceptionHandlingDemo** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_ExceptionHandlingDemo_main(ExceptionHandlingDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
	TRY
	{
		nova_ExceptionData_addCode(exceptionData, exceptionData, 5);
		
		{
			int nova_2_result;
			
			nova_2_result = nova_static_ExceptionHandlingDemo_divide((ExceptionHandlingDemo*)0, exceptionData, 100, 5);
			nova_static_0_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "After working example: "), exceptionData, nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_2_result), exceptionData)));
			nova_2_result = nova_static_ExceptionHandlingDemo_divide((ExceptionHandlingDemo*)0, exceptionData, 100, 3);
			nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "this output will not show."));
		}
	}
	CATCH (5)
	{
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "You used the divide() method incorrectly."));
	}
	FINALLY
	{
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Exiting the try block."));
	}
	END_TRY;
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "After the try block."));
	nova_static_Console_waitForEnter(0, exceptionData);
}

int nova_static_ExceptionHandlingDemo_divide(ExceptionHandlingDemo* this, ExceptionData* exceptionData, int nova_0_num, int nova_0_den)
{
	int nova_zero_check4;
	
	if (nova_0_num % nova_0_den != 0)
	{
		THROW(5);
	}
	nova_zero_check4 = nova_0_den;
	if (nova_zero_check4 == 0)
	{
		THROW(3);
	}
	return nova_0_num / nova_zero_check4;
}

void nova_ExceptionHandlingDemo_this(ExceptionHandlingDemo* this, ExceptionData* exceptionData)
{
}

void nova_ExceptionHandlingDemo_super(ExceptionHandlingDemo* this, ExceptionData* exceptionData)
{
}
