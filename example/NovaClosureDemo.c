#include <precompiled.h>
#include "NovaClosureDemo.h"

typedef int (*nova_1_0_closure)(void*, ExceptionData*, int, int);
typedef int (*nova_2_0_closure)(void*, ExceptionData*, int, int);

nova_VTable_ClosureDemo nova_VTable_ClosureDemo_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

void nova_ClosureDemo_callClosure(ClosureDemo* this, ExceptionData* exceptionData, nova_2_0_closure nova_0_closure, void* nova_ref_0_closure);
int nova_static_ClosureDemo_multiply(ClosureDemo* this, ExceptionData* exceptionData, int nova_0_value1, int nova_0_value2);
int nova_static_ClosureDemo_pow(ClosureDemo* this, ExceptionData* exceptionData, int nova_0_base, int nova_0_pow);

ClosureDemo* nova_ClosureDemo_construct(ClosureDemo* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(ClosureDemo, this,);
	this->vtable = &nova_VTable_ClosureDemo_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_ClosureDemo_super(this, 0);
	
	{
		nova_ClosureDemo_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_ClosureDemo(ClosureDemo** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_ClosureDemo_main(ClosureDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
	ClosureDemo* nova_1_demo;
	
	nova_1_demo = nova_ClosureDemo_construct(0, exceptionData);
	nova_ClosureDemo_callClosure(nova_1_demo, exceptionData, (nova_2_0_closure)&nova_static_ClosureDemo_multiply, (ClosureDemo*)0);
	nova_ClosureDemo_callClosure(nova_1_demo, exceptionData, (nova_2_0_closure)&nova_static_ClosureDemo_pow, (ClosureDemo*)0);
	nova_static_Console_waitForEnter(0, exceptionData);
}

void nova_ClosureDemo_callClosure(ClosureDemo* this, ExceptionData* exceptionData, nova_2_0_closure nova_0_closure, void* nova_ref_0_closure)
{
	int nova_1_value;
	
	nova_1_value = nova_0_closure(this, exceptionData, 5, 3);
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "Closure returned "), exceptionData, nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_1_value), exceptionData)));
}

int nova_static_ClosureDemo_multiply(ClosureDemo* this, ExceptionData* exceptionData, int nova_0_value1, int nova_0_value2)
{
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "multiply was called with "), exceptionData, nova_String_concat(nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_0_value1), exceptionData), exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, " and "), exceptionData, nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_0_value2), exceptionData)))));
	return nova_0_value1 * nova_0_value2;
}

int nova_static_ClosureDemo_pow(ClosureDemo* this, ExceptionData* exceptionData, int nova_0_base, int nova_0_pow)
{
	int nova_1_value;
	int nova_1_i;
	
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "pow was called with "), exceptionData, nova_String_concat(nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_0_base), exceptionData), exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, " and "), exceptionData, nova_2_Int_toString(nova_Int_construct(0, exceptionData, nova_0_pow), exceptionData)))));
	nova_1_value = nova_0_base;
	nova_1_i = 0;
	for (; nova_1_i < nova_0_pow - 1; nova_1_i++)
	{
		nova_1_value = nova_1_value * nova_0_base;
	}
	return nova_1_value;
}

void nova_ClosureDemo_this(ClosureDemo* this, ExceptionData* exceptionData)
{
}

void nova_ClosureDemo_super(ClosureDemo* this, ExceptionData* exceptionData)
{
}
