#include <precompiled.h>
#include "NovaArrayListDemo.h"


nova_VTable_ArrayListDemo nova_VTable_ArrayListDemo_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

ArrayListDemo* nova_ArrayListDemo_construct(ArrayListDemo* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(ArrayListDemo, this,);
	this->vtable = &nova_VTable_ArrayListDemo_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_ArrayListDemo_super(this, 0);
	
	{
		nova_ArrayListDemo_this(this, exceptionData);
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
		
		nova_2_list = nova_ArrayList_construct(0, exceptionData);
		nova_ArrayList_add(nova_2_list, exceptionData, 1);
		nova_ArrayList_add(nova_2_list, exceptionData, 2);
		nova_ArrayList_add(nova_2_list, exceptionData, 4);
		nova_ArrayList_add(nova_2_list, exceptionData, 3);
		nova_2_i = 0;
		for (; nova_2_i < nova_2_list->nova_ArrayList_size; nova_2_i++)
		{
			nova_static_0_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "Contains: "), exceptionData, nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_ArrayList_get(nova_2_list, exceptionData, nova_2_i)), exceptionData)));
		}
		nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Run again? (Y/N)"));
		nova_1_c = nova_static_Console_readChar(0, exceptionData);
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "\nFinished"));
	nova_static_Console_waitForEnter(0, exceptionData);
}

void nova_ArrayListDemo_this(ArrayListDemo* this, ExceptionData* exceptionData)
{
}

void nova_ArrayListDemo_super(ArrayListDemo* this, ExceptionData* exceptionData)
{
}
