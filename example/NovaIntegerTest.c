#include <precompiled.h>
#include "NovaIntegerTest.h"


nova_VTable_IntegerTest nova_VTable_IntegerTest_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

IntegerTest* nova_IntegerTest_construct(IntegerTest* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(IntegerTest, this,);
	this->vtable = &nova_VTable_IntegerTest_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_IntegerTest_super(this, 0);
	
	{
		nova_IntegerTest_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_IntegerTest(IntegerTest** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_IntegerTest_main(IntegerTest* this, ExceptionData* exceptionData, String** nova_0_args)
{
	char nova_1_c;
	
	nova_1_c = 'y';
	while (nova_1_c == 'y' || nova_1_c == 'Y')
	{
		long_long nova_2_start;
		int nova_2_i;
		long_long nova_2_end;
		
		nova_2_start = nova_static_Time_currentTimeMillis(0, exceptionData);
		nova_2_i = 0;
		for (; nova_2_i < 999999; nova_2_i++)
		{
			nova_static_2_Long_toString(0, exceptionData, (long_long)(nova_2_i));
		}
		nova_2_end = nova_static_Time_currentTimeMillis(0, exceptionData);
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "Time taken: "), exceptionData, nova_String_concat(nova_3_Long_toString(nova_Long_construct(0, exceptionData, (nova_2_end - nova_2_start)), exceptionData), exceptionData, nova_String_construct(0, exceptionData, "ms"))));
		nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Run again? (Y/N) "));
		nova_1_c = nova_static_Console_readChar(0, exceptionData);
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "\nFinished"));
	nova_static_Console_waitForEnter(0, exceptionData);
}

void nova_IntegerTest_this(IntegerTest* this, ExceptionData* exceptionData)
{
}

void nova_IntegerTest_super(IntegerTest* this, ExceptionData* exceptionData)
{
}
