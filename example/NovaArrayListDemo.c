#include <precompiled.h>
#include "NovaArrayListDemo.h"




ArrayListDemo* nova_ArrayListDemo_ArrayListDemo(ExceptionData* exceptionData)
{
	ArrayListDemo* this = (ArrayListDemo*)1;
	
	{
	}
	
	return this;
}

void nova_del_ArrayListDemo(ArrayListDemo** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_ArrayListDemo_main(ArrayListDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
	char nova_1_c;
	
	nova_1_c = 'y';
	while (nova_1_c == 'y' || nova_1_c == 'Y')
	{
		ArrayList* nova_2_list;
		int nova_2_i;
		
		nova_2_list = nova_ArrayList_ArrayList(exceptionData);
		nova_ArrayList_add((ArrayList*)(nova_2_list), exceptionData, 1);
		nova_ArrayList_add((ArrayList*)(nova_2_list), exceptionData, 2);
		nova_ArrayList_add((ArrayList*)(nova_2_list), exceptionData, 4);
		nova_ArrayList_add((ArrayList*)(nova_2_list), exceptionData, 3);
		nova_2_i = 0;
		for (; nova_2_i < nova_2_list->nova_ArrayList_size; nova_2_i++)
		{
			nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)("Contains: ")), exceptionData, nova_Integer_toString((Integer*)(nova_Integer_Integer(exceptionData, nova_ArrayList_get((ArrayList*)(nova_2_list), exceptionData, nova_2_i))), exceptionData)));
		}
		nova_static_Console_write((Console*)(0), exceptionData, nova_String_String(exceptionData, "Run again? (Y/N)"));
		nova_1_c = nova_static_Console_readChar((Console*)(0), exceptionData);
	}
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "\nFinished"));
	nova_static_Console_waitForEnter((Console*)(0), exceptionData);
}
