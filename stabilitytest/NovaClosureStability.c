#include <precompiled.h>
#include "NovaClosureStability.h"

typedef int (*nova_1_0_closure)(void*, ExceptionData*, int, int);
typedef double (*nova_2_0_closure)(void*, ExceptionData*, double);
typedef void (*nova_3_0_closure)(void*, ExceptionData*);

nova_VTable_ClosureStability nova_VTable_ClosureStability_val =
{
	nova_Object_toString,
	nova_Object_equals,
};
CCLASS_PRIVATE
(
	int nova_ClosureStability_number;
	
)

void nova_ClosureStability_incrementNumber(ClosureStability* this, ExceptionData* exceptionData);
void nova_static_ClosureStability_testClosures(ClosureStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_static_ClosureStability_testMathClosures(ClosureStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_static_ClosureStability_testInstanceClosure(ClosureStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
int nova_static_ClosureStability_callClosure(ClosureStability* this, ExceptionData* exceptionData, nova_1_0_closure nova_0_closure, void* nova_ref_0_closure, int nova_0_a, int nova_0_b);
double nova_static_ClosureStability_mathClosure(ClosureStability* this, ExceptionData* exceptionData, nova_2_0_closure nova_0_closure, void* nova_ref_0_closure, double nova_0_value);
void nova_static_ClosureStability_instanceClosure(ClosureStability* this, ExceptionData* exceptionData, nova_3_0_closure nova_0_closure, void* nova_ref_0_closure);
int nova_static_ClosureStability_multiply(ClosureStability* this, ExceptionData* exceptionData, int nova_0_value1, int nova_0_value2);
int nova_static_ClosureStability_pow(ClosureStability* this, ExceptionData* exceptionData, int nova_0_base, int nova_0_pow);
double nova_static_ClosureStability_TOLERANCE;

ClosureStability* nova_ClosureStability_ClosureStability(ExceptionData* exceptionData)
{
	CCLASS_NEW(ClosureStability, this);
	
	this->prv->nova_ClosureStability_number = 0;
	this->vtable = &nova_VTable_ClosureStability_val;
	{
	}
	
	return this;
}

void nova_del_ClosureStability(ClosureStability** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_ClosureStability_incrementNumber(ClosureStability* this, ExceptionData* exceptionData)
{
	this->prv->nova_ClosureStability_number++;
}

void nova_static_ClosureStability_test(ClosureStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	nova_static_ClosureStability_TOLERANCE = 0.0000000001;
	nova_static_ClosureStability_testClosures((ClosureStability*)0, exceptionData, nova_0_program);
}

void nova_static_ClosureStability_testClosures(ClosureStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "Testing closures..."));
	nova_static_ClosureStability_testMathClosures((ClosureStability*)0, exceptionData, nova_0_program);
	nova_static_ClosureStability_testInstanceClosure((ClosureStability*)0, exceptionData, nova_0_program);
}

void nova_static_ClosureStability_testMathClosures(ClosureStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	int nova_1_a;
	int nova_1_b;
	double nova_1_value;
	
	nova_static_Console_write((Console*)(0), exceptionData, nova_String_String(exceptionData, "Testing static math closures... "));
	nova_1_a = 5;
	nova_1_b = 3;
	nova_1_value = 0.5;
	if (nova_static_ClosureStability_callClosure((ClosureStability*)0, exceptionData, (nova_1_0_closure)&nova_static_ClosureStability_multiply, (ClosureStability*)0, nova_1_a, nova_1_b) != nova_static_ClosureStability_multiply((ClosureStability*)0, exceptionData, nova_1_a, nova_1_b))
	{
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Failed to call multiply(int, int) closure"));
	}
	if (nova_static_ClosureStability_callClosure((ClosureStability*)0, exceptionData, (nova_1_0_closure)&nova_static_ClosureStability_pow, (ClosureStability*)0, nova_1_a, nova_1_b) != nova_static_ClosureStability_pow((ClosureStability*)0, exceptionData, nova_1_a, nova_1_b))
	{
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Failed to call pow(int, int) closure"));
	}
	if (nova_static_ClosureStability_mathClosure((ClosureStability*)0, exceptionData, (nova_2_0_closure)&nova_static_Math_sin, 0, nova_1_value) - nova_static_Math_sin((Math*)(0), exceptionData, nova_1_value) >= nova_static_ClosureStability_TOLERANCE)
	{
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Failed to call Math.sin(double) closure"));
	}
	if (nova_static_ClosureStability_mathClosure((ClosureStability*)0, exceptionData, (nova_2_0_closure)&nova_static_Math_tan, 0, nova_1_value) - nova_static_Math_tan((Math*)(0), exceptionData, nova_1_value) >= nova_static_ClosureStability_TOLERANCE)
	{
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Failed to call Math.tan(double) closure"));
	}
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "OK"));
}

void nova_static_ClosureStability_testInstanceClosure(ClosureStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	ClosureStability* nova_1_c;
	
	nova_static_Console_write((Console*)(0), exceptionData, nova_String_String(exceptionData, "Testing instance closures... "));
	nova_1_c = nova_ClosureStability_ClosureStability(exceptionData);
	nova_static_ClosureStability_instanceClosure((ClosureStability*)0, exceptionData, (nova_3_0_closure)&nova_ClosureStability_incrementNumber, nova_1_c);
	if (nova_1_c->prv->nova_ClosureStability_number == 0)
	{
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Failed to call incrementNumber() instance closure"));
	}
	nova_static_ClosureStability_instanceClosure((ClosureStability*)0, exceptionData, (nova_3_0_closure)&nova_ClosureStability_incrementNumber, nova_ClosureStability_ClosureStability(exceptionData));
	nova_static_ClosureStability_instanceClosure((ClosureStability*)0, exceptionData, (nova_3_0_closure)&nova_ClosureStability_incrementNumber, nova_1_c);
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "OK"));
}

int nova_static_ClosureStability_callClosure(ClosureStability* this, ExceptionData* exceptionData, nova_1_0_closure nova_0_closure, void* nova_ref_0_closure, int nova_0_a, int nova_0_b)
{
	return nova_0_closure(nova_ref_0_closure, exceptionData, nova_0_a, nova_0_b);
}

double nova_static_ClosureStability_mathClosure(ClosureStability* this, ExceptionData* exceptionData, nova_2_0_closure nova_0_closure, void* nova_ref_0_closure, double nova_0_value)
{
	return nova_0_closure(nova_ref_0_closure, exceptionData, nova_0_value);
}

void nova_static_ClosureStability_instanceClosure(ClosureStability* this, ExceptionData* exceptionData, nova_3_0_closure nova_0_closure, void* nova_ref_0_closure)
{
	nova_0_closure(nova_ref_0_closure, exceptionData);
}

int nova_static_ClosureStability_multiply(ClosureStability* this, ExceptionData* exceptionData, int nova_0_value1, int nova_0_value2)
{
	return nova_0_value1 * nova_0_value2;
}

int nova_static_ClosureStability_pow(ClosureStability* this, ExceptionData* exceptionData, int nova_0_base, int nova_0_pow)
{
	int nova_1_value;
	int nova_1_i;
	
	nova_1_value = nova_0_base;
	nova_1_i = 0;
	for (; nova_1_i < nova_0_pow - 1; nova_1_i++)
	{
		nova_1_value = nova_1_value * nova_0_base;
	}
	return nova_1_value;
}
