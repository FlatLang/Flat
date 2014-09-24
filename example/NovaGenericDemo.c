#include <precompiled.h>
#include "NovaGenericDemo.h"


nova_VTable_GenericDemo nova_VTable_GenericDemo_val =
{
		nova_0_Object_toString,
		nova_0_Object_equals,
};

GenericDemo* nova_0_GenericDemo_construct(GenericDemo* this, ExceptionData* exceptionData)
{
		CCLASS_NEW(GenericDemo, this,);
		this->vtable = &nova_VTable_GenericDemo_val;
		nova_Object_super((Object*)this, 0);
		nova_Object_this((Object*)(this), exceptionData);
		nova_GenericDemo_super(this, 0);
		
		{
				nova_GenericDemo_this(this, exceptionData);
		}
		
		return this;
}

void nova_del_GenericDemo(GenericDemo** this, ExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void nova_static_GenericDemo_main(GenericDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
		Stack* nova_1_strs;
		HashMap* nova_1_words;
		Int** nova_1_nums;
		int nova_1_i;
		
		nova_1_strs = nova_0_Stack_construct(0, exceptionData);
		nova_Stack_test(nova_1_strs, exceptionData);
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Pushing \"test\""));
		nova_Stack_push(nova_1_strs, exceptionData, (Object*)(nova_String_construct(0, exceptionData, "test")));
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Pushing \"ASDFASDF\""));
		nova_Stack_push(nova_1_strs, exceptionData, (Object*)(nova_String_construct(0, exceptionData, "ASDFASDF")));
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Pushing \"3!\""));
		nova_Stack_push(nova_1_strs, exceptionData, (Object*)(nova_String_construct(0, exceptionData, "3!")));
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Pushing \"34!!4334\""));
		nova_Stack_push(nova_1_strs, exceptionData, (Object*)(nova_String_construct(0, exceptionData, "34!!4334")));
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Pushing null"));
		nova_Stack_push(nova_1_strs, exceptionData, (Object*)nova_null);
		while (0 == nova_Stack_isEmpty(nova_1_strs, exceptionData))
		{
				String* nova_2_popped;
				
				nova_2_popped = (String*)nova_Stack_pop(nova_1_strs, exceptionData);
				if (nova_2_popped != (String*)nova_null)
				{
						nova_2_popped = nova_0_String_concat(nova_String_construct(0, exceptionData, "\""), exceptionData, nova_2_popped->vtable->nova_virtual_0_concat(nova_2_popped, exceptionData, nova_String_construct(0, exceptionData, "\"")));
				}
				nova_static_0_Console_writeLine(0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, "Popping: "), exceptionData, nova_2_popped));
		}
		nova_1_words = nova_0_HashMap_construct(0, exceptionData);
		nova_1_nums = (Int**)NOVA_MALLOC(sizeof(Int) * (6));
		nova_1_nums[0] = nova_Int_construct(0, exceptionData, 0);
		nova_1_nums[1] = nova_Int_construct(0, exceptionData, 1);
		nova_1_nums[2] = nova_Int_construct(0, exceptionData, 2);
		nova_1_nums[3] = nova_Int_construct(0, exceptionData, 3);
		nova_1_nums[4] = nova_Int_construct(0, exceptionData, 4);
		nova_1_nums[5] = nova_Int_construct(0, exceptionData, 5);
		nova_HashMap_put(nova_1_words, exceptionData, (Object*)(nova_1_nums[0]), (Object*)(nova_String_construct(0, exceptionData, "Zero")));
		nova_HashMap_put(nova_1_words, exceptionData, (Object*)(nova_1_nums[1]), (Object*)(nova_String_construct(0, exceptionData, "One")));
		nova_HashMap_put(nova_1_words, exceptionData, (Object*)(nova_1_nums[2]), (Object*)(nova_String_construct(0, exceptionData, "Two")));
		nova_HashMap_put(nova_1_words, exceptionData, (Object*)(nova_1_nums[3]), (Object*)(nova_String_construct(0, exceptionData, "Three")));
		nova_HashMap_put(nova_1_words, exceptionData, (Object*)(nova_1_nums[4]), (Object*)(nova_String_construct(0, exceptionData, "Four")));
		nova_HashMap_put(nova_1_words, exceptionData, (Object*)(nova_1_nums[5]), (Object*)(nova_String_construct(0, exceptionData, "Five")));
		nova_HashMap_put(nova_1_words, exceptionData, (Object*)(nova_1_nums[5]), (Object*)nova_null);
		nova_1_i = 0;
		for (; nova_1_i <= 5; nova_1_i++)
		{
				Int* nova_4_index;
				String* nova_local_0;
				
				nova_4_index = nova_1_nums[nova_static_Math_random(0, exceptionData, (long_long)(6))];
				nova_local_0 = nova_4_index->vtable->nova_virtual_2_toString(nova_4_index, exceptionData);
				nova_static_0_Console_writeLine(0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, "Getting value at index "), exceptionData, nova_local_0->vtable->nova_virtual_0_concat(nova_local_0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, " "), exceptionData, (String*)((String*)nova_HashMap_get(nova_1_words, exceptionData, (Object*)(nova_4_index)))))));
		}
		nova_static_Console_waitForEnter(0, exceptionData);
}

void nova_GenericDemo_this(GenericDemo* this, ExceptionData* exceptionData)
{
}

void nova_GenericDemo_super(GenericDemo* this, ExceptionData* exceptionData)
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
				nova_static_GenericDemo_main(0, exceptionData, args);
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