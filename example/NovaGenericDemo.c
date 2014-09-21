#include <precompiled.h>
#include "NovaGenericDemo.h"


nova_VTable_GenericDemo nova_VTable_GenericDemo_val =
{
		nova_0_Object_toString,
		nova_0_Object_equals,
};

void nova_static_GenericDemo_push(GenericDemo* this, ExceptionData* exceptionData, Stack* nova_0_s, Object* nova_0_o);
void nova_static_GenericDemo_pop(GenericDemo* this, ExceptionData* exceptionData, Stack* nova_0_s);

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
		Object* nova_1_o;
		
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Test"));
		nova_1_strs = nova_0_Stack_construct(0, exceptionData);
		nova_static_GenericDemo_push((GenericDemo*)nova_null, exceptionData, nova_1_strs, (Object*)(nova_String_construct(0, exceptionData, "test")));
		nova_static_GenericDemo_push((GenericDemo*)nova_null, exceptionData, nova_1_strs, (Object*)(nova_String_construct(0, exceptionData, "ASDFASDF")));
		nova_static_GenericDemo_push((GenericDemo*)nova_null, exceptionData, nova_1_strs, (Object*)(nova_String_construct(0, exceptionData, "3!")));
		nova_static_GenericDemo_push((GenericDemo*)nova_null, exceptionData, nova_1_strs, (Object*)(nova_String_construct(0, exceptionData, "34!!4334")));
		nova_static_GenericDemo_push((GenericDemo*)nova_null, exceptionData, nova_1_strs, (Object*)nova_null);
		nova_1_o = nova_0_Object_construct(0, exceptionData);
		while (0 == nova_Stack_isEmpty(nova_1_strs, exceptionData))
		{
				nova_static_GenericDemo_pop((GenericDemo*)nova_null, exceptionData, nova_1_strs);
		}
		nova_static_Console_waitForEnter(0, exceptionData);
}

void nova_static_GenericDemo_push(GenericDemo* this, ExceptionData* exceptionData, Stack* nova_0_s, Object* nova_0_o)
{
		nova_static_0_Console_writeLine(0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, "Pushing: "), exceptionData, nova_0_o->vtable->nova_virtual_0_toString(nova_0_o, exceptionData)));
		nova_Stack_push(nova_0_s, exceptionData, nova_0_o);
}

void nova_static_GenericDemo_pop(GenericDemo* this, ExceptionData* exceptionData, Stack* nova_0_s)
{
		nova_static_0_Console_writeLine(0, exceptionData, nova_0_String_concat(nova_String_construct(0, exceptionData, "Popping: "), exceptionData, (String*)(nova_Stack_pop(nova_0_s, exceptionData))));
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