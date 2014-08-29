#include <precompiled.h>
#include "NovaMathDemo.h"


nova_VTable_MathDemo nova_VTable_MathDemo_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

MathDemo* nova_MathDemo_construct(MathDemo* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(MathDemo, this,);
	this->vtable = &nova_VTable_MathDemo_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_MathDemo_super(this, 0);
	
	{
		nova_MathDemo_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_MathDemo(MathDemo** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_MathDemo_main(MathDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
	int nova_1_iterations;
	long_long nova_1_start;
	int nova_1_i;
	long_long nova_1_time;
	
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Beginning benchmark..."));
	nova_1_iterations = 999999;
	nova_1_start = nova_static_Time_currentTimeMillis(0, exceptionData);
	nova_1_i = 0;
	for (; nova_1_i < nova_1_iterations; nova_1_i++)
	{
		TRY
		{
			nova_ExceptionData_addCode(exceptionData, exceptionData, 1);
			
			{
				nova_static_Math_sin(0, exceptionData, (double)(nova_1_i));
			}
		}
		CATCH (1)
		{
		}
		FINALLY
		{
		}
		END_TRY;
	}
	nova_1_time = (long_long)(nova_static_Time_currentTimeMillis(0, exceptionData) - nova_1_start);
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_concat(nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_1_iterations), exceptionData), exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, " iterations of Math.sin() in "), exceptionData, nova_String_concat(nova_3_Long_toString(nova_Long_construct(0, exceptionData, nova_1_time), exceptionData), exceptionData, nova_String_construct(0, exceptionData, "ms")))));
	nova_static_Console_waitForEnter(0, exceptionData);
}

void nova_MathDemo_this(MathDemo* this, ExceptionData* exceptionData)
{
}

void nova_MathDemo_super(MathDemo* this, ExceptionData* exceptionData)
{
}
