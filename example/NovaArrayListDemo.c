#include <precompiled.h>
#include "NovaArrayListDemo.h"


nova_VTable_ArrayListDemo nova_VTable_ArrayListDemo_val =
{
		nova_0_Object_toString,
		nova_0_Object_equals,
};

ArrayListDemo* nova_0_ArrayListDemo_construct(ArrayListDemo* this, ExceptionData* exceptionData)
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
				int nova_3_i;
				
				nova_2_list = nova_ArrayList_construct(0, exceptionData);
				nova_ArrayList_add(nova_2_list, exceptionData, (Object*)(nova_0_Dog_construct(0, exceptionData)));
				nova_ArrayList_add(nova_2_list, exceptionData, (Object*)(nova_0_Dog_construct(0, exceptionData)));
				nova_ArrayList_add(nova_2_list, exceptionData, (Object*)(nova_0_Spider_construct(0, exceptionData)));
				nova_ArrayList_add(nova_2_list, exceptionData, (Object*)(nova_0_Dog_construct(0, exceptionData)));
				nova_ArrayList_add(nova_2_list, exceptionData, (Object*)(nova_0_Animal_construct(0, exceptionData)));
				nova_ArrayList_add(nova_2_list, exceptionData, (Object*)(nova_0_Spider_construct(0, exceptionData)));
				nova_3_i = 0;
				for (; nova_3_i < nova_2_list->nova_ArrayList_size; nova_3_i++)
				{
						Animal* nova_local_0;
						
						nova_local_0 = ((Animal*)nova_ArrayList_get(nova_2_list, exceptionData, nova_3_i));
						nova_static_0_Console_writeLine(0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, "Contains: "), exceptionData, nova_local_0->vtable->nova_virtual_0_toString(nova_local_0, exceptionData)));
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


Null* nova_null;

int main(int argc, char** argvs)
{
		String** args;
		int      i;
		
		ExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_null = nova_0_Null_construct(0, exceptionData);
		nova_static_GC_init(0, exceptionData);
		
		MathNova_init_static();
		
		args = (String**)NOVA_MALLOC(argc * sizeof(String));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_String_construct(0, 0, str);
		}
		
		TRY
		{
				nova_static_ArrayListDemo_main(0, exceptionData, args);
		}
		CATCH (1)
		{
				printf("You broke it.");
				nova_static_Console_waitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		GC_gcollect();
		
		return 0;
}